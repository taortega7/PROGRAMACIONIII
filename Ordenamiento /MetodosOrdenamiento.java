import java.util.Arrays;
import java.util.Random;

public class MetodosOrdenamiento {
    
    /**
     * BUBBLE SORT - Ordenamiento Burbuja
     * Complejidad: O(n²)
     * Compara elementos adyacentes y los intercambia si están en orden incorrecto
     */
    public static int[] burbuja(int[] arr) {
        int[] arrCopia = arr.clone();
        int n = arrCopia.length;
        
        for (int i = 0; i < n - 1; i++) {
            boolean intercambio = false;
            
            for (int j = 0; j < n - i - 1; j++) {
                if (arrCopia[j] > arrCopia[j + 1]) {
                    // Intercambiar elementos
                    int temp = arrCopia[j];
                    arrCopia[j] = arrCopia[j + 1];
                    arrCopia[j + 1] = temp;
                    intercambio = true;
                }
            }
            
            // Si no hubo intercambios, ya está ordenado
            if (!intercambio) {
                break;
            }
        }
        
        return arrCopia;
    }
    
    /**
     * QUICK SORT - Ordenamiento Rápido
     * Complejidad: O(n log n) promedio, O(n²) peor caso
     * Divide y conquista usando un pivote
     */
    public static int[] quickSort(int[] arr) {
        int[] arrCopia = arr.clone();
        quickSortRecursivo(arrCopia, 0, arrCopia.length - 1);
        return arrCopia;
    }
    
    private static void quickSortRecursivo(int[] arr, int inicio, int fin) {
        if (inicio < fin) {
            // Particionar y obtener el índice del pivote
            int pivoteIdx = particionar(arr, inicio, fin);
            
            // Ordenar recursivamente las dos mitades
            quickSortRecursivo(arr, inicio, pivoteIdx - 1);
            quickSortRecursivo(arr, pivoteIdx + 1, fin);
        }
    }
    
