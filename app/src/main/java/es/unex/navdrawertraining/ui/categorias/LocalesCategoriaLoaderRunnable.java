package es.unex.navdrawertraining.ui.categorias;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.modelodatos.Local;
import es.unex.navdrawertraining.modelodatos.LocalCategoria;
import es.unex.navdrawertraining.repoexterno.GithubService;
import es.unex.navdrawertraining.roomdb.CeresLimitDatabase;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocalesCategoriaLoaderRunnable implements Runnable{
    private final OnLocalesCategoriaLoadedListener onLocalesCategoriaLoadedListener;
    private final Context context;
    private final long id_categoria;

    public LocalesCategoriaLoaderRunnable(OnLocalesCategoriaLoadedListener onLocalesCategoriaLoadedListener, Context context, long id_categoria) {
        this.onLocalesCategoriaLoadedListener = onLocalesCategoriaLoadedListener;
        this.context = context;
        this.id_categoria = id_categoria;
    }

    @Override
    public void run() {
        List<LocalCategoria> localCategorias = CeresLimitDatabase.getInstance(context).getDaoLocalCategoria().getCategoria(id_categoria);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://raw.githubusercontent.com/").addConverterFactory(GsonConverterFactory.create()).build();
        GithubService service = retrofit.create(GithubService.class);
        try {
            List<Local> locales = service.listLocales().execute().body();
            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                @Override
                public void run() {
                    onLocalesCategoriaLoadedListener.onLocalesCategoriaLoaded(localCategorias, locales);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
