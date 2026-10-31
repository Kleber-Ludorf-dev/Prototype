package pedidos;

// Esta interface define o contrato do padrão Prototype.
// Toda classe que puder ser copiada deve implementá-la.
// O tipo genérico T indica qual é o tipo do objeto devolvido pela cópia.
public interface Prototype<T> {

    // Este método devolve uma nova cópia do objeto atual.
    T clonar();
}
