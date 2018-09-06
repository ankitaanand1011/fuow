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
import sketch.findusonweb.Adapter.AdapterFavorite;
import sketch.findusonweb.Adapter.AdapterManageProposal;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by Developer on 8/24/18.
 */

public class Manage_Proposal extends AppCompatActivity {
    RecyclerView rv_manage_proposal;
    GlobalClass globalClass;
    Shared_Preference prefrence;
    ProgressDialog pd;
    String TAG = "Manage_Proposal";
    ImageView back;
    ArrayList<HashMap<String,String>> list_namesfavoriteAll;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterManageProposal adapterManageProposal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_proposal_new);
        initialisation();
        function();

        manageProposals();

    }


    public void initialisation(){
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(Manage_Proposal.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(Manage_Proposal.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));



        rv_manage_proposal = findViewById(R.id.rv_manage_proposal);
        back=findViewById(R.id.back_img);

    }

    public void function(){

        list_namesfavoriteAll=new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_manage_proposal.setLayoutManager(mLayoutManager);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void manageProposals() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();
        list_namesfavoriteAll.clear();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                // pd.dismiss();
                list_namesfavoriteAll.clear();
                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    Log.d(TAG, "onResponse: " + jobj);
                    JsonArray data = jobj.getAsJsonArray("data");
                    Log.d(TAG, "Data: " + data);

                    for (int i = 0; i < data.size(); i++) {

                        JsonObject images1 = data.get(i).getAsJsonObject();
                        String id = images1.get("id").toString().replaceAll("\"", "");
                        String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                        String request_id = images1.get("request_id").toString().replaceAll("\"", "");
                        String description = images1.get("description").toString().replaceAll("\"", "");
                        String iterations = images1.get("iterations").toString().replaceAll("\"", "");
                        String duration = images1.get("duration").toString().replaceAll("\"", "");
                        String budget = images1.get("budget").toString().replaceAll("\"", "");
                        String status = images1.get("status").toString().replaceAll("\"", "");
                        String date_requested = images1.get("date_requested").toString().replaceAll("\"", "");
                        String title = images1.get("title").toString().replaceAll("\"", "");
                        String rr_count = images1.get("rr_count").toString().replaceAll("\"", "");
                        String attachment_id = images1.get("attachment_id").toString().replaceAll("\"", "");


                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("id", id);
                        hashMap.put("user_id", user_id);
                        hashMap.put("request_id",request_id);
                        hashMap.put("description", description);
                        hashMap.put("iterations", iterations);
                        hashMap.put("duration", duration);
                        hashMap.put("budget", budget);
                        hashMap.put("status", status);
                        hashMap.put("date_requested", date_requested);
                        hashMap.put("title", title);
                        hashMap.put("rr_count", rr_count);
                        hashMap.put("attachment_id", attachment_id);


                        list_namesfavoriteAll.add(hashMap);
                        Log.d(TAG, "Listmane: " + list_namesfavoriteAll);

                    }

                    adapterManageProposal = new AdapterManageProposal(Manage_Proposal.this, list_namesfavoriteAll);
                    rv_manage_proposal.setAdapter(adapterManageProposal);

                    pd.dismiss();


                } catch (Exception e) {

                    Toasty.warning(Manage_Proposal.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("view", "manageProposals");
                params.put("status", "");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}
