package com.hl3hl3.arcoremeasure;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.singleton.VolleySingleton;
import com.utils.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = Signup.class.getName();
    EditText et_name, cus_mobile_no, cus_login_id, cus_paswd;
    Button btn_submt;
    String c_name, c_mble_no, c_login_id, c_pswd, user_role;
    Context mContext;
    ScrollView scrollView;
    TextView alert_txt;
    private RequestQueue mrequestQueue;
    private RadioGroup radio_grup;
    private RadioButton radio_user_type;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();
        mContext = this;
        btn_submt.setOnClickListener(this);


        cus_mobile_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
                    if (cus_mobile_no.getText().toString().length() < 11)
                        cus_mobile_no.setError("In-Complete Phone Number");
                    else
                        cus_mobile_no.setError(null);
//                }
            }
        });
        cus_login_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
                String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
                Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(cus_login_id.getText().toString());
               if(cus_login_id.getText().toString() == (expression))
                   cus_login_id.setError("In-Valid Email address");
                else
                   cus_login_id.setError(null);
            }
        });
    }

    private void initView() {

        radio_grup = (RadioGroup) findViewById(R.id.radio_grup);
        alert_txt = (TextView) findViewById(R.id.alert_txt);
        et_name = (EditText) findViewById(R.id.cus_name);
        cus_mobile_no = (EditText) findViewById(R.id.cus_mobile_no);
        cus_login_id = (EditText) findViewById(R.id.cus_login_id);
        cus_paswd = (EditText) findViewById(R.id.cus_paswd);
        btn_submt = (Button) findViewById(R.id.btn_submt);
        mProgressView = findViewById(R.id.login_progress);
        mProgressView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_submt:

                c_name = et_name.getText().toString();
                c_mble_no = cus_mobile_no.getText().toString().trim();
                c_login_id = cus_login_id.getText().toString();
                c_pswd = cus_paswd.getText().toString();
                String token = "12345";
                int selectedId = radio_grup.getCheckedRadioButtonId();
                radio_user_type = (RadioButton) findViewById(selectedId);


                if (selectedId == -1) {

                    alert_txt.setText(getString(R.string.select_role));
                    alert_txt.setVisibility(View.VISIBLE);
                    Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                    alert_txt.startAnimation(aniFade);
                    radio_grup.setFocusable(true);
                    radio_grup.setFocusableInTouchMode(true);
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            alert_txt.setVisibility(View.GONE);
                        }
                    }, 2000);

                } else if (c_name.matches("") || c_name.isEmpty()) {

                    alert_txt.setText(getString(R.string.null_user_name));
                    alert_txt.setVisibility(View.VISIBLE);
                    Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                    alert_txt.startAnimation(aniFade);
                    radio_grup.setFocusable(true);
                    radio_grup.setFocusableInTouchMode(true);
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            alert_txt.setVisibility(View.GONE);
                        }
                    }, 2000);
                    return;
                } else if (c_mble_no == null || c_mble_no.matches("") || cus_mobile_no.getText().length() < 11  ) {

                    alert_txt.setText(getString(R.string.null_mobile_no_));
                    alert_txt.setVisibility(View.VISIBLE);
                    Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                    alert_txt.startAnimation(aniFade);
                    radio_grup.setFocusable(true);
                    radio_grup.setFocusableInTouchMode(true);
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            alert_txt.setVisibility(View.GONE);
                        }
                    }, 2000);
                } else if (c_login_id == null || c_login_id.matches("")) {

                    alert_txt.setText(getString(R.string.null_login_id));
                    alert_txt.setVisibility(View.VISIBLE);
                    Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                    alert_txt.startAnimation(aniFade);
                    radio_grup.setFocusable(true);
                    radio_grup.setFocusableInTouchMode(true);
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            alert_txt.setVisibility(View.GONE);
                        }
                    }, 2000);
                } else if (c_pswd == null || c_pswd.matches("")) {

                    alert_txt.setText(getString(R.string.null_pswd));
                    alert_txt.setVisibility(View.VISIBLE);
                    Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                    alert_txt.startAnimation(aniFade);
                    radio_grup.setFocusable(true);
                    radio_grup.setFocusableInTouchMode(true);
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            alert_txt.setVisibility(View.GONE);
                        }
                    }, 2000);
                } else {

                    if (radio_user_type.getText().equals("Admin")) {
                        user_role = "A";
                    } else {

                        user_role = "U";
                    }
                    if (NetworkUtil.isConnected(mContext)) {


//                                   http://test.xolva.com/smart_parking/user_signup.php?customer_name=checking&customer_status=U&&customer_no=03042518403&customer_email=email.com&customer_pswd=password&token=12352
                        String urlAddress = "http://test.xolva.com/smart_parking/user_signup.php?customer_name=" + c_name + "&customer_status=" + user_role + "&customer_no=" + c_mble_no + "&customer_email=" + c_login_id + "&customer_pswd=" + c_pswd + "&token=" + token;
                        mrequestQueue = Volley.newRequestQueue(mContext);
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
                                    if (msg.equals("sucessfull register.")) {

                                        mProgressView.setVisibility(View.VISIBLE);
                                        Toast.makeText(mContext, "Sucessfull Registrartion", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Signup.this, LoginActivity.class));
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
                                params.put("customer_name", c_name);
                                params.put("customer_second_name", String.valueOf(radio_user_type));
                                params.put("customer_no", c_mble_no);
                                params.put("customer_email", c_login_id);
                                params.put("customer_pswd", c_pswd);
                                params.put("token", "12345");
                                return params;
                            }
                        };

                        VolleySingleton.getInstance(Signup.this).addToRequestQueue(jobReq);
                        mrequestQueue.add(jobReq);
                    } else {

                        alert_txt.setText(getString(R.string.conectn_lost));
//                        alert_txt.setBackground(getColor("#C93C3C3C)));
                        alert_txt.setVisibility(View.VISIBLE);
                        Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                        alert_txt.startAnimation(aniFade);
                        radio_grup.setFocusable(true);
                        radio_grup.setFocusableInTouchMode(true);
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {

                                alert_txt.setVisibility(View.GONE);
                            }
                        }, 2000);
                    }

                }
                break;

        }
    }
}
