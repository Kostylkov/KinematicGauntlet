package com.example.speed_meas_concurention;

import java.io.Serializable;
import java.util.Vector;

//model for collecting x,y,z data from gyro and accelerometer.
public class DataModel implements Serializable {

    private Vector<Float> datax,datay,dataz;

    public DataModel(){
        datax = new Vector<Float>();
        datay = new Vector<Float>();
        dataz = new Vector<Float>();
    }

    public Vector<Float> getDatax() {
        return datax;
    }
    public Vector<Float> getDatay() {
        return datay;
    }
    public Vector<Float> getDataz() {
        return dataz;
    }
    public void setDatax(Vector<Float> dx){
        datax = dx;
    }
    public void setDatay(Vector<Float> dy){
        datay = dy;
    }
    public void setDataz(Vector<Float> dz){
        dataz = dz;
    }
    public void insertData(Float data,String xyz){

        switch(xyz){

            case "x":
                datax.add(data);
                break;
            case "y":
                datay.add(data);
                break;
            case "z":
                dataz.add(data);
                break;

        }
    }
}
