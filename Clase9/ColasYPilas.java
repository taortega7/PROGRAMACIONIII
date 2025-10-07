import java.util.*;

public class ColasYPilas {
	public static void main(String[] args) {
		GestorTareas gestor = new GestorTareas();
		Scanner in = new Scanner(System.in);
		int opcion;
		do {
			System.out.println("\n--- GESTOR DE TAREAS ---");
			System.out.println("1. Agregar tarea");
			System.out.println("2. Procesar siguiente tarea");
			System.out.println("3. Consultar estado de tarea");
			System.out.println("0. Salir");
			System.out.print("Seleccione una opción: ");
			opcion = Integer.parseInt(in.nextLine());
			switch (opcion) {
				case 1: {
					System.out.print("ID de la tarea: ");
					String id = in.nextLine();
					System.out.print("Descripción: ");
					String desc = in.nextLine();
					System.out.print("Prioridad (1=Baja, 2=Media, 3=Alta): ");
					int prioridad = Integer.parseInt(in.nextLine());
					long tiempoLlegada = System.currentTimeMillis();
					gestor.agregarTarea(new Tarea(id, desc, prioridad, tiempoLlegada));
					System.out.println("Tarea agregada correctamente.");
					break;
				}
				case 2: {
					Tarea t = gestor.procesarSiguienteTarea();
					if (t != null) {
						System.out.println("Tarea procesada: " + t);
					} else {
						System.out.println("No hay tareas pendientes.");
					}
					break;
				}
				case 3: {
					System.out.print("Ingrese el ID de la tarea a consultar: ");
					String id = in.nextLine();
					String estado = gestor.consultarEstadoTarea(id);
					System.out.println("Estado: " + estado);
					break;
				}
				case 0: {
					System.out.println("Saliendo del sistema.");
					break;
				}
				default:
					System.out.println("Opción inválida.");
			}
		} while (opcion != 0);
		in.close();
	}
}
