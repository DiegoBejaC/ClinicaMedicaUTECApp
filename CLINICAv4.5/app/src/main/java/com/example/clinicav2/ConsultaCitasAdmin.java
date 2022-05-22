package com.example.clinicav2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ConsultaCitasAdmin extends AppCompatActivity {
    /*TextView txtFecha, txtEstado, txtPaciente;*/
    TextView tvTitulo;
    ConstraintLayout fondo;
    String tamanioTexto;
    String backColor;

    RecyclerView idrecyclerview;
    ArrayList<CITAS> citasArrayList;
    BaseClinica objB;

    private CitasAdapter citasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultacitas);

        fondo = (ConstraintLayout)findViewById(R.id.fondoConsultas);
        tvTitulo = (TextView)findViewById(R.id.tvConsultaPant);

        idrecyclerview=findViewById(R.id.idrecyclerview);
        citasArrayList = new ArrayList<>();
        objB = new BaseClinica(this, "clinica", null, 1);
        citasAdapter = new CitasAdapter(this, citasArrayList);

        RecyclerView recyclerView=findViewById(R.id.idrecyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(citasAdapter);
        mostrarDatos();

        //En el adaptador se pone el onclik listener para poder hacer actividades con cada item
        citasAdapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Toas para hacer la prueba
               //Toast.makeText(getApplicationContext(),
                // "seleccion: "+ citasArrayList.get(recyclerView.getChildAdapterPosition(v)).getStringPaciente(), Toast.LENGTH_SHORT).show();
                AlertDialog dialog;
                //Se le coloca un título al dialogo
                builder.setTitle("Opciones Item");
                //Se le coloca la pregunta que se hará al usuario
                builder.setMessage("¿Qué desea hacer con el item "+ citasArrayList.get(recyclerView.getChildAdapterPosition(v)).getStringId() +" ?");

                builder.setPositiveButton("Borrar Item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String id = citasArrayList.get(recyclerView.getChildAdapterPosition(v)).getStringId();
                        BorrarItem(id);

                        //Toast para hacer la prueba --ignorar
                        /*Toast.makeText(getApplicationContext(), "Item borrado: "+ citasArrayList.get(recyclerView.getChildAdapterPosition(v)).getStringId(),
                         Toast.LENGTH_SHORT).show();*/
                    }
                });
                builder.setNegativeButton("Editar Item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String id = citasArrayList.get(recyclerView.getChildAdapterPosition(v)).getStringId();

                        Intent editarItem = new Intent(ConsultaCitasAdmin.this, EditarCita.class);
                        editarItem.putExtra("idEnviado", id);
                        startActivity(editarItem);

                        //Toast para hacer la prueba --ignorar
                        /*Toast.makeText(parent.getContext(), "funciona el editar item. id: "+id+ " "
                                +parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();*/
                    }
                });
                //Se configurará el botón Cancelar
                builder.setNeutralButton("Cancelar", null);

                //Se crea el cuadro de dialogo con todos los elementos de Builder.
                dialog = builder.create();
                //Se muestra el cuadro de dialogo
                dialog.show();
            }
        });

        //Todo el siguiente codigo comentado es para cuando solo necesitamos el dato nada mas de 1 fila y no de una lista entera solo es ilustrativo.
        //tambien para que funcione, necesita que el layout que esta en "item_consultarcitas" esté en el "activity:consultacitas"
        /*txtFecha = (TextView)findViewById(R.id.TxtFechaHora);
        txtEstado = (TextView)findViewById(R.id.TxtEstado);
        txtPaciente = (TextView)findViewById(R.id.TxtNombre);

        String stringPaciente = "0";
        String stringFecha = "0";
        String stringEstado = "0";
        int intEstado = 0;

        BaseClinica objB = new BaseClinica(this, "clinica", null, 1);
        SQLiteDatabase bEstado = objB.getWritableDatabase();

        String consultaSql = "select * from citas order by idcita DESC";

        Cursor cursor = bEstado.rawQuery(consultaSql, null);

        if(cursor.moveToFirst()){
            do{
                stringPaciente = cursor.getString(1);
                stringFecha = cursor.getString(4);
                intEstado = cursor.getInt(7);
            }while (cursor.moveToNext());
        }
        bEstado.close(); //se cierra la conexion

        if(intEstado == 1){
            stringEstado = "Agendado";
        }else if(intEstado == 2){
            stringEstado = "Realizado";
        }else if(intEstado == 3){
            stringEstado = "Cancelado";
        }

        txtFecha.setText(stringFecha);
        txtPaciente.setText(stringPaciente);
        txtEstado.setText(stringEstado);*/

        PreferenceManager.setDefaultValues(this,R.xml.main_preferences,false);
        loadPreference();
    }

    protected void onRestart() {
        super.onRestart();
        loadPreference();
    }

    public void loadPreference(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        tamanioTexto = pref.getString("textSize", "textSmall");
        backColor = pref.getString("backgroundColor", "light");

        switch(tamanioTexto){
            case "textSmall":
                tvTitulo.setTextSize(24);
                break;
            case "textMedium":
                tvTitulo.setTextSize(26);
                break;
            case "textLarge":
                tvTitulo.setTextSize(28);
                break;
        }

        switch(backColor){
            case "dark":
                fondo.setBackgroundColor(Color.parseColor("#550A0A"));
                tvTitulo.setTextColor(Color.WHITE);
                break;
            case "light":
                fondo.setBackgroundColor(Color.parseColor("#FFDEDE"));
                tvTitulo.setTextColor(Color.BLACK);
                break;
        }

    }

    public void mostrarDatos(){
        SQLiteDatabase bEstado = objB.getReadableDatabase();
        CITAS citas = null;
        String consultaSql = "select * from citas order by idcita DESC";

        Cursor cursor = bEstado.rawQuery(consultaSql, null);
        while (cursor.moveToNext()){
            citas = new CITAS();

            citas.setStringId(cursor.getString(0));
            citas.setStringPaciente(cursor.getString(1));
            citas.setStringFecha(cursor.getString(4));
            citas.setIntEstado(cursor.getInt(7));
            if(citas.getIntEstado()==1){
                citas.setStringEstado("Agendado");
            }else if(citas.getIntEstado()==2){
                citas.setStringEstado("Realizado");
            }else if(citas.getIntEstado()==3){
                citas.setStringEstado("Cancelado");
            }
            citasAdapter.agregarCitaAlista(citas);
        }
    }

    public void BorrarItem(String Id){

        String IdItem = Id;

        //Abrimos la base
        //Permisos de lectura y escritura
        SQLiteDatabase bEstado = objB.getReadableDatabase();
        String consultaSQL = "DELETE FROM citas WHERE idcita = '"+IdItem+"'";
        bEstado.execSQL(consultaSQL);
        //Notifica que los datos se han modificado, cualquier Vista que refleje el conjunto de datos debe actualizarse.
        citasAdapter.notifyDataSetChanged();
        //Mensaje de confirmación
        Toast.makeText(this, "Se ha eliminado el item "+IdItem,Toast.LENGTH_LONG).show();
        //Se cierra la conexión
        bEstado.close();

        Intent Regresar = new Intent(ConsultaCitasAdmin.this, ConsultaCitasAdmin.class);
        startActivity(Regresar);
        finish();
    }

}