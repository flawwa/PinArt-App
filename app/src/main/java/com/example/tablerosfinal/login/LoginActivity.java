package com.example.tablerosfinal.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.example.tablerosfinal.BD.DatabaseManager;
import com.example.tablerosfinal.BD.Usuarios;
import com.example.tablerosfinal.Constante;
import com.example.tablerosfinal.Inicio.InicioActivity;
import com.example.tablerosfinal.R;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private CheckBox rememberMeCheckbox;

    public static final String PREFS_NAME = "LoginPrefs";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";
    private static final String PREF_CHECKED = "checked";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        rememberMeCheckbox = findViewById(R.id.rememberMeCheckbox);

        // Restaura los valores de nombre de usuario y contraseña si "Remember me" está marcado
        restoreLoginCredentials();

        findViewById(R.id.loginButton).setOnClickListener(v -> attemptLogin());

        ImageView imageViewButton = findViewById(R.id.imageViewButton);
        imageViewButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void restoreLoginCredentials() {
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            SharedPreferences preferences = EncryptedSharedPreferences.create(
                    PREFS_NAME,
                    masterKeyAlias,
                    getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            if (preferences.getBoolean(PREF_CHECKED, false)) {
                usernameEditText.setText(preferences.getString(PREF_USERNAME, ""));
                passwordEditText.setText(preferences.getString(PREF_PASSWORD, ""));
                rememberMeCheckbox.setChecked(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void attemptLogin() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuarios user = DatabaseManager.getInstance(getApplicationContext()).usuariosDao().getUserByUsername(username);
        if (user != null) {
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPasswordHash());
            if (result.verified) {
                Constante.usuario = user;
                handleSuccessfulLogin(username, password);
            } else {
                Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSuccessfulLogin(String username, String password) {
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            SharedPreferences preferences = EncryptedSharedPreferences.create(
                    PREFS_NAME,
                    masterKeyAlias,
                    getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            if (rememberMeCheckbox.isChecked()) {
                saveLoginCredentials(preferences, username, password);
            } else {
                clearLoginCredentials(preferences);
            }

            // Guardar el ID del usuario en las preferencias compartidas
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("userId", Constante.usuario.getId());
            editor.apply();

            Intent intent = new Intent(LoginActivity.this, InicioActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveLoginCredentials(SharedPreferences preferences, String username, String password) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_USERNAME, username);
        editor.putString(PREF_PASSWORD, password);
        editor.putBoolean(PREF_CHECKED, true);
        editor.apply();
    }

    private void clearLoginCredentials(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(PREF_USERNAME);
        editor.remove(PREF_PASSWORD);
        editor.remove(PREF_CHECKED);
        editor.apply();
    }
}
