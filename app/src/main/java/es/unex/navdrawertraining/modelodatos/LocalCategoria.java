package es.unex.navdrawertraining.modelodatos;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "localCategoria", primaryKeys = {"id_local", "id_categoria" })
public class LocalCategoria {

    @Ignore
    public final static String ITEM_SEP = System.getProperty("line.separator");

    @Ignore
    public final static String ID_CATEGORIA = "id_categoria";

    @Ignore
    public final static String ID_LOCAL = "id_local";


    @NonNull
    private long id_categoria;
    @NonNull
    private long id_local;



    public  LocalCategoria (long id_categoria, long id_local) {
        this.id_categoria = id_categoria;
        this.id_local = id_local;
    }

    @Ignore
    public LocalCategoria (Intent intent) {
        this.id_categoria = intent.getLongExtra(LocalCategoria.ID_CATEGORIA, 0);
        this.id_local = intent.getLongExtra(LocalCategoria.ID_LOCAL, 0);
    }

    public static void packageIntent (Intent intent, long id_categoria, long id_local) {
        intent.putExtra(LocalCategoria.ID_CATEGORIA, id_categoria);
        intent.putExtra(LocalCategoria.ID_LOCAL, id_local);
    }
    public long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public long getId_local() {
        return id_local;
    }

    public void setId_local(long id_local) {
        this.id_local = id_local;
    }
}
