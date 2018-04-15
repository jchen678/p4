import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * This class adds additional functionality to the graph as a whole.
 * 
 * Contains an instance variable, {@link #graph}, which stores information for all the vertices and edges.
 * @see #populateGraph(String)
 *  - loads a dictionary of words as vertices in the graph.
 *  - finds possible edges between all pairs of vertices and adds these edges in the graph.
 *  - returns number of vertices added as Integer.
 *  - every call to this method will add to the existing graph.
 *  - this method needs to be invoked first for other methods on shortest path computation to work.
 * @see #shortestPathPrecomputation()
 *  - applies a shortest path algorithm to precompute data structures (that store shortest path data)
 *  - the shortest path data structures are used later to 
 *    to quickly find the shortest path and distance between two vertices.
 *  - this method is called after any call to populateGraph.
 *  - It is not called again unless new graph information is added via populateGraph().
 * @see #getShortestPath(String, String)
 *  - returns a list of vertices that constitute the shortest path between two given vertices, 
 *    computed using the precomputed data structures computed as part of {@link #shortestPathPrecomputation()}.
 *  - {@link #shortestPathPrecomputation()} must have been invoked once before invoking this method.
 * @see #getShortestDistance(String, String)
 *  - returns distance (number of edges) as an Integer for the shortest path between two given vertices
 *  - this is computed using the precomputed data structures computed as part of {@link #shortestPathPrecomputation()}.
 *  - {@link #shortestPathPrecomputation()} must have been invoked once before invoking this method.
 *  
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class GraphProcessor {

    /**
     * Graph which stores the dictionary words and their associated connections
     */
    private GraphADT<String> graph;
    private Stream<String> fileStream;
    private int[][] distanceMatrix;
    private String[][] predMatrix;
    private ArrayList<String> vertices;
    /**
     * Constructor for this class. Initializes instances variables to set the starting state of the object
     */
    public GraphProcessor() {
        this.graph = new Graph<>();
    }
        
    /**
     * Builds a graph from the words in a file. Populate an internal graph, by adding words from the dictionary as vertices
     * and finding and adding the corresponding connections (edges) between 
     * existing words.
     * 
     * Reads a word from the file and adds it as a vertex to a graph.
     * Repeat for all words.
     * 
     * For all possible pairs of vertices, finds if the pair of vertices is adjacent {@link WordProcessor#isAdjacent(String, String)}
     * If a pair is adjacent, adds an undirected and unweighted edge between the pair of vertices in the graph.
     * 
     * @param filepath file path to the dictionary
     * @return Integer the number of vertices (words) added
     */
    public Integer populateGraph(String filepath) {
        try {
            fileStream = WordProcessor.getWordStream(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        fileStream.forEach(s -> {
            graph.addVertex(s);
            for (String str : graph.getAllVertices()) {
                if (WordProcessor.isAdjacent(str, s)) {
                    if (!str.equals(s)) {
                        graph.addEdge(str, s);
                    }
                }
            }
        });
        
        vertices = (ArrayList<String>)graph.getAllVertices();
        return vertices.size();
    
    }

    
    /**
     * Gets the list of words that create the shortest path between word1 and word2
     * 
     * Example: Given a dictionary,
     *             cat
     *             rat
     *             hat
     *             neat
     *             wheat
     *             kit
     *  shortest path between cat and wheat is the following list of words:
     *     [cat, hat, heat, wheat]
     * 
     * @param word1 first word
     * @param word2 second word
     * @return List<String> list of the words
     */
    public List<String> getShortestPath(String word1, String word2) {
        List<String> list = new ArrayList<>();
//        for (int i = 0; i < vertices.size(); i++) {
//            for (int j = 0; j < vertices.size(); j++) {
//                System.out.print(predMatrix[i][j] + " ");
//            }
//            System.out.println();
//        }
        list.add(word1);
        while (predMatrix[vertices.indexOf(word1)][vertices.indexOf(word2)] != word1) {
            if (predMatrix[vertices.indexOf(word1)][vertices.indexOf(word2)] == null) {
                return null;
            }
            list.add(predMatrix[vertices.indexOf(word1)][vertices.indexOf(word2)]);
            word1 = predMatrix[vertices.indexOf(word1)][vertices.indexOf(word2)];
        }
        return list;
    
    }
    
    /**
     * Gets the distance of the shortest path between word1 and word2
     * 
     * Example: Given a dictionary,
     *             cat
     *             rat
     *             hat
     *             neat
     *             wheat
     *             kit
     *  distance of the shortest path between cat and wheat, [cat, hat, heat, wheat]
     *   = 3 (the number of edges in the shortest path)
     * 
     * @param word1 first word
     * @param word2 second word
     * @return Integer distance
     */
    public Integer getShortestDistance(String word1, String word2) {
        return distanceMatrix[vertices.indexOf(word1)][vertices.indexOf(word2)];
    }
    
    /**
     * Computes shortest paths and distances between all possible pairs of vertices.
     * This method is called after every set of updates in the graph to recompute the path information.
     * Any shortest path algorithm can be used (Djikstra's or Floyd-Warshall recommended).
     */
    public void shortestPathPrecomputation() {
        distanceMatrix = new int[vertices.size()][vertices.size()];
        predMatrix = new String[vertices.size()][vertices.size()];
        
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (i == j) {
                    distanceMatrix[i][j] = 0;
                    predMatrix[i][j] = vertices.get(i);
                }
                else if(graph.isAdjacent(vertices.get(i), vertices.get(j))) {
                    distanceMatrix[i][j] = 1;
                    predMatrix[i][j] = vertices.get(j);
                }
                else {
                    distanceMatrix[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        
        
        for (int k = 0; k < vertices.size(); k++) {
            for (int i = 0; i < vertices.size(); i++) {
                for (int j = 0; j < vertices.size(); j++) {
                    if (distanceMatrix[i][k] != Integer.MAX_VALUE && distanceMatrix[k][j] != Integer.MAX_VALUE) {
                        if (distanceMatrix[i][j] > distanceMatrix[i][k] + distanceMatrix[k][j]) {
                            distanceMatrix[i][j] = distanceMatrix[i][k] + distanceMatrix[k][j];
                            predMatrix[i][j] = vertices.get(k);
                        }
                    }
                }
            }
        }
        
    }
}























