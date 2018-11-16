package com.appstar.tutionportal.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.appstar.tutionportal.R;


public class ProgressUtil {

    private static ProgressUtil progressUtil;
    private Dialog dialog;

    public static ProgressUtil getInstance() {
        if (progressUtil == null) {
            progressUtil = new ProgressUtil();
        }
        return progressUtil;
    }

    public void showDialog(Context context) {
        try {
            if (dialog == null) {
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_progress_util);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setCancelable(false);
            }
            if (!dialog.isShowing())
                dialog.show();
        } catch (Exception e) {
            Utils.log("progress Dialod Exc", "::::    " + e.getMessage());
        }
    }

    public void dismiss() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            Utils.log("progress Dialod Exc", "::::    " + e.getMessage());
        }
    }

}
