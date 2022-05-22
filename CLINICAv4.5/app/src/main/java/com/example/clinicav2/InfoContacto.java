package com.example.clinicav2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.widget.TextView;

public class InfoContacto extends AppCompatActivity {
    TextView tvTitulo, tv1, tv2, tv3, tv4;
    ConstraintLayout fondo;
    String tamanioTexto;
    String backColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_contacto);

        fondo = (ConstraintLayout)findViewById(R.id.fondoInfo);
        tvTitulo = (TextView)findViewById(R.id.tvInfoCon);
        tv1 = (TextView)findViewById(R.id.tvTelefonos);
        tv2 = (TextView)findViewById(R.id.tvCorreos);
        tv3 = (TextView)findViewById(R.id.tvDirecciones);
        tv4 = (TextView)findViewById(R.id.tvDireccionC);

        PreferenceManager.setDefaultValues(this,R.xml.main_preferences,false);
        loadPreference();
    }

    @Override
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
                tv1.setTextSize(14);
                tv2.setTextSize(14);
                tv3.setTextSize(14);
                tv4.setTextSize(14);
                break;
            case "textMedium":
                tvTitulo.setTextSize(26);
                tv1.setTextSize(18);
                tv2.setTextSize(18);
                tv3.setTextSize(18);
                tv4.setTextSize(18);
                break;
            case "textLarge":
                tvTitulo.setTextSize(28);
                tv1.setTextSize(24);
                tv2.setTextSize(24);
                tv3.setTextSize(24);
                tv4.setTextSize(24);
                break;
        }

        switch(backColor){
            case "dark":
                fondo.setBackgroundColor(Color.parseColor("#550A0A"));
                tvTitulo.setTextColor(Color.WHITE);
                tv1.setTextColor(Color.WHITE);
                tv2.setTextColor(Color.WHITE);
                tv3.setTextColor(Color.WHITE);
                tv4.setTextColor(Color.WHITE);
                break;
            case "light":
                fondo.setBackgroundColor(Color.parseColor("#FFDEDE"));
                tvTitulo.setTextColor(Color.BLACK);
                tv1.setTextColor(Color.BLACK);
                tv2.setTextColor(Color.BLACK);
                tv3.setTextColor(Color.BLACK);
                tv4.setTextColor(Color.BLACK);
                break;
        }

    }
}