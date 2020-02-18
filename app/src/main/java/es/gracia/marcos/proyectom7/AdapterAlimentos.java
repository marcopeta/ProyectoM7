package es.gracia.marcos.proyectom7;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

import es.gracia.marcos.proyectom7.ui.alimentos.Alimento;
import es.gracia.marcos.proyectom7.ui.alimentos.ItemClickListener;

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

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                String alimento = listaAlimentos.get(position).getNombre();
                String marca = listaAlimentos.get(position).getMarca();
                Float cantidad = listaAlimentos.get(position).getCantidad();
                String unidad = listaAlimentos.get(position).getUnidad();
                Float grasas = listaAlimentos.get(position).getGrasas();
                Float hidratos = listaAlimentos.get(position).getHidratos();
                Float proteinas = listaAlimentos.get(position).getProteinas();
                int calorias = listaAlimentos.get(position).getCalorias();

                Intent intent = new Intent(v.getContext(), ModificarAlimentoActivity.class);
                intent.putExtra("nombre", alimento);
                intent.putExtra("marca", marca);
                intent.putExtra("cantidad", cantidad);
                intent.putExtra("unidad", unidad);
                intent.putExtra("grasas", grasas);
                intent.putExtra("hidratos", hidratos);
                intent.putExtra("proteinas", proteinas);
                intent.putExtra("calorias", calorias);
                intent.putExtra("posicion", position);
                v.getContext().startActivity(intent);
            }
        });
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
            HidratosItemView.setText("Ch: "+currentAlimento.getHidratos());
            ProteinasItemView.setText("P: "+currentAlimento.getProteinas());
            CaloriasItemView.setText("Kcal: "+currentAlimento.getCalorias());
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
