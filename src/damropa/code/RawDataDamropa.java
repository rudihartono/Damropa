package damropa.code;

import google.staticmap.Coordinate;

/**
 * Created by rudihartono on 23/12/2014.
 */
public class RawDataDamropa {
    private double x;
    private double z;
    private double lat;
    private double lng;
    private double heading;
    private double speed;
    private String date;

    public RawDataDamropa(){

    }
    public RawDataDamropa(double x, double z, double lat, double lng, double speed, double heading, String date){
        this.x = x;
        this.z = z;
        this.lat = lat;
        this.lng = lng;
        this.speed = speed;
        this.heading = heading;
        this.date = date;
    }
    public RawDataDamropa(double lat, double lng, double speed, double heading, String date){
        this.lat = lat;
        this.lng = lng;
        this.speed = speed;
        this.heading = heading;
        this.date = date;
    }
    public void set_x(double x){
        this.x = x;
    }
    public double get_x(){
        return x;
    }
    public void set_z(double z){
        this.z = z;
    }
    public double get_z(){
        return z;
    }
    public void set_lat(double lat){
        this.lat = lat;
    }
    public double get_lat(){
        return this.lat;
    }
    public void set_lng(double lng){
        this.lng = lng;
    }
    public double get_lng(){
        return this.lng;
    }
    public void set_speed(double speed){
        this.speed = speed;
    }
    public double get_speed(){
        return this.speed;
    }
    public void set_heading(double heading){
        this.heading = heading;
    }
    public double get_heading(){
        return this.heading;
    }
    public void set_date(String date){
        this.date = date;
    }
    public String get_date(){
        return this.date;
    }

    public Coordinate getPosition(){
        return new Coordinate(this.lat, this.lng);
    }
}
