package es.unex.navdrawertraining.repoexterno;

import java.io.IOException;
import java.util.List;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.modelodatos.Local;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocalesNetworkLoaderRunnable implements Runnable{
     private final OnLocalesLoaderListener mOnLocalesLoaderListener;

    public LocalesNetworkLoaderRunnable(OnLocalesLoaderListener mOnLocalesLoaderListener) {
        this.mOnLocalesLoaderListener = mOnLocalesLoaderListener;
    }

    @Override
    public void run() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://raw.githubusercontent.com/").addConverterFactory(GsonConverterFactory.create()).build();
        GithubService service = retrofit.create(GithubService.class);
        try {
            List<Local> locales = service.listLocales().execute().body();
            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                @Override
                public void run() {
                    mOnLocalesLoaderListener.onLocalesLoaded(locales);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
