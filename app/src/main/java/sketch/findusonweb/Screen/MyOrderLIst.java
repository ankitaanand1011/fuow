package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import sketch.findusonweb.Adapter.AdapterMyOrder;
import sketch.findusonweb.Adapter.Adapter_invite_friend;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

public class MyOrderLIst extends AppCompatActivity {
    ListView listing;
    String TAG = "Favorites";
    GlobalClass globalClass;
    Shared_Preference prefrence;
    AdapterMyOrder adapter_invoice;
    ImageView back_img;
    ProgressDialog pd;
    ArrayList<HashMap<String,String>> list_namesfavoriteAll;
    TextView total1 ,tv_total1,active, tv_active,pending,tv_pending,cancelled,
            tv_cancelled ,completed ,tv_completed,fraud ,tv_fraud,suspended,tv_suspended;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_list);

        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(MyOrderLIst.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(MyOrderLIst.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));

        list_namesfavoriteAll=new ArrayList<>();

        listing=findViewById(R.id.my_order_list);
        back_img = findViewById(R.id.back_img);


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



        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
                ReviewList();
            }
        } else {
            Toasty.info(MyOrderLIst.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
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
                    tv_suspended.setText(suspended_amount);


                    if (result.equals("1")) {
                        JsonArray data = jobj.getAsJsonArray("data");
                        Log.d(TAG, "Data: " + data);

                        for (int i = 0; i < data.size(); i++) {

                            JsonObject images1 = data.get(i).getAsJsonObject();
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String order_id = images1.get("order_id").toString().replaceAll("\"", "");
                            String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                            String name = images1.get("name").toString().replaceAll("\"", "");
                            String title = images1.get("title").toString().replaceAll("\"", "");
                            String type = images1.get("type").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");
                            String order_status = images1.get("order_status").toString().replaceAll("\"", "");
                            String payment_status = images1.get("payment_status").toString().replaceAll("\"", "");
                            String next_due_date = images1.get("next_due_date").toString().replaceAll("\"", "");
                            String type_id = images1.get("type_id").toString().replaceAll("\"", "");
                            String friendly_url = images1.get("friendly_url").toString().replaceAll("\"", "");
                            String renewable = images1.get("renewable").toString().replaceAll("\"", "");
                            String product_status = images1.get("product_status").toString().replaceAll("\"", "");
                            String amount = images1.get("amount").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("order_id", order_id);
                            hashMap.put("user_id", user_id);
                            hashMap.put("name", name);
                            hashMap.put("title", title);
                            hashMap.put("type", type);
                            hashMap.put("date", date);
                            hashMap.put("next_due_date", next_due_date);
                            hashMap.put("type_id", type_id);
                            hashMap.put("friendly_url", friendly_url);
                            hashMap.put("status", order_status);
                            hashMap.put("renewable", renewable);
                            hashMap.put("product_status", product_status);
                            hashMap.put("amount", amount);
                            hashMap.put("payment_status", payment_status);




                            list_namesfavoriteAll.add(hashMap);
                            Log.d(TAG, "Listmane: " + list_namesfavoriteAll);

                        }
                        Log.d(TAG, "Listmane outer: " + list_namesfavoriteAll);

                        adapter_invoice = new AdapterMyOrder(MyOrderLIst.this, list_namesfavoriteAll);
                        listing.setAdapter(adapter_invoice);
                    }
                    else


                    {


                        Toasty.success(MyOrderLIst.this, result, Toast.LENGTH_SHORT, true).show();
                    }
                    // favorite();

                } catch (Exception e) {

                    Toasty.warning(MyOrderLIst.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("view", "getAllOrdersByUserID");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}