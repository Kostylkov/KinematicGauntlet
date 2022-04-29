package com.example.speed_meas_concurention;

import java.io.Serializable;
import java.util.Vector;

public class Calculations implements Serializable {


    private Vector<DataModel> data;
    private DataModel angles,acc,gyro,velocity,trajectory;
    public int number_of_samples;

    public Calculations(Vector<DataModel> datas, String filtr, int nos) {
        DataModel anglesc,accc,gyroc;
        data = (Vector<DataModel>) datas.clone();
        number_of_samples = nos;
        anglesc = datas.get(0);accc = datas.get(1);gyroc = datas.get(2);//clones for incoming data
        angles = new DataModel();acc = new DataModel(); gyro = new DataModel();velocity = new DataModel();trajectory = new DataModel();//variables for output data
        angles.insertData(anglesc.getDatax().get(0),"x");angles.insertData(anglesc.getDatay().get(0),"y");angles.insertData(anglesc.getDataz().get(0),"z");

        float alfa = (float)1;
        for(int i = 0;i<nos;i++) {



            if(i>0){
            switch (filtr){
                case "nofiltr":
                    angles.insertData(angles.getDatax().get(i-1)+(float)Math.toRadians((float)(gyro.getDatax().get(i-1)/nos)),"x");
                    angles.insertData(angles.getDatay().get(i-1)+(float)Math.toRadians((float)(gyro.getDatay().get(i-1)/nos)),"y");
                    angles.insertData(angles.getDataz().get(i-1)+(float)Math.toRadians((float)(gyro.getDataz().get(i-1)/nos)),"z");
                    break;
                case "Kalman":
                    break;
                case "complementary":

                    angles.insertData(alfa*(angles.getDatax().get(i-1)+(float)Math.toRadians((float)(gyro.getDatax().get(i-1)/nos))+(1-alfa)*(float)Math.atan2((float)accc.getDatay().get(i),(float)accc.getDataz().get(i))),"x");
                    angles.insertData(alfa*(angles.getDatay().get(i-1)+(float)Math.toRadians((float)(gyro.getDatay().get(i-1)/nos))-(1-alfa)*(float)Math.atan2((float)accc.getDatax().get(i),(float)Math.sqrt((float)accc.getDataz().get(i)*(float)accc.getDataz().get(i)+(float)accc.getDatay().get(i)*(float)accc.getDatay().get(i)))),"y");
                    angles.insertData(angles.getDataz().get(i-1)+(float)Math.toRadians((float)(gyro.getDataz().get(i-1)/nos)),"z");


                    break;
                default:
                    break;
            }}

            float[][] macierzobrotu = macierzObrotu(angles.getDatax().get(i),angles.getDatay().get(i),angles.getDataz().get(i));
            float[][] macierzobrotugyro = macierzObrotuGyro(angles.getDatax().get(i),angles.getDatay().get(i),angles.getDataz().get(i));
            //projecting accelerations and gyroscope data
            acc.insertData(macierzobrotu[0][0]*accc.getDatax().get(i)+macierzobrotu[0][1]*accc.getDatay().get(i)+macierzobrotu[0][2]*accc.getDataz().get(i),"x");
            acc.insertData(macierzobrotu[1][0]*accc.getDatax().get(i)+macierzobrotu[1][1]*accc.getDatay().get(i)+macierzobrotu[1][2]*accc.getDataz().get(i),"y");
            acc.insertData(macierzobrotu[2][0]*accc.getDatax().get(i)+macierzobrotu[2][1]*accc.getDatay().get(i)+macierzobrotu[2][2]*accc.getDataz().get(i)-(float)9.81,"z");


            gyro.insertData(macierzobrotugyro[0][0]*gyroc.getDatax().get(i)+macierzobrotugyro[0][1]*gyroc.getDatay().get(i)+macierzobrotugyro[0][2]*gyroc.getDataz().get(i),"x");
            gyro.insertData(macierzobrotugyro[1][0]*gyroc.getDatax().get(i)+macierzobrotugyro[1][1]*gyroc.getDatay().get(i)+macierzobrotugyro[1][2]*gyroc.getDataz().get(i),"y");
            gyro.insertData(macierzobrotugyro[2][0]*gyroc.getDatax().get(i)+macierzobrotugyro[2][1]*gyroc.getDatay().get(i)+macierzobrotugyro[2][2]*gyroc.getDataz().get(i),"z");

            //calculating velocity and trajectory
            if(i==0) {
                velocity.insertData(acc.getDatax().get(i) / nos, "x");
                velocity.insertData(acc.getDatay().get(i) / nos, "y");
                velocity.insertData(acc.getDataz().get(i) / nos, "z");
                trajectory.insertData(velocity.getDatax().get(i)/nos, "x");
                trajectory.insertData(velocity.getDatay().get(i)/nos, "y");
                trajectory.insertData(velocity.getDataz().get(i)/nos, "z");
            }else{
                velocity.insertData(acc.getDatax().get(i) / nos + velocity.getDatax().get(i-1), "x");
                velocity.insertData(acc.getDatay().get(i) / nos + velocity.getDatay().get(i-1), "y");
                velocity.insertData(acc.getDataz().get(i) / nos + velocity.getDataz().get(i-1), "z");
                trajectory.insertData(velocity.getDatax().get(i)/nos + trajectory.getDatax().get(i-1), "x");
                trajectory.insertData(velocity.getDatay().get(i)/nos + trajectory.getDatay().get(i-1), "y");
                trajectory.insertData(velocity.getDataz().get(i)/nos + trajectory.getDataz().get(i-1), "z");
            }
        }
    }

