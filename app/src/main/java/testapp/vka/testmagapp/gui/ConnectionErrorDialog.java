package testapp.vka.testmagapp.gui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import testapp.vka.testmagapp.R;

/**
 * Created by kirill on 20.10.2016.
 */

public class ConnectionErrorDialog extends DialogFragment {
    private OnClickListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.connection_error)
                .setPositiveButton(R.string.repeat, (dialog, id) -> {
                    if (listener != null) {
                        dismiss();
                        listener.onClick();
                    }
                });
        return builder.create();
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }
}
