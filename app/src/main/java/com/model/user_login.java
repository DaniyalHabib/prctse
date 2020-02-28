package com.model;

import org.json.JSONObject;

/**
 * Created by Daniyal on 9/8/2019.
 */

public class user_login {


    private String customerId;
    private String customerName;
    private String customerNo;
    private String customerAddress;
    private String customerStatus;

    public user_login(String customer_id, String customer_name, String customer_no, String customer_address,  String customer_status) {

        try {
            setCustomerId(customer_id);
            setCustomerName(customer_name);
            setCustomerNo(customer_no);
            setCustomerAddress(customer_address);
            setCustomerStatus(customer_status);

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }


    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }
}
