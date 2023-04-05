package com.curuza.utils;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.curuza.R;

public class DialogUtils {

    public static void showErrorDialog(Context context, int stringResId) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();

        View dialogView = View.inflate(context, R.layout.error_dialog, null);
        TextView bodyText = dialogView.findViewById(R.id.body_text);
        bodyText.setText(stringResId);

        Button okButton = dialogView.findViewById(R.id.ok_button);
        okButton.setOnClickListener(v -> dialog.dismiss());

        dialog.setView(dialogView);
        dialog.show();
    }

    public static void showErrorDialog(Context context) {
        showErrorDialog(context, R.string.request_unsuccessful);
    }

    public static AlertDialog getProgressDialog(Context context, int stringResId) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        View dialogView = View.inflate(context, R.layout.progress_dialog, null);

        TextView dialogText = dialogView.findViewById(R.id.dialog_text);
        dialogText.setText(stringResId);

        dialog.setView(dialogView);
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    public static AlertDialog showProgressDialog(Context context, boolean isCancelable) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        View dialogView = View.inflate(context, R.layout.progress_dialog, null);
        dialog.setView(dialogView);
        dialog.setCanceledOnTouchOutside(isCancelable);
        dialog.show();

        return dialog;
    }
}
