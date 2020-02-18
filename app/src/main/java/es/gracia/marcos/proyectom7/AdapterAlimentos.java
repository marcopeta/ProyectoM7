package es.gracia.marcos.proyectom7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

import es.gracia.marcos.proyectom7.ui.alimentos.Alimento;

public class AdapterAlimentos extends RecyclerView.Adapter<AdapterAlimentos.ViewHolderAlimentos> {
    private final LinkedList<Alimento> listaAlimentos;
    private Context context;

    public AdapterAlimentos(Context context, LinkedList<Alimento> listaAlimentos) {
        this.context = context;
        this.listaAlimentos = listaAlimentos;
    }

    @Override
    public AdapterAlimentos.ViewHolderAlimentos onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        return new ViewHolderAlimentos(LayoutInflater.from(context).
                inflate(R.layout.item_list_alimentos, parent, false));
    }

    @Override
    public void onBindViewHolder(AdapterAlimentos.ViewHolderAlimentos holder, int position) {
        Alimento currentAlimento = listaAlimentos.get(position);
        holder.bindTo(currentAlimento);
    }

    @Override
    public int getItemCount() {
        return listaAlimentos.size();
    }

    class ViewHolderAlimentos extends RecyclerView.ViewHolder
            /*implements View.OnClickListener*/ {
        public final TextView AlimentoItemView;
        public final TextView CantidadItemView;
        public final TextView GrasasItemView;
        public final TextView HidratosItemView;
        public final TextView ProteinasItemView;
        public final TextView CaloriasItemView;

        ViewHolderAlimentos(View itemView) {
            super(itemView);
            AlimentoItemView = itemView.findViewById(R.id.idAlimento);
            CantidadItemView = itemView.findViewById(R.id.idCantidad);
            GrasasItemView = itemView.findViewById(R.id.idGrasas);
            HidratosItemView = itemView.findViewById(R.id.idHidratos);
            ProteinasItemView = itemView.findViewById(R.id.idProteinas);
            CaloriasItemView = itemView.findViewById(R.id.idCalorias);
            //this.mAdapter = adapter;
            /*itemView.setOnClickListener(this);*/
        }

        void bindTo(Alimento currentAlimento) {
            AlimentoItemView.setText(currentAlimento.getNombre());
            CantidadItemView.setText(currentAlimento.getCantidad() + currentAlimento.getUnidad());
            GrasasItemView.setText("G: "+currentAlimento.getGrasas());
            HidratosItemView.setText("Ch: "+currentAlimento.getHidratos());
            ProteinasItemView.setText("P: "+currentAlimento.getProteinas());
            CaloriasItemView.setText("Kc: "+currentAlimento.getCalorias());
        }

        /*@Override
        public void onClick(View v) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in listaAlimentos.
            String element = listaAlimentos.get(mPosition);
            // Change the word in the listaAlimentos.
            listaAlimentos.set(mPosition, "COMPRADO! " + element);
            // Notify the adapter, that the data has changed so it can
            // update the RecyclerView to display the data.
            mAdapter.notifyDataSetChanged();
        }*/
    }
}
