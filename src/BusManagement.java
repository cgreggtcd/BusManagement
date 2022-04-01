import java.util.ArrayList;
import java.util.Arrays;

public class BusManagement {

    public static void main(String[] args) {
        ShortestPathsStore shortestPaths = new ShortestPathsStore();
        shortestPaths.shortestPathFromTo(646, 1277);

        BusStopSearch busStopSearch = new BusStopSearch();
        ArrayList<Stop> result = busStopSearch.searchString("HASTINGS ST FS HOLDOM AVE-");
        System.out.println(result.toString());

        ArrivalTimes arrivalTimes = new ArrivalTimes();
        ArrayList<TripSectionDetails> trips = arrivalTimes.searchArrivalTime("5:25:00");
        System.out.println(trips);
    }
}
