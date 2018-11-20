package com.appstar.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.login.ChooseScreen;
import com.appstar.tutionportal.util.SharePreferenceData;

public class Fragment4 extends Fragment {
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    CardView cvStart;
    private static SharePreferenceData sharePreferenceData;

    public static final Fragment4 newInstance(String message) {
        Fragment4 f = new Fragment4();
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.splash_fragment4, container, false);
        sharePreferenceData= new SharePreferenceData();
        cvStart = v.findViewById(R.id.cvStart);
        cvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePreferenceData.setUserLogInFirst(getActivity(),false);
                Intent intent = new Intent(getActivity(), ChooseScreen.class);
                startActivity(intent);
            }
        });
        return v;
    }
}
