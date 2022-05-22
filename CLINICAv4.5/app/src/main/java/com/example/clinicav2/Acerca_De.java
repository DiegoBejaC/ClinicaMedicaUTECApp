package com.example.clinicav2;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Acerca_De extends AppCompatActivity {
    TextView tvTitulo;
    TextView [] textos  = new TextView[9];
    ConstraintLayout fondo;
    String tamanioTexto;
    String backColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca__de);

        fondo = (ConstraintLayout)findViewById(R.id.fondoAcercaDe);
        tvTitulo = (TextView)findViewById(R.id.tvAcercaDePant);
        textos[0] = (TextView)findViewById(R.id.tvA1);
        textos[1] = (TextView)findViewById(R.id.tvA2);
        textos[2] = (TextView)findViewById(R.id.tvA3);
        textos[3] = (TextView)findViewById(R.id.tvA4);
        textos[4] = (TextView)findViewById(R.id.tvA5);
        textos[5] = (TextView)findViewById(R.id.tvA6);
        textos[6] = (TextView)findViewById(R.id.tvA7);
        textos[7] = (TextView)findViewById(R.id.tvA8);
        textos[8] = (TextView)findViewById(R.id.tvA9);

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
                for(int i = 0; i<textos.length;i++){
                    textos[i].setTextSize(14);
                }
                break;
            case "textMedium":
                tvTitulo.setTextSize(26);
                for(int i = 0; i<textos.length;i++){
                    textos[i].setTextSize(18);
                }
                break;
            case "textLarge":
                tvTitulo.setTextSize(28);
                for(int i = 0; i<textos.length;i++){
                    textos[i].setTextSize(24);
                }
                break;
        }

        switch(backColor){
            case "dark":
                fondo.setBackgroundColor(Color.parseColor("#550A0A"));
                tvTitulo.setTextColor(Color.WHITE);
                for(int i = 0; i<textos.length;i++){
                    textos[i].setTextColor(Color.WHITE);
                }
                break;
            case "light":
                fondo.setBackgroundColor(Color.parseColor("#FFDEDE"));
                tvTitulo.setTextColor(Color.BLACK);
                for(int i = 0; i<textos.length;i++){
                    textos[i].setTextColor(Color.BLACK);
                }
                break;
        }

    }
    
}