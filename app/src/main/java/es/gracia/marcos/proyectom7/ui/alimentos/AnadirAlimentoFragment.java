package es.gracia.marcos.proyectom7.ui.alimentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

import es.gracia.marcos.proyectom7.AdapterAlimentos;
import es.gracia.marcos.proyectom7.CajaNavegacionActivity;
import es.gracia.marcos.proyectom7.R;
import io.grpc.internal.LongCounter;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class AnadirAlimentoFragment extends Fragment {

    private final LinkedList<Alimento> listaAlimentos = new LinkedList<>();
    private RecyclerView recyclerAlimentos;
    private AdapterAlimentos aAdapter;
    private DatabaseReference mDatabase;
    View root;
    EditText etNombre, etMarca, etUnidad, etCantidad, etGrasas, etHidratos, etProteinas, etCalorias;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_anadir_alimento, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + CajaNavegacionActivity.getUser().getUid());
        etNombre = root.findViewById(R.id.etAnadirNombre);
        etMarca = root.findViewById(R.id.etAnadirMarca);
        etUnidad = root.findViewById(R.id.etAnadirUnidad);
        etCantidad = root.findViewById(R.id.etAnadirCantidad);
        etGrasas = root.findViewById(R.id.etAnadirGrasas);
        etHidratos = root.findViewById(R.id.etAnadirHidratos);
        etProteinas = root.findViewById(R.id.etAnadirProteinas);
        etCalorias = root.findViewById(R.id.etAnadirCalorias);


        FloatingActionButton fab1 = (FloatingActionButton) root.findViewById(R.id.fabAceptarAlimento);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (true /*comprobar que los campos contengan texto*/) {
                            if (true /*comprobar que la unidad sea correcta*/) {
                                Boolean existe = false;
                                Long posicion = dataSnapshot.child("alimentos").getChildrenCount();
                                Long acaba = dataSnapshot.child("alimentos").getChildrenCount();
                                for (Long i = 0l; i < acaba; i++) {
                                    if (!dataSnapshot.child("alimentos").child(i + "").exists()) {
                                        posicion = i;
                                        acaba++;
                                    } else {
                                        if (etNombre.getText().toString().equals(dataSnapshot.child("alimentos").child(i + "").child("nombre").getValue().toString())) {
                                            Toast.makeText(getContext(), "Este alimento ya esta en la lista", Toast.LENGTH_SHORT).show();
                                            existe = true;
                                            break;
                                        }
                                    }
                                }
                                if (!existe) {
                                    mDatabase.child("alimentos").child(posicion + "").child("nombre").setValue(etNombre.getText().toString());
                                    mDatabase.child("alimentos").child(posicion + "").child("marca").setValue(etMarca.getText().toString());
                                    mDatabase.child("alimentos").child(posicion + "").child("cantidad").setValue(etCantidad.getText().toString());
                                    mDatabase.child("alimentos").child(posicion + "").child("unidad").setValue(etUnidad.getText().toString());
                                    mDatabase.child("alimentos").child(posicion + "").child("grasas").setValue(etGrasas.getText().toString());
                                    mDatabase.child("alimentos").child(posicion + "").child("hidratos").setValue(etHidratos.getText().toString());
                                    mDatabase.child("alimentos").child(posicion + "").child("proteinas").setValue(etProteinas.getText().toString());
                                    mDatabase.child("alimentos").child(posicion + "").child("calorias").setValue(etCalorias.getText().toString());

                                    AlimentosFragment someFragment= new AlimentosFragment();
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.replace(R.id.nav_host_fragment, someFragment );
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                }
                            } else {
                                Toast.makeText(getContext(), "La unidad no es correcta", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Hay campos vacios", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) root.findViewById(R.id.fabDenegarAlimento);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlimentosFragment someFragment= new AlimentosFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, someFragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        
        return root;
    }
}