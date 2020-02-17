package es.gracia.marcos.proyectom7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class RecuperarContrasenaActivity extends AppCompatActivity {
    TextView correo;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasena);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        correo = findViewById(R.id.recuperacionCorreo);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

    }

    public void recuperarContrasena(View view) {
        String mail = correo.getText().toString();

        if (!mail.isEmpty()) {
            mDialog.setMessage("Espera un momento");
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
            resetPassword(mail);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Introduce el correo", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void resetPassword(String mail) {
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mDialog.dismiss();
                    Toast toastCorreo = Toast.makeText(getApplicationContext(), "Se ha enviado un correo para restablecer la contraseña", Toast.LENGTH_SHORT);
                    toastCorreo.show();
                } else {
                    mDialog.dismiss();
                    Toast toastCorreo = Toast.makeText(getApplicationContext(), "No se ha podido enviar el correo, comprueba  que este bien escrito", Toast.LENGTH_SHORT);
                    toastCorreo.show();
                }
            }
        });
    }
}
