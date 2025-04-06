package es.unex.navdrawertraining.ui.reservas;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.modelodatos.Local;
import es.unex.navdrawertraining.modelodatos.Reserva;
import es.unex.navdrawertraining.repoexterno.GithubService;
import es.unex.navdrawertraining.roomdb.CeresLimitDatabase;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocalesReservasLoaderRunnable implements Runnable{
    private final OnLocalesReservasLoadedListener mOnLocalesReservasLoadedListener;
    private final Context context;

    public LocalesReservasLoaderRunnable(OnLocalesReservasLoadedListener mOnLocalesReservasLoadedListener, Context context) {
        this.mOnLocalesReservasLoadedListener = mOnLocalesReservasLoadedListener;
        this.context = context;
    }

    @Override
    public void run() {
        List<Reserva> reservas = CeresLimitDatabase.getInstance(context).getDaoReserva().getAll();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://raw.githubusercontent.com/").addConverterFactory(GsonConverterFactory.create()).build();
        GithubService service = retrofit.create(GithubService.class);
        try {
            List<Local>  locales = service.listLocales().execute().body();
            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                @Override
                public void run() {
                    mOnLocalesReservasLoadedListener.onLocalesReservasLoaded(locales, reservas);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
