package es.gracia.marcos.proyectom7.ui.calculadora;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import es.gracia.marcos.proyectom7.R;

public class CalculadoraFragment extends Fragment {
    private Spinner sistema;
    private TextView mostrarPorcentaje;
    private SeekBar seekBar;

    private TextView mostrarPorcentaje2;
    private SeekBar seekBar2;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calculadora, container, false);
        sistema = root.findViewById(R.id.sp_sistema);
        mostrarPorcentaje = (TextView) root.findViewById(R.id.textView5);
        mostrarPorcentaje2 = (TextView) root.findViewById(R.id.textView6);


        String[] tipoSistema = {"Sistema Ingles", "Sistema Metrico"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, tipoSistema);
        sistema.setAdapter(adapter);

        seekBar = (SeekBar)root.findViewById(R.id.seekBar);
        seekBar.setProgress(0);
        seekBar.setMax(100);

        seekBar2 = (SeekBar)root.findViewById(R.id.seekBar2);
        seekBar2.setProgress(0);
        seekBar2.setMax(100);

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        mostrarPorcentaje.setText(String.valueOf(progress));
                    }
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });

        seekBar2.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        mostrarPorcentaje2.setText(String.valueOf(progress));
                    }
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });

        return root;
    }
}
