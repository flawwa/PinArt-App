/*package com.example.tablerosfinal.PruebaImagenesBD;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.example.tablerosfinal.BD.AppDatabase;
import com.example.tablerosfinal.BDImagenes.ImagenAdapter;
import com.example.tablerosfinal.BDImagenes.Imagenes;
import com.example.tablerosfinal.R;
import java.util.List;
import java.util.concurrent.Executors;

public class PruebaImagenes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImagenAdapter imagenAdapter;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_image_main);

        recyclerView = findViewById(R.id.recyclerViewMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtener la instancia de la base de datos
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "PruebaTablerosFinal").build();

        cargarImagenAuto();

    }

    private void cargarImagenAuto() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                // Carga de imagenes
                String imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTkeXomWfrI7xJps12gxdUF_Ts6lM5vuRoWb5W5HH66ZA&s";

                // Aquí puedo guardar imagen en la base de datos
                Imagenes imagen = new Imagenes("Título de la imagen PRUEBA", "Descripción de la imagen PRUEBA", imageUrl, "MovimientoPrueba");
                database.imagenesDAO().insert(imagen);

                // Actualizar el RecyclerView en el hilo principal
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        actualizarRecyclerView();
                    }
                });
            }
        });
    }

    private void actualizarRecyclerView() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<Imagenes> imagenesList = database.imagenesDAO().getAllImagenesMovimientos("MovimientoPrueba");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imagenAdapter = new ImagenAdapter(imagenesList);
                        recyclerView.setAdapter(imagenAdapter);
                    }
                });
            }
        });
    }
}*/