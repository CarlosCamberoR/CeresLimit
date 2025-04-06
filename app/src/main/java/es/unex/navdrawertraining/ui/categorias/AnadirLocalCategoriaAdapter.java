package es.unex.navdrawertraining.ui.categorias;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.R;
import es.unex.navdrawertraining.modelodatos.Categoria;
import es.unex.navdrawertraining.modelodatos.LocalCategoria;
import es.unex.navdrawertraining.roomdb.CeresLimitDatabase;
import es.unex.navdrawertraining.roomdb.LocalCategoriaDao;

public class AnadirLocalCategoriaAdapter extends RecyclerView.Adapter<AnadirLocalCategoriaAdapter.ViewHolder>
{
    private List<Categoria> categorias;
    private long id_local;
    private Activity activity;
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nombre;
        private final ImageView icono;
        private final CardView container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.tv_nombre_cat3);
            icono = (ImageView)  itemView.findViewById(R.id.iv_icono_categoria3);
            container = (CardView) itemView.findViewById(R.id.cardview_anadirlocal_categoria);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            LocalCategoria localCategoria = new LocalCategoria(categorias.get(getAdapterPosition()).getId_categoria(), id_local);
                            LocalCategoriaDao localCategoriaDao = CeresLimitDatabase.getInstance(view.getContext()).getDaoLocalCategoria();

                            if(localCategoriaDao.get(localCategoria.getId_categoria(), localCategoria.getId_local()) == null){
                                localCategoriaDao.insert(localCategoria);
                                AppExecutors.getInstance().mainThread().execute(() -> activity.onBackPressed());
                            } else {

                                AppExecutors.getInstance().mainThread().execute(() ->Toast.makeText(view.getContext(),
                                        "Este local ya está añadido en la categoría seleccionada. Escoja otra.", Toast.LENGTH_LONG).show());
                            }
                        }
                    });
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

    public AnadirLocalCategoriaAdapter(long id_local, Activity activity){
        categorias = new ArrayList<Categoria>();
        this.id_local = id_local;
        this.activity = activity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_anadir_local_categoria_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getNombre().setText(categorias.get(position).getNombre_categoria());
        holder.getIcono().setImageResource(((Context)activity).getResources().getIdentifier(categorias.get(position).getIcono_categoria(), "drawable", ((Context)activity).getPackageName()));
        holder.getIcono().setColorFilter(((Context)activity).getResources().getColor(((Context)activity).getResources().getIdentifier(categorias.get(position).getColor_categoria(), "color", ((Context)activity).getPackageName())));

    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public long getId_local() {
        return id_local;
    }

    public void setId_local(long id_local) {
        this.id_local = id_local;
    }

    public void swap (List<Categoria> categorias) {
        this.categorias = categorias;
        notifyDataSetChanged();
    }

}
