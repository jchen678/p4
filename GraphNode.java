import java.util.ArrayList;

/**
 * GraphNode that stores edge information of vertex
 * 
 * @param <E> type of a node
 * 
 * @author jchen678 (jchen678@wisc.edu)
 * 
 */
public class GraphNode<E> {
    private E data;
    private ArrayList<GraphNode<E>> edges;
    
    
    /*
     * @param   data    data of vertex
     */
    public GraphNode(E data) {
        this.data = data;
        edges = new ArrayList<GraphNode<E>>();
    }
    
    /*
     * getter for data of node
     * 
     * @return data of node
     */
    public E getData() {
        return data;
    }
    
    @Override
    public String toString() {
        return data.toString();
    }
    
    /*
     * connects node to another node
     * 
     * @param   node    node to be connected to (edge between nodes)
     */
    public void addEdge(GraphNode<E> node) {
        if (!edges.contains(node)) {
            edges.add(node);
            node.edges.add(this);
        }
        
    }
    
    /*
     * removes edge between nodes if edge exists, else do nothing
     * 
     * @param   node    node connection to be removed
     */
    public void removeEdge(GraphNode<E> node) {
        if (hasEdge(node)) {
            edges.remove(node);
            node.edges.remove(this);
        }
        
    }
    
    /*
     * remove all edges from node
     */
    public void removeAllEdges() {
        for (int i = 0; i < edges.size(); i++) {
            removeEdge(edges.get(0));
        }
    }
    
    /*
     * checks if edge between this node and given node exists
     * 
     * @param   node    node to check connection
     * @return true or false if there exists an edge between this node and another given node
     */
    public boolean hasEdge(GraphNode<E> node) {
        return edges.contains(node);
    }
    
    /*
     * gets arraylist of raw data of edges to this node
     * 
     * @return list of nodes connected to this node
     */
    public ArrayList<E> getEdges() {
        ArrayList<E> edgeList = new ArrayList<E>();
        for (GraphNode<E> node : edges) {
            edgeList.add(node.getData());
        }
        return edgeList;
    }
    
}
