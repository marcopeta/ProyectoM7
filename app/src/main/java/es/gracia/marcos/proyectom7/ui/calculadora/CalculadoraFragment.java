package es.gracia.marcos.proyectom7.ui.calculadora;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import es.gracia.marcos.proyectom7.R;

public class CalculadoraFragment extends Fragment {
    private Spinner sistema;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calculadora, container, false);
        sistema = root.findViewById(R.id.sp_sistema);
        String[] tipoSistema = {"Sistema Ingles", "Sistema Metrico"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, tipoSistema);
        sistema.setAdapter(adapter);
        return root;
    }
}
