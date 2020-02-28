package com.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class parking_list {


        @SerializedName("parking_area_id")
        @Expose
        private String parkingAreaId;
        @SerializedName("pm_id")
        @Expose
        private String pmId;
        @SerializedName("area_name")
        @Expose
        private String areaName;
        @SerializedName("area_slot_no")
        @Expose
        private String areaSlotNo;
        @SerializedName("area_dimension")
        @Expose
        private String areaDimension;
        @SerializedName("loc_name")
        @Expose
        private String locName;
        @SerializedName("loc_lat")
        @Expose
        private String locLat;
        @SerializedName("loc_lng")
        @Expose
        private String locLng;
        @SerializedName("area_lat")
        @Expose
        private String areaLat;
        @SerializedName("area_lng")
        @Expose
        private String areaLng;

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
