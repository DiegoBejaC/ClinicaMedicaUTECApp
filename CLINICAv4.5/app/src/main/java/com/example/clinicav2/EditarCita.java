package com.example.clinicav2;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.w3c.dom.Text;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class EditarCita extends AppCompatActivity {
    TextView idCita;
    TextView NomCita;
    TextView Estado;
    TextView Fecha, DUI, Carnet, Motivo;
    TextView LabelP, LabelE, LabelF;

    ConstraintLayout fondo;
    String tamanioTexto;
    String backColor;

    Button Guardar, Cancelar;

    @Override
    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.editar_cita);
        fondo = (ConstraintLayout)findViewById(R.id.fondoEditar);
        idCita = (TextView)findViewById(R.id.txtIdCita);
        NomCita = (TextView)findViewById(R.id.txtNomPac);
        Estado = (TextView)findViewById(R.id.txtEsta);
        Fecha = (TextView)findViewById(R.id.txtDate);
        DUI =  (TextView)findViewById(R.id.txtDUI);
        Carnet = (TextView)findViewById(R.id.txtCarnetEdit);
        Motivo = (TextView)findViewById(R.id.txtMotivoEdit);
        LabelP = (TextView)findViewById(R.id.txtLabelPaciente);
        LabelE = (TextView)findViewById(R.id.txtLabelEstado);
        LabelF = (TextView)findViewById(R.id.txtLabelFecha);
        Guardar = (Button)findViewById(R.id.btnGuardar);
        Cancelar = (Button)findViewById(R.id.btnCancel);

        String IdRecibido = getIntent().getExtras().getString("idEnviado");

        idCita.setText("Id de cita: "+ IdRecibido);

        String stringId = "";
        String stringPaciente = "0";
        String stringFecha = "0";
        String stringEstado = "0";
        String stringDui = "0";
        String stringCarnet = "0";
        String stringMotivo = "0";
        int intEstado = 0;

        BaseClinica objB = new BaseClinica(this, "clinica", null, 1);
        SQLiteDatabase bEstado = objB.getWritableDatabase();

        String consultaSql = "select * from citas where idcita = '"+IdRecibido+"'";

        Cursor cursor = bEstado.rawQuery(consultaSql, null);

        if(cursor.moveToFirst()){
            do{
                stringPaciente = cursor.getString(1);
                stringDui = cursor.getString(2);
                stringCarnet = cursor.getString(3);
                stringFecha = cursor.getString(4);
                stringMotivo = cursor.getString(6);
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

        Fecha.setText(stringFecha);
        NomCita.setText(stringPaciente);
        Estado.setText(stringEstado);
        DUI.setText(stringDui);
        Carnet.setText(stringCarnet);
        Motivo.setText(stringMotivo);

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
                idCita.setTextSize(24);
                Fecha.setTextSize(14);
                NomCita.setTextSize(14);
                Estado.setTextSize(14);
                Guardar.setTextSize(14);
                Cancelar.setTextSize(14);
                LabelP.setTextSize(14);
                LabelE.setTextSize(14);
                LabelF.setTextSize(14);
                break;
            case "textMedium":
                idCita.setTextSize(26);
                Fecha.setTextSize(18);
                NomCita.setTextSize(18);
                Estado.setTextSize(18);
                Guardar.setTextSize(18);
                Cancelar.setTextSize(18);
                LabelP.setTextSize(18);
                LabelE.setTextSize(18);
                LabelF.setTextSize(18);
                break;
            case "textLarge":
                idCita.setTextSize(28);
                Fecha.setTextSize(24);
                NomCita.setTextSize(24);
                Estado.setTextSize(24);
                Guardar.setTextSize(24);
                Cancelar.setTextSize(24);
                LabelP.setTextSize(24);
                LabelE.setTextSize(24);
                LabelF.setTextSize(24);
                break;
        }

        switch(backColor){
            case "dark":
                fondo.setBackgroundColor(Color.parseColor("#550A0A"));
                idCita.setTextColor(Color.WHITE);
                Fecha.setTextColor(Color.BLACK);
                NomCita.setTextColor(Color.BLACK);
                Estado.setTextColor(Color.WHITE);
                Guardar.setTextColor(Color.WHITE);
                Cancelar.setTextColor(Color.WHITE);
                LabelP.setTextColor(Color.WHITE);
                LabelE.setTextColor(Color.WHITE);
                LabelF.setTextColor(Color.WHITE);
                break;
            case "light":
                fondo.setBackgroundColor(Color.parseColor("#FFDEDE"));
                idCita.setTextColor(Color.BLACK);
                Fecha.setTextColor(Color.BLACK);
                NomCita.setTextColor(Color.BLACK);
                Estado.setTextColor(Color.BLACK);
                Guardar.setTextColor(Color.BLACK);
                Cancelar.setTextColor(Color.BLACK);
                LabelP.setTextColor(Color.BLACK);
                LabelE.setTextColor(Color.BLACK);
                LabelF.setTextColor(Color.BLACK);
                break;
        }

    }

    public void GuardarCambios(View v){
        //Abrimos la base
        BaseClinica objB = new BaseClinica(this, "clinica", null, 1);
        //Permisos de lectura y escritura
        SQLiteDatabase bEstado = objB.getWritableDatabase();

        String date = Fecha.getText().toString();
        String NombreCitado = NomCita.getText().toString();
        String idCita = getIntent().getExtras().getString("idEnviado");
        String dui = DUI.getText().toString();
        String carnet = Carnet.getText().toString();
        String motivo = Motivo.getText().toString();
        //Se crea la instancia para editar el dato en la base
        String consultaSQL = "UPDATE citas SET nombrePaciente = '"+NombreCitado+"', dui = '"+dui+"', carnet = '"+carnet+"', fecha = '"+date+"', motivo = '"+motivo+"' WHERE idcita = '"+idCita+"'";

        //Se verifica si el editText contiene información, si no lo contiene, muestra un mensaje de error.
        if(NomCita.equals("")){
            NomCita.setError("Este dato no puede estar vacio");
        }//Si lo tiene, entonces hace lo siguiente:
        else
        {
            //Ejecuta la consulta SQL del Insert
            bEstado.execSQL(consultaSQL);
            //Muestra un mensaje de confirmación
            Toast.makeText(this, "Se ha editado el item exitosamente. id:"+idCita, Toast.LENGTH_SHORT).show();

            Intent Regresar = new Intent(EditarCita.this, ConsultaCitasAdmin.class);
            startActivity(Regresar);
        }
        //Se cierra la conexión.
        bEstado.close();
    }

    public void Cancelar(View v){
        Intent Regresar = new Intent(EditarCita.this, ConsultaCitasAdmin.class);
        startActivity(Regresar);
    }
}
