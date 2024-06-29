package com.example.controlmedicinas.entidades;

public class Pacientes {

    private int id;
    private String nombre;
    private String edad;
    private String medicamento;
    private String dosis;
    private String administracion;
    private String hora_administracion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getAdministracion() {
        return administracion;
    }

    public void setAdministracion(String administracion) {
        this.administracion = administracion;
    }

    public String getHora_administracion() {
        return hora_administracion;
    }

    public void setHora_administracion(String hora_administracion) {
        this.hora_administracion = hora_administracion;
    }
}
