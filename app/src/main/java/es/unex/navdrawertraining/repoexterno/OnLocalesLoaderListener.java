package es.unex.navdrawertraining.repoexterno;

import java.util.List;

import es.unex.navdrawertraining.modelodatos.Local;

public interface OnLocalesLoaderListener {
    public void onLocalesLoaded (List<Local> locales);
}
