import java.util.ArrayList;
import java.util.HashMap;

public class Roadmap {
    //Implemented using an adjacency matrix (which is implemented by a hashmap)
    private final int V;
    private int vertexIndex;
    private int E;
    private final HashMap<Integer, ArrayList<WeightedDirectedEdge>> adj;
    private final int[] vertices;
    private final ArrayList<WeightedDirectedEdge> edges;

    public Roadmap(int V){
        this.V = V;
        vertices = new int[V];
        adj = new HashMap<>();
        this.E = 0;
        this.edges = new ArrayList<>();
        vertexIndex = 0;
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }

    public void addVertex(int v){
        vertices[vertexIndex] = v;
        vertexIndex++;
        adj.put(v, null);
    }

    public boolean addEdge(WeightedDirectedEdge e){
        if(!adj.containsKey(e.from())|| !adj.containsKey(e.to())){
            return false;
        }
        if(adj.get(e.from())==null){
            ArrayList<WeightedDirectedEdge> adjacentEdges = new ArrayList<>();
            adj.put(e.from(), adjacentEdges);
        }
        (adj.get(e.from())).add(e);
        edges.add(e);
        E++;

        return true;
    }

    public Iterable<WeightedDirectedEdge> adj(int v){
        return adj.get(v);
    }

    public Iterable<WeightedDirectedEdge> edges() {
        return edges;
    }

    public int[] vertices() {
        return vertices;
    }

}
