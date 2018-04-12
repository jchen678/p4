import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Undirected and unweighted graph implementation
 * 
 * @param <E> type of a vertex
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class Graph<E> implements GraphADT<E> {
    
    HashMap<E, GraphNode<E>> adjList = new HashMap<>();
    ArrayList<E> vertices = new ArrayList<E>();
    int size = 0;
    /**
     * Instance variables and constructors
     */
    public Graph() {
    }
    
    @Override
    public String toString() {
        return adjList.toString();
    }
    
    /**
     * Add new vertex to the graph
     * 
     * Valid argument conditions:
     * 1. vertex should be non-null
     * 2. vertex should not already exist in the graph 
     * 
     * @param vertex the vertex to be added
     * @return vertex if vertex added, else return null if vertex can not be added (also if valid conditions are violated)
     */
    @Override
    public E addVertex(E vertex) {
        if (vertex != null && adjList.get(vertex) == null) {
            size ++;
            adjList.put(vertex, new GraphNode<E>(vertex));
            vertices.add(vertex);
            return vertex;
        }
        return null;
        
    }

    /**
     * Remove the vertex and associated edge associations from the graph
     * 
     * Valid argument conditions:
     * 1. vertex should be non-null
     * 2. vertex should exist in the graph 
     *  
     * @param vertex the vertex to be removed
     * @return vertex if vertex removed, else return null if vertex and associated edges can not be removed (also if valid conditions are violated)
     */
    @Override
    public E removeVertex(E vertex) {
        if (vertex != null && adjList.get(vertex) != null) {
            GraphNode<E> g = adjList.get(vertex);
            g.removeAllEdges();
            adjList.remove(vertex);
            vertices.remove(vertex);
            return g.getData();
        }
        return null;
    }

    /**
     * Add an edge between two vertices (edge is undirected and unweighted)
     * 
     * Valid argument conditions:
     * 1. both the vertices should exist in the graph
     * 2. vertex1 should not equal vertex2
     *  
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if edge added, else return false if edge can not be added (also if valid conditions are violated)
     */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
        if (adjList.get(vertex1) != null && adjList.get(vertex2) != null && !vertex1.equals(vertex2)) {
            adjList.get(vertex1).addEdge(adjList.get(vertex2));
            return true;
        }
        return false;
    }    

    /**
     * Remove the edge between two vertices (edge is undirected and unweighted)
     * 
     * Valid argument conditions:
     * 1. both the vertices should exist in the graph
     * 2. vertex1 should not equal vertex2
     *  
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if edge removed, else return false if edge can not be removed (also if valid conditions are violated)
     */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
        if (adjList.get(vertex1) != null && adjList.get(vertex2) != null && !vertex1.equals(vertex2)) {
            adjList.get(vertex1).removeEdge(adjList.get(vertex2));
            return true;
        }
        return false;
    }

    /**
     * Check whether the two vertices are adjacent
     * 
     * Valid argument conditions:
     * 1. both the vertices should exist in the graph
     * 2. vertex1 should not equal vertex2
     *  
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if both the vertices have an edge with each other, else return false if vertex1 and vertex2 are not connected (also if valid conditions are violated)
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
        if (adjList.get(vertex1) != null && adjList.get(vertex2) != null && !vertex1.equals(vertex2)) {
            return adjList.get(vertex1).hasEdge(adjList.get(vertex2));
        }
        return false;
    }

    /**
     * Get all the neighbor vertices of a vertex
     * 
     * Valid argument conditions:
     * 1. vertex is not null
     * 2. vertex exists
     * 
     * @param vertex the vertex
     * @return an iterable for all the immediate connected neighbor vertices
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        return (Iterable<E>)adjList.get(vertex).getEdges();
    }

    /**
     * Get all the vertices in the graph
     * 
     * @return an iterable for all the vertices
     */
    @Override
    public Iterable<E> getAllVertices() {
        return (Iterable<E>)vertices;
    }

}
