package es.unex.navdrawertraining.ui.home;

import android.os.Bundle;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import es.unex.navdrawertraining.R;
import es.unex.navdrawertraining.modelodatos.Local;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<Local> locales;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nombre;
        private final ImageView background;
        private final ImageView icono;
        private final RatingBar rating;
        private final CardView container;
        public ViewHolder(@NonNull View view) {
            super(view);
            nombre = (TextView)  view.findViewById(R.id.tv_home_item);
            background = (ImageView) view.findViewById(R.id.iv_background_local);
            icono = (ImageView) view.findViewById(R.id.iv_icono_local);
            rating = (RatingBar) view.findViewById(R.id.rb_rating_local);
            container = (CardView) view.findViewById(R.id.cardview_item);
        }

        public TextView getNombre() {
            return nombre;
        }

        public ImageView getBackground() { return background; }

        public ImageView getIcono() { return icono; }

        public RatingBar getRating() { return rating; }

        public CardView getContainer() {
            return container;
        }
    }

    public HomeAdapter() {
        locales = new ArrayList<Local>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getNombre().setText(locales.get(position).getNombre());
        holder.getRating().setRating(locales.get(position).getValoracionGlobal());

        holder.getContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putLong("id",locales.get(holder.getAdapterPosition()).getId());
                bundle.putString("nombre", locales.get(holder.getAdapterPosition()).getNombre());
                bundle.putString("descripcion", locales.get(holder.getAdapterPosition()).getDescripcion());
                double[] coordenadas = {locales.get(holder.getAdapterPosition()).getCoordenadas().get(0), locales.get(holder.getAdapterPosition()).getCoordenadas().get(1)};
                bundle.putDoubleArray("coordenadas", coordenadas);
                bundle.putFloat("valoracion", locales.get(holder.getAdapterPosition()).getValoracionGlobal());
                bundle.putString("fotoPerfil", locales.get(holder.getAdapterPosition()).getFotoPerfil());
                bundle.putString("fotoBackground", locales.get(holder.getAdapterPosition()).getFotoBackground());
                bundle.putInt("aforoTotal", locales.get(holder.getAdapterPosition()).getAforoTotal());
                bundle.putInt("ocupacionActual", locales.get(holder.getAdapterPosition()).getOcupacionActual());
                bundle.putStringArrayList("tags", new ArrayList<String>(locales.get(holder.getAdapterPosition()).getEtiquetas()));
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_detalles, bundle);

            }
        });

        Picasso.get().load(locales.get(position).getFotoPerfil()).into(holder.icono);
        Picasso.get().load(locales.get(position).getFotoBackground()).into(holder.background);
    }

    @Override
    public int getItemCount() {
        return (locales.size() / 3) % 21; // Solo queremos mostrar unos pocos
    }

    public void swap(List<Local> locales) {
        this.locales = locales;
        for(int i = 0; i < locales.size(); i++) {
            if (locales.get(i).getOcupacionActual().intValue() == locales.get(i).getAforoTotal().intValue()) {
                this.locales.remove(i);
            }
        }
        Comparator<Local> comparador = new Comparator<Local>() {
            @Override
            public int compare(Local local, Local t1) {
                if (local.getValoracionGlobal() == t1.getValoracionGlobal()) {
                    if(((float)local.getOcupacionActual()/local.getAforoTotal())
                            == ((float)t1.getOcupacionActual()/t1.getAforoTotal())) {
                        return 0;
                    } else {
                        if ((local.getOcupacionActual()*1.0f/local.getAforoTotal())
                                > (t1.getOcupacionActual()*1.0f/t1.getAforoTotal())) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                } else {
                    if (local.getValoracionGlobal() > t1.getValoracionGlobal()){
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        };
        Collections.sort(locales, comparador);

    notifyDataSetChanged();
    }
}
