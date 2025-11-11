import java.util.*;

public class Grafo {
    private Map<String, Nodo> nodos;

    public Grafo() {
        this.nodos = new HashMap<>();
    }

    public void agregarNodo(String nombre) {
        nodos.put(nombre, new Nodo(nombre));
    }

    public void agregarArista(String origen, String destino, int peso) {
        Nodo nodoOrigen = nodos.get(origen);
        Nodo nodoDestino = nodos.get(destino);
        if (nodoOrigen != null && nodoDestino != null) {
            nodoOrigen.agregarArista(nodoDestino, peso);
        }
    }

    // Algoritmo de Dijkstra
    public void dijkstra(String inicio) {
        if (!nodos.containsKey(inicio)) {
            System.out.println("El nodo de inicio no existe en el grafo.");
            return;
        }

        Nodo nodoInicio = nodos.get(inicio);
        nodoInicio.setDistancia(0);

        PriorityQueue<Nodo> cola = new PriorityQueue<>();
        cola.add(nodoInicio);

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();

            for (Arista arista : actual.getAdyacentes()) {
                Nodo vecino = arista.getDestino();
                int nuevaDistancia = actual.getDistancia() + arista.getPeso();

                if (nuevaDistancia < vecino.getDistancia()) {
                    vecino.setDistancia(nuevaDistancia);
                    vecino.setPrevio(actual);
                    cola.add(vecino);
                }
            }
        }
    }

    public void mostrarDistancias(String inicio) {
        System.out.println("Distancias mínimas desde el nodo " + inicio + ":");
        for (Nodo nodo : nodos.values()) {
            System.out.print("→ " + nodo.getNombre() + ": ");
            if (nodo.getDistancia() == Integer.MAX_VALUE) {
                System.out.println("Inalcanzable");
            } else {
                System.out.println(nodo.getDistancia());
            }
        }
    }

    public void mostrarCamino(String destino) {
        Nodo nodoDestino = nodos.get(destino);
        if (nodoDestino == null || nodoDestino.getDistancia() == Integer.MAX_VALUE) {
            System.out.println("No hay camino hacia " + destino);
            return;
        }

        List<String> camino = new ArrayList<>();
        for (Nodo nodo = nodoDestino; nodo != null; nodo = nodo.getPrevio()) {
            camino.add(nodo.getNombre());
        }
        Collections.reverse(camino);
        System.out.println("Camino más corto hasta " + destino + ": " + camino);
    }
}
