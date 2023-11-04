import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        // TODO: Implementar backtracking para agregar las aristas al grafo en base a los atributos (nivel, tipo, poder)
    }

    /**
     * Método que lee un archivo de cartas de monstruos y lo convierte en una lista
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

    public static void backtracking(){
        // TODO: Implementar backtracking para agregar las aristas al grafo en base a los atributos (nivel, tipo, poder)
    }
}