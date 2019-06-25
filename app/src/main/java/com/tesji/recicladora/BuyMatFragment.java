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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;

public class BuyMatFragment extends Fragment {

    View view;

    EditText txtCantidad;
    MaterialSpinner spinnerMaterial;
    TextView labelPagar;
    TextView labelACuanto;
    Button btnComprar;
    Button btnCancelar;
    Button btnCotizar;

    private double costo;
    private double pagar;
    private String material;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_buy_mat, container, false);

        txtCantidad = view.findViewById(R.id.etBuyCuantoMat);
        labelACuanto = view.findViewById(R.id.tvBuyAcuanto);
        labelPagar = view.findViewById(R.id.tvBuyPagar);

        spinnerMaterial = view.findViewById(R.id.spinnerBuyMaterial);
        ArrayList<String> mat = new ArrayList<String>();
        final ConexionDB con = new ConexionDB(getContext(), null, 1);
        SQLiteDatabase bd = con.getReadableDatabase();
        Cursor fila = bd.rawQuery("SELECT nombreMat FROM material", null);
        if (fila.moveToFirst()) {
            do {
                mat.add(fila.getString(0));
            } while (fila.moveToNext());
        }
        bd.close();
        con.close();
        ArrayAdapter<String> matAdapeter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, mat);
        spinnerMaterial.setAdapter(matAdapeter);

        spinnerMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int valor = spinnerMaterial.getSelectedItemPosition();
                if (valor != 0) {
                    material = spinnerMaterial.getSelectedItem().toString();
                    ConexionDB conexionDB = new ConexionDB(getContext(), null, 1);
                    SQLiteDatabase sqLiteDatabase = conexionDB.getReadableDatabase();
                    Cursor file = sqLiteDatabase.rawQuery("SELECT precio FROM material WHERE nombreMat='" + material + "';", null);
                    if (file.moveToFirst()) {
                        costo = Double.parseDouble(file.getString(0));
                        labelACuanto.setText("Cuesta $" + costo);
                    }
                    conexionDB.close();
                    sqLiteDatabase.close();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCotizar = view.findViewById(R.id.bBuyCotizar);
        btnCotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtCantidad.getText().toString().trim().length() > 0){
                    pagar = costo * Double.parseDouble(txtCantidad.getText().toString().trim());
                    labelPagar.setText("Pagar $ " + pagar);
                }
            }
        });

        btnComprar = view.findViewById(R.id.bBuyComprar);
        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConexionDB conexionDB = new ConexionDB(getContext(), null, 1);
                SQLiteDatabase sqLiteDatabase = conexionDB.getWritableDatabase();
                ContentValues registre = new ContentValues();
                registre.put("idMat",obtenerId());
                registre.put("pago", pagar);
                Toast.makeText(getContext(), "Comprado", Toast.LENGTH_LONG).show();
                txtCantidad.setText(null);
                spinnerMaterial.setSelection(0);
                labelPagar.setText("Ingresa una cantidad");
                labelACuanto.setText("Selecciona material");
            }
        });

        btnCancelar = view.findViewById(R.id.bBuyCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCantidad.setText(null);
                spinnerMaterial.setSelection(0);
                labelPagar.setText("Ingresa una cantidad");
                labelACuanto.setText("Selecciona material");
            }
        });

        return view;
    }

    private String obtenerId(){
        String id = "";
        ConexionDB cone = new ConexionDB(getContext(), null, 1);
        SQLiteDatabase sql = cone.getReadableDatabase();
        Cursor file = sql.rawQuery("SELECT idMat FROM material WHERE nombreMat='"+material+"';", null);
        if (file.moveToFirst()) {
            id = file.getString(0);
            cone.close();
            sql.close();
        }
        return id;
    }

}
