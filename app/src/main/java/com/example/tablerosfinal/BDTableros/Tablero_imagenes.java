package com.example.tablerosfinal.BDTableros;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.tablerosfinal.BDImagenes.Imagenes;

@Entity(tableName = "tablero_imagenes",
        primaryKeys = {"idTablero", "idImagen"},
        foreignKeys = {
                @ForeignKey(
                        entity = Tableros.class,
                        parentColumns = "id",
                        childColumns = "idTablero",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Imagenes.class,
                        parentColumns = "idImagenes",
                        childColumns = "idImagen",
                        onDelete = ForeignKey.CASCADE
                )
        })
public class Tablero_imagenes {
    private int idTablero; // fk a tableros
    private int idImagen; // fk a Imagenes

    // Constructor
    public Tablero_imagenes(int idTablero, int idImagen) {
        this.idTablero = idTablero;
        this.idImagen = idImagen;
    }

    // Getters y setters
    public int getIdTablero() {
        return idTablero;
    }

    public void setIdTablero(int idTablero) {
        this.idTablero = idTablero;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }
}

