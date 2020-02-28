package com.hl3hl3.arcoremeasure;

import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.Locale;

public class ParkingArea_regis extends AppCompatActivity implements  OnMapReadyCallback {

    private BottomSheetBehavior mBottomSheetBehavior;
    private TextView mTextViewState;
    private MapView mapView;

    public Context mContext;
    public static String TAG = ParkingArea_regis.class.getName();
    LatLng pickup_loc;
    public GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_area_regis);
        mContext = this;
        View bottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mapView = findViewById(R.id.mapView);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }

        Places.initialize(getApplicationContext(), "AIzaSyBc0w-AvPE6AWsOdtsvNcRqaRe9R4XfLyE", Locale.US);
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setHint("Select Parking Area");
//        autocompleteFragment.setHintTextColor();
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getAddress() + "-" + place.getLatLng());
                Toast.makeText(mContext, "" + place.getName(), Toast.LENGTH_SHORT).show();
                LatLng resturant_loc = new LatLng(24.932225, 67.128346);
                pickup_loc = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
//                mMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(27.1750, 78.0422))
//                        .title("Taj Mahal")
//                        .snippet("It is located in India")
//                        .rotation((float) 3.5)
//                        .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.logo)));
//                mMap.addMarker(new MarkerOptions().position(pickup_loc).title(place.getName()));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(pickup_loc));
//                float zoomLevel = 13.0f;
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pickup_loc, zoomLevel));
//                drawNavUsingRetrofit(pickup_loc, resturant_loc);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
                Toast.makeText(mContext, "" + status, Toast.LENGTH_SHORT).show();
            }
        });


//        mTextViewState = findViewById(R.id.text_view_state);
//
//        Button buttonExpand = findViewById(R.id.button_expand);
//        Button buttonCollapse = findViewById(R.id.button_collapse);

//        buttonExpand.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//            }
//        });

//        buttonCollapse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//            }
//        });




        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
//                        mTextViewState.setText("Collapsed");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
//                        mTextViewState.setText("Dragging...");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
//                        mTextViewState.setText("Expanded");
                        break;
//                    case BottomSheetBehavior.STATE_HIDDEN:
//                        mTextViewState.setText("Hidden");
//                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
//                        mTextViewState.setText("Settling...");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//                mTextViewState.setText("Sliding...");
            }
        });
    }



    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
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
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.getUiSettings().setCompassEnabled(false);
        LatLng karachi_latlng = new LatLng(24.8607, 67.0011);

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        map.clear(); //clear old markers

        CameraPosition googlePlex = CameraPosition.builder()
                .target(new LatLng(25.1413,55.1853))
                .zoom(2)
                .bearing(0)
                .tilt(45)
                .build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);

        map.addMarker(new MarkerOptions()
                .position(new LatLng(27.1750, 78.0422))
                .title("Taj Mahal")
                .snippet("It is located in India")
                .rotation((float) 3.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.logo)));

        map.addMarker(new MarkerOptions()
                .position(new LatLng(48.8584, 2.2945))
                .title("Eiffel Tower")
                .snippet("It is located in France")
                .rotation((float) 33.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.logo)));

        map.addMarker(new MarkerOptions()
                .position(new LatLng(25.1413, 55.1853))
                .title("burj al arab")
                .snippet("It is located in Dubai")
                .rotation((float) 93.5)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.logo)));

        map.addMarker(new MarkerOptions()
                .position(new LatLng(38.9637, 35.2433))
                .title("Turkey")
                .snippet("It is located in Turkey")
                .rotation((float) 33.5)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));


        CameraPosition cameraPosition3_ = new CameraPosition.Builder().target(karachi_latlng).zoom(12f).tilt(45).build();
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(karachi_latlng, cameraPosition3_.bearing));
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition3_));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().isMyLocationButtonEnabled();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {  //Initialize Google Play Services
            if (ContextCompat.checkSelfPermission(ParkingArea_regis.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);//Location Permission already granted
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                map.setMyLocationEnabled(true);
                map.setTrafficEnabled(true);
                map.setIndoorEnabled(true);
                map.setBuildingsEnabled(true);
                map.getUiSettings().setZoomControlsEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
                map.getUiSettings().setCompassEnabled(true);
                map.getUiSettings().setMapToolbarEnabled(true);
                mMap.getMyLocation();
            }
//            else {
                //Request Location Permission
//                checkLocationPermission();
//            }
        } else {
            mMap.setMyLocationEnabled(true);
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.setMyLocationEnabled(true);
            map.setTrafficEnabled(true);
            map.setIndoorEnabled(true);
            map.setBuildingsEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
            map.getUiSettings().setCompassEnabled(true);
            map.getUiSettings().setMapToolbarEnabled(true);
            mMap.getMyLocation();
        }

        try {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void open_botm_sheet(View view) {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}
