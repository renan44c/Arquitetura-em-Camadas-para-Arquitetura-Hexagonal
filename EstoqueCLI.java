package adapter.in.cli;

import domain.model.Produto;
import domain.port.GerenciadorEstoquePort;

/**
 * ============================================================
 * CAMADA: ADAPTER — Adaptador de Entrada (Driver Adapter)
 * ============================================================
 *
 * RESPONSABILIDADE:
 *   Ponto de entrada da aplicação via linha de comando (CLI).
 *   Aciona os casos de uso através da porta de entrada.
 *
 * COMPARAÇÃO COM O PROJETO ORIGINAL:
 *   EstoqueController (original) instanciava diretamente
 *   ProdutoRepositorio e GerenciadorEstoque — acoplamento forte.
 *   Agora depende apenas de GerenciadorEstoquePort (interface).
 *   Não sabe nada sobre repositórios ou serviços concretos.
 *
 * REGRA HEXAGONAL:
 *   Adaptadores de entrada ficam "fora do hexágono".
 *   Eles conhecem a porta de entrada (interface) e a acionam.
 *   NUNCA instanciam casos de uso diretamente —
 *   isso é responsabilidade do "wiring" (Main).
 *
 * EXTENSIBILIDADE:
 *   Para expor as mesmas funcionalidades via REST,
 *   crie EstoqueRestController implementando os mesmos
 *   casos de uso via GerenciadorEstoquePort.
 *   Sem tocar em GerenciadorEstoqueService ou no domínio.
 */
public class EstoqueCLI {

    private final GerenciadorEstoquePort gerenciador;

    public EstoqueCLI(GerenciadorEstoquePort gerenciador) {
        this.gerenciador = gerenciador;
    }

    public void executar() {
        System.out.println("=== SISTEMA DE ESTOQUE - ARQUITETURA HEXAGONAL ===\n");

        gerenciador.cadastrarProduto("p1", "Teclado Mecânico", 250.00);
        gerenciador.cadastrarProduto("p2", "Mouse Gamer", 180.00);
        gerenciador.cadastrarProduto("p3", "Monitor 24\"", 920.00);

        Produto produto = gerenciador.consultarProduto("p2");
        System.out.println("Produto consultado: " + produto.getNome()
                + " - R$ " + produto.getPreco());

        double precoMedio = gerenciador.calcularPrecoMedio();
        System.out.println("Preço médio do estoque: R$ " + precoMedio);

        gerenciador.excluirProduto("p1");
        System.out.println("Produto p1 excluído com sucesso.");
    }
}
