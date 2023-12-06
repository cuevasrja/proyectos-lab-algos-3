public class Vertex<T> {
    private T id;
    private int cost;

    public Vertex(T id, int cost) {
        this.id = id;
        this.cost = cost;
    }

    public T getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public void setId(T id) {
        this.id = id;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Vertex<?>)) {
            return false;
        }
        Vertex<?> other = (Vertex<?>) obj;
        return id.equals(other.id);
    }
}
