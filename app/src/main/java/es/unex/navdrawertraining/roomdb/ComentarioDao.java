package es.unex.navdrawertraining.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.navdrawertraining.modelodatos.Comentario;

@Dao
public interface ComentarioDao {
    @Query("SELECT * FROM Comentario")
    public List<Comentario> getAll();

    @Query("SELECT * FROM comentario WHERE id_comentario = :id")
    public Comentario get(long id);
    @Insert
    public long insert(Comentario comentario);

    @Query("DELETE FROM Comentario WHERE id_comentario = :id")
    public void delete(long id);

    @Update
    public int update(Comentario comentario);
}
