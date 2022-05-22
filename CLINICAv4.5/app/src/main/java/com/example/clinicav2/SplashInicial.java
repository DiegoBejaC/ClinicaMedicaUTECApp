package com.example.clinicav2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

public class SplashInicial extends AppCompatActivity {
    ConstraintLayout fondo;
    String backColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_inicial);

        fondo = (ConstraintLayout)findViewById(R.id.fondoSplash);

        PreferenceManager.setDefaultValues(this,R.xml.main_preferences,false);
        loadPreference();

        //Este método se crea para poder ejecutar el método "run" dentro del mismo luego de un tiempo establecido en milisegundos
        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent inicio = new Intent(SplashInicial.this, MainActivity.class); //Se configura para abrir el menu principal
                startActivity(inicio); //Se inicia el menú principal
                finish(); //Se cierra la actividad anterior
            }
        },2500);//El método se ejecutará luego de 2.5 segundos

        //En pocas palabras, se abrirá el menú principal luego de 2.5 segundos de haberse iniciado la aplicación con esta pantalla.
    }

    protected void onRestart() {
        super.onRestart();
        loadPreference();
    }

    public void loadPreference(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        backColor = pref.getString("backgroundColor", "light");

        switch(backColor){
            case "dark":
                fondo.setBackgroundColor(Color.parseColor("#550A0A"));
                break;
            case "light":
                fondo.setBackgroundColor(Color.parseColor("#FFDEDE"));
                break;
        }

    }


}