import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

//For part 3 of assignment
public class ArrivalTimes {
    TST<ArrayList<TripSectionDetails>> arrivalsTree;

    public ArrivalTimes(){
        readInFile();
    }

    //Read in input file
    private void readInFile(){
        try {
            arrivalsTree = new TST<>(); //initialise the TST

            BufferedReader reader = new BufferedReader(new FileReader("stop_times.txt"));
            String line;
            reader.readLine();
            String[] parts;

            while((line = reader.readLine()) != null){ //loop until it runs out of input

                parts = line.trim().split(","); //split into seperate pieces of data

                //split arrival time into sections to check it is a valid time
                parts[1] = parts[1].trim();
                String[] partsOfArrivalTime = parts[1].trim().split(":");
                if(!(Integer.parseInt(partsOfArrivalTime[0]) > 23 || Integer.parseInt(partsOfArrivalTime[1]) > 59
                        || Integer.parseInt(partsOfArrivalTime[2]) > 59)) {

                    //make new trip object
                    TripSectionDetails trip = new TripSectionDetails(Integer.parseInt(parts[0]), Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4]), parts[1], parts[2], ((parts.length > 8)? Double.parseDouble(parts[8]) : 0.0));

                    if (!arrivalsTree.contains(parts[1])) { //if the arrivalsTree does not contain that arrivalTime, add it
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

    /**
     *  Allows user to search for all trips which arrive at an inputted arrival time.
     *  Parameter:
     *      time: String - arrival time being searched for
     *  Return:
     *      ArrayList<TripSectionDetails> - sorted list of trips which arrive at that time.
     */
    public ArrayList<TripSectionDetails> searchArrivalTime(String time){
        ArrayList<TripSectionDetails> result = arrivalsTree.get(time);
        return (result == null)? new ArrayList<>() : InsertionSort.sort(result);
    }
}
