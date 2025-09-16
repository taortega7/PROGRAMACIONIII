import java.util.Stack;

public class PilaS441 {

    public static void main(String[] args) {

        // CREACION DE UNA PILA DE NUM ENTEROS
        Stack<Integer> pila = new Stack<>();

        // VERIFICAR SI LA PILA ESTA VACIA
        System.out.println(pila.empty()); // true

        // INSERTAR ELEMENTOS EN LA PILA
        pila.push(8);
        pila.push(4);
        pila.push(9);
        pila.push(10);
        pila.push(6);

        // IMPRIMIR LA PILA
        System.out.println(pila);

        // MOSTRAR EL ELEMENTO TOPE DE LA PILA
        System.out.println("Tope de la pila: " + pila.peek());

        // ELIMINAR EL ELEMENTO TOPE DE LA PILA
        System.out.println("Elemento removido del tope de la pila: " + pila.pop());

        // IMPRIMIR LA PILA
        System.out.println(pila);

        // BUSCAR Y MOSTRAR LA POSICION DE UN ELEMENTO EN LA PILA
        System.out.println(pila.search(10));
        System.out.println(pila.search(9));
        System.out.println(pila.search(4));
        System.out.println(pila.search(8));
        System.out.println(pila.search(50));

        // VERIFICAR SI LA PILA ESTA VACIA
        System.out.println(pila.empty()); // false
    }
}