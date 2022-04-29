package com.example.speed_meas_concurention;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//wyświetla tabele z zawodnikami i pozwala ją poprawić
public class WidokZawodnikow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widok_zawodnikow);

        //strona z imionami
        String zawodnicyImie = tableToString(MainActivity.bazaDanych, "tabela", "imie");
        TextView widok_zawodnikow_imie = (TextView) findViewById(R.id.widokZawodnikowImie);
        widok_zawodnikow_imie.setText(zawodnicyImie);

        //strona z nazwiskami
        String zawodnicyNazwisko = tableToString(MainActivity.bazaDanych, "tabela", "nazwisko");
        TextView widok_zawodnikow_nazwisko = (TextView) findViewById(R.id.widokZawodnikowNazwisko);
        widok_zawodnikow_nazwisko.setText(zawodnicyNazwisko);


        //cofa i pozwala poprawić tabele zawodników
        Button przyciskPopraw = (Button) findViewById(R.id.przyciskPopraw);
        przyciskPopraw.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
            }
        });

        //przenosi do przebiegu konkurencji
        Button przyciskDalej = (Button) findViewById(R.id.przyciskDalej);
        przyciskDalej.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                switchActivities();

            }
        });
    }


    //zamienia tabele w string do wyświetlenia
    public static String tableToString(SQLiteDatabase db, String tableName, String columnName) {
        String tableString;
        Cursor allRows  = db.rawQuery("SELECT "+ columnName + " FROM " + tableName, null);
        tableString = cursorToString(allRows);
        return tableString;
    }

    //zamienia cursor sql w string
    @SuppressLint("Range")
    public static String cursorToString(Cursor cursor){
        String cursorString = "";
        if (cursor.moveToFirst() ){
            String[] columnNames = cursor.getColumnNames();
            do {
                for (String name: columnNames) {
                    cursorString += String.format("%s   ",
                            cursor.getString(cursor.getColumnIndex(name)));
                }
                cursorString += "\n";
            } while (cursor.moveToNext());
        }
        return cursorString;
    }
    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, PrzebiegKonkurencji.class);
        startActivity(switchActivityIntent);
    }
}