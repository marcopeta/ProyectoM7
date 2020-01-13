package es.gracia.marcos.proyectom7.ui.alimentos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;

import es.gracia.marcos.proyectom7.AdapterAlimentos;
import es.gracia.marcos.proyectom7.R;

public class AlimentosFragment extends Fragment {

    private final LinkedList<String> listaAlimentos = new LinkedList<String>();
    private RecyclerView recyclerAlimentos;
    private AdapterAlimentos aAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_alimentos, container, false);
        llenarLista();

        recyclerAlimentos = root.findViewById(R.id.listadoAlimentos);
        recyclerAlimentos.setLayoutManager(new LinearLayoutManager(getContext()));

        AdapterAlimentos adapter = new AdapterAlimentos(getContext(), listaAlimentos);
        recyclerAlimentos.setAdapter(adapter);
        recyclerAlimentos.setLayoutManager(new LinearLayoutManager(getContext()));
        
        return root;
    }

    private void llenarLista() {
        listaAlimentos.add("Papaya");
        listaAlimentos.add("Platano");
        listaAlimentos.add("Manzana");
        listaAlimentos.add("Pepino");
    }
}