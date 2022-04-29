package com.example.speed_meas_concurention;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    public DBHelper(Context context)
    {
        super(context, "bazaDanych.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table tabela(id integer primary key, imie text, nazwisko text, wynik float);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion)
    {
        db.execSQL("drop table tabela;");
        onCreate(db);
    }
}
