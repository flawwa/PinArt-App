package com.example.tablerosfinal.Config;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import com.example.tablerosfinal.BD.DatabaseManager;
import com.example.tablerosfinal.BD.Usuarios;
import com.example.tablerosfinal.Constante;
import com.example.tablerosfinal.R;
import com.example.tablerosfinal.login.LoginActivity;

public class SettingsActivity extends AppCompatActivity {

    private TextView userNameTextView;
    private Switch nightModeSwitch;
    private CardView logoutCard, deleteAccountCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        userNameTextView = findViewById(R.id.userNameTextView);
        nightModeSwitch = findViewById(R.id.nightModeSwitch);
        logoutCard = findViewById(R.id.logoutCard);
        deleteAccountCard = findViewById(R.id.deleteAccountCard);

        Usuarios currentUser = Constante.usuario;
        if (currentUser != null) {
            String greetingMessage = "¡Hola, " + currentUser.getUsername() + "! ¿Qué deseas hacer hoy?";
            userNameTextView.setText(greetingMessage);
        }

        // Set the initial state of the night mode switch
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isNightMode = preferences.getBoolean("nightMode", false);
        nightModeSwitch.setChecked(isNightMode);

        nightModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("nightMode", isChecked);
            editor.apply();

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        logoutCard.setOnClickListener(v -> showLogoutConfirmationDialog());
        deleteAccountCard.setOnClickListener(v -> showDeleteAccountConfirmationDialog());
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cerrar Sesión")
                .setMessage("¿Estás seguro de que deseas cerrar sesión?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void showDeleteAccountConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Cuenta")
                .setMessage("¿Estás seguro de que deseas eliminar tu cuenta? Esta acción no se puede deshacer.")
                .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAccount();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void logout() {
        SharedPreferences preferences = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        // Redirect to the login screen
        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void deleteAccount() {
        Usuarios currentUser = Constante.usuario;
        if (currentUser != null) {
            DatabaseManager.getInstance(this).usuariosDao().delete(currentUser);
            Toast.makeText(this, "Cuenta eliminada", Toast.LENGTH_SHORT).show();
            logout();
        } else {
            Toast.makeText(this, "No se encontró el usuario", Toast.LENGTH_SHORT).show();
        }
    }
}
