package es.gracia.marcos.proyectom7.ui.calculadora;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;

import es.gracia.marcos.proyectom7.R;

public class CalculadoraFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private Spinner sistema;
    private String textoSistema;
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

        String[] tipoSistema = {"Sistema Metrico", "Sistema Ingles"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, tipoSistema);
        sistema.setAdapter(adapter);
        sistema.setOnItemSelectedListener(this);

        seekBar = (SeekBar)root.findViewById(R.id.seekBar);
        seekBar2 = (SeekBar)root.findViewById(R.id.seekBar2);

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        try {
                            if (textoSistema.equals("Sistema Ingles")) {
                                mostrarPorcentajeAltura.setText(String.valueOf(progress) + "in");
                                String peso = (String) mostrarPorcentajePeso.getText();
                                peso = peso.substring(0, (peso.length() - 2));
                                String altura = (String) mostrarPorcentajeAltura.getText();
                                altura = altura.substring(0, (altura.length() - 2));
                                double calculo = (Integer.parseInt(peso) / Math.pow(Double.parseDouble(altura), 2)) * 703;

                                DecimalFormat df = new DecimalFormat("#.00");
                                resultado.setText(String.valueOf(df.format(calculo) + " ICM"));
                            } else {
                                mostrarPorcentajeAltura.setText(String.valueOf(progress) + "cm");
                                String peso = (String) mostrarPorcentajePeso.getText();
                                peso = peso.substring(0, (peso.length() - 2));
                                String altura = (String) mostrarPorcentajeAltura.getText();
                                altura = altura.substring(0, (altura.length() - 2));
                                double calculo = Integer.parseInt(peso) / Math.pow(Double.parseDouble(altura) * 0.01, 2);

                                DecimalFormat df = new DecimalFormat("#.00");
                                resultado.setText(String.valueOf(df.format(calculo) + " ICM"));
                            }
                        } catch (Exception ex) {

                        }
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
                        try {
                            if (textoSistema.equals("Sistema Ingles")) {
                                mostrarPorcentajePeso.setText(String.valueOf(progress) + "lb");
                                String peso = (String) mostrarPorcentajePeso.getText();
                                peso = peso.substring(0, (peso.length() - 2));
                                String altura = (String) mostrarPorcentajeAltura.getText();
                                altura = altura.substring(0, (altura.length() - 2));
                                double calculo = (Integer.parseInt(peso) / Math.pow(Double.parseDouble(altura), 2)) * 703;

                                DecimalFormat df = new DecimalFormat("#.00");
                                resultado.setText(String.valueOf(df.format(calculo) + " ICM"));
                            } else {
                                mostrarPorcentajePeso.setText(String.valueOf(progress) + "kg");
                                String peso = (String) mostrarPorcentajePeso.getText();
                                peso = peso.substring(0, (peso.length() - 2));
                                String altura = (String) mostrarPorcentajeAltura.getText();
                                altura = altura.substring(0, (altura.length() - 2));
                                double calculo = Integer.parseInt(peso) / Math.pow(Double.parseDouble(altura) * 0.01, 2);

                                DecimalFormat df = new DecimalFormat("#.00");
                                resultado.setText(String.valueOf(df.format(calculo) + " ICM"));
                            }
                        } catch (Exception ex) {

                        }
                    }
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });

        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            textoSistema = parent.getItemAtPosition(position).toString();
            if (textoSistema.equals("Sistema Ingles")) {
                String altura = (String) mostrarPorcentajeAltura.getText();
                String peso = (String) mostrarPorcentajePeso.getText();

                if (altura.charAt((altura.length() - 1)) == 'm') {
                    altura = altura.substring(0, (altura.length() - 2));
                    int progresAltura = (int) (Integer.parseInt(altura) / 2.54);
                    altura += "in";

                    peso = peso.substring(0, (peso.length() - 2));
                    int progresPeso = (int) (Integer.parseInt(peso) * 2.205);
                    peso += "lb";

                    mostrarPorcentajeAltura.setText(altura);
                    seekBar.setMax(86);
                    seekBar.setProgress(progresAltura);
                    mostrarPorcentajePeso.setText(peso);
                    seekBar2.setMax(440);
                    seekBar2.setProgress(progresPeso);
                }

            } else {
                String altura = (String) mostrarPorcentajeAltura.getText();
                String peso = (String) mostrarPorcentajePeso.getText();

                if (altura.charAt((altura.length() - 1)) == 'n') {
                    altura = altura.substring(0, (altura.length() - 2));
                    int progresAltura = (int) (Integer.parseInt(altura) * 2.54);
                    altura += "cm";

                    peso = peso.substring(0, (peso.length() - 2));
                    int progresPeso = (int) (Integer.parseInt(peso) / 2.205);
                    peso += "kg";

                    mostrarPorcentajeAltura.setText(altura);
                    seekBar.setMax(220);
                    seekBar.setProgress(progresAltura);
                    mostrarPorcentajePeso.setText(peso);
                    seekBar2.setMax(200);
                    seekBar2.setProgress(progresPeso);
                }
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
