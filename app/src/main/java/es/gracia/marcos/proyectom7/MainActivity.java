package es.gracia.marcos.proyectom7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    EditText correo, contraseña;
    Button btnIniciarSesion;

    //Inicializamos las SharedPreferences
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    private static final String TAG = "MainActivity.this";
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        correo = (EditText) findViewById(R.id.et_usuario);
        contraseña = (EditText) findViewById(R.id.et_contraseña);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        //Le damos un valor a las SharedPreferences
        preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mAuth = FirebaseAuth.getInstance();
    }

    public void iniciarSesion(View view) {
        mAuth.signInWithEmailAndPassword(correo.getText().toString().trim(), contraseña.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            btnIniciarSesion.setEnabled(false);
                            Intent intent = new Intent(MainActivity.this, CajaNavegacionActivity.class);
                            startActivity(intent);
                            btnIniciarSesion.setEnabled(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void abrirAsistencia(View view) {

    }

    public void abrirRegistro(View view) {
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
    }
}
