import java.util.Random;

public class ArbolBinario {

    // Clase que representa cada nodo del árbol
    // Cada nodo tiene un valor y dos hijos (izquierdo y derecho)
    static class Nodo {
        int valor;
        Nodo izq, der;
    }

    // Crea un nuevo nodo con el valor dado
    // Los hijos quedan en null automáticamente
    static Nodo nuevoNodo(int valor) {
        Nodo n = new Nodo();
        n.valor = valor;
        return n;
    }

    // Inserta un valor en el árbol siguiendo las reglas del BST:
    // - Valores menores van a la izquierda
    // - Valores mayores van a la derecha
    // - Si el valor ya existe, no se inserta (evita duplicados)
    static Nodo insertar(Nodo raiz, int valor) {
        if (raiz == null) return nuevoNodo(valor);
        if (valor < raiz.valor)
            raiz.izq = insertar(raiz.izq, valor);
        else if (valor > raiz.valor)  // Cambiado para evitar duplicados
            raiz.der = insertar(raiz.der, valor);
        return raiz;
    }

    // Recorrido PreOrden: Raíz -> Izquierda -> Derecha
    // Útil para copiar el árbol
    static void preOrden(Nodo n) {
        if (n != null) {
            System.out.print(n.valor + " ");
            preOrden(n.izq);
            preOrden(n.der);
        }
    }

    // Recorrido InOrden: Izquierda -> Raíz -> Derecha
    // En un BST imprime los valores ordenados de menor a mayor
    static void inOrden(Nodo n) {
        if (n != null) {
            inOrden(n.izq);
            System.out.print(n.valor + " ");
            inOrden(n.der);
        }
    }

    // Recorrido PostOrden: Izquierda -> Derecha -> Raíz
    // Útil para eliminar el árbol
    static void postOrden(Nodo n) {
        if (n != null) {
            postOrden(n.izq);
            postOrden(n.der);
            System.out.print(n.valor + " ");
        }
    }

    // Calcula la altura del árbol (número de niveles)
    // Un árbol vacío tiene altura 0
    static int altura(Nodo n) {
        if (n == null) return 0;
        return 1 + Math.max(altura(n.izq), altura(n.der));
    }

    // Verifica si el árbol está balanceado
    // Un árbol está balanceado si la diferencia de altura entre
    // sus subárboles izquierdo y derecho no es mayor a 1
    static boolean estaBalanceado(Nodo n) {
        if (n == null) return true;
        int altIzq = altura(n.izq);
        int altDer = altura(n.der);
        if (Math.abs(altIzq - altDer) > 1) return false;
        return estaBalanceado(n.izq) && estaBalanceado(n.der);
    }

    // Cuenta el total de nodos en el árbol
    static int contarNodos(Nodo n) {
        if (n == null) return 0;
        return 1 + contarNodos(n.izq) + contarNodos(n.der);
    }

    // Busca un valor en el árbol
    // Retorna true si lo encuentra, false si no
    static boolean buscar(Nodo raiz, int valor) {
        if (raiz == null) return false;
        if (raiz.valor == valor) return true;
        return valor < raiz.valor ? buscar(raiz.izq, valor) : buscar(raiz.der, valor);
    }

    // Encuentra el valor mínimo del árbol
    // En un BST siempre está en el nodo más a la izquierda
    static int encontrarMinimo(Nodo raiz) {
        if (raiz == null) return -1;
        while (raiz.izq != null) {
            raiz = raiz.izq;
        }
        return raiz.valor;
    }

    // Encuentra el valor máximo del árbol
    // En un BST siempre está en el nodo más a la derecha
    static int encontrarMaximo(Nodo raiz) {
        if (raiz == null) return -1;
        while (raiz.der != null) {
            raiz = raiz.der;
        }
        return raiz.valor;
    }

    public static void main(String[] args) {
        Random random = new Random();
        Nodo raiz = null;

        // Insertamos 10 valores aleatorios
        for (int i = 0; i < 10; i++) {
            int valor = random.nextInt(100);
            raiz = insertar(raiz, valor);
        }

        System.out.println("Recorrido en preorden:");
        preOrden(raiz);

        System.out.println("\nRecorrido en inorden:");
        inOrden(raiz);

        System.out.println("\nRecorrido en postorden:");
        postOrden(raiz);

        // Información adicional del árbol
        System.out.println("\n\nAltura del árbol: " + altura(raiz));
        System.out.println("Cantidad de nodos: " + contarNodos(raiz));
        System.out.println("Valor mínimo: " + encontrarMinimo(raiz));
        System.out.println("Valor máximo: " + encontrarMaximo(raiz));

        if (estaBalanceado(raiz))
            System.out.println("\nEl árbol está balanceado");
        else
            System.out.println("\nEl árbol no está balanceado");
    }
}