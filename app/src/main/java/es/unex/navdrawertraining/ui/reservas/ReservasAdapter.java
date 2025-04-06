package es.unex.navdrawertraining.ui.reservas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.R;
import es.unex.navdrawertraining.modelodatos.Local;
import es.unex.navdrawertraining.modelodatos.Reserva;
import es.unex.navdrawertraining.roomdb.CeresLimitDatabase;
import es.unex.navdrawertraining.roomdb.DateConverter;

public class ReservasAdapter extends RecyclerView.Adapter<ReservasAdapter.ViewHolder> {
    private List<Reserva> reservas;
    private List<Local> locales;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView text;
        private final ImageButton delete;
        private final TextView fecha;
        private final TextView hora;
        private final ImageView icono;


        public ViewHolder(@NonNull View view) {
            super(view);
            text = (TextView)  view.findViewById(R.id.tv_nombre_local);
            delete=(ImageButton) view.findViewById(R.id.ib_reserva_eliminar);
            fecha = (TextView)  view.findViewById(R.id.tv_fecha_reserva);
            hora = (TextView)  view.findViewById(R.id.tv_hora_reserva);
            icono = (ImageView) view.findViewById(R.id.iv_imagen_local);
        }
        public TextView getText() {
            return text;
        }
        public ImageButton getDelete() {
            return delete;
        }
        public TextView getFecha() {
            return fecha;
        }

        public TextView getHora() {
            return hora;
        }
        public ImageView getIcono() {
            return icono;
        }

    }

    public ReservasAdapter () {
        reservas = new ArrayList<Reserva>();
        locales = new ArrayList<Local>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_reservas_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(reservas.get(position).getFecha_hora_reserva());
        holder.getFecha().setText(new StringBuilder().append(calendar.get(Calendar.DAY_OF_MONTH))
                .append("/").append(calendar.get(Calendar.MONTH))
                .append("/").append(calendar.get(Calendar.YEAR)).toString());
        String sMinuto = null;
        if (calendar.get(Calendar.MINUTE) < 10) {
            sMinuto = "0" + String.valueOf(calendar.get(Calendar.MINUTE));
        } else {
            sMinuto = String.valueOf(calendar.get(Calendar.MINUTE));
        }
        holder.getHora().setText(new StringBuilder().append(calendar.get(Calendar.HOUR_OF_DAY))
                .append(":").append(sMinuto));

        long id = reservas.get(position).getId_reserva();
        Local local = new Local();
        for (int i = 0; i < locales.size(); i++) {
            if (locales.get(i).getId() == id) {
                local = locales.get(i);
                break;
            }
        }
        holder.getText().setText(local.getNombre());
        Picasso.get().load(local.getFotoPerfil()).into(holder.getIcono());

        holder.getDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        CeresLimitDatabase.getInstance(view.getContext()).getDaoReserva().delete(
                                reservas.get(holder.getAdapterPosition()).getId_reserva(),
                                DateConverter.toTimestamp(reservas.get(holder.getAdapterPosition()).getFecha_hora_reserva()));
                        AppExecutors.getInstance().mainThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                reservas.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();
                            }
                        });
                    }
                });
            };
        });
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    public void swap(List<Reserva> reservas, List<Local> locales) {
        this.reservas = reservas;
        this.locales = locales;
        notifyDataSetChanged();

    }
}
