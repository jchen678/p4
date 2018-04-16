
public class Main {
    public static void main(String[] args) {
        GraphProcessor g = new GraphProcessor();
        g.populateGraph("word_list.txt");
        System.out.println(g.getShortestPath("DEFINE", "SHINNY"));
    }
}
