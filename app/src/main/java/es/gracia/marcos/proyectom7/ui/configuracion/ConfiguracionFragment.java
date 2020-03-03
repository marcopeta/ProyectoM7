package es.gracia.marcos.proyectom7.ui.configuracion;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

import es.gracia.marcos.proyectom7.CajaNavegacionActivity;
import es.gracia.marcos.proyectom7.MainActivity;
import es.gracia.marcos.proyectom7.R;

import static java.lang.Integer.parseInt;

public class ConfiguracionFragment extends Fragment{
    private LinearLayout catalan;
    private LinearLayout castellano;
    private LinearLayout ingles;
    private Button guardar;
    private DatabaseReference mDatabase;
    private TextView correo;
    private TextView cerrarSess;
    private EditText nombre;
    private RadioButton rb_anorexia, rb_bulimia, rb_sobrepeso, rb_no;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_configuracion, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + CajaNavegacionActivity.getUser().getUid());
        correo = root.findViewById(R.id.configuracionCorreo);
        nombre = root.findViewById(R.id.configuracionNombre);
        rb_anorexia = (RadioButton) root.findViewById(R.id.configuracion_rb_anorexia);
        rb_bulimia = (RadioButton) root.findViewById(R.id.configuracion_rb_bulimia);
        rb_sobrepeso = (RadioButton) root.findViewById(R.id.configuracion_rb_sobrepeso);
        rb_no = (RadioButton) root.findViewById(R.id.configuracion_rb_no);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    correo.setText(dataSnapshot.child("correo").getValue().toString());
                    nombre.setText(dataSnapshot.child("nombre").getValue().toString());
                    switch (parseInt(dataSnapshot.child("trastorno").getValue().toString())) {
                        case 0:
                            rb_no.setChecked(true);
                            break;
                        case 1:
                            rb_anorexia.setChecked(true);
                            break;
                        case 2:
                            rb_sobrepeso.setChecked(true);
                            break;
                        case 3:
                            rb_bulimia.setChecked(true);
                            break;
                    }
                } catch (Exception ex) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                return;
            }
        });

        guardar = root.findViewById(R.id.btnGuardarDatos);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new AlertDialog.Builder(getContext())
                            .setTitle("")
                            .setMessage("Seguro que quieres cambiar los datos?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            mDatabase.child("nombre").setValue(nombre.getText().toString());
                                            String trastorno;
                                            if (rb_anorexia.isChecked() == true) {
                                                trastorno = "1";
                                            } else if (rb_bulimia.isChecked() == true) {
                                                trastorno = "2";
                                            } else if (rb_sobrepeso.isChecked() == true) {
                                                trastorno = "3";
                                            } else {
                                                trastorno = "0";
                                            }
                                            mDatabase.child("trastorno").setValue(trastorno);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            return;
                                        }
                                    });
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            correo.setText(dataSnapshot.child("correo").getValue().toString());
                                            nombre.setText(dataSnapshot.child("nombre").getValue().toString());
                                            switch (parseInt(dataSnapshot.child("trastorno").getValue().toString())) {
                                                case 0:
                                                    rb_no.setChecked(true);
                                                    break;
                                                case 1:
                                                    rb_anorexia.setChecked(true);
                                                    break;
                                                case 2:
                                                    rb_sobrepeso.setChecked(true);
                                                    break;
                                                case 3:
                                                    rb_bulimia.setChecked(true);
                                                    break;
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            return;
                                        }
                                    });
                                }
                            })
                            .show();
                } catch (Exception ex) {

                }
            }
        });
        catalan = root.findViewById(R.id.linearLayoutCatalan);
        catalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!Locale.getDefault().getLanguage().equals("cat")) {
                        new AlertDialog.Builder(getContext())
                                .setTitle("")
                                .setMessage("Vols canviar al  Català?")
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Locale localizacion = new Locale("cat", "ES");

                                        Locale.setDefault(localizacion);
                                        Configuration config = new Configuration();
                                        config.locale = localizacion;
                                        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
                                        MainActivity.preferences.edit().putString("idioma", "cat").apply();
                                        MainActivity.preferences.edit().putString("pais", "ES").apply();
                                        MainActivity.preferences.edit().commit();
                                        Intent intent = getActivity().getIntent();
                                        getActivity().finish();
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                    } else {
                        Toast.makeText(getContext(), "La aplicació ja esta en Català", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {

                }
            }
        });
        castellano = root.findViewById(R.id.linearLayoutCastellano);
        castellano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!MainActivity.preferences.getString("idioma", "").equals("es")) {
                        new AlertDialog.Builder(getContext())
                                .setTitle("")
                                .setMessage("¿Quieres cambiar al Castellano?")
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Locale localizacion = new Locale("es", "ES");

                                        Locale.setDefault(localizacion);
                                        Configuration config = new Configuration();
                                        config.locale = localizacion;
                                        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
                                        MainActivity.preferences.edit().putString("idioma", "es").apply();
                                        MainActivity.preferences.edit().putString("pais", "ES").apply();
                                        MainActivity.preferences.edit().commit();
                                        Intent intent = getActivity().getIntent();
                                        getActivity().finish();
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();

                    } else {
                        Toast.makeText(getContext(), "La aplicación ya esta en Castellano", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {

                }
            }
        });
        ingles = root.findViewById(R.id.linearLayoutIngles);
        ingles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!Locale.getDefault().getLanguage().equals("en")) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("")
                            .setMessage("Do you want to change to English?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Locale localizacion = new Locale("en", "GB");

                                    Locale.setDefault(localizacion);
                                    Configuration config = new Configuration();
                                    config.locale = localizacion;
                                    getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
                                    MainActivity.preferences.edit().putString("idioma", "en").apply();
                                    MainActivity.preferences.edit().putString("pais", "GB").apply();
                                    MainActivity.preferences.edit().commit();
                                    Intent intent = getActivity().getIntent();
                                    getActivity().finish();
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                    } else {
                        Toast.makeText(getContext(), "The application is already in English", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {

                }
            }
        });
        return root;
    }

    private void cambiarCatalan(){

    }
}