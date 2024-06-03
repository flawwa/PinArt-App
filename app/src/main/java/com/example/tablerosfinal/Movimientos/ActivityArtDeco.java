package com.example.tablerosfinal.Movimientos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ActivityArtDeco extends AppCompatActivity implements AddDialogFragment.OnYesButtonListener {
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artdeco);

        // Cambiar el título
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        textViewTitle.setText("Art Decó");

        // Instancia de la bd
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "PruebaTablerosFinal").allowMainThreadQueries().build();

        // Inserción en la tabla Imagenes
        if (database.imagenesDAO().getImagenById(7) == null) {
            // Crea objetos Imagenes y se guardan en la base de datos
            Imagenes imagen7 = new Imagenes("Autoretrato en un bugatti verde - Tamara", getString(R.string.descripcion_autoretrato), R.drawable.artdeco_autoretrato, "MovimientoArtDeco");
            database.imagenesDAO().insert(imagen7);

            Imagenes imagen8 = new Imagenes("Almuerzo sobre un rascacielos", getString(R.string.descripcion_almuerzo), R.drawable.almuerzosobreunrascacielos_artdeco,"MovimientoArtDeco");
            database.imagenesDAO().insert(imagen8);

            Imagenes imagen9 = new Imagenes("Sol de invierno - Maxfield Parrish", getString(R.string.descripcion_sol_invierno), R.drawable.invierno_artdeco, "MovimientoArtDeco");
            database.imagenesDAO().insert(imagen9);

            Imagenes imagen10 = new Imagenes("Castillo de naipes - Zinaida", getString(R.string.descripcion_castillo_naipes), R.drawable.naipes_artdeco, "MovimientoArtDeco");
            database.imagenesDAO().insert(imagen10);

            Imagenes imagen11 = new Imagenes("Sauna - Zinaida", getString(R.string.descripcion_sauna), R.drawable.sauna_artdeco, "MovimientoArtDeco");
            database.imagenesDAO().insert(imagen11);

            Imagenes imagen12 = new Imagenes("Metropolis - Fritz Lang", getString(R.string.descripcion_metropolis), R.drawable.artdeco_metropolis , "MovimientoArtDeco");
            database.imagenesDAO().insert(imagen12);
        }

        // Asigna títulos, descripciones y URLs de imagen a cada CardView
        asignarDatosCardView();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    startActivity(new Intent(ActivityArtDeco.this, InicioActivity.class));
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.arte) {
                    startActivity(new Intent(ActivityArtDeco.this, ActivityPrueba.class));
                    return true;
                } else if (item.getItemId() == R.id.tab){
                    startActivity(new Intent(ActivityArtDeco.this, ActivityTableros.class));
                } else if (item.getItemId() == R.id.config) {
                    startActivity(new Intent(ActivityArtDeco.this, SettingsActivity.class));
                }
                return false;
            }
        });
    }

    private void asignarDatosCardView() {
        Button shareButton7 = findViewById(R.id.share_button7);
        // Obtener referencias a los elementos de la interfaz de usuario
        CardView cardView7 = findViewById(R.id.cardView7);
        TextView descripcion7 = findViewById(R.id.descripcion7);
        ImageView imagen7View = findViewById(R.id.imagen7);
        Imagenes imagen7 = database.imagenesDAO().getImagenById(7);

        // Configurar la descripción para que se vea truncada
        descripcion7.setMaxLines(1);
        descripcion7.setEllipsize(TextUtils.TruncateAt.END);

        // Configurar el título, descripción y imagen
        TextView titulo7 = findViewById(R.id.titulo7);
        titulo7.setText(imagen7.getTitulo());
        descripcion7.setText(imagen7.getDescripcion());
        Picasso.get().load(imagen7.getResourceId()).into(imagen7View);

        // Setup de los dialog y click listeners
        setupLongPressDialog(cardView7, imagen7.getIdImagenes());
        setupDialogOnClick(cardView7, imagen7);

        shareButton7.setOnClickListener(shareView -> compartirPorWhatsApp(imagen7.getTitulo(), imagen7.getDescripcion(), imagen7View));

        // 8
        Button shareButton8 = findViewById(R.id.share_button8);
        CardView cardView8 = findViewById(R.id.cardView8);
        TextView descripcion8 = findViewById(R.id.descripcion8); // Asegúrate de tener el ID correcto
        ImageView imagen8View = findViewById(R.id.imagen8);
        Imagenes imagen8 = database.imagenesDAO().getImagenById(8);

        // Configurar la descripción para que se vea truncada
        descripcion8.setMaxLines(1);
        descripcion8.setEllipsize(TextUtils.TruncateAt.END);

        // Configurar el título, descripción y imagen
        TextView titulo8 = findViewById(R.id.titulo8);
        titulo8.setText(imagen8.getTitulo());
        descripcion8.setText(imagen8.getDescripcion());
        Picasso.get().load(imagen8.getResourceId()).into(imagen8View);

        // Setup de los dialog y click listeners
        setupLongPressDialog(cardView8, imagen8.getIdImagenes());
        setupDialogOnClick(cardView8, imagen8);

        shareButton8.setOnClickListener(shareView -> compartirPorWhatsApp(imagen8.getTitulo(), imagen8.getDescripcion(), imagen8View));

        // 9
        Button shareButton9 = findViewById(R.id.share_button9);
        CardView cardView9 = findViewById(R.id.cardView9);
        TextView descripcion9 = findViewById(R.id.descripcion9); // Asegúrate de tener el ID correcto
        ImageView imagen9View = findViewById(R.id.imagen9);
        Imagenes imagen9 = database.imagenesDAO().getImagenById(9);

        // Configurar la descripción para que se vea truncada
        descripcion9.setMaxLines(1);
        descripcion9.setEllipsize(TextUtils.TruncateAt.END);

        // Configurar el título, descripción y imagen
        TextView titulo9 = findViewById(R.id.titulo9);
        titulo9.setText(imagen9.getTitulo());
        descripcion9.setText(imagen9.getDescripcion());
        Picasso.get().load(imagen9.getResourceId()).into(imagen9View);

        // Setup de los dialog y click listeners
        setupLongPressDialog(cardView9, imagen9.getIdImagenes());
        setupDialogOnClick(cardView9, imagen9);

        shareButton9.setOnClickListener(shareView -> compartirPorWhatsApp(imagen9.getTitulo(), imagen9.getDescripcion(), imagen9View));



        // 10
        Button shareButton10 = findViewById(R.id.share_button10);
        CardView cardView10 = findViewById(R.id.cardView10);
        TextView descripcion10 = findViewById(R.id.descripcion10); // Asegúrate de tener el ID correcto
        ImageView imagen10View = findViewById(R.id.imagen10);
        Imagenes imagen10 = database.imagenesDAO().getImagenById(10);

        // Configurar la descripción para que se vea truncada
        descripcion10.setMaxLines(1);
        descripcion10.setEllipsize(TextUtils.TruncateAt.END);

        // Configurar el título, descripción y imagen
        TextView titulo10 = findViewById(R.id.titulo10);
        titulo10.setText(imagen10.getTitulo());
        descripcion10.setText(imagen10.getDescripcion());
        Picasso.get().load(imagen10.getResourceId()).into(imagen10View);

        // Setup de los dialog y click listeners
        setupLongPressDialog(cardView10, imagen10.getIdImagenes());
        setupDialogOnClick(cardView10, imagen10);

        shareButton10.setOnClickListener(shareView -> compartirPorWhatsApp(imagen10.getTitulo(), imagen10.getDescripcion(), imagen10View));

        // 11

        Button shareButton11 = findViewById(R.id.share_button11);
        CardView cardView11 = findViewById(R.id.cardView11);
        TextView descripcion11 = findViewById(R.id.descripcion11); // Asegúrate de tener el ID correcto
        ImageView imagen11View = findViewById(R.id.imagen11);
        Imagenes imagen11 = database.imagenesDAO().getImagenById(11);

        // Configurar la descripción para que se vea truncada
        descripcion11.setMaxLines(1);
        descripcion11.setEllipsize(TextUtils.TruncateAt.END);

        // Configurar el título, descripción y imagen
        TextView titulo11 = findViewById(R.id.titulo11);
        titulo11.setText(imagen11.getTitulo());
        descripcion11.setText(imagen11.getDescripcion());
        Picasso.get().load(imagen11.getResourceId()).into(imagen11View);

        // Setup de los dialog y click listeners
        setupLongPressDialog(cardView11, imagen11.getIdImagenes());
        setupDialogOnClick(cardView11, imagen11);

        shareButton11.setOnClickListener(shareView -> compartirPorWhatsApp(imagen11.getTitulo(), imagen11.getDescripcion(), imagen11View));




        // 12
        Button shareButton12 = findViewById(R.id.share_button12);
        CardView cardView12 = findViewById(R.id.cardView12);
        TextView descripcion12 = findViewById(R.id.descripcion12); // Asegúrate de tener el ID correcto
        ImageView imagen12View = findViewById(R.id.imagen12);
        Imagenes imagen12 = database.imagenesDAO().getImagenById(12);

        // Configurar la descripción para que se vea truncada
        descripcion12.setMaxLines(1);
        descripcion12.setEllipsize(TextUtils.TruncateAt.END);

        // Configurar el título, descripción y imagen
        TextView titulo12 = findViewById(R.id.titulo12);
        titulo12.setText(imagen12.getTitulo());
        descripcion12.setText(imagen12.getDescripcion());
        Picasso.get().load(imagen12.getResourceId()).into(imagen12View);

        // Setup de los dialog y click listeners
        setupLongPressDialog(cardView12, imagen12.getIdImagenes());
        setupDialogOnClick(cardView12, imagen12);

        shareButton12.setOnClickListener(shareView -> compartirPorWhatsApp(imagen12.getTitulo(), imagen12.getDescripcion(), imagen12View));

    }

    private void setupLongPressDialog(CardView cardView, int imageId) {
        cardView.setOnLongClickListener(v -> {
            // Aquí se pasa this como el listener al dialogo
            AddDialogFragment dialog = AddDialogFragment.newInstance(this, imageId);
            dialog.show(getSupportFragmentManager(), "AddDialogFragment");
            return true; // Indica que el evento ha sido manejado
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