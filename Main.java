import adapter.in.cli.EstoqueCLI;
import adapter.out.memory.ProdutoRepositorioEmMemoria;
import application.usecase.GerenciadorEstoqueService;
import domain.port.GerenciadorEstoquePort;
import domain.port.ProdutoRepositorioPort;

/**
 * ============================================================
 * WIRING — Composição das Dependências
 * ============================================================
 *
 * RESPONSABILIDADE:
 *   O único lugar do sistema onde as peças concretas se
 *   conectam. Instancia adaptadores, injeta nas portas,
 *   monta os casos de uso.
 *
 *   Em frameworks como Spring Boot, esta responsabilidade
 *   é delegada ao container de injeção de dependência
 *   (via @Bean, @Component, etc.).
 *
 * FLUXO DE DEPENDÊNCIAS (tudo aponta para dentro):
 *
 *   EstoqueCLI
 *      ↓ usa
 *   GerenciadorEstoquePort  (interface — porta de entrada)
 *      ↑ implementada por
 *   GerenciadorEstoqueService
 *      ↓ usa
 *   ProdutoRepositorioPort  (interface — porta de saída)
 *      ↑ implementada por
 *   ProdutoRepositorioEmMemoria
 *
 * TROCAR IMPLEMENTAÇÕES AQUI:
 *   Mudar banco de dados:
 *     ProdutoRepositorioPort repo = new ProdutoRepositorioPostgres(datasource);
 *   Mudar interface de entrada:
 *     new EstoqueRestController(gerenciador).iniciar(8080);
 *   ZERO linhas alteradas no domínio ou nos casos de uso.
 */
public class Main {

    public static void main(String[] args) {

        // Adaptador de saída: persistência em memória
        ProdutoRepositorioPort repositorio = new ProdutoRepositorioEmMemoria();

        // Caso de uso: recebe a interface, não a implementação
        GerenciadorEstoquePort gerenciador = new GerenciadorEstoqueService(repositorio);

        // Adaptador de entrada: CLI aciona o caso de uso
        EstoqueCLI cli = new EstoqueCLI(gerenciador);

        cli.executar();
    }
}
