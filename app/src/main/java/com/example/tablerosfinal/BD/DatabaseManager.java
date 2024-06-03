package com.example.tablerosfinal.BD;

import android.content.Context;
import androidx.room.Room;

public class DatabaseManager {

    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase getInstance(Context context) {
        // Si la instancia aún no ha sido creada, constrúyela utilizando Room
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "PruebaTablerosFinal")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // Permitir consultas en el hilo principal (solo para fines de demostración)
                    .build();
        }
        // Devolver la instancia de la base de datos Room
        return INSTANCE;
    }
}
