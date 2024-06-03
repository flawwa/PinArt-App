package com.example.tablerosfinal.BD;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.tablerosfinal.BDImagenes.Imagenes;
import com.example.tablerosfinal.BDImagenes.ImagenesDAO;
import com.example.tablerosfinal.BDTableros.TableroImagenesDao;
import com.example.tablerosfinal.BDTableros.Tablero_imagenes;
import com.example.tablerosfinal.BDTableros.Tableros;
import com.example.tablerosfinal.BDTableros.TablerosDao;

@Database(entities = {Usuarios.class, Imagenes.class, Tableros.class, Tablero_imagenes.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsuariosDAO usuariosDao();

    public abstract ImagenesDAO imagenesDAO();
    public abstract TablerosDao tablerosDao();
    public abstract TableroImagenesDao tableroImagenesDao();


}
