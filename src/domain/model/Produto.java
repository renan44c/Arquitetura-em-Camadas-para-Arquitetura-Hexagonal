package domain.model;

/**
 * ============================================================
 * CAMADA: DOMAIN — Model
 * ============================================================
 *
 * RESPONSABILIDADE:
 *   Representa o produto com suas validações de negócio.
 *
 * REGRA HEXAGONAL:
 *   Esta classe não importa nada fora de "domain".
 *   Ela não conhece banco de dados, HTTP, CLI — nada externo.
 *   É o coração do hexágono: puro, isolado, testável.
 */
public class Produto {

    private final String id;
    private final String nome;
    private final double preco;

    public Produto(String id, String nome, double preco) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("ID do produto é obrigatório.");
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome do produto é obrigatório.");
        if (preco < 0)
            throw new IllegalArgumentException("Preço não pode ser negativo.");

        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public String getId()    { return id; }
    public String getNome()  { return nome; }
    public double getPreco() { return preco; }

    @Override
    public String toString() {
        return String.format("Produto[id=%s, nome=%s, preco=%.2f]", id, nome, preco);
    }
}
