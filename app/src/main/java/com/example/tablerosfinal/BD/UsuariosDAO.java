package com.example.tablerosfinal.BD;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UsuariosDAO {
    @Insert
    void insert(Usuarios user);

    @Query("SELECT * FROM usuarios WHERE username = :username")
    Usuarios getUserByUsername(String username);

    @Query("SELECT * FROM usuarios WHERE username = :username AND passwordHash = :password")
    Usuarios login(String username, String password);

    @Query("SELECT * FROM usuarios LIMIT 1") // Esto asume que solo hay un usuario
    Usuarios getUser(); // Método para obtener el usuario activo

    @Query("SELECT * FROM usuarios WHERE id = :userId")
    Usuarios getUserById(int userId);

    @Delete
    void delete(Usuarios user);
}