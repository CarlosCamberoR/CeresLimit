package es.unex.navdrawertraining.modelodatos;

import android.content.Intent;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "comentario")
public class Comentario {
    @Ignore
    public final static String ITEM_SEP = System.getProperty("line.separator");

    @Ignore
    public final static String ID_COMENTARIO = "id_comentario";

    @Ignore
    public final static String CADENA_COMENTARIO = "cadena_comentario";

    @PrimaryKey(autoGenerate = true)
    private long id_comentario;

    @ColumnInfo(name = "cadena_comentario")
    private String cadena_comentario;

    public Comentario(long id_comentario, String cadena_comentario) {
        this.id_comentario= id_comentario;
        this.cadena_comentario = cadena_comentario;
    }

    @Ignore
    public Comentario(String cadena_comentario) {
        this.cadena_comentario = cadena_comentario;
    }

    @Ignore
    public Comentario(Intent intent) {
        id_comentario=intent.getLongExtra(Comentario.ID_COMENTARIO,0);
        cadena_comentario= intent.getStringExtra(CADENA_COMENTARIO);
    }

    public long getId_comentario() {
        return id_comentario;
    }

    public void setId_comentario(long id_comentario) {
        this.id_comentario = id_comentario;
    }

    public String getCadena_comentario() {
        return cadena_comentario;
    }

    public void setCadena_comentario(String cadena_comentario) {
        this.cadena_comentario = cadena_comentario;
    }

    public static void packageIntent(Intent intent, String cadenaComentario){
        intent.putExtra(Comentario.CADENA_COMENTARIO, cadenaComentario);
    }

    public String toString() {
        return id_comentario + ITEM_SEP + cadena_comentario;
    }

    public String toLog() {
        return "ID: " + id_comentario + ITEM_SEP + "Comentario: " + cadena_comentario;
    }
}
