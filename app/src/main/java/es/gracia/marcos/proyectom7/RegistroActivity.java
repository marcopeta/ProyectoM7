package es.gracia.marcos.proyectom7;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {

    EditText campoCorreo, campoNombre, campoContraseña, campoTelefono;
    RadioButton rb_anorexia, rb_bulimia, rb_sobrepeso, rb_no;
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private DatabaseReference mDatabase;
    Map<String, Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        campoCorreo = (EditText) findViewById(R.id.et_correoRegistro);
        campoNombre = (EditText) findViewById(R.id.et_usuarioRegistro);
        campoContraseña = (EditText) findViewById(R.id.et_contraseñaRegistro);
        campoTelefono = (EditText) findViewById(R.id.et_telefonoRegistro);

        rb_anorexia = (RadioButton) findViewById(R.id.rb_anorexia);
        rb_bulimia = (RadioButton) findViewById(R.id.rb_bulimia);
        rb_sobrepeso = (RadioButton) findViewById(R.id.rb_sobrepeso);
        rb_no = (RadioButton) findViewById(R.id.rb_no);
    }

    public void registrarUsuario(View view) {
        String correo = campoCorreo.getText().toString();
        final String nombre = campoNombre.getText().toString();
        String contraseña = campoContraseña.getText().toString();
        final String telefono = campoTelefono.getText().toString();
        final String trastorno;

        if (rb_anorexia.isChecked() == true) {
            trastorno = "Anorexia";
        } else if (rb_bulimia.isChecked() == true) {
            trastorno = "Bulimia";
        } else if (rb_sobrepeso.isChecked() == true) {
            trastorno = "Sobrepeso";
        } else {
            trastorno = "No";
        }


        //Comprobamos que el todos los campos tengan texto
        if (!correo.equals("") && !nombre.equals("") && !contraseña.equals("") && !telefono.equals("")) {
            Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
            Matcher mather = pattern.matcher(correo);

            //Comprobamos que el mail sea correcto
            if (mather.find() && telefono.length() == 9) {
                if (contraseña.length() > 6) {
                    mAuth.createUserWithEmailAndPassword(correo, contraseña)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        map.put("nombre", nombre);
                                        map.put("telefono", telefono);
                                        map.put("trastorno", trastorno);
                                        updateUI(user);
                                    } else {
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegistroActivity.this, "No se ha podido crear el usuario",
                                                Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }
                                }
                            });
                    //mDatabase.child("user4").setValue(map);
                    //mDatabase.child(currentUser.getUid()).setValue("hola");
                    mDatabase.child("user3").setValue("adios");
                    Toast toast = Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "La contraseña ha de tener mas de seis caracteres", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "El correo/telefono no es correcto", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Hay campos vacios", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void updateUI(FirebaseUser currentUser) {
    }

}
