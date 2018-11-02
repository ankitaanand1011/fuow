package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Adapter.AdapterActionPlan;
import sketch.findusonweb.Adapter.AdapterSalesEnquiry;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by developer on 24/10/18.
 */

public class OnePageBusinessPlan extends AppCompatActivity {
LinearLayout ll_primary_detail;
    Shared_Preference prefrence;
    RelativeLayout rl_requirement_list;
    String TAG = "One Page Business";
    GlobalClass globalClass;
    ProgressDialog pd;
    RecyclerView rv_sction_plan;
    String id,title_product;
    String plan_id;
    ImageView back_img;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterActionPlan adapterActionPlan;
    ArrayList<HashMap<String,String>> list_namesfavoriteAll;
    TextView tv_primary_detail,tv_requirement,tv_save,tv_update,tv_add_action,
            edt_listing_name,edt_mission_statement,edt_big_picture,edt_where_your_business,edt_whre_your_business_six_month,
    edt_whre_business_in_year,edt_where_business_in_three_year,edt_key_offering,edt_key_features,
    edt_key_benefits,edt_buyer_persona,edt_buyer_reason,edt_success_resource,edt_finance_requi,edt_day_to_day,
    edt_target_market,edt_success_factors,edt_key_people,edt_market_strategy,edt_completion,edt_nice_to_have;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_page_business_plan);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(OnePageBusinessPlan.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(OnePageBusinessPlan.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        id=getIntent().getStringExtra("id");
        title_product=getIntent().getStringExtra("title");
        Log.d(TAG, "onCreate: "+id +title_product);
        AddProductList();
        list_namesfavoriteAll=new ArrayList<>();


        rl_requirement_list=findViewById(R.id.rl_requirement_list);
        ll_primary_detail=findViewById(R.id.ll_primary_detail);
        edt_listing_name=findViewById(R.id.edt_title);
        edt_listing_name.setText(title_product);
        rv_sction_plan=findViewById(R.id.rv_requirement);
        edt_nice_to_have=findViewById(R.id.edt_nice_to_have);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        back_img=findViewById(R.id.back_img);
        rv_sction_plan.setLayoutManager(mLayoutManager);
        edt_mission_statement=findViewById(R.id.edt_mission_statement);
        edt_big_picture=findViewById(R.id.edt_big_picture);
        edt_where_your_business=findViewById(R.id.edt_business_now);
        edt_whre_your_business_six_month=findViewById(R.id.edt_six_month);
        edt_whre_business_in_year=findViewById(R.id.edt_12_month);
        edt_where_business_in_three_year=findViewById(R.id.edt_three_years);
        edt_key_offering=findViewById(R.id.edt_key_offering);
        edt_key_features=findViewById(R.id.edt_key_features);
        edt_key_benefits=findViewById(R.id.edt_key_benefits);
        edt_buyer_persona=findViewById(R.id.edt_buyers_persona);
        edt_buyer_reason=findViewById(R.id.edt_buying_reason);
        edt_success_resource=findViewById(R.id.edt_success_resource);
        edt_finance_requi=findViewById(R.id.edt_finance_requirement);
        edt_day_to_day=findViewById(R.id.edt_day_to_day_work);
        edt_target_market=findViewById(R.id.edt_target_market);
        edt_success_factors=findViewById(R.id.edt_success_factors);
        edt_key_people=findViewById(R.id.edt_key_people);
        edt_market_strategy=findViewById(R.id.edt_marget_strategy);
        edt_completion=findViewById(R.id.edt_competition);

        //ll_action_plan=findViewById(R.id.ll_action_plan);
        tv_primary_detail=findViewById(R.id.tv_primary_detail);
        tv_requirement=findViewById(R.id.tv_requirement);
        tv_save=findViewById(R.id.tv_save);
        tv_add_action=findViewById(R.id.tv_add_action);
        tv_update=findViewById(R.id.tv_update);
       // ll_action_plan.setVisibility(View.GONE);
        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_mission_statement = edt_mission_statement.getText().toString().trim();
                String str_big_picture = edt_big_picture.getText().toString().trim();
                String str_where_your_business = edt_where_your_business.getText().toString().trim();
                String str_six_month = edt_whre_your_business_six_month.getText().toString().trim();
                String str_year = edt_whre_business_in_year.getText().toString().trim();
                String str_three_year = edt_where_business_in_three_year.getText().toString().trim();
                String str_key_offering  = edt_key_offering.getText().toString().trim();
                String str_key_features = edt_key_features.getText().toString().trim();
                String str_key_benefits = edt_key_benefits.getText().toString().trim();
                String str_buyer_persona = edt_buyer_persona.getText().toString().trim();
                String str_success_resource = edt_success_resource.getText().toString().trim();
                String str_finance_req = edt_finance_requi.getText().toString().trim();
                String str_day_to_day= edt_day_to_day.getText().toString().trim();
                String str_target_market= edt_target_market.getText().toString().trim();
                String str_success_factor= edt_success_factors.getText().toString().trim();
                String str_key_people= edt_key_people.getText().toString().trim();
                String str_market_strategy= edt_market_strategy.getText().toString().trim();
                String str_competion= edt_completion.getText().toString().trim();
                String str_buying_reason= edt_buyer_reason.getText().toString().trim();
                String str_nice_to_have= edt_nice_to_have.getText().toString().trim();
                if (!edt_listing_name.getText().toString().isEmpty()) {
                    if (!edt_big_picture.getText().toString().isEmpty()) {

                            if (!edt_where_your_business.getText().toString().isEmpty()) {
                                if (!edt_whre_your_business_six_month.getText().toString().isEmpty()) {
                                    if (!edt_whre_business_in_year.getText().toString().isEmpty()) {
                                        if (!edt_where_business_in_three_year.getText().toString().isEmpty()) {

                                            UpdateAction(str_mission_statement,str_big_picture,str_where_your_business,str_six_month,str_year,str_three_year,str_key_offering,str_key_features,str_key_benefits,str_buyer_persona,str_success_resource,str_finance_req,str_day_to_day,str_target_market,str_success_factor,str_key_people,str_market_strategy,str_competion,str_buying_reason,str_nice_to_have);
                                        } else {
                                            Toasty.warning(OnePageBusinessPlan.this, "Enter 5 years business", Toast.LENGTH_SHORT, true).show();
                                        }
                                    } else {
                                        Toasty.warning(OnePageBusinessPlan.this,"Kindly enter 1 to 3 year business", Toast.LENGTH_SHORT, true).show();
                                    }
                                } else {
                                    Toasty.warning(OnePageBusinessPlan.this,"Kindly enter 6 to 12 month business", Toast.LENGTH_SHORT, true).show();
                                }
                            } else {
                                Toasty.warning(OnePageBusinessPlan.this, "Please Enter the business now", Toast.LENGTH_SHORT, true).show();
                            }

                    }else {
                        Toasty.warning(OnePageBusinessPlan.this, "Please Enter Big Picture Plan", Toast.LENGTH_SHORT, true).show();
                    }

                }
                else {
                    Toasty.warning(OnePageBusinessPlan.this, " Please Enter the Listing Name", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_mission_statement = edt_mission_statement.getText().toString().trim();
                String str_big_picture = edt_big_picture.getText().toString().trim();
                String str_where_your_business = edt_where_your_business.getText().toString().trim();
                String str_six_month = edt_whre_your_business_six_month.getText().toString().trim();
                String str_year = edt_whre_business_in_year.getText().toString().trim();
                String str_three_year = edt_where_business_in_three_year.getText().toString().trim();
                String str_key_offering  = edt_key_offering.getText().toString().trim();
                String str_key_features = edt_key_features.getText().toString().trim();
                String str_key_benefits = edt_key_benefits.getText().toString().trim();
                String str_buyer_persona = edt_buyer_persona.getText().toString().trim();
                String str_success_resource = edt_success_resource.getText().toString().trim();
                String str_finance_req = edt_finance_requi.getText().toString().trim();
                String str_day_to_day= edt_day_to_day.getText().toString().trim();
                String str_target_market= edt_target_market.getText().toString().trim();
                String str_success_factor= edt_success_factors.getText().toString().trim();
                String str_key_people= edt_key_people.getText().toString().trim();
                String str_market_strategy= edt_market_strategy.getText().toString().trim();
                String str_competion= edt_completion.getText().toString().trim();
                String str_buying_reason= edt_buyer_reason.getText().toString().trim();
                String str_nice_to_have= edt_nice_to_have.getText().toString().trim();
                if (!edt_listing_name.getText().toString().isEmpty()) {
                    if (!edt_big_picture.getText().toString().isEmpty()) {

                        if (!edt_where_your_business.getText().toString().isEmpty()) {
                            if (!edt_whre_your_business_six_month.getText().toString().isEmpty()) {
                                if (!edt_whre_business_in_year.getText().toString().isEmpty()) {
                                    if (!edt_where_business_in_three_year.getText().toString().isEmpty()) {

                                        SaveAction(str_mission_statement,str_big_picture,str_where_your_business,str_six_month,str_year,str_three_year,str_key_offering,str_key_features,str_key_benefits,str_buyer_persona,str_success_resource,str_finance_req,str_day_to_day,str_target_market,str_success_factor,str_key_people,str_market_strategy,str_competion,str_buying_reason,str_nice_to_have);
                                    } else {
                                        Toasty.warning(OnePageBusinessPlan.this, "Enter 5 years business", Toast.LENGTH_SHORT, true).show();
                                    }
                                } else {
                                    Toasty.warning(OnePageBusinessPlan.this,"Kindly enter 1 to 3 year business", Toast.LENGTH_SHORT, true).show();
                                }
                            } else {
                                Toasty.warning(OnePageBusinessPlan.this,"Kindly enter 6 to 12 month business", Toast.LENGTH_SHORT, true).show();
                            }
                        } else {
                            Toasty.warning(OnePageBusinessPlan.this, "Please Enter the business now", Toast.LENGTH_SHORT, true).show();
                        }

                    }else {
                        Toasty.warning(OnePageBusinessPlan.this, "Please Enter Big Picture Plan", Toast.LENGTH_SHORT, true).show();
                    }

                }
                else {
                    Toasty.warning(OnePageBusinessPlan.this, " Please Enter the Listing Name", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
        tv_add_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent onAction=new Intent(OnePageBusinessPlan.this,AddActionPlan.class);
                onAction.putExtra("id",id);
                onAction.putExtra("plan_id",plan_id);
                startActivity(onAction);
            }
        });
        tv_primary_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_requirement_list.setVisibility(View.GONE);
                ll_primary_detail.setVisibility(View.VISIBLE);
                tv_add_action.setVisibility(View.GONE);
                tv_primary_detail.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_requirement.setBackgroundColor(getResources().getColor(R.color.white));
                tv_requirement.setTextColor(getResources().getColor(R.color.black));
                tv_primary_detail.setTextColor(getResources().getColor(R.color.white));


            }
        });
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_requirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddActionPlan();
                ll_primary_detail.setVisibility(View.GONE);
                rl_requirement_list.setVisibility(View.VISIBLE);
                tv_add_action.setVisibility(View.VISIBLE);
                tv_requirement.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_primary_detail.setBackgroundColor(getResources().getColor(R.color.white));
                tv_requirement.setTextColor(getResources().getColor(R.color.white));
                tv_primary_detail.setTextColor(getResources().getColor(R.color.black));


            }
        });
    }
    private void AddProductList()
    {
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Category " + response.toString());



                Gson gson = new Gson();

                try
                {


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
                         plan_id = data.get("plan_id").toString().replaceAll("\"", "");


                        JsonObject images1 = data.getAsJsonObject("manage");
                        String isAdd = images1.get("isAdd").toString().replaceAll("\"", "");
                        String isEdit = images1.get("isEdit").toString().replaceAll("\"", "");

                        if(isEdit.equals("true") && isAdd.equals("false")){
                            tv_update.setVisibility(View.VISIBLE);
                            tv_save.setVisibility(View.GONE);
                           UpdateBusiness();

                        }
                        else {

                            tv_update.setVisibility(View.GONE);
                            tv_save.setVisibility(View.VISIBLE);
                        }






                        // list_namesfavoriteAll.add(hashMap);
                        Log.d(TAG, "PLan: " + plan_id +isAdd +isEdit);


                        //Log.d(TAG, "Listmane outer: " + list_namesfavoriteAll);


                    }
                    else{
                        Toasty.warning(OnePageBusinessPlan.this,"Data not found", Toast.LENGTH_SHORT, true).show();

                    }


                    pd.dismiss();




                }catch (Exception e) {

                    Toast.makeText(getApplicationContext(),"data Not found", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    pd.dismiss();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Connection Error", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();



                params.put("view","checkOnePagePlan");
                params.put("user_id", globalClass.getId());
                params.put("listing_id",id);


                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    private void UpdateBusiness()
    {
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Category " + response.toString());



                Gson gson = new Gson();

                try
                {


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
                      //  plan_id = data.get("plan_id").toString().replaceAll("\"", "");
                        String id = data.get("id").toString().replaceAll("\"", "");
                        String user_id = data.get("user_id").toString().replaceAll("\"", "");
                        String listing_id = data.get("listing_id").toString().replaceAll("\"", "");
                        String big_picture = data.get("big_picture").toString().replaceAll("\"", "");
                        String business_now = data.get("business_now").toString().replaceAll("\"", "");
                        String six_month = data.get("six_month").toString().replaceAll("\"", "");
                        String one_page = data.get("one_page").toString().replaceAll("\"", "");
                        String month = data.get("12_month").toString().replaceAll("\"", "");
                        String three_years = data.get("three_years").toString().replaceAll("\"", "");
                        String sync = data.get("sync").toString().replaceAll("\"", "");
                        String mission_statement = data.get("mission_statement").toString().replaceAll("\"", "");
                        String key_offerings = data.get("key_offerings").toString().replaceAll("\"", "");
                        String key_benefits = data.get("key_benefits").toString().replaceAll("\"", "");
                        String buyers_persona = data.get("buyers_persona").toString().replaceAll("\"", "");
                        String buying_reasons = data.get("buying_reasons").toString().replaceAll("\"", "");
                        String important_success_resources = data.get("important_success_resources").toString().replaceAll("\"", "");
                        String finance_requirement = data.get("finance_requirement").toString().replaceAll("\"", "");
                        String day_to_day_expenses = data.get("day_to_day_expenses").toString().replaceAll("\"", "");
                        String target_market = data.get("target_market").toString().replaceAll("\"", "");
                        String success_factors = data.get("success_factors").toString().replaceAll("\"", "");
                        String the_key_people = data.get("the_key_people").toString().replaceAll("\"", "");
                        String go_to_market_strategy = data.get("go_to_market_strategy").toString().replaceAll("\"", "");
                        String competition = data.get("competition").toString().replaceAll("\"", "");
                        String key_constraints = data.get("key_constraints").toString().replaceAll("\"", "");
                        String nice_to_have = data.get("nice_to_have").toString().replaceAll("\"", "");
                        String key_features = data.get("key_features").toString().replaceAll("\"", "");

                        edt_mission_statement.setText(mission_statement);
                        edt_big_picture.setText(big_picture);
                        edt_where_your_business.setText(business_now);
                        edt_whre_your_business_six_month.setText(six_month);
                        edt_whre_business_in_year.setText(month);
                        edt_where_business_in_three_year.setText(three_years);
                        edt_key_offering.setText(key_offerings);
                        edt_key_features.setText(key_features);
                        edt_key_benefits.setText(key_benefits);
                        edt_buyer_persona.setText(buyers_persona);
                        edt_buyer_reason.setText(buying_reasons);
                        edt_success_resource.setText(important_success_resources);
                        edt_finance_requi.setText(finance_requirement);
                        edt_day_to_day.setText(day_to_day_expenses);
                        edt_target_market.setText(target_market);
                        edt_success_factors.setText(success_factors);
                        edt_key_people.setText(the_key_people);
                        edt_market_strategy.setText(go_to_market_strategy);
                        edt_completion.setText(competition);
                        edt_nice_to_have.setText(nice_to_have);








                        // list_namesfavoriteAll.add(hashMap);
                        // Log.d(TAG, "Listmane: " + list_namesfavoriteAll);


                        //Log.d(TAG, "Listmane outer: " + list_namesfavoriteAll);


                    }
                    else{
                        Toasty.warning(OnePageBusinessPlan.this,"Data not found", Toast.LENGTH_SHORT, true).show();

                    }


                    pd.dismiss();




                }catch (Exception e) {

                    Toast.makeText(getApplicationContext(),"data Not found", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    pd.dismiss();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Connection Error", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();



                params.put("view","getOnePageDetails");
                params.put("user_id", globalClass.getId());
                params.put("listing_id",id);
                params.put("plan_id",plan_id);


                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    private void AddActionPlan() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                pd.dismiss();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    Log.d(TAG, "onResponse: " + jobj);

                    String result = jobj.get("success").toString().replaceAll("\"", "");
                    if (result.equals("1")) {
                        JsonArray data = jobj.getAsJsonArray("data");
                        Log.d(TAG, "Data: " + data);

                        for (int i = 0; i < data.size(); i++) {

                            JsonObject images1 = data.get(i).getAsJsonObject();
                            String id_plan = images1.get("id").toString().replaceAll("\"", "");
                            String plan_id_new = images1.get("plan_id").toString().replaceAll("\"", "");
                            String strategy = images1.get("strategy").toString().replaceAll("\"", "");
                            String action_plan = images1.get("action_plan").toString().replaceAll("\"", "");
                            String person = images1.get("person").toString().replaceAll("\"", "");
                            String time_completion = images1.get("time_completion").toString().replaceAll("\"", "");
                            String sync = images1.get("sync").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id_plan);
                            hashMap.put("plan_id", plan_id_new);
                            hashMap.put("strategy", strategy);
                            hashMap.put("person", person);
                            hashMap.put("action_plan", action_plan);
                            hashMap.put("time_completion", time_completion);
                            hashMap.put("sync", sync);



                            list_namesfavoriteAll.add(hashMap);
                            Log.d(TAG, "Listmane: " + list_namesfavoriteAll);

                        }
                        Log.d(TAG, "Listmane outer: " + list_namesfavoriteAll);

                        adapterActionPlan = new AdapterActionPlan(OnePageBusinessPlan.this, list_namesfavoriteAll);
                        rv_sction_plan.setAdapter(adapterActionPlan);
                    }
                    else


                    {


                        Toasty.success(OnePageBusinessPlan.this, result, Toast.LENGTH_SHORT, true).show();
                    }
                    // favorite();

                } catch (Exception e) {

                    Toasty.warning(OnePageBusinessPlan.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("user_id", globalClass.getId());
                params.put("view", "getOnePageItems");
                params.put("listing_id", id);
                params.put("plan_id", plan_id);

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    private void SaveAction(final String mission_statement, final String big_picture, final String business_now, final String six_month, final String month, final String three_years, final String key_offerings, final String key_features, final String key_benefits,final String buyers_persona,final String buying_reasons,final String important_success_resources,final String finance_requirement,final String day_to_day_expenses,final String target_market,final String success_factors,final String the_key_people,final String go_to_market_strategy, final String competition,final String nice_to_have) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                pd.dismiss();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String success = jobj.get("success").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");
                    Log.d(TAG, "message: "+message);

                    if (success.equals("1")) {
                        JsonObject data = jobj.getAsJsonObject("data");
                        String Plan_ID =data.get("plan_id").toString().replaceAll("\"", "");
                        String listing_id=data.get("listing_id").toString().replaceAll("\"", "");

                        globalClass.setId(Plan_ID);
                        globalClass.setName(listing_id);


                        Toasty.success(OnePageBusinessPlan.this, message, Toast.LENGTH_SHORT, true).show();

                        pd.dismiss();

                    } else


                    {


                        Toasty.success(OnePageBusinessPlan.this, message, Toast.LENGTH_SHORT, true).show();
                    }

                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    Log.d(TAG, "Token \n" + message);


                } catch (Exception e) {

                    Toasty.warning(OnePageBusinessPlan.this, "Username Already Exists", Toast.LENGTH_SHORT, true).show();
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
                params.put("user_id", globalClass.getId());
                params.put("listing_id", id);

                params.put("big_picture", big_picture);
                params.put("business_now", business_now);
                params.put("six_month", six_month);
                params.put("12_month", month);
                params.put("three_years", three_years);
                params.put("mission_statement", mission_statement);
                params.put("key_offerings", key_offerings);
                params.put("key_features", key_features);
                params.put("key_benefits",key_benefits);
                params.put("buyers_persona",buyers_persona);
                params.put("buying_reasons",buying_reasons);
                params.put("important_success_resources",important_success_resources);
                params.put("finance_requirement",finance_requirement);
                params.put("day_to_day_expenses",day_to_day_expenses);
                params.put("target_market",target_market);
                params.put("success_factors",success_factors);
                params.put("the_key_people",the_key_people);
                params.put("go_to_market_strategy",go_to_market_strategy);
                params.put("competition",competition);
                params.put("nice_to_have",nice_to_have);


                params.put("view", "updateOnePagePlan");

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    private void UpdateAction(final String mission_statement, final String big_picture, final String business_now, final String six_month, final String month, final String three_years, final String key_offerings, final String key_features, final String key_benefits,final String buyers_persona,final String buying_reasons,final String important_success_resources,final String finance_requirement,final String day_to_day_expenses,final String target_market,final String success_factors,final String the_key_people,final String go_to_market_strategy, final String competition,final String nice_to_have) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                pd.dismiss();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String success = jobj.get("success").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");
                    Log.d(TAG, "message: "+message);

                    if (success.equals("1")) {
                        JsonObject data = jobj.getAsJsonObject("data");
                        String Plan_ID =data.get("id").toString().replaceAll("\"", "");
                        String listing_id=data.get("listing_id").toString().replaceAll("\"", "");

                        globalClass.setId(Plan_ID);
                        globalClass.setName(listing_id);


                        Toasty.success(OnePageBusinessPlan.this, message, Toast.LENGTH_SHORT, true).show();

                        pd.dismiss();

                    } else


                    {


                        Toasty.success(OnePageBusinessPlan.this, message, Toast.LENGTH_SHORT, true).show();
                    }

                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    Log.d(TAG, "Token \n" + message);


                } catch (Exception e) {

                    Toasty.warning(OnePageBusinessPlan.this, "Username Already Exists", Toast.LENGTH_SHORT, true).show();
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
                params.put("user_id", globalClass.getId());
                params.put("listing_id", id);

                params.put("big_picture", big_picture);
                params.put("business_now", business_now);
                params.put("six_month", six_month);
                params.put("12_month", month);
                params.put("three_years", three_years);
                params.put("mission_statement", mission_statement);
                params.put("key_offerings", key_offerings);
                params.put("key_features", key_features);
                params.put("key_benefits",key_benefits);
                params.put("buyers_persona",buyers_persona);
                params.put("buying_reasons",buying_reasons);
                params.put("important_success_resources",important_success_resources);
                params.put("finance_requirement",finance_requirement);
                params.put("day_to_day_expenses",day_to_day_expenses);
                params.put("target_market",target_market);
                params.put("success_factors",success_factors);
                params.put("the_key_people",the_key_people);
                params.put("go_to_market_strategy",go_to_market_strategy);
                params.put("competition",competition);
                params.put("nice_to_have",nice_to_have);
                params.put("plan_id",plan_id);

                params.put("view", "updateOnePagePlan");

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}