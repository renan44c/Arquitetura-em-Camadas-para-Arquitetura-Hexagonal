# estoque-hexagono
## Análise Arquitetural — De Camadas para Hexagonal

---

## Estrutura de pacotes

```
estoque-hexagono/
└── src/
    ├── Main.java                              ← wiring (composição)
    │
    ├── domain/                                ← NÚCLEO DO HEXÁGONO
    │   ├── model/
    │   │   ├── Produto.java                   ← entidade de negócio
    │   │   └── Estoque.java                   ← regra de negócio
    │   └── port/
    │       ├── GerenciadorEstoquePort.java    ← porta de ENTRADA
    │       └── ProdutoRepositorioPort.java    ← porta de SAÍDA
    │
    ├── application/                           ← CASOS DE USO
    │   └── usecase/
    │       └── GerenciadorEstoqueService.java ← implementa porta entrada
    │
    └── adapter/                               ← FORA DO HEXÁGONO
        ├── in/
        │   └── cli/
        │       └── EstoqueCLI.java            ← adaptador de entrada
        └── out/
            └── memory/
                └── ProdutoRepositorioEmMemoria.java ← adaptador de saída
```

---

## Mapeamento: o que mudou e por quê

| Projeto original | Projeto hexagonal | Papel arquitetural |
|---|---|---|
| `Produto.java` | `domain/model/Produto.java` | Entidade de domínio (sem mudanças) |
| `Estoque.java` | `domain/model/Estoque.java` | Regra de negócio (sem mudanças) |
| *(não existia)* | `domain/port/GerenciadorEstoquePort.java` | **Porta de entrada** |
| *(não existia)* | `domain/port/ProdutoRepositorioPort.java` | **Porta de saída** |
| `GerenciadorEstoque.java` | `application/usecase/GerenciadorEstoqueService.java` | Caso de uso (agora depende de interfaces) |
| `EstoqueController.java` | `adapter/in/cli/EstoqueCLI.java` | **Adaptador de entrada** |
| `ProdutoRepositorio.java` | `adapter/out/memory/ProdutoRepositorioEmMemoria.java` | **Adaptador de saída** |
| *(main embutido no controller)* | `Main.java` | **Wiring** (composição das dependências) |

---

## O problema que a arquitetura hexagonal resolve

No projeto original, `GerenciadorEstoque` (Application) importava diretamente
`ProdutoRepositorio` (Infrastructure):

```java
// ANTES — acoplamento forte
import infrastructure.ProdutoRepositorio;

public class GerenciadorEstoque {
    private ProdutoRepositorio produtoRepositorio; // depende do concreto
}
```

Qualquer mudança na camada de persistência (trocar HashMap por Postgres)
obrigava a alterar a classe de negócio.

No projeto hexagonal, o caso de uso depende apenas da interface declarada
no próprio domínio:

```java
// DEPOIS — depende da abstração
import domain.port.ProdutoRepositorioPort;

public class GerenciadorEstoqueService implements GerenciadorEstoquePort {
    private final ProdutoRepositorioPort repositorio; // depende da interface
}
```

---

## Regra de ouro da arquitetura hexagonal

```
As dependências sempre apontam para dentro — em direção ao domínio.

[CLI]  →  [GerenciadorEstoquePort]  ←  [GerenciadorEstoqueService]
                                              ↓
                                    [ProdutoRepositorioPort]
                                              ↑
                                [ProdutoRepositorioEmMemoria]
```

O domínio nunca importa nada dos adaptadores.
Os adaptadores sempre importam do domínio (portas/interfaces).

---

## Extensibilidade demonstrada

### Adicionar interface REST (sem tocar no domínio)
```java
// adapter/in/rest/EstoqueRestController.java
public class EstoqueRestController {
    private final GerenciadorEstoquePort gerenciador;
    // ... mapeia HTTP para chamadas na porta de entrada
}
```

### Trocar persistência para PostgreSQL (sem tocar no domínio)
```java
// adapter/out/postgres/ProdutoRepositorioPostgres.java
public class ProdutoRepositorioPostgres implements ProdutoRepositorioPort {
    // ... implementa com JDBC/JPA
}
```

### No Main, apenas troca o adaptador
```java
ProdutoRepositorioPort repositorio = new ProdutoRepositorioPostgres(datasource);
// todo o resto permanece igual
```
