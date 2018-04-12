
public class Main {
    public static void main(String[] args) {
        Graph<String> g = new Graph<>();
        g.addVertex("asdf");
        g.addVertex("qwer");
        g.addVertex("quack");
        g.addVertex("back");
        g.addVertex("knack");
        g.addEdge("asdf", "qwer");
        
        System.out.println(g.removeVertex("asdf"));
        
        System.out.println(g);
    }
}
