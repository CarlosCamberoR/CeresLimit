package es.unex.navdrawertraining.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.navdrawertraining.modelodatos.LocalCategoria;

@Dao
public interface LocalCategoriaDao {
    @Query("SELECT * FROM LocalCategoria")
    public List<LocalCategoria> getAll();

    @Query("SELECT * FROM LocalCategoria WHERE id_categoria= :id_categoria AND id_local = :id_local")
    public LocalCategoria get (long id_categoria, long id_local);

    @Query("SELECT * FROM LocalCategoria WHERE id_categoria= :id_categoria")
    public List<LocalCategoria> getCategoria (long id_categoria);

    @Insert
    public long insert(LocalCategoria localCategoria);

    @Query("DELETE FROM LocalCategoria WHERE  id_categoria= :id_categoria AND id_local = :id_local")
    public void delete(long id_categoria, long id_local);

    @Update
    public int update(LocalCategoria localCategoria);
}
