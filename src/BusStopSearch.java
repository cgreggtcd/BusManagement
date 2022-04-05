import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/*
    This class allows the search of all bus stops in the system by their name.
 */
public class BusStopSearch {
    private TST<Stop> tst;

    public BusStopSearch(){
        buildTST();
    }

    /**
     * Search all stops for the input string.
     * @param search string which is being searched for.
     * @return ArrayList of stops which contain the search parameter.
     */
    public ArrayList<Stop> searchString(String search){
        Stop result = tst.get(search);
        if(result != null){
            ArrayList<Stop> answer = new ArrayList<>();
            answer.add(result);
            return answer;
        }
        Iterable<String> keysWithPrefix = tst.keysWithPrefix(search);
        if(keysWithPrefix != null){
            ArrayList<Stop> answers = new ArrayList<>();
            for(String key: keysWithPrefix){
                answers.add(tst.get(key));
            }
            return answers;
        }
        return new ArrayList<>();
    }

    // Reads in file and builds the TST of the stop names.
    private void buildTST(){
        try{
            tst = new TST<>();
            BufferedReader reader = new BufferedReader(new FileReader("stops.txt"));
            String line;
            reader.readLine();//skips first line

            while((line = reader.readLine()) != null){ //loops until it runs out of input
                String[] parts = line.trim().split(","); //split into pieces of data

                //move any prefixes to the end
                String name = parts[2];
                String prefix = name.substring(0,8);
                if(prefix.equals("FLAGSTOP")){
                    StringBuilder temp = new StringBuilder(name);
                    temp.delete(0, 9);
                    temp.append(" ");
                    temp.append(prefix);
                    name = temp.toString();
                }

                prefix = name.substring(0,2);
                if(prefix.equals("WB") || prefix.equals("NB") || prefix.equals("SB") || prefix.equals("EB")){
                    StringBuilder temp = new StringBuilder(name);
                    temp.delete(0, 3);
                    temp.append(" ");
                    temp.append(prefix);
                    name = temp.toString();
                }

                //add the stop
                Stop stop = new Stop(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), name, parts[3],
                        Double.parseDouble(parts[4]), Double.parseDouble(parts[5]));
                tst.put(name, stop);
            }

        } catch (Exception e){
            return;
        }
    }
}
