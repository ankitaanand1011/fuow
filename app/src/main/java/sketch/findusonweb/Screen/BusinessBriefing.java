package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
import java.util.Iterator;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Adapter.AdapterActionPlan;
import sketch.findusonweb.Adapter.AdapterBusinessBriefing;
import sketch.findusonweb.Adapter.AdapterBusinessBriefingTitle3;
import sketch.findusonweb.Adapter.AdapterbusinessBriefingTitle2;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by developer on 26/10/18.
 */

public class BusinessBriefing  extends AppCompatActivity {
    GlobalClass globalClass;
    ProgressDialog pd;
    ArrayAdapter dataAdapter1;
    String TAG="Business Briefing";
    String id;
    ImageView back_img;
    RelativeLayout rl_text;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.LayoutManager mLayoutManager_new;
    RecyclerView.LayoutManager mLayoutManager_title;
    ArrayList<HashMap<String,String>> list_brief;
    ArrayList<HashMap<String,String>> list_service;
    ArrayList<HashMap<String,String>> list_needs;
    Shared_Preference prefrence;
    String field_name;
    RecyclerView rv_details,rv_service,rv_needs;
    AdapterBusinessBriefing businessBriefing;
    AdapterbusinessBriefingTitle2 adapterbusinessBriefingTitle2;
    AdapterBusinessBriefingTitle3 adapterBusinessBriefingTitle3;
    TextView tv_title,tv_title_offering,tv_title_need,tv_save;
    String title,title1,title2;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_briefing);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(BusinessBriefing.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(BusinessBriefing.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        rv_details=findViewById(R.id.rv_details);
        rl_text=findViewById(R.id.rl_text);
        rv_service=findViewById(R.id.rv_offering);
        rv_needs=findViewById(R.id.rv_need);
        tv_title_need=findViewById(R.id.tv_title_need);
        tv_title=findViewById(R.id.tv_title);
        back_img=findViewById(R.id.back_img);
        tv_save=findViewById(R.id.tv_save);
        tv_title_offering=findViewById(R.id.tv_title_offering);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager_new = new LinearLayoutManager(getApplicationContext());
        mLayoutManager_title = new LinearLayoutManager(getApplicationContext());
        rv_details.setLayoutManager(mLayoutManager);
        rv_service.setLayoutManager(mLayoutManager_new);
        rv_needs.setLayoutManager(mLayoutManager_title);
        id=getIntent().getStringExtra("id");
        list_brief = new ArrayList<>();
        list_service = new ArrayList<>();
        list_needs = new ArrayList<>();
        AddActionPlan();
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddActionPlanUpdate();
            }
        });

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
                        JsonObject data = jobj.getAsJsonObject("data");
                        Log.d(TAG, "Data: " + data);
                        JsonObject data_sequence = data.getAsJsonObject("0");
                        JsonObject data_sequence1= data.getAsJsonObject("1");
                        JsonObject data_sequence2= data.getAsJsonObject("2");

                       title = data_sequence.get("title1").toString().replaceAll("\"", "");

                            JsonArray element1 = data_sequence.getAsJsonArray("elements1");
                            for (int j = 0; j < element1.size(); j++) {
                                JsonObject element_new = element1.get(j).getAsJsonObject();
                                field_name = element_new.get("field_name").toString().replaceAll("\"", "");
                                String label_name = element_new.get("label_name").toString().replaceAll("\"", "");
                                String value = element_new.get("value").toString().replaceAll("\"", "");
                                String type = element_new.get("type").toString().replaceAll("\"", "");
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("field_name", field_name);
                                hashMap.put("label_name", label_name);
                                hashMap.put("value", value);
                                hashMap.put("type", type);
                                list_brief.add(hashMap);

                            }
                        tv_title.setText(title);
                            businessBriefing = new AdapterBusinessBriefing(BusinessBriefing.this, list_brief);
                            rv_details.setAdapter(businessBriefing);
                        Log.d(TAG, "onResponse: "+businessBriefing);
                        title1 = data_sequence1.get("title2").toString().replaceAll("\"", "");

                        JsonArray element2 = data_sequence1.getAsJsonArray("elements2");
                        for (int j = 0; j < element2.size(); j++) {
                            JsonObject element_new = element2.get(j).getAsJsonObject();
                            String field_name = element_new.get("field_name").toString().replaceAll("\"", "");
                            String label_name = element_new.get("label_name").toString().replaceAll("\"", "");
                            String value = element_new.get("value").toString().replaceAll("\"", "");
                            String type = element_new.get("type").toString().replaceAll("\"", "");
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("field_name", field_name);
                            hashMap.put("label_name", label_name);
                            hashMap.put("value", value);
                            hashMap.put("type", type);
                            list_service.add(hashMap);

                        }
                        tv_title_offering.setText(title1);

                        adapterbusinessBriefingTitle2 = new AdapterbusinessBriefingTitle2(BusinessBriefing.this, list_service);
                        rv_service.setAdapter(adapterbusinessBriefingTitle2);
                        title2 = data_sequence2.get("title3").toString().replaceAll("\"", "");

                        JsonArray element3 = data_sequence2.getAsJsonArray("elements3");
                        for (int j = 0; j < element3.size(); j++) {
                            JsonObject element_new = element3.get(j).getAsJsonObject();
                             field_name = element_new.get("field_name").toString().replaceAll("\"", "");
                            String label_name = element_new.get("label_name").toString().replaceAll("\"", "");
                            String value = element_new.get("value").toString().replaceAll("\"", "");
                            String type = element_new.get("type").toString().replaceAll("\"", "");
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("field_name", field_name);
                            hashMap.put("label_name", label_name);
                            hashMap.put("value", value);
                            hashMap.put("type", type);
                            list_needs.add(hashMap);

                        }
                        tv_title_need.setText(title2);

                        adapterBusinessBriefingTitle3 = new AdapterBusinessBriefingTitle3(BusinessBriefing.this, list_needs);
                        rv_needs.setAdapter(adapterBusinessBriefingTitle3);
                           // list_products.add(hashMap);
                            Log.d(TAG, "Listmane: " + list_brief);

                        }
                        Log.d(TAG, "Listmane outer: " + list_brief);




                    // favorite();

                } catch (Exception e) {

                    Toasty.warning(BusinessBriefing.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("view", "businessBriefing");
                params.put("listing_id", id);


                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    private void AddActionPlanUpdate() {
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
                    String data = jobj.get("data").toString().replaceAll("\"", "");

                    String result = jobj.get("success").toString().replaceAll("\"", "");
                    if (result.equals("1")) {

                        Toasty.success(BusinessBriefing.this,data, Toast.LENGTH_SHORT, true).show();

                    }

                    else{
                        Toasty.warning(BusinessBriefing.this,data, Toast.LENGTH_SHORT, true).show();

                    }



                    // favorite();

                } catch (Exception e) {

                    Toasty.warning(BusinessBriefing.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("view", "submit_business_briefing");
                params.put("listing_id", id);

                // businessBriefing
                ArrayList<String> listStr = businessBriefing.getListString();
                Log.d(TAG, "listStr: "+listStr);

                for (int i = 0; i < list_brief.size(); i++){

                    params.put(list_brief.get(i).get("field_name"), listStr.get(i));

                }
                ArrayList<String> list1Str = adapterbusinessBriefingTitle2.getListString();
                Log.d(TAG, "list1Str: "+list1Str);

                for (int i = 0; i < list_service.size(); i++){

                    params.put(list_service.get(i).get("field_name"), list1Str.get(i));

                }
                ArrayList<String> list2Str = adapterBusinessBriefingTitle3.getListString();
                Log.d(TAG, "list2Str: "+list2Str);

                for (int i = 0; i < list_needs.size(); i++){

                    params.put(list_needs.get(i).get("field_name"), list2Str.get(i));

                }
               // Log.d(TAG, "getParams: "+params);
                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}