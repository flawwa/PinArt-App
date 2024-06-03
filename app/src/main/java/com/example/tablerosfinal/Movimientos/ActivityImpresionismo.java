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

public class ActivityImpresionismo extends AppCompatActivity implements AddDialogFragment.OnYesButtonListener {

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos_descripcion);

        // Cambiar el título
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        textViewTitle.setText("Impresionismo");

        // Instancia de la bd
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "PruebaTablerosFinal").allowMainThreadQueries().build();

        // Inserción en la tabla Imagenes
        if (database.imagenesDAO().getImagenById(1) == null) {
            // Crear objetos Imagenes y guardarlos en la base de datos
            Imagenes imagen1 = new Imagenes("Corriendo por la playa - Sorolla", getString(R.string.descripcion_imagen1), R.drawable.impresionismo_corriendo, "MovimientoImpresionismo");
            database.imagenesDAO().insert(imagen1);

            Imagenes imagen2 = new Imagenes("Escena de verano - Frédéric Bazille", getString(R.string.descripcion_imagen2), R.drawable.impresionismo_esverano,
                    "MovimientoImpresionismo");
            database.imagenesDAO().insert(imagen2);

            Imagenes imagen3 = new Imagenes("Efecto de lluvia - Caillebotte", getString(R.string.descripcion_imagen3), R.drawable.impresionismo_lluvia,
                    "MovimientoImpresionismo");
            database.imagenesDAO().insert(imagen3);

            Imagenes imagen4 = new Imagenes("Niños en la playa - Sorolla", getString(R.string.descripcion_imagen4), R.drawable.impresionismo_ninosplaya, "MovimientoImpresionismo");
            database.imagenesDAO().insert(imagen4);

            Imagenes imagen5 = new Imagenes("Nocturno: azul - Whistler", getString(R.string.descripcion_imagen5), R.drawable.impresionismo_nocturno, "MovimientoImpresionismo");
            database.imagenesDAO().insert(imagen5);

            Imagenes imagen6 = new Imagenes("La urraca - Monet", getString(R.string.descripcion_imagen6), R.drawable.impresionismo_nieve,"MovimientoImpresionismo");
            database.imagenesDAO().insert(imagen6);
        }

        // Asignar títulos, descripciones y URLs de imagen a cada CardView
        asignarDatosCardView();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    startActivity(new Intent(ActivityImpresionismo.this, InicioActivity.class));
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.arte) {
                    startActivity(new Intent(ActivityImpresionismo.this, ActivityPrueba.class));
                    return true;
                } else if (item.getItemId() == R.id.tab){
                    startActivity(new Intent(ActivityImpresionismo.this, ActivityTableros.class));
                } else if (item.getItemId() == R.id.config) {
                    startActivity(new Intent(ActivityImpresionismo.this, SettingsActivity.class));
                }
                return false;
            }
        });
    }


    private void asignarDatosCardView() {
        // Obtener referencias a los elementos de la interfaz de usuario
        CardView cardView1 = findViewById(R.id.cardView1);
        TextView descripcion1 = findViewById(R.id.descripcion1); // Asegúrate de tener el ID correcto
        ImageView imagen1View = findViewById(R.id.imagen1);
        Imagenes imagen1 = database.imagenesDAO().getImagenById(1);

        // Configurar la descripción para que se vea truncada
        descripcion1.setMaxLines(1);
        descripcion1.setEllipsize(TextUtils.TruncateAt.END);

        // Configurar el título, descripción y imagen
        TextView titulo1 = findViewById(R.id.titulo1);
        titulo1.setText(imagen1.getTitulo());
        descripcion1.setText(imagen1.getDescripcion());
        Picasso.get().load(imagen1.getResourceId()).into(imagen1View);

        // Setup de los dialog y click listeners
        setupLongPressDialog(cardView1, imagen1.getIdImagenes());
        setupDialogOnClick(cardView1, imagen1);

        Button shareButton1 = findViewById(R.id.share_button1);
        shareButton1.setOnClickListener(shareView -> compartirPorWhatsApp(imagen1.getTitulo(), imagen1.getDescripcion(), imagen1View));


        // CardView2
        CardView cardView2 = findViewById(R.id.cardView2);
        TextView descripcion2 = findViewById(R.id.descripcion2);
        ImageView imagen2View = findViewById(R.id.imagen2);
        Imagenes imagen2 = database.imagenesDAO().getImagenById(2);

        descripcion2.setMaxLines(1);
        descripcion2.setEllipsize(TextUtils.TruncateAt.END);

        TextView titulo2 = findViewById(R.id.titulo2);
        titulo2.setText(imagen2.getTitulo());
        descripcion2.setText(imagen2.getDescripcion());
        Picasso.get().load(imagen2.getResourceId()).into(imagen2View);
        setupLongPressDialog(cardView2, imagen2.getIdImagenes());
        setupDialogOnClick(cardView2, imagen2);

        Button shareButton2 = findViewById(R.id.share_button2);
        shareButton2.setOnClickListener(shareView -> compartirPorWhatsApp(imagen2.getTitulo(), imagen2.getDescripcion(), imagen2View));

        // CardView3
        CardView cardView3 = findViewById(R.id.cardView3);
        TextView descripcion3 = findViewById(R.id.descripcion3);
        ImageView imagen3View = findViewById(R.id.imagen3);
        Imagenes imagen3 = database.imagenesDAO().getImagenById(3);

        descripcion3.setMaxLines(1);
        descripcion3.setEllipsize(TextUtils.TruncateAt.END);

        TextView titulo3 = findViewById(R.id.titulo3);
        titulo3.setText(imagen3.getTitulo());
        descripcion3.setText(imagen3.getDescripcion());
        Picasso.get().load(imagen3.getResourceId()).into(imagen3View);
        setupLongPressDialog(cardView3, imagen3.getIdImagenes());
        setupDialogOnClick(cardView3, imagen3);

        Button shareButton3 = findViewById(R.id.share_button3);
        shareButton3.setOnClickListener(shareView -> compartirPorWhatsApp(imagen3.getTitulo(), imagen3.getDescripcion(), imagen3View));

        // CardView4
        CardView cardView4 = findViewById(R.id.cardView4);
        TextView descripcion4 = findViewById(R.id.descripcion4);
        ImageView imagen4View = findViewById(R.id.imagen4);
        Imagenes imagen4 = database.imagenesDAO().getImagenById(4);

        descripcion4.setMaxLines(1);
        descripcion4.setEllipsize(TextUtils.TruncateAt.END);

        TextView titulo4 = findViewById(R.id.titulo4);
        titulo4.setText(imagen4.getTitulo());
        descripcion4.setText(imagen4.getDescripcion());
        Picasso.get().load(imagen4.getResourceId()).into(imagen4View);
        setupLongPressDialog(cardView4, imagen4.getIdImagenes());
        setupDialogOnClick(cardView4, imagen4);

        Button shareButton4 = findViewById(R.id.share_button4);
        shareButton4.setOnClickListener(shareView -> compartirPorWhatsApp(imagen4.getTitulo(), imagen4.getDescripcion(), imagen4View));

        // CardView5
        CardView cardView5 = findViewById(R.id.cardView5);
        TextView descripcion5 = findViewById(R.id.descripcion5);
        ImageView imagen5View = findViewById(R.id.imagen5);
        Imagenes imagen5 = database.imagenesDAO().getImagenById(5);

        descripcion5.setMaxLines(1);
        descripcion5.setEllipsize(TextUtils.TruncateAt.END);

        TextView titulo5 = findViewById(R.id.titulo5);
        titulo5.setText(imagen5.getTitulo());
        descripcion5.setText(imagen5.getDescripcion());
        Picasso.get().load(imagen5.getResourceId()).into(imagen5View);
        setupLongPressDialog(cardView5, imagen5.getIdImagenes());
        setupDialogOnClick(cardView5, imagen5);

        Button shareButton5 = findViewById(R.id.share_button5);
        shareButton5.setOnClickListener(shareView -> compartirPorWhatsApp(imagen5.getTitulo(), imagen5.getDescripcion(), imagen5View));

        // CardView6
        CardView cardView6 = findViewById(R.id.cardView6);
        TextView descripcion6 = findViewById(R.id.descripcion6);
        ImageView imagen6View = findViewById(R.id.imagen6);
        Imagenes imagen6 = database.imagenesDAO().getImagenById(6);

        descripcion6.setMaxLines(1);
        descripcion6.setEllipsize(TextUtils.TruncateAt.END);

        TextView titulo6 = findViewById(R.id.titulo6);
        titulo6.setText(imagen6.getTitulo());
        descripcion6.setText(imagen6.getDescripcion());
        Picasso.get().load(imagen6.getResourceId()).into(imagen6View);
        setupLongPressDialog(cardView6, imagen6.getIdImagenes());
        setupDialogOnClick(cardView6, imagen6);

        Button shareButton6 = findViewById(R.id.share_button6);
        shareButton6.setOnClickListener(shareView -> compartirPorWhatsApp(imagen6.getTitulo(), imagen6.getDescripcion(), imagen6View));
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