package com.example.tablerosfinal.Tableros;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.tablerosfinal.BDImagenes.ImagenAdapter;
import com.example.tablerosfinal.BDImagenes.Imagenes;
import com.example.tablerosfinal.Inicio.InicioActivity;
import com.example.tablerosfinal.Movimientos.ActivityPrueba;
import com.example.tablerosfinal.R;
import com.example.tablerosfinal.BD.DatabaseManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;

/*Muestra la colección de imagenes que se asocian a un tablero especifico*/

public class ActivityTableroImgsAgregadas extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView emptyTextView;
    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagenesagregadas_tablero);

        recyclerView = findViewById(R.id.imagesRecyclerView);
        titleTextView = findViewById(R.id.titleTextView);
        emptyTextView = findViewById(R.id.emptyTextView);

        // Establece el StaggeredGridLayoutManager con dos columnas y orientación vertical
        int spanCount = 2;
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS); // Asegura que los gaps se manejen adecuadamente
        recyclerView.setLayoutManager(layoutManager);


        int tableroId = getIntent().getIntExtra("tablero_id", -1);
        String tableroName = getIntent().getStringExtra("tablero_name");

        if (tableroName != null) {
            titleTextView.setText(tableroName);
        }

        if (tableroId != -1) {
            List<Imagenes> imagenes = DatabaseManager.getInstance(this).tableroImagenesDao().getImagenesForTablero(tableroId);
            if (imagenes.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                findViewById(R.id.emptyContainer).setVisibility(View.VISIBLE);
            } else {
                recyclerView.setAdapter(new ImagenAdapter(imagenes, false));
                recyclerView.setVisibility(View.VISIBLE);
                findViewById(R.id.emptyContainer).setVisibility(View.GONE);
            }
        }


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    startActivity(new Intent(ActivityTableroImgsAgregadas.this, InicioActivity.class));
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.arte) {
                    startActivity(new Intent(ActivityTableroImgsAgregadas.this, ActivityPrueba.class));
                    return true;
                } else if (item.getItemId() == R.id.tab) {
                    startActivity(new Intent(ActivityTableroImgsAgregadas.this, ActivityTableros.class));
                }
                return false;
            }
        });
    }
}