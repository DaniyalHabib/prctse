package com.hl3hl3.arcoremeasure.renderer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hl3hl3.arcoremeasure.LoginActivity;
import com.hl3hl3.arcoremeasure.R;
import com.hl3hl3.arcoremeasure.Signup;
import com.hl3hl3.arcoremeasure.dashboard_admin;
import com.hl3hl3.arcoremeasure.dashboard_pm;
import com.singleton.VolleySingleton;
import com.utils.NetworkUtil;
import com.utils.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class pm_signup extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = pm_signup.class.getName();
    EditText pm_name, pm_email, pm_pswd, pm_mobile_no, pm_nic, pm_adres;
    Button sign_up_btn;
    public static String admin_id;
    String p_name, p_email, p_pswd, p_adres, p_moble, p_nic;
    private RequestQueue mrequestQueue;
    Context mContext;
    ImageView mImgCheck;
    ScrollView scroll;
    private View mProgressView;
    Animation animFadein, animslideup;
    AnimationSet s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pm_signup);
        mContext = this;
        initView();
        sign_up_btn.setOnClickListener(this);

        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        animslideup = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);
         s = new AnimationSet(true);
    }

    private void initView() {

        pm_name = (EditText) findViewById(R.id.pm_name);
        pm_email = (EditText) findViewById(R.id.pm_email);
        pm_pswd = (EditText) findViewById(R.id.pm_pswd);
        pm_mobile_no = (EditText) findViewById(R.id.pm_mobile_no);
        pm_nic = (EditText) findViewById(R.id.pm_nic);
        pm_adres = (EditText) findViewById(R.id.pm_adres);
        sign_up_btn = (Button) findViewById(R.id.sign_up_btn);
        scroll = (ScrollView) findViewById(R.id.scroll);
        mImgCheck = (ImageView) findViewById(R.id.imageView);
        mProgressView = findViewById(R.id.login_progress);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.sign_up_btn:

                p_name = pm_name.getText().toString();
                p_adres = pm_adres.getText().toString();
                p_moble = pm_mobile_no.getText().toString();
                p_nic = pm_nic.getText().toString();
                p_email = pm_email.getText().toString();
                p_pswd = pm_pswd.getText().toString();


                if (p_name.matches("") || p_name.isEmpty()) {
                    pm_name.setError("Please enter a name first.! ");
                }
                else if (p_adres.matches("") || p_adres.isEmpty()) {
                    pm_adres.setError("Please enter address first!.");
                }
                else if (p_moble.matches("") || p_moble.isEmpty()) {
                    pm_mobile_no.setError("Please enter mobile number fist .!");
                }
                else if(pm_mobile_no.getText().length()< 11){
                    pm_mobile_no.setError("Please enter complete didit .!");
                }
                else if (p_nic.matches("") || p_nic.isEmpty()) {
                    pm_nic.setError("Please enter nic number fist.!");
                }
                else if (pm_nic.getText().length() < 13 ) {
                    pm_nic.setError("Please complete  nic digit fist.!");
                }
                else if (p_email.matches("") || p_email.isEmpty()) {
                    pm_email.setError("Please enter a email name .!");
                }
                else if (p_pswd.isEmpty() || p_pswd.matches("")) {
                    pm_pswd.setError("Please enter password first.!");
                }
                else {
                    mProgressView.setVisibility(View.VISIBLE);
                    if (NetworkUtil.isConnected(mContext)) {

                        admin_id = SharedPrefManager.getInstance(getApplicationContext()).getUser().getCustomerId();
                        String urlAddress = "http://test.xolva.com/smart_parking/admin/add_parking_mnger.php?admin_id=" + admin_id + "&pm_name=" + p_name + "&pm_email" + p_email + "&pm_pswd" + p_pswd + "&pm_mobile_no" + p_moble + "&pm_nic_no" + p_nic + "&pm_address" + p_adres;
                        mrequestQueue = Volley.newRequestQueue(mContext);
                        //if everything is fine
                        StringRequest jobReq = new StringRequest(Request.Method.POST, urlAddress, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String jsonObject) {

                                try {

                                    JSONObject obj = null;
                                    obj = new JSONObject(String.valueOf(jsonObject));
                                    Log.d(TAG, "json_response" + jsonObject);

                                    JSONObject userJson = obj.getJSONObject("info");
                                    String msg = userJson.getString("msg");
                                    Log.d(TAG, "jsobject" + msg);
                                    if (msg.equals("sucessfull register.. parking manager")) {

                                        sign_up_btn.setVisibility(View.INVISIBLE);
                                        scroll.setVisibility(View.INVISIBLE);
                                        mProgressView.setVisibility(View.INVISIBLE);
                                        mImgCheck.setVisibility(View.VISIBLE);
                                        s.setInterpolator(new AccelerateInterpolator());
                                        s.addAnimation(animFadein);
                                        mImgCheck.startAnimation(s);
                                        new Handler().postDelayed(new Runnable() {

                                            @Override
                                            public void run() {
                                                ((Animatable) mImgCheck.getDrawable()).start();
                                                startActivity(new Intent(getApplicationContext(), dashboard_admin.class));
                                                Toast.makeText(mContext, "Sucessfull Registrartion", Toast.LENGTH_SHORT).show();
                                            }
                                        }, 3500);



                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e(TAG, "error_" + e.toString());
                                    Toast.makeText(mContext, "Something is Wrong Please try Again.." + e.toString(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), "Response Error" + error, Toast.LENGTH_SHORT).show();
                                        Log.e(TAG, "Volley_Error" + error.toString());
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("admin_id", admin_id);
                                params.put("pm_name", p_name);
                                params.put("pm_email", p_email);
                                params.put("pm_pswd", p_pswd);
                                params.put("pm_mobile_no", p_moble);
                                params.put("pm_nic_no", p_nic);
                                params.put("pm_address", p_adres);

                                return params;
                            }
                        };

                        VolleySingleton.getInstance(pm_signup.this).addToRequestQueue(jobReq);
                        mrequestQueue.add(jobReq);

                    } else {
                        Toast.makeText(pm_signup.this, "Service Provide is not available ", Toast.LENGTH_SHORT).show();


                    }
                }
            }
        }
    }

