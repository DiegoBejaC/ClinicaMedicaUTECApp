package com.example.clinicav2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Intent openAct; //Objeto intent universal para abrir la actividad según el botón que se presione

    Button botonAgendarCita; //El primer y el último botón de toda la pantalla
    Button [] botones = new Button[6]; // Los demás botones de en medio

    ConstraintLayout fondo;
    String tamanioTexto;
    String backColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botonAgendarCita = (Button)findViewById(R.id.btnNuevaCita);
        botones[0] = (Button)findViewById(R.id.btnContacto);
        botones[1] = (Button)findViewById(R.id.btnHorarios);
        botones[2] = (Button)findViewById(R.id.btnConfig);
        botones[3] = (Button)findViewById(R.id.btnAcercaDe);
        botones[4] = (Button)findViewById(R.id.btnSalir);
        botones[5] = (Button)findViewById(R.id.btnAdmin);

        fondo = (ConstraintLayout)findViewById(R.id.fondoMenu);

        PreferenceManager.setDefaultValues(this,R.xml.main_preferences,false);
        loadPreference();
    }

    //Aquí se programan los métodos para aplicar las configuraciones de la pantalla de ajustes.
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
                for(int i = 0; i<botones.length;i++){
                    botones[i].setTextSize(14);
                }
                botonAgendarCita.setTextSize(18);
                break;
            case "textMedium":
                for(int i = 0; i<botones.length;i++){
                    botones[i].setTextSize(16);
                }
                botonAgendarCita.setTextSize(20);
                break;
            case "textLarge":
                for(int i = 0; i<botones.length;i++){
                    botones[i].setTextSize(18);
                }
                botonAgendarCita.setTextSize(22);
                break;
        }

        switch(backColor){
            case "dark":
                fondo.setBackgroundColor(Color.BLACK);
                //fondo.setBackgroundColor(Color.parseColor("#550A0A"));
                break;
            case "light":
                fondo.setBackgroundColor(Color.parseColor("#FFDEDE"));
                break;
        }

    }



    //PROGRAMACIÓN DE LOS MÉTODOS PARA LAS ACCIONES DE LOS BOTONES INICIA AQUÍ
    //Para botón "NUEVA CITA"
    public void abrirCita(View view){
        openAct = new Intent(this, AgendaCita.class);
        startActivity(openAct);
    }
    //Para botón "Contacto"
    public void abrirContacto(View view){
        openAct = new Intent(this, InfoContacto.class);
        startActivity(openAct);
    }
    //Para botón "Horarios de atención"
    public void abrirHorarios(View view){
        openAct = new Intent(this, HorariosAtencion.class);
        startActivity(openAct);
    }
    //Para botón "Configuración"
    public void abrirConfig(View view){
        openAct = new Intent(this, SettingsClinica.class);
        startActivity(openAct);
    }
    //Para botón "Acerca de"
    public void abrirAcercaDe(View view){
        openAct = new Intent(this, Acerca_De.class);
        startActivity(openAct);
    }
    //Para botón "Salir"
    public void salir(View view){
        finish();
    }
    //Para botón "Administrador"
    public void abrirAdmin(View view){
        openAct = new Intent(MainActivity.this, InicioSesion.class);
        startActivity(openAct);
    }


}