package com.example.tablerosfinal.Movimientos;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CardItemDecoration extends RecyclerView.ItemDecoration {

    private int largeCardHeight;
    private int normalCardHeight;

    public CardItemDecoration(int largeCardHeight, int normalCardHeight) {
        this.largeCardHeight = largeCardHeight;
        this.normalCardHeight = normalCardHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);

        // Calcula la altura de la tarjeta en función de la posición
        int cardHeight = (position == 0) ? largeCardHeight : normalCardHeight;
        outRect.bottom = cardHeight;
    }
}
