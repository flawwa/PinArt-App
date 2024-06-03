package com.example.tablerosfinal.BDTableros;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface TablerosDao {
    @Insert
    void insert(Tableros tablero);

    @Query("SELECT * FROM tableros WHERE idUsuario = :userId")
    List<Tableros> getTablerosByUserId(int userId);

    @Query("SELECT * FROM tableros")
    List<Tableros> getAllTableros();

    @Query("DELETE FROM tableros WHERE id = :id")
    void deleteById(int id);


}

