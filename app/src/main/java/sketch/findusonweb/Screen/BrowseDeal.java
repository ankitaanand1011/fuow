package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Adapter.AdapterBrowse;
import sketch.findusonweb.Adapter.AdapterBrowseDeal;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;


public class BrowseDeal extends AppCompatActivity {
    String TAG = "login";
    ListView list_deals;
   // TextView post_requirement;
    ImageView back_img;
    Shared_Preference prefrence;
    Context context;
    GlobalClass globalClass;
    String keywords,business,low,high;
    ProgressDialog pd;
    ArrayList<HashMap<String,String>> list_names;
   // ArrayList<String> list_names = new ArrayList<>();
    AdapterBrowseDeal adapterBrowseDeal;

    ImageView search_button;
    LinearLayout ll_search,ll_main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_deal);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(BrowseDeal.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(BrowseDeal.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        list_names = new ArrayList<>();


       // post_requirement=findViewById(R.id.post_requirement);
        list_deals = findViewById(R.id.list_deals);
        back_img=findViewById(R.id.back_img);

        search_button=findViewById(R.id.search_button);
        ll_search=findViewById(R.id.ll_search);
        ll_main=findViewById(R.id.ll_main);

        ll_main.setVisibility(View.VISIBLE);
        ll_search.setVisibility(View.GONE);


        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
                browseJob();
            }
        } else {
            Toasty.info(BrowseDeal.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    search_edit.setVisibility(View.VISIBLE);
                header_img.setVisibility(View.GONE);
                //  list_names.clear();*/


                ll_search.setVisibility(View.VISIBLE);


            }
        });




        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       // browseJob();


    }


    private void browseJob() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());


                list_names.clear();
                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                  //  String success = jobj.get("success").toString().replaceAll("\"", "");
               //     String message = jobj.get("message").toString().replaceAll("\"", "");
                   // Log.d(TAG, "message: "+message);

                   // if (success.equals("1")) {
                        JsonArray images=jobj.getAsJsonArray("data");

                        for (int i = 0; i < images.size(); i++) {
                            JsonObject images1 = images.get(i).getAsJsonObject();
                         //   String id = images1.get("id").toString().replaceAll("\"", "");
                            String title = images1.get("title").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");
                            String price = images1.get("price").toString().replaceAll("\"", "");
                            String discount_value = images1.get("discount_value").toString().replaceAll("\"", "");
                            String new_price = images1.get("new_price").toString().replaceAll("\"", "");
                            String rating = images1.get("rating").toString().replaceAll("\"", "");
                            String description = images1.get("description").toString().replaceAll("\"", "");
                            String listing_name = images1.get("listing_name").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("title", title);
                            hashMap.put("date", date);
                            hashMap.put("price", price);
                            hashMap.put("discount_value", discount_value);
                            hashMap.put("new_price", new_price);
                            hashMap.put("rating", rating);
                            hashMap.put("description", description);
                            hashMap.put("listing_name", listing_name);

                            list_names.add(hashMap);

                            // Toasty.success(SearchListing.this, login, Toast.LENGTH_SHORT, true).show();

                        }
                        adapterBrowseDeal = new AdapterBrowseDeal(BrowseDeal.this, list_names);
                        list_deals.setAdapter(adapterBrowseDeal);


              /*      } else


                    {


                        Toasty.success(BrowseDeal.this, message, Toast.LENGTH_SHORT, true).show();
                    }

                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    Log.d(TAG, "Token \n" + message);*/

                    pd.dismiss();
                } catch (Exception e) {

                    Toasty.warning(BrowseDeal.this, "Username Already Exists", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),"Registration Error", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();



                params.put("view","BrowseDealSearch");

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

/*
    private void browseJobFilter(final String keyword) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

      //  pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

              //  pd.dismiss();
                list_names.clear();
                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String success = jobj.get("success").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");
                    Log.d(TAG, "message: "+message);

                    if (success.equals("1")) {
                        JsonArray images=jobj.getAsJsonArray("data");

                        for (int i = 0; i < images.size(); i++) {
                            JsonObject images1 = images.get(i).getAsJsonObject();
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String title = images1.get("title").toString().replaceAll("\"", "");
                            String description = images1.get("description").toString().replaceAll("\"", "");
                            String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                            String primary_category = images1.get("primary_category").toString().replaceAll("\"", "");
                            String status = images1.get("status").toString().replaceAll("\"", "");
                            String profile_pic = images1.get("profile_pic").toString().replaceAll("\"", "");
                            String duration = images1.get("duration").toString().replaceAll("\"", "");
                            String budget = images1.get("budget").toString().replaceAll("\"", "");
                            String date_requested = images1.get("date_requested").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("title", title);
                            hashMap.put("description", description);
                            hashMap.put("primary_category", primary_category);
                            hashMap.put("status", status);
                            hashMap.put("duration", duration);
                            hashMap.put("profile_pic", profile_pic);
                            hashMap.put("budget", budget);
                            hashMap.put("id", id);
                            hashMap.put("date_requested", date_requested);
                            list_names.add(hashMap);
                            Log.d(TAG, "Image: " + primary_category);
                            // Toasty.success(SearchListing.this, login, Toast.LENGTH_SHORT, true).show();

                        }
                        adapterBrowseDeal = new AdapterBrowseDeal(BrowseDeal.this, list_names);
                        list_deals.setAdapter(adapterBrowseDeal);



                    } else


                    {


                        Toasty.success(BrowseDeal.this, message, Toast.LENGTH_SHORT, true).show();
                    }

                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    Log.d(TAG, "Token \n" + message);


                } catch (Exception e) {

                    Toasty.warning(BrowseDeal.this, "Username Already Exists", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),"Registration Error", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();



                params.put("view", globalClass.browse_job);
                params.put("low_price", "");
                params.put("high_price", "");
                params.put("keyword", keyword);
                params.put("business", "");


                Log.d(TAG, "getParams: "+params);

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
*/

}
