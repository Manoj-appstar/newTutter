package com.appstar.tutionportal.student.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.appstar.tutionportal.R;
import com.appstar.tutionportal.student.fragment.AddClass;
import com.appstar.tutionportal.teacher.activities.AddClasses;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class AddImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity context;
    private List<String> listItemsList2;
    private int focusedItem = 0;

    public AddImageAdapter
            (Activity context, List<String> listItemsList2) {
        this.listItemsList2 = listItemsList2;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dynamic_add_image, null);
        ListRowViewHolder holder = new ListRowViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String adviSorName = listItemsList2.get(position);
        ListRowViewHolder holder2 = (ListRowViewHolder) holder;
        holder.itemView.setSelected(focusedItem == position);
        holder.getLayoutPosition();
        Glide.with(context).load(new File(adviSorName)).into(holder2.image);
        //MainActivity.mRecyclerView.smoothScrollToPosition(position);


    }

    public void delete(int position) {
        listItemsList2.remove(position);
        notifyDataSetChanged();
      //  AddClasses.aAdd.setText(listItemsList2.size() <= 0 ? "Add Image" : "Add More Image");
      /*  if (context instanceof AddClasses)
            ((AddClasses)(context)).onRemoveImage();*/
    }
    @Override
    public int getItemCount() {
        return (null != listItemsList2 ? listItemsList2.size() : 0);
        }

    public class ListRowViewHolder extends RecyclerView.ViewHolder {
        private static final int SELECT_PICTURE = 100;
        protected TextView txt_add_image, email, date;
        ImageButton remove_image;
        ImageView image;

        public ListRowViewHolder(View view) {
            super(view);
            this.image = view.findViewById(R.id.add_product);
            this.remove_image = view.findViewById(R.id.remove_item);
            remove_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete(getAdapterPosition());
                }
            });

            this.txt_add_image = view.findViewById(R.id.txt_add_image);
        /*    AddClasses.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openImageChooser();
                }
            });*/

        }

        public void openImageChooser() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            (context).startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
        }
    }
}
