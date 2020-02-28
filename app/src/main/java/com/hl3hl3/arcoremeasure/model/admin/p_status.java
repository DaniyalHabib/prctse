package com.hl3hl3.arcoremeasure.model.admin;


import android.util.Log;

import com.google.gson.annotations.SerializedName;
import org.json.JSONObject;
public class p_status {



    @SerializedName("parking_area_id")
    private String parkingAreaId;
    @SerializedName("pm_id")
    private String pmId;
    @SerializedName("area_name")
    private String areaName;
    @SerializedName("area_slot_no")
    private String areaSlotNo;
    @SerializedName("area_dimension")
    private String areaDimension;
    @SerializedName("loc_name")
    private String locName;
    @SerializedName("loc_lat")
    private String locLat;
    @SerializedName("loc_lng")
    private String locLng;
    @SerializedName("area_lat")
    private String areaLat;
    @SerializedName("area_lng")
    private String areaLng;


//    ----------------------------------------------------------------------------------------------

    public p_status(JSONObject jsonObject) {


        try {
            setParkingAreaId(jsonObject.optString("parking_area_id", ""));
            setPmId(jsonObject.optString("pm_id", ""));
            setAreaName(jsonObject.optString("area_name", ""));
            setAreaSlotNo(jsonObject.optString("area_slot_no", ""));
            setAreaDimension(jsonObject.optString("area_dimension", ""));
            setLocName(jsonObject.optString("loc_name", ""));
            setLocLat(jsonObject.optString("loc_lat", ""));
            setLocLng(jsonObject.optString("loc_lng", ""));
            setAreaLat(jsonObject.optString("area_lat", ""));
            setAreaLng(jsonObject.optString("area_lng", ""));



        } catch (Exception e) {
            Log.d("Exception" , "Exception : "+e);
        }


    }

    public String getParkingAreaId() {
        return parkingAreaId;
    }

    public void setParkingAreaId(String parkingAreaId) {
        this.parkingAreaId = parkingAreaId;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaSlotNo() {
        return areaSlotNo;
    }

    public void setAreaSlotNo(String areaSlotNo) {
        this.areaSlotNo = areaSlotNo;
    }

    public String getAreaDimension() {
        return areaDimension;
    }

    public void setAreaDimension(String areaDimension) {
        this.areaDimension = areaDimension;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getLocLat() {
        return locLat;
    }

    public void setLocLat(String locLat) {
        this.locLat = locLat;
    }

    public String getLocLng() {
        return locLng;
    }

    public void setLocLng(String locLng) {
        this.locLng = locLng;
    }

    public String getAreaLat() {
        return areaLat;
    }

    public void setAreaLat(String areaLat) {
        this.areaLat = areaLat;
    }

    public String getAreaLng() {
        return areaLng;
    }

    public void setAreaLng(String areaLng) {
        this.areaLng = areaLng;
    }


}