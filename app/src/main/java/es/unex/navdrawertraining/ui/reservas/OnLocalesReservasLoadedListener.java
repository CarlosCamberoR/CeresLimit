package es.unex.navdrawertraining.ui.reservas;

import java.util.List;

import es.unex.navdrawertraining.modelodatos.Local;
import es.unex.navdrawertraining.modelodatos.Reserva;

public interface OnLocalesReservasLoadedListener {
    public void onLocalesReservasLoaded (List<Local> locales, List<Reserva> reservas);
}