    public DataModel getAcc(){

        return acc;
    }

    public DataModel getGyro(){

        return gyro;
    }

    public DataModel getAngles(){

        return angles;
    }

    public Float getHighestVelocity(){

        float vmax = 0;
        for(int i = 0;i < number_of_samples; i++){

            //calculation of lenght of the velocity vector
            float vglob = (float)Math.sqrt((float)(velocity.getDatax().get(i)*velocity.getDatax().get(i) + velocity.getDatay().get(i)*velocity.getDatay().get(i) + velocity.getDataz().get(i)*velocity.getDataz().get(i)));

            if(vmax <= vglob)
                vmax = vglob;
            else;
        }
        return vmax;
    }

    public DataModel getVelocity(){

        return velocity;
    }

    public DataModel getTrajectory(){

        return trajectory;
    }
    private float[][] macierzObrotu(float roll, float pitch, float yaw){


            float[][] macierzobrotu = {{cos(yaw) * cos(pitch), cos(yaw) * sin(pitch) * sin(roll) - sin(yaw) * cos(roll), cos(yaw) * sin(pitch) * cos(roll) + sin(yaw) * sin(roll)},
                    {sin(yaw) * cos(pitch), sin(yaw) * sin(pitch) * sin(roll) + cos(yaw) * cos(roll), sin(yaw) * sin(pitch) * cos(roll) - cos(yaw) * sin(roll)},
                    {-sin(pitch), cos(pitch) * sin(roll), cos(pitch) * cos(roll)}};
                return macierzobrotu;

    }

    private float[][] macierzObrotuGyro(float roll, float pitch, float yaw){


        float[][] macierzobrotu = {{1, sin(pitch)*sin(roll)/cos(pitch),cos(roll)*sin(pitch)/cos(pitch)},
                                  {0, cos(roll), -sin(roll)},
                                  {0, sin(roll)/cos(pitch), cos(roll)/cos(pitch)}};



    return macierzobrotu;
    }


    private float sin(float x) {
        x = (float)Math.sin((double)x);
        return x;
    }

    private float cos(float x) {
        x = (float)Math.cos((double)x);
        return x;
    }
}
