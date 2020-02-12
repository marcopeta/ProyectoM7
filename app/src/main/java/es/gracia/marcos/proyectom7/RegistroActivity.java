package es.gracia.marcos.proyectom7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {

    EditText campoCorreo, campoUsuario, campoContraseña, campoTelefono;
    RadioButton rb_anorexia, rb_bulimia, rb_sobrepeso, rb_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().hide();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        campoCorreo = (EditText) findViewById(R.id.et_correoRegistro);
        campoUsuario = (EditText) findViewById(R.id.et_usuarioRegistro);
        campoContraseña = (EditText) findViewById(R.id.et_contraseñaRegistro);
        campoTelefono = (EditText) findViewById(R.id.et_telefonoRegistro);

        rb_anorexia = (RadioButton) findViewById(R.id.rb_anorexia);
        rb_bulimia = (RadioButton) findViewById(R.id.rb_bulimia);
        rb_sobrepeso = (RadioButton) findViewById(R.id.rb_sobrepeso);
        rb_no = (RadioButton) findViewById(R.id.rb_no);

        myRef.child("usuario1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nombre = dataSnapshot.child("nombre").getValue().toString();
                campoUsuario.setText(nombre);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void registrarUsuario(View view) {
        String correo = campoCorreo.getText().toString();
        String usuario = campoUsuario.getText().toString();
        String contraseña = campoContraseña.getText().toString();
        String telefono = campoTelefono.getText().toString();
        String trastorno;

        Map<String, Object> map = new HashMap<>();
        map.put("correo", correo);
        map.put("usuario", usuario);
        map.put("contraseña", contraseña);
        map.put("telefono", telefono);
        map.put("trastorno", 0);
        map.put("idioma", "e");


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
        if (!correo.equals("") && !usuario.equals("") && !contraseña.equals("") && !telefono.equals("")) {
            Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
            Matcher mather = pattern.matcher(correo);

            //Comprobamos que el mail sea correcto
            if (mather.find() && telefono.length() == 9) {
                //Cargamos los datos en el SharedPreferences de la Main Activity
                MainActivity.editor.putString("mail", correo);
                MainActivity.editor.putString("user", usuario);
                MainActivity.editor.putString("pass", contraseña);
                MainActivity.editor.putString("tel", telefono);
                MainActivity.editor.putString("enf", trastorno);
                MainActivity.editor.commit();

                //Mostramos un toast y cargamos la MainActivity
                Toast toast = Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "El correo/telefono no es correcto", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Hay campos vacios", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
