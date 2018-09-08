package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
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

import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;


public class EarningsScreen extends AppCompatActivity {


    RecyclerView rv_earning;
    String TAG = "Earnings";
    GlobalClass globalClass;
    Shared_Preference prefrence;
    AdapterEarning adapterEarning;
    ImageView back_img;
    ProgressDialog pd;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<HashMap<String,String>> list_namesfavoriteAll;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earning_screen);

        initialisation();
        functions();

    }

    public void initialisation(){

        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(EarningsScreen.this);
        pd = new ProgressDialog(EarningsScreen.this);

        rv_earning=findViewById(R.id.rv_earning);
        back_img=findViewById(R.id.back_img);

    }


    public void functions(){

        list_namesfavoriteAll=new ArrayList<>();

        prefrence.loadPrefrence();

        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_earning.setLayoutManager(mLayoutManager);


        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
                ReviewList();
            }
        } else {
            Toasty.info(EarningsScreen.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }



        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                            String order_id = images1.get("order_id").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");
                            String title = images1.get("title").toString().replaceAll("\"", "");
                            String listing_name = images1.get("listing_name").toString().replaceAll("\"", "");
                            String amount = images1.get("amount").toString().replaceAll("\"", "");
                            String type = images1.get("type").toString().replaceAll("\"", "");
                            String commission_amount = images1.get("commission_amount").toString().replaceAll("\"", "");
                            String sales_earning = images1.get("sales_earning").toString().replaceAll("\"", "");
                            String claimed = images1.get("claimed").toString().replaceAll("\"", "");
                            String status = images1.get("status").toString().replaceAll("\"", "");
                            String order_status = images1.get("order_status").toString().replaceAll("\"", "");
                            String invoice_status = images1.get("invoice_status").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("order_id", order_id);
                            hashMap.put("date", date);
                            hashMap.put("title", title);
                            hashMap.put("type", type);
                            hashMap.put("amount", amount);
                            hashMap.put("sales_earning", sales_earning);
                            hashMap.put("commission_amount", commission_amount);
                            hashMap.put("claimed", claimed);
                            hashMap.put("status", status);
                            hashMap.put("listing_name", listing_name);
                            hashMap.put("order_status", order_status);
                            hashMap.put("invoice_status", invoice_status);


                            list_namesfavoriteAll.add(hashMap);
                            Log.d(TAG, "Listmane: " + list_namesfavoriteAll);

                        }
                        Log.d(TAG, "Listmane outer: " + list_namesfavoriteAll);

                        adapterEarning = new AdapterEarning(EarningsScreen.this, list_namesfavoriteAll,pd);
                        rv_earning.setAdapter(adapterEarning);
                    }
                    else


                    {


                        Toasty.success(EarningsScreen.this, result, Toast.LENGTH_SHORT, true).show();
                    }
                    // favorite();

                } catch (Exception e) {

                    Toasty.warning(EarningsScreen.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("view", "myEarnings");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
}
