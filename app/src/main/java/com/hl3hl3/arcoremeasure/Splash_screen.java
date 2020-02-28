package com.hl3hl3.arcoremeasure;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.utils.NetworkUtil;
import com.utils.SharedPrefManager;

public class Splash_screen extends AppCompatActivity {


    private static int SPLASH_TIME_OUT = 3000;
    Context context;
    public ImageView splash_logo;
    TextView internt_abord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        context = this;

        if (NetworkUtil.isConnected(context)) {

            Log.d("TAG", "msg_splah" + SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn());
            Boolean is_login = SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn();
            if (is_login.equals( true)) {

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        String status = SharedPrefManager.getInstance(getApplicationContext()).getUser().getCustomerStatus();
                        switch (status) {
                            case "A":

                                startActivity(new Intent(getApplicationContext(), dashboard_admin.class));
                                Toast.makeText(Splash_screen.this, "Welcome to Admin", Toast.LENGTH_SHORT).show();
                                break;
                            case "U":
                                startActivity(new Intent(getApplicationContext(), dashboard_user.class));
                                Toast.makeText(Splash_screen.this, "Welcome to User", Toast.LENGTH_SHORT).show();
                                break;
                            case "PM":
                                Toast.makeText(context, "Welcome to Parking Manger", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), dashboard_pm.class));
                                break;
                        }
//                        finish();
                    }
                }, SPLASH_TIME_OUT);

            } else {

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {


                        Intent i = new Intent(Splash_screen.this, LoginActivity.class);
                        startActivity(i);
//                        finish();

                    }
                }, SPLASH_TIME_OUT);

            }
        } else {
            Toast.makeText(context, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
//            internt_abord.setVisibility(View.VISIBLE);
//            splash_logo.setVisibility(View.GONE);
        }



    }
}
//http://localhost/smart_parking/user_signup.php?customer_name=checking&customer_status=U&&customer_no=03042518403&customer_email=email.com&customer_pswd=password&token=12352