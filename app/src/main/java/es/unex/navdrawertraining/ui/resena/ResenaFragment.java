package es.unex.navdrawertraining.ui.resena;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;

import com.squareup.picasso.Picasso;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.R;
import es.unex.navdrawertraining.databinding.FragmentResenaBinding;
import es.unex.navdrawertraining.modelodatos.Comentario;
import es.unex.navdrawertraining.modelodatos.Valoracion;
import es.unex.navdrawertraining.roomdb.CeresLimitDatabase;


public class ResenaFragment extends Fragment {

    private FragmentResenaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentResenaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Imagenes de background y perfil
        Picasso.get().load(getArguments().getString("fotoPerfil")).into(binding.ivLogoLocal);
        Picasso.get().load(getArguments().getString("fotoBackground")).into(binding.ivBackgroundLocal);
        //Nombre del local
        binding.tvNombreLocal.setText(getArguments().getString("nombre"));

        //Gestion del ratingbar
        RatingBar ratingBar = binding.ratingbarLocal;
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating == 0.0f) {
                    ratingBar.setRating(0.5f);
                }
            }
        });

        AppExecutors.getInstance().diskIO().execute(new ValoracionDiskLoaderRunnable(new OnValoracionLoadedListener() {
            @Override
            public void onValoracionLoaded(Valoracion valoracion) {
                if (valoracion != null) {
                    ratingBar.setRating(valoracion.getPuntuacion());
                }
            }
        }, getContext(), getArguments().getLong("id")));

        EditText etComentario = binding.etComentario;
        AppExecutors.getInstance().diskIO().execute(new ComentariosDiskLoaderRunnable(new OnComentariosLoadedListener() {
            @Override
            public void onComentariosLoaded(Comentario comentario) {
                if(comentario != null) {
                    etComentario.setText(comentario.getCadena_comentario());
                }
            }
        }, getContext(), getArguments().getLong("id")));

        binding.bConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        Valoracion valoracion = CeresLimitDatabase.getInstance(getContext()).getDaoValoracion().get(getArguments().getLong("id"));
                        if (valoracion == null && ratingBar.getRating() > 0.0f) { //Por defecto, el minimo que se puede votar es 0.5, si es 0, significa que no se ha votado
                            valoracion = new Valoracion(getArguments().getLong("id"), ratingBar.getRating());
                            CeresLimitDatabase.getInstance(getContext()).getDaoValoracion().insert(valoracion);
                        } else {
                            if (ratingBar.getRating() > 0.0f) {
                                valoracion.setPuntuacion(ratingBar.getRating());
                                CeresLimitDatabase.getInstance(getContext()).getDaoValoracion().update(valoracion);
                            }
                        }
                        Comentario comentario = CeresLimitDatabase.getInstance(getContext()).getDaoComentario().get(getArguments().getLong("id"));
                        if (!etComentario.getText().toString().isEmpty()){
                            if (comentario == null) {
                                comentario = new Comentario(getArguments().getLong("id"), etComentario.getText().toString());
                                CeresLimitDatabase.getInstance(getContext()).getDaoComentario().insert(comentario);
                            } else {
                                comentario.setCadena_comentario(etComentario.getText().toString());
                                CeresLimitDatabase.getInstance(getContext()).getDaoComentario().update(comentario);
                            }
                        }
                    }
                });
                getActivity().onBackPressed();
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