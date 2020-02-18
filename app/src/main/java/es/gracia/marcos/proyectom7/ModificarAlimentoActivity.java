package es.gracia.marcos.proyectom7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

import es.gracia.marcos.proyectom7.ui.alimentos.Alimento;
import es.gracia.marcos.proyectom7.ui.alimentos.AlimentosFragment;

public class ModificarAlimentoActivity extends AppCompatActivity {

    private final LinkedList<Alimento> listaAlimentos = new LinkedList<>();
    private RecyclerView recyclerAlimentos;
    private AdapterAlimentos aAdapter;
    private DatabaseReference mDatabase;
    EditText etNombre, etMarca, etUnidad, etCantidad, etGrasas, etHidratos, etProteinas, etCalorias;
    private boolean backEnabled = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_alimento);
        Toolbar toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().setTitle("Modificar Alimento");
        mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + CajaNavegacionActivity.getUser().getUid());
        etNombre = findViewById(R.id.etModificarNombre);
        etMarca = findViewById(R.id.etModificarMarca);
        etUnidad = findViewById(R.id.etModificarUnidad);
        etCantidad = findViewById(R.id.etModificarCantidad);
        etGrasas = findViewById(R.id.etModificarGrasas);
        etHidratos = findViewById(R.id.etModificarHidratos);
        etProteinas = findViewById(R.id.etModificarProteinas);
        etCalorias = findViewById(R.id.etModificarCalorias);


        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fabAceptarModificacionAlimento);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        backEnabled = true;
                        onBackPressed();
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
