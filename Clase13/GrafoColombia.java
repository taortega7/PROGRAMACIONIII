import java.util.*;

/**
 * Implementación del Algoritmo de Dijkstra con Listas de Adyacencia
 * Estructuras de datos: ArrayList, LinkedList, List
 * 
 * Grafo: Mapa de Colombia (Cali → Cartagena)
 * Método de ordenamiento: Burbuja (Bubble Sort)
 */
public class GrafoColombia {

    // ==================== CLASES INTERNAS ====================
    
    /**
     * Representa una arista del grafo (conexión entre ciudades)
     */
    static class Arista {
        String destino;
        int peso;  // distancia en km

        Arista(String destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }

        @Override
        public String toString() {
            return destino + " (" + peso + " km)";
        }
    }

    /**
     * Nodo para la cola de prioridad en Dijkstra
     */
    static class Nodo implements Comparable<Nodo> {
        String nombre;
        int distancia;

        Nodo(String nombre, int distancia) {
            this.nombre = nombre;
            this.distancia = distancia;
        }

        @Override
        public int compareTo(Nodo otro) {
            return Integer.compare(this.distancia, otro.distancia);
        }
    }

    /**
     * Representa un recorrido completo con su ruta y distancia total
     */
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

    // ==================== ATRIBUTOS DEL GRAFO ====================
    
    // REQUISITO: Lista de Adyacencia con LinkedList
    private Map<String, LinkedList<Arista>> listaAdyacencia;
    
    // Para reconstruir caminos
    private Map<String, String> predecesor;
    
    // REQUISITO: ArrayList para almacenar todos los recorridos
    private List<Recorrido> todosLosRecorridos;

    // Constructor
    public GrafoColombia() {
        this.listaAdyacencia = new HashMap<>();
        this.predecesor = new HashMap<>();
        this.todosLosRecorridos = new ArrayList<>();
    }

    // ==================== CONSTRUCCIÓN DEL GRAFO ====================
    
    /**
     * Agrega una ciudad al grafo
     */
    public void agregarCiudad(String nombre) {
        // Usar LinkedList como estructura de la lista de adyacencia
        listaAdyacencia.putIfAbsent(nombre, new LinkedList<>());
    }

    /**
     * Agrega una ruta bidireccional entre dos ciudades
     */
    public void agregarRuta(String origen, String destino, int distanciaKm) {
        listaAdyacencia.get(origen).add(new Arista(destino, distanciaKm));
        listaAdyacencia.get(destino).add(new Arista(origen, distanciaKm));
    }

    // ==================== ALGORITMO DE DIJKSTRA ====================
    
