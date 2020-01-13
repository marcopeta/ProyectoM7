package es.gracia.marcos.proyectom7.ui.alimentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import es.gracia.marcos.proyectom7.R;

public class AlimentosFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_alimentos, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        return root;
    }
}