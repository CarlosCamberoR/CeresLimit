package es.unex.navdrawertraining.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.unex.navdrawertraining.modelodatos.Categoria;
import es.unex.navdrawertraining.modelodatos.Comentario;
import es.unex.navdrawertraining.modelodatos.Favorito;
import es.unex.navdrawertraining.modelodatos.LocalCategoria;
import es.unex.navdrawertraining.modelodatos.Reserva;
import es.unex.navdrawertraining.modelodatos.Valoracion;


@Database(entities={Categoria.class, Reserva.class, Comentario.class, Valoracion.class, Favorito.class, LocalCategoria.class},version=1)
public abstract class CeresLimitDatabase extends RoomDatabase {
    private static CeresLimitDatabase instance;

    public static CeresLimitDatabase getInstance(Context context) {
        if(instance==null)
            instance= Room.databaseBuilder(context,CeresLimitDatabase.class,"CeresLimit.db").build();
        return instance;
    }

    public abstract CategoriaDao getDaoCategoria();

    public abstract ReservaDao getDaoReserva();

    public abstract ComentarioDao getDaoComentario();

    public abstract ValoracionDao getDaoValoracion();

    public abstract FavoritoDao getDaoFavorito();

    public abstract  LocalCategoriaDao getDaoLocalCategoria();
}
