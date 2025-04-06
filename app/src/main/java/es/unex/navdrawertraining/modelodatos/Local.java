package es.unex.navdrawertraining.modelodatos;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Local{

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("coordenadas")
    @Expose
    private List<Double> coordenadas = null;
    @SerializedName("valoracionGlobal")
    @Expose
    private float valoracionGlobal;
    @SerializedName("foto_perfil")
    @Expose
    private String fotoPerfil;
    @SerializedName("foto_background")
    @Expose
    private String fotoBackground;
    @SerializedName("aforo_total")
    @Expose
    private Integer aforoTotal;
    @SerializedName("ocupacion_actual")
    @Expose
    private Integer ocupacionActual;
    @SerializedName("etiquetas")
    @Expose
    private List<String> etiquetas = null;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Double> getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(List<Double> coordenadas) {
        this.coordenadas = coordenadas;
    }

    public float getValoracionGlobal() {
        return valoracionGlobal;
    }

    public void setValoracionGlobal(float valoracionGlobal) {
        this.valoracionGlobal = valoracionGlobal;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getFotoBackground() {
        return fotoBackground;
    }

    public void setFotoBackground(String fotoBackground) {
        this.fotoBackground = fotoBackground;
    }

    public Integer getAforoTotal() {
        return aforoTotal;
    }

    public void setAforoTotal(Integer aforoTotal) {
        this.aforoTotal = aforoTotal;
    }

    public Integer getOcupacionActual() {
        return ocupacionActual;
    }

    public void setOcupacionActual(Integer ocupacionActual) {
        this.ocupacionActual = ocupacionActual;
    }

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

}