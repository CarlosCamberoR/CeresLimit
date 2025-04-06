package es.unex.navdrawertraining.ui.categorias;

import java.util.List;

import es.unex.navdrawertraining.modelodatos.Categoria;

public interface OnCategoriasLoadedListener {
    public void onCategoriasLoaded (List<Categoria> categorias);
}
