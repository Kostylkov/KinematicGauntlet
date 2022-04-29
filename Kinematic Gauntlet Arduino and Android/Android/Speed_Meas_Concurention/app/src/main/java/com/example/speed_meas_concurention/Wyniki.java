package com.example.speed_meas_concurention;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Vector;

public class Wyniki extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wyniki);

        //strona z imionami
        String zawodnicyImie = WidokZawodnikow.tableToString(MainActivity.bazaDanych, "tabela", "imie");
        //strona z nazwiskami
        String zawodnicyNazwisko = WidokZawodnikow.tableToString(MainActivity.bazaDanych, "tabela", "nazwisko");
        //strona z wynikami
        String zawodnicyWynik = WidokZawodnikow.tableToString(MainActivity.bazaDanych, "tabela", "wynik");

        String numeracja = "";

        //wpis bazy danych do wektorów

        String wynikparser = zawodnicyWynik;
        String imieparser = zawodnicyImie;
        String nazwiskoparser = zawodnicyNazwisko;
        Vector<Double> wyniki = new Vector<>();
        Vector<String> imiona = new Vector<>();
        Vector<String> nazwiska = new Vector<>();

        zawodnicyWynik = "";
        zawodnicyImie = "";
        zawodnicyNazwisko = "";

        for(int i = 1; i<PrzebiegKonkurencji.ID;i++) {
            numeracja += "\n";
            numeracja += Integer.toString(i);

            String wynik = wynikparser.substring(0, wynikparser.indexOf("\n"));
            wyniki.add(Double.parseDouble(wynik));
            wynikparser = wynikparser.substring(wynikparser.indexOf("\n") + 1);

            String imie = imieparser.substring(0, imieparser.indexOf("\n"));
            imiona.add(imie);
            imieparser = imieparser.substring(imieparser.indexOf("\n") + 1);

            String nazwisko = nazwiskoparser.substring(0, nazwiskoparser.indexOf("\n"));
            nazwiska.add(nazwisko);
            nazwiskoparser = nazwiskoparser.substring(nazwiskoparser.indexOf("\n") + 1);

        }


        bubblesort(wyniki,imiona,nazwiska,PrzebiegKonkurencji.ID - 1);

        for(int i = PrzebiegKonkurencji.ID - 2;i>=0;i--)
        {
            zawodnicyImie +="\n" + imiona.get(i);
            zawodnicyNazwisko+="\n" + nazwiska.get(i);
            zawodnicyWynik+="\n" + wyniki.get(i);
        }



        //wyświetlenie ostatecznych wyników
        TextView widok_zawodnikow_imie = (TextView) findViewById(R.id.zwyciezcyImie);
        widok_zawodnikow_imie.setText(zawodnicyImie);
        TextView widok_zawodnikow_nazwisko = (TextView) findViewById(R.id.zwyciezcyNazwisko);
        widok_zawodnikow_nazwisko.setText(zawodnicyNazwisko);
        TextView widok_zawodnikow_wynik = (TextView) findViewById(R.id.zwyciezcyWynik);
        widok_zawodnikow_wynik.setText(zawodnicyWynik);

        TextView numery = (TextView) findViewById(R.id.numeracja);
        numery.setText(numeracja);

        //przenosi na początek
        Button przyciskReset = (Button) findViewById(R.id.przyciskReset);
        przyciskReset.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                PrzebiegKonkurencji.ID = 1;
                switchActivities();
            }
        });
    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }

    private void bubblesort(Vector<Double> wyniki, Vector<String> imie, Vector<String> nazwisko, int n)
    {
        if (n == 1)                     //passes are done
        {
            return;
        }

        for (int i=0; i<n-1; i++)       //iteration through unsorted elements
        {
            if (wyniki.get(i) > wyniki.get(i+1))      //check if the elements are in order
            {                           //if not, swap them
                double temp = wyniki.get(i);
                wyniki.set(i,wyniki.get(i+1));
                wyniki.set(i+1,temp);

                String temp1 = imie.get(i);
                imie.set(i,imie.get(i+1));
                imie.set(i+1,temp1);

                String temp2 = nazwisko.get(i);
                nazwisko.set(i,nazwisko.get(i+1));
                nazwisko.set(i+1,temp2);
            }
        }

        bubblesort(wyniki,imie,nazwisko, n-1);           //one pass done, proceed to the next
    }

}
