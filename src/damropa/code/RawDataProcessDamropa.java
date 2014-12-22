package damropa.code;

/**
 * Created by rudihartono on 23/12/2014.
 */
public class RawDataProcessDamropa extends RawDataDamropa{
    public double[] arrayX;
    public double[] arrayZ;
    public RawDataProcessDamropa(){

    }

    public RawDataProcessDamropa(double lat, double lng, double speed, double heading, String date, double[] arrayX, double[] arrayZ){
        super(lat,lng,speed,heading,date);
        this.arrayX = arrayX;
        this.arrayZ = arrayZ;
    }
    public void setArrayX(double[] arrayX){
        this.arrayX = arrayX;
    }
    public double[] getArrayX(){return this.arrayX;}
    public void setArrayZ(double []arrayZ){
        this.arrayZ = arrayZ;
    }
    public double[] getArrayZ(){return this.arrayZ;}
}
