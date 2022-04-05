/*
    Weighted directed edge class used for Roadmap
 */
public class WeightedDirectedEdge {
    final private int v,w;
    final private double weight;
    public WeightedDirectedEdge(int v, int w, double weight){
        this.v = v;
        this.w = w;
        this.weight = weight;
    }
    double weight(){
        return weight;
    }
    int from(){
        return v;
    }
    int to(){
        return w;
    }
}
