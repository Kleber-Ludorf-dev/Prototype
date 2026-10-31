package pedidos;

import java.util.HashMap;
import java.util.Map;

// Esta classe guarda os pedidos-modelo e entrega cópias deles.
public class CatalogoDePedidos {

    // O mapa associa uma chave de texto a um pedido-modelo.
    private Map<String, Pedido> modelos = new HashMap<>();

    // Este método guarda o modelo no catálogo com a chave informada.
    public void registrar(String chave, Pedido modelo) {
        modelos.put(chave, modelo);
    }

    // Este método devolve sempre uma cópia do modelo, nunca o objeto guardado (RF08).
    public Pedido criar(String chave) {
        Pedido modelo = modelos.get(chave);
        // Se a chave não existe, o mapa devolve null e lançamos o erro pedido em RF09.
        if (modelo == null) {
            throw new IllegalArgumentException("Modelo de pedido não registrado: " + chave);
        }
        return modelo.clonar();
    }
}
