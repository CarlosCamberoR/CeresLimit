package es.unex.navdrawertraining.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.navdrawertraining.modelodatos.Categoria;

@Dao
public interface CategoriaDao {
    @Query("SELECT * FROM Categoria")
    public List<Categoria> getAll();

    @Insert
    public long insert(Categoria categoria);

    @Query("DELETE FROM Categoria WHERE id_categoria = :id")
    public void delete(long id);

    @Update
    public int update(Categoria categoria);
}
