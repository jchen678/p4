
public class Main {
    public static void main(String[] args) {
        GraphProcessor g = new GraphProcessor();
        g.populateGraph("word_list.txt");
        g.shortestPathPrecomputation();
        System.out.println(g.getShortestPath("CHARGE", "GIMLETS"));
    }
}
