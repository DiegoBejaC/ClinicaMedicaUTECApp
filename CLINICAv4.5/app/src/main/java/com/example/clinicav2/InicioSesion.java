package com.example.clinicav2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InicioSesion extends AppCompatActivity {
    TextView txtLogin, txtPass,txtUserS,txtPassS, tvTitulo; //Se crean los objetos de los textViews
    ConstraintLayout fondo;
    String tamanioTexto;
    String backColor;
    Button botonIngresar, botonCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        fondo = (ConstraintLayout)findViewById(R.id.fondoLogin);
        botonIngresar = (Button)findViewById(R.id.btnSiguiente);
        botonCancelar = (Button)findViewById(R.id.btnCancelar);

        //Se inicializan los TextViews
        tvTitulo = (TextView)findViewById(R.id.tvIngresarPant);
        txtLogin = (TextView)findViewById(R.id.txtUsuario); //EditText
        txtPass = (TextView)findViewById(R.id.txtContra); //EditText
        txtUserS = (TextView)findViewById(R.id.txtUser); //Textos
        txtPassS = (TextView)findViewById(R.id.txtPass); //Textos

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
                txtLogin.setTextSize(14);
                txtPass.setTextSize(14);
                txtUserS.setTextSize(14);
                txtPassS.setTextSize(14);
                botonIngresar.setTextSize(14);
                botonCancelar.setTextSize(14);
                break;
            case "textMedium":
                tvTitulo.setTextSize(26);
                txtLogin.setTextSize(18);
                txtPass.setTextSize(18);
                txtUserS.setTextSize(18);
                txtPassS.setTextSize(18);
                botonIngresar.setTextSize(18);
                botonCancelar.setTextSize(18);
                break;
            case "textLarge":
                tvTitulo.setTextSize(28);
                txtLogin.setTextSize(24);
                txtPass.setTextSize(24);
                txtUserS.setTextSize(24);
                txtPassS.setTextSize(24);
                botonIngresar.setTextSize(24);
                botonCancelar.setTextSize(24);
                break;
        }

        switch(backColor){
            case "dark":
                fondo.setBackgroundColor(Color.parseColor("#550A0A"));
                tvTitulo.setTextColor(Color.WHITE);
                txtUserS.setTextColor(Color.WHITE);
                txtPassS.setTextColor(Color.WHITE);
                break;
            case "light":
                fondo.setBackgroundColor(Color.parseColor("#FFDEDE"));
                tvTitulo.setTextColor(Color.BLACK);
                tvTitulo.setTextColor(Color.BLACK);
                txtUserS.setTextColor(Color.BLACK);
                txtPassS.setTextColor(Color.BLACK);
                break;
        }

    }

    //Método para el botón "Siguiente"

    public void ingresarAdmin(View view){
        //Datos de prueba mientras no utilicemos bases de datos
        /*String stringLogin = "admin";//Login para ingresar
        String stringPass = "12345";//Contraseña para ingresar*/

        //Se crean las variables que contendran los datos de los txt y se inicializan
        String stringLogin = "0";
        String stringPass = "0";
        //se crea el objeto que inicializa la bd y se crea la conexion
        BaseClinica objB = new BaseClinica(this, "clinica", null, 1);
        SQLiteDatabase bEstado = objB.getWritableDatabase();
        //se llenan las variables con los datos de los txt
        String userLogin = txtLogin.getText().toString();//login ingresado por el usuario
        String userPass = txtPass.getText().toString();//Contraseña ingresada por el usuario
        //se llena la variable con la consulta y los datos ingresados por el usuario
        String consultaSql = "select * from usuarios where usuario = '"+userLogin+"'  and clave = '"+userPass+"'";
        //se crea un cursor que es para que retorne los datos del SELECT
        Cursor cursor = bEstado.rawQuery(consultaSql, null);
        //se recorre el cursor que contiene los datos del SELECT
        if(cursor.moveToFirst()){
            do{
                stringLogin = cursor.getString(2); //se le asigna lo del select a la variable correspondiente
                stringPass = cursor.getString(3);//se le asigna lo del select a la variable correspondiente
            }while (cursor.moveToNext());
        }
        bEstado.close(); //se cierra la conexion

        /*txtUserS.setText(stringLogin);
        txtPassS.setText(stringPass);*/
        if(userLogin.equals(stringLogin) && userPass.equals(stringPass)){//Si el usuario Y la contraseña son los correctos
            Intent objEntrar = new Intent(this, ConsultaCitasAdmin.class); //Se inicializa un intent para abrir la actividad "ConsultaCitas"
            startActivity(objEntrar); //Se inicia la actividad
        }else{ //Si no son los datos correctos
            if(userLogin.isEmpty()){ //Si el campo para el usuario está vacío
                txtLogin.setError("Ingrese su usuario"); //Se muestra un error
            }
            if(userPass.isEmpty()){ //Si el campo para la clave está vacío
                txtPass.setError("Ingrese su contraseña");//Se muestra un error
            }
            Toast.makeText(this, "DATOS INCORRECTOS O INCOMPLETOS", Toast.LENGTH_SHORT).show(); //Se muestra un mensaje
        }
    }

    //Método para el botón "Cancelar"
    public void regresarMenu(View view){
        finish();//Se finaliza la actividad
    }

}