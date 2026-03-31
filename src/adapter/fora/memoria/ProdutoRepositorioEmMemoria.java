package adapter.out.memory;

import domain.model.Produto;
import domain.port.ProdutoRepositorioPort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * ============================================================
 * CAMADA: ADAPTER — Adaptador de Saída (Driven Adapter)
 * ============================================================
 *
 * RESPONSABILIDADE:
 *   Implementa ProdutoRepositorioPort usando HashMap em memória.
 *   É o equivalente direto do ProdutoRepositorio original,
 *   mas agora com um papel arquitetural explícito.
 *
 * COMPARAÇÃO COM O PROJETO ORIGINAL:
 *   ProdutoRepositorio (original) era uma classe concreta
 *   conhecida por toda a aplicação.
 *   Agora é um adaptador trocável — o domínio não sabe
 *   que ele existe.
 *
 * REGRA HEXAGONAL:
 *   Adaptadores de saída ficam "fora do hexágono".
 *   Eles conhecem o domínio (implementam a porta),
 *   mas o domínio não os conhece.
 *
 *   Substituição: criar ProdutoRepositorioPostgres
 *   implementando a mesma interface e trocar no "wiring".
 *   Zero impacto em domínio ou casos de uso.
 *
 * MUDANÇA TÉCNICA:
 *   buscarPorId agora retorna Optional<Produto>, eliminando
 *   retornos null e forçando tratamento explícito.
 */
public class ProdutoRepositorioEmMemoria implements ProdutoRepositorioPort {

    private final HashMap<String, Produto> banco = new HashMap<>();

    @Override
    public void salvar(Produto produto) {
        banco.put(produto.getId(), produto);
    }

    @Override
    public Optional<Produto> buscarPorId(String id) {
        return Optional.ofNullable(banco.get(id));
    }

    @Override
    public void excluirPorId(String id) {
        banco.remove(id);
    }

    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(banco.values());
    }
}
