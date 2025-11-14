import java.util.*;

/**
 * Implementación del Algoritmo de Dijkstra con Listas de Adyacencia
 * Usando ArrayList, LinkedList y List como estructuras de datos
 * 
 * Grafo: Mapa de Colombia (Cali → Cartagena)
 * Método de ordenamiento: Burbuja (Bubble Sort)
 */
public class GrafoColombia {

    // ------------------- CLASE ARISTA -------------------
    static class Arista {
        String destino;
        int peso;

        Arista(String destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }

        @Override
        public String toString() {
            return destino + " (" + peso + " km)";
        }
    }

    // ------------------ CLASE NODO PARA PRIORITY QUEUE ------------------
    static class Nodo implements Comparable<Nodo> {
        String nombre;
        int distancia;

        Nodo(String nombre, int distancia) {
            this.nombre = nombre;
            this.distancia = distancia;
        }

        @Override
        public int compareTo(Nodo o) {
            return Integer.compare(this.distancia, o.distancia);
        }
    }

    // ------------------ CLASE RECORRIDO ------------------
    static class Recorrido {
        List<String> ruta;
        int distanciaTotal;

        Recorrido(List<String> ruta, int distanciaTotal) {
            this.ruta = new ArrayList<>(ruta);
            this.distanciaTotal = distanciaTotal;
        }

        @Override
        public String toString() {
            return String.join(" → ", ruta) + " | " + distanciaTotal + " km";
        }
    }

    // ============ ATRIBUTOS DEL GRAFO ============
    // REQUISITO: Listas de Adyacencia con LinkedList
    private Map<String, LinkedList<Arista>> listaAdyacencia;
    private Map<String, String> predecesor;
    private List<Recorrido> todosLosRecorridos; // ArrayList para almacenar recorridos

    public GrafoColombia() {
        this.listaAdyacencia = new HashMap<>();
        this.predecesor = new HashMap<>();
        this.todosLosRecorridos = new ArrayList<>();
    }

    // ============ MÉTODOS DEL GRAFO ============
    
    public void agregarCiudad(String nombre) {
        // Usar LinkedList como lista de adyacencia
        listaAdyacencia.putIfAbsent(nombre, new LinkedList<>());
    }

    public void agregarRuta(String origen, String destino, int distanciaKm) {
        // Grafo NO dirigido (bidireccional)
        listaAdyacencia.get(origen).add(new Arista(destino, distanciaKm));
        listaAdyacencia.get(destino).add(new Arista(origen, distanciaKm));
    }

    // ============ ALGORITMO DE DIJKSTRA ============
    public Map<String, Integer> dijkstra(String inicio) {
        Map<String, Integer> distancias = new HashMap<>();
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>();
        Set<String> visitados = new HashSet<>();

        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         🚀 ALGORITMO DE DIJKSTRA - PASO A PASO           ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println("\n🎯 Nodo inicial: " + inicio);
        System.out.println("📊 Inicializando distancias...\n");

        // Inicializar distancias en infinito
        for (String ciudad : listaAdyacencia.keySet()) {
            distancias.put(ciudad, Integer.MAX_VALUE);
        }

        // Distancia del nodo inicial es 0
        distancias.put(inicio, 0);
        colaPrioridad.add(new Nodo(inicio, 0));

        int paso = 1;

        // Proceso principal de Dijkstra
        while (!colaPrioridad.isEmpty()) {
            Nodo actual = colaPrioridad.poll();

            // Si ya fue visitado, continuar
            if (visitados.contains(actual.nombre)) continue;
            
            visitados.add(actual.nombre);

            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("📍 PASO " + paso++ + " - Visitando: " + actual.nombre);
            System.out.println("📏 Distancia acumulada desde " + inicio + ": " + actual.distancia + " km");
            System.out.println("───────────────────────────────────────────────────────────");

            // Explorar vecinos (usando LinkedList de adyacencia)
            LinkedList<Arista> vecinos = listaAdyacencia.get(actual.nombre);
            System.out.println("🔍 Explorando " + vecinos.size() + " vecino(s):\n");

            for (Arista arista : vecinos) {
                int nuevaDistancia = distancias.get(actual.nombre) + arista.peso;

                System.out.print("   • " + arista.destino + " (+" + arista.peso + " km) → ");

                if (nuevaDistancia < distancias.get(arista.destino)) {
                    int distanciaAnterior = distancias.get(arista.destino);
                    distancias.put(arista.destino, nuevaDistancia);
                    predecesor.put(arista.destino, actual.nombre);
                    colaPrioridad.add(new Nodo(arista.destino, nuevaDistancia));

                    if (distanciaAnterior == Integer.MAX_VALUE) {
                        System.out.println("✅ NUEVO: " + nuevaDistancia + " km");
                    } else {
                        System.out.println("✅ MEJORADO: " + distanciaAnterior + " km → " + nuevaDistancia + " km");
                    }
                } else {
                    System.out.println("⏭️  Sin mejora (actual: " + distancias.get(arista.destino) + " km)");
                }
            }
            System.out.println();
        }

        return distancias;
    }

