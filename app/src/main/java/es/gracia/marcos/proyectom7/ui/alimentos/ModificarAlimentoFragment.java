package es.gracia.marcos.proyectom7.ui.alimentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import es.gracia.marcos.proyectom7.CajaNavegacionActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

import es.gracia.marcos.proyectom7.AdapterAlimentos;
import es.gracia.marcos.proyectom7.R;

public class ModificarAlimentoFragment extends Fragment {
    private final LinkedList<Alimento> listaAlimentos = new LinkedList<>();
    private RecyclerView recyclerAlimentos;
    private AdapterAlimentos aAdapter;
    private DatabaseReference mDatabase;
    View root;
    EditText etNombre, etMarca, etUnidad, etCantidad, etGrasas, etHidratos, etProteinas, etCalorias;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_modificar_alimento, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + CajaNavegacionActivity.getUser().getUid());

        return root;
    }
}
