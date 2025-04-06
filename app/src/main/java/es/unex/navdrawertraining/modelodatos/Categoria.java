package es.unex.navdrawertraining.modelodatos;

import android.content.Intent;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName="Categoria")
public class Categoria {

    @Ignore
    public static final String ITEM_SEP = System.getProperty("line.separator");

    @Ignore
    public final static String ID_CATEGORIA = "id_categoria";
    @Ignore
    public final static String NOMBRE_CATEGORIA = "nombre_categoria";
    @Ignore
    public final static String ICONO_CATEGORIA = "icono_categoria";
    @Ignore
    public final static String COLOR_CATEGORIA = "color_categoria";

    @PrimaryKey(autoGenerate = true)
    private long id_categoria;
    @ColumnInfo(name = "nombre_categoria")
    private String nombre_categoria;
    @ColumnInfo(name = "icono_categoria")
    private String icono_categoria;
    @ColumnInfo(name = "color_categoria")
    private String color_categoria;

    @Ignore
    public Categoria() {
        this.nombre_categoria = null;
        this.icono_categoria = null;
        this.color_categoria = null;
    }

    public Categoria(long id_categoria, String nombre_categoria, String icono_categoria, String color_categoria) {
        this.id_categoria =id_categoria;
        this.nombre_categoria = nombre_categoria;
        this.icono_categoria = icono_categoria;
        this.color_categoria = color_categoria;
    }


    public long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombre_categoria() {
        return nombre_categoria;
    }

    public void setNombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
    }

    public String getIcono_categoria() {
        return icono_categoria;
    }

    public void setIcono_categoria(String icono_categoria) {
        this.icono_categoria = icono_categoria;
    }

    public String getColor_categoria() {
        return color_categoria;
    }

    public void setColor_categoria(String color_categoria) {
        this.color_categoria = color_categoria;
    }



}
