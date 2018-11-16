package com.appstar.tutionportal.student.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryResponse implements Serializable {

//    @SerializedName("data")
//    public String data;

    @SerializedName("data")
    @Expose
    private ArrayList<CategoryModel> arrayList = new ArrayList<>();

    public ArrayList<CategoryModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<CategoryModel> arrayList) {
        this.arrayList = arrayList;
    }

    public class CategoryModel {

        @SerializedName("category_id")
        @Expose
        private String id;

        @SerializedName("category_name")
        @Expose
        private String name;

        @SerializedName("image")
        @Expose
        private String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

//
//    public CategoryResponse(String id, String name, String image) {
//        this.id = id;
//        this.name = name;
//        this.image = image;
//    }
//
//    public CategoryResponse(String resp) {
//
//        try {
//
//            JSONObject jo = new JSONObject(resp);
//            JSONArray ja = jo.getJSONArray("data");
//
//            arrayList = new ArrayList<>();
//
//            for (int i = 0; i < ja.length(); i++) {
//
//                JSONObject jo1 = ja.getJSONObject(i);
//                arrayList.add(new CategoryResponse(jo1.getString("category_id"),
//                        jo1.getString("category_name"),
//                        jo1.getString("image")));
//
//            }
//
//            setArrayList(arrayList);
//
//        } catch (Exception e) {
////            Utils.log("Category Parse Exc", "::::    " + e.getMessage());
//        }
//
//    }
//
//    public ArrayList<CategoryResponse> getArrayList() {
//        return arrayList;
//    }
//
//    public void setArrayList(ArrayList<CategoryResponse> arrayList) {
//        this.arrayList = arrayList;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }

//    public boolean isSelected() {
//        return isSelected;
//    }
//
//    public void setSelected(boolean selected) {
//        isSelected = selected;
//    }

}
