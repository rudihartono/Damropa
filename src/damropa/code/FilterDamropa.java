package damropa.code;

import google.staticmap.Coordinate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by rudihartono on 23/12/2014.
 */
public class FilterDamropa {
    private ThreeParameters t;
    private ArrayList<RawDataDamropa> rawdata, filteredbySpeed;
    private ArrayList<RawDataProcessDamropa> filteredData;
    private ArrayList<RoadAnomalyDamropa> finalData;
    private final double SPEED = 5;
    private int length;

    public FilterDamropa(){
    }

    public void setParameter(double tz, double tx, double ts){
        t =  new ThreeParameters();
        this.t.setParameter(tz,tx,ts);
    }
    public void setLength(int length){
        this.length = length;
    }
    public int getLength(){return this.length;}
    public ThreeParameters getParameter(){
        return this.t;
    }
    public ArrayList<RawDataDamropa> getRawdata(){
        return this.rawdata;
    }
    public ArrayList<RawDataDamropa> getRawDataSecondPhase(){
        return this.filteredbySpeed;
    }
    public ArrayList<RawDataProcessDamropa> getFilteredData(){
        return this.filteredData;
    }
    public ArrayList<RoadAnomalyDamropa> getFinalData(){
        return this.finalData;
    }

    public void filter_by_speed(ArrayList<RawDataDamropa> e){
        RawDataDamropa rawDataDamropa;
        filteredbySpeed = new ArrayList<RawDataDamropa>();

        for(int i=0;i<e.size(); i++){
            rawDataDamropa = e.get(i);
            if(rawDataDamropa.get_speed() > SPEED){
                filteredbySpeed.add(e.get(i));
            }
        }
    }

    public void high_pass_filter(ArrayList<RawDataDamropa> e){
        filteredbySpeed = null;
        filteredbySpeed = new ArrayList<RawDataDamropa>();

        filteredbySpeed.add(new RawDataDamropa(0, 0, e.get(0).get_lat(), e.get(0).get_lng(), e.get(0).get_speed(), e.get(0).get_heading(), e.get(0).get_date()));

        for(int i = 1;i<e.size() - 1;i++){

            double xOut = (float) 0.9*filteredbySpeed.get(i-1).get_x() + 0.9*(e.get(i).get_x() - e.get(i-1).get_x());
            double  zOut = (float) 0.9*filteredbySpeed.get(i-1).get_z() + 0.9*(e.get(i).get_z() - e.get(i-1).get_z());

            //parse big decimal

            //double xIn = convertFromBigDecimal(xOut);
            //double zIn = convertFromBigDecimal(zOut);

            filteredbySpeed.add(new RawDataDamropa(xOut, zOut, rawdata.get(i).get_lat(), rawdata.get(i).get_lng(), rawdata.get(i).get_speed(), rawdata.get(i).get_heading(), rawdata.get(i).get_date()));
        }
    }

    //flter by tz or threshold of z axis
    //get z and z array into other class data
    public void filter_by_z(ArrayList<RawDataDamropa> e){
        RawDataDamropa rawDataDamropa;
        filteredData = new ArrayList<RawDataProcessDamropa>();

        for(int i=0;i<e.size()-length; i++){
            rawDataDamropa = e.get(i);
            double[] arrayX = new double[length];
            double[] arrayZ = new double[length];
            double speedSum = 0;

            if(Math.abs(rawDataDamropa.get_z()) > t.getTz()){
                int j=0;//counter for array
                int k=i;//counter for array of damropalist

                while(k < i+length){
                    if(k < e.size()){
                        arrayX[j] = e.get(k).get_x();
                        arrayZ[j] = e.get(k).get_z();
                        speedSum += e.get(k).get_speed();
                    }
                    k++;
                    j++;
                }

                double speedMean = speedSum/length;
                filteredData.add(new RawDataProcessDamropa(rawDataDamropa.get_lat(),rawDataDamropa.get_lng(),speedMean,e.get(i).get_heading(),e.get(i).get_date(),
                        arrayX,arrayZ));
                speedMean = 0;
                speedSum = 0;
                i = i+ length;
            }
        }
    }
    //filter by tx for reject police bump, expansiont joint etc
    public void filter_by_tx(ArrayList<RawDataProcessDamropa> e){
        RawDataProcessDamropa rdm;
        filteredData = null;
        filteredData = new ArrayList<RawDataProcessDamropa>();

        for(int i=0;i<e.size(); i++){
            rdm = e.get(i);
            boolean isDamageRoad = false;
            int j = 0;

            while(j < rdm.getArrayX().length && isDamageRoad == false){
                double x = e.get(i).getArrayX()[j];
                double z = e.get(i).getArrayZ()[j];

                double xzrasio = (x/z);
                if(z > t.getTz()) {
                    if (Math.abs(xzrasio) > t.getTx()) {
                        isDamageRoad = true;
                    }
                }
                j++;
            }
            if(isDamageRoad == true){
                filteredData.add(rdm);
            }
        }
    }

    public double convertFromBigDecimal(double param){
        BigDecimal value = new BigDecimal(Double.toString(param));
        value = value.setScale(7, RoundingMode.UP);

        double newValue  = Double.parseDouble(value.toString());

        return newValue;
    }

    public void filter_by_ts(ArrayList<RawDataProcessDamropa> e){
        RawDataProcessDamropa rdm;
        filteredData = null;
        filteredData = new ArrayList<RawDataProcessDamropa>();

        for(int i=0;i<e.size(); i++){
            rdm = e.get(i);
            boolean isDamageRoad = false;
            int j = 0;


            while(j<e.get(i).getArrayX().length && isDamageRoad == false){

                if(rdm.getArrayZ()[j] > t.getTz()) {
                    if (Math.abs(rdm.getArrayZ()[j]) > rdm.get_speed() * t.getTs()) {
                        isDamageRoad = true;
                    }
                }
                j++;
            }
            if(isDamageRoad == true){
                filteredData.add(rdm);
            }
        }
    }

    public void toRoadAnomali(ArrayList<RawDataProcessDamropa> e){
        finalData = new ArrayList<RoadAnomalyDamropa>();
        filteredData = null;
        rawdata = null;
        int i = 0;
        while(i < e.size()){
            finalData.add(new RoadAnomalyDamropa(new Coordinate(e.get(i).get_lat(),e.get(i).get_lng()),e.get(i).get_speed(),
                    e.get(i).get_heading(),"pothole",e.get(i).get_date()));
            i++;
        }
    }

    public void setRawdata(ArrayList<RawDataDamropa> e){
        rawdata = new ArrayList<RawDataDamropa>();
        this.rawdata = e;
    }
    public int get_size(){
        return filteredData.size();
    }
    public int getFinalDataSize(){return finalData.size();}
}
