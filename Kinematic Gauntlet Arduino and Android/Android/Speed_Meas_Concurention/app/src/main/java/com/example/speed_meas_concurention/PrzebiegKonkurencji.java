package com.example.speed_meas_concurention;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

public class PrzebiegKonkurencji extends AppCompatActivity {

    //licznik zwodników
    public static int ID = 1;
    private int LastID = 1;
    private Vector<DataModel> datas;
    private Calculations calc;

    TextView wynik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przebieg_konkurencji);
        datas = new Vector<DataModel>();
        Cursor idcheck = MainActivity.bazaDanych.rawQuery("SELECT Id FROM tabela", null);
        idcheck.moveToLast();
        LastID = idcheck.getPosition()+1;
        idcheck.close();

        przebieg_zawodow();

        //wysyła wiadomość do rękawicy o rozpoczęciu pomiaru
        Button przyciskCios = (Button) findViewById(R.id.przyciskCios);
        przyciskCios.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                request_to_url("cios");

            }
        });

        Button przyciskcheck = (Button) findViewById(R.id.check);
        przyciskcheck.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v)
            {
                Intent intent = new Intent(PrzebiegKonkurencji.this,Check.class);
                intent.putExtra("Calculations", calc);
                startActivity(intent);
            }
        });

        //przenosi do następnego zawodnika
        Button przycisknastepny = (Button) findViewById(R.id.przyciskNastepny);
        przycisknastepny.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(wynik == null)
                    Toast.makeText(PrzebiegKonkurencji.this, "Brak ciosu", Toast.LENGTH_LONG).show();
                else
                {
                    wynik.setText("");
                    ID++;
                    if (ID > LastID) {
                        switchActivities();
                    } else
                        przebieg_zawodow();
                }
            }
        });


    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, Wyniki.class);
        startActivity(switchActivityIntent);
    }


    //wyświetla kolejnych zawodników
    @SuppressLint("Range")
    private void przebieg_zawodow()
    {

        String imie_i_nazwisko = "";
        Cursor cursorr  = MainActivity.bazaDanych.rawQuery("SELECT imie,nazwisko FROM tabela WHERE id = "+ID, null);
        cursorr.moveToFirst();
        imie_i_nazwisko = cursorr.getString(0);
        imie_i_nazwisko += " ";
        cursorr.moveToFirst();
        imie_i_nazwisko += cursorr.getString(1);
        TextView imienazwisko = (TextView) findViewById(R.id.zawodnik);
        imienazwisko.setText(imie_i_nazwisko);
        cursorr.close();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //metoda odpowiedzialna za wysylanie zapytania url
    public void request_to_url(String command)
    {

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkinfo = connMgr.getActiveNetworkInfo();

        if(networkinfo !=null && networkinfo.isConnected())
        {

            new PrzebiegKonkurencji.request_data().execute("http://" + Connectivity.url_esp32 + "/" + command);//192.168.43.151//43.141

        }

        else
        {

            Toast.makeText(PrzebiegKonkurencji.this, "Not connected  ", Toast.LENGTH_LONG).show();

        }

    }

    //metoda odpowiedzialna za format odebranych danych
    private class request_data extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... url)
        {

            return com.example.speed_meas_concurention.Connectivity.geturl(url[0]);

        }
        @Override
        protected void onPostExecute(String result_data)
        {

            if(result_data !=null)
            {



                wynik = (TextView)findViewById(R.id.wynik);
                MsParser ms = new MsParser();
                datas = ms.parser(result_data);
                int nos = ms.getNumber_of_samples(result_data);
                calc = new Calculations(datas,"complementary",nos);
                float vmax = calc.getHighestVelocity();
                wynik.setText(String.valueOf(vmax));
                if(result_data != "error") {
                    String sqlstring = "Update tabela Set wynik = " + vmax + " Where Id = " + ID;
                    MainActivity.bazaDanych.execSQL(sqlstring);
                }
                else
                    Toast.makeText(PrzebiegKonkurencji.this, "ERROR", Toast.LENGTH_LONG).show();

            }
            else
            {

                Toast.makeText(PrzebiegKonkurencji.this, "Null data", Toast.LENGTH_LONG).show();

            }

        }

    }

}