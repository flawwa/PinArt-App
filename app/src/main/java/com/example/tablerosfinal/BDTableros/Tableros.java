package com.example.tablerosfinal.BDTableros;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import com.example.tablerosfinal.BD.Usuarios;

@Entity(tableName = "tableros",
        foreignKeys = @ForeignKey(
                entity = Usuarios.class,
                parentColumns = "id",
                childColumns = "idUsuario",
                onDelete = ForeignKey.CASCADE
        ))
public class Tableros {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nombre;
    private int idUsuario; // Aquí establezco la relación con el usuario

    // Constructor
    public Tableros(String nombre, int idUsuario) {
        this.nombre = nombre;
        this.idUsuario = idUsuario;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
