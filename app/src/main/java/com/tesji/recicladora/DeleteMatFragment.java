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

public class DeleteMatFragment extends Fragment {

    View view;
    EditText txtId;
    TextView lblNom;
    TextView lblDesc;
    TextView lblPrecio;
    Button btnBuscar, btnEliminar;
    private String id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_delete_mat, container, false);

        txtId = view.findViewById(R.id.etDeleteMat);
        lblDesc = view.findViewById(R.id.tvDeleteDesd);
        lblNom = view.findViewById(R.id.tvDeleteMat);
        lblPrecio = view.findViewById(R.id.tvDeleteCost);
        btnBuscar = view.findViewById(R.id.bDeleteBuscar);
        btnEliminar = view.findViewById(R.id.bDelete);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtId.getText().toString().trim().length() != 0) {
                    id = txtId.getText().toString().trim();
                    ConexionDB conexionDB = new ConexionDB(getContext(), null, 1);
                    SQLiteDatabase sqLiteDatabase = conexionDB.getReadableDatabase();
                    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM material WHERE idMat='" + id + "';", null);
                    if (cursor.moveToFirst()) {
                        lblNom.setText(cursor.getString(1));
                        lblDesc.setText(cursor.getString(2));
                        lblPrecio.setText(cursor.getString(3));
                    }else
                        Toast.makeText(getContext(), "No existe el material", Toast.LENGTH_SHORT).show();
                    conexionDB.close();
                    sqLiteDatabase.close();
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConexionDB conexionDB = new ConexionDB(getContext(), null,1);
                SQLiteDatabase sqLiteDatabase = conexionDB.getWritableDatabase();
                sqLiteDatabase.delete("material", "idMat='"+id+"'", null);
                txtId.setText(null);
                lblNom.setText(null);
                lblDesc.setText(null);
                lblPrecio.setText(null);
                Toast.makeText(getContext(), "Eliminado ", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
