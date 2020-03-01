package es.gracia.marcos.proyectom7;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

import es.gracia.marcos.proyectom7.ui.alimentos.Alimento;
import es.gracia.marcos.proyectom7.ui.alimentos.ItemClickListener;
import es.gracia.marcos.proyectom7.ui.consejos.Consejo;

public class AdapterConsejos extends RecyclerView.Adapter<AdapterConsejos.ViewHolderConsejos> {
    private final LinkedList<Consejo> listaConsejos;
    private Context context;

    public AdapterConsejos(Context context, LinkedList<Consejo> listaConsejos) {
        this.context = context;
        this.listaConsejos = listaConsejos;
    }

    @NonNull
    @Override
    public ViewHolderConsejos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterConsejos.ViewHolderConsejos(LayoutInflater.from(context).
                inflate(R.layout.item_list_consejos, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderConsejos holder, int position) {
        Consejo currentConsejo = listaConsejos.get(position);
        holder.bindTo(currentConsejo);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                String titol = listaConsejos.get(position).getTitol();
                String text = listaConsejos.get(position).getTextConsejo();
                String autor = listaConsejos.get(position).getAutor();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaConsejos.size();
    }

    class ViewHolderConsejos extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView TitolItemView;
        public final TextView TextItemView;
        public final TextView AutorItemView;

        ItemClickListener itemClickListener;

        ViewHolderConsejos(View itemView) {
            super(itemView);
            TitolItemView = itemView.findViewById(R.id.idTitol);
            TextItemView = itemView.findViewById(R.id.idText);
            AutorItemView = itemView.findViewById(R.id.idAutor);

            //this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        void bindTo(Consejo currentConsejo) {
            TitolItemView.setText(currentConsejo.getTitol());
            TextItemView.setText(currentConsejo.getTextConsejo());
            AutorItemView.setText(" -" + currentConsejo.getAutor());
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
