package com.example.tablerosfinal.Tableros;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tablerosfinal.R;

// Es un dialogo que te permite ingresar el nombre de un tablero nuevo
public class CrearTableroDialogFragment extends DialogFragment {

    public interface OnBoardCreatedListener {
        void onBoardCreated(String boardName);
    }

    private OnBoardCreatedListener listener;

    public static CrearTableroDialogFragment newInstance(OnBoardCreatedListener listener) {
        CrearTableroDialogFragment fragment = new CrearTableroDialogFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_crear_tablero, container, false);
        final EditText boardNameEditText = view.findViewById(R.id.boardNameEditText);
        Button boardCreateButton = view.findViewById(R.id.boardCreateButton);
        boardCreateButton.setOnClickListener(v -> {
            String boardName = boardNameEditText.getText().toString().trim();
            if (!boardName.isEmpty()) {
                if (listener != null) {
                    listener.onBoardCreated(boardName);
                }
                dismiss();
            } else {
                boardNameEditText.setError("Ingrese un nombre válido");
            }
        });
        return view;
    }
}
