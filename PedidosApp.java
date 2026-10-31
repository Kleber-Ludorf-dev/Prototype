package pedidos;

import java.math.BigDecimal;
import java.util.Locale;

// Esta classe executa o cenário de teste e verifica os critérios de aceitação.
// Ela não tem regras de negócio, apenas monta o cenário e confere os resultados (RT05).
public class PedidosApp {

    public static void main(String[] args) {

        //Montagem do pedido-modelo
        Pedido modelo = new Pedido("(modelo)", "-");
        modelo.adicionarItem(new ItemPedido("Café 500g", 10, new BigDecimal("18.90")));
        modelo.adicionarItem(new ItemPedido("Açúcar 1kg", 20, new BigDecimal("4.50")));
        modelo.adicionarItem(new ItemPedido("Filtro de papel", 5, new BigDecimal("7.20")));

        CatalogoDePedidos catalogo = new CatalogoDePedidos();
        catalogo.registrar("reposicao-mensal", modelo);

        //Pedido A: cópia sem nenhum ajuste
        Pedido a = catalogo.criar("reposicao-mensal");
        a.setCliente("Mercado Central");
        a.setEnderecoEntrega("Rua das Flores, 120");

        //Pedido B: muda a quantidade de Café para 20
        Pedido b = catalogo.criar("reposicao-mensal");
        b.setCliente("Padaria Boa Massa");
        b.setEnderecoEntrega("Av. Paraná, 800");
        b.getItens().get(0).setQuantidade(20);

        //Pedido C: adiciona o item Adoçante
        Pedido c = catalogo.criar("reposicao-mensal");
        c.setCliente("Restaurante do Porto");
        c.setEnderecoEntrega("Rua do Cais, 45");
        c.adicionarItem(new ItemPedido("Adoçante 100ml", 12, new BigDecimal("6.00")));

        // Impressão dos pedidos
        imprimir(modelo);
        imprimir(a);
        imprimir(b);
        imprimir(c);
        System.out.println();

        //Verificação dos critérios de aceitação

        // CA-01: todos os ids devem ser diferentes entre si.
        boolean idsDiferentes = true;
        Pedido[] todos = {modelo, a, b, c};
        for (int i = 0; i < todos.length; i++) {
            for (int j = i + 1; j < todos.length; j++) {
                if (todos[i].getId() == todos[j].getId()) {
                    idsDiferentes = false;
                }
            }
        }
        resultado("CA-01 Os quatro pedidos possuem ids diferentes", idsDiferentes);

        // CA-02: o total do modelo continua 315,00.
        resultado("CA-02 O total do modelo continua R$ 315,00",
                igual(modelo.valorTotal(), "315.00"));

        // CA-03: os totais de A, B e C estão corretos.
        boolean totaisCertos = igual(a.valorTotal(), "315.00")
                && igual(b.valorTotal(), "504.00")
                && igual(c.valorTotal(), "387.00");
        resultado("CA-03 Totais: A = 315,00; B = 504,00; C = 387,00", totaisCertos);

        // CA-04: o modelo continua com 3 itens.
        resultado("CA-04 O modelo continua com 3 itens", modelo.getItens().size() == 3);

        // CA-05: nenhum item das cópias é a mesma instância de um item do modelo.
        boolean instanciasDiferentes = true;
        Pedido[] copias = {a, b, c};
        for (Pedido copia : copias) {
            for (ItemPedido itemCopia : copia.getItens()) {
                for (ItemPedido itemModelo : modelo.getItens()) {
                    if (itemCopia == itemModelo) {
                        instanciasDiferentes = false;
                    }
                }
            }
        }
        resultado("CA-05 Nenhum item das cópias é o mesmo objeto do modelo", instanciasDiferentes);

        // CA-06: criar com chave inexistente deve lançar IllegalArgumentException.
        boolean lancouErro = false;
        try {
            catalogo.criar("inexistente");
        } catch (IllegalArgumentException e) {
            lancouErro = true;
        }
        resultado("CA-06 criar(\"inexistente\") lança IllegalArgumentException", lancouErro);

        // CA-07: o cliente do modelo continua "(modelo)".
        resultado("CA-07 O cliente do modelo permanece \"(modelo)\"",
                modelo.getCliente().equals("(modelo)"));
    }

    // Este método imprime um pedido no formato pedido pelo enunciado.
    private static void imprimir(Pedido p) {
        // Usamos o formato brasileiro para mostrar a vírgula como separador decimal.
        String total = String.format(Locale.forLanguageTag("pt-BR"), "%.2f", p.valorTotal());
        System.out.println("Pedido #" + p.getId() + " | " + p.getCliente() + " | "
                + p.getEnderecoEntrega() + " | itens=" + p.getItens().size()
                + " | total=R$ " + total);
    }

    // Este método compara um BigDecimal com um valor em texto, ignorando diferenças de escala.
    private static boolean igual(BigDecimal valor, String esperado) {
        return valor.compareTo(new BigDecimal(esperado)) == 0;
    }

    // Este método imprime OK ou FALHA para cada critério.
    private static void resultado(String descricao, boolean passou) {
        System.out.println(descricao + ": " + (passou ? "OK" : "FALHA"));
    }
}
