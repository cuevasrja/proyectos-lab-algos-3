/*
 * Proyecto 2 del Laboratorio de Algoritmos y Estructuras de Datos III
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
         // Creamos un mazo de cartas mostro leyendo el archivo deck.csv
         String archivo = "deck.csv";
         List<CartaMostro> mazo = cargarMazo(archivo);
 
         // Creamos el grafo no dirigido del mazo de cartas
         GraphProj2<CartaMostro> grafo = nuevoMazo(mazo);
 
         // Agregamos las aristas al grafo, es decir, conectamos las cartas mostro
         // que tengan exactamente una característica en común
         conectarCartasSemejantes(grafo, mazo);
 
         // Obtenemos todas las ternas de cartas mostro que cumplan los
         // requisitos de la carta Mundo Chiquito
         Set<List<CartaMostro>> ternas = backtracking(grafo);
 
         // Imprimimos las ternas
         System.out.println("Las posibles combinaciones son: ");
         for (List<CartaMostro> terna : ternas) {
             System.out.println(terna.get(0) + " " + terna.get(1) + " " + terna.get(2));
         }
         System.out.println("Combinaciones posibles: " + ternas.size());
     }
 
     public static GraphProj2<CartaMostro> nuevoMazo(List<CartaMostro> mazo){
         GraphProj2<CartaMostro> grafo = new AdjacencyListGraphProj2<CartaMostro>();
         for (CartaMostro carta : mazo){
             grafo.add(carta);
         }
         return grafo;
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
      * Método que implementa para agregar las aristas al grafo
      * Complejidad O(n^2) donde n es el número de cartas de monstruos
      * @param grafo Grafo no dirigido
      * @param cartas Lista de cartas de monstruos
      */
     public static void conectarCartasSemejantes(GraphProj2<CartaMostro> grafo, List<CartaMostro> mazo){
         for (CartaMostro carta : mazo){
             for (CartaMostro c : mazo ){
                 if (!grafo.areConnected(c, carta) && atributoSemejante(carta, c)){
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
     public static boolean atributoSemejante(CartaMostro a, CartaMostro b){
         // Verificamos si las cartas tienen el mismo nivel y el resto de atributos diferentes
         boolean nivelIgual = a.getNivel() == b.getNivel() && !(a.getTipo().equals(b.getTipo()) || a.getPoder() == b.getPoder());
         // Verificamos si las cartas tienen el mismo tipo y el resto de atributos diferentes
         boolean tipoIgual = a.getTipo().equals(b.getTipo()) && !(a.getNivel() == b.getNivel() || a.getPoder() == b.getPoder());
         // Verificamos si las cartas tienen el mismo poder y el resto de atributos diferentes
         boolean poderIgual = a.getPoder() == b.getPoder() && !(a.getNivel() == b.getNivel() || a.getTipo().equals(b.getTipo()));
         // Retornamos el resultado de la comparacion
         return nivelIgual || tipoIgual || poderIgual;
     }
 
     /**
      * Algoritmo de backtracking para encontrar las combinaciones de 3 cartas mostro con atributos en comun uno a uno.
      * Complejidad O(n^3) donde n es el numero de cartas mostro.
      * @param grafo
      * @return Conjunto de combinaciones de 3 cartas mostro con atributos en comun uno a uno.
      */
     public static Set<List<CartaMostro>> backtracking(GraphProj2<CartaMostro> grafo){
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
     public static void backRec(GraphProj2<CartaMostro> grafo, List<CartaMostro> sol, Set<List<CartaMostro>> combinaciones){
         if (sol.size() == 3){
             List<CartaMostro> solF = new ArrayList<>(sol);
             combinaciones.add(solF);
             return;
         }
         for (CartaMostro carta : accionesValidas(grafo, sol)){
             if (esValida(sol, carta)){
                 // Agregamos la carta mostro a la solucion
                 sol.add(carta);
                 // Llamamos recursivamente
                 backRec(grafo, sol, combinaciones);
                 // Eliminamos la ultima carta mostro de la solucion (Hay que tener cuidado ya que la carta se puede repetir)
                 sol.remove(sol.size() - 1);
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
         return atributoSemejante(carta, ultima);
     }
 
     public static List<CartaMostro> accionesValidas(GraphProj2<CartaMostro> grafo, List<CartaMostro> sol){
         // Verificamos si sol no tiene elementos
         if (sol.size() == 0) return grafo.getAllVertices();
         // En caso contrario, tomamos el ultimo elemento de sol
         CartaMostro ultima = sol.get(sol.size() - 1);
         //retornamos los vecinos de la ultima carta de sol
         return grafo.getVerticesConnectedTo(ultima);
     }
 }
 