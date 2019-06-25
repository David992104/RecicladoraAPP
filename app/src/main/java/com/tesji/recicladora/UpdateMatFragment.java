package com.tesji.recicladora;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateMatFragment extends Fragment {

    View view;
    EditText txtId;
    EditText txtNom;
    EditText txtDesc;
    EditText txtPrecio;
    Button btnBuscar, btnActualizar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_update_mat, container, false);

        txtId = view.findViewById(R.id.etActMat);
        txtDesc = view.findViewById(R.id.tvActDesd);
        txtNom = view.findViewById(R.id.tvActMat);
        txtPrecio = view.findViewById(R.id.tvActCost);
        btnBuscar = view.findViewById(R.id.bActBuscar);
        btnActualizar = view.findViewById(R.id.bActualizar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtId.getText().toString().trim().length() != 0) {
                    String id = txtId.getText().toString().trim();
                    ConexionDB conexionDB = new ConexionDB(getContext(), null, 1);
                    SQLiteDatabase sqLiteDatabase = conexionDB.getReadableDatabase();
                    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM material WHERE idMat='" + id + "';", null);
                    if (cursor.moveToFirst()) {
                        txtNom.setText(cursor.getString(1));
                        txtDesc.setText(cursor.getString(2));
                        txtPrecio.setText(cursor.getString(3));
                    }else
                        Toast.makeText(getContext(), "No existe el material", Toast.LENGTH_SHORT).show();
                    conexionDB.close();
                    sqLiteDatabase.close();
                }
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConexionDB conexionDB = new ConexionDB(getContext(), null, 1);
                SQLiteDatabase sqLiteDatabase = conexionDB.getWritableDatabase();
                ContentValues registre = new ContentValues();
                registre.put("nombreMat", txtNom.getText().toString().trim());
                registre.put("descripcion", txtDesc == null ? "" : txtDesc.getText().toString().trim());
                registre.put("precio", Float.parseFloat(txtPrecio.getText().toString().trim()));

                sqLiteDatabase.insert("material", null, registre);

                txtPrecio.setText(null);
                txtDesc.setText(null);
                txtNom.setText(null);
                txtId.setText(null);

                Toast.makeText(getContext(), "Actualizado", Toast.LENGTH_LONG).show();

                sqLiteDatabase.close();
                conexionDB.close();
            }
        });

        return view;
    }
}
