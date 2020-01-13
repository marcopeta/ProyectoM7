package es.gracia.marcos.proyectom7.ui.alimentos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

import es.gracia.marcos.proyectom7.R;

public class AlimentosFragment extends Fragment {
/* PROBA RECYCLERVIEW
    RecyclerView.Adapter<AlimentosFragment.AlimentViewHolder> {

        private final LinkedList<String> mWordList;
        private final LayoutInflater mInflater;

        class AlimentViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {
            public final TextView alimentItemView;
            final AlimentosFragment mAdapter;

            public AlimentViewHolder(View itemView, AlimentosFragment adapter) {
                super(itemView);
                alimentItemView = itemView.findViewById(R.id.nav_alimentos);
                this.mAdapter = adapter;
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                // Get the position of the item that was clicked.
                int mPosition = getLayoutPosition();

                // Use that to access the affected item in mWordList.
                String element = mAlimentList.get(mPosition);
                // Change the word in the mWordList.

                mWordList.set(mPosition, "Click! " + element);
                // Notify the adapter, that the data has changed so it can
                // update the RecyclerView to display the data.
                mAdapter.notifyDataSetChanged();
            }
        }

        AlimentosFragment(Context context, LinkedList<String> alimentList) {
            mInflater = LayoutInflater.from(context);
            this.mWordList = alimentList;
        }*/

        public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_alimentos, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        return root;
    }
}