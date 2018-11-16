package com.appstar.tutionportal.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.appstar.tutionportal.util.Utils;

public class DialogError {


    public static void setMessage(Activity context, String message) {

// ...Irrelevant code for customizing the buttons and title
        try {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setMessage(message);
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog dialog = builder1.create();

            dialog.setCancelable(false);
            if (!dialog.isShowing())
                dialog.show();
            dialog.setCancelable(true);
        } catch (Exception e) {
            Utils.log("progress Dialog Exc", "::::    " + e.getMessage());
        }
    }

    public static void setMessage(Activity context, String text, String message, boolean cancelable, final Runnable runnable) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message);
            builder.setCancelable(true);
            builder.setPositiveButton(
                    text,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            runnable.run();
                            dialog.cancel();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.setCancelable(cancelable);

            if (!dialog.isShowing())
                dialog.show();

        } catch (Exception e) {
            Utils.log("progress Dialog Exc", "::::    " + e.getMessage());
        }
    }

    public static void setMessage(Activity context, String message, String btnCancel, String btnOk, boolean cancelable, final Runnable runnable) {
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message);
            builder.setCancelable(true);
            final AlertDialog dialog = builder.create();
           /* builder.setPositiveButton(
                    btnOk,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            runnable.run();
                        }
                    });
            builder.setNegativeButton(btnCancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog.dismiss();
                }
            });*/
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, btnCancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog.cancel();
                }
            });
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, btnOk, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    runnable.run();
                }
            });
            dialog.setCancelable(cancelable);

            if (!dialog.isShowing())
                dialog.show();

        } catch (Exception e) {
            Utils.log("progress Dialog Exc", "::::    " + e.getMessage());
        }
    }
}
