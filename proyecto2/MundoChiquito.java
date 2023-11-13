import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MundoChiquito {
	public static void main(String[] args) {
		// Leemos el archivo de cartas de monstruos
		String archivo = "deck.csv";
		List<CartaMostro> cartas = leerArchivo(archivo);
		// Inicializamos nuestro grafo no dirigido
		GraphProj2<CartaMostro> grafo = new AdjacencyListGraphProj2<CartaMostro>();
		// Agregamos los vértices al grafo, es decir, las cartas de mostro
		for (CartaMostro carta : cartas) {
			grafo.add(carta);
		}
		// Agregamos las aristas al grafo
		conectarCartasSemejantes(grafo, cartas);

		// Imprimimos el grafo
		System.out.println("Grafo no dirigido:");
		System.out.println(grafo);

		// Obtenemos las combinaciones de cartas de monstruos con atributos en común uno
		// a uno
		Set<List<CartaMostro>> combinaciones = backtracking(grafo);

		// Imprimimos las combinaciones
		System.out.println("Combinaciones de cartas de monstruos con atributos en común uno a uno:");
		for (List<CartaMostro> combinacion : combinaciones) {
			System.out.println(combinacion);
		}
		// Imprimimos la cantidad de combinaciones
		System.out.println("Cantidad de combinaciones: " + combinaciones.size());
	}

	/**
	 * Método que lee un archivo de cartas de monstruos y lo convierte en una lista
	 * de cartas de monstruos. Complejidad O(n) donde n es el número de cartas de
	 * monstruos
	 *
	 * @param nombreArchivo Nombre del archivo a leer
	 * @return Lista de cartas de monstruos
	 */
	public static List<CartaMostro> leerArchivo(String nombreArchivo) {
		// Creamos una lista de cartas de monstruos
		List<CartaMostro> cartas = new ArrayList<>();

		// Leemos el archivo de extensión .csv y lo convertimos en una lista de cartas
		// de monstruos
		try {
			File archivo = new File(nombreArchivo);
			Scanner lector = new Scanner(archivo);
			// Ignoramos la primera línea del archivo
			lector.nextLine();
			// Leemos el archivo línea por línea y creamos las cartas de monstruos
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
			System.out.println("Error al leer el archivo");
			return null;
		}
	}

	/**
	 * Método que implementa para agregar las aristas al grafo
	 * Complejidad O(n^2) donde n es el número de cartas de monstruos
	 *
	 * @param grafo  Grafo no dirigido
	 * @param cartas Lista de cartas de monstruos
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
				if (!grafo.areConnected(c, carta) && tienenUnAtributoEnComun(carta, c)) {
					grafo.connect(c, carta);
				}
			}
		}
	}

	/**
	 * Método que verifica si existe un solo atributo igual entre dos cartas
	 * mostro
	 * Complejidad O(1)
	 *
	 * @param a Carta Mostro
	 * @param b Carta Mostro
	 * @return True si las dos cartas tienen un solo atributo en común,
	 *         False en caso contrario.
	 */
	public static boolean tienenUnAtributoEnComun(CartaMostro a, CartaMostro b) {
		int atributosEnComun = 0;
		if (a.getNivel() == b.getNivel()) {
			atributosEnComun++;
		}
		if (a.getPoder() == b.getPoder()) {
			atributosEnComun++;
		}
		if (a.getTipo().equals(b.getTipo())) {
			atributosEnComun++;
		}
		return atributosEnComun == 1;
	}

	/**
	 * Algoritmo de backtracking para encontrar las combinaciones de 3 cartas mostro
	 * con atributos en común uno a uno.
	 * Complejidad O(n^3) donde n es el numero de cartas mostro.
	 *
	 * @param grafo
	 * @return Conjunto de combinaciones de 3 cartas mostro con atributos en común
	 *         uno a uno.
	 */
	public static Set<List<CartaMostro>> backtracking(GraphProj2<CartaMostro> grafo) {
		List<CartaMostro> solInicial = new ArrayList<>();
		Set<List<CartaMostro>> combinaciones = new HashSet<>();
		backRec(grafo, solInicial, combinaciones);
		return combinaciones;
	}

	/**
	 * Algoritmo de backtracking para encontrar las combinaciones de 3 cartas mostro
	 * con atributos en común uno a uno.
	 * Complejidad O(n^3) donde n es el numero de cartas mostro.
	 *
	 * @param grafo
	 * @param sol
	 * @return Conjunto de combinaciones de 3 cartas mostro con atributos en común
	 *         uno a uno.
	 */
	public static void backRec(GraphProj2<CartaMostro> grafo, List<CartaMostro> sol,
			Set<List<CartaMostro>> combinaciones) {
		if (sol.size() == 3) {
			List<CartaMostro> solF = new ArrayList<>(sol);
			combinaciones.add(solF);
			return;
		}
		for (CartaMostro carta : grafo.getAllVertices()) {
			if (esValida(sol, carta)) {
				// Agregamos la carta mostro a la solución
				sol.add(carta);
				// Llamamos recursivamente
				backRec(grafo, sol, combinaciones);
				// Eliminamos la ultima carta mostro de la solución (Hay que tener cuidado ya
				// que la carta se puede repetir)
				sol.remove(sol.size() - 1);
			}
		}
	}

	/**
	 * Método que verifica si una carta mostro puede ser agregada a una solución.
	 * Complejidad O(1)
	 *
	 * @param sol
	 * @param carta
	 * @return True si la carta mostro puede ser agregada a la solución, False en
	 *         caso contrario.
	 */
	public static boolean esValida(List<CartaMostro> sol, CartaMostro carta) {
		// Verificamos el tamaño de la solución
		if (sol.size() == 0) {
			return true;
		}
		// Tomamos el ultimo elemento de la solución
		CartaMostro ultima = sol.get(sol.size() - 1);
		// No es necesario verificar si la carta ya esta en la solución, ya que la carta
		// se puede repetir.
		// Verificamos si la carta mostro es semejante a la ultima carta de la solución
		return tienenUnAtributoEnComun(carta, ultima);
	}
}
