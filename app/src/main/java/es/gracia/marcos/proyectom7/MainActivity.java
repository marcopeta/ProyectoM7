package es.gracia.marcos.proyectom7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    CheckBox sesionInciada;
    Button btnIniciarSesion, btnAbrirRegistro, BtnAbrirAsistencia;
    CheckBox cbKeep;

    //Inicializamos las SharedPreferences
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    private static final String TAG = "MainActivity.this";
    private static FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    private boolean activado;
    private static final String STRING_PREFERENCES = "es.gracia.marcos.proyectom7";
    private static final String PREFERENCE_STATE = "estado.checkbox.sesion";

    public static FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getStateSession()) {
            Intent intent = new Intent(MainActivity.this, CajaNavegacionActivity.class);
            startActivity(intent);
            finish();
        }

        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        correo = (EditText) findViewById(R.id.et_usuario);
        contraseña = (EditText) findViewById(R.id.et_contraseña);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        sesionInciada = findViewById(R.id.cb_keepSesion);
        mDialog = new ProgressDialog(this);
        cbKeep = findViewById(R.id.cb_keepSesion);
        activado = cbKeep.isChecked();
        cbKeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activado) cbKeep.setChecked(false);
                activado = cbKeep.isChecked();
            }
        });
        //Le damos un valor a las SharedPreferences
        preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mAuth = FirebaseAuth.getInstance();
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciadoSesion();
            }
        });
    }

    public void iniciadoSesion() {
        try {
            mDialog.setMessage("Espera un momento...");
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
            mAuth.signInWithEmailAndPassword(correo.getText().toString().trim(), contraseña.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                keepSession();
                                mDialog.dismiss();
                                Log.d(TAG, "signInWithEmail:success");
                                Intent intent = new Intent(MainActivity.this, CajaNavegacionActivity.class);
                                startActivity(intent);
                                if  (getStateSession()) finish();
                            } else {
                                mDialog.dismiss();
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Correo/Contraseña incorrecto",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            mDialog.dismiss();
            Toast.makeText(this, "Algo ha salido mal...", Toast.LENGTH_SHORT).show();
        }
    }

    /*public void iniciarSesion(View view) {
        iniciadoSesion();
    }*/

    public void keepSession() {
        SharedPreferences p = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        p.edit().putBoolean(PREFERENCE_STATE, cbKeep.isChecked()).apply();
    }

    public boolean getStateSession() {
        SharedPreferences p = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        return p.getBoolean(PREFERENCE_STATE, false);
    }

    public void abrirAsistencia(View view) {
        Intent intent = new Intent(this, RecuperarContrasenaActivity.class);
        startActivity(intent);
    }

    public void abrirRegistro(View view) {
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
    }
}
