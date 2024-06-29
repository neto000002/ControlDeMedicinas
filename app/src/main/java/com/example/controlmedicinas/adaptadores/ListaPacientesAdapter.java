package com.example.controlmedicinas.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controlmedicinas.R;
import com.example.controlmedicinas.VerActivity;
import com.example.controlmedicinas.entidades.Pacientes;

import java.util.ArrayList;

public class ListaPacientesAdapter extends RecyclerView.Adapter<ListaPacientesAdapter.PacienteViewHolder> {

    ArrayList<Pacientes> listaPacientes;

    public ListaPacientesAdapter(ArrayList<Pacientes>listaPacientes){
        this.listaPacientes = listaPacientes;
    }

    @NonNull
    @Override
    public PacienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_pacientes,null,false);
        return new PacienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PacienteViewHolder holder, int position) {
        holder.viewNombre.setText(listaPacientes.get(position).getNombre());
        holder.viewEdad.setText(listaPacientes.get(position).getEdad());
        holder.viewMedicamento.setText(listaPacientes.get(position).getMedicamento());
        holder.viewDosis.setText(listaPacientes.get(position).getDosis());
        holder.viewAdministracion.setText(listaPacientes.get(position).getAdministracion());
        holder.viewHora_Administracion.setText(listaPacientes.get(position).getHora_administracion());


    }

    @Override
    public int getItemCount() {
        return listaPacientes.size();
    }

    public class PacienteViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewEdad, viewMedicamento, viewDosis, viewAdministracion, viewHora_Administracion;
        public PacienteViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById((R.id.viewNombre));
            viewEdad = itemView.findViewById((R.id.viewEdad));
            viewMedicamento = itemView.findViewById((R.id.viewMedicamento));
            viewDosis = itemView.findViewById((R.id.viewDosis));
            viewAdministracion = itemView.findViewById((R.id.viewAdministracion));
            viewHora_Administracion = itemView.findViewById((R.id.viewHora));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, VerActivity.class);
                    intent.putExtra("ID", listaPacientes.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });

        }
    }
}
