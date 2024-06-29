package com.example.controlmedicinas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.controlmedicinas.db.DbPacientes;
import com.example.controlmedicinas.entidades.Pacientes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VerActivity extends AppCompatActivity {

    EditText txtNombre, txtEdad, txtMedicamento, txtDosis, txtAdministracion, txtHora_administracion;
    Button btnGuarda;
    FloatingActionButton fabEditar, fabEliminar;

    Pacientes pacientes;
    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver);

        txtNombre = findViewById(R.id.txtNombre);
        txtEdad = findViewById(R.id.txtEdad);
        txtMedicamento = findViewById(R.id.txtMedicamento);
        txtDosis = findViewById(R.id.txtDosis);
        txtAdministracion = findViewById(R.id.txtAdministracion);
        txtHora_administracion = findViewById(R.id.txtHora);
        btnGuarda = findViewById(R.id.btnGuarda);
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        DbPacientes dbPacientes = new DbPacientes(VerActivity.this);
        pacientes = dbPacientes.verPacientes(id);

        if (pacientes != null) {
            txtNombre.setText(pacientes.getNombre());
            txtEdad.setText(pacientes.getEdad());
            txtMedicamento.setText(pacientes.getMedicamento());
            txtDosis.setText(pacientes.getDosis());
            txtAdministracion.setText(pacientes.getAdministracion());
            txtHora_administracion.setText(pacientes.getHora_administracion());
            btnGuarda.setVisibility(View.INVISIBLE);
            txtNombre.setInputType(InputType.TYPE_NULL);
            txtEdad.setInputType(InputType.TYPE_NULL);
            txtMedicamento.setInputType(InputType.TYPE_NULL);
            txtDosis.setInputType(InputType.TYPE_NULL);
            txtAdministracion.setInputType(InputType.TYPE_NULL);
            txtHora_administracion.setInputType(InputType.TYPE_NULL);
        }

        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerActivity.this, EditarActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VerActivity.this);
                builder.setMessage("¿Desea eliminar el paciente?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (dbPacientes.eliminarPaciente(id)) {
                                    lista();

                                }

                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });

    }
    private void lista(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}