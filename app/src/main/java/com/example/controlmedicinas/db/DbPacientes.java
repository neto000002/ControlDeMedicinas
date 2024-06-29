package com.example.controlmedicinas.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.controlmedicinas.entidades.Pacientes;

import java.util.ArrayList;

public class DbPacientes extends DbHelper{

    Context context;
    public DbPacientes(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarPaciente(String nombre, Integer edad, String medicamento, String dosis, String administracion, String hora_administracion){
       long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("edad", edad);
            values.put("medicamento", medicamento);
            values.put("dosis", dosis);
            values.put("administracion", administracion);
            values.put("hora_administracion", hora_administracion);

            id = db.insert(TABLE_PACIENTES, null, values);
        } catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public ArrayList<Pacientes>mostrarPacientes(){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Pacientes> listaPacientes = new ArrayList<>();
        Pacientes pacientes = null;
        Cursor cursorPacientes = null;

        cursorPacientes = db.rawQuery("SELECT * FROM " + TABLE_PACIENTES, null);

        if(cursorPacientes.moveToFirst()){
            do {
                pacientes = new Pacientes();
                pacientes.setId(cursorPacientes.getInt(0));
                pacientes.setNombre(cursorPacientes.getString(1));
                pacientes.setEdad(cursorPacientes.getString(2));
                pacientes.setMedicamento(cursorPacientes.getString(3));
                pacientes.setDosis(cursorPacientes.getString(4));
                pacientes.setAdministracion(cursorPacientes.getString(5));
                pacientes.setHora_administracion(cursorPacientes.getString(6));
                listaPacientes.add(pacientes);
            }while (cursorPacientes.moveToNext());
        }
        cursorPacientes.close();
        return listaPacientes;
    }

    public Pacientes verPacientes(int id){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Pacientes pacientes = null;
        Cursor cursorPacientes;

        cursorPacientes = db.rawQuery("SELECT * FROM " + TABLE_PACIENTES + " WHERE id = " + id + " LIMIT 1", null);
        ;

        if(cursorPacientes.moveToFirst()){
                pacientes = new Pacientes();
                pacientes.setId(cursorPacientes.getInt(0));
                pacientes.setNombre(cursorPacientes.getString(1));
                pacientes.setEdad(cursorPacientes.getString(2));
                pacientes.setMedicamento(cursorPacientes.getString(3));
                pacientes.setDosis(cursorPacientes.getString(4));
                pacientes.setAdministracion(cursorPacientes.getString(5));
                pacientes.setHora_administracion(cursorPacientes.getString(6));
            }

        cursorPacientes.close();
        return pacientes;
    }

    public boolean editarPaciente(int id, String nombre, Integer edad, String medicamento, String dosis, String administracion, String hora_administracion){

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_PACIENTES + " SET nombre = '"+ nombre +"', edad = '"+ edad +"', medicamento = '"+ medicamento +"', dosis = '"+ dosis +"', administracion = '"+ administracion +"', hora_administracion = '"+ hora_administracion +"' WHERE id='"+ id +"' ");
            correcto = true;
        } catch (Exception ex){
            ex.toString();
            correcto = false;
        }finally {
            db.close();
        }
        return correcto;
    }

    public boolean eliminarPaciente(int id){

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_PACIENTES + " WHERE id = '"+ id +"'");
            correcto = true;
        } catch (Exception ex){
            ex.toString();
            correcto = false;
        }finally {
            db.close();
        }
        return correcto;
    }
}
