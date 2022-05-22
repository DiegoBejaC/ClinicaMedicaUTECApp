package com.example.clinicav2;

public class CITAS {

    private String stringPaciente;
    private String stringFecha;
    private String stringEstado;
    private int intEstado;
    private String stringId;
    private int intId;

    public CITAS() {
    }

    public CITAS(String stringPaciente, String stringFecha, String stringEstado, int intEstado, String stringId, int intId) {
        this.stringPaciente = stringPaciente;
        this.stringFecha = stringFecha;
        this.stringEstado = stringEstado;
        this.intEstado = intEstado;
        this.stringId = stringId;
        this.intId = intId;
    }

    public String getStringPaciente() {
        return stringPaciente;
    }

    public void setStringPaciente(String stringPaciente) {
        this.stringPaciente = stringPaciente;
    }

    public String getStringFecha() {
        return stringFecha;
    }

    public void setStringFecha(String stringFecha) {
        this.stringFecha = stringFecha;
    }

    public String getStringEstado() {
        return stringEstado;
    }

    public void setStringEstado(String stringEstado) {
        this.stringEstado = stringEstado;
    }

    public int getIntEstado() {
        return intEstado;
    }

    public void setIntEstado(int intEstado) {
        this.intEstado = intEstado;
    }

    public String getStringId() {return stringId; }

    public void setStringId(String stringId) { this.stringId = stringId; }

    public int getIntId() { return intId; }

    public void setIntId(int intId) { this.intId = intId; }
}
