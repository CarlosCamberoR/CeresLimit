package es.unex.navdrawertraining.ui.detalles;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.R;
import es.unex.navdrawertraining.databinding.FragmentDetallesBinding;
import es.unex.navdrawertraining.modelodatos.Comentario;
import es.unex.navdrawertraining.modelodatos.Favorito;
import es.unex.navdrawertraining.modelodatos.Local;
import es.unex.navdrawertraining.modelodatos.Valoracion;
import es.unex.navdrawertraining.repoexterno.LocalesNetworkLoaderRunnable;
import es.unex.navdrawertraining.repoexterno.OnLocalesLoaderListener;
import es.unex.navdrawertraining.roomdb.CeresLimitDatabase;
import es.unex.navdrawertraining.roomdb.FavoritoDao;
import es.unex.navdrawertraining.ui.resena.ComentariosDiskLoaderRunnable;
import es.unex.navdrawertraining.ui.resena.OnComentariosLoadedListener;
import es.unex.navdrawertraining.ui.resena.OnValoracionLoadedListener;
import es.unex.navdrawertraining.ui.resena.ValoracionDiskLoaderRunnable;


public class DetallesFragment extends Fragment {

    private FragmentDetallesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDetallesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Nombre del local
        TextView nombreLocal = binding.tvNombreLocal;
        nombreLocal.setText(getArguments().getString("nombre"));

        //Descripcion del local
        TextView descripcion = binding.tvDescipcion;
        descripcion.setText(getArguments().getString("descripcion"));

        //Imagenes de background y perfil
        Picasso.get().load(getArguments().getString("fotoPerfil")).into(binding.ivLogoLocal);
        Picasso.get().load(getArguments().getString("fotoBackground")).into(binding.ivBackgroundLocal);

        //Progress bar y el porcentaje de dentro de él
        ProgressBar progressBar = binding.progressBar;
        int capacidad = getArguments().getInt("ocupacionActual") * 100 / getArguments().getInt("aforoTotal") ;
        progressBar.setProgress(capacidad);
        binding.tvDetallesNumaforo.setText(getArguments().getInt("ocupacionActual") + " / " + getArguments().getInt("aforoTotal"));
        binding.swipeRlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AppExecutors.getInstance().networkIO().execute(new LocalesNetworkLoaderRunnable(new OnLocalesLoaderListener() {
                    @Override
                    public void onLocalesLoaded(List<Local> locales) {
                        for(Local l: locales){
                            if(l.getId() == getArguments().getLong("id")) {
                                int oc=(int)(Math.random() * (l.getAforoTotal()- l.getOcupacionActual()) + l.getOcupacionActual());
                                int cp= oc * 100 / l.getAforoTotal();
                                // Simulamos que el aforo ha cambiado respecto a su ultimo valor multiplicandolo por un numero
                                // aleatorio, pero aun así lo obtenemos del repositorio externo para que sea "realista".
                                getActivity().runOnUiThread(() ->   {progressBar.setProgress(cp);
                                    binding.tvDetallesNumaforo.setText(oc + " / " + getArguments().getInt("aforoTotal"));} );
                                break;
                            }
                        }
                        getActivity().runOnUiThread(() -> binding.swipeRlayout.setRefreshing(false));
                    }
                }));
            }
        });

        binding.fbReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putLong("id",getArguments().getLong("id"));
                bundle.putString("nombre", getArguments().getString("nombre"));
                bundle.putString("fotoPerfil", getArguments().getString("fotoPerfil"));
                bundle.putString("fotoBackground", getArguments().getString("fotoBackground"));
                Navigation.findNavController(view).navigate(R.id.action_nav_detalles_to_nav_hacer_reserva2, bundle);
            }
        });

        //Rating bar
        RatingBar rbValoracion = binding.ratingbarLocal;

        AppExecutors.getInstance().diskIO().execute(new ValoracionDiskLoaderRunnable(new OnValoracionLoadedListener() {
            @Override
            public void onValoracionLoaded(Valoracion valoracion) {
                if (valoracion != null) {
                    binding.sinValoracion.setVisibility(View.GONE);
                    rbValoracion.setRating(valoracion.getPuntuacion());
                }
            }
        }, getContext(), getArguments().getLong("id")));

        AppExecutors.getInstance().diskIO().execute(new ComentariosDiskLoaderRunnable(new OnComentariosLoadedListener() {
            @Override
            public void onComentariosLoaded(Comentario comentario) {
                if (comentario!=null){
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            binding.tvCuerpoComentario.setText(comentario.getCadena_comentario());
                            String nombreUsuario = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("nombreUsuario","Kiko");
                            if (nombreUsuario.isEmpty()) {
                                nombreUsuario = "Kiko";
                            }
                            binding.tvNombreUsuario.setText(nombreUsuario);
                            binding.tvDetalles5mentarios.setVisibility(View.GONE);
                            binding.cardviewDetallesComentario.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        }, getContext(), getArguments().getLong("id")));

        binding.bResena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putLong("id", getArguments().getLong("id"));
                bundle.putString("nombre", getArguments().getString("nombre"));
                bundle.putString("fotoPerfil", getArguments().getString("fotoPerfil"));
                bundle.putString("fotoBackground", getArguments().getString("fotoBackground"));
                Navigation.findNavController(view).navigate(R.id.detalles_to_resena_action, bundle);

            }
        });

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if(CeresLimitDatabase.getInstance(getContext()).getDaoFavorito().get(getArguments().getLong("id")) != null){
                    getActivity().runOnUiThread(() -> binding.ibDetallesFavorito.setImageResource(R.drawable.icono_favorito));
                }
            }
        });
        binding.ibDetallesFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        FavoritoDao favoritoDao = CeresLimitDatabase.getInstance(getContext()).getDaoFavorito();
                        if(favoritoDao.get(getArguments().getLong("id")) != null) {
                            favoritoDao.delete(getArguments().getLong("id"));
                            getActivity().runOnUiThread(() -> binding.ibDetallesFavorito.setImageResource(R.drawable.icono_nofav));
                        } else {
                            favoritoDao.insert(new Favorito(getArguments().getLong("id")));
                            getActivity().runOnUiThread(() -> binding.ibDetallesFavorito.setImageResource(R.drawable.icono_favorito));
                        }
                    }
                });
            }
        });

        binding.ibDetallesCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle ();
                bundle.putLong("id", getArguments().getLong("id"));
                Navigation.findNavController(view).navigate(R.id.anadir_local_categoria_action, bundle);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}