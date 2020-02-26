package es.gracia.marcos.proyectom7.ui.inicio;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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

import es.gracia.marcos.proyectom7.AdapterAlimentosCalendario;
import es.gracia.marcos.proyectom7.AdapterAlimentosDia;
import es.gracia.marcos.proyectom7.CajaNavegacionActivity;
import es.gracia.marcos.proyectom7.MainActivity;
import es.gracia.marcos.proyectom7.R;
import es.gracia.marcos.proyectom7.RegistroActivity;
import es.gracia.marcos.proyectom7.ui.alimentos.Alimento;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class InicioFragment extends Fragment {
    private TextView tvDia;
    private DatabaseReference mDatabase;
    private final LinkedList<Alimento> listadoAlimentosDia = new LinkedList<>();
    private RecyclerView recyclerAlimentos;
    private ProgressDialog mDialog;
    String diaActual;
    View root;
    private AdapterAlimentosDia aAdapter;

    Calendar currentTime;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_inicio, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        mDialog = new ProgressDialog(getContext());
        tvDia = root.findViewById(R.id.tvDia);
        mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + CajaNavegacionActivity.getUser().getUid());
        currentTime = Calendar.getInstance();
        diaActual = currentTime.get(Calendar.DAY_OF_MONTH) + "-" + (currentTime.get(Calendar.MONTH) + 1) + "-" + currentTime.get(Calendar.YEAR);
        tvDia.setText(currentTime.get(Calendar.DAY_OF_MONTH) + "/" + (currentTime.get(Calendar.MONTH) + 1) + "/" + currentTime.get(Calendar.YEAR));


        mDialog.setMessage("Espera un momento...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nombre;
                String marca;
                Float cantidad;
                String unidad;
                Float grasas;
                Float hidratos;
                Float proteinas;
                int calorias;
                listadoAlimentosDia.clear();
                Long acaba = dataSnapshot.child("calendario").child(diaActual).getChildrenCount();
                for (int i = 0; i < acaba; i++){
                    if (dataSnapshot.child("calendario").child(diaActual).child(i+"").exists()){
                        nombre = dataSnapshot.child("calendario").child(diaActual).child(i+"").child("nombre").getValue().toString();
                        marca = dataSnapshot.child("calendario").child(diaActual).child(i+"").child("marca").getValue().toString();
                        cantidad = parseFloat(dataSnapshot.child("calendario").child(diaActual).child(i+"").child("cantidad").getValue().toString());
                        unidad = dataSnapshot.child("calendario").child(diaActual).child(i+"").child("unidad").getValue().toString();
                        grasas = parseFloat(dataSnapshot.child("calendario").child(diaActual).child(i+"").child("grasas").getValue().toString());
                        hidratos = parseFloat(dataSnapshot.child("calendario").child(diaActual).child(i + "").child("hidratos").getValue().toString());
                        proteinas = parseFloat(dataSnapshot.child("calendario").child(diaActual).child(i + "").child("proteinas").getValue().toString());
                        calorias = parseInt(dataSnapshot.child("calendario").child(diaActual).child(i + "").child("calorias").getValue().toString());
                        listadoAlimentosDia.add(new Alimento(nombre, marca, cantidad, unidad, grasas, hidratos, proteinas, calorias));
                    } else {
                        acaba++;
                    }
                }
                recyclerAlimentos = root.findViewById(R.id.listadoAlimentosDia);
                aAdapter = new AdapterAlimentosDia(getContext(), listadoAlimentosDia);
                recyclerAlimentos.setAdapter(aAdapter);
                recyclerAlimentos.setLayoutManager(new LinearLayoutManager(getContext()));
                mDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mDialog.dismiss();
            }
        });

        return root;
    }


}