    /**
     * Implementa el algoritmo de Dijkstra para encontrar caminos más cortos
     * @param inicio Ciudad de partida
     * @param mostrarPasos Si debe mostrar el proceso paso a paso
     * @return Mapa con las distancias mínimas desde el inicio a cada ciudad
     */
    public Map<String, Integer> dijkstra(String inicio, boolean mostrarPasos) {
        // Estructuras necesarias
        Map<String, Integer> distancias = new HashMap<>();
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>();
        Set<String> visitados = new HashSet<>();

        if (mostrarPasos) {
            System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
            System.out.println("║         🚀 ALGORITMO DE DIJKSTRA - PASO A PASO           ║");
            System.out.println("╚═══════════════════════════════════════════════════════════╝");
            System.out.println("\n🎯 Nodo inicial: " + inicio);
        }

        // Limpiar predecesores
        predecesor.clear();

        // PASO 1: Inicializar todas las distancias en infinito
        for (String ciudad : listaAdyacencia.keySet()) {
            distancias.put(ciudad, Integer.MAX_VALUE);
        }

        // PASO 2: Distancia del nodo inicial es 0
        distancias.put(inicio, 0);
        colaPrioridad.add(new Nodo(inicio, 0));

        int paso = 1;

        // PASO 3: Proceso principal de Dijkstra
        while (!colaPrioridad.isEmpty()) {
            // Extraer el nodo con menor distancia
            Nodo actual = colaPrioridad.poll();

            // Si ya fue visitado, continuar
            if (visitados.contains(actual.nombre)) continue;
            
            // Marcar como visitado
            visitados.add(actual.nombre);

            if (mostrarPasos) {
                System.out.println("\n═══════════════════════════════════════════════════════════");
                System.out.println("📍 PASO " + paso++ + " - Visitando: " + actual.nombre);
                System.out.println("📏 Distancia acumulada: " + actual.distancia + " km");
                System.out.println("───────────────────────────────────────────────────────────");
            }

            // Explorar todos los vecinos (usando LinkedList de adyacencia)
            LinkedList<Arista> vecinos = listaAdyacencia.get(actual.nombre);
            
            if (mostrarPasos) {
                System.out.println("🔍 Explorando " + vecinos.size() + " vecino(s):");
            }

            for (Arista arista : vecinos) {
                // Calcular nueva distancia potencial
                int nuevaDistancia = distancias.get(actual.nombre) + arista.peso;

                if (mostrarPasos) {
                    System.out.print("   • " + arista.destino + " (+" + arista.peso + " km) → ");
                }

                // Si encontramos un camino más corto
                if (nuevaDistancia < distancias.get(arista.destino)) {
                    int distanciaAnterior = distancias.get(arista.destino);
                    
                    // Actualizar distancia
                    distancias.put(arista.destino, nuevaDistancia);
                    
                    // Guardar predecesor para reconstruir camino
                    predecesor.put(arista.destino, actual.nombre);
                    
                    // Agregar a la cola de prioridad
                    colaPrioridad.add(new Nodo(arista.destino, nuevaDistancia));

                    if (mostrarPasos) {
                        if (distanciaAnterior == Integer.MAX_VALUE) {
                            System.out.println("✅ NUEVO: " + nuevaDistancia + " km");
                        } else {
                            System.out.println("✅ MEJORADO: " + distanciaAnterior + " km → " + nuevaDistancia + " km");
                        }
                    }
                } else {
                    if (mostrarPasos) {
                        System.out.println("⏭️  Sin mejora (mantiene: " + distancias.get(arista.destino) + " km)");
                    }
                }
            }
        }

        return distancias;
    }

    // ==================== RECONSTRUCCIÓN DE CAMINOS ====================
    
    /**
     * Reconstruye el camino desde inicio hasta fin usando los predecesores
     */
    public List<String> reconstruirCamino(String inicio, String fin) {
        LinkedList<String> camino = new LinkedList<>();
        String actual = fin;

        // Retroceder desde el destino hasta el origen
        while (actual != null) {
            camino.addFirst(actual);
            actual = predecesor.get(actual);
        }

        // Verificar que el camino sea válido
        if (!camino.isEmpty() && camino.getFirst().equals(inicio)) {
            return camino;
        }
        
        return new ArrayList<>(); // Camino no encontrado
    }

    // ==================== GENERACIÓN DE RECORRIDOS ====================
    
