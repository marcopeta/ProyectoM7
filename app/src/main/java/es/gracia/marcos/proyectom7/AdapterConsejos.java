package es.gracia.marcos.proyectom7;

import android.content.Context;
import android.content.Intent;
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

import java.util.LinkedList;

import es.gracia.marcos.proyectom7.ui.alimentos.Alimento;
import es.gracia.marcos.proyectom7.ui.alimentos.ItemClickListener;
import es.gracia.marcos.proyectom7.ui.consejos.Consejo;

public class AdapterConsejos extends RecyclerView.Adapter<AdapterConsejos.ViewHolderConsejos> {
    private final LinkedList<Consejo> listaConsejos;
    private Context context;
    DatabaseReference mDatabase;

    public AdapterConsejos(Context context, LinkedList<Consejo> listaConsejos) {
        this.context = context;
        this.listaConsejos = listaConsejos;
        mDatabase = FirebaseDatabase.getInstance().getReference("Consejos/");

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
            public void onItemClickListener(View v, final int position) {
                String titol = listaConsejos.get(position).getTitol();
                String text = listaConsejos.get(position).getTextConsejo();
                final String autor = listaConsejos.get(position).getAutor();
                String trastorno = listaConsejos.get(position).getTrastorno();
                boolean isAutor = false;
                if (CajaNavegacionActivity.getNom().equals(autor)) {
                    isAutor = true;
                }

                if (isAutor) {
                    Intent intent = new Intent(v.getContext(), ModificarConsejoActivity.class);
                    intent.putExtra("titol", titol);
                    intent.putExtra("text", text);
                    intent.putExtra("autor", autor);
                    intent.putExtra("trastorno", trastorno);
                    intent.putExtra("posicion", position);
                    v.getContext().startActivity(intent);
                } else {
                    Intent intent = new Intent(v.getContext(), VerConsejoActivity.class);

                    intent.putExtra("titol", titol);
                    intent.putExtra("text", text);
                    intent.putExtra("posicion", position);
                    intent.putExtra("autor", autor);

                    v.getContext().startActivity(intent);




                }
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
            TextItemView.setText("");
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
