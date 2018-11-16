package com.appstar.avatar;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;

import com.appstar.avatar.adapter.TinderRVAdapter;
import com.appstar.tutionportal.R;


/**
 * Created by athbk on 5/25/17.
 */

public class TinderRecyclerView extends RecyclerView {

    private final int DEFAULT_COLUMN = 3;
    private final int DEFAULT_SPAN_COLUMN = 2;
    private final float DEFAULT_CELLASPECTRATIO = 1f;

    private int column = DEFAULT_COLUMN;
    private int spanColumn = DEFAULT_SPAN_COLUMN;
    private float spectAtio = DEFAULT_CELLASPECTRATIO;

    private ItemTouchHelper mItemTouchHelper;
    boolean isMoveNext;

    public TinderRecyclerView(Context context) {
        super(context);
    }

    public TinderRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TinderRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TinderRecyclerView, defStyle, 0);
        column = ta.getInt(R.styleable.TinderRecyclerView_numberColoum, DEFAULT_COLUMN);
        spanColumn = ta.getInt(R.styleable.TinderRecyclerView_spanCount, DEFAULT_SPAN_COLUMN);
        spectAtio = ta.getFloat(R.styleable.TinderRecyclerView_aspectRatio, DEFAULT_CELLASPECTRATIO);
        ta.recycle();
    }

    public void initRecyclerView(Context context, TinderRVAdapter adapter) {
        com.appstar.avatar.utils.SpannedGridLayoutManager spannedGridLayoutManager = new com.appstar.avatar.utils.SpannedGridLayoutManager(context, new com.appstar.avatar.utils.SpannedGridLayoutManager.GridSpanLookup() {
            @Override
            public com.appstar.avatar.utils.SpannedGridLayoutManager.SpanInfo getSpanInfo(int position) {
                if (position == 0) {
                    return new com.appstar.avatar.utils.SpannedGridLayoutManager.SpanInfo(spanColumn, spanColumn);
                } else {
                    return new com.appstar.avatar.utils.SpannedGridLayoutManager.SpanInfo(1, 1);
                }
            }
        }, column, spectAtio);
        setLayoutManager(spannedGridLayoutManager);
        setAdapter(adapter);

        ItemTouchHelper.Callback callback = new com.appstar.avatar.utils.SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(this);

        adapter.setDragStartListener(new com.appstar.avatar.utils.OnStartDragListener() {
            @Override
            public void onStartDrag(ViewHolder viewHolder) {
                if (!isMoveNext)
                    mItemTouchHelper.startDrag(viewHolder);
            }
        });
    }

    public void setMoveNext(boolean bool) {
        this.isMoveNext = bool;
    }
}
