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

import java.text.DecimalFormat;

import es.gracia.marcos.proyectom7.R;

public class CalculadoraFragment extends Fragment {
    private Spinner sistema;
    private TextView mostrarPorcentajeAltura, mostrarPorcentajePeso, resultado;
    private SeekBar seekBar, seekBar2;

    private int pesoN;
    private int estaturaN;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calculadora, container, false);
        sistema = root.findViewById(R.id.sp_sistema);
        mostrarPorcentajeAltura = (TextView) root.findViewById(R.id.tv_altura);
        mostrarPorcentajePeso = (TextView) root.findViewById(R.id.tv_peso);
        resultado = (TextView) root.findViewById(R.id.tv_resultado);

        String[] tipoSistema = {"Sistema Metric", "Sistema Ingles"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, tipoSistema);
        sistema.setAdapter(adapter);

        seekBar = (SeekBar)root.findViewById(R.id.seekBar);
        seekBar2 = (SeekBar)root.findViewById(R.id.seekBar2);

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        mostrarPorcentajeAltura.setText(String.valueOf(progress) + "cm");
                        String peso = (String) mostrarPorcentajePeso.getText();
                        peso = peso.substring(0,(peso.length() - 2));
                        String altura = (String) mostrarPorcentajeAltura.getText();
                        altura = altura.substring(0,(altura.length() - 2));
                        double calculo = Integer.parseInt(peso)/((Integer.parseInt(altura)*0.01)*(Integer.parseInt(altura)*0.01));

                        DecimalFormat df = new DecimalFormat("#.00");
                        resultado.setText(String.valueOf(df.format(calculo) + " ICM"));
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
                        mostrarPorcentajePeso.setText(String.valueOf(progress) + "kg");
                        String peso = (String) mostrarPorcentajePeso.getText();
                        peso = peso.substring(0,(peso.length() - 2));
                        String altura = (String) mostrarPorcentajeAltura.getText();
                        altura = altura.substring(0,(altura.length() - 2));
                        double calculo = Integer.parseInt(peso)/((Integer.parseInt(altura)*0.01)*(Integer.parseInt(altura)*0.01));

                        DecimalFormat df = new DecimalFormat("#.00");
                        resultado.setText(String.valueOf(df.format(calculo) + " ICM"));
                    }
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });

        return root;
    }
}
