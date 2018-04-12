import java.util.ArrayList;

public class GraphNode<E> {
    private E data;
    private ArrayList<GraphNode<E>> edges;
    
    public GraphNode(E data) {
        this.data = data;
        edges = new ArrayList<GraphNode<E>>();
    }
    
    public E getData() {
        return data;
    }
    
    @Override
    public String toString() {
        String returnString = "";
        for (GraphNode<E> n : edges) {
            returnString += "Connected to: " + n.getData().toString();
        }
        return returnString;
    }
    
    public void addEdge(GraphNode<E> node) {
        edges.add(node);
        node.edges.add(this);
    }
    
    public void removeEdge(GraphNode<E> node) {
        edges.remove(node);
        node.edges.remove(this);
    }
    
    public void removeAllEdges() {
        for (int i = 0; i < edges.size(); i++) {
            removeEdge(edges.get(0));
        }
    }
    
    public boolean hasEdge(GraphNode<E> node) {
        return edges.contains(node);
    }
    
    public ArrayList<GraphNode<E>> getEdges() {
        return edges;
    }
}
