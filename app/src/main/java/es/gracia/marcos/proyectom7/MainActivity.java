package es.gracia.marcos.proyectom7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText usuario, contraseña;

    //Inicializamos las SharedPreferences
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        usuario = (EditText) findViewById(R.id.et_usuario);
        contraseña = (EditText) findViewById(R.id.et_contraseña);
        //Le damos un valor a las SharedPreferences
        preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void iniciarSesion(View view) {
        if (!usuario.getText().toString().equals("") && !contraseña.getText().toString().equals("")) {
            if (usuario.getText().toString().equals(preferences.getString("user", "No hay usuario")) && contraseña.getText().toString().equals(preferences.getString("user", "No hay contraseña"))) {
                usuario.setText(preferences.getString("user", "No hay usuario"));
                contraseña.setText(preferences.getString("pass", "No hay contraseña"));
            } else {

                Toast toast = Toast.makeText(getApplicationContext(), "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Hay campos vacíos.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void abrirAsistencia(View view) {

    }

    public void abrirRegistro(View view) {
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
    }
}
