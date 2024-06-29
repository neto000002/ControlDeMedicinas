package com.example.controlmedicinas;

import com.example.controlmedicinas.db.DbPacientes;
import com.example.controlmedicinas.db.DbHelper;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class NuevoActivity extends AppCompatActivity {

    EditText txtNombre, txtEdad, txtMedicamento, txtDosis, txtAdministracion, txtHora;
    Button btnGuarda, btnAbrirCamara, btnCrear;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        txtNombre = findViewById(R.id.txtNombre);
        txtEdad = findViewById(R.id.txtEdad);
        txtMedicamento = findViewById(R.id.txtMedicamento);
        txtDosis = findViewById(R.id.txtDosis);
        txtAdministracion = findViewById(R.id.txtAdministracion);
        txtHora = findViewById(R.id.txtHora);
        btnGuarda = findViewById(R.id.btnGuarda);
        btnAbrirCamara = findViewById(R.id.btnCamara);
        btnCrear = findViewById(R.id.btnCrear); // Agregado el botón para crear la base de datos
        imgView = findViewById(R.id.imageView);

        txtHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarTimePickerDialog();
            }
        });

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarRegistro();
            }
        });

        btnAbrirCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearBaseDeDatos();
            }
        });
    }

    private void mostrarTimePickerDialog() {
        final Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String amPm = (hourOfDay < 12) ? "AM" : "PM";
                        txtHora.setText(String.format("%02d:%02d %s", hourOfDay % 12, minute, amPm));
                    }
                }, hora, minuto, false);

        timePickerDialog.show();
    }

    private void guardarRegistro() {
        DbPacientes dbPacientes = new DbPacientes(NuevoActivity.this);
        long id = dbPacientes.insertarPaciente(
                txtNombre.getText().toString(),
                Integer.valueOf(txtEdad.getText().toString()),
                txtMedicamento.getText().toString(),
                txtDosis.getText().toString(),
                txtAdministracion.getText().toString(),
                txtHora.getText().toString()
        );

        if(id > 0){
            Toast.makeText(NuevoActivity.this,"REGISTRO GUARDADO",Toast.LENGTH_LONG).show();
            limpiar();
        }else{
            Toast.makeText(NuevoActivity.this,"ERROR AL GUARDAR REGISTRO",Toast.LENGTH_LONG).show();
        }
    }

    private void limpiar(){
        txtNombre.setText("");
        txtEdad.setText("");
        txtMedicamento.setText("");
        txtDosis.setText("");
        txtAdministracion.setText("");
        txtHora.setText("");
    }

    private void abrirCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 1);
        }
    }

    private void crearBaseDeDatos() {
        // Crear la base de datos utilizando DbHelper
        DbHelper dbHelper = new DbHelper(this);
        dbHelper.getWritableDatabase(); // Abre la base de datos en modo escritura

        Toast.makeText(this, "Base de datos creada", Toast.LENGTH_SHORT).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            imgView.setImageBitmap(imgBitmap); // Establece la imagen capturada en el ImageView
            guardarImagenEnBaseDeDatos(imgBitmap); // Guarda la imagen en la base de datos
        }
    }

    private void guardarImagenEnBaseDeDatos(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbHelper.COL_IMAGEN, byteArray);

        // Aquí puedes agregar el resto de los valores del paciente a 'values' según sea necesario
        // values.put(DbHelper.COL_NOMBRE, "Nombre del paciente");
        // values.put(DbHelper.COL_EDAD, 30);
        // values.put(DbHelper.COL_MEDICAMENTO, "Nombre del medicamento");
        // values.put(DbHelper.COL_DOSIS, "Dosis del medicamento");
        // values.put(DbHelper.COL_ADMINISTRACION, "Administración del medicamento");
        // values.put(DbHelper.COL_HORA_ADMINISTRACION, "Hora de administración");

        long newRowId = db.insert(DbHelper.TABLE_PACIENTES, null, values);
        db.close();
    }
}
