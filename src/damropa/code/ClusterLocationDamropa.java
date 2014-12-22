package damropa.code;

import google.staticmap.Coordinate;

/**
 * Created by rudihartono on 23/12/2014.
 */
public class ClusterLocationDamropa {
    private boolean isRangein;
    private Heading heading;
    private static final double r = 6378137;

    public ClusterLocationDamropa(){

    }

    private String directionTo(double p1, double p2){

        return heading.toString();
    }

    public double getDistance(Coordinate p1, Coordinate p2){
        double dLat = Math.toRadians(p2.getLatitude() - p1.getLatitude());
        double dLng = Math.toRadians(p2.getLongitude() - p1.getLongitude());
        double a  = Math.sin(dLat/2)*Math.sin(dLat/2) +
                Math.cos(Math.toRadians(p1.getLatitude())) * Math.cos(Math.toRadians(p2.getLatitude()))*
                        Math.sin(dLng / 2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = r*c;

        return d;
    }

    public double rad(double x){
        return x*(Math.PI/180);
    }
    public void showLocation(Coordinate e){
        System.out.println(e.getLatitude() +","+ e.getLongitude());
    }
}
