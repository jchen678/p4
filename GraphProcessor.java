import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    private int[][] distanceMatrix; //stores shortest distance of vertices in format [origin vertex][end vertex] with the value being the distance
    private String[][] predMatrix; //stores predecessor information for vertices, in same format as above, but value instead indicates next node to find shortest path
    private ArrayList<String> vertices = new ArrayList<String>(); //stores all vertices of graph
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

        //for each item in the stream, add a vertex to graph and check all previous vertices for adjacency (1 letter change) 
        fileStream.forEach(s -> {
            graph.addVertex(s);
            for (String str : graph.getAllVertices()) {
                if (WordProcessor.isAdjacent(str, s)) { //check of current string and strings already in graph are similar
                    if (!str.equals(s)) { //ignore same string
                        graph.addEdge(str, s);
                    }
                }
            }
        });
        vertices = (ArrayList<String>)graph.getAllVertices();
        shortestPathPrecomputation();
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
        int reverse = 0; //check wether to reverse path afterwards
        
        //reverse words if word1 has a higher index, due to bottom half of matrix only working for predecessors
        if (vertices.indexOf(word1) < vertices.indexOf(word2)) {
            String temp = word2;
            word2 = word1;
            word1 = temp;
            reverse = 1;
        }
        
        //follows predecessor matrix to trace shortest path
        //System.out.println("word1 index: " + vertices.indexOf(word1) + " word2 index: " + vertices.indexOf(word2));
        if (predMatrix[vertices.indexOf(word1)][vertices.indexOf(word2)] == null) {
            System.out.println("initial was null");
            return list;
        }
        list.add(word1);
        while (!(predMatrix[vertices.indexOf(word1)][vertices.indexOf(word2)].equals(word2))) {
            //System.out.println("checking: " + word1 + " to: " + word2);
            list.add(predMatrix[vertices.indexOf(word1)][vertices.indexOf(word2)]);
            word1 = predMatrix[vertices.indexOf(word1)][vertices.indexOf(word2)];
        }
        //System.out.println("checking: " + word1 + " to: " + word2);
        
        
        list.add(word2);

        if (reverse == 1) {
            Collections.reverse(list);
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
        //gets integer distance from distance matrix
        return distanceMatrix[vertices.indexOf(word1)][vertices.indexOf(word2)]; 
    }

    /**
     * Computes shortest paths and distances between all possible pairs of vertices.
     * This method is called after every set of updates in the graph to recompute the path information.
     * Any shortest path algorithm can be used (Djikstra's or Floyd-Warshall recommended).
     */
    public void shortestPathPrecomputation() {
        
        //sets up predecessor matrix by filling collumns with corresponding vertex (determined through index in vertices arraylist)
        //sets up distance matrix by checking for 1 length adjacencies and placing a 1 in corresponding index of matrix, else set to deafult of Integer.MAX_VALUE
        distanceMatrix = new int[vertices.size()][vertices.size()];
        predMatrix = new String[vertices.size()][vertices.size()];

        for (int i = 0; i < vertices.size(); i++) {
            for (int j = i; j < vertices.size(); j++) {
                if (i == j) {
                    distanceMatrix[i][j] = 0;
                    predMatrix[i][j] = null;
                }
                else if(graph.isAdjacent(vertices.get(i), vertices.get(j))) {
                    distanceMatrix[i][j] = distanceMatrix[j][i] = 1;
                    predMatrix[i][j] = vertices.get(j);
                    predMatrix[j][i] = vertices.get(i);
                }
                else {
                    distanceMatrix[i][j] = distanceMatrix[j][i] = Integer.MAX_VALUE;
                }
            }
        }
        
//        for (int i = 0; i < vertices.size(); i++) {
//            for (int j = 0; j < vertices.size(); j++) {
//                System.out.print(distanceMatrix[i][j] + "\t");
//            }
//            System.out.println();
//        }
            

        
        //implements floyd-warshal for shortest path and distance of a undirected graph
        for (int k = 0; k < vertices.size(); k++) {
            int[][] nextDistanceMatrix = new int[vertices.size()][vertices.size()];
            String[][] nextPredMatrix = new String[vertices.size()][vertices.size()];
            for (int i = 0; i < vertices.size(); i++) {
                for (int j = 0; j < vertices.size(); j++) {
                    if (i != k && j != k && j != i) {
                        if (distanceMatrix[i][k] != Integer.MAX_VALUE && distanceMatrix[k][j] != Integer.MAX_VALUE) {
                            if (distanceMatrix[i][j] > distanceMatrix[i][k] + distanceMatrix[k][j]) {
                                nextDistanceMatrix[i][j] = nextDistanceMatrix[j][i] = distanceMatrix[i][k] + distanceMatrix[k][j];
                                nextPredMatrix[i][j] = predMatrix[i][k];
                            }
                            else {
                                nextDistanceMatrix[i][j] = distanceMatrix[i][j];
                                nextPredMatrix[i][j] = predMatrix[i][j];
                            }
                        } else {
                            nextDistanceMatrix[i][j] = distanceMatrix[i][j];
                            nextPredMatrix[i][j] = predMatrix[i][j];
                        }
                    }else {
                        nextDistanceMatrix[i][j]= distanceMatrix[i][j];
                        nextPredMatrix[i][j] = predMatrix[i][j];
                    }
                }
            }
            distanceMatrix = nextDistanceMatrix;
            predMatrix = nextPredMatrix;

        }
        
//        for (int i = 0; i < vertices.size(); i++) {
//            for (int j = 0; j < vertices.size(); j++) {
//                System.out.print(distanceMatrix[i][j] + "\t");
//            }
//            System.out.println();
//        }

    }
}























