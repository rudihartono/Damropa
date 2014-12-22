package damropa.code;

/**
 * Created by rudihartono on 23/12/2014.
 */
public class ThreeParameters {
    private double tz;
    private double tx;
    private double ts;
    private int score;
    private int incorr;
    private int corr;

    public ThreeParameters(){

    }
    public ThreeParameters(double tz, double tx, double ts){
        this.tz = tz;
        this.tx = tx;
        this.ts = ts;
    }
    public void setParameter(double tz, double tx, double ts){
        this.tz = tz;
        this.tx = tx;
        this.ts = ts;
    }
    public double getTz(){
        return tz;
    }

    public double getTx(){return tx;}

    public double getTs(){return ts;}

    public int getScore(){return score;}
    public void setScore(int score){
        this.score = score;
    }
    public double[] getArrayT(){
        double[] arrayT = new double[3];
        arrayT[0] = this.tz;
        arrayT[1] = this.tx;
        arrayT[2] = this.ts;

        return arrayT;
    }
    public void setIncorr(int incorr){
        this.incorr = incorr;
    }
    public int getIncorr(){return this.incorr;}
    public void setCorr(int corr){
        this.corr = corr;
    }
    public int getCorr(){return this.corr;}
}
