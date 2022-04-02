public class TripSectionDetails implements Comparable<TripSectionDetails>{
    private final int tripId, stopId, stopSequence;
    private final String arrivalTime, departureTime;
    private final double distanceTravelled;



    public TripSectionDetails(int tripId, int stopId, int stopSequence, String arrivalTime, String departureTime, double distanceTravelled){
        this.tripId = tripId;
        this.stopId = stopId;
        this.stopSequence = stopSequence;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.distanceTravelled = distanceTravelled;
    }

    public String toString(){
        return String.format("(Trip ID: %d, Stop ID: %d, Stop sequence: %d, Arrival time: %s, Departure time: %s, " +
                "Distance travelled: %.4f)", tripId, stopId, stopSequence, arrivalTime, departureTime, distanceTravelled);
    }

    public int getTripId() {
        return tripId;
    }

    public int getStopId() {
        return stopId;
    }

    public int getStopSequence() {
        return stopSequence;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public double getDistanceTravelled() {
        return distanceTravelled;
    }

    @Override
    public int compareTo(TripSectionDetails o) {
        return Integer.compare(this.tripId, o.tripId);
    }
}
