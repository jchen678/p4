
public class Main {
    public static void main(String[] args) {
        GraphProcessor g = new GraphProcessor();
        
        g.populateGraph("text.txt");
        g.shortestPathPrecomputation();
        System.out.println(g.getShortestPath("LIT", "KITE"));
    }
}
