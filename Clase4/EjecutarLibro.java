package Clase4;

public class EjecutarLibro {
    // Ejemplo de arreglo de Libro con 5 elementos
    public static void main(String[] args) {
        Libro[] libros = new Libro[5];

        libros[0] = new Libro("Cien años de soledad", "Gabriel García Márquez", 25990);
        libros[1] = new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", 19500);
        libros[2] = new Libro("El Principito", "Antoine de Saint-Exupéry", 15750);
        libros[3] = new Libro("Rayuela", "Julio Cortázar", 22300);
        libros[4] = new Libro("Ficciones", "Jorge Luis Borges", 18400);

        // Ejemplo de impresión de los libros
        for (Libro libro : libros) {
            System.out.println(libro.getTitulo() + " - " + libro.getAutor() + " - $" + libro.getPrecio());
        }
        int precioTotal = 0;
        for (Libro libro : libros) {
            precioTotal += libro.getPrecio();
        }
        //Muestra el precio total de los libros
        System.out.println("Precio total: $" + precioTotal);
    }
}