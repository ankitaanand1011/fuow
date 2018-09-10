package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import sketch.findusonweb.Adapter.AdapterTrasanction;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

public class Financial_Transaction extends AppCompatActivity {
    RecyclerView rv_financial;
    String TAG = "Favorites";
    GlobalClass globalClass;
    Shared_Preference prefrence;
    AdapterTrasanction adapter_invoice;
    ImageView back_img;
    ProgressDialog pd;
    RecyclerView.LayoutManager mLayoutManager;
    TextView tv_purchases,tv_active,tv_complete,tv_personal,tv_credit_balance;

    ArrayList<HashMap<String,String>> list_namesfavoriteAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_trasanction);

        initialisation();
        functions();

    }

    private void initialisation() {

        rv_financial=findViewById(R.id.rv_financial);
        back_img=findViewById(R.id.back_img);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(Financial_Transaction.this);
        pd = new ProgressDialog(Financial_Transaction.this);

        tv_purchases=findViewById(R.id.tv_purchases);
        tv_active=findViewById(R.id.tv_active);
        tv_complete=findViewById(R.id.tv_complete);
        tv_personal=findViewById(R.id.tv_personal);
        tv_credit_balance=findViewById(R.id.tv_credit_balance);
    }

    private void functions() {

        prefrence.loadPrefrence();
        list_namesfavoriteAll=new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_financial.setLayoutManager(mLayoutManager);

        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));


        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
                ReviewList();
            }
        } else {
            Toasty.info(Financial_Transaction.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
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
                    String purchases = jobj.get("purchases").toString().replaceAll("\"", "");
                    String active = jobj.get("active").toString().replaceAll("\"", "");
                    String complete = jobj.get("complete").toString().replaceAll("\"", "");
                    String personal = jobj.get("personal").toString().replaceAll("\"", "");
                    String credit_balance = jobj.get("credit_balance").toString().replaceAll("\"", "");


                    tv_purchases.setText(purchases);
                    tv_active.setText(active);
                    tv_complete.setText(complete);
                    tv_personal.setText(personal);
                    tv_credit_balance.setText(credit_balance);

                    if (result.equals("1")) {
                        JsonArray data = jobj.getAsJsonArray("data");
                        Log.d(TAG, "Data: " + data);

                        for (int i = 0; i < data.size(); i++) {

                            JsonObject images1 = data.get(i).getAsJsonObject();

                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                            String gateway_id = images1.get("gateway_id").toString().replaceAll("\"", "");
                            String transaction_id = images1.get("transaction_id").toString().replaceAll("\"", "");
                            String type = images1.get("type").toString().replaceAll("\"", "");
                            String invoice_id = images1.get("invoice_id").toString().replaceAll("\"", "");
                            String description = images1.get("description").toString().replaceAll("\"", "");
                            String amount = images1.get("amount").toString().replaceAll("\"", "");
                            String comments = images1.get("comments").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("user_id", user_id);
                            hashMap.put("gateway_id", gateway_id);
                            hashMap.put("transaction_id", transaction_id);
                            hashMap.put("type", type);
                            hashMap.put("invoice_id", invoice_id);
                            hashMap.put("description", description);
                            hashMap.put("amount", amount);
                            hashMap.put("date", date);




                            list_namesfavoriteAll.add(hashMap);
                            Log.d(TAG, "Listmane: " + list_namesfavoriteAll);

                        }
                        Log.d(TAG, "Listmane outer: " + list_namesfavoriteAll);

                        adapter_invoice = new AdapterTrasanction(Financial_Transaction.this, list_namesfavoriteAll);
                        rv_financial.setAdapter(adapter_invoice);
                    }
                    else


                    {


                        Toasty.success(Financial_Transaction.this, result, Toast.LENGTH_SHORT, true).show();
                    }
                    // favorite();

                } catch (Exception e) {

                    Toasty.warning(Financial_Transaction.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("view", "financialTransaction");
                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}