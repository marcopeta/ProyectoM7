package es.gracia.marcos.proyectom7;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.LinkedList;

import es.gracia.marcos.proyectom7.ui.alimentos.Alimento;
import es.gracia.marcos.proyectom7.ui.alimentos.ItemClickListener;
import es.gracia.marcos.proyectom7.ui.inicio.InicioFragment;


public class AdapterAlimentosDia extends RecyclerView.Adapter<AdapterAlimentosDia.ViewHolderAlimentos> {
    private final LinkedList<Alimento> listaAlimentos;
    private Context context;
    private static DatabaseReference mDatabase;
    static String diaActual;
    Calendar currentTime;





    public AdapterAlimentosDia(Context context, LinkedList<Alimento> listaAlimentos) {
        this.context = context;
        this.listaAlimentos = listaAlimentos;
        mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + CajaNavegacionActivity.getUser().getUid());
        currentTime = Calendar.getInstance();

        diaActual = currentTime.get(Calendar.DAY_OF_MONTH) + "-" + (currentTime.get(Calendar.MONTH) + 1) + "-" + currentTime.get(Calendar.YEAR);


    }

    @Override
    public ViewHolderAlimentos onCreateViewHolder(ViewGroup parent,
                                                  int viewType) {
        return new ViewHolderAlimentos(LayoutInflater.from(context).
                inflate(R.layout.item_list_alimentos, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolderAlimentos holder, int position) {
        Alimento currentAlimento = listaAlimentos.get(position);
        holder.bindTo(currentAlimento);
    }

    @Override
    public int getItemCount() {
        return listaAlimentos.size();
    }

    class ViewHolderAlimentos extends RecyclerView.ViewHolder implements View.OnClickListener{

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
            GrasasItemView.setText("G: "+currentAlimento.getGrasas());
            HidratosItemView.setText("H: "+currentAlimento.getHidratos());
            ProteinasItemView.setText("P: "+currentAlimento.getProteinas());
            CaloriasItemView.setText("Kcal: "+currentAlimento.getCalorias());
        }

        @Override
        public void onClick(View v) {
            mostrarDialogo();
        }


        public void mostrarDialogo(){
            new AlertDialog.Builder(context)
                    .setTitle("Gestió d'Aliments")
                    .setMessage("Vol eliminar aquest Aliment de la llista?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,final int which) {
                            if (mDatabase == null) return;
                            mDatabase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    // Long acaba = dataSnapshot.child("calendario").child(diaActual).getChildrenCount();
                                    int position =  which;
                                    for (Long i = 0l; i <= position; i++) {
                                        if (!dataSnapshot.child("calendario").child(diaActual).child(i + "").exists()) {
                                            position++;
                                        }
                                    }
                                    mDatabase.child("calendario").child(diaActual).child(position + "").removeValue();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    return;
                                }
                            });                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("HOLA", "Alimento NO eliminado");
                        }
                    })
                    .show();
        }


    }
}
