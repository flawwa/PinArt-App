package com.example.tablerosfinal.login;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.tablerosfinal.BD.DatabaseManager;
import com.example.tablerosfinal.BD.Usuarios;
import com.example.tablerosfinal.R;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);

        // Registro de usuario al hacer clic en el botón de registro
        findViewById(R.id.registerButton).setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(MainActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(MainActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }

            String passwordHash = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            Usuarios newUser = new Usuarios(username, passwordHash);
            DatabaseManager.getInstance(getApplicationContext()).usuariosDao().insert(newUser);

            Toast.makeText(MainActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
        });


        ImageView imageViewButton = findViewById(R.id.imageViewButton);
        imageViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}