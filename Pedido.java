package pedidos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Esta classe representa um pedido e funciona como o "protótipo" do padrão.
public class Pedido implements Prototype<Pedido> {

    // Este contador é estático, ou seja, é compartilhado por todos os pedidos.
    // Ele garante ids sequenciais e únicos, começando em 1 (RF05).
    private static long proximoId = 1;

    private long id;
    private String cliente;
    private String enderecoEntrega;
    private List<ItemPedido> itens;
    private LocalDate criadoEm;

    // Este construtor recebe o novo id do contador e usa a data de hoje.
    // Como a cópia também passa por ele, ela ganha id e data novos (RF07).
    public Pedido(String cliente, String enderecoEntrega) {
        this.id = proximoId;
        proximoId++;
        this.cliente = cliente;
        this.enderecoEntrega = enderecoEntrega;
        this.itens = new ArrayList<>();
        this.criadoEm = LocalDate.now();
    }

    // Este método adiciona um item à lista do pedido.
    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }

    // Este método soma os subtotais dos itens e usa duas casas decimais.
    public BigDecimal valorTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemPedido item : itens) {
            total = total.add(item.subtotal());
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    // Este método faz a cópia profunda (RF06).
    // Primeiro criamos um pedido novo, que já recebe id e data novos.
    // Depois copiamos cliente e endereço e, por fim, cada item um por um.
    @Override
    public Pedido clonar() {
        Pedido copia = new Pedido(this.cliente, this.enderecoEntrega);
        for (ItemPedido item : this.itens) {
            // O construtor de cópia cria um objeto novo para cada item.
            copia.adicionarItem(new ItemPedido(item));
        }
        return copia;
    }

    public long getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public LocalDate getCriadoEm() {
        return criadoEm;
    }
}
