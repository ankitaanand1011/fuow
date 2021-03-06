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
import sketch.findusonweb.Adapter.Adapter_invoice;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

public class Invoice extends AppCompatActivity {
    ListView listing;
    String TAG = "Favorites";
    GlobalClass globalClass;
    Shared_Preference prefrence;
    Adapter_invoice adapter_invoice;
    ImageView back_img;
    ProgressDialog pd;
    ArrayList<HashMap<String,String>> list_namesfavoriteAll;
    TextView  total1,tv_total1,paid,tv_paid,unpaid,tv_unpaid,cancelled,tv_cancelled;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinvoice);


        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(Invoice.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(Invoice.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));

        list_namesfavoriteAll=new ArrayList<>();


        listing=findViewById(R.id.invoice_list);
        back_img=findViewById(R.id.back_img);


        total1=findViewById(R.id.total1);
        tv_total1=findViewById(R.id.tv_total1);
        paid=findViewById(R.id.paid);
        tv_paid=findViewById(R.id.tv_paid);
        unpaid=findViewById(R.id.unpaid);
        tv_unpaid=findViewById(R.id.tv_unpaid);
        cancelled=findViewById(R.id.cancelled);
        tv_cancelled=findViewById(R.id.tv_cancelled);




        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
                ReviewList();
            }
        } else {
            Toasty.info(Invoice.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
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

                list_namesfavoriteAll.clear();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    Log.d(TAG, "onResponse: " + jobj);

//                    String result = jobj.get("success").toString().replaceAll("\"", "");

                    String total11 = jobj.get("total").toString().replaceAll("\"", "");
                    String total_amount = jobj.get("total_amount").toString().replaceAll("\"", "");
                    String paid1 = jobj.get("paid").toString().replaceAll("\"", "");
                    String paid_amount = jobj.get("paid_amount").toString().replaceAll("\"", "");
                    String unpaid1 = jobj.get("unpaid").toString().replaceAll("\"", "");
                    String unpaid_amount = jobj.get("unpaid_amount").toString().replaceAll("\"", "");
                    String cancelled1 = jobj.get("cancelled").toString().replaceAll("\"", "");
                    String cancelled_amount = jobj.get("cancelled_amount").toString().replaceAll("\"", "");

                    total1.setText("TOTAL"+ "\n" +"("+total11+")");
                    tv_total1.setText(total_amount);
                    paid.setText("PAID"+ "\n" +"("+paid1+")");
                    tv_paid.setText(paid_amount);
                    unpaid.setText("UNPAID"+ "\n" +"("+unpaid1+")");
                    tv_unpaid.setText(unpaid_amount);
                    cancelled.setText("CANCELLED"+ "\n" +"("+cancelled1+")");
                    tv_cancelled.setText(cancelled_amount);




                   // if (result.equals("1")) {
                        JsonArray data = jobj.getAsJsonArray("data");
                        Log.d(TAG, "Data: " + data);

                        for (int i = 0; i < data.size(); i++) {

                            JsonObject images1 = data.get(i).getAsJsonObject();
                            String invoice_id = images1.get("invoice_id").toString().replaceAll("\"", "");
                            String order_id = images1.get("order_id").toString().replaceAll("\"", "");
                            String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                            String order_number = images1.get("order_number").toString().replaceAll("\"", "");
                            String type = images1.get("type").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");
                            String date_due = images1.get("date").toString().replaceAll("\"", "");
                            String total = images1.get("total").toString().replaceAll("\"", "");

                            String status = images1.get("status").toString().replaceAll("\"", "");

                            //  Log.d(TAG, "Images 1: " + User_id);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("invoice_id", invoice_id);
                            hashMap.put("order_id", order_id);
                            hashMap.put("user_id", user_id);
                            hashMap.put("order_number", order_number);
                            hashMap.put("type", type);
                            hashMap.put("date_due", date_due);
                            hashMap.put("total", total);
                            hashMap.put("status", status);
                            hashMap.put("date", date);



                            list_namesfavoriteAll.add(hashMap);
                            Log.d(TAG, "Listmane: " + list_namesfavoriteAll);

                        }
                        Log.d(TAG, "Listmane outer: " + list_namesfavoriteAll);

                        adapter_invoice = new Adapter_invoice(Invoice.this, list_namesfavoriteAll);
                        listing.setAdapter(adapter_invoice);



                    pd.dismiss();

                } catch (Exception e) {

                    Toasty.warning(Invoice.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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