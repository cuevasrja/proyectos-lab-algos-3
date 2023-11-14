/*
 * Proyecto 2 del Laboratorio de Algoritmos y Estructuras de Datos III
 * Autores: Juan Cuevas (19-10056) y Luis Isea (19-10175).
 */

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MundoChiquito {
	public static void main(String[] args) {
		// Creamos un mazo de cartas mostro leyendo el archivo deck.csv
		String archivo = "deck.csv";
		List<CartaMostro> mazo = cargarMazo(archivo);

		// Creamos el grafo no dirigido del mazo de cartas
		AdjacencyListGraphProj2<CartaMostro> grafo = createDeckGraph(mazo);

		// Agregamos las aristas al grafo, es decir, conectamos las cartas mostro
		// que tengan exactamente una característica en común
		conectarCartasSemejantes(grafo, mazo);

		// Obtenemos todas las ternas de cartas mostro que cumplan los
		// requisitos de la carta Mundo Chiquito
		List<List<CartaMostro>> ternas = findCombinations(grafo);

		// Imprimimos las ternas
		for (List<CartaMostro> terna : ternas) {
			System.out.println(terna.get(0) + " " + terna.get(1) + " " + terna.get(2));
		}
	}

	/**
	 * Método que lee un archivo de cartas mostro y lo convierte en una lista
	 * de cartas mostro.
	 * Complejidad O(n) donde n es el número de cartas mostro
	 *
	 * @param nombreArchivo Nombre del archivo a leer
	 * @return Lista de cartas mostro
	 */
	public static List<CartaMostro> cargarMazo(String nombreArchivo) {
		// Creamos una lista de cartas mostro
		List<CartaMostro> cartas = new ArrayList<>();

		// Leemos el archivo de extensión .csv y lo convertimos en una lista de cartas
		// mostro
		try {
			File archivo = new File(nombreArchivo);
			Scanner lector = new Scanner(archivo);
			// Ignoramos la primera línea del archivo
			lector.nextLine();
			// Leemos el archivo línea por línea y creamos las cartas mostro
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
	 * Método que crea un grafo no dirigido a partir de una lista de cartas mostro
	 * Complejidad O(n) donde n es el número de cartas mostro
	 *
	 * @param cartas Lista de cartas mostro
	 * @return Grafo no dirigido
	 */
	public static AdjacencyListGraphProj2<CartaMostro> createDeckGraph(List<CartaMostro> mazo) {
		AdjacencyListGraphProj2<CartaMostro> grafo = new AdjacencyListGraphProj2<>();
		for (CartaMostro carta : mazo) {
			grafo.add(carta);
		}
		return grafo;
	}

	/**
	 * Método que agrega las aristas al grafo del mazo de cartas mostro.
	 * Para agregar una arista entre dos cartas mostro, estas deben tener
	 * exactamente una característica en común.
	 * Complejidad O(n^2) donde n es el número de cartas mostro
	 *
	 * @param grafo  Grafo no dirigido
	 * @param cartas Lista de cartas mostro
	 */
	public static void conectarCartasSemejantes(GraphProj2<CartaMostro> grafo, List<CartaMostro> mazo) {
		for (CartaMostro carta : mazo) {
			for (CartaMostro c : mazo) {
				// No agregamos una arista de una carta mostro a si misma
				if (c == carta) {
					continue;
				}
				// Verificamos que las cartas no estén conectadas y que tengan un solo atributo
				// en común
				if (!grafo.areConnected(c, carta) && tienenUnaCaracteristicaEnComun(carta, c)) {
					grafo.connect(c, carta);
				}
			}
		}
	}

	/**
	 * Método que verifica si dos cartas mostro tienen exactamente una
	 * característica
	 * en común.
	 * Complejidad O(1)
	 *
	 * @param a Carta Mostro
	 * @param b Carta Mostro
	 * @return True si las dos cartas tienen una sola característica en
	 *         común, False en caso contrario.
	 */
	public static boolean tienenUnaCaracteristicaEnComun(CartaMostro a, CartaMostro b) {
		int caracteristicasEnComun = 0;
		if (a.getNivel() == b.getNivel()) {
			caracteristicasEnComun++;
		}
		if (a.getPoder() == b.getPoder()) {
			caracteristicasEnComun++;
		}
		if (a.getTipo().equals(b.getTipo())) {
			caracteristicasEnComun++;
		}
		return caracteristicasEnComun == 1;
	}

	/**
	 * Algoritmo que encuentra todas las ternas de cartas mostro que cumplen
	 * con los requisitos de la carta Mundo Chiquito.
	 * Complejidad O(n^3) donde n es el numero de cartas mostro.
	 *
	 * @param grafo
	 * @return Conjunto de ternas de 3 cartas mostro con exactamente una
	 *         característica en común entre una carta y la siguiente.
	 */
	public static List<List<CartaMostro>> findCombinations(GraphProj2<CartaMostro> grafo) {
		List<CartaMostro> sol = new ArrayList<>();
		List<List<CartaMostro>> ternas = new LinkedList<>();
		for (CartaMostro carta : grafo.getAllVertices()) {
			// Agregamos la primera carta mostro a la solución parcial
			sol.add(carta);
			// Ahora probamos con las cartas mostro que son adyacentes a la carta mostro
			// agregada
			for (CartaMostro adyacente : grafo.getVerticesConnectedTo(carta)) {
				sol.add(adyacente);
				for (CartaMostro adyacente2 : grafo.getVerticesConnectedTo(adyacente)) {
					sol.add(adyacente2);
					// Al añadir la tercera carta mostro, llegamos a una terna completa
					// y la agregamos al conjunto de ternas
					ternas.add(sol);
					// Removemos la tercera carta mostro de la solución parcial y probamos con
					// la siguiente carta adyacente2
					sol.remove(adyacente2);
				}
				// Una vez que probamos todas las ternas posibles que tienen como segunda carta
				// a `adyacente` y empiezan en `carta`, probamos con la siguiente carta
				// adyacente
				sol.remove(adyacente);
			}
			// Una vez que probamos todas las ternas posibles que comienzan con `carta`, la
			// removemos de la solución parcial y probamos con la siguiente carta
			sol.remove(carta);
		}
		return ternas;
	}
}
