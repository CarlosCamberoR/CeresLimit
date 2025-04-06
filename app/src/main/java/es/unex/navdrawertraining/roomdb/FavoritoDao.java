package es.unex.navdrawertraining.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.navdrawertraining.modelodatos.Favorito;

@Dao
public interface FavoritoDao {
    @Query("SELECT * FROM Favorito")
    public List<Favorito> getAll();

    @Query("SELECT * FROM Favorito WHERE id_favorito= :id")
    public Favorito get(long id);

    @Insert
    public long insert(Favorito favorito);

    @Query("DELETE FROM Favorito WHERE id_favorito = :id")
    public void delete(long id);

    @Update
    public int update(Favorito favorito);


}