    // ============ RECONSTRUIR CAMINO ============
    public List<String> reconstruirCamino(String inicio, String fin) {
        LinkedList<String> camino = new LinkedList<>();
        String actual = fin;

        while (actual != null) {
            camino.addFirst(actual);
            actual = predecesor.get(actual);
        }

        if (!camino.isEmpty() && camino.getFirst().equals(inicio)) {
            return camino;
        }
        return new ArrayList<>(); // Retornar lista vacía si no hay camino
    }

    // ============ CALCULAR DISTANCIA DE UN CAMINO ============
    public int calcularDistanciaCamino(List<String> camino) {
        int distanciaTotal = 0;
        for (int i = 0; i < camino.size() - 1; i++) {
            String origen = camino.get(i);
            String destino = camino.get(i + 1);

            for (Arista arista : listaAdyacencia.get(origen)) {
                if (arista.destino.equals(destino)) {
                    distanciaTotal += arista.peso;
                    break;
                }
            }
        }
        return distanciaTotal;
    }

    // ============ GENERAR TODOS LOS RECORRIDOS POSIBLES ============
    public void generarRecorridos(String inicio, String fin, Map<String, Integer> distancias) {
        todosLosRecorridos.clear();

        // Recorrido óptimo encontrado por Dijkstra
        List<String> caminoOptimo = reconstruirCamino(inicio, fin);
        if (!caminoOptimo.isEmpty()) {
            int distOptima = distancias.get(fin);
            todosLosRecorridos.add(new Recorrido(caminoOptimo, distOptima));
        }

        // Generar recorridos alternativos visitando otras ciudades
        List<String> todasCiudades = new ArrayList<>(listaAdyacencia.keySet());
        
        for (String ciudadIntermedia : todasCiudades) {
            if (!ciudadIntermedia.equals(inicio) && !ciudadIntermedia.equals(fin)) {
                List<String> caminoAlternativo = new ArrayList<>();
                caminoAlternativo.add(inicio);
                caminoAlternativo.add(ciudadIntermedia);
                caminoAlternativo.add(fin);
                
                int distancia = calcularDistanciaCamino(caminoAlternativo);
                if (distancia > 0) {
                    todosLosRecorridos.add(new Recorrido(caminoAlternativo, distancia));
                }
            }
        }

        // Recorrido pasando por 2 ciudades intermedias
        if (todasCiudades.size() >= 4) {
            List<String> caminoLargo = new ArrayList<>();
            caminoLargo.add(inicio);
            for (String ciudad : todasCiudades) {
                if (!ciudad.equals(inicio) && !ciudad.equals(fin)) {
                    caminoLargo.add(ciudad);
                }
            }
            caminoLargo.add(fin);
            
            int distancia = calcularDistanciaCamino(caminoLargo);
            if (distancia > 0) {
                todosLosRecorridos.add(new Recorrido(caminoLargo, distancia));
            }
        }
    }

    // ============ MÉTODO DE ORDENAMIENTO: BURBUJA (BUBBLE SORT) ============
    public void ordenarRecorridos() {
        int n = todosLosRecorridos.size();
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║      🔄 MÉTODO DE ORDENAMIENTO: BURBUJA (BUBBLE SORT)    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        
        System.out.println("📋 Ordenando " + n + " recorridos por distancia...\n");

        // Algoritmo de Burbuja
        for (int i = 0; i < n - 1; i++) {
            boolean huboIntercambio = false;
            
            for (int j = 0; j < n - i - 1; j++) {
                if (todosLosRecorridos.get(j).distanciaTotal > 
                    todosLosRecorridos.get(j + 1).distanciaTotal) {
                    
                    // Intercambiar
                    Recorrido temp = todosLosRecorridos.get(j);
                    todosLosRecorridos.set(j, todosLosRecorridos.get(j + 1));
                    todosLosRecorridos.set(j + 1, temp);
                    huboIntercambio = true;
                }
            }
            
            if (!huboIntercambio) {
                System.out.println("✅ Lista ya ordenada en iteración " + (i + 1));
                break;
            }
        }
        
        System.out.println("✅ Ordenamiento completado exitosamente!\n");
    }

    // ============ MOSTRAR RESULTADOS ============
    
