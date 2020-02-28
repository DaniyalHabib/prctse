package com.hl3hl3.arcoremeasure;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.hl3hl3.arcoremeasure.renderer.pm_signup;
import com.utils.SharedPrefManager;

public class dashboard_admin extends AppCompatActivity {

        TextView txt_admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        txt_admin= (TextView)findViewById(R.id.txt_admin);
        String  admin_name = SharedPrefManager.getInstance(getApplicationContext()).getUser().getCustomerName();
        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_toolbar);
        txt_admin.setText(admin_name);
        setSupportActionBar(toolbar);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_right_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_logout:
                new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SharedPrefManager.getInstance(getApplicationContext()).logout();
                                startActivity(new Intent(dashboard_admin.this, LoginActivity.class));
                            }
                        }).setNegativeButton("No", null).show();

                break;
            case R.id.refrsh:

              startActivity(new Intent( dashboard_admin.this, dashboard_admin.class));

                break;

        }
        return (super.onOptionsItemSelected(item));
    }

    public void pm_regist_call(View view) {

        startActivity(new Intent( dashboard_admin.this, pm_signup.class));

    }

    public void call_p_a_regis(View view) {

        startActivity(new Intent(getApplicationContext(),ParkingArea_regis.class));
    }

    public void call_status(View view) {

        startActivity(new Intent(dashboard_admin.this,Status_details.class));
    }
}
