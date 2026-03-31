package application.usecase;

import domain.model.Estoque;
import domain.model.Produto;
import domain.port.GerenciadorEstoquePort;
import domain.port.ProdutoRepositorioPort;

import java.util.List;

/**
 * ============================================================
 * CAMADA: APPLICATION — Caso de Uso
 * ============================================================
 *
 * RESPONSABILIDADE:
 *   Implementa a porta de entrada GerenciadorEstoquePort.
 *   Orquestra o fluxo dos casos de uso usando o domínio
 *   e a porta de saída.
 *
 * COMPARAÇÃO COM O PROJETO ORIGINAL:
 *   GerenciadorEstoque (original) dependia de ProdutoRepositorio
 *   (classe concreta). Agora dependemos de ProdutoRepositorioPort
 *   (interface do domínio) — nunca da implementação concreta.
 *
 * REGRA HEXAGONAL:
 *   Depende apenas de:
 *     - domain.model.*     (entidades e lógica de negócio)
 *     - domain.port.*      (contratos/interfaces)
 *   NUNCA de adaptadores concretos.
 *
 *   A implementação concreta (ProdutoRepositorioEmMemoria)
 *   é injetada pelo "wiring" externo (Main / DI container).
 *
 * SOBRE Optional:
 *   buscarPorId retorna Optional para forçar o tratamento
 *   explícito de "produto não encontrado" — evitando NPE.
 */
public class GerenciadorEstoqueService implements GerenciadorEstoquePort {

    private final ProdutoRepositorioPort repositorio;
    private final Estoque estoque;

    public GerenciadorEstoqueService(ProdutoRepositorioPort repositorio) {
        this.repositorio = repositorio;
        this.estoque = new Estoque();
    }

    @Override
    public void cadastrarProduto(String id, String nome, double preco) {
        Produto produto = new Produto(id, nome, preco);
        repositorio.salvar(produto);
    }

    @Override
    public Produto consultarProduto(String id) {
        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Produto com ID '" + id + "' não encontrado."));
    }

    @Override
    public void excluirProduto(String id) {
        // Valida existência antes de excluir (regra de negócio)
        repositorio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Produto com ID '" + id + "' não encontrado."));
        repositorio.excluirPorId(id);
    }

    @Override
    public double calcularPrecoMedio() {
        List<Produto> produtos = repositorio.listarTodos();
        return estoque.calcularPrecoMedio(produtos);
    }
}
