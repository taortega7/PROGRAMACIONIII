public class Tarea {
    private String id;
    private String descripcion;
    private int prioridad; // 1: Baja, 2: Media, 3: Alta
    private long tiempoLlegada;

    public Tarea(String id, String descripcion, int prioridad, long tiempoLlegada) {
        this.id = id;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.tiempoLlegada = tiempoLlegada;
    }

    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public long getTiempoLlegada() {
        return tiempoLlegada;
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "id='" + id + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", prioridad=" + prioridad +
                ", tiempoLlegada=" + tiempoLlegada +
                '}';
    }
}
