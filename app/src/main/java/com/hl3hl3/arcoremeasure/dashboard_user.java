package com.hl3hl3.arcoremeasure;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hl3hl3.Urls.Urls;
import com.model.map_model.parking_area_marker;
import com.model.marker_model.ImageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class dashboard_user extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {
    public MapView mapView;
    private GoogleMap mMap;
    public static String TAG = dashboard_user.class.getName();
    Context context;
    ArrayList<String> parking_area_id_list = new ArrayList<>();
    ArrayList<String> pm_id_list = new ArrayList<>();
    ArrayList<String> area_name_list = new ArrayList<>();
    ArrayList<String> slot_list = new ArrayList<>();
    ArrayList<String> loc_name_list = new ArrayList<>();
    ArrayList<LatLng> latlng_list = new ArrayList<LatLng>();
    ArrayList<String> loc_lat = new ArrayList<>();
    ArrayList<String> loc_lng = new ArrayList<>();
    public ArrayAdapter<String> area_name_adptr;
    public Spinner spinner;
    LatLng latLng;
    String Temp_Name;
    Marker marker;
    int i=0;
    String areaName;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);
        context = this;
        area_name_list.add(0, "Please Select Parking");
        mapView = findViewById(R.id.mapView);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
        spinner = (Spinner) findViewById(R.id.spinner);
        initializeMap();
        getData();
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);




    }


    private void getData() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Urls.Parking_Area, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (i = 0; i < response.length(); i++) {

                        try {

                            JSONObject jsonObject = response.getJSONObject(i);
                            Log.d("api_res", String.valueOf(response));
                            Log.d("jsonObject", String.valueOf(jsonObject.getString("area_name")));
                            String _id_ = jsonObject.getString("parking_area_id");
                            parking_area_id_list.add(jsonObject.getString("parking_area_id"));
                            pm_id_list.add(jsonObject.getString("pm_id"));
                            area_name_list.add(jsonObject.getString("area_name"));
                            slot_list.add(jsonObject.getString("area_slot_no"));
                            loc_name_list.add(jsonObject.getString("loc_name"));
                            loc_lat.add(jsonObject.getString("loc_lat"));
                            loc_lng.add(jsonObject.getString("loc_lng"));
                            double lat = Double.parseDouble(jsonObject.getString("loc_lat"));
                            double lng = Double.parseDouble(jsonObject.getString("loc_lng"));
                            areaName = jsonObject.getString("area_name");
                            latLng = new LatLng(lat, lng);
                            latlng_list.add(latLng);

                             marker =  mMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .icon(BitmapDescriptorFactory.fromBitmap(parking_area_marker.createCustomMarker(dashboard_user.this, R.drawable.p_logo, jsonObject.getString("area_name"))))
                                    .title(jsonObject.getString("area_name"))
                                    .snippet(jsonObject.getString("area_name"))
                                    .anchor(0.5f, 1));
                             marker.setTag(_id_);

                            Log.d("Latlng", latLng.toString());
                            marker.setInfoWindowAnchor(12,21);


                                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker arg0) {
                                        // if marker source is clicked


                                            if (arg0.getId().equals("Water Pump Market Parking")) {
                                                // display toast
                                                Toast.makeText(dashboard_user.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();

                                                android.app.AlertDialog.Builder mBulider = new android.app.AlertDialog.Builder(context);
                                                v = LayoutInflater.from(context).inflate(R.layout.parking_details, null);

                                                Button btn_close_popup = (Button) v.findViewById(R.id.btn_close_popup);
                                                Button btn_accpt_job = (Button) v.findViewById(R.id.btn_accpt_job_);

                                                GridView gridView = (GridView) findViewById(R.id.grid_view);
                                                gridView.setAdapter(new ImageAdapter(getApplication()));



                                                mBulider.setView(v);
                                                android.app.AlertDialog dialog = mBulider.create();
                                                dialog.show();



                                            }
                                            else if(arg0.getTitle().equals("Water Pump Bus Parking")){
                                                Toast.makeText(dashboard_user.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();
                                                android.app.AlertDialog.Builder mBulider = new android.app.AlertDialog.Builder(context);
                                                v = LayoutInflater.from(context).inflate(R.layout.parking_details, null);

                                                GridView gridView = (GridView) v.findViewById(R.id.grid_view);
                                                gridView.setAdapter(new ImageAdapter(getApplication()));
                                                Button btn_close_popup = (Button) v.findViewById(R.id.btn_close_popup);
                                                Button btn_accpt_job = (Button) v.findViewById(R.id.btn_accpt_job_);
                                                mBulider.setView(v);
                                                android.app.AlertDialog dialog = mBulider.create();
                                                dialog.show();
                                            }
                                            else if (arg0.getId().equals("Gulberg_parking")) {
                                                // display toast
                                                Toast.makeText(dashboard_user.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();
                                                android.app.AlertDialog.Builder mBulider = new android.app.AlertDialog.Builder(context);
                                                v = LayoutInflater.from(context).inflate(R.layout.parking_details, null);
                                                GridView gridView = (GridView) findViewById(R.id.grid_view);
                                                gridView.setAdapter(new ImageAdapter(getApplication()));
                                                Button btn_close_popup = (Button) v.findViewById(R.id.btn_close_popup);
                                                Button btn_accpt_job = (Button) v.findViewById(R.id.btn_accpt_job_);
                                                mBulider.setView(v);
                                                android.app.AlertDialog dialog = mBulider.create();
                                                dialog.show();
                                            }



                                        return true;
                                    }
                                });


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    area_name_adptr = new ArrayAdapter<String>(dashboard_user.this, R.layout.spinner_item, area_name_list) {
                        @Override
                        public boolean isEnabled(int position) {
                            if (position == 0) {

                                return false;
                            } else {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if (position == 0) {
                                // Set the hint text color gray
                                tv.setTextColor(Color.GRAY);

                            } else {
                                tv.setTextColor(Color.BLACK);
                                Drawable img = getContext().getResources().getDrawable(R.drawable.adress);
                                tv.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);


                            }
                            return view;
                        }
                    };
                    area_name_adptr.setDropDownViewResource(R.layout.spinner_item);
                    spinner.setSelection(0);
                    spinner.setAdapter(area_name_adptr);


                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();

                    Log.d("response_failed", e.toString());
                }

