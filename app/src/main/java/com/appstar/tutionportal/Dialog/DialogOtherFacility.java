package com.appstar.tutionportal.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.volley.RequestServer;

public class DialogOtherFacility implements OnResponseListener {
    View dialogView;
    ImageView ivCross;
    Dialog dialog;
    int REQ_CLASS = 198;
    RequestServer requestServer;
    SharePreferenceData sharePreferenceData;
    TextView tvResend, tvTimer;
    int second, minute = 0;
    int counter = 59;
    Runnable runnable;
    boolean isResendDisabled = true;
    Handler handler = new Handler();
    private Activity mActivity;
    private AlertDialog alertDialog;

    public void showDialog(Activity context) {

// ...Irrelevant code for customizing the buttons and title
        try {

            requestServer = new RequestServer(context, this);
            sharePreferenceData = new SharePreferenceData();

            dialog = new Dialog(context);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow()
                    .getAttributes().windowAnimations = R.style.DialogAnimation;
// Include dialog.xml file
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_other_facility);
            ivCross = dialog.findViewById(R.id.ivCross);
            ivCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            dialog.setCancelable(false);

            if (!dialog.isShowing())
                dialog.show();
            dialog.setCancelable(true);
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

    @Override
    public void onSuccess(int reqCode, String response) {

    }

    @Override
    public void onFailed(int reqCode, String response) {

    }
}
