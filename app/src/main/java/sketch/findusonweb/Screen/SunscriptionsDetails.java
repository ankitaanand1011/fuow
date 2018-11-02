package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by developer on 30/10/18.
 */

public class SunscriptionsDetails extends AppCompatActivity {
    GlobalClass globalClass;
    Shared_Preference prefrence;
    ProgressDialog pd;
    String TAG = "Summary";
    String id;
    ImageView back_img;
    RelativeLayout rl_listing_details,rl_order_details,rl_usage_limit,rl_usage_limit_new,rl_quick_statics,rl_quick_statics_new;
    LinearLayout ll_listing_details,ll_order_details,ll_usage_limit_new,ll_quick_statics_new;
    ImageView arrow_img_1,arrow_img_2,arrow_img_3,arrow_img_4,arrow_img_5,arrow_img_6,arrow_img_7;
    TextView tv_view,tv_edit,tv_id_value,tv_public_url,tv_category,tv_location,tv_submit_date,tv_update_date,
            tv_order_id,tv_type,tv_next_due_date,tv_status,tv_usage_category,tv_usage_location,tv_usage_document,
            tv_usage_product,tv_usage_gallery,tv_homepage,tv_homepage_exibition,
            tv_homepage_header,tv_total_impression,tv_search_impression,tv_impression_last_week;
    ArrayList<HashMap<String,String>> listing;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscription_details);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(SunscriptionsDetails.this);
        pd = new ProgressDialog(SunscriptionsDetails.this);
        id=getIntent().getStringExtra("id");
        Log.d(TAG, "Value of id: "+id);
        viewListingByUser();
        back_img=findViewById(R.id.back_img);
        tv_id_value=findViewById(R.id.tv_id_val);
        tv_public_url=findViewById(R.id.tv_url_val);
        tv_category=findViewById(R.id.tv_category_val);
        tv_location=findViewById(R.id.tv_location_val);
        tv_submit_date=findViewById(R.id.tv_date_submitted_val);
        tv_update_date=findViewById(R.id.tv_date_last_updated_val);
        tv_order_id=findViewById(R.id.tv_order_val);
        tv_type=findViewById(R.id.tv_type_val);
        tv_next_due_date=findViewById(R.id.tv_next_due_date_val);
        tv_status=findViewById(R.id.tv_status_val);
        tv_usage_category=findViewById(R.id.tv_category_usage_edit);
        tv_usage_location=findViewById(R.id.tv_locations_val);
        tv_usage_document=findViewById(R.id.tv_document_val);
        tv_usage_product=findViewById(R.id.tv_product_val);
        tv_usage_gallery=findViewById(R.id.tv_gallery_val);
        tv_homepage=findViewById(R.id.tv_homepage_val);
        tv_homepage_exibition=findViewById(R.id.tv_exibition_val);
        tv_homepage_header=findViewById(R.id.tv_header_banner_val);
        tv_total_impression=findViewById(R.id.tv_quick_statistics_edit);
        tv_search_impression=findViewById(R.id.tv_impression_val);
        tv_impression_last_week=findViewById(R.id.tv_total_search_impression_val);


        /*...........................................*/
        rl_listing_details=findViewById(R.id.rl_listing_details);
        ll_listing_details=findViewById(R.id.ll_listing_detail);
        rl_order_details=findViewById(R.id.rl_order_details);
        ll_order_details=findViewById(R.id.ll_order_detail);

        arrow_img_1=findViewById(R.id.arrow_img_1);
        arrow_img_2=findViewById(R.id.arrow_img_2);

        tv_edit=findViewById(R.id.edit_img);
        tv_view=findViewById(R.id.tv_view);
        ll_listing_details.setVisibility(View.GONE);
        ll_order_details.setVisibility(View.GONE);
        ll_usage_limit_new.setVisibility(View.GONE);
        ll_quick_statics_new.setVisibility(View.GONE);

        rl_listing_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_listing_details.setVisibility(View.VISIBLE);
                ll_order_details.setVisibility(View.GONE);



                arrow_img_1.setVisibility(View.GONE);
                arrow_img_2.setVisibility(View.VISIBLE);


                tv_edit.setVisibility(View.VISIBLE);
                tv_view.setVisibility(View.GONE);

            }
        });
        rl_order_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_listing_details.setVisibility(View.GONE);
                ll_order_details.setVisibility(View.VISIBLE);



                arrow_img_1.setVisibility(View.VISIBLE);
                arrow_img_2.setVisibility(View.GONE);


                tv_edit.setVisibility(View.GONE);
                tv_view.setVisibility(View.VISIBLE);

            }
        });
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void viewListingByUser() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                //pd.dismiss();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    Log.d(TAG, "onResponse: " + jobj);
                    String result = jobj.get("success").toString().replaceAll("\"", "");

                    //  JsonArray data = jobj.getAsJsonArray("data");
                    //  Log.d(TAG, "Data: " + data);

                    if (result.equals("1")) {
                        JsonObject data = jobj.getAsJsonObject("data");
                        Log.d(TAG, "Data: " + data);
                        int number = data.size();
                        // tv_total1.setText(String.valueOf(number));


                       // JsonObject images1 = data.getAsJsonObject("order_details");
                        String id = data.get("id").toString().replaceAll("\"", "");
                        String order_id = data.get("order_id").toString().replaceAll("\"", "");
                        String price = data.get("price").toString().replaceAll("\"", "");
                        String gateway = data.get("gateway").toString().replaceAll("\"", "");
                        String date = data.get("date").toString().replaceAll("\"", "");
                        String status = data.get("status").toString().replaceAll("\"", "");
                        String reciever = data.get("reciever").toString().replaceAll("\"", "");
                        String companyname = data.get("companyname").toString().replaceAll("\"", "");
                        String next_invoice_date = data.get("next_invoice_date").toString().replaceAll("\"", "");
                        String amount_recurring = data.get("amount_recurring").toString().replaceAll("\"", "");
                        String period = data.get("period").toString().replaceAll("\"", "");
                        String product_status = data.get("product_status").toString().replaceAll("\"", "");
                        String product_group_name = data.get("product_group_name").toString().replaceAll("\"", "");
                        String product_name = data.get("product_name").toString().replaceAll("\"", "");
                        String product_title = data.get("product_title").toString().replaceAll("\"", "");
                        String amount = data.get("amount").toString().replaceAll("\"", "");
                        String type = data.get("type").toString().replaceAll("\"", "");
                        tv_order_id.setText(order_id);
                        tv_type.setText(type);
                        tv_status.setText(status);
                      //  tv_next_due_date.setText(next_due_date);


                        JsonObject listing_details = data.getAsJsonObject("listing_details");
                       // String id = listing_details.get("id").toString().replaceAll("\"", "");
                       // String date = listing_details.get("date").toString().replaceAll("\"", "");
                        String listing_address1 = listing_details.get("listing_address1").toString().replaceAll("\"", "");
                        String listing_address2 = listing_details.get("listing_address2").toString().replaceAll("\"", "");
                        String location_text_1 = listing_details.get("location_text_1").toString().replaceAll("\"", "");
                        String location_text_2 = listing_details.get("location_text_2").toString().replaceAll("\"", "");
                        String location_search_text = listing_details.get("location_search_text").toString().replaceAll("\"", "");
                        tv_id_value.setText(id);
                        tv_location.setText(location_text_2);
                        tv_submit_date.setText(date);


                        JsonObject usages_limit = data.getAsJsonObject("usages_limit");
                        String categories = usages_limit.get("categories").toString().replaceAll("\"", "");
                        String location = usages_limit.get("location").toString().replaceAll("\"", "");
                        String document = usages_limit.get("document").toString().replaceAll("\"", "");
                        String Products_Services = usages_limit.get("products_services").toString().replaceAll("\"", "");
                        String gallery = usages_limit.get("gallery").toString().replaceAll("\"", "");
                        String home_page = usages_limit.get("home_page").toString().replaceAll("\"", "");
                        String Home_page_exhibition_and_conferences = usages_limit.get("home_page_exhibition_and_conferences").toString().replaceAll("\"", "");
                        String Home_page_header_banner = usages_limit.get("home_page_header_banner").toString().replaceAll("\"", "");
                        Log.d(TAG, "onResponse: "+categories+location+document);
                        tv_usage_category.setText(categories);
                        tv_usage_location.setText(location);
                        tv_usage_product.setText(Products_Services);
                        tv_usage_gallery.setText(gallery);
                        tv_homepage.setText(home_page);
                        tv_usage_document.setText(document);
                        tv_homepage_exibition.setText(Home_page_exhibition_and_conferences);
                        tv_homepage_header.setText(Home_page_header_banner);

                        JsonObject quick_statistics = data.getAsJsonObject("quick_statistics");
                        String total_impressions = quick_statistics.get("total_impressions").toString().replaceAll("\"", "");
                        String totla_search_impr = quick_statistics.get("total_search_impressions").toString().replaceAll("\"", "");
                        String last_week_imp = quick_statistics.get("impressions_in_the_last_week").toString().replaceAll("\"", "");
                        tv_total_impression.setText(total_impressions);
                        tv_search_impression.setText(totla_search_impr);
                        tv_impression_last_week.setText(last_week_imp);

                        // list_namesfavoriteAll.add(hashMap);
                        // Log.d(TAG, "Listmane: " + list_namesfavoriteAll);


                        //Log.d(TAG, "Listmane outer: " + list_namesfavoriteAll);


                    }
                    else{
                        Toasty.warning(SunscriptionsDetails.this,"Data not found", Toast.LENGTH_SHORT, true).show();

                    }

                    Log.d(TAG, "Listmane outer: " + listing);



                    pd.dismiss();

                } catch (Exception e) {

                    Toasty.warning(SunscriptionsDetails.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),"Registration Error", Toast.LENGTH_LONG).show();
                //  pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("view", "viewOrder");
              //  params.put("order_id", globalClass.getOrder_id());
                params.put("type", globalClass.getType());

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}