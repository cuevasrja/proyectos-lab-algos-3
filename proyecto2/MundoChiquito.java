import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MundoChiquito{
    public static void main(String[] args) {
        String archivo = args[0];
        // Leemos el archivo de cartas de monstruos
        List<CartaMostro> cartas = leerArchivo(archivo);
        // Inicializamos nuestro grafo no dirigido
        Graph<CartaMostro> grafo = new AdjacencyListGraph<CartaMostro>();
        // Agregamos los vértices al grafo
        for (CartaMostro carta : cartas){
            grafo.add(carta);
        }
        // Agregamos las aristas al grafo
        conectarCartasSemejantes(grafo, cartas);

        // Imprimimos el grafo
        System.out.println("Grafo no dirigido:");
        System.out.println(grafo);

        // Obtenemos las combinaciones de cartas de monstruos con atributos en comun uno a uno
        Set<List<CartaMostro>> combinaciones = backtracking(grafo);

        // Imprimimos las combinaciones
        System.out.println("Combinaciones de cartas de monstruos con atributos en comun uno a uno:");
        for (List<CartaMostro> combinacion : combinaciones){
            System.out.println(combinacion);
        }
        // Imprimimos la cantidad de combinaciones
        System.out.println("Cantidad de combinaciones: " + combinaciones.size());
    }

    /**
     * Método que lee un archivo de cartas de monstruos y lo convierte en una lista
     * de cartas de monstruos. Complejiad O(n) donde n es el número de cartas de monstruos
     * @param nombreArchivo Nombre del archivo a leer
     * @return Lista de cartas de monstruos
     */
    public static List<CartaMostro> leerArchivo(String nombreArchivo){
        // Creamos una lista de cartas de monstruos
        List<CartaMostro> cartas = new ArrayList<>();
        
        // Leemos el archivo de extensión .csv y lo convertimos en una lista de cartas
        // de monstruos
        try{
            File archivo = new File(nombreArchivo);
            Scanner lector = new Scanner(archivo);
            // Ignoramos la primera línea del archivo
            lector.nextLine();
            // Leemos el archivo línea por línea y creamos las cartas de monstruos
            while(lector.hasNextLine()){
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
        } catch (Exception e){
            System.out.println("Error al leer el archivo");
            return null;
        }
    }

    /**
     * Método que implementa para agregar las aristas al grafo
     * Complejidad O(n^2) donde n es el número de cartas de monstruos
     * @param grafo Grafo no dirigido
     * @param cartas Lista de cartas de monstruos
     */
    public static void conectarCartasSemejantes(Graph<CartaMostro> grafo, List<CartaMostro> mazo){
        for (CartaMostro carta : mazo){
            for (CartaMostro c : mazo ){
                if (!grafo.areConnected(c, carta) && atributosSemejantes(carta, c)){
                    grafo.connect(c, carta);
                }
            }
        }
    }

    /**
     * Metodo que verifica si existe al menos un atributo igual entre dos cartas mostro
     * Complejidad O(1)
     * @param a Carta Mostro
     * @param b Carta Mostro
     * @return True si las dos cartas tienen por lo menos un atributo en comun, False en caso contrario.
     */
    public static boolean atributosSemejantes(CartaMostro a, CartaMostro b){
        return a.getNivel() == b.getNivel() || a.getPoder() == b.getPoder() || a.getTipo().equals(b.getTipo());
    }

    /**
     * Algoritmo de backtracking para encontrar las combinaciones de 3 cartas mostro con atributos en comun uno a uno.
     * Complejidad O(n^3) donde n es el numero de cartas mostro.
     * @param grafo
     * @return Conjunto de combinaciones de 3 cartas mostro con atributos en comun uno a uno.
     */
    public static Set<List<CartaMostro>> backtracking(Graph<CartaMostro> grafo){
        List<CartaMostro> solInicial = new ArrayList<>();
        Set<List<CartaMostro>> combinaciones = new HashSet<>();
        backRec(grafo, solInicial, combinaciones);
        return combinaciones;
    }

    /**
     * Algoritmo de backtracking para encontrar las combinaciones de 3 cartas mostro con atributos en comun uno a uno.
     * Complejidad O(n^3) donde n es el numero de cartas mostro.
     * @param grafo
     * @param sol
     * @return Conjunto de combinaciones de 3 cartas mostro con atributos en comun uno a uno.
     */
    public static void backRec(Graph<CartaMostro> grafo, List<CartaMostro> sol, Set<List<CartaMostro>> combinaciones){
        if (sol.size() == 3){
            List<CartaMostro> solF = new ArrayList<>(sol);
            combinaciones.add(solF);
            return;
        }
        for (CartaMostro carta : grafo.getAllVertices()){
            if (esValida(sol, carta)){
                sol.add(carta);
                backRec(grafo, sol, combinaciones);
                sol.remove(carta);
            }
        }
    }

    /**
     * Metodo que verifica si una carta mostro puede ser agregada a una solucion.
     * Complejidad O(1)
     * @param sol
     * @param carta
     * @return True si la carta mostro puede ser agregada a la solucion, False en caso contrario.
     */
    public static boolean esValida(List<CartaMostro> sol, CartaMostro carta){
        // Verificamos el tamaño de la solucion
        if (sol.size() == 0){
            return true;
        }
        // Tomamos el ultimo elemento de la solucion
        CartaMostro ultima = sol.get(sol.size() - 1);
        // No es necesario verificar si la carta ya esta en la solucion, ya que la carta se puede repetir.
        // Verificamos si la carta mostro es semejante a la ultima carta de la solucion
        return atributosSemejantes(carta, ultima);
    }
}