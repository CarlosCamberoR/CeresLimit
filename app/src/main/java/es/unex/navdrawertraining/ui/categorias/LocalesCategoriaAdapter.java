package es.unex.navdrawertraining.ui.categorias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.R;
import es.unex.navdrawertraining.modelodatos.Local;
import es.unex.navdrawertraining.modelodatos.LocalCategoria;
import es.unex.navdrawertraining.roomdb.CeresLimitDatabase;

public class LocalesCategoriaAdapter extends RecyclerView.Adapter<LocalesCategoriaAdapter.ViewHolder> {
    List<LocalCategoria> localesCategoria;
    List<Local> locales;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nombre;
        private final ImageView background;
        private final ImageView icono;
        private final RatingBar rating;
        private final ImageButton eliminarLocal;
        private final CardView container;

        public ViewHolder(@NonNull View view) {
            super(view);
            nombre = (TextView)  view.findViewById(R.id.tv_localesCategoria_nombre_item);
            background = (ImageView) view.findViewById(R.id.iv_localesCategoria_background_local);
            icono = (ImageView) view.findViewById(R.id.iv_localesCategoria_imagen_local);
            rating = (RatingBar) view.findViewById(R.id.rv_localesCategorias_rating);
            eliminarLocal = (ImageButton) view.findViewById(R.id.ib_localesCategorias_eliminar);
            container = (CardView) view.findViewById(R.id.cardview_localesCategoria_item);
        }

        public TextView getNombre() {
            return nombre;
        }

        public ImageView getBackground() {
            return background;
        }

        public ImageView getIcono() {
            return icono;
        }

        public RatingBar getRating() {
            return rating;
        }

        public ImageButton getEliminarLocal() {
            return eliminarLocal;
        }

        public CardView getContainer() {
            return container;
        }
    }

    public LocalesCategoriaAdapter() {
        localesCategoria = new ArrayList<LocalCategoria>();
        locales = new ArrayList<Local>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_locales_categoria_item_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Local local = new Local();
        for(int i = 0; i < locales.size(); i++) {
            if (locales.get(i).getId() == localesCategoria.get(position).getId_local()) {
                local = locales.get(i);
                break;
            }
        }
        holder.getNombre().setText(local.getNombre());
        holder.getRating().setRating(local.getValoracionGlobal());
        Picasso.get().load(local.getFotoPerfil()).into(holder.icono);
        Picasso.get().load(local.getFotoBackground()).into(holder.background);

        holder.getEliminarLocal().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppExecutors.getInstance().diskIO().execute(() -> {
                    CeresLimitDatabase.getInstance(view.getContext()).getDaoLocalCategoria().delete(localesCategoria.get(holder.getAdapterPosition()).getId_categoria(), localesCategoria.get(holder.getAdapterPosition()).getId_local());
                    localesCategoria.remove(holder.getAdapterPosition());
                    AppExecutors.getInstance().mainThread().execute(() -> notifyDataSetChanged());
                });
            }
        });

        final Local finalLocal = local;
        holder.getContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putLong("id", finalLocal.getId());
                bundle.putString("nombre", finalLocal.getNombre());
                bundle.putString("descripcion", finalLocal.getDescripcion());
                double[] coordenadas = {finalLocal.getCoordenadas().get(0), finalLocal.getCoordenadas().get(1)};
                bundle.putDoubleArray("coordenadas", coordenadas);
                bundle.putFloat("valoracion", finalLocal.getValoracionGlobal());
                bundle.putString("fotoPerfil", finalLocal.getFotoPerfil());
                bundle.putString("fotoBackground", finalLocal.getFotoBackground());
                bundle.putInt("aforoTotal", finalLocal.getAforoTotal());
                bundle.putInt("ocupacionActual", finalLocal.getOcupacionActual());
                bundle.putStringArrayList("tags", new ArrayList<String>(finalLocal.getEtiquetas()));
                Navigation.findNavController(view).navigate(R.id.local_categoria_to_detalles_action, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localesCategoria.size();
    }

    public void swap(List<LocalCategoria> localesCategoria, List<Local> locales) {
        this.localesCategoria = localesCategoria;
        this.locales = locales;
        notifyDataSetChanged();

    }
}
