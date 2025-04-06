package es.unex.navdrawertraining.ui.categorias;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.R;
import es.unex.navdrawertraining.modelodatos.Categoria;
import es.unex.navdrawertraining.roomdb.CeresLimitDatabase;

public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.ViewHolder>{
    private List<Categoria> categorias;
    private final Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nombre;
        private final ImageView icono;
        private final ImageButton borrar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.tv_nombre_cat2);
            icono = (ImageView)  itemView.findViewById(R.id.iv_icono_categoria2);
            borrar = (ImageButton)  itemView.findViewById(R.id.ib_categorias_borrar);
            borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            CeresLimitDatabase.getInstance(view.getContext()).getDaoCategoria().
                                    delete(categorias.get(getAdapterPosition()).getId_categoria());
                            categorias.remove(getAdapterPosition());
                            AppExecutors.getInstance().mainThread().execute(() -> notifyDataSetChanged());
                        }
                    });

                }
            });
            icono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putLong("id", categorias.get(getAdapterPosition()).getId_categoria());
                    Navigation.findNavController(view).navigate(R.id.locales_categoria_action, bundle);
                }
            });
        }

        public TextView getNombre() {
            return nombre;
        }

        public ImageView getIcono() {
            return icono;
        }

        public ImageButton getBorrar() {
            return borrar;
        }
    }

    public CategoriasAdapter(Context context){
        categorias = new ArrayList<Categoria>();
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_categorias_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getNombre().setText(categorias.get(position).getNombre_categoria());
        holder.getIcono().setImageResource(context.getResources().getIdentifier(categorias.get(position).getIcono_categoria(), "drawable", context.getPackageName()));
        holder.getIcono().setColorFilter(context.getResources().getColor(context.getResources().getIdentifier(categorias.get(position).getColor_categoria(), "color", context.getPackageName())));

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
