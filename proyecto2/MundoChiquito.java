/*
* Proyecto 2 del Laboratorio de Algoritmos y Estructuras de Datos III.
* Autores: Juan Cuevas (19-10056) y Luis Isea (19-10175).
*/

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MundoChiquito {
	public static void main(String[] args) {
		// Creamos un mazo de cartas mostro leyendo el archivo "deck.csv".
		String archivo = "deck.csv";
		List<CartaMostro> mazo = cargarMazo(archivo);

		// Creamos el grafo no dirigido del mazo de cartas.
		GraphProj2<CartaMostro> grafo = createDeckGraph(mazo);

		// Agregamos las aristas al grafo, es decir, conectamos las cartas mostro
		// que tengan exactamente una característica en común.
		conectarCartas(grafo, mazo);

		// Obtenemos todas las ternas de cartas mostro que cumplan los
		// requisitos de la carta Mundo Chiquito.
		Set<List<CartaMostro>> ternas = findCombinations(grafo);

		// Imprimimos las ternas.
		for (List<CartaMostro> terna : ternas) {
			System.out.println(terna.get(0) + " " + terna.get(1) + " " + terna.get(2));
		}
	}

	/**
	 * Método que lee un archivo de cartas mostro y lo convierte en una lista
	 * de cartas mostro.
	 * Complejidad O(n) donde n es el número de cartas mostro (|V| = n).
	 * Es decir, complejidad O(|V|).
	 *
	 * @param nombreArchivo Nombre del archivo a leer.
	 * @return Lista de cartas mostro.
	 */
	public static List<CartaMostro> cargarMazo(String nombreArchivo) {
		// Creamos una lista de cartas mostro.
		List<CartaMostro> cartas = new ArrayList<>();

		// Leemos el archivo de extensión .csv y lo convertimos en una lista de cartas
		// mostro.
		try {
			File archivo = new File(nombreArchivo);
			Scanner lector = new Scanner(archivo);
			// Ignoramos la primera línea del archivo.
			lector.nextLine();
			// Leemos el archivo línea por línea y creamos las cartas mostro.
			while (lector.hasNextLine()) {
				String linea = lector.nextLine();
				String[] datos = linea.split(",");
				String nombre = datos[0];
				int nivel = Integer.parseInt(datos[1]);
				String tipo = datos[2];
				int poder = Integer.parseInt(datos[3]);
				CartaMostro carta = new CartaMostro(nombre, nivel, poder, tipo);
				cartas.add(carta);
			}
			lector.close();
			return cartas;
		} catch (Exception e) {
			System.out.println("Error al leer el archivo " + nombreArchivo);
			return null;
		}
	}

	/**
	 * Método que crea un grafo no dirigido a partir de una lista de cartas mostro.
	 * Complejidad O(n) donde n es el número de cartas mostro (|V| = n).
	 * Es decir, complejidad O(|V|).
	 *
	 * @param mazo Lista de cartas mostro.
	 * @return Grafo no dirigido.
	 */
	public static GraphProj2<CartaMostro> createDeckGraph(List<CartaMostro> mazo) {
		GraphProj2<CartaMostro> grafo = new AdjacencyListGraphProj2<CartaMostro>();
		// Añadimos las cartas mostro como vértices del grafo.
		for (CartaMostro carta : mazo) {
			grafo.add(carta);
		}
		return grafo;
	}

	/**
	 * Método que agrega las aristas al grafo. Es decir, conecta las cartas mostro
	 * que tengan exactamente una característica en común.
	 * Complejidad O(n^2) donde n es el número de cartas de monstruos (|V| = n).
	 * Es decir, complejidad O(|V|^2).
	 *
	 * @param grafo  Grafo no dirigido.
	 * @param cartas Lista de cartas de monstruos.
	 */
	public static void conectarCartas(GraphProj2<CartaMostro> grafo, List<CartaMostro> mazo) {
		for (CartaMostro carta : mazo) {
			for (CartaMostro c : mazo) {
				// Verificamos si las cartas no están conectadas y tienen exactamente una
				// característica en común.
				if (!grafo.areConnected(c, carta) && unaCaractEnComun(carta, c)) {
					// Si es así, entonces las conectamos.
					grafo.connect(c, carta);
				}
			}
		}
	}

	/**
	 * Método que verifica si dos cartas mostro tienen exactamente una
	 * característica en común.
	 * Complejidad O(1).
	 *
	 * @param a Carta Mostro.
	 * @param b Carta Mostro.
	 * @return True si las dos cartas tienen una sola característica en común
	 *         (nivel, tipo o poder). False en caso contrario.
	 */
	public static boolean unaCaractEnComun(CartaMostro a, CartaMostro b) {
		// Verificamos si las cartas tienen el mismo nivel y el resto de atributos
		// diferentes.
		boolean nivelIgual = a.getNivel() == b.getNivel()
				&& !(a.getTipo().equals(b.getTipo()) || a.getPoder() == b.getPoder());
		// Verificamos si las cartas tienen el mismo tipo y el resto de atributos
		// diferentes.
		boolean tipoIgual = a.getTipo().equals(b.getTipo())
				&& !(a.getNivel() == b.getNivel() || a.getPoder() == b.getPoder());
		// Verificamos si las cartas tienen el mismo poder y el resto de atributos
		// diferentes.
		boolean poderIgual = a.getPoder() == b.getPoder()
				&& !(a.getNivel() == b.getNivel() || a.getTipo().equals(b.getTipo()));
		// Si alguna de las tres condiciones se cumple, entonces las cartas tienen
		// exactamente una característica en común.
		return nivelIgual || tipoIgual || poderIgual;
	}

	/**
	 * Algoritmo inspirado en backtracking para encontrar las combinaciones de 3
	 * cartas mostro donde cada carta mostro tiene exactamente una característica en
	 * común con la siguiente carta mostro de la combinación.
	 * Complejidad O(n^3) donde n es el numero de cartas mostro (|V| = n).
	 * Es decir, complejidad O(|V|^3).
	 *
	 * @param grafo Grafo no dirigido.
	 * @return Conjunto de ternas de cartas mostro que cumplen con las condiciones
	 *         de la carta Mundo Chiquito.
	 */
	public static Set<List<CartaMostro>> findCombinations(GraphProj2<CartaMostro> grafo) {
		List<CartaMostro> solInicial = new ArrayList<>();
		Set<List<CartaMostro>> combinaciones = new HashSet<>();
		findCombinationsRec(grafo, solInicial, combinaciones);
		return combinaciones;
	}

	/**
	 * Parte recursiva del algoritmo inspirado en backtracking para encontrar las
	 * combinaciones de 3 cartas mostro donde cada carta mostro tiene exactamente
	 * una característica en común con la siguiente carta mostro de la combinación.
	 * Complejidad O(n^3) donde n es el numero de cartas mostro (|V| = n).
	 * Es decir, complejidad O(|V|^3).
	 *
	 * @param grafo         Grafo no dirigido.
	 * @param solParcial    Solución parcial.
	 * @param combinaciones Conjunto de ternas de cartas mostro que cumplen con las
	 *                      condiciones de la carta Mundo Chiquito.
	 */
	public static void findCombinationsRec(GraphProj2<CartaMostro> grafo, List<CartaMostro> solParcial,
			Set<List<CartaMostro>> combinaciones) {
		// Verificamos si la solución parcial tiene 3 cartas mostro
		// Si es así, entonces la solución parcial es una solución final.
		if (solParcial.size() == 3) {
			// Creamos una copia de la solución parcial.
			List<CartaMostro> solFinal = new ArrayList<>(solParcial);
			// Agregamos la solución final al conjunto de combinaciones.
			combinaciones.add(solFinal);
			return;
		}
		// Si la solución parcial no tiene 3 cartas mostro, entonces
		// buscamos las cartas mostro que se pueden agregar a la solución parcial.
		for (CartaMostro carta : cartasValidas(grafo, solParcial)) {
			// Agregamos la carta mostro a la solución.
			solParcial.add(carta);
			// Llamamos recursivamente.
			findCombinationsRec(grafo, solParcial, combinaciones);
			// Eliminamos la ultima carta mostro de la solución, esto es para
			// poder probar con otras cartas mostro en la misma posición.
			solParcial.remove(solParcial.size() - 1);
		}
	}

	/**
	 * Método que retorna las cartas mostro que se pueden agregar a la solución
	 * parcial.
	 * Complejidad O(1) ya que el método getVerticesConnectedTo() es O(1) y
	 * getAllVertices() es O(1) también.
	 *
	 * @param grafo      Grafo no dirigido.
	 * @param solParcial Solución parcial.
	 * @return Lista de cartas mostro que se pueden agregar a la solución
	 *         parcial.
	 */
	public static List<CartaMostro> cartasValidas(GraphProj2<CartaMostro> grafo, List<CartaMostro> solParcial) {
		// Verificamos si solParcial no tiene elementos.
		if (solParcial.size() == 0)
			// Si es así, retornamos todos los vértices del grafo, para que
			// se creen soluciones parciales que empiecen con todas las carta mostro.
			return grafo.getAllVertices();
		// En caso contrario, tomamos el ultimo elemento de solParcial.
		CartaMostro ultima = solParcial.get(solParcial.size() - 1);
		// Y retornamos los vecinos de la ultima carta de solParcial, es decir,
		// las cartas mostro que comparten exactamente una característica con
		// la ultima carta de solParcial.
		return grafo.getVerticesConnectedTo(ultima);
	}
}
