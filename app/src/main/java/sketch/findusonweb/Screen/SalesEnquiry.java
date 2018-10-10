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
import android.widget.ImageView;
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
import sketch.findusonweb.Adapter.AdapterEarning;
import sketch.findusonweb.Adapter.AdapterSalesEnquiry;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;


public class SalesEnquiry extends AppCompatActivity{

    AdapterSalesEnquiry adapterSalesEnquiry;
    RecyclerView rv_sales_enquiry;
    String TAG = "Earnings";
    GlobalClass globalClass;
    Shared_Preference prefrence;
    ImageView back_img;
    ProgressDialog pd;
    TextView tv_add_products;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<HashMap<String,String>> list_namesfavoriteAll;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales_enquiry);

        initialisation();
        functions();

    }

    public void initialisation(){

        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(SalesEnquiry.this);
        pd = new ProgressDialog(SalesEnquiry.this);
        tv_add_products=findViewById(R.id.tv_contact_request);
        rv_sales_enquiry=findViewById(R.id.rv_sales_enquiry);
        back_img=findViewById(R.id.back_img);

    }


    public void functions(){

        list_namesfavoriteAll=new ArrayList<>();

        prefrence.loadPrefrence();

        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_sales_enquiry.setLayoutManager(mLayoutManager);


        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
                ReviewList();
            }
        } else {
            Toasty.info(SalesEnquiry.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }



        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_add_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addContact=new Intent(SalesEnquiry.this,AddContactRequest.class);
                startActivity(addContact);
            }
        });
    }


    private void ReviewList() {
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
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                            String message = images1.get("message").toString().replaceAll("\"", "");
                            String status = images1.get("status").toString().replaceAll("\"", "");
                            String date_requested = images1.get("date_requested").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("user_id", user_id);
                            hashMap.put("message", message);
                            hashMap.put("status", status);
                            hashMap.put("date_requested", date_requested);



                            list_namesfavoriteAll.add(hashMap);
                            Log.d(TAG, "Listmane: " + list_namesfavoriteAll);

                        }
                        Log.d(TAG, "Listmane outer: " + list_namesfavoriteAll);

                        adapterSalesEnquiry = new AdapterSalesEnquiry(SalesEnquiry.this, list_namesfavoriteAll);
                        rv_sales_enquiry.setAdapter(adapterSalesEnquiry);
                    }
                    else


                    {


                        Toasty.success(SalesEnquiry.this, result, Toast.LENGTH_SHORT, true).show();
                    }
                    // favorite();

                } catch (Exception e) {

                    Toasty.warning(SalesEnquiry.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("view", "getSalesEnquiryByUser");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
}
