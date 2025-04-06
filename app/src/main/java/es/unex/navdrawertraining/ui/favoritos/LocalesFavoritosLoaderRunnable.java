package es.unex.navdrawertraining.ui.favoritos;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.modelodatos.Favorito;
import es.unex.navdrawertraining.modelodatos.Local;
import es.unex.navdrawertraining.repoexterno.GithubService;
import es.unex.navdrawertraining.roomdb.CeresLimitDatabase;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocalesFavoritosLoaderRunnable implements Runnable{
    private final OnLocalesFavoritosLoadedListener mOnLocalesFavoritosLoadedListener;
    private final Context context;

    public LocalesFavoritosLoaderRunnable(OnLocalesFavoritosLoadedListener mOnLocalesFavoritosLoadedListener, Context context) {
        this.mOnLocalesFavoritosLoadedListener = mOnLocalesFavoritosLoadedListener;
        this.context = context;
    }

    @Override
    public void run() {
        List<Favorito> favoritos = CeresLimitDatabase.getInstance(context).getDaoFavorito().getAll();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://raw.githubusercontent.com/").addConverterFactory(GsonConverterFactory.create()).build();
        GithubService service = retrofit.create(GithubService.class);
        try {
            List<Local>  locales = service.listLocales().execute().body();
            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                @Override
                public void run() {
                    mOnLocalesFavoritosLoadedListener.onLocalesFavoritosLoaded(locales, favoritos);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
