package com.appstar.tutionportal.institute.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appstar.tutionportal.Model.Branch;
import com.appstar.tutionportal.Model.Institute;
import com.appstar.tutionportal.R;
import com.appstar.tutionportal.institute.activities.AddInstituteBranch;
import com.appstar.tutionportal.institute.fragments.ActivityBranchClass;
import com.appstar.tutionportal.institute.fragments.FragmentInstitute;
import com.appstar.tutionportal.teacher.activities.AddClasses;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class ExpandableAdapter extends BaseExpandableListAdapter {
    List<Institute> instituteList;
    Activity context;
    FragmentInstitute obj;
    List<String> stringList = new ArrayList<>();
    int counter = 0;

    public ExpandableAdapter(FragmentInstitute institute, Activity context, List<Institute> sList) {
        obj = institute;
        this.context = context;
        instituteList = sList;
        addList();
    }

    private void addList() {
        stringList.add("https://upload.wikimedia.org/wikipedia/en/8/8d/McKnight_Brain_Institute.JPG");
        stringList.add("http://com-psychiatry-dpi.sites.medinfo.ufl.edu/files/2012/09/BrainInstitute-Large.jpg");
        stringList.add("http://www.kjit.org/upload/images/ayurved%20college%20building%20photo.jpg");
        stringList.add("https://www.laserspineinstitute.com/assets/img/locations/scottsdale/facility-sm.jpg");
        stringList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR8p29UwhDoX368yQ_22ZnFyHywbi-WR_QEboLmNdTcGv4mXnAI5A");
        stringList.add("https://schoolbook.getmyuni.com/assets/images/rev_img/55158__27840/1514468367Jacob_School_of_Biotechnology_and_Bioengineering_AJ.jpg");
        stringList.add("https://www.iitr.ac.in/institute/uploads/Image/121004011141_iitroorkee.jpg");
        stringList.add("http://photos.wikimapia.org/p/00/01/49/50/35_big.jpg");
        stringList.add("https://static-collegedunia.com/public/college_data/images/logos/1522307712LOGO.png");
        stringList.add("https://upload.wikimedia.org/wikipedia/en/thumb/7/71/UC-CUHK-Logo.svg/1200px-UC-CUHK-Logo.svg.png");
        stringList.add("https://static-collegedunia.com/public/college_data/images/appImage/28862_UCER_APP.jpg");
        stringList.add("https://static-collegedunia.com/public/college_data/images/appImage/15034700001443431058ALLAHABADUNIVERSITYNEW.jpg");
    }

    @Override
    public int getGroupCount() {
        return instituteList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return instituteList.get(i).getBranches().size();
    }

    @Override
    public Institute getGroup(int i) {
        return instituteList.get(i);
    }

    @Override
    public Branch getChild(int i, int i1) {
        return instituteList.get(i).getBranches().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return Long.valueOf(instituteList.get(i).getInstituteId());
    }

    @Override
    public long getChildId(int i, int i1) {
        return Long.valueOf(instituteList.get(i).getBranches().get(i1).getBranchId());
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpand, View view, ViewGroup viewGroup) {
        final ViewInstituteHolder holder;
        Institute institute = instituteList.get(groupPosition);
        if (view == null) {
            holder = new ViewInstituteHolder();
            view = LayoutInflater.from(context).inflate(R.layout.institute_item, null);
            holder.tvViewBranches = view.findViewById(R.id.tvViewBranches);
            holder.tvInstituteName = view.findViewById(R.id.tvInstituteName);
            holder.tvPhone = view.findViewById(R.id.tvPhone);
            holder.tvBranchCount = view.findViewById(R.id.tvBranchCount);
            holder.imgIndicator = view.findViewById(R.id.imgIndicator);
            holder.imgInstitutePic = view.findViewById(R.id.imgInstitutePic);
            holder.imgAddBranch = view.findViewById(R.id.imgAddBranch);
            holder.tvBranchCount = view.findViewById(R.id.tvBranchCount);
            view.setTag(holder);
        } else
            holder = (ViewInstituteHolder) view.getTag();

        holder.tvInstituteName.setText(institute.getName());
        holder.tvPhone.setText("Phone: +91 " + institute.getPhoneNo());
        holder.tvBranchCount.setText("Total Branch: " + institute.getBranches().size());

        if (institute.getBranches().size() <= 0) {
            holder.tvViewBranches.setText("No branch added");
        } else
            holder.tvViewBranches.setText("View Branches");

        holder.imgInstitutePic.setImageResource(R.mipmap.ic_launcher);
        // Glide.with(context).load(institute.getImage()).apply(RequestOptions.placeholderOf(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)).into(holder.imgInstitutePic);
        clickInstituteView(institute, holder.imgAddBranch, holder.tvViewBranches);
        if (counter > 11)
            counter = 0;

        Glide.with(context).load(stringList.get(counter)).apply(RequestOptions.placeholderOf(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)).into(holder.imgInstitutePic);
        counter++;
        if (isExpand) {
            holder.imgIndicator.setImageResource(R.drawable.ic_arrow_group_bottom);
        } else {
            holder.imgIndicator.setImageResource(R.drawable.ic_arrow_group_left);
        }

        return view;
    }


    private void clickInstituteView(final Institute institute, ImageView imgAddBranch, TextView tvViewBranches) {
        tvViewBranches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (institute.getBranches().size() > 0) {

                }
            }
        });
        imgAddBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddInstituteBranch.class);
                intent.putExtra("institute_id", institute.getInstituteId());
                intent.putExtra("institute_name", institute.getName());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        final ViewBranchHolder holder;
        Branch branch = instituteList.get(groupPosition).getBranches().get(childPosition);
        branch.setInstituteName(instituteList.get(groupPosition).getName());
        if (view == null) {
            holder = new ViewBranchHolder();
            view = LayoutInflater.from(context).inflate(R.layout.institute_branch_item, null);
            holder.tvBranchName = view.findViewById(R.id.tvBranchName);
            holder.tvBranchPhone = view.findViewById(R.id.tvBranchPhone);
            holder.tvBranchLocation = view.findViewById(R.id.tvBranchLocation);
            holder.tvViewClass = view.findViewById(R.id.tvViewClass);
            holder.llViewMap = view.findViewById(R.id.llViewMap);
            holder.cvAddClass = view.findViewById(R.id.cvAddClass);
            view.setTag(holder);
        } else
            holder = (ViewBranchHolder) view.getTag();

        holder.tvBranchName.setText(branch.getName());
        holder.tvBranchPhone.setText(branch.getPhoneNo());
        holder.tvBranchLocation.setText("Address: " + branch.getAddress());
        branchClick(branch, holder.llViewMap, holder.cvAddClass, holder.tvViewClass);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    private void branchClick(final Branch branch, LinearLayout llViewMap, CardView cvAddClass, TextView tvViewClass) {
        llViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        cvAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddClasses.class);
                intent.putExtra("obj", branch);
                intent.putExtra("from", "institute");
                context.startActivity(intent);
            }
        });

        tvViewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityBranchClass.class);
                intent.putExtra("obj", branch);
                context.startActivity(intent);
            }
        });
    }

    class ViewInstituteHolder {
        TextView tvViewBranches, tvInstituteName, tvPhone, tvBranchCount;
        ImageView imgIndicator, imgInstitutePic, imgAddBranch;
    }

    class ViewBranchHolder {
        TextView tvBranchName, tvBranchPhone, tvBranchLocation, tvViewClass;
        LinearLayout llViewMap;
        CardView cvAddClass;
    }
}
