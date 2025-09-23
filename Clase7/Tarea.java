

import java.util.EmptyStackException;

public class Tarea {
	// Clase Vector como clase interna estática
	static class Vector<T> {
		private Object[] elementos;
		private int capacidad;
		private int tope;

		public Vector() {
			this.capacidad = 10;
			this.elementos = new Object[capacidad];
			this.tope = 0;
		}

		public boolean empty() {
			return tope == 0;
		}

		public T push(T item) {
			if (tope == capacidad) {
				aumentarCapacidad();
			}
			elementos[tope++] = item;
			return item;
		}

		public T pop() {
			if (empty()) {
				throw new EmptyStackException();
			}
			T item = (T) elementos[--tope];
			elementos[tope] = null;
			return item;
		}

		public T peek() {
			if (empty()) {
				throw new EmptyStackException();
			}
			return (T) elementos[tope - 1];
		}

		public int search(T item) {
			for (int i = tope - 1, pos = 1; i >= 0; i--, pos++) {
				if (elementos[i].equals(item)) {
					return pos;
				}
			}
			return -1;
		}

		public int size() {
			return tope;
		}

		private void aumentarCapacidad() {
			capacidad = capacidad * 2;
			Object[] nuevo = new Object[capacidad];
			System.arraycopy(elementos, 0, nuevo, 0, tope);
			elementos = nuevo;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for (int i = 0; i < tope; i++) {
				sb.append(elementos[i]);
				if (i < tope - 1) sb.append(", ");
			}
			sb.append("]");
			return sb.toString();
		}
	}

	public static void main(String[] args) {
		Vector<Integer> pila = new Vector<>();

		System.out.println("¿La pila está vacía? " + pila.empty());

		// Insertar elementos
		pila.push(5);
		pila.push(10);
		pila.push(15);
		pila.push(20);
		System.out.println("Pila actual: " + pila);

		// Consultar tope
		System.out.println("Elemento en el tope: " + pila.peek());

		// Eliminar tope
		System.out.println("Elemento removido: " + pila.pop());
		System.out.println("Pila después de pop: " + pila);

		// Buscar elementos
		System.out.println("Posición de 10: " + pila.search(10));
		System.out.println("Posición de 5: " + pila.search(5));
		System.out.println("Posición de 100: " + pila.search(100));

		// Tamaño
		System.out.println("Tamaño de la pila: " + pila.size());

		// Vaciar la pila
		while (!pila.empty()) {
			System.out.println("Pop: " + pila.pop());
		}
		System.out.println("¿La pila está vacía? " + pila.empty());
	}
}
