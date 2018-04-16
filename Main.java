
public class Main {
    public static void main(String[] args) {
        GraphProcessor g = new GraphProcessor();
        
        g.populateGraph("text.txt");
        System.out.println(g.getShortestPath("LIT", "KITES"));
        System.out.println(g.getShortestPath("LIT", "SWAG"));
    }
}
