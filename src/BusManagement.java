import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class BusManagement {
    private static Roadmap readInFiles(){
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

    public static void main(String[] args) {
        Roadmap test = readInFiles();
        if(test == null){
            System.out.println("Null");
        } else {
            System.out.print("Vertices: \n");
            for (int vertex : test.vertices()) {
                System.out.printf("%d\n", vertex);
            }
            System.out.printf("Weighted edges: %d\n", test.E());
            ArrayList<WeightedDirectedEdge> edges = (ArrayList<WeightedDirectedEdge>) test.edges();

            System.out.print("First 50 edges\n");
            //check first 50
            for (int i = 0; i < 50; i++) {
                WeightedDirectedEdge edge = edges.get(i);
                System.out.printf("%d->%d, weight=%.2f\n", edge.from(), edge.to(), edge.weight());
            }

            System.out.print("Middle 50 edges\n");
            //check last lot from transfers and first lot from stop times
            for (int i = 5080; i < 5130; i++) {
                WeightedDirectedEdge edge = edges.get(i);
                System.out.printf("%d->%d, weight=%.2f\n", edge.from(), edge.to(), edge.weight());
            }

            System.out.println("Last 50 edges");
            //check end of all edges
            for (int i = 1721490; i < 1721498; i++){
                WeightedDirectedEdge edge = edges.get(i);
                System.out.printf("%d->%d, weight=%.2f\n", edge.from(), edge.to(), edge.weight());
            }
        }
    }
}
