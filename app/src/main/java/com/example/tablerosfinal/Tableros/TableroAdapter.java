package com.example.tablerosfinal.Tableros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Random;

import com.example.tablerosfinal.BDTableros.Tableros;
import com.example.tablerosfinal.R;

    /*Muestra la lista de los tableros , permite eliminarlos y se encarga de la selección de un tablero*/

public class TableroAdapter extends RecyclerView.Adapter<TableroAdapter.BoardViewHolder> {

    private List<Tableros> boardNames;
    private Context context;
    private OnTableroSelectedListener listener;
    private int imagenId;



    public TableroAdapter(List<Tableros> boardNames, Context context, OnTableroSelectedListener listener, int imagenId) {
        this.boardNames = boardNames;
        this.context = context;
        this.listener = listener;
        this.imagenId = imagenId;
    }

    public interface OnTableroSelectedListener {
        void onTableroSelected(int tableroId, int imageId);
    }


    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cards_tableros, parent, false);
        return new BoardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
        Tableros boardName = boardNames.get(position);
        holder.textViewBoardName.setText(boardName.getNombre());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTableroSelected(boardName.getId(), imagenId);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (context instanceof ActivityTableros) {
                ((ActivityTableros) context).showDeleteDialog(boardName.getId());
            }
            return true;
        });
    }


    @Override
    public int getItemCount() {
        return boardNames.size();
    }

    public List<Tableros> getBoardNames() {
        return boardNames;
    }

    public static class BoardViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBoardName;

        public BoardViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBoardName = itemView.findViewById(R.id.textViewBoardName);
        }
    }

    public void removeTableroById(int tableroId) {
        for (int i = 0; i < boardNames.size(); i++) {
            if (boardNames.get(i).getId() == tableroId) {
                boardNames.remove(i);
                notifyItemRemoved(i);
                return;
            }
        }
    }
}