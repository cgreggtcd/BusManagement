import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    /**
     * @return number of vertices in roadmap
     */
    public int V(){
        return V;
    }

    /**
     * @return number of edges in roadmap
     */
    public int E(){
        return E;
    }

    /**
     * Add vertex v to roadmap
     * @param v vertex to be added
     */
    public void addVertex(int v){
        vertices[vertexIndex] = v;
        vertexIndex++;
        adj.put(v, null);
    }

    /**
     * Add edge to roadmap
     * @param e edge to be added
     */
    public void addEdge(WeightedDirectedEdge e){
        if(!adj.containsKey(e.from())|| !adj.containsKey(e.to())){ //if the stopFrom or stopTo is not valid, return
            return;
        }
        if(adj.get(e.from())==null){ //if this is the first edge from the vertex from, make a new arraylist
            ArrayList<WeightedDirectedEdge> adjacentEdges = new ArrayList<>();
            adj.put(e.from(), adjacentEdges);
        }
        (adj.get(e.from())).add(e);
        edges.add(e);
        E++;
    }

    /**
     * Get the edges adjacent (incident) to vertex v.
     * @param v vertex to check adjacent edges of
     * @return edges adjacent to v.
     */
    public Iterable<WeightedDirectedEdge> adj(int v){
        return adj.get(v);
    }

    /**
     * @return Iterable of all edges in the roadmap.
     */
    public Iterable<WeightedDirectedEdge> edges() {
        return edges;
    }

    /**
     * @return an array of all vertices in the roadmap
     */
    public int[] vertices() {
        return vertices;
    }

    /**
     * Sort the vertices
     */
    public void sortVertices() {
        Quicksort.quickSort(vertices);
    }

    /**
     * Check if the roadmap contains a vertex
     * @param vertex vertex to check presence
     * @return true if vertex is present, false otherwise
     */
    public boolean containsVertex(int vertex){
        return (Arrays.binarySearch(vertices, vertex) > -1);
    }

}
