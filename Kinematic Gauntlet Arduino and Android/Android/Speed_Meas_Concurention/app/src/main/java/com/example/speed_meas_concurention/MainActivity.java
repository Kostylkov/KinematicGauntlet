package com.example.speed_meas_concurention;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//odpowiada za stworzenie bazy danych i zapis nowych zawodników do konkurencji
public class MainActivity extends Activity
{
    public static SQLiteDatabase bazaDanych;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper DBHelp = new DBHelper(getApplicationContext());
        bazaDanych = DBHelp.getWritableDatabase();

        //zapisuje nowych zawodników
        Button przyciskZapisz = (Button) findViewById(R.id.przyciskZapisz);
        przyciskZapisz.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0)
            {
                EditText poleId = (EditText) findViewById(R.id.zapisImie);
                EditText poleTekst = (EditText) findViewById(R.id.zapisNazwisko);
                ContentValues wartosci = new ContentValues();
                wartosci.put("imie", poleId.getText().toString());
                wartosci.put("nazwisko", poleTekst.getText().toString());
                bazaDanych.insert("tabela", null, wartosci);
                poleId.setText("");
                poleTekst.setText("");
            }
        });

        //czyści konkurencje
        Button przyciskWyczyść = (Button) findViewById(R.id.przyciskWyczyśćKonkurencje);
        przyciskWyczyść.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
            DBHelp.onUpgrade(bazaDanych,1,1);
            }
        });

        Button przyciskStart = (Button) findViewById(R.id.przyciskStart);
        przyciskStart.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
            switchActivities();
            }
        });

    }
    //intent do widok zawodników
    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, WidokZawodnikow.class);
        startActivity(switchActivityIntent);
    }


}


