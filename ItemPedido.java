package pedidos;

import java.math.BigDecimal;

// Esta classe representa uma linha do pedido (um produto com quantidade e preço).
public class ItemPedido {

    private String produto;
    private int quantidade;
    // O preço usa BigDecimal porque double e float não são seguros para dinheiro (RT03).
    private BigDecimal precoUnitario;

    // Este construtor cria um item novo a partir dos dados informados.
    public ItemPedido(String produto, int quantidade, BigDecimal precoUnitario) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    // Este é o construtor de cópia: ele cria um item independente do original.
    // String e BigDecimal são imutáveis, então copiar a referência é seguro.
    public ItemPedido(ItemPedido origem) {
        this.produto = origem.produto;
        this.quantidade = origem.quantidade;
        this.precoUnitario = origem.precoUnitario;
    }

    // O subtotal é a quantidade multiplicada pelo preço unitário.
    public BigDecimal subtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    public String getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    // Este setter permite ajustar a quantidade, como no pedido B do cenário.
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }
}
