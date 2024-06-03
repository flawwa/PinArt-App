package com.example.tablerosfinal.Movimientos;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tablerosfinal.Config.SettingsActivity;
import com.example.tablerosfinal.Inicio.InicioActivity;
import com.example.tablerosfinal.R;
import com.example.tablerosfinal.Tableros.ActivityTableros;
import com.example.tablerosfinal.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityPrueba extends AppCompatActivity {
    private View lastAdjustedView = null;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private CardView cardView;
    private int largeCardHeight;
    private int normalCardHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos_main);

        // obtiene las alturas de las tarjetas desde los recursos <dimen>
        Resources resources = getResources();
        largeCardHeight = resources.getDimensionPixelSize(R.dimen.large_card_height);
        normalCardHeight = resources.getDimensionPixelSize(R.dimen.normal_card_height);

        // Referencias a los elementos del layout
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView2 = findViewById(R.id.recyclerView2); // referencia al RecyclerView de la segunda tanda
        cardView = findViewById(R.id.cardView_2);

        // setteo del primer RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CardAdapter());

        // setteo del segundo RecyclerView
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager2);
        CardAdapterDos adapterDos = new CardAdapterDos(largeCardHeight, normalCardHeight);
        recyclerView2.setAdapter(adapterDos); // Se Utiliza el adapterdos para la segunda tanda de cardviews

        // Agregar un OnScrollListener al segundo RecyclerView
        recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Calcular el centro visible de la pantalla en relación con el RecyclerView
                int recyclerViewCenterX = recyclerView2.getWidth() / 2 + recyclerView2.computeHorizontalScrollOffset();

                View closestChild = null;
                int smallestDistance = Integer.MAX_VALUE;

                // Iterar a través de todas las vistas visibles
                for (int i = 0; i < recyclerView2.getChildCount(); i++) {
                    View child = recyclerView2.getChildAt(i);
                    int childCenterX = child.getLeft() + child.getWidth() / 2;

                    int distance = Math.abs(recyclerViewCenterX - childCenterX);

                    // Comprobar si esta vista está más cerca del centro que las anteriores
                    if (distance < smallestDistance) {
                        smallestDistance = distance;
                        closestChild = child;
                    }
                }

                if (closestChild != null && closestChild != lastAdjustedView) {
                    // Ajustar dinámicamente el tamaño del CardView más cercano al centro con animación
                    animateHeightChange(closestChild, largeCardHeight);

                    // Restaurar el tamaño de la vista anteriormente ajustada con animación
                    if (lastAdjustedView != null) {
                        animateHeightChange(lastAdjustedView, normalCardHeight);
                    }

                    // Actualizar la vista anteriormente ajustada
                    lastAdjustedView = closestChild;
                }
            }
        });


        // Esto es una prueba
        cardView.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_HOVER_ENTER:
                        Log.d("Hover", "Cursor enter detected on CardView");
                        cardView.setScaleX(1.1f);
                        cardView.setScaleY(1.1f);
                        break;
                    case MotionEvent.ACTION_HOVER_EXIT:
                        Log.d("Hover", "Cursor exit detected on CardView");
                        cardView.setScaleX(1f);
                        cardView.setScaleY(1f);
                        break;
                }
                return false;
            }
        });

        // OnGenericMotionListener para el CardView fluido
        cardView.setOnGenericMotionListener(new View.OnGenericMotionListener() {
            @Override
            public boolean onGenericMotion(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_HOVER_MOVE) {
                    Log.d("Hover", "Cursor move detected on CardView");
                }
                return false;
            }
        });

        // BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    startActivity(new Intent(ActivityPrueba.this, InicioActivity.class));
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.arte) {
                    startActivity(new Intent(ActivityPrueba.this, ActivityPrueba.class));
                    return true;
                } else if (item.getItemId() == R.id.tab) {
                    startActivity(new Intent(ActivityPrueba.this, ActivityTableros.class));
                } else if (item.getItemId() == R.id.config) {
                    startActivity(new Intent(ActivityPrueba.this, SettingsActivity.class));
                }
                return false;
            }
        });
    }

    private void animateHeightChange(final View view, int targetHeight) {
        final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofInt(view.getHeight(), targetHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height = (int) valueAnimator.getAnimatedValue();
                view.setLayoutParams(layoutParams);
            }
        });
        animator.setInterpolator(new DecelerateInterpolator()); // Esto hace que la animación vaya fluida
        animator.setDuration(300);
        animator.start();
    }

    private void logout() {
        // Limpiar las credenciales guardadas
        SharedPreferences preferences = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();  // Limpia todas las preferencias
        editor.apply();

        // Redirigir al usuario a la pantalla de inicio de sesión
        Intent intent = new Intent(ActivityPrueba.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}