package es.unex.navdrawertraining.ui.busquedaavanzada;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Locale;

import es.unex.navdrawertraining.R;
import es.unex.navdrawertraining.modelodatos.Local;

public class BusquedaAvanzadaAdapter extends RecyclerView.Adapter<BusquedaAvanzadaAdapter.ViewHolder>{

    private List<Local> todos;
    private List<Local> locales;
    String query;
    int modo;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nombre;
        private final ImageView background;
        private final ImageView icono;
        private final RatingBar rating;
        private final TextView tags;
        private final CardView container;

        public ViewHolder(@NonNull View view) {
            super(view);
            nombre = (TextView)  view.findViewById(R.id.tv_home_item);
            background = (ImageView) view.findViewById(R.id.iv_background_local2);
            icono = (ImageView) view.findViewById(R.id.iv_imagen_local);
            rating = (RatingBar) view.findViewById(R.id.rv_busquedaAvanzada);
            tags = (TextView) view.findViewById(R.id.tv_tags_title);
            container = (CardView) view.findViewById(R.id.cardview_item);
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

        public TextView getTags() {
            return tags;
        }

        public CardView getContainer() {
            return container;
        }
    }

    public BusquedaAvanzadaAdapter () {
        locales = new ArrayList<Local>();
        todos = new ArrayList<Local>();
        query = "";
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_busqueda_avanzada_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getNombre().setText(locales.get(position).getNombre());
        holder.getRating().setRating(locales.get(position).getValoracionGlobal());
        String tags = "";
        for (String tag : locales.get(position).getEtiquetas()) {
            tags = tags +" · " + tag;
        }
        tags = tags.substring(2);
        Log.i("Etiquetas", tags);
        holder.getTags().setText(tags);

        Picasso.get().load(locales.get(position).getFotoPerfil()).into(holder.icono);
        Picasso.get().load(locales.get(position).getFotoBackground()).into(holder.background);

        holder.getContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putLong("id",locales.get(holder.getAdapterPosition()).getId());
                bundle.putString("nombre", locales.get(holder.getAdapterPosition()).getNombre());
                bundle.putString("descripcion", locales.get(holder.getAdapterPosition()).getDescripcion());
                double[] coordenadas = {locales.get(holder.getAdapterPosition()).getCoordenadas().get(0),
                        locales.get(holder.getAdapterPosition()).getCoordenadas().get(1)};
                bundle.putDoubleArray("coordenadas", coordenadas);
                bundle.putFloat("valoracion", locales.get(holder.getAdapterPosition()).getValoracionGlobal());
                bundle.putString("fotoPerfil", locales.get(holder.getAdapterPosition()).getFotoPerfil());
                bundle.putString("fotoBackground", locales.get(holder.getAdapterPosition()).getFotoBackground());
                bundle.putInt("aforoTotal", locales.get(holder.getAdapterPosition()).getAforoTotal());
                bundle.putInt("ocupacionActual", locales.get(holder.getAdapterPosition()).getOcupacionActual());
                bundle.putStringArrayList("tags", new ArrayList<String>(locales.get(holder.getAdapterPosition()).getEtiquetas()));
                Navigation.findNavController(view).navigate(R.id.action_nav_busquedaAvanzada_to_nav_detalles, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return locales.size();
    }

    public void swap(List<Local> locales) {
        this.locales = locales;
        this.todos = locales;
        notifyDataSetChanged();
    }
    public void filter () {
        switch (modo){
            case 1: //todos
                    locales =  new ArrayList<Local>();
                    for (int i = 0; i < todos.size(); i++) {
                        if (todos.get(i).getNombre().toLowerCase().contains(query.toLowerCase())){
                            locales.add(todos.get(i));
                        }
                   }
                   break;

            case 2: //solo con sitio
                    locales =  new ArrayList<Local>();
                    for (int i = 0; i < todos.size(); i++) {
                        if (todos.get(i).getNombre().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT)) &&
                                todos.get(i).getAforoTotal().intValue() != todos.get(i).getOcupacionActual().intValue()){
                            locales.add(todos.get(i));
                        }
                    }
                    break;
        }
        notifyDataSetChanged();

    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getModo() {
        return modo;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }
}
