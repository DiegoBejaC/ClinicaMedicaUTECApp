package com.example.clinicav2;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class HorariosAtencion extends AppCompatActivity {
    TextView tvTitulo;
    TextView [] textos  = new TextView[3];
    ConstraintLayout fondo;
    String tamanioTexto;
    String backColor;
    ImageView relojBlanco, relojNegro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);

        fondo = (ConstraintLayout)findViewById(R.id.fondoHorarios);
        tvTitulo = (TextView)findViewById(R.id.tvHorarioPant);
        textos[0] = (TextView)findViewById(R.id.tvDias);
        textos[1] = (TextView)findViewById(R.id.tvHoras1);
        textos[2] = (TextView)findViewById(R.id.tvHoras2);

        relojBlanco = (ImageView)findViewById(R.id.relojClaro);
        relojNegro = (ImageView)findViewById(R.id.relojOscuro);

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
                relojBlanco.setVisibility(View.VISIBLE);
                relojNegro.setVisibility(View.INVISIBLE);
                break;
            case "light":
                fondo.setBackgroundColor(Color.parseColor("#FFDEDE"));
                tvTitulo.setTextColor(Color.BLACK);
                for(int i = 0; i<textos.length;i++){
                    textos[i].setTextColor(Color.BLACK);
                }
                relojBlanco.setVisibility(View.INVISIBLE);
                relojNegro.setVisibility(View.VISIBLE);
                break;
        }

    }
}