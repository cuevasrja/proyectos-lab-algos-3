import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class AlfonsoJose {
    public static void main(String[] args) {
        String archivo = "atlantis.txt";
        // Leemos el archivo y obtenemos la matriz
        int[][] matriz = leerArchivo(archivo);

        // Construimos el grafo a partir de la matriz
        Graph<Integer> grafo = construitGrafo(matriz);

        // Calculamos las componentes fuertemente conexas del grafo
        List<Set<Vertex<Integer>>> CFC = componentesFuertementeConexas(grafo);

        // Construimos el grafo reducido a partir del grafo y las componentes fuertemente conexas
        Graph<Integer> grafoReducido = reducirGrafo(grafo, CFC);

        int[] mutableInt = {0};
        // Calculamos la cantidad de cubos máximos de agua que se pueden usar para inundar Atlantis
        cubosMaximos(matriz, grafoReducido, CFC, mutableInt);

        int cubosMaximos = mutableInt[0];
        // Imprimimos la cantidad de cubos máximos de agua que se pueden usar para inundar Atlantis
        System.out.println("Cantidad de cubos máximos de agua que se pueden usar para inundar Atlantis: " + cubosMaximos);
        
    }

    /**
     * Retorna un string con el texto coloreado
     * Complejidad: O(1)
     * @param color Numero del color
     * @param i Numero del componente
     * @return String con el texto coloreado
     */
    public static String colorear(int color, int i){
        return "\u001B[" + color + "m" + "Componente " + i + "\u001B[0m";
    }

    /**
     * Imprime las componentes fuertemente conexas
     * Complejidad: O(|CC|) donde |CC| es la cantidad de componentes fuertemente conexas
     * @param componentes lista de conjuntos de vértices. Cada conjunto representa una componente fuertemente conexa
    */
    public static void imprimirCC(List<Set<Vertex<Integer>>> componentes){
        int i = 1;
        int color = 91;
        // Recorremos las componentes fuertemente conexas
        for (Set<Vertex<Integer>> componente : componentes) {
            if (color > 96) color = 91;
            System.out.println(colorear(color, i) + ": " + componente);
            i++;
            color++;
        }
        System.out.println("");
    }

    public static void imprimirMatriz(int[][] matriz){
        for (int i = 0; i < matriz.length; i++) {
            System.out.print("[");
            for (int j = 0; j < matriz[0].length; j++) {
                System.out.print(matriz[i][j]);
                if (j != matriz[0].length-1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
        System.out.println("");
    }

    /**
     * Lee el archivo y devuelve un grafo con los datos del archivo.
     * Complejidad: O(|V|) donde |V| es la cantidad de vertices del grafo
     * @param path
     * @return matriz
    */
    public static int[][] leerArchivo(String path) {
        int[][] matriz = null;
        try {
            File archivo = new File(path);
            Scanner sc = new Scanner(archivo);
            // Leemos la cantidad de lineas del archivo y esas son las filas de la matriz
            int filas = 0;
            while (sc.hasNextLine()) {
                filas++;
                sc.nextLine();
            }
            sc.close();
            // Leemos la cantidad de columnas del archivo y esas son las columnas de la matriz
            sc = new Scanner(archivo);
            String linea = sc.nextLine();
            sc.close();
            sc = new Scanner(archivo);
            String[] valores = linea.split(" ");
            int columnas = valores.length;
            matriz = new int[filas][columnas];
            // Llenamos la matriz con los datos del archivo
            int i = 0;
            while (sc.hasNextLine()) {
                linea = sc.nextLine();
                valores = linea.split(" ");
                for (int j = 0; j < valores.length; j++) {
                    matriz[i][j] = Integer.parseInt(valores[j]);
                }
                i++;
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el archivo");
        }
        return matriz;
    }

    public static Graph<Integer> construitGrafo(int[][] matriz){
        Graph<Integer> grafo = new AdjacencyListGraph<>();
        // Llenamos la matriz con los datos del archivo
        int vertex = 0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                Vertex<Integer> v = new Vertex<Integer>(vertex, matriz[i][j]);
                grafo.add(v);
                vertex++;
            }
        }
        // Recorremos la matriz y agregamos los arcos al grafo.
        // Los arcos se agregan de la siguiente manera:
        // Sean u y v dos vertices adyacentes, entonces se agrega un arco de u a v si costo(u) >= costo(v). El costo de cada vertice es el valor de la matriz en esa posicion.
        for (Vertex<Integer> u : grafo.getAllVertices()) {
            for (Vertex<Integer> v : grafo.getAllVertices()) {
                int uI = u.getId()/matriz[0].length;
                int uJ = u.getId()%matriz[0].length;
                int vI = v.getId()/matriz[0].length;
                int vJ = v.getId()%matriz[0].length;
                if (Math.abs(uI-vI) + Math.abs(uJ-vJ) == 1 && u.getCost() >= v.getCost()) {
                    grafo.connect(u, v);
                }
            }
        }
        return grafo;
    }

    /**
     * Retorna una lista de conjuntos de vértices que representan las componentes fuertemente conexas del grafo
     * Complejidad: O(|V|*|E|) donde |V| es la cantidad de vértices y |E| es la cantidad de arcos
     * @param graph grafo dirigido
     * @return lista de conjuntos de vértices. Cada conjunto representa una componente fuertemente conexa
    */
    public static List<Set<Vertex<Integer>>> componentesFuertementeConexas(Graph<Integer> graph){
        // Inicialiamos nuestra pila de caminos cerrados y nuestro conjunto de vértices visitados
        Stack<Vertex<Integer>> stack = new Stack<>();
        Set<Vertex<Integer>> visited = new HashSet<>();

        // Realizamos DFS en el grafo original
        for (Vertex<Integer> vertex : graph.getAllVertices()) {
            // Si el vértice no ha sido visitado, hacemos DFS
            if (!visited.contains(vertex)) {
                dfs(graph, vertex, visited, stack);
            }
        }

        // Creamos el grafo transpuesto
        Graph<Integer> transposedGraph = simetrico(graph);

        // Reiniciamos nuestro conjunto de vértices visitados
        visited.clear();
        // Creamos una lista de conjuntos de vértices que representan las componentes fuertemente conexas
        List<Set<Vertex<Integer>>> CFC = new ArrayList<>();

        // Realizamos DFS en el grafo transpuesto en el orden de finalización de los vértices
        while (!stack.isEmpty()) {
            Vertex<Integer> vertex = stack.pop();
            // Si el vértice no ha sido visitado, hacemos DFS
            if (!visited.contains(vertex)) {
                // Creamos un conjunto de vértices que representan una componente fuertemente conexa
                Set<Vertex<Integer>> componente = new HashSet<>();
                dfs(transposedGraph, vertex, visited, componente);
                // Agregamos la componente fuertemente conexa a la lista de componentes
                CFC.add(componente);
            }
        }

        return CFC;
    }

    /**
     * DFS en un grafo dirigido
     * Complejidad: O(|V|+|E|) donde |E| es la cantidad de arcos y |V| es la cantidad de vértices
     * @param graph grafo dirigido
     * @param vertex vértice inicial
     * @param visited conjunto de vértices visitados
     * @param result conjunto de vértices visitados en orden de finalización
    */
    private static void dfs(Graph<Integer> graph, Vertex<Integer> vertex, Set<Vertex<Integer>> visited, Collection<Vertex<Integer>> result) {
        // Agregamos el vértice al conjunto de vértices visitados
        visited.add(vertex);
        // Recorremos los vértices adyacentes al vértice actual
        for (Vertex<Integer> adjacentVertex : graph.getOutwardEdges(vertex)) {
            // Si el vértice adyacente no ha sido visitado, hacemos DFS
            if (!visited.contains(adjacentVertex)) {
                dfs(graph, adjacentVertex, visited, result);
            }
        }
        // Agregamos el vértice al conjunto de vértices visitados en orden de finalización
        result.add(vertex);
    }

    /**
     * Retorna el grafo simetrico de un grafo dirigido
     * Complejidad: O(|V|*|E|) donde |V| es la cantidad de vértices y |E| es la cantidad de arcos
     * @param graph grafo dirigido
     * @return grafo simetrico
    */
    private static Graph<Integer> simetrico(Graph<Integer> graph) {
        // Creamos un nuevo grafo
        Graph<Integer> transposedGraph = new AdjacencyListGraph<>();

        // Agregamos los vértices al grafo simetrico
        for (Vertex<Integer> vertex : graph.getAllVertices()) {
            transposedGraph.add(vertex);
        }

        // Conectamos los vértices del grafo simetrico
        for (Vertex<Integer> vertex : graph.getAllVertices()) {
            // Recorremos los vértices que tengan arcos de entrada en el vértice actual.
            // Estos vértices serán los vértices de salida en el grafo simetrico.
            for (Vertex<Integer> adjacentVertex : graph.getInwardEdges(vertex)) {
                transposedGraph.connect(vertex, adjacentVertex);
            }
        }

        return transposedGraph;
    }
    
    /**
     * Retorna un grafo reducido a partir de un grafo dirigido y una lista de conjuntos de vértices que representan las componentes fuertemente conexas del grafo
     * Complejidad: O(|V|*|E|) donde |V| es la cantidad de vértices y |E| es la cantidad de arcos
     * @param graph grafo dirigido
     * @param CFC lista de conjuntos de vértices. Cada conjunto representa una componente fuertemente conexa
     * @return grafo reducido
    */
    public static Graph<Integer> reducirGrafo(Graph<Integer> graph, List<Set<Vertex<Integer>>> CFC){
        // Creamos un nuevo grafo
        Graph<Integer> reducedGraph = new AdjacencyListGraph<>();
        // Obtenemos los vértices del grafo original en orden de finalización
        List<Vertex<Integer>> vertices = graph.getAllVertices();

        // Agregamos un vertice por cada componente fuertemente conexa, el costo de cada vertice es el de cualquier vertice de la componente fuertemente conexa
        // ya que todos los vertices de una componente fuertemente conexa tienen el mismo costo.
        for (Set<Vertex<Integer>> componente : CFC) {
            Vertex<Integer> vertex = componente.iterator().next();
            reducedGraph.add(vertex);
        }
        

        // Conectamos los vértices del grafo reducido
        for (Vertex<Integer> vertex : vertices) {
            // Recorremos los vértices que tengan arcos de salida en el vértice actual.
            // Estos vértices serán los vértices de entrada en el grafo reducido.
            for (Vertex<Integer> adjacentVertex : graph.getOutwardEdges(vertex)) {
                // Si el vértice actual y el vértice adyacente no pertenecen a la misma componente fuertemente conexa, conectamos los vértices en el grafo reducido
                if (!perteneceAComponente(vertex, adjacentVertex, CFC)) {
                    reducedGraph.connect(vertex, adjacentVertex);
                }
            }
        }

        return reducedGraph;
    }

    /**
     * Retorna true si dos vértices pertenecen a la misma componente fuertemente conexa
     * Complejidad: O(|CC|) donde |CC| es la cantidad de componentes fuertemente conexas
     * @param vertex1 vértice 1
     * @param vertex2 vértice 2
     * @param CFC lista de conjuntos de vértices. Cada conjunto representa una componente fuertemente conexa
     * @return true si los vértices pertenecen a la misma componente fuertemente conexa
    */
    private static boolean perteneceAComponente(Vertex<Integer> vertex1, Vertex<Integer> vertex2, List<Set<Vertex<Integer>>> CFC) {
        // Recorremos las componentes fuertemente conexas
        for (Set<Vertex<Integer>> componente : CFC) {
            // Si la componente fuertemente conexa contiene a los dos vértices, retornamos true
            if (componente.contains(vertex1) && componente.contains(vertex2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calcula la cantidad de cubos máximos de agua que se pueden usar para inundar Atlantis
     * Complejidad: O(?)
     * @param matriz matriz de costos (Altura de cada bloque)
     * @param grafoReducido grafo reducido a componentes fuertemente conexas
     * @return cantidad de cubos máximos de agua que se pueden usar para inundar Atlantis
     */
    public static void cubosMaximos(int[][] matriz, Graph<Integer> grafoReducido, List<Set<Vertex<Integer>>> CFC, int[] mutableInt){
        Graph<Integer> graphAux = grafoReducido;

        List<Vertex<Integer>> fuentes = getFuentes(graphAux);
        List<Vertex<Integer>> sumideros = getSumideros(graphAux);
        
        // Imprimimos las componentes fuertemente conexas
        imprimirCC(CFC);

        // Imprimimos las fuentes
        System.out.println("Fuentes: " + fuentes);

        // Imprimimos los sumideros
        System.out.println("Sumideros: " + sumideros);

        // Imprimimos el grafo reducido
        System.out.println("Grafo reducido: ");
        System.out.println(graphAux);

        // Imprimimos la matriz
        imprimirMatriz(matriz);
        
        // TODO: Calcular la cantidad de cubos máximos de agua que se pueden usar para inundar Atlantis
    }

    /**
     * Retorna true si un vértice es borde
     * Complejidad: O(1)
     * @param vertex vértice
     * @param matriz matriz de costos (Altura de cada bloque)
     * @return true si el vértice es borde
     */
    private static boolean esBorde(Vertex<Integer> vertex, int[][] matriz) {
        int i = vertex.getId()/matriz[0].length;
        int j = vertex.getId()%matriz[0].length;
        System.out.println("i: " + i + " j: " + j);
        System.out.println("v: " + vertex);
        if (i == 0 || i == matriz.length-1 || j == 0 || j == matriz[0].length-1) {
            return true;
        }
        return false;
    }

    /**
     * Retorna una lista de vértices sumideros
     * Complejidad: O(|V|*|E|) donde |V| es la cantidad de vértices y |E| es la cantidad de arcos
     * @param graph grafo dirigido
     * @return lista de vértices sumideros
     */
    private static List<Vertex<Integer>> getSumideros(Graph<Integer> graph) {
        List<Vertex<Integer>> sumideros = new ArrayList<>();
        for (Vertex<Integer> vertex : graph.getAllVertices()) {
            if (graph.getOutDegree(vertex) == 0 && graph.getInDegree(vertex) > 0) {
                sumideros.add(vertex);
            }
        }
        return sumideros;
    }

    /**
     * Retorna una lista de vértices fuentes
     * Complejidad: O(|V|*|E|) donde |V| es la cantidad de vértices y |E| es la cantidad de arcos
     * @param graph grafo dirigido
     * @return lista de vértices fuentes
     */
    private static List<Vertex<Integer>> getFuentes(Graph<Integer> graph) {
        List<Vertex<Integer>> fuentes = new ArrayList<>();
        for (Vertex<Integer> vertex : graph.getAllVertices()) {
            if (graph.getInDegree(vertex) == 0 && graph.getOutDegree(vertex) > 0) {
                fuentes.add(vertex);
            }
        }
        return fuentes;
    }

    /**
     * Retorna el predecesor con menor costo de un vértice
     * Complejidad: O(|E|) donde |E| es la cantidad de arcos
     * @param vertex vértice
     * @param graph grafo dirigido
     * @return predecesor con menor costo
     */
    private static Vertex<Integer> predecesorMin(Vertex<Integer> vertex, Graph<Integer> graph) {
        Vertex<Integer> predecesorMin = null;
        int costoMin = Integer.MAX_VALUE;
        for (Vertex<Integer> predecesor : graph.getInwardEdges(vertex)) {
            if (predecesor.getCost() < costoMin) {
                predecesorMin = predecesor;
                costoMin = predecesor.getCost();
            }
        }
        return predecesorMin;
    }

    /**
     * Retorna el tamaño de la componente fuertemente conexa a la que pertenece un vértice
     * Complejidad: O(|CC|) donde |CC| es la cantidad de componentes fuertemente conexas
     * @param vertex vértice
     * @param CFC lista de conjuntos de vértices. Cada conjunto representa una componente fuertemente conexa
     * @return tamaño de la componente fuertemente conexa a la que pertenece un vértice
     */
    private static int getSizeCFC(Vertex<Integer> vertex, List<Set<Vertex<Integer>>> CFC) {
        for (Set<Vertex<Integer>> componente : CFC) {
            if (componente.contains(vertex)) {
                return componente.size();
            }
        }
        return 0;
    }
}