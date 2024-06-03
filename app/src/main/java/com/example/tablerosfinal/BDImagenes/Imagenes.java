package com.example.tablerosfinal.BDImagenes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "imagenes")
public class Imagenes {
    @PrimaryKey(autoGenerate = true)
    private int idImagenes;

    private String titulo;
    private String descripcion;
    // private String url;
    private int resourceId;

    private String movimiento;

    // Constructor
    public Imagenes(String titulo, String descripcion, int resourceId /*String url*/, String movimiento) {
        this.titulo = titulo;
        this.descripcion = descripcion;
       // this.url = url;
        this.resourceId = resourceId;
        this.movimiento = movimiento;
    }

    // Getters
    public int getIdImagenes() {
        return idImagenes;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getResourceId() {
        return resourceId;
    }

    /*public String getUrl() {
        return url;
    }*/

    public String getMovimiento() { return movimiento;}

    // Setters
    public void setIdImagenes(int idImagenes) {
        this.idImagenes = idImagenes;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public void setMovimiento(String movimiento) {this.movimiento = movimiento;}
}
