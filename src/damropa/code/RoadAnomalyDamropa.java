package damropa.code;

import google.staticmap.Coordinate;

/**
 * Created by rudihartono on 23/12/2014.
 */
public class RoadAnomalyDamropa {
    private Coordinate location;
    private double speed;
    private double heading;
    private String label;
    private String date;

    public RoadAnomalyDamropa(Coordinate location, double speed, double heading, String label, String date){
        this.location = location;
        this.speed = speed;
        this.heading = heading;
        this.label = label;
        this.date = date;
    }

    public Coordinate getLocation(){
        return this.location;
    }
    public void setLocation(Coordinate coordinate){
        this.location = coordinate;
    }
    public double getSpeed(){return this.speed;}
    public double getHeading(){return this.heading;}
    public String getLabel(){return this.label;}
    public String getDate(){return this.date;}
    public void setSpeed(double speed){this.speed =speed;}
    public void setHeading(double heading){this.heading =heading;}
    public void setLabel(String label){this.label =label;}
    public void setDate(String date){this.date=date;}
}
