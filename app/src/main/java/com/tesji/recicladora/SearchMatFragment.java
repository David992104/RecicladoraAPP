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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchMatFragment extends Fragment {

    View view;
    EditText txtId;
    TextView lblNom;
    TextView lblDesc;
    TextView lblPrecio;
    Button btnBuscar, btnLimpiar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find_mat, container, false);

        txtId = view.findViewById(R.id.etSearchMat);
        lblDesc = view.findViewById(R.id.tvSearchDesd);
        lblNom = view.findViewById(R.id.tvSearchMat);
        lblPrecio = view.findViewById(R.id.tvSearchCost);
        btnBuscar = view.findViewById(R.id.bSearchBuscar);
        btnLimpiar = view.findViewById(R.id.bSearchLimpiar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtId.getText().toString().trim().length() != 0) {
                    String id = txtId.getText().toString().trim();
                    ConexionDB conexionDB = new ConexionDB(getContext(), null, 1);
                    SQLiteDatabase sqLiteDatabase = conexionDB.getReadableDatabase();
                    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM material WHERE idMat='" + id + "';", null);
                    if (cursor.moveToFirst()){
                        lblNom.setText(cursor.getString(1));
                        lblDesc.setText(cursor.getString(2));
                        lblPrecio.setText(cursor.getString(3));
                    }else{
                        Toast.makeText(getContext(), "No existe el material", Toast.LENGTH_SHORT).show();
                    }

                    conexionDB.close();
                    sqLiteDatabase.close();
                }
            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lblPrecio.setText(null);
                lblDesc.setText(null);
                lblNom.setText(null);
                txtId.setText(null);
            }
        });


        return view;
    }
}
