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

    private final LinkedList<Alimento> listaAlimentos = new LinkedList<>();
    private RecyclerView recyclerAlimentos;
    private AdapterAlimentos aAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_alimentos, container, false);
        listaAlimentos.add(new Alimento("Papaya", 120));
        listaAlimentos.add(new Alimento("Manzana", 52));
        listaAlimentos.add(new Alimento("Arroz", 250));
        listaAlimentos.add(new Alimento("Platano", 100));

        recyclerAlimentos = root.findViewById(R.id.listadoAlimentos);
        aAdapter = new AdapterAlimentos(getContext(), listaAlimentos);
        recyclerAlimentos.setAdapter(aAdapter);
        recyclerAlimentos.setLayoutManager(new LinearLayoutManager(getContext()));
        
        return root;
    }
}