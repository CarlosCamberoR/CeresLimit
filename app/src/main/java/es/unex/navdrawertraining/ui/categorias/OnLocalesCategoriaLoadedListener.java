package es.unex.navdrawertraining.ui.categorias;

import java.util.List;

import es.unex.navdrawertraining.modelodatos.Local;
import es.unex.navdrawertraining.modelodatos.LocalCategoria;

public interface OnLocalesCategoriaLoadedListener {
    public void onLocalesCategoriaLoaded(List<LocalCategoria> localesCategorias, List<Local> locales);
}
