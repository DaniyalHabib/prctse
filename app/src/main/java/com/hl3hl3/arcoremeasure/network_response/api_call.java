package com.hl3hl3.arcoremeasure.network_response;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.hl3hl3.arcoremeasure.model.admin.p_status;
import com.model.user_login;
import com.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class api_call {


    private static final String tag = api_call.class.getName();


    public RecyclerView.Adapter adapter;
    public Context ctx;
    public p_status status_;
    public ArrayList<p_status> status_list;
    public RecyclerView mList;
    public LinearLayoutManager linearLayoutManager;
    public DividerItemDecoration dividerItemDecoration;


    public ArrayList<p_status> getParking_Status(Context context) {

        final ProgressDialog progressDialog = new ProgressDialog(context   );
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        status_list = new ArrayList<>();
        user_login user_login =  SharedPrefManager.getInstance(context).getUser();
        String user_id = user_login.getCustomerId();

        String url = "http://adstracking.softtricks.net/Webservice/getUserJob?user_id="+user_id;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(tag, "jobs_avalible" + response.toString());

                for (int i = 0; i < response.length(); i++) {

                    try {

                        mList.setVisibility(View.VISIBLE);
                        JSONObject jsonObject = response.getJSONObject(i);
//                        example_model_ = new Example(jsonObject);
//                        compaignRouts__model.add(example_model_);
                        adapter.notifyDataSetChanged();
                        Log.d(tag, "Example_modelClass" + status_.toString());


                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Volley", error.toString());
                progressDialog.dismiss();
//                txt_no_jobs.setVisibility(View.VISIBLE);
                mList.setVisibility(View.GONE);
                NetworkResponse response = error.networkResponse;

                if (error instanceof ServerError && response != null) {

                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        Log.d(tag, "String job_avalble" + error);
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string

                        Log.d(tag, "catch 1job_avalble" + error);
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?

                        Log.d(tag, "catch 2job_avalble" + error);
                        e2.printStackTrace();

                    }
                }
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);

//        return compaignRouts__model ;
        return status_list;

    }
}