    private static int particionar(int[] arr, int inicio, int fin) {
        int pivote = arr[fin];
        int i = inicio - 1;
        
        for (int j = inicio; j < fin; j++) {
            if (arr[j] <= pivote) {
                i++;
                // Intercambiar arr[i] y arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        // Colocar el pivote en su posición correcta
        int temp = arr[i + 1];
        arr[i + 1] = arr[fin];
        arr[fin] = temp;
        
        return i + 1;
    }
    
    /**
     * MERGE SORT - Ordenamiento por Mezcla
     * Complejidad: O(n log n) garantizado
     * Divide el array en mitades, las ordena y las fusiona
     */
    public static int[] mergeSort(int[] arr) {
        int[] arrCopia = arr.clone();
        mergeSortRecursivo(arrCopia, 0, arrCopia.length - 1);
        return arrCopia;
    }
    
    private static void mergeSortRecursivo(int[] arr, int izq, int der) {
        if (izq < der) {
            int medio = izq + (der - izq) / 2;
            
            // Ordenar primera y segunda mitad
            mergeSortRecursivo(arr, izq, medio);
            mergeSortRecursivo(arr, medio + 1, der);
            
            // Fusionar las mitades ordenadas
            fusionar(arr, izq, medio, der);
        }
    }
    
    private static void fusionar(int[] arr, int izq, int medio, int der) {
        // Tamaños de los subarrays
        int n1 = medio - izq + 1;
        int n2 = der - medio;
        
        // Arrays temporales
        int[] izqArray = new int[n1];
        int[] derArray = new int[n2];
        
        // Copiar datos a arrays temporales
        for (int i = 0; i < n1; i++) {
            izqArray[i] = arr[izq + i];
        }
        for (int j = 0; j < n2; j++) {
            derArray[j] = arr[medio + 1 + j];
        }
        
        // Fusionar los arrays temporales
        int i = 0, j = 0;
        int k = izq;
        
        while (i < n1 && j < n2) {
            if (izqArray[i] <= derArray[j]) {
                arr[k] = izqArray[i];
                i++;
            } else {
                arr[k] = derArray[j];
                j++;
            }
            k++;
        }
        
        // Copiar elementos restantes
        while (i < n1) {
            arr[k] = izqArray[i];
            i++;
            k++;
        }
        
        while (j < n2) {
            arr[k] = derArray[j];
            j++;
            k++;
        }
    }
    
    /**
     * INSERTION SORT - Ordenamiento por Inserción
     * Complejidad: O(n²)
     * Construye el array ordenado insertando elementos en su posición correcta
     */
    public static int[] insercion(int[] arr) {
        int[] arrCopia = arr.clone();
        
        for (int i = 1; i < arrCopia.length; i++) {
            int clave = arrCopia[i];
            int j = i - 1;
            
            // Mover elementos mayores que la clave una posición adelante
            while (j >= 0 && arrCopia[j] > clave) {
                arrCopia[j + 1] = arrCopia[j];
                j--;
            }
            
            arrCopia[j + 1] = clave;
        }
        
        return arrCopia;
    }
    
    /**
     * SELECTION SORT - Ordenamiento por Selección
     * Complejidad: O(n²)
     * Selecciona el elemento mínimo y lo coloca en su posición correcta
     */
    public static int[] seleccion(int[] arr) {
        int[] arrCopia = arr.clone();
        int n = arrCopia.length;
        
        for (int i = 0; i < n - 1; i++) {
            // Encontrar el índice del elemento mínimo
            int idxMinimo = i;
            for (int j = i + 1; j < n; j++) {
                if (arrCopia[j] < arrCopia[idxMinimo]) {
                    idxMinimo = j;
                }
            }
            
            // Intercambiar el elemento mínimo con el primero no ordenado
            int temp = arrCopia[idxMinimo];
            arrCopia[idxMinimo] = arrCopia[i];
            arrCopia[i] = temp;
        }
        
        return arrCopia;
    }
    
    /**
     * Compara el rendimiento de todos los algoritmos
     */
    public static void compararAlgoritmos(int[] arr) {
        System.out.println("Array original (" + arr.length + " elementos):");
        if (arr.length <= 20) {
            System.out.println(Arrays.toString(arr));
        } else {
            System.out.print("[");
            for (int i = 0; i < 10; i++) {
                System.out.print(arr[i] + ", ");
            }
            System.out.print("..., ");
            for (int i = arr.length - 3; i < arr.length; i++) {
                System.out.print(arr[i]);
                if (i < arr.length - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
        System.out.println();
        
        System.out.println("Resultados:");
        System.out.println("-".repeat(70));
        System.out.printf("%-20s | %-15s | %-10s%n", "Algoritmo", "Tiempo (ms)", "Ordenado");
        System.out.println("-".repeat(70));
        
        // Array de referencia ordenado
        int[] arrOrdenado = arr.clone();
        Arrays.sort(arrOrdenado);
        
        // Probar cada algoritmo
        ejecutarYMedir("Burbuja", arr, arrOrdenado);
        ejecutarYMedir("Quick Sort", arr, arrOrdenado);
        ejecutarYMedir("Merge Sort", arr, arrOrdenado);
        ejecutarYMedir("Inserción", arr, arrOrdenado);
        ejecutarYMedir("Selección", arr, arrOrdenado);
        
        System.out.println("-".repeat(70));
        System.out.println("\nArray ordenado:");
        if (arrOrdenado.length <= 20) {
            System.out.println(Arrays.toString(arrOrdenado));
        } else {
            System.out.print("[");
            for (int i = 0; i < 10; i++) {
                System.out.print(arrOrdenado[i] + ", ");
            }
            System.out.print("..., ");
            for (int i = arrOrdenado.length - 3; i < arrOrdenado.length; i++) {
                System.out.print(arrOrdenado[i]);
                if (i < arrOrdenado.length - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
    }
    
    private static void ejecutarYMedir(String nombre, int[] arr, int[] arrOrdenado) {
        long inicio = System.nanoTime();
        int[] resultado = null;
        
        switch (nombre) {
            case "Burbuja":
                resultado = burbuja(arr);
                break;
            case "Quick Sort":
                resultado = quickSort(arr);
                break;
            case "Merge Sort":
                resultado = mergeSort(arr);
                break;
            case "Inserción":
                resultado = insercion(arr);
                break;
            case "Selección":
                resultado = seleccion(arr);
                break;
        }
        
        long fin = System.nanoTime();
        double tiempoMs = (fin - inicio) / 1_000_000.0;
        
        boolean esIgual = Arrays.equals(resultado, arrOrdenado);
        System.out.printf("%-20s | %12.4f ms | %-10s%n", nombre, tiempoMs, esIgual ? "✓" : "✗");
    }
    
    /**
     * Método principal con ejemplos
     */
    public static void main(String[] args) {
        // EJEMPLO 1: Array pequeño
        System.out.println("=".repeat(70));
        System.out.println("EJEMPLO 1: Array pequeño");
        System.out.println("=".repeat(70));
        int[] arrayPequeno = {64, 34, 25, 12, 22, 11, 90, 88, 45, 50};
        compararAlgoritmos(arrayPequeno);
        
        // EJEMPLO 2: Array mediano
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EJEMPLO 2: Array mediano con números aleatorios");
        System.out.println("=".repeat(70));
        int[] arrayMediano = generarArrayAleatorio(50, 1, 100);
        compararAlgoritmos(arrayMediano);
        
        // EJEMPLO 3: Array grande
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EJEMPLO 3: Array grande");
        System.out.println("=".repeat(70));
        int[] arrayGrande = generarArrayAleatorio(500, 1, 1000);
        compararAlgoritmos(arrayGrande);
        
        // EJEMPLO 4: Uso individual
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EJEMPLO 4: Uso individual de cada método");
        System.out.println("=".repeat(70));
        int[] testArr = {5, 2, 8, 1, 9, 3, 7, 4, 6};
        System.out.println("Array de prueba: " + Arrays.toString(testArr));
        System.out.println();
        
        System.out.println("Burbuja:    " + Arrays.toString(burbuja(testArr)));
        System.out.println("Quick Sort: " + Arrays.toString(quickSort(testArr)));
        System.out.println("Merge Sort: " + Arrays.toString(mergeSort(testArr)));
        System.out.println("Inserción:  " + Arrays.toString(insercion(testArr)));
        System.out.println("Selección:  " + Arrays.toString(seleccion(testArr)));
    }
    
    /**
     * Genera un array de números aleatorios
     */
    private static int[] generarArrayAleatorio(int tamano, int min, int max) {
        Random random = new Random();
        int[] arr = new int[tamano];
        for (int i = 0; i < tamano; i++) {
            arr[i] = random.nextInt(max - min + 1) + min;
        }
        return arr;
    }
}