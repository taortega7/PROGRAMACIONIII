public class MainDijkstra {
    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        // Agregar nodos
        grafo.agregarNodo("A");
        grafo.agregarNodo("B");
        grafo.agregarNodo("C");
        grafo.agregarNodo("D");
        grafo.agregarNodo("E");

        // Agregar aristas (grafo dirigido)
        grafo.agregarArista("A", "B", 6);
        grafo.agregarArista("A", "D", 1);
        grafo.agregarArista("D", "B", 2);
        grafo.agregarArista("D", "E", 1);
        grafo.agregarArista("B", "E", 2);
        grafo.agregarArista("B", "C", 5);
        grafo.agregarArista("E", "C", 5);

        // Ejecutar Dijkstra desde A
        grafo.dijkstra("A");

        // Mostrar resultados
        grafo.mostrarDistancias("A");
        grafo.mostrarCamino("C");
    }
}
