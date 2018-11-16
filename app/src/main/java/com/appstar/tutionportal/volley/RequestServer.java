package com.appstar.tutionportal.volley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.appstar.tutionportal.student.interfaces.OnResponseListener;
import com.appstar.tutionportal.util.SharePreferenceData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by Manoj on 08/08/18.
 */

public class RequestServer {
    static com.android.volley.RequestQueue requestQueue;
    public boolean isShowDialog;
    String url;
    OnResponseListener listener;
    int reqType;
    ProgressDialog progressDialog;
    Activity context;
    int socketTimeout = 40000;// 40 sec


    public RequestServer(Activity context, OnResponseListener listener) {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context);
        this.listener = listener;
        this.context = context;

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
    }


    public void postRequest(String url, JSONObject jsonObject, final int reqType, boolean isShowDialog) {
        this.reqType = reqType;
        this.isShowDialog = isShowDialog;
        if (isShowDialog && !progressDialog.isShowing()) {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onSuccess(reqType, response.toString());
                if (progressDialog.isShowing())
                    progressDialog.cancel();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailed(reqType, error.getMessage());
                if (progressDialog.isShowing())
                    progressDialog.cancel();
            }
        });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyLog.DEBUG = true;
        requestQueue.add(jsonObjReq);
    }

    public void getRequest(String url, final int reqType, boolean isShowDialog) {
        this.reqType = reqType;
        this.isShowDialog = isShowDialog;
        if (isShowDialog && !progressDialog.isShowing()) {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onSuccess(reqType, response.toString());
                if (progressDialog.isShowing())
                    progressDialog.cancel();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (progressDialog.isShowing())
                    progressDialog.cancel();
                listener.onFailed(reqType, error.getMessage());
            }
        });
        requestQueue.add(jsonObjReq);


    }

    public com.android.volley.RequestQueue getRequestQueue() {

        return requestQueue;
    }

    public void sendStringPost(String url, final JSONObject jsonObject, final int reqType, boolean isShowDialog) {
        this.reqType = reqType;
        this.isShowDialog = isShowDialog;
        if (isShowDialog && !progressDialog.isShowing()) {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(reqType, response.toString());
                if (progressDialog.isShowing())
                    progressDialog.cancel();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailed(reqType, error.getMessage());
                if (progressDialog.isShowing())
                    progressDialog.cancel();
            }
        }) {
            //use this if you have to use form posting : for application/x-www-form-urlencoded;
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                try {
                    params = toMap(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("content-type", "application/x-www-form-urlencoded");
//                params.put("content-type", "application/json");
                return params;
            }
        };
        //40 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        // mRequestQueue.add(request);
        requestQueue.add(request);

    }

    public void sendStringPostWithHeader(String url, final JSONObject jsonObject, final int reqType, boolean isShowDialog) {
        this.reqType = reqType;
        this.isShowDialog = isShowDialog;
        if (isShowDialog && !progressDialog.isShowing()) {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(reqType, response.toString());
                if (progressDialog.isShowing())
                    progressDialog.cancel();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailed(reqType, error.getMessage());
                if (progressDialog.isShowing())
                    progressDialog.cancel();
            }
        }) {
            //use this if you have to use form posting : for application/x-www-form-urlencoded;
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                try {
                    params = toMap(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("content-type", "application/x-www-form-urlencoded");
//                params.put("user_token", SharePreferenceData.getToken(context));
                params.put("user_token", "abcd1234");
//                params.put("content-type", "application/json");
                return params;
            }
        };
        //40 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        // mRequestQueue.add(request);
        requestQueue.add(request);

    }


    public void sendStringPostText(String url, final JSONObject jsonObject, final int reqType, boolean isShowDialog) {
        this.reqType = reqType;
        this.isShowDialog = isShowDialog;
        if (isShowDialog && !progressDialog.isShowing()) {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(reqType, response.toString());
                if (progressDialog.isShowing())
                    progressDialog.cancel();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailed(reqType, error.getMessage());
                if (progressDialog.isShowing())
                    progressDialog.cancel();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("content-type", "application/x-www-form-urlencoded");
//                params.put("content-type", "application/json");
                return params;
            }

            //use this if you have to use form posting : for application/x-www-form-urlencoded;
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                try {
                    params = toMap(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return params;
            }
        };

        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        requestQueue.add(request);

    }


    public Map<String, String> toMap(JSONObject object) throws JSONException {
        Map<String, String> map = new HashMap<String, String>();
        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            String value = object.get(key).toString();
            map.put(key, value);
        }
        return map;
    }

    public List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    private void hideDialog() {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing())
                    progressDialog.cancel();

            }
        });

    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }

    public void uploadPostImage(final int reqCode, String selectedPath, int pid, String url) {
        this.isShowDialog = isShowDialog;
        if (isShowDialog && !progressDialog.isShowing()) {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }
        try {
            MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
            //    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(180, TimeUnit.SECONDS).readTimeout(180, TimeUnit.SECONDS).build();
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("pic", getFileName(selectedPath), RequestBody.create(MEDIA_TYPE_PNG, new File(selectedPath)))
                    .addFormDataPart("pid", String.valueOf(pid))

                    .build();
            okhttp3.Request request = new okhttp3.Request.Builder().url(url).post(body).build();
            //   Response response = client.newCall(request).execute();
            // String result = response.body().string();
            OkHttpClient client = new OkHttpClient.Builder().build();
            Call call = client.newCall(request);
            call.enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    listener.onFailed(reqCode, e.getMessage());
                    if (progressDialog.isShowing())
                        progressDialog.cancel();

                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    String result = response.body().string();
                    listener.onSuccess(reqCode, result);
                    if (progressDialog.isShowing())
                        progressDialog.cancel();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    public void uploadMultiDataWithImage(final int reqCode, String url, final String path, HashMap<String, String> mapValues, boolean isShowDialog) {

        try {
            this.isShowDialog = isShowDialog;
            if (isShowDialog && !progressDialog.isShowing()) {
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
            }
            MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

// OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(180, TimeUnit.SECONDS).readTimeout(180, TimeUnit.SECONDS).build();
            // MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);

            if (!TextUtils.isEmpty(path))
                builder.addFormDataPart("image", getFileName(path), RequestBody.create(MEDIA_TYPE_PNG, new File(path)));
            for (Map.Entry<String, String> entry : mapValues.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                builder.addFormDataPart(key, value);
            }
            RequestBody requestBody = builder.build();

       /*
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("user_image", getFileName(path), RequestBody.create(MEDIA_TYPE_PNG, new File(path)))
                    // .addFormDataPart("class_image", listItemsList.get(i))
                    .addFormDataPart("id", userId)
                    .build();*/
            okhttp3.Request request = new okhttp3.Request.Builder().url(url).post(requestBody).addHeader("user_token", SharePreferenceData.getToken(context)).build();
// Response response = client.newCall(request).execute();
// String result = response.body().string();
            OkHttpClient client = new OkHttpClient.Builder().build();
            Call call = client.newCall(request);
            call.enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    listener.onFailed(reqCode, e.getMessage());
                    if (progressDialog.isShowing())
                        progressDialog.cancel();
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    String result = response.body().string();
                    listener.onSuccess(reqCode, result);
                    if (progressDialog.isShowing())
                        progressDialog.cancel();

                }

            });


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void uploadClassImage(final int reqCode, String url, final List<String> paths, HashMap<String, String> mapValues, boolean isShowDialog) {

        try {
            this.isShowDialog = isShowDialog;
        //    if (isShowDialog && !progressDialog.isShowing()) {
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
      //      }
            MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

// OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(180, TimeUnit.SECONDS).readTimeout(180, TimeUnit.SECONDS).build();
            // MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);

            for (String path : paths) {
                if (!TextUtils.isEmpty(path))
                    builder.addFormDataPart("userfile[]", getFileName(path), RequestBody.create(MEDIA_TYPE_PNG, new File(path)));
            }
            for (Map.Entry<String, String> entry : mapValues.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                builder.addFormDataPart(key, value);
            }
            RequestBody requestBody = builder.build();

       /*RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("user_image", getFileName(path), RequestBody.create(MEDIA_TYPE_PNG, new File(path)))
                    // .addFormDataPart("class_image", listItemsList.get(i))
                    .addFormDataPart("id", userId)
                    .build();*/

            okhttp3.Request request = new okhttp3.Request.Builder().url(url).post(requestBody).addHeader("user_token", SharePreferenceData.getToken(context)).build();
// Response response = client.newCall(request).execute();
// String result = response.body().string();
            OkHttpClient client = new OkHttpClient.Builder().build();
            Call call = client.newCall(request);
            call.enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    listener.onFailed(reqCode, e.getMessage());
                    if (progressDialog.isShowing())
                        progressDialog.cancel();
                }
                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    String result = response.body().string();
                    listener.onSuccess(reqCode, result);
                    if (progressDialog.isShowing())
                        progressDialog.cancel();
                    }
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
