package com.tesji.recicladora;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

public class ConexionDB extends SQLiteOpenHelper {

    public ConexionDB(Context context, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "recicladora", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE material(idMat INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombreMat TEXT NOT NULL," +
                "descripcion TEXT," +
                "precio REAL NOT NULL);");
        db.execSQL("CREATE TABLE compra(idCompra INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idMat INTEGER NOT NULL,"+
                "pago DECIMAL NOT NULL, "+
                "FOREIGN KEY (idMat) REFERENCES material(idMat));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}