/*
    Class to store stop information. Used in BusStopSearch.
    Params:
        id, code: int
        name, desc: String
        latitude, longitude: double
 */
public class Stop {
    private final int id, code;
    private final String name, desc;
    private final double latitude, longitude;

    public Stop (int id, int code, String name, String desc, double latitude, double longitude){
        this.id = id;
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String toString() {
        return String.format("Stop name: %s, Stop desc: %s, Stop ID: %d, Stop code: %d, Stop latitude/longitude: %.6f, %.6f", name, desc, id, code, latitude, longitude);
    }
}
