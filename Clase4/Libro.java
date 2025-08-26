package Clase4;

public class Libro {
    private String titulo;
    private String autor;
    private double precio;

    public Libro(String titulo, String autor, double precio) {
        this.titulo = titulo;
        this.autor = autor;
        this.precio = precio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;

    }

    public static double calcularPrecioTotal(Libro[] libros) {
        double total = 0.0;
        for (int i = 0; i < libros.length; i++) {
            total += libros[i].getPrecio();
        }
        return total;

    }

    public static Libro[] filtrarPorPrecioMayorA(Libro[] libros, double precioMinimo) {
                int count = 0;
                for (Libro libro : libros) {
                 if (libro.getPrecio() > precioMinimo) {
                     count++;
                 }
                }
                Libro[] resultado = new Libro[count];
                int idx = 0;
                for (Libro libro : libros) {
                 if (libro.getPrecio() > precioMinimo) {
                     resultado[idx++] = libro;
                 }
                }
                return resultado;
    }

    public static void ordenarPorPrecio(Libro[] libros) {
        int n = libros.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (libros[j].getPrecio() > libros[j + 1].getPrecio()) {
                    Libro temp = libros[j];
                    libros[j] = libros[j + 1];
                    libros[j + 1] = temp;
                }
            }
        }
    }

}
