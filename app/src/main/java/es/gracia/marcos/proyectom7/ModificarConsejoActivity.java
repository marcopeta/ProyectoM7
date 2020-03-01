package es.gracia.marcos.proyectom7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ModificarConsejoActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    EditText etTitle, etText;
    private boolean backEnabled = false;
    private int posicion;
    RadioButton rb_anorexia, rb_bulimia, rb_sobrepeso, rb_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_consejo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().setTitle("Modificar Consejo");

        mDatabase = FirebaseDatabase.getInstance().getReference("Consejos/");
        etText = findViewById(R.id.etModificarText);
        etTitle = findViewById(R.id.etModificarTitle);
        rb_anorexia = findViewById(R.id.rb_anorexiaModificar);
        rb_bulimia = findViewById(R.id.rb_bulimiaModificar);
        rb_sobrepeso = findViewById(R.id.rb_sobrepesoModificar);
        rb_no = findViewById(R.id.rb_noModificar);

        Intent intent = getIntent();

        etTitle.setText(intent.getStringExtra("titol"));
        etText.setText(intent.getStringExtra("text"));

        switch (intent.getStringExtra("trastorno")) {
            case "0":
                rb_no.setChecked(true);
                break;
            case "1":
                rb_anorexia.setChecked(true);
                break;
            case "2":
                rb_bulimia.setChecked(true);
                break;
            case "3":
                rb_sobrepeso.setChecked(true);
                break;
            default:
                break;
        }

        posicion = intent.getIntExtra("posicion", 0);



        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fabAceptarModificacionConsejo);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Boolean existe = false;
                        Long acaba = dataSnapshot.getChildrenCount();
                        for (Long i = 0l; i <= posicion; i++) {
                            if (!dataSnapshot.child(i + "").exists()) {
                                posicion++;
                            }
                        }

                        if (!etTitle.getText().toString().isEmpty() && !etText.getText().toString().isEmpty() ) {
                            if (!existe) {
                                Map<String, Object> map = new HashMap<>();
                                String trastorno;
                                mDatabase.child(posicion + "").child("titol").setValue(etTitle.getText().toString());
                                mDatabase.child(posicion + "").child("text").setValue(etText.getText().toString());
                                mDatabase.child(posicion + "").child("autor").setValue(CajaNavegacionActivity.getNom());
                                if (rb_anorexia.isChecked()) {
                                    trastorno = "1";
                                } else if (rb_bulimia.isChecked()) {
                                    trastorno = "2";
                                } else if (rb_sobrepeso.isChecked()) {
                                    trastorno = "3";
                                } else {
                                    trastorno = "0";
                                }
                                mDatabase.child(posicion + "").child("trastorno").setValue(trastorno);
                                backEnabled = true;
                                onBackPressed();
                            }
                        } else {
                            Toast.makeText(ModificarConsejoActivity.this, "Hay campos vacios", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fabDenegarModificacionConsejo);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backEnabled = true;
                onBackPressed();
            }
        });

        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.fabEliminarConsejo);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Long acaba = dataSnapshot.getChildrenCount();
                        for (Long i = 0l; i <= posicion; i++) {
                            if (!dataSnapshot.child(i + "").exists()) {
                                posicion++;
                            }
                        }

                        mDatabase.child(posicion + "").removeValue();

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
