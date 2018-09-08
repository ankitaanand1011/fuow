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
import sketch.findusonweb.Adapter.AdapterReward;
import sketch.findusonweb.Adapter.AdapterSalesOrder;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;


public class My_Rewards extends AppCompatActivity {

    RecyclerView rv_reward;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterReward adapterReward;
    ImageView back;


    String TAG = "rewards";
    GlobalClass globalClass;
    Shared_Preference prefrence;
    ProgressDialog pd;
    ArrayList<HashMap<String,String>> list_namesfavoriteAll;
    TextView tv_total,tv_used,tv_balance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_rewards);

        initialisation();
        function();
    }

    public void initialisation(){
        rv_reward = findViewById(R.id.rv_reward);
        back=findViewById(R.id.back_img);

        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(My_Rewards.this);
        pd = new ProgressDialog(My_Rewards.this);


        tv_total = findViewById(R.id.tv_total);
        tv_used = findViewById(R.id.tv_used);
        tv_balance = findViewById(R.id.tv_balance);
    }

    public  void function(){

        list_namesfavoriteAll = new ArrayList<>();

        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_reward.setLayoutManager(mLayoutManager);
        
        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
                ReviewList();
            }
        } else {
            Toasty.info(My_Rewards.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }


        back.setOnClickListener(new View.OnClickListener() {
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
//                        JsonObject data=jobj.getAsJsonObject("data");

                        //  String Login = data.get("msg").getAsString().replaceAll("\"", "");
                        String balance = jobj.get("balance").getAsString().replaceAll("\"", "");
                        String used_credits = jobj.get("used_credits").getAsString().replaceAll("\"", "");
                        String total_credits = jobj.get("total_credits").getAsString().replaceAll("\"", "");



                        tv_total.setText(total_credits);
                        tv_used.setText(used_credits);
                        tv_balance.setText(balance);


                        JsonArray data = jobj.getAsJsonArray("data");
                        Log.d(TAG, "Data: " + data);

                        for (int i = 0; i < data.size(); i++) {

                            JsonObject images1 = data.get(i).getAsJsonObject();
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String type = images1.get("type").toString().replaceAll("\"", "");
                            String credit_name = images1.get("credit_name").toString().replaceAll("\"", "");
                            String balance1 = images1.get("balance").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");
                            String points = images1.get("points").toString().replaceAll("\"", "");
                            String Comments = images1.get("Comments").toString().replaceAll("\"", "");
                            String name = images1.get("name").toString().replaceAll("\"", "");
                            String sub_total = images1.get("sub_total").toString().replaceAll("\"", "");

                            //  Log.d(TAG, "Images 1: " + User_id);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("type", type);
                            hashMap.put("credit_name", credit_name);
                            hashMap.put("balance1", balance1);
                            hashMap.put("date", date);
                            hashMap.put("points", points);
                            hashMap.put("Comments", Comments);
                            hashMap.put("name", name);
                            hashMap.put("sub_total", sub_total);


                            list_namesfavoriteAll.add(hashMap);
                            Log.d(TAG, "Listmane: " + list_namesfavoriteAll);

                        }
                        Log.d(TAG, "Listmane outer: " + list_namesfavoriteAll);

                        adapterReward = new AdapterReward(My_Rewards.this, list_namesfavoriteAll);
                        rv_reward.setAdapter(adapterReward);
                    }
                    else


                    {


                        Toasty.success(My_Rewards.this, result, Toast.LENGTH_SHORT, true).show();
                    }
                    // favorite();

                } catch (Exception e) {

                    Toasty.warning(My_Rewards.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("view", "getMyTaskRewardsByUser");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
}
