import java.util.ArrayList;

public class Pruebas {
    public static void main(String[] args){
        AdjacencyListGraph<String> graph = new AdjacencyListGraph<String>();
        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", 
                             "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                             "U", "V", "W", "X", "Y", "Z"};
        for(String vertex : vertices){
            graph.add(vertex);
        }
        System.out.println(graph.size());
    }
}