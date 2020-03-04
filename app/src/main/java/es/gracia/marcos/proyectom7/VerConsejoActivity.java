package es.gracia.marcos.proyectom7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class VerConsejoActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    TextView tvTitle, tvText,tvAutor;
    private boolean backEnabled = false;
    private int posicion;
    Button btnReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_consejo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mDatabase = FirebaseDatabase.getInstance().getReference("Consejos/");
        tvText = findViewById(R.id.tvTextV);
        tvTitle = findViewById(R.id.tvTitleV);
        tvAutor = findViewById(R.id.tvAutorV);
        btnReport = findViewById(R.id.btnReport);


        Intent intent = getIntent();

        tvTitle.setText(intent.getStringExtra("titol"));
        tvText.setText(intent.getStringExtra("text"));
        tvAutor.setText("-"+intent.getStringExtra("autor"));
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.report_advice, Toast.LENGTH_SHORT);
                toast.show();
                btnReport.setEnabled(false);
            }
        });
        posicion = intent.getIntExtra("posicion", 0);
    }
}
