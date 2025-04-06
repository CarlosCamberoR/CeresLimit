package es.unex.navdrawertraining.ui.resena;

import android.content.Context;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.modelodatos.Valoracion;
import es.unex.navdrawertraining.roomdb.CeresLimitDatabase;

public class ValoracionDiskLoaderRunnable implements Runnable{
    private final OnValoracionLoadedListener onValoracionLoadedListener;
    private final Context context;
    private final long id_local;


    public ValoracionDiskLoaderRunnable(OnValoracionLoadedListener onValoracionLoadedListener, Context context, long id_local) {
        this.onValoracionLoadedListener = onValoracionLoadedListener;
        this.context = context;
        this.id_local = id_local;
    }

    @Override
    public void run() {
        Valoracion valoracion = CeresLimitDatabase.getInstance(context).getDaoValoracion().get(id_local);
        AppExecutors.getInstance().mainThread().execute(() -> onValoracionLoadedListener.onValoracionLoaded(valoracion));
    }
}
