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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AgendaCita extends AppCompatActivity {
    Intent nuevaCita;   // El intent para poder abrir la actividad necesaria
    EditText edtNombreC, edtDui, edtCarnet, edtFecha, edtMotivo; //Los objetos para los cuadros de texto
    Spinner sp; //El spinner o lista desplegable para mostrar los turnos
    String [] turnos; //Este array se requiere para colocar los elementos al spinner anteriormente declarado
    String elNombre, elDui, elCarnet, laFecha, elMotivo; //Estos strings ayudarán a almacenar el nombre, dui, carnet, fecha y motivo
                                                        //a la base de datos
    int elTurno, estado; //Estos enteros ayudarán a almacenar el turno y estado de la cita a la base de datos
    int elID; //Para obtener el ID de la cita ingresada

    //Estos otros controles se inicializarán para poder ser afectados por las configuraciones de color y tamaño
    TextView tvTitulo;
    TextView [] textos = new TextView[6];
    ConstraintLayout fondo;
    String tamanioTexto;
    String backColor;
    Button botonAgendar, botonCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_cita);
        //Inicializacion de los demás objetos
        fondo = (ConstraintLayout)findViewById(R.id.fondoAgendarCita);
        tvTitulo = (TextView)findViewById(R.id.tvAgendarCitaPant);
        textos[0] = (TextView)findViewById(R.id.tvAC1);
        textos[1] = (TextView)findViewById(R.id.tvAC2);
        textos[2] = (TextView)findViewById(R.id.tvAC3);
        textos[3] = (TextView)findViewById(R.id.tvAC4);
        textos[4] = (TextView)findViewById(R.id.tvAC5);
        textos[5] = (TextView)findViewById(R.id.tvAC6);
        botonAgendar = (Button)findViewById(R.id.btnSiguiente2);
        botonCancelar = (Button)findViewById(R.id.btnCancelar2);

        //Inicialización de los editText
        edtNombreC = (EditText)findViewById(R.id.txtNombre);
        edtDui = (EditText)findViewById(R.id.txtDui);
        edtCarnet = (EditText)findViewById(R.id.txtCarnet);
        edtFecha = (EditText)findViewById(R.id.txtFecha);
        edtMotivo = (EditText)findViewById(R.id.txtMotivo);

        //Se obtienen los elementos de Turnos directo de un array creado en el archivo "strings.xml"
        turnos = getResources().getStringArray(R.array.turnosHoras);
        //Se inicializa el spinner
        sp = (Spinner)findViewById(R.id.spTurnos);
        //Se crea el adaptador para colocar los elementos de "turnos" en el spinner para rellenarlo
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, turnos);
        //Se configura el adaptador al spinner, con esto, el spinner contendrá los datos de los turnos
        sp.setAdapter(adaptador);

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
                botonAgendar.setTextSize(14);
                botonCancelar.setTextSize(14);
                edtNombreC.setTextSize(14);
                edtDui.setTextSize(14);
                edtCarnet.setTextSize(14);
                edtFecha.setTextSize(14);
                edtMotivo.setTextSize(14);
                break;
            case "textMedium":
                tvTitulo.setTextSize(26);
                for(int i = 0; i<textos.length;i++){
                    textos[i].setTextSize(18);
                }
                botonAgendar.setTextSize(18);
                botonCancelar.setTextSize(18);
                edtNombreC.setTextSize(18);
                edtDui.setTextSize(18);
                edtCarnet.setTextSize(18);
                edtFecha.setTextSize(18);
                edtMotivo.setTextSize(18);
                break;
            case "textLarge":
                tvTitulo.setTextSize(28);
                for(int i = 0; i<textos.length;i++){
                    textos[i].setTextSize(24);
                }
                botonAgendar.setTextSize(24);
                botonCancelar.setTextSize(24);
                edtNombreC.setTextSize(24);
                edtDui.setTextSize(24);
                edtCarnet.setTextSize(24);
                edtFecha.setTextSize(24);
                edtMotivo.setTextSize(24);
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

    //Método para salir al mençu principal
    public void regresarMenu(View view){
        finish();
    }

    //Método para el botón "Agendar"
    public void agendarCitaNueva(View view){
        //Agregando los datos a la base.--------------------------------------

        //Se crea el objeto de la clase "BaseClinica", el cual creará la base de datos llamada "clinica"
        BaseClinica objB = new BaseClinica(this, "clinica", null, 1);
        //Se obtiene los permisos de lectura y escritura a la base de datos.
        //(Si es la primera vez que se crea un objeto de la clase que gestiona la base, la base se crea)
        //(Para las próximas instancias de objeto de esta clase, la base no se vuelve a crear ya que antes se hizo)
        SQLiteDatabase bEstado = objB.getWritableDatabase();

        //Se asignan los datos a sus respectivas variables para luego ser utilizados en una consulta de SQL
        elNombre = edtNombreC.getText().toString();
        elDui = edtDui.getText().toString();
        elCarnet = edtCarnet.getText().toString();
        laFecha = edtFecha.getText().toString();
        //Para el caso de los turnos, según el elemento elegido en el Spinner, se asignará un número entero, siendo:
        //1 - Matutino
        //2 - Vespertino
        if(sp.getSelectedItem().toString().equals("Matutino")){
            elTurno = 1;
        }else{
            elTurno = 2;
        }
        elMotivo = edtMotivo.getText().toString();
        estado = 1; //Las citas registradas por cualquier usuario normal siempre serán agendadas

        //Ahora se creara la consulta a la base.
        String consultaSql = "insert into citas(nombrePaciente, dui, carnet, fecha, turno, motivo, estado) " +
                "values('" + elNombre + "','" + elDui + "','" + elCarnet + "','" + laFecha + "'," +
                elTurno + ",'" + elMotivo + "'," + estado + ")";

        //Ahora se ejecutará la consulta
        bEstado.execSQL(consultaSql);

        //Necesitaremos obtener el ID del nuevo registro ingresado en ese instante
        Cursor cursor = bEstado.rawQuery("SELECT * FROM citas ORDER BY idcita desc LIMIT 1", null);

        //Si el ID fue recuperado, se asignará a la variable elID, para ser enviado luego a la siguiente actividad.
        if(cursor.moveToNext()){
            elID = cursor.getInt(0);
        }else{
            Toast.makeText(this, "No se pudo recuperar la información de la cita agendada", Toast.LENGTH_SHORT).show();
        }

        //Al final se cierra la conexión con la base
        bEstado.close();

        //Ahora se mandarán los datos a la nueva actividad

        //Colocando los datos para ser enviados a la actividad que sera incovada después.
        //La cual mostrará los datos de la cita recien ingresada
        nuevaCita = new Intent(this, cita.class); // Se inicializa el intent para llamar a la actividad llamada "cita"

        //Se mandan datos extras
        nuevaCita.putExtra("IdCitaPaciente", elID);
        nuevaCita.putExtra("NombrePaciente", elNombre);
        nuevaCita.putExtra("DuiPaciente", elDui);
        nuevaCita.putExtra("CarnetPaciente", elCarnet);
        nuevaCita.putExtra("FechaPaciente", laFecha);
        nuevaCita.putExtra("TurnoPaciente", sp.getSelectedItem().toString());
        nuevaCita.putExtra("MotivoPaciente", elMotivo);
        nuevaCita.putExtra("EstadoPaciente", "Agendado");

        //Se inicia la actividad
        startActivity(nuevaCita);

        //Se finaliza la actividad en la que ahora nos encontramos
        finish();
    }
}