package es.gracia.marcos.proyectom7;

import android.app.ProgressDialog;
import android.app.assist.AssistStructure;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {

    EditText campoCorreo, campoNombre, campoContraseña, campoTelefono;
    RadioButton rb_anorexia, rb_bulimia, rb_sobrepeso, rb_no;
    Button btnRegistrar;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

        btnRegistrar = findViewById(R.id.btnRegistrar);
        mDialog = new ProgressDialog(this);
    }

    public void registrarUsuario(View view) {
        try {
            btnRegistrar.setEnabled(false);
            mDialog.setMessage(getApplicationContext().getResources().getString(R.string.wait_dialog));
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
            final String correo = campoCorreo.getText().toString();
            final String nombre = campoNombre.getText().toString();
            final String contraseña = campoContraseña.getText().toString();
            final String telefono = campoTelefono.getText().toString();
            final String trastorno;

            if (rb_anorexia.isChecked() == true) {
                trastorno = "1";
            } else if (rb_bulimia.isChecked() == true) {
                trastorno = "2";
            } else if (rb_sobrepeso.isChecked() == true) {
                trastorno = "3";
            } else {
                trastorno = "0";
            }


            //Comprobamos que el todos los campos tengan texto
            if (!correo.equals("") && !nombre.equals("") && !contraseña.equals("") && !telefono.equals("")) {
                Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
                Matcher mather = pattern.matcher(correo);

                //Comprobamos que el mail sea correcto
                if (mather.find() && telefono.length() == 9) {
                    if (contraseña.length() >= 6) {
                        mAuth.createUserWithEmailAndPassword(correo, contraseña)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Map<String, String> map = new HashMap<>();
                                            map.put("nombre", nombre);
                                            map.put("telefono", telefono);
                                            map.put("trastorno", trastorno);
                                            map.put("correo", correo);
                                            mDatabase.child(user.getUid()).setValue(map);
                                            mDialog.dismiss();
                                            Toast toast = Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT);
                                            toast.show();
                                            Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            btnRegistrar.setEnabled(true);
                                        } else {
                                            mDialog.dismiss();
                                            Toast.makeText(RegistroActivity.this, "No se ha podido crear el usuario",
                                                    Toast.LENGTH_SHORT).show();
                                            btnRegistrar.setEnabled(true);
                                        }
                                    }
                                });
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "La contraseña ha de tener mas de seis caracteres", Toast.LENGTH_SHORT);
                        toast.show();
                        btnRegistrar.setEnabled(true);
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "El correo/telefono no es correcto", Toast.LENGTH_SHORT);
                    toast.show();
                    btnRegistrar.setEnabled(true);
                }
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Hay campos vacios", Toast.LENGTH_SHORT);
                toast.show();
                btnRegistrar.setEnabled(true);
            }
        } catch (Exception ex) {

        }
    }

    private void updateUI(FirebaseUser currentUser) {
    }



}
