package es.unex.navdrawertraining.modelodatos;

import android.content.Intent;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName="Favorito")
public class Favorito {

    @Ignore
    public final static String ID_FAVORITO = "id_favorito";

    @PrimaryKey(autoGenerate = false)
    private long id_favorito;


    public Favorito(long id_favorito) {
        this.id_favorito = id_favorito;
    }

    @Ignore
    public Favorito(Intent intent) {
        id_favorito =intent.getLongExtra(Favorito.ID_FAVORITO,0);
    }

    public long getId_favorito() {
        return id_favorito;
    }

    public void setId_favorito(long id_favorito) {
        this.id_favorito = id_favorito;
    }

    public static void packageIntent (Intent intent, long id_favorito) {
        intent.putExtra(Favorito.ID_FAVORITO, id_favorito);
    }

    public String toString() {
        return id_favorito +"";
    }

    public String toLog() {
        return "ID: " + id_favorito;
    }

}
