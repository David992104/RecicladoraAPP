package com.tesji.recicladora;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowMatFragment extends Fragment {

    View view;
    ListView listaMat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_mat, container, false);

        listaMat = view.findViewById(R.id.lvShowMat);

        ArrayList<String> puestos = new ArrayList<>();
        ConexionDB admin = new ConexionDB(getContext(), null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();
        Cursor fila = db.rawQuery("SELECT * FROM material", null);
        if (fila.moveToFirst()) {
            do {
                puestos.add(fila.getString(0) + "-" + fila.getString(1) + " : \n"
                        + fila.getString(2) + "\nCosto " + fila.getString(3));
            }while (fila.moveToNext());
        }
        db.close();
        admin.close();

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, puestos);
        listaMat.setAdapter(adaptador);

        return  view;
    }
}
