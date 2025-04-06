package es.unex.navdrawertraining.ui.home;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.navdrawertraining.R;
import es.unex.navdrawertraining.modelodatos.Categoria;

public class HomeCategoriasAdapter extends RecyclerView.Adapter<HomeCategoriasAdapter.ViewHolder> {
    private List<Categoria> categorias;
    private final Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nombre;
        private final ImageView icono;
        public ViewHolder(@NonNull View view) {
            super(view);
            nombre = (TextView)  view.findViewById(R.id.tv_nombre_cat);
            icono = (ImageView) view.findViewById(R.id.iv_icono_categoria);

            icono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putLong("id", categorias.get(getAdapterPosition()).getId_categoria());
                    Navigation.findNavController(view).navigate(R.id.home_to_locales_categoria_action, bundle);
                }
            });
        }

        public TextView getNombre() {
            return nombre;
        }

        public ImageView getIcono() {
            return icono;
        }
    }

    public HomeCategoriasAdapter(Context context) {
        categorias = new ArrayList<Categoria>();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_categorias_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getNombre().setText(categorias.get(position).getNombre_categoria());
        holder.getIcono().setImageResource(context.getResources().getIdentifier(categorias.get(position).getIcono_categoria(), "drawable", context.getPackageName()));
        holder.getIcono().setColorFilter(context.getResources().getColor(context.getResources().getIdentifier(categorias.get(position).getColor_categoria(), "color", context.getPackageName())));
        Log.i("ColorCategoria", categorias.get(position).getColor_categoria());
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public void swap (List<Categoria> categorias) {
        this.categorias = categorias;
        notifyDataSetChanged();
    }
}
