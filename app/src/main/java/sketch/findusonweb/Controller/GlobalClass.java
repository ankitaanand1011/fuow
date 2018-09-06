package sketch.findusonweb.Controller;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Developer on 3/9/18.
 */

public class GlobalClass extends Application {

    String TAG = "app";

    public Boolean login_status = false;
    public String Category="getCategory";
    public String Location="getLocation";
    public String Status_new="2";
    public String view_friend="inviteFriend";
    public String list="viewListing";
    public String browse_job="browse_request";
    public String search_business="search";
    public String browseProducts="browseProducts";
    public String pound="£";


    private static GlobalClass mInstance;

    String id,List_id;

    String name;
    String email;

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    String business;
    String phone_number;
    String fcm_reg_token;
    String deviceid;
    String profil_pic;
    String fname;
    String lname;
    String organization;

    public String device_type = "Android";
    public String view1="validateLogin";
    public  String RegisterView="register";
    public String login_from= "";
    public RequestQueue mRequestQueue;
    String currency_symbol;
    String grand_total;

    String shipping_id, shipping_fname, shipping_lname, shipping_address, shipping_city, shipping_state,
            shipping_country, shipping_zip, shipping_mobile,shipping_full_address ;

    public static synchronized GlobalClass getInstance() {
        return mInstance;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public String getCurrency_symbol() {
        return currency_symbol;
    }

    public void setCurrency_symbol(String currency_symbol) {
        this.currency_symbol = currency_symbol;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Boolean getLogin_status() {
        return login_status;
    }

    public void setLogin_status(Boolean login_status) {
        this.login_status = login_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getList_Id() {
        return List_id;
    }

    public void setListId(String List_id) {
        this.List_id = List_id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getFcm_reg_token() {
        return fcm_reg_token;
    }

    public void setFcm_reg_token(String fcm_reg_token) {
        this.fcm_reg_token = fcm_reg_token;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getProfil_pic() {
        return profil_pic;
    }

    public void setProfil_pic(String profil_pic) {
        this.profil_pic = profil_pic;
    }

    public String getLogin_from() {
        return login_from;
    }

    public void setLogin_from(String login_from) {
        this.login_from = login_from;
    }

    ///////////////////////////////////////////////////////////
    //cart section


    public String cart_no= "0";

    public String getCart_no() {
        return cart_no;
    }

    public void setCart_no(String cart_no) {
        this.cart_no = cart_no;
    }

    public ArrayList<HashMap<String,String>> CART_item_list = new ArrayList<>();

    public ArrayList<HashMap<String, String>> getCART_item_list() {
        return CART_item_list;
    }

    public void setCART_item_list(ArrayList<HashMap<String, String>> CART_item_list) {
        this.CART_item_list = CART_item_list;
    }

    /////////////////////////////////////////////
    //shipping_details


    public String getShipping_id() {
        return shipping_id;
    }

    public void setShipping_id(String shipping_id) {
        this.shipping_id = shipping_id;
    }

    public String getShipping_fname() {
        return shipping_fname;
    }

    public void setShipping_fname(String shipping_fname) {
        this.shipping_fname = shipping_fname;
    }

    public String getShipping_lname() {
        return shipping_lname;
    }

    public void setShipping_lname(String shipping_lname) {
        this.shipping_lname = shipping_lname;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getShipping_city() {
        return shipping_city;
    }

    public void setShipping_city(String shipping_city) {
        this.shipping_city = shipping_city;
    }

    public String getShipping_state() {
        return shipping_state;
    }

    public void setShipping_state(String shipping_state) {
        this.shipping_state = shipping_state;
    }

    public String getShipping_country() {
        return shipping_country;
    }

    public void setShipping_country(String shipping_country) {
        this.shipping_country = shipping_country;
    }

    public String getShipping_zip() {
        return shipping_zip;
    }

    public void setShipping_zip(String shipping_zip) {
        this.shipping_zip = shipping_zip;
    }

    public String getShipping_mobile() {
        return shipping_mobile;
    }

    public void setShipping_mobile(String shipping_mobile) {
        this.shipping_mobile = shipping_mobile;
    }

    public String getShipping_full_address() {
        return shipping_full_address;
    }

    public void setShipping_full_address(String shipping_full_address) {
        this.shipping_full_address = shipping_full_address;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }

    /////////////////////////////////////////
   public ArrayList<String> GselectedString = new ArrayList<>();
    public ArrayList<String> Capcity_selectedString = new ArrayList<>();

    public ArrayList<String> getGselectedString() {
        return GselectedString;
    }

    public void setGselectedString(ArrayList<String> gselectedString) {
        GselectedString = gselectedString;
    }

    public ArrayList<String> getCapcity_selectedString() {
        return Capcity_selectedString;
    }

    public void setCapcity_selectedString(ArrayList<String> capcity_selectedString) {
        Capcity_selectedString = capcity_selectedString;
    }


    /////////////////////

    public boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

}
