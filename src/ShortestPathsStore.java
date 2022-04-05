import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.HashMap;

public class ShortestPathsStore {
    private final Roadmap roadmap;
    private HashMap<Integer, EdgeToDistanceToPair> stopArraysMap;

    public ShortestPathsStore(){
        roadmap = readInFiles();
        if(roadmap != null) {
            roadmap.sortVertices();
            stopArraysMap = new HashMap<>();
        }
    }

    /**
     * Finds the shortest path from stopFrom to stopTo
     * @param stopFrom the starting stop for shortest path
     * @param stopTo the ending stop for shortest path
     * @return a pair of the weight of the shortest path and a string of the vertices it goes through
     */
    public AbstractMap.SimpleEntry<Double, String> shortestPathFromTo(int stopFrom, int stopTo){
        //if either of the stops is not valid, return null
        if(!roadmap.containsVertex(stopFrom) || !roadmap.containsVertex(stopTo)){
            return null;
        }

        // if Dijkstra has not been run for this stopFrom before, run it
        if(!stopArraysMap.containsKey(stopFrom)){
            ShortestPath shortestPath = new ShortestPath(stopFrom, roadmap);
            EdgeToDistanceToPair edgeToDistanceToPair = new EdgeToDistanceToPair(shortestPath.edgeTo(), shortestPath.distanceTo());
            stopArraysMap.put(stopFrom, edgeToDistanceToPair);
        }

        //Construct a list of the vertices in the shortest path
        StringBuilder route = new StringBuilder();
        HashMap<Integer, WeightedDirectedEdge> edgeTo = (stopArraysMap.get(stopFrom)).edgeTo;
        WeightedDirectedEdge edge = edgeTo.get(stopTo);
        while(edge.from() != stopFrom){
            route.insert(0, "->"+edge.to());
            edge = edgeTo.get(edge.from());
        }
        route.insert(0, edge.from());

        return new AbstractMap.SimpleEntry<>((stopArraysMap.get(stopFrom)).distanceTo.get(stopTo), route.toString());
    }

    /**
     * @param key vertex to check the presence of
     * @return true if key is present, false otherwise
     */
    public boolean hasVertex(int key){
        return stopArraysMap.containsKey(key);
    }

    /**
     * @return Roadmap based on input files
     */
    private Roadmap readInFiles(){
        try {
            Roadmap roadmap = new Roadmap((int) Files.lines(Paths.get("stops.txt")).count()-1);
            BufferedReader reader = new BufferedReader(new FileReader("stops.txt"));
            String line;
            reader.readLine();

            // Get all stop_ids from stops.txt and add them as vertices to the roadmap
            while((line = reader.readLine()) != null){
                String[] parts = line.trim().split(",");
                int stop = Integer.parseInt(parts[0]);
                roadmap.addVertex(stop);
            }

            reader = new BufferedReader(new FileReader("transfers.txt"));
            reader.readLine();
            // Get all transfers from transfers.txt and add them as WeightedDirectedEdges to the roadmap
            while((line = reader.readLine()) != null){
                String[] parts = line.trim().split(",");
                int from = Integer.parseInt(parts[0]);
                int to = Integer.parseInt(parts[1]);
                double transferType = Double.parseDouble(parts[2]);
                double weight = (transferType == 0)? 2 : Double.parseDouble(parts[3])/100.0;
                WeightedDirectedEdge edge = new WeightedDirectedEdge(from, to, weight);
                roadmap.addEdge(edge);
            }


            reader = new BufferedReader(new FileReader("stop_times.txt"));
            reader.readLine(); //skip first row (has column titles)
            boolean fileOver = (line = reader.readLine()) == null; //read first line and check if it is null
            if (!fileOver) {
                String[] parts = line.trim().split(","); //split first line into segments
                int previousStopId = Integer.parseInt(parts[3]); //get first stop value
                int transferId = Integer.parseInt(parts[0]); //get first transferId
                fileOver = (line = reader.readLine()) == null; //read second line and check if null
                parts = (fileOver) ? null : line.trim().split(","); //split second line into segments

                //Get all consecutive stops from same transfers in stopTimes and add them as weighted directed edges with weight 1 to the roadmap.
                //If they already exist, they will still be added, as they will have different weights.
                while (!fileOver) {
                    while (!fileOver && Integer.parseInt(parts[0]) == transferId) { //while the transferId has not changed
                        int currentStopId = Integer.parseInt(parts[3]); //store new stopId value
                        //add new edge
                        WeightedDirectedEdge edge = new WeightedDirectedEdge(previousStopId, currentStopId, 1);
                        roadmap.addEdge(edge);

                        previousStopId = currentStopId; //set current stop to previous

                        //prepare next line
                        fileOver = (line = reader.readLine()) == null;
                        parts = (fileOver) ? null : line.trim().split(",");
                    }
                    //will happen once either the file is over, or the transferId has changed
                    if (!fileOver) {
                        transferId = Integer.parseInt(parts[0]);
                        previousStopId = Integer.parseInt(parts[3]); //set current stop to previous

                        //prepare next line
                        fileOver = (line = reader.readLine()) == null;
                        parts = (fileOver) ? null : line.trim().split(",");
                    }
                }
            }

            return roadmap;
        }
        catch(Exception e) {
            return null;
        }
    }

    /*
        Class for storing the edgeTo and distanceTo hashmaps which are returned by dijkstra.
        Included so that the hashmap only needs to be looked up once.
     */
    private class EdgeToDistanceToPair {
        public HashMap<Integer, WeightedDirectedEdge> edgeTo;
        public HashMap<Integer, Double> distanceTo;

        public EdgeToDistanceToPair(HashMap<Integer, WeightedDirectedEdge> edgeTo, HashMap<Integer, Double> distanceTo){
            this.edgeTo = edgeTo;
            this.distanceTo = distanceTo;
        }
    }
}
