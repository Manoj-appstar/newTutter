package com.appstar.avatar.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.appstar.tutionportal.R;

import java.util.List;

/**
 * Created by athbk on 5/26/17.
 */

public abstract class TinderRVAdapter<T, VH extends ItemVH> extends RecyclerView.Adapter<VH> implements com.appstar.avatar.utils.ItemTouchHelperAdapter {

    protected abstract List<T> getListItem();

    protected abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    protected abstract void onBindItemViewHolder(final VH holder, int position);

    private com.appstar.avatar.utils.OnStartDragListener mDragStartListener;

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateItemViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        onBindItemViewHolder(holder, position);
//        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
//                    mDragStartListener.onStartDrag(holder);
//                }
//                return false;
//            }
//        });

       /* holder.itemView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                mDragStartListener.onStartDrag(holder);
                return false;
            }
        });*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDragStartListener.onStartDrag(holder);
            }
        });
      /*  holder.btnDelete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mDragStartListener.onStartDrag(holder);
                return false;
            }
        });*/

    }

    @Override
    public int getItemCount() {
        if (getListItem() == null) return 0;
        return getListItem().size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (getListItem() == null) return false;
        Log.e("fromPosition: " + fromPosition, "toPosition: " + toPosition);
//        Collections.swap(getListItem(), fromPosition, toPosition);
        moveList(getListItem(), fromPosition, toPosition);
//        if (toPosition == 0 || toPosition == 1){
//            notifyDataSetChanged();
//        }
//        else {
        notifyItemMoved(fromPosition, toPosition);
//        }
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        if (getListItem() == null)
            return;
        getListItem().remove(position);
        notifyItemRemoved(position);
    }



    public void setDragStartListener(com.appstar.avatar.utils.OnStartDragListener mDragStartListener) {
        this.mDragStartListener = mDragStartListener;
    }

    public void moveList(List<T> list, int fromToPosition, int toPosition) {
        if (list == null || list.size() < fromToPosition + 1 || list.size() < toPosition + 1)
            return;

        T model = list.get(fromToPosition);
        list.remove(fromToPosition);
        list.add(toPosition, model);
    }
}
