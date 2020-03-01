package es.gracia.marcos.proyectom7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.Map;

import es.gracia.marcos.proyectom7.ui.alimentos.Alimento;
import es.gracia.marcos.proyectom7.ui.alimentos.AlimentosFragment;

public class ModificarAlimentoActivity extends AppCompatActivity {

    private final LinkedList<Alimento> listaAlimentos = new LinkedList<>();
    private RecyclerView recyclerAlimentos;
    private AdapterAlimentos aAdapter;
    private DatabaseReference mDatabase;
    EditText etUnidad, etCantidad, etGrasas, etHidratos, etProteinas, etCalorias;
    TextView etNombre, etMarca;
    private boolean backEnabled = false;
    private int posicion;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_alimento);
        Toolbar toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().setTitle(R.string.modify_aliment);
        mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + CajaNavegacionActivity.getUser().getUid());
        etNombre = findViewById(R.id.tvModificarNombre);
        etMarca = findViewById(R.id.tvModificarMarca);
        etUnidad = findViewById(R.id.etModificarUnidad);
        etCantidad = findViewById(R.id.etModificarCantidad);
        etGrasas = findViewById(R.id.etModificarGrasas);
        etHidratos = findViewById(R.id.etModificarHidratos);
        etProteinas = findViewById(R.id.etModificarProteinas);
        etCalorias = findViewById(R.id.etModificarCalorias);

        Intent intent = getIntent();

        etNombre.setText(intent.getStringExtra("nombre"));
        etMarca.setText(intent.getStringExtra("marca"));
        etUnidad.setText(intent.getStringExtra("unidad"));
        etCantidad.setText(intent.getFloatExtra("cantidad", 0)+"");
        etGrasas.setText(intent.getFloatExtra("grasas", 0)+"");
        etHidratos.setText(intent.getFloatExtra("hidratos", 0)+"");
        etProteinas.setText(intent.getFloatExtra("proteinas", 0)+"");
        etCalorias.setText(intent.getIntExtra("calorias", 0)+"");

        posicion = intent.getIntExtra("posicion", 0);




        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fabAceptarModificacionAlimento);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Boolean existe = false;
                        Long acaba = dataSnapshot.child("alimentos").getChildrenCount();
                        for (Long i = 0l; i <= posicion; i++) {
                            if (!dataSnapshot.child("alimentos").child(i + "").exists()) {
                                posicion++;
                            }
                        }

                        if (!etNombre.getText().toString().isEmpty() && !etMarca.getText().toString().isEmpty() && !etUnidad.getText().toString().isEmpty() && !etCantidad.getText().toString().isEmpty() && !etGrasas.getText().toString().isEmpty() && !etHidratos.getText().toString().isEmpty() && !etProteinas.getText().toString().isEmpty() && !etCalorias.getText().toString().isEmpty()) {
                            if (!existe) {
                                mDatabase.child("alimentos").child(posicion + "").child("cantidad").setValue(etCantidad.getText().toString());
                                mDatabase.child("alimentos").child(posicion + "").child("unidad").setValue(etUnidad.getText().toString().toLowerCase());
                                mDatabase.child("alimentos").child(posicion + "").child("grasas").setValue(etGrasas.getText().toString());
                                mDatabase.child("alimentos").child(posicion + "").child("hidratos").setValue(etHidratos.getText().toString());
                                mDatabase.child("alimentos").child(posicion + "").child("proteinas").setValue(etProteinas.getText().toString());
                                mDatabase.child("alimentos").child(posicion + "").child("calorias").setValue(etCalorias.getText().toString());

                                backEnabled = true;
                                onBackPressed();
                            }
                        } else {
                            Toast.makeText(ModificarAlimentoActivity.this, "Hay campos vacios", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fabDenegarModificacionAlimento);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backEnabled = true;
                onBackPressed();
            }
        });

        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.fabEliminarAlimento);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Long acaba = dataSnapshot.child("alimentos").getChildrenCount();
                        for (Long i = 0l; i <= posicion; i++) {
                            if (!dataSnapshot.child("alimentos").child(i + "").exists()) {
                                posicion++;
                            }
                        }

                        mDatabase.child("alimentos").child(posicion + "").removeValue();

                        backEnabled = true;
                        onBackPressed();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backEnabled) {
            super.onBackPressed();
        }
    }
}
