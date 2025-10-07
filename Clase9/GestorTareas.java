import java.util.*;

public class GestorTareas {
    private Queue<Tarea> colaEspera;
    private Stack<Tarea> pilaPrioritaria;
    private Map<String, String> registroTrazabilidad;

    public GestorTareas() {
        colaEspera = new LinkedList<>();
        pilaPrioritaria = new Stack<>();
        registroTrazabilidad = new HashMap<>();
    }

    public void agregarTarea(Tarea tarea) {
        if (tarea.getPrioridad() == 3) {
            pilaPrioritaria.push(tarea);
        } else {
            colaEspera.offer(tarea);
        }
    }

    public Tarea procesarSiguienteTarea() {
        Tarea tareaProcesada = null;
        long tiempoProcesamiento = System.currentTimeMillis();
        if (!pilaPrioritaria.isEmpty()) {
            tareaProcesada = pilaPrioritaria.pop();
        } else if (!colaEspera.isEmpty()) {
            tareaProcesada = colaEspera.poll();
        }
        if (tareaProcesada != null) {
            registroTrazabilidad.put(
                tareaProcesada.getId(),
                "Completada | Llegada: " + tareaProcesada.getTiempoLlegada() +
                " | Procesada: " + tiempoProcesamiento
            );
        }
        return tareaProcesada;
    }

    public String consultarEstadoTarea(String idTarea) {
        if (registroTrazabilidad.containsKey(idTarea)) {
            return "Completada: " + registroTrazabilidad.get(idTarea);
        }
        for (Tarea t : pilaPrioritaria) {
            if (t.getId().equals(idTarea)) {
                return "Pendiente en pila prioritaria";
            }
        }
        for (Tarea t : colaEspera) {
            if (t.getId().equals(idTarea)) {
                return "Pendiente en cola de espera";
            }
        }
        return "ID no encontrado";
    }
}
