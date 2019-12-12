package es.gracia.marcos.proyectom7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
    }

    public void abrirRegistro(View view) {
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
    }

    public void abrirAsistencia(View view) {
    }
}
