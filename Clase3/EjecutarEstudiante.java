import java.util.Arrays;

public class EjecutarEstudiante {
    public static void main(String[] args) {

        // Creación de los objetos
        Estudiante objEst1 = new Estudiante(1, 1006720569, "Angelica", "Ingeniería");
        Estudiante objEst2 = new Estudiante(2, 1001256512, "Miguel", "Ingeniería");
        Estudiante objEst3 = new Estudiante(3, 1000999111, "Byron", "Medicina");

        // Creación del arreglo de objetos (Estudiantes)
        Estudiante[] e = new Estudiante[4];
        e[0] = objEst1;
        e[1] = objEst2;
        e[2] = objEst3;
        e[3] = new Estudiante(4, 1663405777, "Tatiana", "Ingeniería");

        //*** Actividad independiente ***
        // Matricular cursos a cada estudiante
        objEst1.matricularCursos(new String[] { "Programación", "Matemáticas", "Bases de Datos" });
        objEst2.matricularCursos(new String[] { "Física", "Cálculo", "Algoritmos" });
        objEst3.matricularCursos(new String[] { "Anatomía", "Biología", "Química" });
        e[3].matricularCursos(new String[] { "Matematicas", "Redes", "Ingenieria De Software" });

        // Imprimir todos los estudiantes con sus cursos
        System.out.println("\n--- Lista de estudiantes con sus cursos ---");
        for (Estudiante est : e) {
            System.out.println(est);
        }
    }
}
