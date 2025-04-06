package es.unex.navdrawertraining.ui.categorias;

import android.content.Context;

import java.util.List;

import es.unex.navdrawertraining.AppExecutors;
import es.unex.navdrawertraining.modelodatos.Categoria;
import es.unex.navdrawertraining.roomdb.CeresLimitDatabase;

public class CategoriasDiskLoaderRunnable implements Runnable{
    private final OnCategoriasLoadedListener onCategoriasLoadedListener;
    private final Context context;
    public CategoriasDiskLoaderRunnable(OnCategoriasLoadedListener onCategoriasLoadedListener, Context context) {
        this.onCategoriasLoadedListener = onCategoriasLoadedListener;
        this.context = context;
    }

    @Override
    public void run() {
        List<Categoria> categorias = CeresLimitDatabase.getInstance(context).getDaoCategoria().getAll();
        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                onCategoriasLoadedListener.onCategoriasLoaded(categorias);
            }
        });
    }
}
