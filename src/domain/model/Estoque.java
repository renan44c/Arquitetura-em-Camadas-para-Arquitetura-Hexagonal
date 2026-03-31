package domain.model;

import java.util.List;

/**
 * ============================================================
 * CAMADA: DOMAIN — Model
 * ============================================================
 *
 * RESPONSABILIDADE:
 *   Encapsula as regras de negócio relacionadas ao estoque.
 *   Neste exemplo: cálculo do preço médio.
 *
 * REGRA HEXAGONAL:
 *   Lógica de negócio pura. Zero dependências externas.
 *   Pode crescer com regras como: "estoque mínimo",
 *   "política de desconto", "margem de lucro" — sem tocar
 *   em nenhuma camada externa.
 */
public class Estoque {

    public double calcularPrecoMedio(List<Produto> produtos) {
        if (produtos == null || produtos.isEmpty()) return 0.0;

        return produtos.stream()
                .mapToDouble(Produto::getPreco)
                .average()
                .orElse(0.0);
    }
}
