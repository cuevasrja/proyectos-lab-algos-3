/*
 * PruebasTest.java: Pruebas para el proyecto 1, usando assert.
 * Para ejecutarlo: java -ea PruebasTest
 * Ya que por defecto las aserciones están desactivadas, para activarlas se usa
 * la opción -ea.
 */

import java.util.ArrayList;
import java.util.Collection;

public class PruebasTest {
	public static void main(String[] args) {
		Graph<String> graph = new AdjacencyListGraph<String>();

		// Test add method
		graph.add("A");
		graph.add("B");
		graph.add("C");
		graph.add("D");
		graph.add("E");
		graph.add("F");
		graph.add("G");
		graph.add("H");
		graph.add("I");
		graph.add("J");
		graph.add("K");
		graph.add("L");
		graph.add("M");
		graph.add("N");
		graph.add("O");
		graph.add("P");
		graph.add("Q");
		graph.add("R");
		graph.add("S");
		graph.add("T");
		graph.add("U");
		graph.add("V");
		graph.add("W");
		graph.add("X");
		graph.add("Y");
		graph.add("Z");

		// Test connect method
		graph.connect("A", "B");
		graph.connect("A", "C");
		graph.connect("A", "D");
		graph.connect("B", "C");
		graph.connect("B", "D");
		graph.connect("C", "D");
		graph.connect("D", "E");
		graph.connect("E", "F");
		graph.connect("F", "G");
		graph.connect("G", "H");
		graph.connect("H", "I");
		graph.connect("I", "J");
		graph.connect("J", "K");
		graph.connect("K", "L");
		graph.connect("L", "M");
		graph.connect("M", "N");
		graph.connect("N", "O");
		graph.connect("O", "P");
		graph.connect("P", "Q");
		graph.connect("Q", "R");
		graph.connect("R", "S");
		graph.connect("S", "T");
		graph.connect("T", "U");
		graph.connect("U", "V");
		graph.connect("V", "W");
		graph.connect("W", "X");
		graph.connect("X", "Y");
		graph.connect("Y", "Z");

		// Test disconnect method
		graph.disconnect("A", "B");
		graph.disconnect("A", "C");
		graph.disconnect("A", "D");
		graph.disconnect("B", "C");
		graph.disconnect("B", "D");
		graph.disconnect("C", "D");
		graph.disconnect("D", "E");
		graph.disconnect("E", "F");
		graph.disconnect("F", "G");
		graph.disconnect("G", "H");
		graph.disconnect("H", "I");
		graph.disconnect("I", "J");
		graph.disconnect("J", "K");
		graph.disconnect("K", "L");
		graph.disconnect("L", "M");
		graph.disconnect("M", "N");
		graph.disconnect("N", "O");
		graph.disconnect("O", "P");
		graph.disconnect("P", "Q");
		graph.disconnect("Q", "R");
		graph.disconnect("R", "S");
		graph.disconnect("S", "T");
		graph.disconnect("T", "U");
		graph.disconnect("U", "V");
		graph.disconnect("V", "W");
		graph.disconnect("W", "X");
		graph.disconnect("X", "Y");
		graph.disconnect("Y", "Z");

		// Test size method
		assert graph.size() == 26;

		// Test getOutwardEdges method
		assert graph.getOutwardEdges("A").isEmpty();
		graph.connect("A", "B");
		assert graph.getOutwardEdges("A").contains("B");
		graph.connect("A", "C");
		assert graph.getOutwardEdges("A").contains("B");
		assert graph.getOutwardEdges("A").contains("C");
		graph.connect("A", "D");
		assert graph.getOutwardEdges("A").contains("B");
		assert graph.getOutwardEdges("A").contains("C");
		assert graph.getOutwardEdges("A").contains("D");

		// Test getInwardEdges method
		assert graph.getInwardEdges("A").isEmpty();
		graph.connect("B", "A");
		assert graph.getInwardEdges("A").contains("B");
		graph.connect("C", "A");
		assert graph.getInwardEdges("A").contains("B");
		assert graph.getInwardEdges("A").contains("C");
		graph.connect("D", "A");
		assert graph.getInwardEdges("A").contains("B");
		assert graph.getInwardEdges("A").contains("C");
		assert graph.getInwardEdges("A").contains("D");

		// Test getVerticesConnectedTo method
		assert graph.getVerticesConnectedTo("A").contains("B");
		assert graph.getVerticesConnectedTo("A").contains("C");
		assert graph.getVerticesConnectedTo("A").contains("D");

		// Test getAllVertices method
		Collection<String> allVertices = graph.getAllVertices();
		assert allVertices.contains("A");
		assert allVertices.contains("B");
		assert allVertices.contains("C");
		assert allVertices.contains("D");
		assert allVertices.contains("E");
		assert allVertices.contains("F");
		assert allVertices.contains("G");
		assert allVertices.contains("H");
		assert allVertices.contains("I");
		assert allVertices.contains("J");
		assert allVertices.contains("K");
		assert allVertices.contains("L");
		assert allVertices.contains("M");
		assert allVertices.contains("N");
		assert allVertices.contains("O");
		assert allVertices.contains("P");
		assert allVertices.contains("Q");
		assert allVertices.contains("R");
		assert allVertices.contains("S");
		assert allVertices.contains("T");
		assert allVertices.contains("U");
		assert allVertices.contains("V");
		assert allVertices.contains("W");
		assert allVertices.contains("X");
		assert allVertices.contains("Y");
		assert allVertices.contains("Z");

		// Test subgraph method
		Collection<String> vertices2 = new ArrayList<String>();
		vertices2.add("A");
		vertices2.add("B");
		vertices2.add("C");
		Graph<String> graph2 = graph.subgraph(vertices2);
		assert graph2.size() == 3;
		assert graph2.getOutwardEdges("A").contains("B");
		assert graph2.getOutwardEdges("A").contains("C");
		assert graph2.getInwardEdges("B").contains("A");
		assert graph2.getInwardEdges("C").contains("A");
		assert graph2.getVerticesConnectedTo("A").contains("B");
		assert graph2.getVerticesConnectedTo("A").contains("C");
		Collection<String> allVertices2 = graph2.getAllVertices();
		assert allVertices2.contains("A");
		assert allVertices2.contains("B");
		assert allVertices2.contains("C");
	}
}
