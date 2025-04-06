package es.unex.navdrawertraining.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

import es.unex.navdrawertraining.modelodatos.Reserva;


@Dao
public interface ReservaDao {
    @Query("SELECT * FROM Reserva")
    public List<Reserva> getAll();

    @Query("SELECT * FROM Reserva WHERE id_reserva= :reserva_id AND fecha_hora_reserva= :fecha_hora")
    public Reserva get(long reserva_id, Long fecha_hora);
    @Insert
    public long insert(Reserva reserva);

    @Query("DELETE FROM Reserva WHERE id_reserva = :id AND fecha_hora_reserva = :fecha_hora_reserva")
    public void delete(long id, Long fecha_hora_reserva);

    @Update
    public int update(Reserva reserva);
}
