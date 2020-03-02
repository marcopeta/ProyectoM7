package es.gracia.marcos.proyectom7.ui.inicio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

import es.gracia.marcos.proyectom7.AdapterAlimentos;
import es.gracia.marcos.proyectom7.AdapterAlimentosDia;
import es.gracia.marcos.proyectom7.AdapterAnadirDia;
import es.gracia.marcos.proyectom7.AnadirAlimentoActivity;
import es.gracia.marcos.proyectom7.CajaNavegacionActivity;
import es.gracia.marcos.proyectom7.R;
import es.gracia.marcos.proyectom7.ui.alimentos.Alimento;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class AnadirAlimentoDiaActivity extends AppCompatActivity {


    private final LinkedList<Alimento> listaAlimentos = new LinkedList<>();
    private RecyclerView recyclerAlimentos;
    private AdapterAnadirDia aAdapter;
    private DatabaseReference mDatabase;
    private ProgressDialog mDialog;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_alimento_dia);
        mDialog = new ProgressDialog(AnadirAlimentoDiaActivity.this);


        //View root = inflater.inflate(R.layout.activity_anadir_alimento_dia, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + CajaNavegacionActivity.getUser().getUid());

        mDialog.setMessage(getApplicationContext().getResources().getString(R.string.wait_dialog));
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String nombre;
                    String marca;
                    Float cantidad;
                    String unidad;
                    Float grasas;
                    Float hidratos;
                    Float proteinas;
                    int calorias;
                    listaAlimentos.clear();
                    Long acaba = dataSnapshot.child("alimentos").getChildrenCount();
                    for (int i = 0; i < acaba; i++) {
                        if (dataSnapshot.child("alimentos").child(i + "").exists()) {
                            nombre = dataSnapshot.child("alimentos").child(i + "").child("nombre").getValue().toString();
                            marca = dataSnapshot.child("alimentos").child(i + "").child("marca").getValue().toString();
                            cantidad = parseFloat(dataSnapshot.child("alimentos").child(i + "").child("cantidad").getValue().toString());
                            unidad = dataSnapshot.child("alimentos").child(i + "").child("unidad").getValue().toString();
                            grasas = parseFloat(dataSnapshot.child("alimentos").child(i + "").child("grasas").getValue().toString());
                            hidratos = parseFloat(dataSnapshot.child("alimentos").child(i + "").child("hidratos").getValue().toString());
                            proteinas = parseFloat(dataSnapshot.child("alimentos").child(i + "").child("proteinas").getValue().toString());
                            calorias = parseInt(dataSnapshot.child("alimentos").child(i + "").child("calorias").getValue().toString());
                            listaAlimentos.add(new Alimento(nombre, marca, cantidad, unidad, grasas, hidratos, proteinas, calorias));
                        } else {
                            acaba++;
                        }
                        mDialog.dismiss();

                    }
                    recyclerAlimentos = findViewById(R.id.listadoAlimentos);
                    aAdapter = new AdapterAnadirDia(AnadirAlimentoDiaActivity.this, listaAlimentos);
                    recyclerAlimentos.setAdapter(aAdapter);
                    recyclerAlimentos.setLayoutManager(new LinearLayoutManager(AnadirAlimentoDiaActivity.this));
                } catch (Exception ex) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mDialog.dismiss();
            }
        });
    }

}
