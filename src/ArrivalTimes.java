import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ArrivalTimes {
    TST<ArrayList<TripSectionDetails>> arrivalsTree;

    public ArrivalTimes(){
        readInFile();
    }

    private void readInFile(){
        try {
            arrivalsTree = new TST<>();
            BufferedReader reader = new BufferedReader(new FileReader("stop_times.txt"));
            String line;
            reader.readLine();
            String[] parts;
            while((line = reader.readLine()) != null){
                parts = line.trim().split(",");
                parts[1] = parts[1].trim();
                String[] partsOfArrivalTime = parts[1].trim().split(":");
                if(!(Integer.parseInt(partsOfArrivalTime[0]) > 23 || Integer.parseInt(partsOfArrivalTime[1]) > 59
                        || Integer.parseInt(partsOfArrivalTime[2]) > 59)) {
                    TripSectionDetails trip = new TripSectionDetails(Integer.parseInt(parts[0]), Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4]), parts[1], parts[2], ((parts.length > 8)? Double.parseDouble(parts[8]) : 0.0));
                    if (!arrivalsTree.contains(parts[1])) {
                        ArrayList<TripSectionDetails> temp = new ArrayList<>();
                        temp.add(trip);
                        arrivalsTree.put(parts[1], temp);
                    } else {
                        arrivalsTree.get(parts[1]).add(trip);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<TripSectionDetails> searchArrivalTime(String time){
        System.out.println("Searching for "+ time);
        ArrayList<TripSectionDetails> result = arrivalsTree.get(time);
        return (result == null)? new ArrayList<>() : result;
    }
}
