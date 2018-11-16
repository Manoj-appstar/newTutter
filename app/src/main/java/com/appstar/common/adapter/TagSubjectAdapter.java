package com.appstar.common.adapter;

import android.util.Log;
import android.view.View;

import com.appstar.tutionportal.Model.Subject;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class TagSubjectAdapter<T> {
    private List<T> mTagDatas;
    private TagSubjectAdapter.OnDataChangedListener mOnDataChangedListener;
    @Deprecated
    private HashSet<Integer> mCheckedPosList = new HashSet<Integer>();

    public TagSubjectAdapter(List<T> datas) {
        mTagDatas = datas;
    }

    @Deprecated
    public TagSubjectAdapter(T[] datas) {
        mTagDatas = new ArrayList<T>(Arrays.asList(datas));
    }

    interface OnDataChangedListener {
        void onChanged();
    }

    void setOnDataChangedListener(TagSubjectAdapter.OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    @Deprecated
    public void setSelectedList(int... poses) {
        Set<Integer> set = new HashSet<>();
        for (int pos : poses) {
            set.add(pos);
        }
        setSelectedList(set);
    }

    @Deprecated
    public void setSelectedList(Set<Integer> set) {
        mCheckedPosList.clear();
        if (set != null) {
            mCheckedPosList.addAll(set);
        }
        notifyDataChanged();
    }

    @Deprecated
    HashSet<Integer> getPreCheckedList() {
        return mCheckedPosList;
    }


    public int getCount() {
        return mTagDatas == null ? 0 : mTagDatas.size();
    }

    public void notifyDataChanged() {
        if (mOnDataChangedListener != null)
            mOnDataChangedListener.onChanged();
    }

    public T getItem(int position) {
        return mTagDatas.get(position);
    }

    public abstract View getView(FlowLayout parent, int position, T t);


    public void onSelected(int position, View view) {
        Log.d("zhy", "onSelected " + position);
    }

    public void unSelected(int position, View view) {
        Log.d("zhy", "unSelected " + position);
    }

    public boolean setSelected(int position, T t) {
        return false;
    }
}