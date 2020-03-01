package es.gracia.marcos.proyectom7.ui.consejos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import es.gracia.marcos.proyectom7.CajaNavegacionActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import es.gracia.marcos.proyectom7.AdapterAlimentos;
import es.gracia.marcos.proyectom7.AdapterConsejos;
import es.gracia.marcos.proyectom7.AnadirAlimentoActivity;
import es.gracia.marcos.proyectom7.CajaNavegacionActivity;
import es.gracia.marcos.proyectom7.R;
import es.gracia.marcos.proyectom7.ui.alimentos.Alimento;

public class AnadirConsejoActivity extends AppCompatActivity {
    private final LinkedList<Consejo> listaConsejos = new LinkedList<>();
    private RecyclerView recyclerConsejos;
    private AdapterConsejos aAdapter;
    private DatabaseReference mDatabase;
    EditText etTitle, etText;
    RadioButton rb_anorexia, rb_bulimia, rb_sobrepeso, rb_no;
    private boolean backEnabled = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_consejo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().setTitle("Añadir Consejo");
        mDatabase = FirebaseDatabase.getInstance().getReference("Consejos/");
        etTitle = findViewById(R.id.etTitle);
        etText = findViewById(R.id.etText);
        final String[] autor = new String[1];
        rb_anorexia = (RadioButton) findViewById(R.id.rb_anorexia);
        rb_bulimia = (RadioButton) findViewById(R.id.rb_bulimia);
        rb_sobrepeso = (RadioButton) findViewById(R.id.rb_sobrepeso);
        rb_no = (RadioButton) findViewById(R.id.rb_no);

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fabAceptarConsejo);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!etTitle.getText().toString().isEmpty() && !etText.getText().toString().isEmpty()) {
                            Boolean existe = false;
                            Long posicion = dataSnapshot.getChildrenCount();
                            Long acaba = dataSnapshot.getChildrenCount();
                            for (Long i = 0l; i < acaba; i++) {
                                if (!dataSnapshot.child(i + "").exists()) {
                                    posicion = i;
                                    acaba++;

                                } else {
                                    if (etTitle.getText().toString().equals(dataSnapshot.child(i + "").child("titol").getValue().toString())) {
                                        Toast.makeText(AnadirConsejoActivity.this, "Este consejo ya está en la lista", Toast.LENGTH_SHORT).show();
                                        existe = true;
                                        break;
                                    }
                                }
                            }
                            if (!existe) {
                                Map<String, Object> map = new HashMap<>();
                                String trastorno;

                                map.put("autor", CajaNavegacionActivity.getNom() );
                                map.put("text", etText.getText().toString());
                                map.put("titol", etTitle.getText().toString());

                                if (rb_anorexia.isChecked() == true) {
                                    trastorno = "1";
                                } else if (rb_bulimia.isChecked() == true) {
                                    trastorno = "2";
                                } else if (rb_sobrepeso.isChecked() == true) {
                                    trastorno = "3";
                                } else {
                                    trastorno = "0";
                                }
                                map.put("trastorno", trastorno);
                                mDatabase.child(posicion + "").setValue(map);
                                backEnabled = true;
                                onBackPressed();
                            }
                        } else {
                            Toast.makeText(AnadirConsejoActivity.this, "Hay campos vacios", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fabDenegarConsejo);
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
