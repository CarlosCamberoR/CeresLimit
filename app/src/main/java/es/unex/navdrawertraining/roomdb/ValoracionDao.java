package es.unex.navdrawertraining.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.navdrawertraining.modelodatos.Valoracion;


@Dao
public interface ValoracionDao {
    @Query("SELECT * FROM Valoracion")
    public List<Valoracion> getAll();

    @Query("SELECT * FROM Valoracion WHERE id_valoracion = :id")
    public Valoracion get(long id);

    @Insert
    public long insert(Valoracion valoracion);

    @Query("DELETE FROM Valoracion WHERE id_valoracion = :id")
    public void deleteAll(long id);

    @Update
    public int update(Valoracion valoracion);
}
