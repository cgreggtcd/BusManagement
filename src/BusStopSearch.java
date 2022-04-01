import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class BusStopSearch {
    private TST<Stop> tst;

    public BusStopSearch(){
        buildTST();
    }

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

    private void buildTST(){
        try{
            tst = new TST<>();
            BufferedReader reader = new BufferedReader(new FileReader("stops.txt"));
            String line;
            reader.readLine();
            while((line = reader.readLine()) != null){
                String[] parts = line.trim().split(",");
                String name = parts[2];
                String prefix = name.substring(0,3);
                if(prefix.equals("WB ") || prefix.equals("NB ") || prefix.equals("SB ") || prefix.equals("EB ")){
                    StringBuilder temp = new StringBuilder(name);
                    temp.delete(0, 3);
                    temp.append(" ");
                    temp.append(prefix);
                    name = temp.toString();
                }
                Stop stop = new Stop(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), name, parts[3],
                        Double.parseDouble(parts[4]), Double.parseDouble(parts[5]));
                tst.put(name, stop);
            }

        } catch (Exception e){
            return;
        }
    }
}
