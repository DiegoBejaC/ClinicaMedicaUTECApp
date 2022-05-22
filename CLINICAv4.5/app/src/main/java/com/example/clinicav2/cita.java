package com.example.clinicav2;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class cita extends AppCompatActivity {
    //Los objetos de los textViews donde se mostrarán los datos al iniciar las actividades.
    TextView tvTitulo, tvNombreRes, tvDuiRes, tvCarnetRes, tvFechaRes, tvTurnoRes, tvMotivoRes, tvEstadoRes;
    //Las variables para poder recuperar los datos enviados desde la actividad de "AgendaCita"
    String NombreObtenido, DuiObtenido, CarnetObtenido, FechaObtenido, TurnoObtenido, MotivoObtenido, EstadoObtenido;
    String IdObtenido; //El ID correspondiente a la nueva cita, esto servirá para el botón "Cancelar cita"
                    //para poder borrar la cita de la base de datos con el ID.
    ConstraintLayout fondo;
    String tamanioTexto;
    String backColor;
    Button botonCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cita);

        fondo = (ConstraintLayout)findViewById(R.id.fondoCitaAgendada);
        botonCancelar = (Button)findViewById(R.id.btnCancelarCita);

        //Inicializando los textViews
        tvTitulo = (TextView)findViewById(R.id.tvCitaPant);
        tvNombreRes = (TextView)findViewById(R.id.tvNombre);
        tvDuiRes = (TextView)findViewById(R.id.tvDUI);
        tvCarnetRes = (TextView)findViewById(R.id.tvCarnet);
        tvFechaRes = (TextView)findViewById(R.id.tvFecha);
        tvTurnoRes = (TextView)findViewById(R.id.tvTurno);
        tvMotivoRes = (TextView)findViewById(R.id.tvMotivoConsulta);
        tvEstadoRes = (TextView)findViewById(R.id.tvEstado);

        //Se crea el Bundle para recibir los datos que se enviaron de la actividad anterior
        Bundle todosLosDatos = getIntent().getExtras();
        //Se guardan esos datos en sus respectivas variables.
        IdObtenido = String.valueOf(todosLosDatos.getInt("IdCitaPaciente")); //Este NO se mostrará en la pantalla
        NombreObtenido = todosLosDatos.getString("NombrePaciente");
        DuiObtenido = todosLosDatos.getString("DuiPaciente");
        CarnetObtenido = todosLosDatos.getString("CarnetPaciente");
        FechaObtenido = todosLosDatos.getString("FechaPaciente");
        TurnoObtenido = todosLosDatos.getString("TurnoPaciente");
        MotivoObtenido = todosLosDatos.getString("MotivoPaciente");
        EstadoObtenido = todosLosDatos.getString("EstadoPaciente");

        //Se modifican los textViews para mostrar los datos obtenidos
        tvNombreRes.setText("Nombre: " + NombreObtenido);
        tvDuiRes.setText("DUI: " + DuiObtenido);
        tvCarnetRes.setText("Carnet: " + CarnetObtenido);
        tvFechaRes.setText("Fecha: " + FechaObtenido);
        tvTurnoRes.setText("Turno: " + TurnoObtenido);
        tvMotivoRes.setText("Motivo: " + MotivoObtenido);
        tvEstadoRes.setText("Estado: " + EstadoObtenido);

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
                tvNombreRes.setTextSize(14);
                tvDuiRes.setTextSize(14);
                tvCarnetRes.setTextSize(14);
                tvFechaRes.setTextSize(14);
                tvTurnoRes.setTextSize(14);
                tvMotivoRes.setTextSize(14);
                tvEstadoRes.setTextSize(14);
                botonCancelar.setTextSize(14);
                break;
            case "textMedium":
                tvTitulo.setTextSize(26);
                tvNombreRes.setTextSize(18);
                tvDuiRes.setTextSize(18);
                tvCarnetRes.setTextSize(18);
                tvFechaRes.setTextSize(18);
                tvTurnoRes.setTextSize(18);
                tvMotivoRes.setTextSize(18);
                tvEstadoRes.setTextSize(18);
                botonCancelar.setTextSize(18);
                break;
            case "textLarge":
                tvTitulo.setTextSize(28);
                tvNombreRes.setTextSize(24);
                tvDuiRes.setTextSize(24);
                tvCarnetRes.setTextSize(24);
                tvFechaRes.setTextSize(24);
                tvTurnoRes.setTextSize(24);
                tvMotivoRes.setTextSize(24);
                tvEstadoRes.setTextSize(24);
                botonCancelar.setTextSize(24);
                break;
        }

        switch(backColor){
            case "dark":
                fondo.setBackgroundColor(Color.parseColor("#550A0A"));
                tvTitulo.setTextColor(Color.WHITE);
                tvNombreRes.setTextColor(Color.WHITE);
                tvDuiRes.setTextColor(Color.WHITE);
                tvCarnetRes.setTextColor(Color.WHITE);
                tvFechaRes.setTextColor(Color.WHITE);
                tvTurnoRes.setTextColor(Color.WHITE);
                tvMotivoRes.setTextColor(Color.WHITE);
                tvEstadoRes.setTextColor(Color.WHITE);
                break;
            case "light":
                fondo.setBackgroundColor(Color.parseColor("#FFDEDE"));
                tvTitulo.setTextColor(Color.BLACK);
                tvNombreRes.setTextColor(Color.BLACK);
                tvDuiRes.setTextColor(Color.BLACK);
                tvCarnetRes.setTextColor(Color.BLACK);
                tvFechaRes.setTextColor(Color.BLACK);
                tvTurnoRes.setTextColor(Color.BLACK);
                tvMotivoRes.setTextColor(Color.BLACK);
                tvEstadoRes.setTextColor(Color.BLACK);
                break;
        }

    }

    //Método para cancelar la cita
    //Por ahora sólo está programado para colocar un mensaje con un Toast.
    //ESTE MÉTODO DEBE DE BORRAR EL REGISTRO RECIÉN AGREGADO A LA BASE DE DATOS EN CUANDO SE PRESIONE EL BOTÓN
    public void cancelarCita(View view){
        //Se crea el objeto para abrir la base
        BaseClinica BaseEstado = new BaseClinica(this,"clinica",null,1);
        //Se obtienen permisos de lectura y escritura
        SQLiteDatabase Permiso=BaseEstado.getWritableDatabase();
        //Consulta para eliminar por medio del ID
        Permiso.delete("citas" ,"idcita"+"=?" ,new String[]{IdObtenido});
        Toast.makeText(this, "Cita cancelada con éxito.", Toast.LENGTH_SHORT).show(); //Se muestra un mensaje
        finish();//Se finaliza la actividad para volver al menú principal
    }

}