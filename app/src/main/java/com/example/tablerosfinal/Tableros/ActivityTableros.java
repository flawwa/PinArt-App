package com.example.tablerosfinal.Tableros;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tablerosfinal.BD.DatabaseManager;
import com.example.tablerosfinal.BDTableros.Tableros;
import com.example.tablerosfinal.Config.SettingsActivity;
import com.example.tablerosfinal.Constante;
import com.example.tablerosfinal.Inicio.InicioActivity;
import com.example.tablerosfinal.Movimientos.ActivityArtDeco;
import com.example.tablerosfinal.Movimientos.ActivityPrueba;
import com.example.tablerosfinal.R;
import com.example.tablerosfinal.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;


/*Gestiona la visualización de los tableros, pudiendo verlos, crearlos o eliminarlos*/
public class ActivityTableros extends AppCompatActivity implements CrearTableroDialogFragment.OnBoardCreatedListener {
    private RecyclerView tablerosRecyclerView;
    private TableroAdapter boardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tableros_main);

        tablerosRecyclerView = findViewById(R.id.tablerosRecyclerView);
        int numeroColumnas = 2;
        tablerosRecyclerView.setLayoutManager(new GridLayoutManager(this, numeroColumnas));

        int userId = Constante.usuario.getId();


        boardAdapter = new TableroAdapter(DatabaseManager.getInstance(this).tablerosDao().getTablerosByUserId(userId), this, this::openTableroImgsAgregadas, -1);
        tablerosRecyclerView.setAdapter(boardAdapter);

        findViewById(R.id.createBoardButton).setOnClickListener(v ->
                CrearTableroDialogFragment.newInstance(this).show(getSupportFragmentManager(), "CrearTableroDialogFragment")
        );

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    startActivity(new Intent(ActivityTableros.this, InicioActivity.class));
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.arte) {
                    startActivity(new Intent(ActivityTableros.this, ActivityPrueba.class));
                    return true;
                } else if (item.getItemId() == R.id.tab) {
                    startActivity(new Intent(ActivityTableros.this, ActivityTableros.class));
                } else if (item.getItemId() == R.id.config) {
                    startActivity(new Intent(ActivityTableros.this, SettingsActivity.class));
                }
                return false;
            }
        });
    }

    public void showDeleteDialog(int tableroId) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Tablero")
                .setMessage("¿Estás seguro de que quieres eliminar este tablero?")
                .setPositiveButton("Sí", (dialog, which) -> deleteTablero(tableroId))
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteTablero(int tableroId) {
        DatabaseManager.getInstance(this).tablerosDao().deleteById(tableroId);
        boardAdapter.removeTableroById(tableroId);
        boardAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBoardCreated(String boardName) {
        Tableros tablero = new Tableros(boardName, Constante.usuario.getId());
        DatabaseManager.getInstance(this).tablerosDao().insert(tablero);
        boardAdapter.getBoardNames().add(tablero);
        boardAdapter.notifyDataSetChanged();
    }

    private void openTableroImgsAgregadas(int tableroId, int unusedImageId) {
        Intent intent = new Intent(this, ActivityTableroImgsAgregadas.class);
        intent.putExtra("tablero_id", tableroId);
        // intent.putExtra("tablero_name", tableroName);
        startActivity(intent);
    }
}