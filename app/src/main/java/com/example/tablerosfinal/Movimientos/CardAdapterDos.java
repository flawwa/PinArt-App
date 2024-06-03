package com.example.tablerosfinal.Movimientos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tablerosfinal.R;
import com.squareup.picasso.Picasso;


public class CardAdapterDos extends RecyclerView.Adapter<CardAdapterDos.CardViewHolder> {

    private int largeCardHeight;
    private int normalCardHeight;

    public CardAdapterDos(int largeCardHeight, int normalCardHeight) {
        this.largeCardHeight = largeCardHeight;
        this.normalCardHeight = normalCardHeight;
    }

    private String[] titles = {"Ofelía - John Everett", "Hilas y las nínfas - John Waterhouse", "El rapto de Geminedes - John Waterhouse", "Dánae - Gustav Klimpt", "Chica arrodillada - Egon Schiele"};
    private String[] imageUrls = {
            "https://historia-arte.com/_/eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbSI6WyJcL2FydHdvcmtcL2ltYWdlRmlsZVwvam9obi1ldmVyZXR0LW1pbGxhaXMtb3BoZWxpYS5qcGciLCJyZXNpemUsMTUwMHxmb3JtYXQsd2VicCJdfQ.A9STjU4A6XkcMVrm6rt85KJ8MdbsEWQU7REnxE06/mTg.webp",
            "https://historia-arte.com/_/eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbSI6WyJcL2FydHdvcmtcL2ltYWdlRmlsZVwvNjFlZmE3MThjNDlkOS5qcGciLCJyZXNpemUsMTUwMHxmb3JtYXQsd2VicCJdfQ.x2QsfC63-ieEpO25h6VEg_hPrdkQQ5Tj_-597eq1XeU.webp",
            "https://historia-arte.com/_/eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbSI6WyJcL2FydHdvcmtcL2ltYWdlRmlsZVwvNjVlMzM5MjYxYTU2Ny5qcGciLCJyZXNpemUsMTUwMHxmb3JtYXQsd2VicCJdfQ.gVFUbwkQtiZCx2CMkQTcdmmANk-gy61-47zMENQrrUc.webp",
            "https://historia-arte.com/_/eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbSI6WyJcL2FydHdvcmtcL2ltYWdlRmlsZVwvZ3VzdGF2X2tsaW10XzAxMC5qcGciLCJyZXNpemUsMTUwMHxmb3JtYXQsd2VicCJdfQ.VtbvXh-PIrxqftQ727_YE1K9PQVmqXbXrFO2w49fVbY.webp",
            "https://historia-arte.com/_/eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbSI6WyJcL2FydHdvcmtcL2ltYWdlRmlsZVwvNjVhZmRmYmQ3YzUxNi5qcGciLCJyZXNpemUsMTUwMHxmb3JtYXQsd2VicCJdfQ.ECv_NDRHZA9_UgIwx-yNwBVx6owtV60qYM4mtnEDQO8.webp"
    };

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movimientos_2, parent, false); // Infla el nuevo layout
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.titleTextView.setText(titles[position]);
        Picasso.get().load(imageUrls[position]).into(holder.imageView);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (position == 0) {
            layoutParams.height = largeCardHeight;
        } else {
            layoutParams.height = normalCardHeight;
        }
        holder.itemView.setLayoutParams(layoutParams);
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
            imageView = itemView.findViewById(R.id.imageView_card_2); // Actualiza las referencias a los IDs
            titleTextView = itemView.findViewById(R.id.textView_title_2); // Actualiza las referencias a los IDs
        }
    }
}