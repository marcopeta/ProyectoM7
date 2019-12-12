package es.gracia.marcos.proyectom7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {

    EditText campoCorreo, campoUsuario, campoContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        campoCorreo = (EditText) findViewById(R.id.et_correoRegistro);
        campoUsuario = (EditText) findViewById(R.id.et_usuarioRegistro);
        campoContraseña = (EditText) findViewById(R.id.et_contraseñaRegistro);
    }

    public void registrarUsuario(View view) {
        String correo = campoCorreo.getText().toString();
        String usuario = campoUsuario.getText().toString();
        String contraseña = campoContraseña.getText().toString();

        Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");

        Matcher mather = pattern.matcher(correo);

        if (mather.find() == true && usuario != null && contraseña != null) {
            SharedPreferences preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("mail", correo);
            editor.putString("user", usuario);
            editor.putString("pass", contraseña);
            editor.commit();
            Toast toast = Toast.makeText(getApplicationContext(),"Usuario registrado correctamente", Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),"El correo no es correcto", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
