package com.tesji.recicladora;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowBuysFragment extends Fragment {

    View view;
    ListView listBuys;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_buys, container, false);

        listBuys = view.findViewById(R.id.lvShowBuys);

        ArrayList<String> puestos = new ArrayList<>();
        ConexionDB admin = new ConexionDB(getContext(), null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();
        Cursor fila = db.rawQuery("SELECT * FROM compra", null);
        ConexionDB conexionDB = new ConexionDB(getContext(), null, 1);
        SQLiteDatabase sqLiteDatabase = conexionDB.getReadableDatabase();
        if (fila.moveToFirst()) {
            do {
                Cursor file = sqLiteDatabase.rawQuery("SELECT nombreMat FROM material WHERE idMat='" + fila.getString(1) + "';", null);
                if (file.moveToFirst())
                    puestos.add(fila.getString(0) + "- Material : " + file.getString(0) +
                            " Se pago : \n" + fila.getString(2));
            } while (fila.moveToNext());
        }
        db.close();
        admin.close();

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, puestos);
        listBuys.setAdapter(adaptador);

        return view;
    }
}
