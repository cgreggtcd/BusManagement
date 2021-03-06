import java.util.HashMap;
import java.util.PriorityQueue;

public class ShortestPath {
    private final int stopFrom;
    private final HashMap<Integer, WeightedDirectedEdge> edgeTo;
    private final HashMap<Integer, Double> distanceTo;
    private final Roadmap roadmap;

    public ShortestPath(int stopFrom, Roadmap roadmap){
        this.stopFrom = stopFrom;
        this.roadmap = roadmap;
        this.edgeTo = new HashMap<>();
        this.distanceTo = new HashMap<>();
        dijkstra();
    }

    /**
     * @return the hash map of the edges to each vertex
     */
    public HashMap<Integer, WeightedDirectedEdge> edgeTo(){
        return edgeTo;
    }

    /**
     * @return hashmap of the lengths of the shortest paths to each vertex
     */
    public HashMap<Integer, Double> distanceTo(){
        return distanceTo;
    }

    /**
     * Runs Dijkstra's shortest path algorithm on the class' roadmap.
     */
    private void dijkstra(){
        PriorityQueue<Integer> pq = new PriorityQueue<>(roadmap.V());
        for(Integer vertex : roadmap.vertices()){
            distanceTo.put(vertex, Double.POSITIVE_INFINITY);
        }
        distanceTo.put(stopFrom, 0.0);
        pq.add(stopFrom);

        while(!pq.isEmpty()){
            int u = findMinVertex(pq);
            pq.remove(u);

            if(roadmap.adj(u) != null){ //added to deal with pendant vertices
                for(WeightedDirectedEdge e : roadmap.adj(u)){
                    double alternateRoute = distanceTo.get(u) + e.weight();
                    if (alternateRoute < distanceTo.get(e.to())){
                        distanceTo.put(e.to(), alternateRoute);
                        edgeTo.put(e.to(), e);
                        pq.add(e.to());
                    }
                }
            }
        }
    }

    /**
     * Find the vertex with the shortest distanceTo of those in pq.
     * @param pq Queue of vertices
     * @return vertex in queue with shortest distanceTo
     */
    private int findMinVertex(PriorityQueue<Integer> pq){
        int currentMinVertex = -1;
        double currentMinValue = Double.POSITIVE_INFINITY;

        for(int vertex : pq){
            if(distanceTo.get(vertex) <= currentMinValue){
                currentMinValue = distanceTo.get(vertex);
                currentMinVertex = vertex;
            }
        }
        return currentMinVertex;
    }

}
