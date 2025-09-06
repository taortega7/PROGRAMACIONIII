// Clase que representa un paquete
public class Paquete {
    // Atributos básicos del paquete
    private String id;
    private String destinatario;
    private String fechaIngreso;
    
    // Constructor - crea un paquete nuevo
    public Paquete(String id, String destinatario, String fechaIngreso) {
        this.id = id;
        this.destinatario = destinatario;
        this.fechaIngreso = fechaIngreso;
    }
    
    // Método para obtener el ID del paquete
    public String getId() {
        return id;
    }
    
    // Método para obtener el destinatario
    public String getDestinatario() {
        return destinatario;
    }
    
    // Método para obtener la fecha de ingreso
    public String getFechaIngreso() {
        return fechaIngreso;
    }
    
    // Método para mostrar información completa del paquete
    public void mostrarInfo() {
        System.out.println("ID: " + id + " | Destinatario: " + destinatario + " | Fecha: " + fechaIngreso);
    }
}