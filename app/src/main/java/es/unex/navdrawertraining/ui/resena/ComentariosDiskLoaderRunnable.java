package es.unex.navdrawertraining.ui.resena;

import android.content.Context;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.modelodatos.Comentario;
import es.unex.navdrawertraining.roomdb.CeresLimitDatabase;

public class ComentariosDiskLoaderRunnable implements Runnable{
    private final OnComentariosLoadedListener onComentariosLoadedListener;
    private final Context context;
    private final long id_local;


    public ComentariosDiskLoaderRunnable(OnComentariosLoadedListener onComentariosLoadedListener, Context context, long id_local) {
        this.onComentariosLoadedListener = onComentariosLoadedListener;
        this.context = context;
        this.id_local = id_local;
    }

    @Override
    public void run() {
        Comentario comentario = CeresLimitDatabase.getInstance(context).getDaoComentario().get(id_local);
        AppExecutors.getInstance().mainThread().execute(() -> onComentariosLoadedListener.onComentariosLoaded(comentario));

    }
}
