import java.util.*;

class Arista {
    String destino;
    int peso;
    
    public Arista(String destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }
}

class Nodo implements Comparable<Nodo> {
    String nombre;
    int distancia;
    
    public Nodo(String nombre, int distancia) {
        this.nombre = nombre;
        this.distancia = distancia;
    }
    
    @Override
    public int compareTo(Nodo otro) {
        return Integer.compare(this.distancia, otro.distancia);
    }
}

public class Dijkstra {
    private Map<String, List<Arista>> grafo;
    private Map<String, String> predecesoresGlobales;
    
    public Dijkstra() {
        this.grafo = new HashMap<>();
    }
    
    /**
     * Agrega una arista al grafo
     */
    public void agregarArista(String origen, String destino, int peso) {
        grafo.putIfAbsent(origen, new ArrayList<>());
        grafo.putIfAbsent(destino, new ArrayList<>());
        grafo.get(origen).add(new Arista(destino, peso));
    }
    
    /**
     * Implementación del algoritmo de Dijkstra
     * Retorna un mapa con las distancias más cortas desde el nodo inicio
     */
    public Map<String, Integer> calcularDistancias(String inicio) {
        // Inicializar distancias
        Map<String, Integer> distancias = new HashMap<>();
        Map<String, String> predecesores = new HashMap<>();
        Set<String> visitados = new HashSet<>();
        
        // Cola de prioridad (min-heap)
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>();
        
        // Inicializar todas las distancias como infinito
        for (String vertice : grafo.keySet()) {
            distancias.put(vertice, Integer.MAX_VALUE);
            predecesores.put(vertice, null);
        }
        
        // Distancia al nodo inicial es 0
        distancias.put(inicio, 0);
        colaPrioridad.offer(new Nodo(inicio, 0));
        
        while (!colaPrioridad.isEmpty()) {
            Nodo nodoActual = colaPrioridad.poll();
            String verticeActual = nodoActual.nombre;
            
            // Si ya visitamos este nodo, continuar
            if (visitados.contains(verticeActual)) {
                continue;
            }
            
            visitados.add(verticeActual);
            
            // Explorar vecinos
            List<Arista> vecinos = grafo.get(verticeActual);
            if (vecinos != null) {
                for (Arista arista : vecinos) {
                    if (!visitados.contains(arista.destino)) {
                        int nuevaDistancia = distancias.get(verticeActual) + arista.peso;
                        
                        // Si encontramos un camino más corto
                        if (nuevaDistancia < distancias.get(arista.destino)) {
                            distancias.put(arista.destino, nuevaDistancia);
                            predecesores.put(arista.destino, verticeActual);
                            colaPrioridad.offer(new Nodo(arista.destino, nuevaDistancia));
                        }
                    }
                }
            }
        }
        
        // Guardar predecesores para reconstruir caminos
        this.predecesoresGlobales = predecesores;
        
        return distancias;
    }
    
    /**
     * Reconstruye el camino más corto desde inicio hasta destino
     */
    public List<String> obtenerCamino(String inicio, String destino) {
        List<String> camino = new ArrayList<>();
        String nodoActual = destino;
        
        while (nodoActual != null) {
            camino.add(0, nodoActual); // Agregar al inicio
            nodoActual = predecesoresGlobales.get(nodoActual);
        }
        
        // Verificar si existe un camino válido
        if (camino.isEmpty() || !camino.get(0).equals(inicio)) {
            return null;
        }
        
        return camino;
    }
    
    /**
     * Imprime las distancias y caminos más cortos
     */
    public void imprimirResultados(String inicio, Map<String, Integer> distancias) {
        System.out.println("Distancias más cortas desde " + inicio + ":");
        System.out.println("-".repeat(50));
        
        List<String> vertices = new ArrayList<>(distancias.keySet());
        Collections.sort(vertices);
        
        for (String vertice : vertices) {
            int distancia = distancias.get(vertice);
            String distStr = (distancia == Integer.MAX_VALUE) ? "∞" : String.valueOf(distancia);
            System.out.println(inicio + " -> " + vertice + ": " + distStr);
        }
        
        System.out.println("\nCaminos más cortos:");
        System.out.println("-".repeat(50));
        
        for (String vertice : vertices) {
            if (!vertice.equals(inicio) && distancias.get(vertice) != Integer.MAX_VALUE) {
                List<String> camino = obtenerCamino(inicio, vertice);
                if (camino != null) {
                    String caminoStr = String.join(" -> ", camino);
                    System.out.println(caminoStr + " (distancia: " + distancias.get(vertice) + ")");
                }
            }
        }
    }
    
    /**
     * Método principal - Ejemplo de uso
     */
    public static void main(String[] args) {
        // Crear el grafo
        Dijkstra grafo = new Dijkstra();
        
        // Agregar aristas (origen, destino, peso)
        grafo.agregarArista("A", "B", 4);
        grafo.agregarArista("A", "C", 2);
        grafo.agregarArista("B", "C", 1);
        grafo.agregarArista("B", "D", 5);
        grafo.agregarArista("C", "D", 8);
        grafo.agregarArista("C", "E", 10);
        grafo.agregarArista("D", "E", 2);
        grafo.agregarArista("D", "F", 6);
        grafo.agregarArista("E", "F", 3);
        
        // Ejecutar Dijkstra desde el nodo 'A'
        String nodoInicio = "A";
        Map<String, Integer> distancias = grafo.calcularDistancias(nodoInicio);
        
        // Imprimir resultados
        grafo.imprimirResultados(nodoInicio, distancias);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Ejemplo de camino específico:");
        System.out.println("=".repeat(50));
        
        String destino = "F";
        List<String> caminoEspecifico = grafo.obtenerCamino(nodoInicio, destino);
        if (caminoEspecifico != null) {
            System.out.println("Camino de " + nodoInicio + " a " + destino + ":");
            System.out.println(String.join(" -> ", caminoEspecifico));
            System.out.println("Distancia total: " + distancias.get(destino));
        }
    }
}