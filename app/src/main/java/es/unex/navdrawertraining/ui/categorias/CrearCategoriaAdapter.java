package es.unex.navdrawertraining.ui.categorias;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.unex.navdrawertraining.R;

public class CrearCategoriaAdapter extends RecyclerView.Adapter<CrearCategoriaAdapter.ViewHolder>{
    private final ArrayList<Integer> iconos;
    private int lastCheckedIcon = -1;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView icono;
        private final RadioButton radio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icono = (ImageView)  itemView.findViewById(R.id.iv_crearcategoria_icono_item);
            radio = (RadioButton) itemView.findViewById(R.id.rb_crearcategoria_radio);
            radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastCheckedIcon = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }

        public ImageView getIcono() {
            return icono;
        }

        public RadioButton getRadio() {
            return radio;
        }
    }
    public  CrearCategoriaAdapter() {
        //TODO deuda tecnica
        List<Integer> auxiliar = Arrays.asList(R.drawable.icono_cat_alcohol, R.drawable.icono_cat_burger, R.drawable.icono_cat_burger_drink,
                                                R.drawable.icono_cat_cafe, R.drawable.icono_cat_cerveza, R.drawable.icono_cat_chino,
                                                R.drawable.icono_cat_cocktail, R.drawable.icono_cat_cookie, R.drawable.icono_cat_croissant,
                                                R.drawable.icono_cat_familia, R.drawable.icono_cat_fiesta, R.drawable.icono_cat_helado,
                                                R.drawable.icono_cat_pasta, R.drawable.icono_cat_pescado, R.drawable.icono_cat_petfriendly,
                                                R.drawable.icono_cat_pizza, R.drawable.icono_cat_restaurante, R.drawable.icono_cat_sopa,
                                                R.drawable.icono_cat_tarta, R.drawable.icono_cat_te, R.drawable.icono_cat_tostada,
                                                R.drawable.icono_cat_vegetariano);

        iconos = new ArrayList<>();
        iconos.addAll(auxiliar);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_crear_categoria_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getIcono().setImageResource(iconos.get(position));

        holder.getRadio().setChecked(lastCheckedIcon == position);
    }

    @Override
    public int getItemCount() {
        return iconos.size();
    }

    public int getLastCheckedIcon() {
        return lastCheckedIcon;
    }

    public ArrayList<Integer> getIconos() {
        return iconos;
    }
}