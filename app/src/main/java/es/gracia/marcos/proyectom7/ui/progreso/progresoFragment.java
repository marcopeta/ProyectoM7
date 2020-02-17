package es.gracia.marcos.proyectom7.ui.progreso;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.gracia.marcos.proyectom7.CajaNavegacionActivity;
import es.gracia.marcos.proyectom7.MainActivity;
import es.gracia.marcos.proyectom7.R;
import es.gracia.marcos.proyectom7.RegistroActivity;

import static java.lang.Float.parseFloat;

public class progresoFragment extends Fragment {

    View root;
    EditText etIMC;
    Button btnAnadir;
    BarChart barChart;
    private DatabaseReference mDatabase;
    ArrayList<BarEntry> barEntries = new ArrayList();
    ArrayList<String> theDates = new ArrayList();
    Calendar currentTime;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_progreso, container, false);
        currentTime = Calendar.getInstance();
        etIMC = root.findViewById(R.id.etIMC);
        btnAnadir = root.findViewById(R.id.btnAnadir);
        mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + CajaNavegacionActivity.getUser().getUid());
        btnAnadir.setTransformationMethod(null);
        btnAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prueba = etIMC.getText().toString();
                if (!prueba.isEmpty()) {
                    if (Double.parseDouble(prueba) > 10.0 && Double.parseDouble(prueba) < 55.0) {
                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child("progreso").exists()) {
                                    boolean existe = true;
                                    int contador = 0;
                                    String datosDia = dataSnapshot.child("progreso").child("0").child("dia").getValue().toString();
                                    Toast.makeText(getContext(), datosDia + " = " + (currentTime.get(Calendar.DAY_OF_MONTH) + "/" + (currentTime.get(Calendar.MONTH) + 1) + "/" + currentTime.get(Calendar.YEAR)), Toast.LENGTH_SHORT).show();
                                    if (datosDia.equals(currentTime.get(Calendar.DAY_OF_MONTH) + "/" + (currentTime.get(Calendar.MONTH) + 1) + "/" + currentTime.get(Calendar.YEAR))) {
                                        mDatabase.child("progreso").child((dataSnapshot.child("progreso").getChildrenCount()-1)+"").child("imc").setValue(parseFloat(etIMC.getText().toString()));
                                        etIMC.setText("");
                                        cargarTabla();
                                    } else {
                                        anadirProgreso();
                                    }
                                } else {
                                    anadirProgreso();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "Introduce un numero entre 10 y 55", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "El campo esta vacio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        barChart = root.findViewById(R.id.prograsoChart);
        barChart.setScaleEnabled(true);
        barChart.setFitBars(true);
        barChart.setDoubleTapToZoomEnabled(false);

        cargarTabla();

        return root;
    }

    private void anadirProgreso() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("progreso").getChildrenCount() > 14) {
                    String dia;
                    Float imc;
                    for (int i = 1; i <= 14; i++) {
                        dia = dataSnapshot.child("progreso").child("" + i).child("dia").getValue().toString();
                        imc = parseFloat(dataSnapshot.child("progreso").child("" + i).child("imc").getValue().toString());
                        mDatabase.child("progreso").child("" + (i - 1)).child("dia").setValue(dia);
                        mDatabase.child("progreso").child("" + (i - 1)).child("imc").setValue(imc);
                    }
                    dia = currentTime.get(Calendar.DAY_OF_MONTH) + "/" + (currentTime.get(Calendar.MONTH) + 1) + "/" + currentTime.get(Calendar.YEAR);
                    imc = parseFloat(etIMC.getText().toString());
                    mDatabase.child("progreso").child("" + (14)).child("dia").setValue(dia);
                    mDatabase.child("progreso").child("" + (14)).child("imc").setValue(imc);
                    etIMC.setText("");


                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("dia", currentTime.get(Calendar.DAY_OF_MONTH) + "/" + (currentTime.get(Calendar.MONTH) + 1) + "/" + currentTime.get(Calendar.YEAR));
                    map.put("imc", etIMC.getText().toString());
                    etIMC.setText("");
                    long indice = dataSnapshot.child("progreso").getChildrenCount();
                    mDatabase.child("progreso").child(indice + "").setValue(map);
                }
                cargarTabla();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void cargarTabla() {
        barEntries = new ArrayList();
        theDates = new ArrayList();


        mDatabase.child("progreso").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Float ultimoNum = 0f;

                    for (int i = 0; i < dataSnapshot.getChildrenCount(); i++){
                        String dia = dataSnapshot.child(i+"").child("dia").getValue().toString();
                        Float imc = parseFloat(dataSnapshot.child(i+"").child("imc").getValue().toString());
                        theDates.add(dia);
                        barEntries.add(new BarEntry(i, imc));
                        ultimoNum = imc;
                    }

                    formatoTabla(dataSnapshot, ultimoNum);
                } else {
                    barChart.setNoDataText("Aun no hay info");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                barChart.setNoDataText("Aun no hay info");
            }
        });
    }

    private void formatoTabla(DataSnapshot dataSnapshot, Float ultimoNum) {
        BarDataSet barDataSet = new BarDataSet(barEntries,"IMCs");
        barDataSet.setValueTextSize(15);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData theData = new BarData(barDataSet);
        barChart.setData(theData);
        barChart.zoom(1f,1f,1f,1f);

        switch((int) dataSnapshot.getChildrenCount() - 1){
            case 5:
                barChart.zoom(1.3f,1f,2f,1f);
                break;
            case 6:
                barChart.zoom(1.4f,1f,2f,1f);
                break;
            case 7:
                barChart.zoom(1.5f,1f,2f,1f);
                break;
            case 8:
                barChart.zoom(1.6f,1f,2f,1f);
                break;
            case 9:
                barChart.zoom(1.7f,1f,2f,1f);
                break;
            case 10:
                barChart.zoom(1.8f,1f,2f,1f);
                break;
            case 11:
                barChart.zoom(1.9f,1f,2f,1f);
                break;
            case 12:
                barChart.zoom(2f,1f,2f,1f);
                break;
            case 13:
                barChart.zoom(2.2f,1f,2f,1f);
                break;
            case 14:
                barChart.zoom(2.25f,1f,2f,1f);
                break;
            case 15:
                barChart.zoom(2.5f,1f,2f,1f);
                break;

        }

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(theDates));

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMaximum(ultimoNum + 10);
        yAxis.setAxisMinimum(ultimoNum - 10);
        yAxis.setLabelCount(20);
        yAxis.setTextSize(15);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);
        Legend legend = barChart.getLegend();
        legend.setEnabled(false);
        Description description = barChart.getDescription();
        description.setEnabled(false);

        barChart.animateY(2000);
        barChart.invalidate();
    }
}