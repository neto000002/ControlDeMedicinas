package com.example.controlmedicinas;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controlmedicinas.adaptadores.ListaPacientesAdapter;
import com.example.controlmedicinas.db.DbHelper;
import com.example.controlmedicinas.db.DbPacientes;
import com.example.controlmedicinas.entidades.Pacientes;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    RecyclerView listaPacientes;
    ArrayList<Pacientes> listaArrayPacientes;
    ListaPacientesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaPacientes = findViewById(R.id.listPacientes);
        listaPacientes.setLayoutManager(new LinearLayoutManager(this));

        // Configurar el adaptador con los datos actuales de la base de datos
        DbPacientes dbPacientes = new DbPacientes(MainActivity.this);
        listaArrayPacientes = dbPacientes.mostrarPacientes();
        adapter = new ListaPacientesAdapter(listaArrayPacientes);
        listaPacientes.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recargar la lista de pacientes cada vez que la actividad se reanuda
        DbPacientes dbPacientes = new DbPacientes(MainActivity.this);
        listaArrayPacientes.clear();
        listaArrayPacientes.addAll(dbPacientes.mostrarPacientes());
        adapter.notifyDataSetChanged();
    }

    // Crear opciones del menú
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal,menu);
        return true;
    }

    // Manejar la selección de elementos del menú
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() ==R.id.menuNuevo ) {
            nuevoRegistro();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    // Abrir la actividad para agregar un nuevo registro
    private void nuevoRegistro(){
        Intent intent = new Intent(this,NuevoActivity.class);
        startActivity(intent);
    }
}
