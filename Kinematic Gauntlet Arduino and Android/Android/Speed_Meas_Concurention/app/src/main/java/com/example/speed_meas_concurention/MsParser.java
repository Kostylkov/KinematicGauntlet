package com.example.speed_meas_concurention;

import java.util.Vector;

//class responsible for decoding messages from esp32
public class MsParser {

    DataModel angles, gyro, acc;
    Vector<DataModel> dm;
    private int number_of_samples;

    public MsParser(){

        angles = new DataModel();
        gyro = new DataModel();
        acc = new DataModel();
        dm = new Vector<>();

    }

    public Vector<DataModel> parser(String message){

        String substring;
        substring = message.substring(message.indexOf("r"));

        //base orientation of fist
        angles.insertData(Float.valueOf(substring.substring(1,substring.indexOf("p"))),"x");
        substring = substring.substring(substring.indexOf("p"));
        angles.insertData(Float.valueOf(substring.substring(1,substring.indexOf("y"))),"y");
        substring = substring.substring(substring.indexOf("y"));
        angles.insertData(Float.valueOf(substring.substring(1,substring.indexOf("ax"))),"z");
        substring = substring.substring(substring.indexOf("ax"));


        while(substring.indexOf("E")!=0){

            acc.insertData(Float.valueOf(substring.substring(2,substring.indexOf("ay"))),"x");
            substring = substring.substring(substring.indexOf("ay"));
            acc.insertData(Float.valueOf(substring.substring(2,substring.indexOf("az"))),"y");
            substring = substring.substring(substring.indexOf("az"));
            acc.insertData(Float.valueOf(substring.substring(2,substring.indexOf("gx"))),"z");
            substring = substring.substring(substring.indexOf("gx"));
            gyro.insertData(Float.valueOf(substring.substring(2,substring.indexOf("gy"))),"x");
            substring = substring.substring(substring.indexOf("gy"));
            gyro.insertData(Float.valueOf(substring.substring(2,substring.indexOf("gz"))),"y");
            substring = substring.substring(substring.indexOf("gz"));


            if(substring.indexOf("ax")>=0) {
                gyro.insertData(Float.valueOf(substring.substring(2, substring.indexOf("ax"))), "z");
                substring = substring.substring(substring.indexOf("ax"));
            }
            else {
                gyro.insertData(Float.valueOf(substring.substring(2, substring.indexOf("END"))), "z");
                substring = substring.substring(substring.indexOf("END"));
            }
        }
        dm.add(angles);dm.add(acc);dm.add(gyro);
        return dm;
    }

    public int getNumber_of_samples(String message){


        int i = Integer.valueOf(message.substring(message.indexOf("D")+1));
        return i;

    }
}
