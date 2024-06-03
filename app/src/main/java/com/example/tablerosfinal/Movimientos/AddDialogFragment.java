package com.example.tablerosfinal.Movimientos;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.tablerosfinal.R;

public class AddDialogFragment extends DialogFragment {

    // Añadido
    public interface OnYesButtonListener {
        void onYesSelected(int imageId);// spot
    }

    private OnYesButtonListener listener;
    private int imageId; // spot

    public static AddDialogFragment newInstance(OnYesButtonListener listener, int imageId) {
        AddDialogFragment fragment = new AddDialogFragment();
        fragment.listener = listener;
        fragment.imageId = imageId; // spot
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment, container, false);
        view.findViewById(R.id.btn_yes).setOnClickListener(v -> {
            if (listener != null) {
                listener.onYesSelected(imageId); // spot
            }
            // acción "Sí"
            dismiss();
        });
        view.findViewById(R.id.btn_no).setOnClickListener(v -> {
            // acción "No"
            dismiss();
        });
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

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        return dialog;
    }
}