import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ExamenPilasMap {

    static class PilaEnteros {
        private int[] datos;
        private int top = -1; // el -1 es vacía

        public PilaEnteros(int capacidad) {
            datos = new int[capacidad];
            top = -1;
        }

        public boolean estaLlena() {
            return top + 1 == datos.length;
        }

        public boolean estaVacia() {
            return top == -1;
        }

        public void apilar(int x) {
            if (estaLlena())
                throw new IllegalStateException("Pila llena");
            datos[++top] = x;
        }

        public int desapilar() {
            if (estaVacia())
                throw new IllegalStateException("Pila vacía");
            int x = datos[top--];
            return x;
        }
    }

    // (50 pt) Verifica si el par está balanceado.
    public static boolean estaBalanceado(String x) {

        PilaEnteros pila = new PilaEnteros(x.length()); // Creo la pila con capacidad igual al tamaño de la cadena

        for (int i = 0; i < x.length(); i++) { // Revisa cada caracter de la cadena
            char c = x.charAt(i);

            if (c == '(' || c == '[' || c == '{') { // Si es un caracter de apertura se pone arriba
                pila.apilar((int) c); // apilo el caracter como entero
            }

            else if (c == ')' || c == ']' || c == '}') { // Si es un caracter de cierre reviso que tambien hay uno que
                                                         // abre
                if (pila.estaVacia())
                    return false; // si la pila está vacía, no está balanceado
                int tope = pila.desapilar(); // se elimina el ultimo que fue abierto

                if ((c == ')' && tope != (int) '(') || // Verifico si el tipo de cierre corresponde al que se abrió
                        (c == ']' && tope != (int) '[') ||
                        (c == '}' && tope != (int) '{')) {
                    return false; // si no corresponde, no está balanceado
                }
            }
        }
        // Si la pila queda vacía, está balanceado
        return pila.estaVacia();
    }

    // (50 pt) Actualiza la calificación si x existe el id y está en el rango 0..100.
    public static String actualizarCalificaciones(Map<Integer, Integer> califPorId, int id, int nuevo) { // Reviso si el id existe en el mapa

        if (califPorId.containsKey(id)) { // Uso containsKey para ver si existe el id

            // Verifico si la nueva calificación está en el rango 0..100

            if (nuevo >= 0 && nuevo <= 100) { // Valido el rango correcto
                califPorId.put(id, nuevo); // Actualizo la calificación usando put
                return "true"; // Si todo está bien retorna true
            }
        }
        return "false"; // Si no existe el id o el rango es incorrecto, retorno false
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String cadena = sc.next();
        System.out.println("Balanceado: " + estaBalanceado(cadena));

        Map<Integer, Integer> mapa = new HashMap<>();
        mapa.put(101, 10);
        System.out.println("Actualizado: " + actualizarCalificaciones(mapa, 101, 15));

        sc.close();
    }
}
