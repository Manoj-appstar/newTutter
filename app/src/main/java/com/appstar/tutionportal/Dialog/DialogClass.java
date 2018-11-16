package com.appstar.tutionportal.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.Utils;
import com.appstar.tutionportal.volley.RequestServer;

public class DialogClass {
    View dialogView;
    EditText etPhone;
    Button btnChangePhone;
    LinearLayout llVerifyOtp, llChangePhone;
    TextView tvText;
    Dialog dialog;
    RequestServer requestServer;
    SharePreferenceData sharePreferenceData;
    TextView tvResend, tvTimer;
    int second, minute = 0;
    int counter = 59;
    Runnable runnable;
    boolean isResendDisabled = true;
    Handler handler = new Handler();
    TextView tvIndividualClass, tvInsitituteClass;
    private Activity mActivity;
    private AlertDialog alertDialog;
    private Utils utils;
    CardView cvCardIndividual,cvCardInstitute;

    public void showDialog(Activity context) {

// ...Irrelevant code for customizing the buttons and title
        try {

            sharePreferenceData = new SharePreferenceData();
            utils = new Utils();

            dialog = new Dialog(context);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow()
                    .getAttributes().windowAnimations = R.style.DialogAnimation;

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_dialogclass);
            tvIndividualClass = dialog.findViewById(R.id.tvIndividualClass);
            tvInsitituteClass = dialog.findViewById(R.id.tvInsitituteClass);
            cvCardIndividual = dialog.findViewById(R.id.cvCardIndividual);
            cvCardInstitute = dialog.findViewById(R.id.cvCardInstitute);
            tvIndividualClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Individual");
                    utils.openFragment(mActivity, FragmentNames.ADD_Class, FragmentNames._ADD_CLASS, bundle, false);
                }
            });
            tvInsitituteClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Institute");
                    utils.openFragment(mActivity, FragmentNames.ADD_Class, FragmentNames._ADD_CLASS, bundle, false);
                }
            });

            cvCardIndividual.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Individual");
                    utils.openFragment(mActivity, FragmentNames.ADD_Class, FragmentNames._ADD_CLASS, bundle, false);
                }
            });
            cvCardInstitute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Institute");
                    utils.openFragment(mActivity, FragmentNames.ADD_Class, FragmentNames._ADD_CLASS, bundle, false);
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
                llChangePhone.setVisibility(View.VISIBLE);
                llVerifyOtp.setVisibility(View.GONE);
                tvText.setText("Your Phone Number");
                btnChangePhone.setText("Change Phone No.");
                tvTimer.setVisibility(View.GONE);
                tvResend.setVisibility(View.GONE);
                dialog.dismiss();
            }
        } catch (Exception e) {
            Utils.log("progress Dialod Exc", "::::    " + e.getMessage());
        }
    }
}
