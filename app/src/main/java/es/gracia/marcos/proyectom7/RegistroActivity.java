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

        //Comprobamos que el todos los campos tengan texto
        if (!correo.equals("") && !usuario.equals("") && !contraseña.equals("")) {
            Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
            Matcher mather = pattern.matcher(correo);

            //Comprobamos que el mail sea correcto
            if (mather.find()) {
                //Cargamos los datos en el SharedPreferences de la Main Activity
                MainActivity.editor.putString("mail", correo);
                MainActivity.editor.putString("user", usuario);
                MainActivity.editor.putString("pass", contraseña);
                MainActivity.editor.commit();

                //Mostramos un toast y cargamos la MainActivity
                Toast toast = Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "El correo no es correcto", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Hay campos vacios", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
