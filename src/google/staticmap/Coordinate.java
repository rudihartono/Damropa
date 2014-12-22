package google.staticmap;

import java.text.DecimalFormat;

/**
 * Created by rudihartono on 03/12/2014.
 */
public class Coordinate {
    public static final DecimalFormat COORDINATE_FORMAT = new DecimalFormat("0.000000");

    private double latitude;
    private double longitude;
    public Coordinate(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude(){
        return this.latitude;
    }
    public double getLongitude(){
        return this.longitude;
    }
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }
    public String toString(){
        StringBuilder builder = new StringBuilder(21);
        builder.append(COORDINATE_FORMAT.format(this.latitude));
        builder.append(",");
        builder.append(COORDINATE_FORMAT.format(this.longitude));

        return builder.toString();
    }
}