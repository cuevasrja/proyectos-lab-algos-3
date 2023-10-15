/*
 * Proyecto 1 del Laboratorio de Algoritmos y Estructuras de Datos III
 * Autores: Juan Cuevas (19-10056) y Luis Isea (19-10175).
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AdjacencyListGraph<T> implements Graph<T> {
	/*
	 * Creamos dos HashMap que contiene una lista de adyacencia para cada vértice.
	 * El primer HashMap es para los vertices de salida y el segundo para los de
     * entrada.
	 * Sea el grafo G = (V, E) con |V| = n y |E| = m, ambos HashMap tienen n entradas
	 * y la suma de las longitudes de las listas de adyacencia es 2m.
	 */
	HashMap<T, List<T>> adjacencyListOut = new HashMap<T, List<T>>();
    HashMap<T, List<T>> adjacencyListIn = new HashMap<T, List<T>>();

	/*
	 * Recibe un vértice y lo agrega al grafo. Retorna true si el vértice es
	 * agregado con éxito.
	 * Retorna false en caso contrario.
	 * Si el vértice ya existe en el grafo, no se agrega y se retorna false.
	 * Si el vértice es null, se retorna false.
	 * Si el vértice es agregado con éxito, se crea una lista de adyacencia vacía
	 * para el vértice.
	 * Complejidad: O(1)
	 */
	public boolean add(T vertex) {
		if (contains(vertex)) {
			return false;
		}
		if (vertex == null) {
			return false;
		}
		adjacencyListOut.put(vertex, new ArrayList<T>());
        adjacencyListIn.put(vertex, new ArrayList<T>());
		return true;
	}

	/*
	 * Recibe dos vértices 'from' y 'to' y agrega al grafo un arco saliente de
	 * 'from' y entrante a 'to'.
	 * Retorna true si el arco es agregado con éxito.
	 * Retorna false en caso contrario.
	 * Si alguno de los vértices no existe en el grafo, no se agrega el arco y
	 * se retorna false.
	 * Si el arco ya existe en el grafo, no se agrega el arco y se retorna false.
	 * Complejidad: O(1)
	 */
	public boolean connect(T from, T to) {
		if (!contains(from) || !contains(to)) {
			return false;
		}
		if (adjacencyListOut.get(from).contains(to) || adjacencyListIn.get(to).contains(from)) {
			return false;
		}
		adjacencyListOut.get(from).add(to);
        adjacencyListIn.get(to).add(from);
		return true;
	}

	/*
	 * Recibe dos vértices 'from' y 'to' y elimina del grafo el arco saliente de
	 * 'from' y entrante a 'to'.
	 * Retorna true si el arco es eliminado con éxito.
	 * Retorna false en caso contrario.
	 * Si alguno de los vértices no existe en el grafo, no se elimina el arco y se
	 * retorna false.
	 * Si el arco no existe en el grafo, no se elimina el arco y se retorna false.
	 * Complejidad: O(1)
	 */
	public boolean disconnect(T from, T to) {
		if (!contains(from) || !contains(to)) {
			return false;
		}
		if (!adjacencyListOut.get(from).contains(to) || !adjacencyListIn.get(to).contains(from)) {
			return false;
		}
		adjacencyListOut.get(from).remove(to);
        adjacencyListIn.get(to).remove(from);
		return true;
	}

	/*
	 * Recibe un vértice y retorna true si el vértice pertenece a V.
	 * Retorna false en caso contrario.
	 * Complejidad: O(1)
	 */
	public boolean contains(T vertex) {
		return adjacencyListOut.containsKey(vertex) && adjacencyListIn.containsKey(vertex);
	}

	/*
	 * Recibe un vértice v y retorna la lista de predecedores de v. Es decir,
	 * retorna la lista de todos los u ∈ V tales que (u, v) ∈ E.
	 * Si ocurre algún error, retorna la referencia null
	 * Complejidad: O(1)
	 */
	public List<T> getInwardEdges(T to) {
        if (!contains(to)) {
		    return null;
        }
        return adjacencyListIn.get(to);
	}

	/*
	 * Recibe un vértice v y retorna la lista de sucesores de v. Es decir, retorna
	 * la lista de todos los u ∈ V tales que (v, u) ∈ E.
	 * Si ocurre algún error, retorna la referencia null.
	 * Complejidad: O(1)
	 */
	public List<T> getOutwardEdges(T from) {
		if (!contains(from)) {
			return null;
		}
		return adjacencyListOut.get(from);
	}

	/*
	 * Recibe un vértice v y retorna la lista de vértices adyacentes a v. Es decir,
	 * retorna la lista de todos los u ∈ V tales que (v, u) ∈ E o (u, v) ∈ E.
	 * Si ocurre algún error, retorna la referencia null.
	 * Complejidad: O(1)
	 */
	public List<T> getVerticesConnectedTo(T vertex) {
		if (!contains(vertex)) {
            return null;
        }
        List<T> result = new ArrayList<T>();
        result.addAll(adjacencyListOut.get(vertex));
		result.addAll(adjacencyListIn.get(vertex));
        return result.distinct();
	}

	/*
	 * Retorna la lista de todos vértices del grafo.
	 * Es decir, todos los elementos de V.
	 * Complejidad: O(1)
	 */
	public List<T> getAllVertices() {
		return adjacencyListOut.keySet().stream().toList();
	}

	/*
	 * Recibe un vértice y lo elimina del grafo. Retorna true si el vértice es
	 * eliminado con éxito. Retorna false en caso contrario.
	 * Si el vértice no existe en el grafo, no se elimina y se retorna false.
	 * Complejidad: O(n). Siendo n la cantidad de vértices.
	 */
	public boolean remove(T vertex) {
		if (!contains(vertex)) {
			return false;
		}
        adjacencyListOut.remove(vertex);
        adjacencyListIn.remove(vertex);
        for (T v : adjacencyListOut.keySet()) {
            adjacencyListOut.get(v).remove(vertex);
        }
        for (T v : adjacencyListIn.keySet()) {
            adjacencyListIn.get(v).remove(vertex);
        }
		return true;
	}

	/*
	 * Retorna la cantidad de vértices que contiene el grafo. Es decir, la
	 * cardinalidad del conjunto de vértices |V|.
	 * Complejidad: O(1)
	 */
	public int size() {
		return adjacencyListOut.size();
	}

	/*
	 * Recibe una colección V′ de vértices y retorna otra instancia de grafo
	 * donde el conjunto de vértices contiene solo aquellos vértices presentes en V’
	 * y solo aquellos arcos asociados a esos vértices. Es decir,
	 * retorna G′ = (V′, E′)
	 * donde E′ = {(u, v) ∈ E ∣ u ∈ V′ ∧ v ∈ V′}.
	 * Si ocurre algún error, retorna la referencia null.
	 * Complejidad: O(n*m). Siendo n la cantidad de vértices y m la cantidad promedio de arcos por vértice.
	 */
	public Graph<T> subgraph(Collection<T> vertices) {
		if (vertices == null) {
			return null;
		}
		AdjacencyListGraph<T> result = new AdjacencyListGraph<T>();
		for (T vertex : vertices) {
			if (!contains(vertex)) {
				return null;
			}
			result.add(vertex);
		}
		for (T vertex : vertices) {
			for (T edge : adjacencyListOut.get(vertex)) {
				if (vertices.contains(edge)) {
					result.connect(vertex, edge);
				}
			}
		}
		return result;
	}

	/*
	 * Retorna una representación en String del grafo.
	 * Complejidad: O(n). Siendo n la cantidad de vértices.
	 */
	@Override
	public String toString() {
		String result = "";
		for (T vertex : adjacencyListOut.keySet()) {
			result += "[" + vertex + "] -> " + adjacencyListOut.get(vertex) + "\n";
		}
		return result;
	}
}
