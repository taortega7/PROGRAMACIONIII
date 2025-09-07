import java.util.Scanner;

// Clase principal del sistema de casilleros
public class SistemaCasilleros {

    // ATRIBUTOS
    private String[][] casilleros; // Matriz que representa los casilleros (3x4 = 12 casilleros)
    private Paquete[][] paquetes; // Matriz para guardar los paquetes en cada casillero
    private Scanner scanner; // Scanner para leer datos del usuario
    private final int FILAS = 3; // Tamaño filas de la matriz
    private final int COLUMNAS = 4; // Tamaño columnas de la matriz

    // CONSTRUCTOR
    public SistemaCasilleros() {
        casilleros = new String[FILAS][COLUMNAS];
        paquetes = new Paquete[FILAS][COLUMNAS];
        scanner = new Scanner(System.in);
        inicializarCasilleros();
    }

    // MÉTODOS DE INICIALIZACIÓN
    private void inicializarCasilleros() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (i == 0) {
                    casilleros[i][j] = "PEQUEÑO-LIBRE";
                } else if (i == 1) {
                    casilleros[i][j] = "MEDIANO-LIBRE";
                } else {
                    casilleros[i][j] = "GRANDE-LIBRE";
                }
                paquetes[i][j] = null; // Sin paquete inicialmente
            }
        }
    }

    // MENÚ Y EJECUCIÓN
    public void mostrarMenu() {
        System.out.println("\n==================================================");
        System.out.println("    SISTEMA DE CASILLEROS AMAZON");
        System.out.println("==================================================");
        System.out.println("1. Registrar paquete en casillero");
        System.out.println("2. Consultar casilleros disponibles");
        System.out.println("3. Información de paquetes en casilleros");
        System.out.println("4. Salir de la aplicación");
        System.out.println("==================================================");
        System.out.print("Seleccione una opción: ");
    }

    public void ejecutar() {
        System.out.println("¡Bienvenido al Sistema de Casilleros Amazon!");
        int opcion = 0;

        while (opcion != 4) {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1 -> registrarPaquete();
                case 2 -> consultarCasillerosDisponibles();
                case 3 -> mostrarInformacionPaquetes();
                case 4 -> System.out.println("¡Gracias por usar el sistema!");
                default -> {
                    System.out.println("❌ Opción inválida. Seleccione un número del 1 al 4.");
                    System.out.println("Regresando al menú principal...");
                }
            }

            // Pausa para que el usuario pueda leer
            if (opcion != 4) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
            }
        }
    }

    // VALIDACIONES
    private boolean validarId(String id) {
        if (id.length() != 5)
            return false;
        for (int i = 0; i < 5; i++) {
            if (id.charAt(i) < '0' || id.charAt(i) > '9')
                return false;
        }
        return true;
    }

    private boolean validarFecha(String fecha) {
        if (fecha.length() != 10)
            return false;
        if (fecha.charAt(2) != '/' || fecha.charAt(5) != '/')
            return false;

        String dia = fecha.substring(0, 2);
        String mes = fecha.substring(3, 5);
        String anio = fecha.substring(6, 10);

        int diaNum = Integer.parseInt(dia);
        int mesNum = Integer.parseInt(mes);

        if (diaNum < 1 || diaNum > 31)
            return false;
        if (mesNum < 1 || mesNum > 12)
            return false;

        return true;
    }

    // REGISTRO DE PAQUETES
    private void registrarPaquete() {
        System.out.println("\n--- REGISTRO DE PAQUETE ---");
        mostrarEsquemaCasilleros();

        if (!hayCasillerosLibres()) {
            System.out.println("❌ No hay casilleros disponibles en este momento.");
            return;
        }

        // ID del paquete
        String id;
        while (true) {
            System.out.print("Ingrese ID del paquete (5 dígitos): ");
            id = scanner.nextLine();
            if (validarId(id))
                break;
            System.out.println("❌ ID inválido. Ejemplo: 12345");
        }

        // Destinatario
        String destinatario = "";
        while (destinatario.isEmpty()) {
            System.out.print("Ingrese nombre del destinatario: ");
            destinatario = scanner.nextLine().trim();
            if (destinatario.isEmpty()) {
                System.out.println("❌ El destinatario no puede estar vacío.");
            }
        }

        // Fecha
        String fecha;
        while (true) {
            System.out.print("Ingrese fecha de ingreso (dd/mm/aaaa): ");
            fecha = scanner.nextLine();
            if (validarFecha(fecha))
                break;
            System.out.println("❌ Fecha inválida. Ejemplo: 15/12/2024");
        }

        Paquete nuevoPaquete = new Paquete(id, destinatario, fecha);

        // Selección de casillero
        System.out.print("Ingrese número de fila (0-2): ");
        int fila = scanner.nextInt();
        System.out.print("Ingrese número de columna (0-3): ");
        int columna = scanner.nextInt();

        if (fila < 0 || fila > 2 || columna < 0 || columna > 3) {
            System.out.println("\n❌ POSICIÓN INVÁLIDA");
            return;
        }

        if (!casilleros[fila][columna].contains("LIBRE")) {
            System.out.println("\n❌ CASILLERO OCUPADO");
            return;
        }

        paquetes[fila][columna] = nuevoPaquete;
        casilleros[fila][columna] = casilleros[fila][columna].replace("LIBRE", "OCUPADO");

        System.out.println("\n✅ ¡PAQUETE REGISTRADO EXITOSAMENTE!");
        System.out.print("Detalles: ");
        nuevoPaquete.mostrarInfo();
    }

    // CONSULTAS
    private void consultarCasillerosDisponibles() {
        System.out.println("\n--- CASILLEROS DISPONIBLES ---");
        boolean hayDisponibles = false;
        int contador = 0;

        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (casilleros[i][j].contains("LIBRE")) {
                    System.out.println("📦 Posición (" + i + "," + j + ") - " + casilleros[i][j]);
                    hayDisponibles = true;
                    contador++;
                }
            }
        }

        if (!hayDisponibles) {
            System.out.println("❌ No hay casilleros disponibles.");
        } else {
            System.out.println("\nTotal disponibles: " + contador + " de 12");
        }

        mostrarEsquemaCasilleros();
    }

    private void mostrarInformacionPaquetes() {
        System.out.println("\n--- INFORMACIÓN DE PAQUETES ---");
        boolean hayPaquetes = false;
        int contador = 0;

        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (paquetes[i][j] != null) {
                    System.out.println("📍 Casillero (" + i + "," + j + "):");
                    System.out.print("   → ");
                    paquetes[i][j].mostrarInfo();
                    hayPaquetes = true;
                    contador++;
                }
            }
        }

        if (!hayPaquetes) {
            System.out.println("📭 No hay paquetes registrados.");
        } else {
            System.out.println("\nTotal paquetes: " + contador);
        }
    }

    private void mostrarEsquemaCasilleros() {
        System.out.println("\n--- ESQUEMA DE CASILLEROS ---");
        System.out.println("L = Libre, O = Ocupado");
        System.out.println("P = Pequeño, M = Mediano, G = Grande");

        System.out.print("    ");
        for (int j = 0; j < COLUMNAS; j++) {
            System.out.print("  Col" + j + "  ");
        }
        System.out.println();

        for (int i = 0; i < FILAS; i++) {
            System.out.print("F" + i + "  ");
            for (int j = 0; j < COLUMNAS; j++) {
                String tamaño = "?";
                if (casilleros[i][j].contains("PEQUEÑO"))
                    tamaño = "P";
                if (casilleros[i][j].contains("MEDIANO"))
                    tamaño = "M";
                if (casilleros[i][j].contains("GRANDE"))
                    tamaño = "G";

                String estado = casilleros[i][j].contains("LIBRE") ? "L" : "O";
                System.out.print("[" + tamaño + "-" + estado + "] ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean hayCasillerosLibres() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (casilleros[i][j].contains("LIBRE"))
                    return true;
            }
        }
        return false;
    }
}