    /**
     * Genera todos los recorridos válidos desde origen hasta destino
     */
    public void generarRecorridos(String origen, String destino, Map<String, Integer> distancias) {
        todosLosRecorridos.clear();

        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║         🛣️  GENERANDO RECORRIDOS ALTERNATIVOS            ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        // 1. RECORRIDO ÓPTIMO (resultado directo de Dijkstra)
        List<String> caminoOptimo = reconstruirCamino(origen, destino);
        if (!caminoOptimo.isEmpty()) {
            int distOptima = distancias.get(destino);
            todosLosRecorridos.add(new Recorrido(caminoOptimo, distOptima));
            System.out.println("✅ Ruta óptima: " + String.join(" → ", caminoOptimo) + 
                             " (" + distOptima + " km)");
        }

        // 2. RECORRIDOS ALTERNATIVOS (pasando por cada ciudad intermedia)
        List<String> todasCiudades = new ArrayList<>(listaAdyacencia.keySet());
        
        for (String ciudadIntermedia : todasCiudades) {
            // Saltar si es origen o destino o si ya está en la ruta óptima
            if (ciudadIntermedia.equals(origen) || ciudadIntermedia.equals(destino)) {
                continue;
            }
            
            // Verificar que la ciudad intermedia NO esté en el camino óptimo
            if (caminoOptimo.contains(ciudadIntermedia)) {
                continue; // Ya está en la ruta óptima, no crear duplicado
            }

            // Calcular: origen → ciudadIntermedia → destino
            // Guardamos el predecesor original
            Map<String, String> predecesorOriginal = new HashMap<>(predecesor);
            
            // Ejecutar Dijkstra desde la ciudad intermedia
            Map<String, Integer> distanciasDesdeIntermedia = dijkstra(ciudadIntermedia, false);
            
            // Verificar que existan ambos caminos
            if (distancias.get(ciudadIntermedia) != Integer.MAX_VALUE && 
                distanciasDesdeIntermedia.get(destino) != Integer.MAX_VALUE) {
                
                // Calcular distancia total
                int distanciaTotal = distancias.get(ciudadIntermedia) + 
                                   distanciasDesdeIntermedia.get(destino);
                
                // Reconstruir primera parte: origen → intermedia
                predecesor = predecesorOriginal;
                List<String> parte1 = reconstruirCamino(origen, ciudadIntermedia);
                
                // Reconstruir segunda parte: intermedia → destino
                dijkstra(ciudadIntermedia, false);
                List<String> parte2 = reconstruirCamino(ciudadIntermedia, destino);
                
                // Restaurar predecesor original para el siguiente ciclo
                predecesor = predecesorOriginal;
                
                // Validar que ambas partes sean válidas y partan del origen
                if (!parte1.isEmpty() && !parte2.isEmpty() && 
                    parte1.get(0).equals(origen) && parte2.get(parte2.size() - 1).equals(destino)) {
                    
                    // Combinar ambas partes (sin duplicar ciudad intermedia)
                    List<String> rutaCompleta = new ArrayList<>(parte1);
                    for (int i = 1; i < parte2.size(); i++) {
                        rutaCompleta.add(parte2.get(i));
                    }
                    
                    // Verificar que no sea duplicado
                    boolean esDuplicado = false;
                    for (Recorrido r : todosLosRecorridos) {
                        if (r.ruta.equals(rutaCompleta)) {
                            esDuplicado = true;
                            break;
                        }
                    }
                    
                    if (!esDuplicado && rutaCompleta.size() >= 3) {
                        todosLosRecorridos.add(new Recorrido(rutaCompleta, distanciaTotal));
                        System.out.println("✅ Ruta vía " + ciudadIntermedia + ": " + 
                                         String.join(" → ", rutaCompleta) + 
                                         " (" + distanciaTotal + " km)");
                    }
                }
            }
        }

        System.out.println("\n📊 Total de recorridos válidos: " + todosLosRecorridos.size() + "\n");
    }

    // ==================== ORDENAMIENTO BURBUJA ====================
    
    /**
     * REQUISITO: Método de ordenamiento Burbuja para ordenar recorridos
     * Ordena de menor a mayor distancia
     */
    public void ordenarRecorridosBurbuja() {
        int n = todosLosRecorridos.size();
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║      🔄 MÉTODO DE ORDENAMIENTO: BURBUJA (BUBBLE SORT)    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        
        System.out.println("📋 Ordenando " + n + " recorridos por distancia (menor a mayor)...\n");

        // Algoritmo de ordenamiento burbuja
        for (int i = 0; i < n - 1; i++) {
            boolean huboIntercambio = false;
            
            for (int j = 0; j < n - i - 1; j++) {
                // Comparar distancias
                if (todosLosRecorridos.get(j).distanciaTotal > 
                    todosLosRecorridos.get(j + 1).distanciaTotal) {
                    
                    // Intercambiar posiciones
                    Recorrido temp = todosLosRecorridos.get(j);
                    todosLosRecorridos.set(j, todosLosRecorridos.get(j + 1));
                    todosLosRecorridos.set(j + 1, temp);
                    huboIntercambio = true;
                }
            }
            
            // Optimización: si no hubo intercambios, ya está ordenado
            if (!huboIntercambio) {
                System.out.println("✅ Lista ya ordenada en iteración " + (i + 1));
                break;
            }
        }
        
        System.out.println("✅ Ordenamiento completado exitosamente!\n");
    }

    // ==================== VISUALIZACIÓN DE RESULTADOS ====================
    
