package com.curuza.domain.common;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.curuza.R;

public class ProgressDialog extends AlertDialog {
    private TextView mDialogTextView;

    public ProgressDialog(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        View dialogView = View.inflate(context, R.layout.progress_dialog, null);
        setView(dialogView);
        setCanceledOnTouchOutside(false);

        mDialogTextView = dialogView.findViewById(R.id.dialog_text);
    }

    public void setText(int stringResId) {
        mDialogTextView.setText(stringResId);
    }
}
