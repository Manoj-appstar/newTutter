package com.appstar.tutionportal.student.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.extras.FragmentNames;
import com.appstar.tutionportal.util.SharePreferenceData;
import com.appstar.tutionportal.util.UtilsStudent;

public class FragmentQuestion extends Fragment {

    TextView tvClassName;
    ImageView ivBack;
    String data,data1;
    String[] array;
    TextView tvQuestion, tvAdvice, tvAnswer;
    private UtilsStudent utilsStudent;
    private Activity mActivity;
    private SharePreferenceData sharePreferenceData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_question_support, container, false);
        utilsStudent = new UtilsStudent();
        mActivity = getActivity();
        sharePreferenceData = new SharePreferenceData();
        findView(view);
        onClick();
        Bundle extras = getArguments();
        if (extras != null) {
            data = extras.getString("data");
            data1 = extras.getString("data1");
            tvQuestion.setText(data);
        }
        if (data.equalsIgnoreCase("I want to register a teacher with Tutter")) {
            tvAdvice.setText("To register with Tutter,Kindly follow this link and fill in the required details.");
        } else if (data.equalsIgnoreCase("can i get my favorite teacher listed with Tutter")) {
            tvAdvice.setText("you can write to us at");
            tvAnswer.setVisibility(View.GONE);
        } else if (data.equalsIgnoreCase("What are the payment options available to me?")) {
            tvAdvice.setText("Paytm");
            tvAnswer.setVisibility(View.GONE);
        } else if (data.equalsIgnoreCase("What is the average refund procesing time?")) {
            tvAdvice.setText("48 hours");
            tvAnswer.setVisibility(View.GONE);
        }
        return view;
    }

    private void findView(View view) {
        tvClassName = view.findViewById(R.id.tvClassName);
        ivBack = view.findViewById(R.id.ivBack);
        tvClassName.setText("Learn");
        tvQuestion = view.findViewById(R.id.tvQuestion);
        tvAdvice = view.findViewById(R.id.tvAdvice);
        tvAnswer = view.findViewById(R.id.tvAnswer);
    }

    private void onClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("data", data1);
                utilsStudent.openFragment(mActivity, FragmentNames.STUDENT_LISTING_SUPPORT, FragmentNames._STUDENT_LISTING_SUPPORT, bundle, true);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        UtilsStudent.setCurrentScreen(FragmentNames._STUDENT_QUESTION);
    }
}