    public void mostrarGrafo() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║           📊 GRAFO DE COLOMBIA (5 CIUDADES)              ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        System.out.println("🗺️  Lista de Adyacencia (LinkedList):\n");

        for (Map.Entry<String, LinkedList<Arista>> entry : listaAdyacencia.entrySet()) {
            System.out.printf("   🏙️  %-15s → ", entry.getKey());
            LinkedList<Arista> aristas = entry.getValue();
            for (int i = 0; i < aristas.size(); i++) {
                System.out.print(aristas.get(i));
                if (i < aristas.size() - 1) System.out.print(", ");
            }
            System.out.println();
        }
    }

    public void mostrarTablaDistancias(String inicio, Map<String, Integer> distancias) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║           📏 TABLA DE DISTANCIAS FINALES                 ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        List<Map.Entry<String, Integer>> listaOrdenada = new ArrayList<>(distancias.entrySet());
        listaOrdenada.sort(Map.Entry.comparingByValue());

        for (Map.Entry<String, Integer> entry : listaOrdenada) {
            String ciudad = entry.getKey();
            int distancia = entry.getValue();
            List<String> camino = reconstruirCamino(inicio, ciudad);

            if (ciudad.equals(inicio)) {
                System.out.printf("   %-15s → %5d km  (🎯 inicio)\n", ciudad, distancia);
            } else {
                System.out.printf("   %-15s → %5d km  Ruta: %s\n", 
                    ciudad, distancia, String.join(" → ", camino));
            }
        }
    }

    public void mostrarRecorridos() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║        🏆 RECORRIDOS ORDENADOS (MENOR A MAYOR)           ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        for (int i = 0; i < todosLosRecorridos.size(); i++) {
            Recorrido r = todosLosRecorridos.get(i);
            if (i == 0) {
                System.out.println("   🥇 #" + (i + 1) + " (ÓPTIMO): " + r);
            } else {
                System.out.println("   📍 #" + (i + 1) + ": " + r);
            }
        }

        if (!todosLosRecorridos.isEmpty()) {
            Recorrido mejor = todosLosRecorridos.get(0);
            System.out.println("\n" + "═".repeat(61));
            System.out.println("✅ MENOR RECORRIDO ENCONTRADO:");
            System.out.println("   Ruta: " + String.join(" → ", mejor.ruta));
            System.out.println("   Distancia: " + mejor.distanciaTotal + " km");
            System.out.println("   Ciudades visitadas: " + mejor.ruta.size());
            System.out.println("═".repeat(61));
        }
    }

    // ============ MAIN ============
    public static void main(String[] args) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                           ║");
        System.out.println("║    ALGORITMO DE DIJKSTRA - COLOMBIA (Cali → Cartagena)   ║");
        System.out.println("║    Implementación con Listas de Adyacencia                ║");
        System.out.println("║    (ArrayList, LinkedList, List)                          ║");
        System.out.println("║                                                           ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");

        // Crear grafo
        GrafoColombia grafo = new GrafoColombia();

        // Agregar 5 ciudades principales de Colombia
        grafo.agregarCiudad("Cali");
        grafo.agregarCiudad("Medellín");
        grafo.agregarCiudad("Bogotá");
        grafo.agregarCiudad("Barranquilla");
        grafo.agregarCiudad("Cartagena");

        // Agregar rutas (distancias reales aproximadas en km)
        grafo.agregarRuta("Cali", "Medellín", 416);
        grafo.agregarRuta("Cali", "Bogotá", 460);
        grafo.agregarRuta("Medellín", "Bogotá", 410);
        grafo.agregarRuta("Medellín", "Cartagena", 642);
        grafo.agregarRuta("Bogotá", "Barranquilla", 991);
        grafo.agregarRuta("Barranquilla", "Cartagena", 120);

        // Mostrar grafo
        grafo.mostrarGrafo();

        // Ejecutar Dijkstra desde Cali
        String origen = "Cali";
        String destino = "Cartagena";
        Map<String, Integer> distancias = grafo.dijkstra(origen);

        // Mostrar tabla de distancias
        grafo.mostrarTablaDistancias(origen, distancias);

        // Generar todos los recorridos posibles
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         🛣️  GENERANDO RECORRIDOS ALTERNATIVOS            ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        
        grafo.generarRecorridos(origen, destino, distancias);
        System.out.println("✅ Se generaron " + grafo.todosLosRecorridos.size() + " recorridos\n");

        // Ordenar recorridos (Método Burbuja)
        grafo.ordenarRecorridos();

        // Mostrar recorridos ordenados
        grafo.mostrarRecorridos();

        System.out.println("\n✅ Programa finalizado exitosamente\n");
    }
}