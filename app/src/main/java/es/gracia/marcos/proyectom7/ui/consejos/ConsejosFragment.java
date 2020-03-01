package es.gracia.marcos.proyectom7.ui.consejos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import es.gracia.marcos.proyectom7.AdapterConsejos;
import es.gracia.marcos.proyectom7.AnadirAlimentoActivity;
import es.gracia.marcos.proyectom7.CajaNavegacionActivity;
import es.gracia.marcos.proyectom7.R;
import es.gracia.marcos.proyectom7.RegistroActivity;
import es.gracia.marcos.proyectom7.ui.alimentos.Alimento;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class ConsejosFragment extends Fragment {
    private final LinkedList<Consejo> listaConsejos = new LinkedList<>();
    private RecyclerView recyclerConsejos;
    private AdapterConsejos aAdapter;
    private DatabaseReference mDatabase;
    private DatabaseReference mUser;
    private ProgressDialog mDialog;

    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_consejos, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference("Consejos/");
        mUser = FirebaseDatabase.getInstance().getReference("Users/" + CajaNavegacionActivity.getUser().getUid());
        mDialog = new ProgressDialog(getContext());


        mDialog.setMessage("Espera un momento...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String titol;
                String text;
                String autor;
                String trastorno = null;


                listaConsejos.clear();
                Long acaba = dataSnapshot.getChildrenCount();
                for (int i = 0; i < acaba; i++) {
                    if (dataSnapshot.child(i + "").exists()) {
                        titol = dataSnapshot.child(i + "").child("titol").getValue().toString();
                        text = dataSnapshot.child(i + "").child("text").getValue().toString();
                        autor = dataSnapshot.child(i + "").child("autor").getValue().toString();
                        trastorno = dataSnapshot.child(i + "").child("trastorno").getValue().toString();
                        if (trastorno.equals(CajaNavegacionActivity.getTrastorno()) || autor.equals(CajaNavegacionActivity.getNom())) listaConsejos.add(new Consejo(titol, text, autor, trastorno));
                    } else {
                        acaba++;
                    }
                }

                recyclerConsejos = root.findViewById(R.id.listaConsejos);
                aAdapter = new AdapterConsejos(getContext(), listaConsejos);
                recyclerConsejos.setAdapter(aAdapter);
                recyclerConsejos.setLayoutManager(new LinearLayoutManager(getContext()));
                mDialog.dismiss();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mDialog.dismiss();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fabAnadirConsejo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AnadirConsejoActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return root;
    }
}
