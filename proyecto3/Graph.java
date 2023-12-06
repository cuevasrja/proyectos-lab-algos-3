import java.util.List;

interface Graph<T> {
    boolean add(Vertex<T> vertexObject);
    boolean connect(Vertex<T> from, Vertex<T> to);
    boolean disconnect(Vertex<T> from, Vertex<T> to);
    boolean contains(Vertex<T> vertex);
    List<Vertex<T>> getInwardEdges(Vertex<T> to);
    List<Vertex<T>> getOutwardEdges(Vertex<T> from);
    int getInDegree(Vertex<T> vertex);
    int getOutDegree(Vertex<T> vertex);
    List<Vertex<T>> getVerticesConnectedTo(Vertex<T> vertex);
    List<Vertex<T>> getAllVertices();
    boolean remove(Vertex<T> vertex);
    int size();
}
