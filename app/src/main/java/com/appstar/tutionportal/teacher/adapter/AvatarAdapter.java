package com.appstar.tutionportal.teacher.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appstar.avatar.adapter.ItemVH;
import com.appstar.avatar.adapter.TinderRVAdapter;
import com.appstar.tutionportal.Model.IFinishDrag;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.teacher.activities.AddClasses;
import com.appstar.tutionportal.teacher.activities.ImageMover;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by athbk on 5/26/17.
 */

public class AvatarAdapter extends TinderRVAdapter<String, AvatarAdapter.AvatarVH> {

    List<Integer> listInt;
    List<String> listString;
    List<String> listData;
    Context context;

    boolean isMoveNext;
    IFinishDrag iFinishDrag;
    AddClasses obj;

    public AvatarAdapter(AddClasses obj, Context context, List<String> list) {
        this.obj = obj;
        this.listData = list;
        this.context = context;
    }

//    public AvatarAdapter(Context context, List<String> list) {
//        this.listString = list;
//        this.context = context;
//        listInt = new ArrayList<>();
//        for (int i=0; i< listString.size(); i++){
//            listInt.add(i);
//        }
//    }

    @Override
    protected List<String> getListItem() {
        return listData;
    }

    @Override
    protected AvatarVH onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new AvatarVH(view);
    }

    @Override
    protected void onBindItemViewHolder(final AvatarVH holder, final int position) {
//        holder.imageView.setImageResource(listString.get(position));

        if (TextUtils.isEmpty(listData.get(position))) {

            if (position == 0) {
                holder.tvNoData.setTextSize(13);
                holder.btnDelete.getLayoutParams().height = 70;
                holder.imgNoData.getLayoutParams().height = 70;
            } else {
                holder.tvNoData.setTextSize(12);
                holder.imgNoData.getLayoutParams().height = 40;
                holder.btnDelete.getLayoutParams().height = 40;
            }
            holder.llNoData.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.GONE);
            holder.imageView.setImageResource(R.drawable.ic_add);
            holder.btnDelete.setVisibility(View.GONE);
            holder.btnDelete.setImageResource(R.drawable.ic_delete_blue);
        } else {
            holder.llNoData.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);
            holder.imageView.setImageResource(R.drawable.ic_add);
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.btnDelete.setImageResource(R.drawable.ic_delete_blue);
            Glide.with(context).load(new File(listData.get(position))).into(holder.imageView);
        }
        holder.imgNoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (obj != null)
                    obj.openImageChooser(position);
                Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  listData.set(position,"");
                  notifyDataSetChanged();
            }
        });
        Log.e("TAG", "" + position);
        Log.e("IMAGE", "" + listData.get(position));
        //holder.tv.setText(""+listData.get(position).getStt());

        if (isMoveNext) {
            holder.imageView.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View view, DragEvent dragEvent) {
                    Intent intent = new Intent(context, ImageMover.class);
                    context.startActivity(intent);
                    return false;
                }
            });
        }


    }


    public void setiFinishDrag(IFinishDrag iFinishDrag) {
        this.iFinishDrag = iFinishDrag;
    }

    class AvatarVH extends ItemVH {

        LinearLayout llNoData;
        FrameLayout layout;
        ImageView imageView, imgNoData;
        ImageView btnDelete;
        TextView tv, tvNoData;
        CardView cvSelectImage;

        public AvatarVH(View itemView) {
            super(itemView);
            llNoData = itemView.findViewById(R.id.llNoData);
            cvSelectImage = itemView.findViewById(R.id.cvSelectImage);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imgNoData = itemView.findViewById(R.id.imgNoData);
            layout = (FrameLayout) itemView.findViewById(R.id.layout);
            tv = (TextView) itemView.findViewById(R.id.tv);
            tvNoData = itemView.findViewById(R.id.tvNoData);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnDelete.setVisibility(View.GONE);
            imgNoData.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
           /* btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemDismiss(getLayoutPosition());
                }
            });*/
        }

        @Override
        public void onItemSelected() {
            super.onItemSelected();
            layout.setAlpha(0.5f);
//            Log.e("DRAG", "DRAG");
        }


        @Override
        public void onItemClear() {
            super.onItemClear();
            layout.setAlpha(1f);
//            Log.e("FINISH DRAG", "FINISH DRAG");
            if (iFinishDrag != null) {
                iFinishDrag.updateListData((ArrayList<String>) getListItem());
            }

        }
    }
}
