
public class Pruebas {
	public static void main(String[] args) {
		AdjacencyListGraph<String> graph = new AdjacencyListGraph<String>();
		String[] vertices = { "A", "A", "B", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
				"U", "V", "W", "X", "Y", "Z", "Z" };
		for (String vertex : vertices) {
			graph.add(vertex);
		}
		graph.connect("A", "B");
		graph.connect("A", "C");
		graph.connect("A", "C");
		graph.connect("C", "D");
		System.out.println("Creamos un grafo con las 26 letras del alfabeto");
		System.out.println("El núm de vertices del grafo es: " + graph.size());
		System.out.println("La representación en String del grafo es:");
		System.out.println(graph);
		System.out.println("Imprimamos los sucesores de A:");
		System.out.println(graph.getOutwardEdges("A"));
		System.out.println("Imprimamos todos los vértices del grafo:");
		System.out.println(graph.getAllVertices());
		AdjacencyListGraph<String> graph2 = new AdjacencyListGraph<String>();
		System.out.println("\nCreamos un grafo vacío");
		System.out.println("El núm de vertices del grafo es: " + graph2.size());
		System.out.println("La representación en String del grafo es:");
		System.out.println(graph2);
	}
}
