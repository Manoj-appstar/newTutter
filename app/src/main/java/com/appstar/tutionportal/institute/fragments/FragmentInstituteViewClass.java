package com.appstar.tutionportal.institute.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.institute.activities.AddInstituteBranch;
import com.appstar.tutionportal.institute.activities.AddInstituteClass;
import com.appstar.tutionportal.institute.adapter.PagerHomeAdapter;

public class FragmentInstituteViewClass extends Fragment {
    View view;
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerHomeAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_institute_class, container, false);
        this.view = view;
        initView();
        setUpField();
        onClick();
        return view;
    }


    private void initView() {
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(0);
    }

    private void setUpField() {
        adapter = new PagerHomeAdapter(getActivity(), getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void onClick() {
        view.findViewById(R.id.btnAddClass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), AddInstituteClass.class));

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        notifySetChange();
    }
    private void notifySetChange() {
        if (adapter != null)
            adapter.notifyDataSetChanged();

    }
}