    /**
     * Muestra la estructura del grafo (lista de adyacencia)
     */
    public void mostrarGrafo() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║           📊 GRAFO DE COLOMBIA (5 CIUDADES)              ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        System.out.println("🗺️  Lista de Adyacencia (usando LinkedList):\n");

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

    /**
     * Muestra la tabla de distancias mínimas desde el origen
     */
    public void mostrarTablaDistancias(String origen, Map<String, Integer> distancias) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║           📏 TABLA DE DISTANCIAS MÍNIMAS                 ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        // Ordenar por distancia
        List<Map.Entry<String, Integer>> listaOrdenada = new ArrayList<>(distancias.entrySet());
        listaOrdenada.sort(Map.Entry.comparingByValue());

        for (Map.Entry<String, Integer> entry : listaOrdenada) {
            String ciudad = entry.getKey();
            int distancia = entry.getValue();
            List<String> camino = reconstruirCamino(origen, ciudad);

            if (ciudad.equals(origen)) {
                System.out.printf("   %-15s → %5d km  (🎯 punto de partida)\n", ciudad, distancia);
            } else if (distancia == Integer.MAX_VALUE) {
                System.out.printf("   %-15s → ∞ (no alcanzable)\n", ciudad);
            } else {
                System.out.printf("   %-15s → %5d km  Ruta: %s\n", 
                    ciudad, distancia, String.join(" → ", camino));
            }
        }
    }

    /**
     * Muestra todos los recorridos ordenados
     */
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

        // Resumen del menor recorrido
        if (!todosLosRecorridos.isEmpty()) {
            Recorrido mejor = todosLosRecorridos.get(0);
            System.out.println("\n" + "═".repeat(61));
            System.out.println("✅ MENOR RECORRIDO ENCONTRADO:");
            System.out.println("   Ruta: " + String.join(" → ", mejor.ruta));
            System.out.println("   Distancia total: " + mejor.distanciaTotal + " km");
            System.out.println("   Número de ciudades: " + mejor.ruta.size());
            System.out.println("═".repeat(61));
        }
    }

    // ==================== PROGRAMA PRINCIPAL ====================
    
    public static void main(String[] args) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                           ║");
        System.out.println("║    ALGORITMO DE DIJKSTRA - MAPA DE COLOMBIA              ║");
        System.out.println("║    Origen: Cali  →  Destino: Cartagena                   ║");
        System.out.println("║                                                           ║");
        System.out.println("║    Estructuras: ArrayList, LinkedList, List               ║");
        System.out.println("║    Ordenamiento: Burbuja (Bubble Sort)                    ║");
        System.out.println("║                                                           ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");

        // Crear instancia del grafo
        GrafoColombia grafo = new GrafoColombia();

        // PASO 1: Agregar las 5 ciudades principales
        grafo.agregarCiudad("Cali");
        grafo.agregarCiudad("Medellín");
        grafo.agregarCiudad("Bogotá");
        grafo.agregarCiudad("Barranquilla");
        grafo.agregarCiudad("Cartagena");

        // PASO 2: Agregar las rutas (distancias en km)
        grafo.agregarRuta("Cali", "Medellín", 416);
        grafo.agregarRuta("Cali", "Bogotá", 460);
        grafo.agregarRuta("Medellín", "Bogotá", 410);
        grafo.agregarRuta("Medellín", "Cartagena", 642);
        grafo.agregarRuta("Bogotá", "Barranquilla", 991);
        grafo.agregarRuta("Barranquilla", "Cartagena", 120);

        // PASO 3: Mostrar estructura del grafo
        grafo.mostrarGrafo();

        // PASO 4: Ejecutar algoritmo de Dijkstra
        String origen = "Cali";
        String destino = "Cartagena";
        Map<String, Integer> distancias = grafo.dijkstra(origen, true);

        // PASO 5: Mostrar tabla de distancias
        grafo.mostrarTablaDistancias(origen, distancias);

        // PASO 6: Generar todos los recorridos posibles
        grafo.generarRecorridos(origen, destino, distancias);

        // PASO 7: Ordenar recorridos usando método Burbuja
        grafo.ordenarRecorridosBurbuja();

        // PASO 8: Mostrar recorridos ordenados y resultado final
        grafo.mostrarRecorridos();

        System.out.println("\n✅ Programa finalizado exitosamente\n");
    }
}