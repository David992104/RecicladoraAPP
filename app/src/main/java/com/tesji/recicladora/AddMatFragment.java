package com.tesji.recicladora;

import android.content.ContentValues;
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
import android.widget.Toast;

public class AddMatFragment extends Fragment {

    View view;
    Button btnAgregar;
    private Button btnCancelar;
    private EditText txtNombre;
    private EditText txtDescripcion;
    private EditText txtPrecio;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_mat, container, false);

        btnAgregar = view.findViewById(R.id.bAgregarMat);
        btnCancelar = view.findViewById(R.id.bCancelarMat);
        txtNombre = view.findViewById(R.id.etAddMatNom);
        txtDescripcion = view.findViewById(R.id.etAddMatDesc);
        txtPrecio = view.findViewById(R.id.etAddMatPrecio);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNombre.getText().toString().trim().length() == 0) {
                    txtNombre.requestFocus();
                    txtNombre.setError("Ingresa un nombre");
                } else if (txtPrecio.getText().toString().trim().length() > 0) {
                    if (!(Float.parseFloat(txtPrecio.getText().toString().trim()) > 0)) {
                        txtPrecio.requestFocus();
                        txtPrecio.setError("Ingresa un valor numerico ");
                    } else {
                        ConexionDB conexionDB = new ConexionDB(getContext(), null, 1);
                        SQLiteDatabase database = conexionDB.getWritableDatabase();

                        ContentValues registre = new ContentValues();
                        registre.put("nombreMat", txtNombre.getText().toString().trim());
                        registre.put("descripcion", txtDescripcion == null ? "" : txtDescripcion.getText().toString().trim());
                        registre.put("precio", Float.parseFloat(txtPrecio.getText().toString().trim()));

                        database.insert("material", null, registre);

                        txtPrecio.setText(null);
                        txtDescripcion.setText(null);
                        txtNombre.setText(null);

                        Toast.makeText(getContext(), "Agregado", Toast.LENGTH_LONG).show();

                        conexionDB.close();
                        database.close();
                    }
                } else {
                    txtPrecio.requestFocus();
                    txtPrecio.setError("Ingresa un precio");
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtPrecio.setText(null);
                txtDescripcion.setText(null);
                txtNombre.setText(null);
            }
        });

        return view;
    }
}
