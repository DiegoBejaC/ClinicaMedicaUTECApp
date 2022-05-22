package com.example.clinicav2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BaseClinica extends SQLiteOpenHelper {

    //Este es el constructor por el cual se creará el objeto especificando el nombre de la base de datos.
    public BaseClinica(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //La base de datos inicial contendrá la tabla de Usuarios
        //la que corresponderá a los administradores de la app.
        //Campos:
        //idUsuario el ID del usuario
        //nombreUsuario: primer nombre y primer apellido del usuario
        //usuario: nombre de login
        //clave: contraseña del usuario

        db.execSQL("create table usuarios(\n" +
                "idUsuario integer not null primary key autoincrement,\n" +
                "nombreUsuario text,\n" +
                "usuario text,\n" +
                "clave text\n" +
                ")");

        //Aquí se agregan los usuarios administradores:
        db.execSQL("insert into usuarios(nombreUsuario, usuario, clave) values('Diego Bejarano', 'fer', '12345')");
        db.execSQL("insert into usuarios(nombreUsuario, usuario, clave) values('Violeta Hernández', 'vio', '23456')");
        db.execSQL("insert into usuarios(nombreUsuario, usuario, clave) values('Dany Cerna', 'dnj', '34567')");
        db.execSQL("insert into usuarios(nombreUsuario, usuario, clave) values('Reynaldo Ventura', 'rey', '45678')");
        db.execSQL("insert into usuarios(nombreUsuario, usuario, clave) values('Miguel Miranda', 'alb', '56789')");
        db.execSQL("insert into usuarios(nombreUsuario, usuario, clave) values('Carlos Maravilla', 'car', '67890')");


        //La Base de datos inicial contendrá la tabla de Citas
        //La cual a su vez contiene los siguietes campos:
        //idCita: identificador de la cita
        //nombrePaciente: nombre de paciente
        //dui: DUI del paciente con guión
        //carnet: carnet del estudiante con los dos guiones
        //fecha: fecha de la consulta en formato "AAAA-MM-DD"
        //turno: entero que indica el turno Matutino (1) o Vespertino (2)
        //motivo: descripción del motivo de la cita
        //estado: número que indica el estado, ya sea AGENDADO(1), REALIZADO(2), CANCELADO(3)
        db.execSQL("create table citas(idcita integer not null primary key autoincrement, nombrePaciente text not null, "  +
                "dui text, carnet text, fecha text, turno integer, motivo text, estado integer)");

        //Se ingresó este dato de prueba para asegurar la manera en que se deben de agregar los datos para esta tabla.
        //El usuario será capaz de ingresar sus datos desde la aplicación y estos se guardarán en la base.
        db.execSQL("insert into citas(nombrePaciente, dui, carnet, fecha, turno, motivo, estado) values('Diego Fernando Bejarano', '12345678-9', '25-0211-2017', '2021-12-21', 1, 'Dolor de cabeza fuerte', 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
