package com.example.tablerosfinal.BDImagenes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tablerosfinal.R;
import java.util.List;

public class ImagenAdapter extends RecyclerView.Adapter<ImagenAdapter.ImagenViewHolder> {

    private List<Imagenes> imagenesList;
    private boolean showText;

    public ImagenAdapter(List<Imagenes> imagenesList, boolean showText) {
        this.imagenesList = imagenesList;
        this.showText = showText;
    }

    @NonNull
    @Override
    public ImagenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_2, parent, false);
        return new ImagenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagenViewHolder holder, int position) {
        Imagenes imagen = imagenesList.get(position);
        holder.bind(imagen, showText);
    }

    @Override
    public int getItemCount() {
        return imagenesList.size();
    }

    public static class ImagenViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tituloTextView;
        TextView descripcionTextView;

        public ImagenViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tituloTextView = itemView.findViewById(R.id.tituloTextView);
            descripcionTextView = itemView.findViewById(R.id.descripcionTextView);
        }

        public void bind(Imagenes imagen, boolean showText) {
            if (showText) {
                tituloTextView.setText(imagen.getTitulo());
                descripcionTextView.setText(imagen.getDescripcion());
                tituloTextView.setVisibility(View.VISIBLE);
                descripcionTextView.setVisibility(View.VISIBLE);
            } else {
                tituloTextView.setVisibility(View.GONE);
                descripcionTextView.setVisibility(View.GONE);
            }
            imageView.setImageResource(imagen.getResourceId());
        }
    }
}