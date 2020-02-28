package com.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.hl3hl3.arcoremeasure.LoginActivity;
import com.model.user_login;

/**
 * Created by Daniyal on 9/8/2019.
 */

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String customer_id = "customer_id";
    private static final String customer_name = "customer_name";
    private static final String customer_no = "customer_no";
    private static final String customer_address = "customer_address";
    private static final String customer_status = "customer_status";
    private static SharedPrefManager mInstance;
    private static Context ctx;

    private SharedPrefManager(Context context) {
        ctx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }


    //this method will store the user data in shared preferences
    public void userLogin(user_login user) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(customer_id, user.getCustomerId());
        editor.putString(customer_name, user.getCustomerName());
        editor.putString(customer_no, user.getCustomerNo());
        editor.putString(customer_address, user.getCustomerAddress());
        editor.putString(customer_status, user.getCustomerStatus());

        editor.apply();
    }




    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(customer_id, null) != null;
    }



    //this method will give the logged in user
    public user_login getUser() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new user_login(
                sharedPreferences.getString(customer_id, null),
                sharedPreferences.getString(customer_name, null),
                sharedPreferences.getString(customer_no, null),
                sharedPreferences.getString(customer_address, null),
                sharedPreferences.getString(customer_status, null)
        );
    }


    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }
}