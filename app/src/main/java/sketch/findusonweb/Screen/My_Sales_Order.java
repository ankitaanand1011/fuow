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
import sketch.findusonweb.Adapter.AdapterSalesOrder;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;


public class My_Sales_Order extends AppCompatActivity {

    RecyclerView rv_sales_order;
    String TAG = "sales_order";
    GlobalClass globalClass;
    Shared_Preference prefrence;
    AdapterSalesOrder adapterSalesOrder;
    ImageView back_img;
    ProgressDialog pd;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<HashMap<String,String>> list_namesfavoriteAll;
    TextView total1 ,tv_total1,active, tv_active,pending,tv_pending,cancelled,
            tv_cancelled ,completed ,tv_completed,fraud ,tv_fraud,suspended,tv_suspended;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales_order_screen);

        initialisation();
        functions();

    }

    public void initialisation(){

        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(My_Sales_Order.this);
        pd = new ProgressDialog(My_Sales_Order.this);

        rv_sales_order=findViewById(R.id.rv_sales_order);
        back_img=findViewById(R.id.back_img);

        total1 = findViewById(R.id.total1);
        tv_total1 = findViewById(R.id.tv_total1);
        active = findViewById(R.id.active);
        tv_active = findViewById(R.id.tv_active);
        pending = findViewById(R.id.pending);
        tv_pending = findViewById(R.id.tv_pending);
        cancelled = findViewById(R.id.cancelled);
        tv_cancelled = findViewById(R.id.tv_cancelled);
        completed = findViewById(R.id.completed);
        tv_completed = findViewById(R.id.tv_completed);
        fraud = findViewById(R.id.fraud);
        tv_fraud = findViewById(R.id.tv_fraud);
        suspended = findViewById(R.id.suspended);
        tv_suspended = findViewById(R.id.tv_suspended);


    }


    public void functions(){

        list_namesfavoriteAll=new ArrayList<>();

        prefrence.loadPrefrence();

        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_sales_order.setLayoutManager(mLayoutManager);



        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
                ReviewList();
            }
        } else {
            Toasty.info(My_Sales_Order.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
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

                    String total = jobj.get("total").toString().replaceAll("\"", "");
                    String total_amount = jobj.get("total_amount").toString().replaceAll("\"", "");
                    String active1 = jobj.get("active").toString().replaceAll("\"", "");
                    String active_amount = jobj.get("active_amount").toString().replaceAll("\"", "");
                    String pending1 = jobj.get("pending").toString().replaceAll("\"", "");
                    String pending_amount = jobj.get("pending_amount").toString().replaceAll("\"", "");
                    String cancelled1 = jobj.get("cancelled").toString().replaceAll("\"", "");
                    String cancelled_amount = jobj.get("cancelled_amount").toString().replaceAll("\"", "");
                    String completed1 = jobj.get("completed").toString().replaceAll("\"", "");
                    String completed_amount = jobj.get("completed_amount").toString().replaceAll("\"", "");
                    String fraud1 = jobj.get("fraud").toString().replaceAll("\"", "");
                    String fraud_amount = jobj.get("fraud_amount").toString().replaceAll("\"", "");
                    String suspended1 = jobj.get("suspended").toString().replaceAll("\"", "");
                    String suspended_amount = jobj.get("suspended_amount").toString().replaceAll("\"", "");

                    total1.setText("TOTAL"+ "\n" +"("+total+")");
                    tv_total1.setText(total_amount);
                    active.setText("ACTIVE"+ "\n" +"("+active1+")");
                    tv_active.setText(active_amount);
                    pending.setText("PENDING"+ "\n" +"("+pending1+")");
                    tv_pending.setText(pending_amount);
                    cancelled.setText("CANCELLED"+ "\n" +"("+cancelled1+")");
                    tv_cancelled.setText(cancelled_amount);
                    completed.setText("COMPLETED"+ "\n" +"("+completed1+")");
                    tv_completed.setText(completed_amount);
                    fraud.setText("FRAUD"+ "\n" +"("+fraud1+")");
                    tv_fraud.setText(fraud_amount);
                    suspended.setText("SUSPENDED"+ "\n" +"("+suspended1+")");
                    tv_suspended.setText(globalClass.pound+suspended_amount);


                    if (result.equals("1")) {
                        JsonArray data = jobj.getAsJsonArray("data");
                        Log.d(TAG, "Data: " + data);

                        for (int i = 0; i < data.size(); i++) {


                            JsonObject images1 = data.get(i).getAsJsonObject();
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String order_id = images1.get("order_id").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");
                            String title = images1.get("title").toString().replaceAll("\"", "");
                            String listing_name = images1.get("listing_name").toString().replaceAll("\"", "");
                            String amount = images1.get("amount").toString().replaceAll("\"", "");
                            String type = images1.get("type").toString().replaceAll("\"", "");
                            String order_status = images1.get("order_status").toString().replaceAll("\"", "");
                            String invoice_status = images1.get("invoice_status").toString().replaceAll("\"", "");
                            String buyer = images1.get("buyer").toString().replaceAll("\"", "");
                            String status = images1.get("status").toString().replaceAll("\"", "");

                            //  Log.d(TAG, "Images 1: " + User_id);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("order_id", order_id);
                            hashMap.put("date", date);
                            hashMap.put("title", title);
                            hashMap.put("listing_name", listing_name);
                            hashMap.put("type", type);
                            hashMap.put("amount", amount);
                            hashMap.put("order_status", order_status);
                            hashMap.put("buyer", buyer);
                            hashMap.put("invoice_status", invoice_status);
                            hashMap.put("status", status);


                            list_namesfavoriteAll.add(hashMap);
                            Log.d(TAG, "Listmane: " + list_namesfavoriteAll);

                        }
                        Log.d(TAG, "Listmane outer: " + list_namesfavoriteAll);

                        adapterSalesOrder = new AdapterSalesOrder(My_Sales_Order.this, list_namesfavoriteAll);
                        rv_sales_order.setAdapter(adapterSalesOrder);
                    }
                    else


                    {


                        Toasty.success(My_Sales_Order.this, result, Toast.LENGTH_SHORT, true).show();
                    }
                    // favorite();

                } catch (Exception e) {

                    Toasty.warning(My_Sales_Order.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("view", "mySalesOrders");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


}
