package domain.port;

import domain.model.Produto;

/**
 * ============================================================
 * CAMADA: DOMAIN — Porta de Entrada (Driver Port / Primary Port)
 * ============================================================
 *
 * RESPONSABILIDADE:
 *   Define o CONTRATO que o mundo externo usa para acionar
 *   os casos de uso do sistema.
 *
 * COMPARAÇÃO COM O PROJETO ORIGINAL:
 *   Antes: EstoqueController dependia diretamente de
 *          GerenciadorEstoque (classe concreta).
 *   Agora: EstoqueController depende desta interface.
 *          GerenciadorEstoqueService a implementa.
 *
 * REGRA HEXAGONAL:
 *   Adaptadores de entrada (CLI, REST, eventos) chamam
 *   métodos desta interface — não sabem quem implementa.
 *
 *   [Adaptador de Entrada] --> usa --> [GerenciadorEstoquePort] (domínio)
 *                                              ↑
 *                                   [GerenciadorEstoqueService] implementa
 *
 * BENEFÍCIO:
 *   Trocar CLI por REST Controller? Só criar outro adaptador.
 *   A lógica de negócio permanece intacta.
 */
public interface GerenciadorEstoquePort {

    void cadastrarProduto(String id, String nome, double preco);

    Produto consultarProduto(String id);

    void excluirProduto(String id);

    double calcularPrecoMedio();
}
