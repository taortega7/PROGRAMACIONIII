import java.util.Arrays;

public class Estudiante {
    // Atributos
    private int id;
    private int cedula;
    private String nombre;
    private String facultad;
    private String[] cursos; // Nuevo atributo

    // Constructor
    public Estudiante(int id, int cedula, String nombre, String facultad) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.facultad = facultad;
        this.cursos = new String[0]; // Inicialmente sin cursos
    }

    // Método Matricular Cursos
    public void matricularCursos(String[] cursos) {
        this.cursos = cursos; // Guardamos los cursos en el objeto
        System.out.println("Cursos matriculados por " + nombre + ": " + Arrays.toString(cursos));
    }

    // Método toString
    public String toString() {
        return "Estudiante id: " + id 
            + " | Cédula: " + cedula 
            + " | Nombre: " + nombre 
            + " | Facultad: " + facultad 
            + " | Cursos: " + Arrays.toString(cursos) + " ]";
    }
}
