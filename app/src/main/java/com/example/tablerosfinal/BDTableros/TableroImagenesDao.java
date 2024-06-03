package com.example.tablerosfinal.BDTableros;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.tablerosfinal.BDImagenes.Imagenes;
import java.util.List;

@Dao
public interface TableroImagenesDao {

    // Inserta una nueva relación entre un tablero y una imagen
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Tablero_imagenes tableroImagen);

    // Obtener todas las imágenes asociadas con un tablero específico
    @Query("SELECT * FROM imagenes WHERE idImagenes IN (SELECT idImagen FROM tablero_imagenes WHERE idTablero = :idTablero)")
    List<Imagenes> getImagenesForTablero(int idTablero);

    @Insert
    void insertTableroImagen(Tablero_imagenes tableroImagen);

    @Query("SELECT EXISTS(SELECT 1 FROM tablero_imagenes WHERE idTablero = :idTablero AND idImagen = :idImagen)")
    boolean isImageOnTablero(int idTablero, int idImagen);

}
