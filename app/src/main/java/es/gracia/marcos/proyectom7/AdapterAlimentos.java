package es.gracia.marcos.proyectom7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class AdapterAlimentos extends RecyclerView.Adapter<AdapterAlimentos.ViewHolderAlimentos> {
    private final LinkedList<String> listaAlimentos;
    private LayoutInflater mInflater;

    public AdapterAlimentos(Context context,
                           LinkedList<String> listaAlimentos) {
        mInflater = LayoutInflater.from(context);
        this.listaAlimentos = listaAlimentos;
    }

    @NonNull
    @Override
    public ViewHolderAlimentos onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View mItemView = mInflater.inflate(R.layout.item_list_alimentos,
                parent, false);
        return new ViewHolderAlimentos(mItemView, this);
    }

    @Override
    public void onBindViewHolder(ViewHolderAlimentos holder, int position) {
        String mCurrent = listaAlimentos.get(position);
        holder.AlimentoItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return listaAlimentos.size();
    }

    class ViewHolderAlimentos extends RecyclerView.ViewHolder
            /*implements View.OnClickListener*/ {
        public final TextView AlimentoItemView;
        final AdapterAlimentos mAdapter;

        public ViewHolderAlimentos(View itemView, AdapterAlimentos adapter) {
            super(itemView);
            AlimentoItemView = itemView.findViewById(R.id.idAlimento);
            this.mAdapter = adapter;
            /*itemView.setOnClickListener(this);*/
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
