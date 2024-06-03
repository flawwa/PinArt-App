package com.example.tablerosfinal.BDImagenes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface ImagenesDAO {

    @Insert
    void insert(Imagenes imagen);

    @Update
    void update(Imagenes imagen);

    @Delete
    void delete(Imagenes imagen);

    @Query("SELECT * FROM imagenes")
    List<Imagenes> getAllImagenes();

    @Query("SELECT * FROM imagenes WHERE idImagenes = :id")
    Imagenes getImagenById(int id);

    @Query("SELECT * FROM imagenes WHERE movimiento = :movimiento")
    List<Imagenes> getAllImagenesMovimientos(String movimiento);
}

