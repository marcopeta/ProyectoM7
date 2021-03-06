package es.gracia.marcos.proyectom7.ui.calendario;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Locale;

import es.gracia.marcos.proyectom7.AdapterAlimentos;
import es.gracia.marcos.proyectom7.AdapterAlimentosCalendario;
import es.gracia.marcos.proyectom7.CajaNavegacionActivity;
import es.gracia.marcos.proyectom7.R;
import es.gracia.marcos.proyectom7.ui.alimentos.Alimento;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class CalendarioFragment extends Fragment {

    private CalendarView calendario;
    private final LinkedList<Alimento> listaAlimentos = new LinkedList<>();
    private RecyclerView recyclerAlimentos;
    private AdapterAlimentosCalendario aAdapter;
    private DatabaseReference mDatabase;
    private TextView tvGrasas, tvHidratos, tvProteinas, tvCalorias;
    String dia;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_calendario, container, false);
        calendario = root.findViewById(R.id.calendario);
        mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + CajaNavegacionActivity.getUser().getUid());
        tvGrasas = root.findViewById(R.id.grasasT);
        tvHidratos = root.findViewById(R.id.hidratosT);
        tvProteinas = root.findViewById(R.id.proteinasT);
        tvCalorias = root.findViewById(R.id.caloriasT);

        cargarDiaInicio();

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dia = dayOfMonth + "-" + (month+1) + "-" + year;
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            Float grasasTotal = 0.00f, hidratosTotal = 0.00f, proteinasTotal = 0.00f;
                            int caloriasTotal = 0;
                            String nombre;
                            String marca;
                            Float cantidad;
                            String unidad;
                            Float grasas;
                            Float hidratos;
                            Float proteinas;
                            int calorias;
                            listaAlimentos.clear();
                            Long acaba = dataSnapshot.child("calendario").child(dia).getChildrenCount();
                            for (int i = 0; i < acaba; i++) {
                                if (dataSnapshot.child("calendario").child(dia).child(i + "").exists()) {
                                    nombre = dataSnapshot.child("calendario").child(dia).child(i + "").child("nombre").getValue().toString();
                                    marca = dataSnapshot.child("calendario").child(dia).child(i + "").child("marca").getValue().toString();
                                    cantidad = parseFloat(dataSnapshot.child("calendario").child(dia).child(i + "").child("cantidad").getValue().toString());
                                    unidad = dataSnapshot.child("calendario").child(dia).child(i + "").child("unidad").getValue().toString();
                                    grasas = parseFloat(dataSnapshot.child("calendario").child(dia).child(i + "").child("grasas").getValue().toString());
                                    hidratos = parseFloat(dataSnapshot.child("calendario").child(dia).child(i + "").child("hidratos").getValue().toString());
                                    proteinas = parseFloat(dataSnapshot.child("calendario").child(dia).child(i + "").child("proteinas").getValue().toString());
                                    calorias = parseInt(dataSnapshot.child("calendario").child(dia).child(i + "").child("calorias").getValue().toString());
                                    grasasTotal += parseFloat(dataSnapshot.child("calendario").child(dia).child(i + "").child("grasas").getValue().toString());
                                    hidratosTotal += parseFloat(dataSnapshot.child("calendario").child(dia).child(i + "").child("hidratos").getValue().toString());
                                    proteinasTotal += parseFloat(dataSnapshot.child("calendario").child(dia).child(i + "").child("proteinas").getValue().toString());
                                    caloriasTotal += parseInt(dataSnapshot.child("calendario").child(dia).child(i + "").child("calorias").getValue().toString());
                                    listaAlimentos.add(new Alimento(nombre, marca, cantidad, unidad, grasas, hidratos, proteinas, calorias));
                                } else {
                                    acaba++;
                                }
                            }
                            tvGrasas.setText("G: " + String.format(Locale.US, "%.2f", grasasTotal));
                            tvHidratos.setText("H: " + String.format(Locale.US, "%.2f", hidratosTotal));
                            tvProteinas.setText("P: " + String.format(Locale.US, "%.2f", proteinasTotal));
                            tvCalorias.setText("Kcal: " + caloriasTotal);
                            recyclerAlimentos = root.findViewById(R.id.listadoAlimentosCalendario);
                            aAdapter = new AdapterAlimentosCalendario(getContext(), listaAlimentos);
                            recyclerAlimentos.setAdapter(aAdapter);
                            recyclerAlimentos.setLayoutManager(new LinearLayoutManager(getContext()));
                        } catch (Exception ex) {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
        return root;
    }

    private void cargarDiaInicio() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    Calendar currentTime = Calendar.getInstance();
                    String diaHoy = currentTime.get(Calendar.DAY_OF_MONTH) + "-" + (currentTime.get(Calendar.MONTH) + 1) + "-" + currentTime.get(Calendar.YEAR);

                    Float grasasTotal = 0.00f, hidratosTotal = 0.00f, proteinasTotal = 0.00f;
                    int caloriasTotal = 0;
                    String nombre;
                    String marca;
                    Float cantidad;
                    String unidad;
                    Float grasas;
                    Float hidratos;
                    Float proteinas;
                    int calorias;
                    listaAlimentos.clear();
                    Long acaba = dataSnapshot.child("calendario").child(diaHoy).getChildrenCount();
                    for (int i = 0; i < acaba; i++) {
                        if (dataSnapshot.child("calendario").child(diaHoy).child(i + "").exists()) {
                            nombre = dataSnapshot.child("calendario").child(diaHoy).child(i + "").child("nombre").getValue().toString();
                            marca = dataSnapshot.child("calendario").child(diaHoy).child(i + "").child("marca").getValue().toString();
                            cantidad = parseFloat(dataSnapshot.child("calendario").child(diaHoy).child(i + "").child("cantidad").getValue().toString());
                            unidad = dataSnapshot.child("calendario").child(diaHoy).child(i + "").child("unidad").getValue().toString();
                            grasas = parseFloat(dataSnapshot.child("calendario").child(diaHoy).child(i + "").child("grasas").getValue().toString());
                            hidratos = parseFloat(dataSnapshot.child("calendario").child(diaHoy).child(i + "").child("hidratos").getValue().toString());
                            proteinas = parseFloat(dataSnapshot.child("calendario").child(diaHoy).child(i + "").child("proteinas").getValue().toString());
                            calorias = parseInt(dataSnapshot.child("calendario").child(diaHoy).child(i + "").child("calorias").getValue().toString());
                            grasasTotal += parseFloat(dataSnapshot.child("calendario").child(diaHoy).child(i + "").child("grasas").getValue().toString());
                            hidratosTotal += parseFloat(dataSnapshot.child("calendario").child(diaHoy).child(i + "").child("hidratos").getValue().toString());
                            proteinasTotal += parseFloat(dataSnapshot.child("calendario").child(diaHoy).child(i + "").child("proteinas").getValue().toString());
                            caloriasTotal += parseInt(dataSnapshot.child("calendario").child(diaHoy).child(i + "").child("calorias").getValue().toString());
                            listaAlimentos.add(new Alimento(nombre, marca, cantidad, unidad, grasas, hidratos, proteinas, calorias));
                        } else {
                            acaba++;
                        }
                    }
                    tvGrasas.setText("G: " + String.format(Locale.US, "%.2f", grasasTotal));
                    tvHidratos.setText("H: " + String.format(Locale.US, "%.2f", hidratosTotal));
                    tvProteinas.setText("P: " + String.format(Locale.US, "%.2f", proteinasTotal));
                    tvCalorias.setText("Kcal: " + caloriasTotal);
                    recyclerAlimentos = root.findViewById(R.id.listadoAlimentosCalendario);
                    aAdapter = new AdapterAlimentosCalendario(getContext(), listaAlimentos);
                    recyclerAlimentos.setAdapter(aAdapter);
                    recyclerAlimentos.setLayoutManager(new LinearLayoutManager(getContext()));
                } catch (Exception ex) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}