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
import sketch.findusonweb.Adapter.AdapterDueInvoices;
import sketch.findusonweb.Adapter.AdapterSalesOrder;
import sketch.findusonweb.Adapter.Adapter_Due_Invoice_Detail;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;


public class DueInvoices extends AppCompatActivity {

    RecyclerView rv_due_invoice;
    String TAG = "DueInvoices";
    GlobalClass globalClass;
    Shared_Preference prefrence;
    Adapter_Due_Invoice_Detail adapter_due_invoice_detail;
    ImageView back_img;
    ProgressDialog pd;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<HashMap<String,String>> list_namesfavoriteAll;
    TextView total1 ,tv_total1,paid, tv_active,unpaid,tv_pending,cancelled,
            tv_cancelled ,completed ,tv_completed,fraud ,tv_fraud,suspended,tv_suspended;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.due_invoices_screen);

        initialisation();
        functions();

    }

    public void initialisation(){

        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(DueInvoices.this);
        pd = new ProgressDialog(DueInvoices.this);

        rv_due_invoice=findViewById(R.id.rv_due_invoice);
        back_img=findViewById(R.id.back_img);

        total1 = findViewById(R.id.total1);
        tv_total1 = findViewById(R.id.tv_total1);
        paid = findViewById(R.id.active);
        tv_active = findViewById(R.id.tv_active);
        unpaid = findViewById(R.id.pending);
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
        rv_due_invoice.setLayoutManager(mLayoutManager);



        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
                ReviewList();
            }
        } else {
            Toasty.info(DueInvoices.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
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

                    String paid_new = jobj.get("paid").toString().replaceAll("\"", "");

                    String total = jobj.get("total").toString().replaceAll("\"", "");
                    String total_amount = jobj.get("total_amount").toString().replaceAll("\"", "");
                    String paid_amount = jobj.get("paid_amount").toString().replaceAll("\"", "");
                    String unpaid_amount = jobj.get("unpaid_amount").toString().replaceAll("\"", "");
                    String unpaid_new = jobj.get("unpaid").toString().replaceAll("\"", "");

                    String cancelled1 = jobj.get("cancelled").toString().replaceAll("\"", "");
                    String cancelled_amount = jobj.get("cancelled_amount").toString().replaceAll("\"", "");


                    total1.setText("TOTAL"+ "\n" +"("+total+")");
                    tv_total1.setText(total_amount);
                    paid.setText("PAID"+ "\n" +"("+paid_new+")");
                    tv_active.setText(paid_amount);
                    unpaid.setText("UNPAID"+ "\n" +"("+unpaid_new+")");
                    tv_pending.setText(unpaid_amount);
                    completed.setText("CANCELLED"+ "\n" +"("+cancelled1+")");
                    tv_completed.setText(cancelled_amount);


                  //  if (result.equals("1")) {
                        JsonArray data = jobj.getAsJsonArray("data");
                        Log.d(TAG, "Data: " + data);

                        for (int i = 0; i < data.size(); i++) {


                            JsonObject images1 = data.get(i).getAsJsonObject();
                            String invoice_id = images1.get("invoice_id").toString().replaceAll("\"", "");
                            String order_id = images1.get("order_id").toString().replaceAll("\"", "");
                            String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                            String order_number = images1.get("order_number").toString().replaceAll("\"", "");
                            String total_new = images1.get("total").toString().replaceAll("\"", "");
                            String type = images1.get("type").toString().replaceAll("\"", "");
                            String status = images1.get("status").toString().replaceAll("\"", "");
                            String balance = images1.get("balance").toString().replaceAll("\"", "");
                            String date_due = images1.get("date_due").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");

                            //  Log.d(TAG, "Images 1: " + User_id);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("invoice_id", invoice_id);
                            hashMap.put("order_id", order_id);
                            hashMap.put("date", date);
                            hashMap.put("user_id", user_id);
                            hashMap.put("order_number", order_number);
                            hashMap.put("type", type);
                            hashMap.put("total_new", total_new);
                            hashMap.put("date_due", date_due);
                            hashMap.put("balance", balance);
                            hashMap.put("status", status);


                            list_namesfavoriteAll.add(hashMap);
                            Log.d(TAG, "Listmane: " + list_namesfavoriteAll);

                        }
                        Log.d(TAG, "Listmane outer: " + list_namesfavoriteAll);

                        adapter_due_invoice_detail = new Adapter_Due_Invoice_Detail(DueInvoices.this, list_namesfavoriteAll);
                        rv_due_invoice.setAdapter(adapter_due_invoice_detail);

                   /* else


                    {


                        Toasty.success(DueInvoices.this, result, Toast.LENGTH_SHORT, true).show();
                    }*/
                    // favorite();

                } catch (Exception e) {

                    Toasty.warning(DueInvoices.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("view", "getMyInvoices");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }




}
