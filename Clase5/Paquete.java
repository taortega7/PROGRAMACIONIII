// Clase que representa un paquete
class Paquete {
    private String id;
    private String destinatario;
    private String fechaIngreso;

    // Constructor
    public Paquete(String id, String destinatario, String fechaIngreso) {
        this.id = id;
        this.destinatario = destinatario;
        this.fechaIngreso = fechaIngreso;
    }

    // Getters básicos
    public String getId() {
        return id;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    // Método para mostrar información
    public void mostrarInfo() {
        System.out.println("ID: " + id + " | Destinatario: " + destinatario + " | Fecha: " + fechaIngreso);
    }
}