package com.example.clinicav2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CitasAdapter extends RecyclerView.Adapter<CitasAdapter.citasView> implements View.OnClickListener {

    private List<CITAS> citasList = new ArrayList<>();
    private Context context;

    private ArrayList<CITAS>citasArrayList;
    private View.OnClickListener listener;

    public CitasAdapter(Context context, ArrayList<CITAS> citasList) {
        this.citasList = citasList;
        this.context = context;
        this.citasArrayList=citasList;
    }

    @NonNull
    @Override
    public citasView onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_consultacitas, viewGroup, false);

        view.setOnClickListener(this);

        return new citasView(view);
    }

    @Override
    public void onBindViewHolder(citasView citasView, int i) {
        CITAS citas=citasList.get(i);
        citasView.TxtId.setText(citas.getStringId());
        citasView.TxtNombre.setText(citas.getStringPaciente());
        citasView.TxtFechaHora.setText(citas.getStringFecha());
        citasView.TxtEstado.setText(citas.getStringEstado());

    }

    @Override
    public int getItemCount() {
        return citasList.size();
    }

    public void agregarCitaAlista(CITAS citas){
        citasList.add(citas);
        this.notifyDataSetChanged();
    }

    public void setOnclickListener(View.OnClickListener listener){
        this.listener=listener;
    }


    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public class citasView extends RecyclerView.ViewHolder{

        private TextView TxtFechaHora, TxtEstado, TxtNombre, TxtId;

        public citasView(@NonNull View itemView) {
            super(itemView);

            TxtId=itemView.findViewById(R.id.TxtId);
            TxtFechaHora=itemView.findViewById(R.id.TxtFechaHora);
            TxtEstado=itemView.findViewById(R.id.TxtEstado);
            TxtNombre=itemView.findViewById(R.id.TxtNombre);
        }
    }
}
