package es.unex.navdrawertraining.modelodatos;

import android.content.Intent;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName="Valoracion")
public class Valoracion {
    @Ignore
    public final static String ID_VALORACION = "id_valoracion";

    @Ignore
    public final static String PUNTUACION = "puntuacion";

    @PrimaryKey(autoGenerate = false)
    private long id_valoracion;

    @ColumnInfo(name = "puntuacion")
    private float puntuacion;

    public Valoracion(long id_valoracion, float puntuacion) {
        this.id_valoracion=id_valoracion;
        this.puntuacion=puntuacion;
    }

    @Ignore
    public Valoracion(Intent intent) {
        id_valoracion=intent.getLongExtra(Valoracion.ID_VALORACION,0);
        puntuacion=intent.getFloatExtra(Valoracion.PUNTUACION, 0);
    }

    public long getId_valoracion() {
        return id_valoracion;
    }

    public void setId_valoracion(long id_valoracion) {
        this.id_valoracion = id_valoracion;
    }

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String toString() {
        return id_valoracion +" "+ puntuacion;
    }

    public String toLog() {
        return "id_local: " + id_valoracion + "puntuacion: " + puntuacion;
    }

}
