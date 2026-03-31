package domain.port;

import domain.model.Produto;
import java.util.List;
import java.util.Optional;

/**
 * ============================================================
 * CAMADA: DOMAIN — Porta de Saída (Driven Port / Secondary Port)
 * ============================================================
 *
 * RESPONSABILIDADE:
 *   Define o CONTRATO que o domínio exige para persistir produtos.
 *   É uma interface declarada dentro do domínio — o domínio
 *   diz "eu preciso disso", mas não sabe quem vai implementar.
 *
 * COMPARAÇÃO COM O PROJETO ORIGINAL:
 *   Antes: GerenciadorEstoque dependia de ProdutoRepositorio
 *          (classe concreta da infraestrutura).
 *   Agora: GerenciadorEstoque depende desta interface.
 *          ProdutoRepositorioEmMemoria (adaptador) a implementa.
 *
 * REGRA HEXAGONAL (Dependency Inversion Principle):
 *   A dependência aponta para DENTRO (domínio).
 *   Infraestrutura depende do domínio — nunca o contrário.
 *
 *   [Adaptador de Saída] --> implementa --> [ProdutoRepositorioPort] (domínio)
 *
 * TROCAR DE BANCO É TRIVIAL:
 *   Crie ProdutoRepositorioPostgres, ProdutoRepositorioMongo —
 *   sem alterar uma linha do domínio ou dos casos de uso.
 */
public interface ProdutoRepositorioPort {

    void salvar(Produto produto);

    Optional<Produto> buscarPorId(String id);

    void excluirPorId(String id);

    List<Produto> listarTodos();
}
