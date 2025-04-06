package es.unex.navdrawertraining.ui.detalles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import es.unex.navdrawertraining.R;
import es.unex.navdrawertraining.ui.busquedaavanzada.BusquedaAvanzadaAdapter;

public class DetallesComentarioAdapter extends RecyclerView.Adapter<DetallesComentarioAdapter.ViewHolder> {

    String dummies[];

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nombre;
        private final TextView comentario;

        public ViewHolder(@NonNull View view) {
            super(view);
            nombre = (TextView) view.findViewById(R.id.tv_nombre_usuario);
            comentario = (TextView) view.findViewById(R.id.tv_cuerpo_comentario);
        }

        public TextView getNombre() {
            return nombre;
        }

        public TextView getComentario() {
            return comentario;
        }
    }

    public DetallesComentarioAdapter(){
        dummies = new String[4];
        dummies[0] = "Carlos";
        dummies[1] = "Santiago";
        dummies[2] = "Maria";
        dummies[3] = "Raquel";
    }

    @NonNull
    @Override
    public DetallesComentarioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_detalles_comentario, parent, false);

        return new DetallesComentarioAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetallesComentarioAdapter.ViewHolder holder, int position) {
        holder.getNombre().setText(dummies[position%4]);
        holder.getComentario().setText("Me ha encantado el sitio, estoy deseando volver. Es más, la semana siguiente volveré. Esta app es genial.");
    }

    @Override
    public int getItemCount() {
        return dummies.length;
    }
}
