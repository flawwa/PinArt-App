package com.example.tablerosfinal.Movimientos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.room.Room;
import com.example.tablerosfinal.BD.AppDatabase;
import com.example.tablerosfinal.BDImagenes.Imagenes;
import com.example.tablerosfinal.Config.SettingsActivity;
import com.example.tablerosfinal.Constante;
import com.example.tablerosfinal.Inicio.InicioActivity;
import com.example.tablerosfinal.R;
import com.example.tablerosfinal.Tableros.ActivityTableros;
import com.example.tablerosfinal.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ActivityNeoexpresionismo extends AppCompatActivity implements AddDialogFragment.OnYesButtonListener {
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neo);

        // Cambiar el título
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        textViewTitle.setText("Neoexpresionismo");

        // Instancia de la bd
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "PruebaTablerosFinal").allowMainThreadQueries().build();

        // Inserción en la tabla Imagenes
        if (database.imagenesDAO().getImagenById(13) == null) {
            // Crear objetos Imagenes y guardarlos en la base de datos
            Imagenes imagen13 = new Imagenes("Washing the salt off - Brett Whiteley", getString(R.string.descripcion_imagen13), R.drawable.neo_washing, "MovimientoNeoexpresionismo");
            database.imagenesDAO().insert(imagen13);

            Imagenes imagen14 = new Imagenes("Untitled painting- Graca Morais", getString(R.string.descripcion_imagen14), R.drawable.neo_untitled , "MovimientoNeoexpresionismo");
            database.imagenesDAO().insert(imagen14);

            Imagenes imagen15 = new Imagenes("Space Station - Landon Mackenzie", getString(R.string.descripcion_imagen15), R.drawable.neo_spacestation, "MovimientoNeoexpresionismo");
            database.imagenesDAO().insert(imagen15);

            Imagenes imagen16 = new Imagenes("After Puno - Michel Basquiat", getString(R.string.descripcion_imagen16), R.drawable.neo_afterpuno, "MovimientoNeoexpresionismo");
            database.imagenesDAO().insert(imagen16);

            Imagenes imagen17 = new Imagenes("Kena Gigi Uang Kembali - Nyoman Masriadi", getString(R.string.descripcion_imagen17), R.drawable.neo_kenagigi, "MovimientoNeoexpresionismo");
            database.imagenesDAO().insert(imagen17);

            Imagenes imagen18 = new Imagenes("The fog of war - Marlene Dumas", getString(R.string.descripcion_imagen18), R.drawable.neo_thefog, "MovimientoNeoexpresionismo");
            database.imagenesDAO().insert(imagen18);
        }

        // Asigna títulos, descripciones y URLs de imagen a cada CardView
        asignarDatosCardView();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    startActivity(new Intent(ActivityNeoexpresionismo.this, InicioActivity.class));
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.arte) {
                    startActivity(new Intent(ActivityNeoexpresionismo.this, ActivityPrueba.class));
                    return true;
                } else if (item.getItemId() == R.id.tab){
                    startActivity(new Intent(ActivityNeoexpresionismo.this, ActivityTableros.class));
                } else if (item.getItemId() == R.id.config) {
                    startActivity(new Intent(ActivityNeoexpresionismo.this, SettingsActivity.class));
                }
                return false;
            }
        });
    }


    private void asignarDatosCardView() {
        // Obtener referencias a los elementos de la interfaz de usuario
        CardView cardView13 = findViewById(R.id.cardView13);
        TextView descripcion13 = findViewById(R.id.descripcion13); // Asegúrate de tener el ID correcto
        ImageView imagen13View = findViewById(R.id.imagen13);
        Imagenes imagen13 = database.imagenesDAO().getImagenById(13);

        // Configurar la descripción para que se vea truncada
        descripcion13.setMaxLines(1);
        descripcion13.setEllipsize(TextUtils.TruncateAt.END);

        // Configurar el título, descripción y imagen
        TextView titulo13 = findViewById(R.id.titulo13);
        titulo13.setText(imagen13.getTitulo());
        descripcion13.setText(imagen13.getDescripcion());
        Picasso.get().load(imagen13.getResourceId()).into(imagen13View);

        // Setup de los dialog y click listeners
        setupLongPressDialog(cardView13, imagen13.getIdImagenes());
        setupDialogOnClick(cardView13, imagen13);

        Button shareButton13 = findViewById(R.id.share_button13);
        shareButton13.setOnClickListener(shareView -> compartirPorWhatsApp(imagen13.getTitulo(), imagen13.getDescripcion(), imagen13View));



        // 2
        CardView cardView14 = findViewById(R.id.cardView14);
        TextView descripcion14 = findViewById(R.id.descripcion14);
        ImageView imagen14View = findViewById(R.id.imagen14);
        Imagenes imagen14 = database.imagenesDAO().getImagenById(14);

        descripcion14.setMaxLines(1);
        descripcion14.setEllipsize(TextUtils.TruncateAt.END);

        TextView titulo14 = findViewById(R.id.titulo14);
        titulo14.setText(imagen14.getTitulo());
        descripcion14.setText(imagen14.getDescripcion());
        Picasso.get().load(imagen14.getResourceId()).into(imagen14View);
        setupLongPressDialog(cardView14, imagen14.getIdImagenes());
        setupDialogOnClick(cardView14, imagen14);

        Button shareButton14 = findViewById(R.id.share_button14);
        shareButton14.setOnClickListener(shareView -> compartirPorWhatsApp(imagen14.getTitulo(), imagen14.getDescripcion(), imagen14View));

        // 3
        CardView cardView15 = findViewById(R.id.cardView15);
        TextView descripcion15 = findViewById(R.id.descripcion15); // Asegúrate de tener el ID correcto
        ImageView imagen15View = findViewById(R.id.imagen15);
        Imagenes imagen15 = database.imagenesDAO().getImagenById(15);

        // Configurar la descripción para que se vea truncada
        descripcion15.setMaxLines(1);
        descripcion15.setEllipsize(TextUtils.TruncateAt.END);

        // Configurar el título, descripción y imagen
        TextView titulo15 = findViewById(R.id.titulo15);
        titulo15.setText(imagen15.getTitulo());
        descripcion15.setText(imagen15.getDescripcion());
        Picasso.get().load(imagen15.getResourceId()).into(imagen15View);

        // Setup de los dialog y click listeners
        setupLongPressDialog(cardView15, imagen15.getIdImagenes());
        setupDialogOnClick(cardView15, imagen15);

        Button shareButton15 = findViewById(R.id.share_button15);
        shareButton15.setOnClickListener(shareView -> compartirPorWhatsApp(imagen15.getTitulo(), imagen15.getDescripcion(), imagen15View));
        // 4
        CardView cardView16 = findViewById(R.id.cardView16);
        TextView descripcion16 = findViewById(R.id.descripcion16); // Asegúrate de tener el ID correcto
        ImageView imagen16View = findViewById(R.id.imagen16);
        Imagenes imagen16 = database.imagenesDAO().getImagenById(16);

        // Configurar la descripción para que se vea truncada
        descripcion16.setMaxLines(1);
        descripcion16.setEllipsize(TextUtils.TruncateAt.END);

        // Configurar el título, descripción y imagen
        TextView titulo16 = findViewById(R.id.titulo16);
        titulo16.setText(imagen16.getTitulo());
        descripcion16.setText(imagen16.getDescripcion());
        Picasso.get().load(imagen16.getResourceId()).into(imagen16View);

        // Setup de los dialog y click listeners
        setupLongPressDialog(cardView16, imagen16.getIdImagenes());
        setupDialogOnClick(cardView16, imagen16);

        Button shareButton16 = findViewById(R.id.share_button16);
        shareButton16.setOnClickListener(shareView -> compartirPorWhatsApp(imagen16.getTitulo(), imagen16.getDescripcion(), imagen16View));


        // 5
        CardView cardView17 = findViewById(R.id.cardView17);
        TextView descripcion17 = findViewById(R.id.descripcion17); // Asegúrate de tener el ID correcto
        ImageView imagen17View = findViewById(R.id.imagen17);
        Imagenes imagen17 = database.imagenesDAO().getImagenById(17);

        // Configurar la descripción para que se vea truncada
        descripcion17.setMaxLines(1);
        descripcion17.setEllipsize(TextUtils.TruncateAt.END);

        // Configurar el título, descripción y imagen
        TextView titulo17 = findViewById(R.id.titulo17);
        titulo17.setText(imagen17.getTitulo());
        descripcion17.setText(imagen17.getDescripcion());
        Picasso.get().load(imagen17.getResourceId()).into(imagen17View);

        // Setup de los dialog y click listeners
        setupLongPressDialog(cardView17, imagen17.getIdImagenes());
        setupDialogOnClick(cardView17, imagen17);

        Button shareButton17 = findViewById(R.id.share_button17);
        shareButton17.setOnClickListener(shareView -> compartirPorWhatsApp(imagen17.getTitulo(), imagen17.getDescripcion(), imagen17View));


        // 18
        CardView cardView18 = findViewById(R.id.cardView18);
        TextView descripcion18 = findViewById(R.id.descripcion18); // Asegúrate de tener el ID correcto
        ImageView imagen18View = findViewById(R.id.imagen18);
        Imagenes imagen18 = database.imagenesDAO().getImagenById(18);

        // Configurar la descripción para que se vea truncada
        descripcion18.setMaxLines(1);
        descripcion18.setEllipsize(TextUtils.TruncateAt.END);

        // Configurar el título, descripción y imagen
        TextView titulo18 = findViewById(R.id.titulo18);
        titulo18.setText(imagen18.getTitulo());
        descripcion18.setText(imagen18.getDescripcion());
        Picasso.get().load(imagen18.getResourceId()).into(imagen18View);

        // Setup de los dialog y click listeners
        setupLongPressDialog(cardView18, imagen18.getIdImagenes());
        setupDialogOnClick(cardView18, imagen18);

        Button shareButton18 = findViewById(R.id.share_button18);
        shareButton18.setOnClickListener(shareView -> compartirPorWhatsApp(imagen18.getTitulo(), imagen18.getDescripcion(), imagen18View));
    }

    private void setupLongPressDialog(CardView cardView, int imageId) {
        cardView.setOnLongClickListener(v -> {
            AddDialogFragment dialog = AddDialogFragment.newInstance(this, imageId);
            dialog.show(getSupportFragmentManager(), "AddDialogFragment");
            return true;
        });
    }

    @Override
    public void onYesSelected(int imageId) {
        TablerosListDialogFragment dialog = TablerosListDialogFragment.newInstance(Constante.usuario.getId(), imageId);
        dialog.show(getSupportFragmentManager(), "TablerosListDialogFragment");
    }

    private void setupDialogOnClick(CardView cardView, Imagenes imagen) {
        cardView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(imagen.getTitulo());

            // Inflar y configurar el layout para el diálogo
            final View customLayout = getLayoutInflater().inflate(R.layout.prueba_dialog, null);
            builder.setView(customLayout);

            ImageView dialogImage = customLayout.findViewById(R.id.dialog_image);
            TextView dialogDescription = customLayout.findViewById(R.id.dialog_description);

            // Configurar el texto para mostrar completamente en el diálogo
            dialogDescription.setText(imagen.getDescripcion());
            dialogDescription.setMaxLines(Integer.MAX_VALUE);
            dialogDescription.setEllipsize(null);

            Picasso.get().load(imagen.getResourceId()).into(dialogImage);

            builder.setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss());
            builder.create().show();
        });
    }

    private void compartirPorWhatsApp(String titulo, String descripcion, ImageView imageView) {
        String shareText = titulo + "\n\n" + descripcion;
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        try {
            // Guardar el bitmap en un archivo
            File cachePath = new File(getCacheDir(), "images");
            cachePath.mkdirs();
            File file = new File(cachePath, "shared_image.png");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

            // Obtener el URI del archivo
            Uri imageUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", file);

            // Crear el intent de compartir
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setPackage("com.whatsapp");

            startActivity(shareIntent);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al compartir la imagen.", Toast.LENGTH_SHORT).show();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "WhatsApp no está instalado.", Toast.LENGTH_SHORT).show();
        }
    }
}

