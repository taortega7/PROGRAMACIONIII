import java.util.ArrayList;
import java.util.List;

public class Nodo implements Comparable<Nodo> {
    private String nombre;
    private List<Arista> adyacentes;
    private int distancia;
    private Nodo previo;

    public Nodo(String nombre) {
        this.nombre = nombre;
        this.adyacentes = new ArrayList<>();
        this.distancia = Integer.MAX_VALUE; // Infinito
        this.previo = null;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Arista> getAdyacentes() {
        return adyacentes;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public Nodo getPrevio() {
        return previo;
    }

    public void setPrevio(Nodo previo) {
        this.previo = previo;
    }

    public void agregarArista(Nodo destino, int peso) {
        adyacentes.add(new Arista(destino, peso));
    }

    @Override
    public int compareTo(Nodo otro) {
        return Integer.compare(this.distancia, otro.distancia);
    }
}
