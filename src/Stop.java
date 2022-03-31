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

    public int getId() {
        return id;
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
