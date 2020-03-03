package es.gracia.marcos.proyectom7;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.zip.Inflater;

import es.gracia.marcos.proyectom7.ui.alimentos.Alimento;
import es.gracia.marcos.proyectom7.ui.alimentos.ItemClickListener;
import es.gracia.marcos.proyectom7.ui.inicio.AnadirAlimentoDiaActivity;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class AdapterAnadirDia extends RecyclerView.Adapter<AdapterAnadirDia.ViewHolderAlimentos> {


    private final LinkedList<Alimento> listaAlimentos;
    private Context context;
    private DatabaseReference mDatabase;
    String diaActual;
    Calendar currentTime;
    int pos = 0;
    EditText et;
    public static boolean volver =  false;



    public AdapterAnadirDia(Context context, LinkedList<Alimento> listaAlimentos) {
        this.context = context;
        this.listaAlimentos = listaAlimentos;
        mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + CajaNavegacionActivity.getUser().getUid());
        currentTime = Calendar.getInstance();
        volver = false;
        diaActual = currentTime.get(Calendar.DAY_OF_MONTH) + "-" + (currentTime.get(Calendar.MONTH) + 1) + "-" + currentTime.get(Calendar.YEAR);
    }

    @NonNull
    @Override
    public AdapterAnadirDia.ViewHolderAlimentos onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        return new AdapterAnadirDia.ViewHolderAlimentos(LayoutInflater.from(context).inflate(R.layout.item_list_alimentos, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAnadirDia.ViewHolderAlimentos holder, int position) {
        Alimento currentAlimento = listaAlimentos.get(position);
        volver = false;
        holder.bindTo(currentAlimento);

        holder.setItemClickListener(new ItemClickListener() {

            @Override
            public void onItemClickListener(View v, final int position) {
                pos = position;
                //etDialog = v.findViewById(R.id.etQuantity);


                final View mView = LayoutInflater.from(context).inflate(R.layout.dialog_alimento, null);
                et = mView.findViewById(R.id.etQuantity);
                new AlertDialog.Builder(context)
                        .setTitle(R.string.dialog_title)
                        .setMessage(R.string.dialog_quantity)
                        .setView(mView)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //if (mDatabase == null) return;
                                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        int posicionAlimento = pos;
                                        if (et.getText().toString().isEmpty()) {
                                            Toast.makeText(context, "Por favor, introduzca una cantidad.", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        Long posicion = dataSnapshot.child("calendario").child(diaActual).getChildrenCount();
                                        Float grasas, hidratos, proteinas;
                                        int calorias;
                                        // posicion++;
                                        Map<String, Object> map = new HashMap<>();

                                        for (Long i = 0l; i <= posicionAlimento; i++) {
                                            if (!dataSnapshot.child("alimentos").child(i + "").exists()) {
                                                posicionAlimento++;
                                            }
                                        }
                                        grasas = (parseFloat(dataSnapshot.child("alimentos").child(posicionAlimento + "").child("grasas").getValue().toString()) * parseFloat(et.getText().toString()) / 100f);
                                        ;
                                        hidratos = (parseFloat(dataSnapshot.child("alimentos").child(posicionAlimento + "").child("hidratos").getValue().toString()) * parseFloat(et.getText().toString()) / 100f);
                                        ;
                                        proteinas = (parseFloat(dataSnapshot.child("alimentos").child(posicionAlimento + "").child("proteinas").getValue().toString()) * parseFloat(et.getText().toString()) / 100f);
                                        ;
                                        calorias = (parseInt(dataSnapshot.child("alimentos").child(posicionAlimento + "").child("calorias").getValue().toString()) * parseInt(et.getText().toString()) / 100);
                                        ;
                                        map.put("nombre", dataSnapshot.child("alimentos").child(posicionAlimento + "").child("nombre").getValue().toString());
                                        map.put("marca", dataSnapshot.child("alimentos").child(posicionAlimento + "").child("marca").getValue().toString());
                                        map.put("cantidad", parseFloat(et.getText().toString()));
                                        map.put("unidad", dataSnapshot.child("alimentos").child(posicionAlimento + "").child("unidad").getValue().toString());
                                        map.put("grasas", grasas);
                                        map.put("hidratos", hidratos);
                                        map.put("proteinas", proteinas);
                                        map.put("calorias", calorias);
                                        //listaAlimentos.add(new Alimento(nombre, marca, cantidad, unidad, grasas, hidratos, proteinas, calorias));
                                        //mDatabase.child("calendario").child(diaActual).child(posicion + "").setValue(map);
                                        if (dataSnapshot.child("calendario").child(diaActual).child(posicion + "").exists()) {
                                            posicion++;
                                            mDatabase.child("calendario").child(diaActual).child(posicion + "").setValue(map);
                                            volver=true;
                                        } else {
                                            mDatabase.child("calendario").child(diaActual).child(posicion + "").setValue(map);
                                            volver = true;
                                        }
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
                                Log.d("TAG", "Alimento NO eliminado");
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaAlimentos.size();
    }


    static class ViewHolderAlimentos extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView AlimentoItemView;
        public final TextView CantidadItemView;
        public final TextView GrasasItemView;
        public final TextView HidratosItemView;
        public final TextView ProteinasItemView;
        public final TextView CaloriasItemView;
        ItemClickListener itemClickListener;

        ViewHolderAlimentos(View itemView) {
            super(itemView);
            AlimentoItemView = itemView.findViewById(R.id.idAlimento);
            CantidadItemView = itemView.findViewById(R.id.idCantidad);
            GrasasItemView = itemView.findViewById(R.id.idGrasas);
            HidratosItemView = itemView.findViewById(R.id.idHidratos);
            ProteinasItemView = itemView.findViewById(R.id.idProteinas);
            CaloriasItemView = itemView.findViewById(R.id.idCalorias);
            //this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        void bindTo(Alimento currentAlimento) {
            AlimentoItemView.setText(currentAlimento.getNombre());
            CantidadItemView.setText(currentAlimento.getCantidad() + currentAlimento.getUnidad());
            GrasasItemView.setText("G: " + currentAlimento.getGrasas());
            HidratosItemView.setText("H: " + currentAlimento.getHidratos());
            ProteinasItemView.setText("P: " + currentAlimento.getProteinas());
            CaloriasItemView.setText("Kcal: " + currentAlimento.getCalorias());
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClickListener(v, getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }
    }

}
