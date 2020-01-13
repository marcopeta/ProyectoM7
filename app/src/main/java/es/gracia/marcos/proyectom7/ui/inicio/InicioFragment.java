package es.gracia.marcos.proyectom7.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import es.gracia.marcos.proyectom7.R;

public class InicioFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inicio, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        Toolbar barra = root.findViewById(R.id.toolbar);
        //barra.setBackground(getResources().getDrawable(R.color.white));

        return root;
    }
}