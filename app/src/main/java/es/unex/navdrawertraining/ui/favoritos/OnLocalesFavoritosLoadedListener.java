package es.unex.navdrawertraining.ui.favoritos;

import java.util.List;

import es.unex.navdrawertraining.modelodatos.Favorito;
import es.unex.navdrawertraining.modelodatos.Local;

public interface OnLocalesFavoritosLoadedListener {
    public void onLocalesFavoritosLoaded(List<Local> locales, List<Favorito> favoritos);
}
