package com.hl3hl3.arcoremeasure;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.hl3hl3.arcoremeasure.adapter.admin.detail_adapter;
import com.hl3hl3.arcoremeasure.model.admin.p_status;
import com.hl3hl3.arcoremeasure.network_response.api_call;
import com.model.user_login;
import com.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Status_details extends AppCompatActivity {


    private static final String tag = Status_details.class.getName();
    public RecyclerView.Adapter adapter;
    public Context ctx;
    public p_status status_;
    public ArrayList<p_status> status_list;
    public RecyclerView mList;
    public LinearLayoutManager linearLayoutManager;
    public DividerItemDecoration dividerItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_details);

        mList = findViewById(R.id.main_list);
        ctx = this;
        status_list = new ArrayList<>();

        adapter = new detail_adapter(ctx,  status_list,  new detail_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(p_status item) {

            }
        });


        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());
        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
//        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);
//      api_call call_netwrok_Service = new api_call();
//      status_list =  call_netwrok_Service.getParking_Status(ctx);

        getData();
    }

    private void getData() {

        final ProgressDialog progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
//        status_list = new ArrayList<>();
        user_login user_login =  SharedPrefManager.getInstance(ctx).getUser();
        String user_id = user_login.getCustomerId();

        String url = "http://test.xolva.com/smart_parking/get_parking_details.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d(tag, "jobs_avalible" + response.toString());

                for (int i = 0; i < response.length(); i++) {

                    try {

                        mList.setVisibility(View.VISIBLE);
                        JSONObject jsonObject = response.getJSONObject(i);
                        status_ = new p_status(jsonObject);
                        status_list.add(status_);
                        adapter.notifyDataSetChanged();
                        Log.d(tag, "Example_modelClass" + response.getJSONObject(i));


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


        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(jsonArrayRequest);

    }


}
