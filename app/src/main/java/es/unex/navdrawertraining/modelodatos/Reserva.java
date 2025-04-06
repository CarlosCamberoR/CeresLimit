package es.unex.navdrawertraining.modelodatos;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.unex.navdrawertraining.roomdb.DateConverter;

@Entity(tableName = "reserva", primaryKeys = {"id_reserva", "fecha_hora_reserva"})
public class Reserva {

    @Ignore
    public final static String ID_RESERVA = "id_reserva";

    @Ignore
    public final static String FECHA_HORA_RESERVA = "fecha_hora_reserva";

    @Ignore
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.US);

    @NonNull
    private long id_reserva;


    @TypeConverters(DateConverter.class)
    @NonNull
    private Date fecha_hora_reserva;

    @Ignore
    Reserva (Date fecha_hora_reserva) {
        this.fecha_hora_reserva = fecha_hora_reserva;
    }
    @Ignore
    public Reserva(long id_reserva, String fecha_hora_reserva) {
        this.id_reserva = id_reserva;
        try {
            this.fecha_hora_reserva = Reserva.FORMAT.parse(fecha_hora_reserva);
        }catch ( ParseException e) {
            this.fecha_hora_reserva = new Date();
        }
    }
    @Ignore
    public Reserva (Intent intent) {
        this.id_reserva = intent.getLongExtra(Reserva.ID_RESERVA, 0);
        try {
            this.fecha_hora_reserva = Reserva.FORMAT.parse(intent.getStringExtra(Reserva.FECHA_HORA_RESERVA));
        } catch (ParseException e) {
            fecha_hora_reserva = new Date();
        }

    }

    public Reserva(long id_reserva, Date fecha_hora_reserva) {
        this.id_reserva = id_reserva;
        this.fecha_hora_reserva = fecha_hora_reserva;
    }

    public long getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(long id_reserva) {
        this.id_reserva = id_reserva;
    }

    public Date getFecha_hora_reserva() {
        return fecha_hora_reserva;
    }

    public void setFecha_hora_reserva(Date fecha_hora_reserva) {
        this.fecha_hora_reserva = fecha_hora_reserva;
    }

    public static void packageIntent (Intent intent, String fecha_hora_reserva) {
        intent.putExtra(Reserva.FECHA_HORA_RESERVA, fecha_hora_reserva);
    }
}
