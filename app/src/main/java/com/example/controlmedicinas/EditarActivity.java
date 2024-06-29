package com.example.controlmedicinas;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.controlmedicinas.db.DbPacientes;
import com.example.controlmedicinas.entidades.Pacientes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class EditarActivity extends AppCompatActivity {

    EditText txtNombre, txtEdad, txtMedicamento, txtDosis, txtAdministracion, txtHora_administracion;
    Button btnGuarda;
    FloatingActionButton fabEditar, fabEliminar;
    boolean correcto = false;

    Pacientes pacientes;
    int id = 0;

    @SuppressLint("RestrictedApi")
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
        fabEditar.setVisibility(View.INVISIBLE);
        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = -1;
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        DbPacientes dbPacientes = new DbPacientes(EditarActivity.this);
        pacientes = dbPacientes.verPacientes(id);

        if (pacientes != null) {
            txtNombre.setText(pacientes.getNombre());
            txtEdad.setText(pacientes.getEdad());
            txtMedicamento.setText(pacientes.getMedicamento());
            txtDosis.setText(pacientes.getDosis());
            txtAdministracion.setText(pacientes.getAdministracion());
            txtHora_administracion.setText(pacientes.getHora_administracion());
        }

        txtHora_administracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogoSeleccionHora();
            }
        });

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos()) {
                    guardarCambios();
                    configurarAlarma();
                }
            }
        });
    }

    private void mostrarDialogoSeleccionHora() {
        // Obtenemos la hora actual
        Calendar calendario = Calendar.getInstance();
        int horaActual = calendario.get(Calendar.HOUR_OF_DAY);
        int minutoActual = calendario.get(Calendar.MINUTE);
        boolean esFormato24Horas = android.text.format.DateFormat.is24HourFormat(this);

        // Creamos un diálogo de selección de hora
        TimePickerDialog dialogoHora = new TimePickerDialog(EditarActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int horaSeleccionada, int minutoSeleccionado) {
                        // Aquí obtenemos la hora seleccionada y la mostramos en el EditText
                        String horaFormateada;
                        if (esFormato24Horas) {
                            horaFormateada = String.format("%02d:%02d", horaSeleccionada, minutoSeleccionado);
                        } else {
                            // Convertimos la hora de 24 horas a 12 horas y mostramos AM/PM
                            int hora12Formato = (horaSeleccionada == 0 || horaSeleccionada == 12) ? 12 : horaSeleccionada % 12;
                            String amPm = horaSeleccionada < 12 ? "AM" : "PM";
                            horaFormateada = String.format("%02d:%02d %s", hora12Formato, minutoSeleccionado, amPm);
                        }
                        txtHora_administracion.setText(horaFormateada);
                    }
                }, horaActual, minutoActual, esFormato24Horas); // El último parámetro indica si se muestra el formato de 24 horas

        // Mostramos el diálogo
        dialogoHora.show();
    }

    private boolean validarCampos() {
        if (txtNombre.getText().toString().isEmpty() || txtEdad.getText().toString().isEmpty() ||
                txtMedicamento.getText().toString().isEmpty() || txtDosis.getText().toString().isEmpty() ||
                txtAdministracion.getText().toString().isEmpty() || txtHora_administracion.getText().toString().isEmpty()) {
            Toast.makeText(EditarActivity.this, "DEBE LLENAR TODOS LOS CAMPOS", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void guardarCambios() {
        DbPacientes dbPacientes = new DbPacientes(EditarActivity.this);
        correcto = dbPacientes.editarPaciente(id, txtNombre.getText().toString(),
                Integer.valueOf(txtEdad.getText().toString()), txtMedicamento.getText().toString(),
                txtDosis.getText().toString(), txtAdministracion.getText().toString(),
                txtHora_administracion.getText().toString());

        if (correcto) {
            Toast.makeText(EditarActivity.this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
            verRegistro();
        } else {
            Toast.makeText(EditarActivity.this, "ERROR AL MODIFICAR", Toast.LENGTH_LONG).show();
        }
    }

    private void verRegistro() {
        Intent intent = new Intent(this, VerActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    private void configurarAlarma() {
        // Obtener la hora y los minutos de la hora de administración
        String horaAdministracion = txtHora_administracion.getText().toString();
        String[] partes = horaAdministracion.split(":| ");
        int hora = Integer.parseInt(partes[0]);
        int minuto = Integer.parseInt(partes[1]);
        if (partes.length == 3 && partes[2].equals("PM") && hora != 12) {
            hora += 12;
        } else if (partes.length == 3 && partes[2].equals("AM") && hora == 12) {
            hora = 0;
        }

        // Configurar el Calendar con la hora seleccionada
        Calendar calendario = Calendar.getInstance();
        calendario.set(Calendar.HOUR_OF_DAY, hora);
        calendario.set(Calendar.MINUTE, minuto);
        calendario.set(Calendar.SECOND, 0);

        // Si la hora seleccionada ya pasó hoy, configurar la alarma para mañana
        Calendar ahora = Calendar.getInstance();
        if (calendario.before(ahora)) {
            calendario.add(Calendar.DATE, 1);
        }

        // Crear el Intent para el BroadcastReceiver
        Intent intent = new Intent(this, AlarmaReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Configurar el AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendario.getTimeInMillis(), pendingIntent);
            Toast.makeText(this, "Alarma configurada para las " + horaAdministracion, Toast.LENGTH_LONG).show();
        }
    }


}