//                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();

                Log.d(TAG, "error_login_" + error);
                if (error instanceof NetworkError) {
                    Toast.makeText(context, "Network are not Connected", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(context, "Server error", Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {

                    Toast.makeText(context, "Auth Failed", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {

                    Toast.makeText(context, "Parse Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(context, "Internet not Found", Toast.LENGTH_SHORT).show();
                }
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }


    private void initializeMap() {
        if (mMap == null) {
            mapView = findViewById(R.id.mapView);
            mapView.getMapAsync(this);
            //setup markers etc...

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    public void onPause() {

        super.onPause();
        mapView.onPause();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


//        LatLng sindh_latlng = new LatLng(25.894/3, 68.5247);
        LatLng karachi_latlng = new LatLng(24.8607, 67.0011);

        CameraPosition cameraPosition3_ = new CameraPosition.Builder().target(karachi_latlng).zoom(11f).tilt(75).build();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(karachi_latlng, cameraPosition3_.bearing));
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition3_));
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setBuildingsEnabled(true);


//            //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(dashboard_user.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                googleMap.setMyLocationEnabled(true);
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.setMyLocationEnabled(true);
                mMap.setIndoorEnabled(true);
                mMap.setBuildingsEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.getUiSettings().setCompassEnabled(true);
                mMap.getUiSettings().setMapToolbarEnabled(true);
                googleMap.getMyLocation();
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            googleMap.setMyLocationEnabled(true);
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setMyLocationEnabled(true);
            mMap.setIndoorEnabled(true);
            mMap.setBuildingsEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setMapToolbarEnabled(true);
            googleMap.getMyLocation();
        }


        final ViewGroup parent = (ViewGroup) mapView.findViewWithTag("GoogleMapMyLocationButton").getParent();
        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        View zoom_btn = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("1"));


        parent.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Resources r = getResources();
                    //convert our dp margin into pixels
                    int marginPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());
                    // Get the map compass view
                    View mapCompass = parent.getChildAt(4);

                    // create layoutParams, giving it our wanted width and height(important, by default the width is "match parent")
                    RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(mapCompass.getHeight(), mapCompass.getHeight());
                    // position on top right
                    rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
                    rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                    rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
                    //give compass margin
                    rlp.setMargins(marginPixels, marginPixels, marginPixels, marginPixels);
                    rlp.setMargins(0, 380, 20, 0); // 160 la truc y , 30 la  truc x
                    mapCompass.setLayoutParams(rlp);

                    RelativeLayout.LayoutParams locatn_btn = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
                    locatn_btn.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                    locatn_btn.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                    locatn_btn.setMargins(0, 250, 180, 0);

                    RelativeLayout.LayoutParams zoom_btn1 = (RelativeLayout.LayoutParams) zoom_btn.getLayoutParams();
                    zoom_btn1.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                    zoom_btn1.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                    zoom_btn1.setMargins(0, 500, 180, 0);


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


//        getMarkerLoc();


    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(dashboard_user.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(dashboard_user.this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", (dialogInterface, i) -> {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(dashboard_user.this,
                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                    1200);
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        1200);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();

        Temp_Name = parent.getItemAtPosition(position).toString();

        for (int j = 0; j < area_name_list.size(); j++) {
            if (Temp_Name.equals(area_name_list.get(j))) {
                for (int i = 0; i < latlng_list.size(); i++) {
                    if (i == j) {

//                        LatLng latLng1 = new LatLng(24.937129, 67.079641);
                        CameraPosition cameraPosition3_ = new CameraPosition.Builder().target(latlng_list.get(i)).zoom(16f).tilt(30).build();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng_list.get(i), cameraPosition3_.bearing));
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition3_));
                        Log.d(TAG, "latlng_list" + latlng_list.get(i));
                    }
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void start(View view) {
        Intent intent = new Intent(dashboard_user.this,ForegroundService.class);
        startService(intent);
        Toast.makeText(context, "Start", Toast.LENGTH_SHORT).show();
    }

    public void stop(View view) {
        Intent intent = new Intent(dashboard_user.this,ForegroundService.class);
        stopService(intent);
        Toast.makeText(context, "Stop", Toast.LENGTH_SHORT).show();

    }
}
