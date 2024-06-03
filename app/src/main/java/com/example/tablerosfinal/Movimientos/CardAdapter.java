package com.example.tablerosfinal.Movimientos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tablerosfinal.R;
import com.squareup.picasso.Picasso;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private String[] titles = {"Impresionismo", "Art Decó", "Neoexpresionismo" /* "Objetividad", "Cubismo"*/};
    private String[] imageUrls = {
            "https://historia-arte.com/_/eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbSI6WyJcL2FydHdvcmtcL2ltYWdlRmlsZVwvY2xhdWRlLW1vbmV0LTAzNy5qcGciLCJyZXNpemUsMTUwMHxmb3JtYXQsd2VicCJdfQ.02qtZLdYtwykwIbDRVzmyu4uMIVwtqycSsduksUdMjY.webp",
            "https://historia-arte.com/_/eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbSI6WyJcL2FydHdvcmtcL2ltYWdlRmlsZVwvMTkxMy1fZDBiMWQwYjBkMGJkZDE4Zi5qcGciLCJyZXNpemUsMTUwMHxmb3JtYXQsd2VicCJdfQ.O-fNdVw97E5ZfQHcAjraDULXwPdBXjxtihNixOjhxNo.webp",
            "https://3minutosdearte.com/wp-content/uploads/2018/01/Basquiat-Bird-on-money-1981-e1575575109431.jpg"
            /*"https://historia-arte.com/_/eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbSI6WyJcL2FydHdvcmtcL2ltYWdlRmlsZVwvY2FybmF2YWxfYmVja21hbm4uanBnIiwicmVzaXplLDE1MDB8Zm9ybWF0LHdlYnAiXX0.4-C0oP4v0LPjTTHFVrXVHWpxh_nYAu1nrr8gmankug4.webp",
            "https://historia-arte.com/_/eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbSI6WyJcL2FydHdvcmtcL2ltYWdlRmlsZVwvNjQzYmRhYzFjZmZiNy5qcGVnIiwicmVzaXplLDE1MDB8Zm9ybWF0LHdlYnAiXX0.9c5AaS5Dk8ikAa3EGKLc2layG2hRojgDhDUYp75CEMo.webp"*/
    };

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movimientos, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.titleTextView.setText(titles[position]);
        // Carga la imagen desde la URL con Picasso
        Picasso.get().load(imageUrls[position]).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Intent intent;
                    switch (adapterPosition) {
                        case 0:
                            intent = new Intent(v.getContext(), ActivityImpresionismo.class);
                            break;
                        case 1:
                            intent = new Intent(v.getContext(), ActivityArtDeco.class);
                            break;
                       case 2:
                            intent = new Intent(v.getContext(), ActivityNeoexpresionismo.class);
                            break;
                        default:
                            Toast.makeText(v.getContext(), "Actividad no implementada", Toast.LENGTH_SHORT).show();
                            return;
                    }
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleTextView;

        CardViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.textView);
        }
    }
}
