package com.example.speed_meas_concurention;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Connectivity
{

    //String get esp32's adress
    public static String url_esp32 = "192.168.43.200";//192.168.43.151//43.141

    //method responsible for building okhttp3 client
    public static String geturl(String url_esp32)
    {

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();



        Request request = new Request.Builder()
                .url(url_esp32)
                .build();

        try
        {

            Response response = client.newCall(request).execute();
            return response.body().string();

        }

        catch(IOException error)

        {

            String e = "error";
            return e;

        }

    }

}
