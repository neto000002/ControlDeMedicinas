package com.example.controlmedicinas.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NOMBRE = "agenda.db";
    public static final String TABLE_PACIENTES = "t_pacientes";
    public static final String COL_ID = "id";
    public static final String COL_NOMBRE = "nombre";
    public static final String COL_EDAD = "edad";
    public static final String COL_MEDICAMENTO = "medicamento";
    public static final String COL_DOSIS = "dosis";
    public static final String COL_ADMINISTRACION = "administracion";
    public static final String COL_HORA_ADMINISTRACION = "hora_administracion";
    public static final String COL_IMAGEN = "imagen"; // Columna para almacenar la imagen como blob

    public DbHelper(Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PACIENTES + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NOMBRE + " TEXT NOT NULL," +
                COL_EDAD + " INTEGER NOT NULL," +
                COL_MEDICAMENTO + " TEXT NOT NULL," +
                COL_DOSIS + " TEXT NOT NULL," +
                COL_ADMINISTRACION + " TEXT NOT NULL," +
                COL_HORA_ADMINISTRACION + " TEXT NOT NULL," +
                COL_IMAGEN + " BLOB)"); // La columna de imagen se define como BLOB
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PACIENTES);
        onCreate(sqLiteDatabase);
    }
}
