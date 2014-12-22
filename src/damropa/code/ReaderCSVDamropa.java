package damropa.code;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by rudihartono on 23/12/2014.
 */
public class ReaderCSVDamropa {
    private ArrayList<RawDataDamropa> rawDataDamropas;
    private ArrayList<RoadAnomalyDamropa> finalData;
    public String csvFile;
    private String split;

    public ReaderCSVDamropa(String csvFile, String split){
        this.csvFile = csvFile;
        this.split = split;
    }

    public void read() {
        BufferedReader br = null;
        String line = "";
        rawDataDamropas = new ArrayList<RawDataDamropa>();

        try {
            br = new BufferedReader(new FileReader(csvFile));
            int i = 0;
            while ((line = br.readLine()) != null) {

                String[] data = line.split(split);

                //lat, lng, speed, heading, x,y,z,time,date
                data[0] = data[0].replace(",", "."); //lat
                data[1] = data[1].replace(",", "."); //lng
                data[2] = data[2].replace(",", "."); //speed
                data[3] = data[3].replace(",", "."); //heading
                data[4] = data[4].replace(",", "."); //x
                data[6] = data[6].replace(",", "."); //z
                try {
                    double speed = (Double.parseDouble(data[2])*3600)/1000; //ms to kmh
                    rawDataDamropas.add(new RawDataDamropa(Double.parseDouble(data[4]), Double.parseDouble(data[6]),
                            convertFromBigDecimal(Double.parseDouble(data[0]),7),convertFromBigDecimal(Double.parseDouble(data[1]),7), speed,
                            Double.parseDouble(data[3]), data[8]));

                }catch (NumberFormatException exNumber){
                    System.out.println("File doesn't support in this system.");
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public double convertFromBigDecimal(double param, int param2){
        BigDecimal value = new BigDecimal(Double.toString(param));
        value = value.setScale(param2, RoundingMode.UP);

        double newValue  = Double.parseDouble(value.toString());

        return newValue;
    }

    public void readFinalData(){
        BufferedReader br = null;
        String line = "";
        finalData = new ArrayList<RoadAnomalyDamropa>();

        try {
            br = new BufferedReader(new FileReader(csvFile));
            int i = 0;
            while ((line = br.readLine()) != null) {

                String[] data = line.split(split);

                //lat, lng, speed, heading, x,y,z,time,date
                data[0] = data[0].replace(",", "."); //lat
                data[1] = data[1].replace(",", "."); //lng
                data[2] = data[2].replace(",", "."); //speed
                data[3] = data[3].replace(",", "."); //heading
                data[4] = data[4].replace(",", "."); //x
                data[6] = data[6].replace(",", "."); //z

                try {
                    double speed = (Double.parseDouble(data[2])*3600)/1000; //ms to kmh

                    //tambah data ke list
                    rawDataDamropas.add(new RawDataDamropa(Double.parseDouble(data[4]), Double.parseDouble(data[6]),
                            Double.parseDouble(data[0]),Double.parseDouble(data[1]), speed,
                            Double.parseDouble(data[3]), data[8]));
                }catch (NumberFormatException exNumber){
                    System.out.println("File doesn't support in this sistem.");
                }

            }

        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            System.out.println("File not found.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<RawDataDamropa> get_data(){
        return rawDataDamropas;
    }

    public int getSize(){
        return rawDataDamropas.size();
    }
    public void printLocation(ArrayList<RoadAnomalyDamropa> e){
        double lat;
        ClusterLocationDamropa cl = new ClusterLocationDamropa();
        if(e.size() > 0) {
            double speed = 0;
            double speedMean = 0;
            RoadAnomalyDamropa rad = e.get(0);
            lat = 0;
            int count=0;

            for (int i = 0; i < e.size(); i++) {
                rad = e.get(i);
                speed = speed + rad.getSpeed();

                if (lat != rad.getLocation().getLatitude()) {
                    System.out.println(rad.getLocation().getLatitude() + "," + rad.getLocation().getLongitude());
                }
                lat = rad.getLocation().getLatitude();
                count++;
            }
            speedMean = speed/e.size();

            System.out.println(count);
            System.out.println(speedMean);
        }
    }
}