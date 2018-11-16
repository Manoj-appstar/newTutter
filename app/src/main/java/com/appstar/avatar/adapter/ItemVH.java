package com.appstar.avatar.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class ItemVH extends RecyclerView.ViewHolder implements com.appstar.avatar.utils.ItemTouchHelperViewHolder {


    public ItemVH(View itemView) {
        super(itemView);
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
