package com.example.tablerosfinal.Inicio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.tablerosfinal.BD.AppDatabase;
import com.example.tablerosfinal.BD.DatabaseManager;
import com.example.tablerosfinal.BD.Usuarios;
import com.example.tablerosfinal.Config.SettingsActivity;
import com.example.tablerosfinal.Constante;
import com.example.tablerosfinal.Movimientos.ActivityPrueba;
import com.example.tablerosfinal.R;
import com.example.tablerosfinal.Tableros.ActivityTableros;
import com.example.tablerosfinal.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InicioActivity extends AppCompatActivity {

    private int[] imageResources = {R.drawable.ini_pic, R.drawable.monjas_inicio, R.drawable.inicio_hombres};
    private String[] textResources = {"Tu galería de arte personal", "Explora. Guarda. Inspírate", "Descubre el arte que amas"};
    private ViewPager2 viewPager;
    private RecursosImageAdapter imageAdapter;
    private TextView textViewSubTitle;

    private TextView textViewGreeting;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        viewPager = findViewById(R.id.viewPager);
        imageAdapter = new RecursosImageAdapter(this, imageResources);
        viewPager.setAdapter(imageAdapter);

        textViewSubTitle = findViewById(R.id.textViewSubTitle);

        // Aquí se realiza el cambio de página en el ViewPager
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Actualiza el texto según la posición de la página seleccionada
                textViewSubTitle.setText(textResources[position]);
            }
        });

        // BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    if (!(InicioActivity.this instanceof InicioActivity)) {
                        startActivity(new Intent(InicioActivity.this, InicioActivity.class));
                        finish();
                    }
                    return true;
                } else if (item.getItemId() == R.id.arte) {
                    startActivity(new Intent(InicioActivity.this, ActivityPrueba.class));
                    return true;
                } else if (item.getItemId() == R.id.tab) {
                    startActivity(new Intent(InicioActivity.this, ActivityTableros.class));
                } else if (item.getItemId() == R.id.config) {
                    // logout();
                    startActivity(new Intent(InicioActivity.this, SettingsActivity.class));
                }
                return false;
            }
        });

        // Saludo
        // Saludo
        textViewGreeting = findViewById(R.id.textViewGreeting);
        appDatabase = DatabaseManager.getInstance(this);

// Obtener el usuario activo
        Usuarios userSaved = Constante.usuario;

        if (userSaved != null) {
            // Si se encuentra el usuario, actualizamos el TextView con el saludo y el nombre del usuario
            String usernameSaved = userSaved.getUsername();
            String saludoSaved = obtenerSaludo();
            String mensajeSaved = saludoSaved + ", " + usernameSaved;
            textViewGreeting.setText(mensajeSaved);
        } else {
            // Si no se encuentra un usuario activo, mostramos un saludo genérico
            textViewGreeting.setText(obtenerSaludo() + ", Usuario");
        }
    }

        // Método para obtener el saludo según la hora del día
    private String obtenerSaludo() {
        int hora = obtenerHoraDelDia();
        if (hora >= 0 && hora < 12) {
            return "Buenos días";
        } else if (hora >= 12 && hora < 18) {
            return "Buenas tardes";
        } else {
            return "Buenas noches";
        }
    }

    // Método para obtener la hora del día
    private int obtenerHoraDelDia() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH", Locale.getDefault());
        String horaActual = dateFormat.format(new Date());
        return Integer.parseInt(horaActual);
    }

    private void logout() {
        // Limpiar las credenciales guardadas
        SharedPreferences preferences = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();  // Limpia todas las preferencias
        editor.apply();

        // Redirigir al usuario a la pantalla de inicio de sesión
        Intent intent = new Intent(InicioActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
