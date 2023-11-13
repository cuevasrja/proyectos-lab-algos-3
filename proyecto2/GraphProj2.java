import java.util.List;

interface GraphProj2<T> {
	boolean add(T vertex);

	boolean connect(T from, T to);

	boolean disconnect(T from, T to);

	boolean contains(T vertex);

	boolean areConnected(T from, T to);

	List<T> getVerticesConnectedTo(T vertex);

	List<T> getAllVertices();

	boolean remove(T vertex);

	int size();
}
