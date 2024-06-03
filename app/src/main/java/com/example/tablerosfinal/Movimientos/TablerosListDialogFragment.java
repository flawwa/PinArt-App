package com.example.tablerosfinal.Movimientos;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.tablerosfinal.BDTableros.Tablero_imagenes;
import com.example.tablerosfinal.R;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tablerosfinal.BD.DatabaseManager;
import com.example.tablerosfinal.Tableros.TableroAdapter;

public class TablerosListDialogFragment extends DialogFragment {
    private RecyclerView recyclerView;
    private TableroAdapter adapter;
    private int userId;
    private int imageId;

    public static TablerosListDialogFragment newInstance(int userId, int imageId) {
        TablerosListDialogFragment fragment = new TablerosListDialogFragment();
        Bundle args = new Bundle();
        args.putInt("userId", userId);
        args.putInt("imageId", imageId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt("userId");
            imageId = getArguments().getInt("imageId");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_tableros_list, container, false);
        recyclerView = view.findViewById(R.id.tablerosRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() != null) {
            userId = getArguments().getInt("userId");
            imageId = getArguments().getInt("imageId");
        }
        adapter = new TableroAdapter(DatabaseManager.getInstance(getContext()).tablerosDao().getTablerosByUserId(userId), getContext(), this::onTableroSelected, imageId);
        recyclerView.setAdapter(adapter);

        Button closeButton = view.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> dismiss());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    public void onTableroSelected(int tableroId, int imagenId) {
        if (DatabaseManager.getInstance(getContext()).tableroImagenesDao().isImageOnTablero(tableroId, imagenId)) {
            // notificación al usuario
            Toast.makeText(getContext(), "Esta imagen ya ha sido añadida a este tablero.", Toast.LENGTH_SHORT).show();
        } else {
            Tablero_imagenes nuevoRegistro = new Tablero_imagenes(tableroId, imagenId);
            DatabaseManager.getInstance(getContext()).tableroImagenesDao().insertTableroImagen(nuevoRegistro);
            dismiss();
        }
    }

}