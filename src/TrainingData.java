import damropa.code.*;
import google.staticmap.Coordinate;

import java.util.ArrayList;

/**
 * Created by rudihartono on 23/12/2014.
 */
public class TrainingData {

    public static void main(String[] args){

        //set training data
        ArrayList<RoadAnomalyDamropa> trainingData = new ArrayList<RoadAnomalyDamropa>();
        ArrayList<RoadAnomalyDamropa> rad = new ArrayList<RoadAnomalyDamropa>();

        //clustering by training data

        //add training data manual correction
        trainingData.add(new RoadAnomalyDamropa(new Coordinate(-6.86138201504946, 107.589407293126), 10.0, 2.5, "anomalies", "09/12/2014 17:03:04"));
        trainingData.add(new RoadAnomalyDamropa(new Coordinate(-6.86102377250791, 107.589472588152), 13.5, 9.89, "anomalies", "09/12/2014 17:03:18"));
        trainingData.add(new RoadAnomalyDamropa(new Coordinate(-6.86092679388821, 107.589495135471), 14.4, 5.8, "anomalies", "09/12/2014 17:03:21"));
        trainingData.add(new RoadAnomalyDamropa(new Coordinate(-6.86075555160642, 107.589528914541), 11.7, 8.7, "anomalies", "09/12/2014 17:03:27"));
        trainingData.add(new RoadAnomalyDamropa(new Coordinate(-6.8606357742101, 107.589551880956), 10.8, 11, "anomalies", "09/12/2014 17:03:32"));
        trainingData.add(new RoadAnomalyDamropa(new Coordinate(-6.86050359159708, 107.590490067378), 14.4, 88.3, "anomalies", "09/12/2014 17:04:00"));
        trainingData.add(new RoadAnomalyDamropa(new Coordinate(-6.86049294658005, 107.590598529205), 12.5, 85.4, "anomalies", "09/12/2014 17:04:03"));
        trainingData.add(new RoadAnomalyDamropa(new Coordinate(-6.86049562878907, 107.590763904154), 10.0, 92.5, "anomalies", "09/12/2014 17:04:12"));
        trainingData.add(new RoadAnomalyDamropa(new Coordinate(-6.86018197797239, 107.593233380467), 16.2, 87.1, "anomalies", "09/12/2014 17:05:25"));
        trainingData.add(new RoadAnomalyDamropa(new Coordinate(-6.86017510481179, 107.593840230256), 13.5, 97.1, "anomalies", "09/12/2014 17:05:39"));
        trainingData.add(new RoadAnomalyDamropa(new Coordinate(-6.86266419477761, 107.594023710117), 19.8, 180.7, "anomalies", "09/12/2014 17:06:40"));
        trainingData.add(new RoadAnomalyDamropa(new Coordinate(-6.86279453337193, 107.594011137262), 14.4, 182.1, "anomalies", "09/12/2014 17:06:43"));
        trainingData.add(new RoadAnomalyDamropa(new Coordinate(-6.86306065879762, 107.594124963507), 13.5, 114.5, "anomalies", "09/12/2014 17:06:52"));
        trainingData.add(new RoadAnomalyDamropa(new Coordinate(-6.86350674368441, 107.594297965989), 12.6, 200, "anomalies", "09/12/2014 17:07:09"));
        trainingData.add(new RoadAnomalyDamropa(new Coordinate(-6.86356013640761, 107.594262594357), 7.2, 202.2, "anomalies", "09/12/2014 17:07:14"));
        trainingData.add(new RoadAnomalyDamropa(new Coordinate(-6.86368762515485, 107.594192605466), 9.72, 210.4, "anomalies", "09/12/2014 17:07:20"));

        //variable penghitung
        ClusterLocationDamropa cl = new ClusterLocationDamropa();
        //double jarak1 = cl.getDistance(rad.get(0).getLocation(),trainingData.get(0).getLocation());

        //rumus score = corr - incorr2
        int j = 0;
        int corr = 0;
        int incorr = 0;
        int score = 0;

        ThreeParameters[] hpp = new ThreeParameters[10000];
        ReaderCSVDamropa rcd = new ReaderCSVDamropa("/Users/rudihartono/IdeaProjects/readCSV/file/JalanUpi/jalanfpmipagerbang2.csv", ";");
        rcd.read();
        ArrayList<RawDataDamropa> data;
        data = rcd.get_data();
        FilterDamropa filter = new FilterDamropa();
        filter.setLength(100);
        filter.setRawdata(data);
        filter.filter_by_speed(data);
        filter.high_pass_filter(filter.getRawDataSecondPhase());

        double tz = 0.2;
        double tx = 0.51233;
        double ts = 0;
        int k = 0;
        boolean pause = false;

        //data training
        //while(k < hpp.length && pause == false){
            hpp[k] = new ThreeParameters(tz, tx, ts);

            filter.setParameter(hpp[k].getTz(), hpp[k].getTx(), hpp[k].getTs());

            filter.filter_by_z(filter.getRawDataSecondPhase());

            System.out.println("after filtered by tz: " + filter.getFilteredData().size());

            filter.filter_by_tx(filter.getFilteredData());

            System.out.println("after filtered by tx : " + filter.getFilteredData().size());

            filter.filter_by_ts(filter.getFilteredData());
            System.out.println("after filtered by ts : " + filter.getFilteredData().size());

            //fase label
            filter.toRoadAnomali(filter.getFilteredData());
            rad = filter.getFinalData();
            corr = 0;
            incorr = 0;
            score = 0;
            j=0;
            while (j < rad.size()) {
                boolean isMatches = false;
                int i = 0;
                while(i<trainingData.size() && isMatches == false){
                    if (cl.getDistance(rad.get(j).getLocation(), trainingData.get(i).getLocation()) <= 20.0) {
                        System.out.println(cl.getDistance(rad.get(j).getLocation(), trainingData.get(i).getLocation()));
                        corr = corr + 1;
                        isMatches = true;
                    }
                    i++;
                }
                j++;
            }

            incorr = Math.abs(rad.size() - (16 - corr));

            System.out.println("Terdeteksi sebagai anomalies : " + rad.size() +" corr : "+ corr + " incorr : " + incorr);

            score = corr - (incorr*incorr);

            hpp[k].setCorr(corr);
            hpp[k].setIncorr(incorr);
            hpp[k].setScore(score);

            tx = tx + 0.0002;
            k++;
            if(score < 1){
                pause = false;
            }

       // }

        //searching for high score
        double max = -999;
        int index = 0;

        for(int i=0;i<k-1;i++){
            if(max < hpp[i].getScore()){
                System.out.println(hpp[i].getScore());
                max = hpp[i].getScore();
                index = i;
            }
        }
        System.out.println(hpp[index].getScore()+" tz "+ hpp[index].getTz()+" tx "+hpp[index].getTx()+" tz "+ rcd.convertFromBigDecimal(hpp[index].getTs(),4));

        System.out.println(hpp[index].getCorr()  +" incorr : "+hpp[index].getIncorr());

        double angka = rcd.convertFromBigDecimal(hpp[index].getTs(),10);
        System.out.println(angka);

        //print location
        rcd.printLocation(rad);
        System.out.println("=====");
        rcd.printLocation(trainingData);
    }
}
