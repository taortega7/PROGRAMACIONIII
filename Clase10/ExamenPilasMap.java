import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ExamenPilasMap {

    static class PilaEnteros {
        private int[] datos;
        private int tope = -1; // -1 si esta vacía

        public PilaEnteros(int capacidad) {
            datos = new int[capacidad];
            tope = -1;
        }

        public boolean estaLlena() {
            return tope + 1 == datos.length;
        }

        public boolean estaVacia() {
            return toep == -1;
        }

        public void apilar(int x) {
            if (estaLlena())
                throw new IllegalStateException("Pila llena");
            datos[++tope] = x;
        }

        public int desapilar() {
            if (estaVacia())
                throw new IllegalStateException("Pila vacía");
            int x = datos[tope--];
            return x;
        }
    }

    // (50 pt) Verifica si el par está balanceado.
    public static boolean estaBalanceado(String x) {

        PilaEnteros pila = new PilaEnteros(x.length()); //Creo la pila con capacidad igual al tamaño de la cadena

        for (int i = 0; i < x.length(); i++) { //Revisa cada caracter de la cadena
            char c = x.charAt(i);

            if (c == '(' || c == '[' || c == '{') { //Si es un caracter de apertura se pone arriba
                pila.apilar((int) c); //El caracter se agrega como entero
            }

            else if (c == ')' || c == ']' || c == '}') { //Si es un caracter de cierre reviso que tambien haya uno que abre
                if (pila.estaVacia())
                    return false; //Si la pila está vacía no está balanceado
                int tope = pila.desapilar(); //Se elimina el ultimo que fue abierto

                if ((c == ')' && tope != (int) '(') || //Verifico si el tipo de cierre corresponde al que se abrió
                        (c == ']' && tope != (int) '[') ||
                        (c == '}' && tope != (int) '{')) {
                    return false; //Si no corresponde no está balanceado
                }
            }
        }
      
        return pila.estaVacia();  //Si la pila queda vacía, está balanceado
    }

    // (50 pt) Actualiza la calificación si x existe en el id y está en el rango 0..100.
    public static String actualizarCalificaciones(Map<Integer, Integer> califPorId, int id, int nuevo) { //Reviso si el id existe en el mapa

        if (califPorId.containsKey(id)) { //Uso containsKey para ver si existe el id

            //Verifico si la nueva calificación está en el rango 0..100

            if (nuevo >= 0 && nuevo <= 100) { //Valido el rango correcto
                califPorId.put(id, nuevo); //Actualizo la calificación usando put
                return "true"; //Si todo está bien retorna true
            }
        }
        return "false"; //Si no existe el id o el rango es incorrecto, retorno false
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingresa la cadena a verificar: ");
        String cadena = sc.nextLine(); //Cambié next() por nextLine() para leer toda la línea y me compile 
        //Con el next() no me leia 
        System.out.println("Balanceado: " + estaBalanceado(cadena));

        Map<Integer, Integer> mapa = new HashMap<>();
        mapa.put(101, 10);

        System.out.print("Ingresa el id a actualizar: "); //Lo agregue para que el usuario pueda ingresar el id 
        int id = sc.nextInt();
        System.out.print("Ingresa la nueva calificación: "); //Agregado para que el usuario pueda ingresar la nueva calificación
        int nuevo = sc.nextInt();

        System.out.println("Actualizado: " + actualizarCalificaciones(mapa, id, nuevo)); // Me imprime si se actualizó o no 

        sc.close();
    }
}
