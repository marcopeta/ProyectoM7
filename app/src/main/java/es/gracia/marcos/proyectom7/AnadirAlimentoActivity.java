package es.gracia.marcos.proyectom7;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import es.gracia.marcos.proyectom7.ui.alimentos.Alimento;
import es.gracia.marcos.proyectom7.ui.alimentos.AlimentosFragment;

import static java.lang.Integer.parseInt;

public class AnadirAlimentoActivity extends AppCompatActivity {

    private final LinkedList<Alimento> listaAlimentos = new LinkedList<>();
    private RecyclerView recyclerAlimentos;
    private AdapterAlimentos aAdapter;
    private DatabaseReference mDatabase;
    EditText etNombre, etMarca, etUnidad, etCantidad, etGrasas, etHidratos, etProteinas, etCalorias;
    private boolean backEnabled = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_alimento);
        Toolbar toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().setTitle("Añadir Alimento");
        mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + CajaNavegacionActivity.getUser().getUid());
        etNombre = findViewById(R.id.etAnadirNombre);
        etMarca = findViewById(R.id.etAnadirMarca);
        etUnidad = findViewById(R.id.etAnadirUnidad);
        etCantidad = findViewById(R.id.etAnadirCantidad);
        etGrasas = findViewById(R.id.etAnadirGrasas);
        etHidratos = findViewById(R.id.etAnadirHidratos);
        etProteinas = findViewById(R.id.etAnadirProteinas);
        etCalorias = findViewById(R.id.etAnadirCalorias);

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fabAceptarAlimento);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!etNombre.getText().toString().isEmpty() && !etMarca.getText().toString().isEmpty() && !etUnidad.getText().toString().isEmpty() && !etCantidad.getText().toString().isEmpty() && !etGrasas.getText().toString().isEmpty() && !etHidratos.getText().toString().isEmpty() && !etProteinas.getText().toString().isEmpty() && !etCalorias.getText().toString().isEmpty()) {
                            if (etUnidad.getText().toString().length() <= 2) {
                                Boolean existe = false;
                                Long posicion = dataSnapshot.child("alimentos").getChildrenCount();
                                Long acaba = dataSnapshot.child("alimentos").getChildrenCount();
                                for (Long i = 0l; i < acaba; i++) {
                                    if (!dataSnapshot.child("alimentos").child(i + "").exists()) {
                                        posicion = i;
                                        acaba++;
                                    } else {
                                        if (etNombre.getText().toString().equals(dataSnapshot.child("alimentos").child(i + "").child("nombre").getValue().toString())) {
                                            Toast.makeText(AnadirAlimentoActivity.this, "Este alimento ya esta en la lista", Toast.LENGTH_SHORT).show();
                                            existe = true;
                                            break;
                                        }
                                    }
                                }
                                if (!existe) {
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("nombre", etNombre.getText().toString());
                                    map.put("marca", etMarca.getText().toString());
                                    map.put("cantidad", etCantidad.getText().toString());
                                    map.put("unidad", etUnidad.getText().toString());
                                    map.put("grasas", etGrasas.getText().toString());
                                    map.put("hidratos", etHidratos.getText().toString());
                                    map.put("proteinas", etProteinas.getText().toString());
                                    map.put("calorias", etCalorias.getText().toString());

                                    mDatabase.child("alimentos").child(posicion + "").setValue(map);

                                    backEnabled = true;
                                    onBackPressed();
                                }
                            } else {
                                Toast.makeText(AnadirAlimentoActivity.this, "La unidad no es correcta", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AnadirAlimentoActivity.this, "Hay campos vacios", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fabDenegarAlimento);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backEnabled = true;
                onBackPressed();
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