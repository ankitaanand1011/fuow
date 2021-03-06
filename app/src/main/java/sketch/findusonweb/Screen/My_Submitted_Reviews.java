package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import sketch.findusonweb.Adapter.AdapterReview;

import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

public class My_Submitted_Reviews extends AppCompatActivity {

    RecyclerView rv_submitted_review;
    String TAG = "Favorites";
    GlobalClass globalClass;
    Shared_Preference prefrence;
    AdapterReview adapterReview;
    ImageView back_img;
    ProgressDialog pd;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<HashMap<String,String>> list_namesfavoriteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted_review);

        initialisation();
        functions();

    }

    public void initialisation(){

        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(My_Submitted_Reviews.this);
        pd = new ProgressDialog(My_Submitted_Reviews.this);

        rv_submitted_review=findViewById(R.id.rv_submitted_review);
        back_img=findViewById(R.id.back_img);

    }


    public void functions(){

        list_namesfavoriteAll=new ArrayList<>();

        prefrence.loadPrefrence();

        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_submitted_review.setLayoutManager(mLayoutManager);


        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
                ReviewList();
            }
        } else {
            Toasty.info(My_Submitted_Reviews.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
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
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String listing_id = images1.get("listing_id").toString().replaceAll("\"", "");
                            String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                            String rating_id = images1.get("rating_id").toString().replaceAll("\"", "");
                            String comment_count = images1.get("comment_count").toString().replaceAll("\"", "");
                            String helpful_count = images1.get("helpful_count").toString().replaceAll("\"", "");
                            String helpful_total = images1.get("helpful_total").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");
                            String name = images1.get("name").toString().replaceAll("\"", "");
                            String title = images1.get("title").toString().replaceAll("\"", "");
                            String review = images1.get("review").toString().replaceAll("\"", "");
                            String sync = images1.get("sync").toString().replaceAll("\"", "");
                            String rating = images1.get("rating").toString().replaceAll("\"", "");
                            String comments = images1.get("comments").toString().replaceAll("\"", "");
                            String rating_static = images1.get("rating_static").toString().replaceAll("\"", "");
                            String status = images1.get("status").toString().replaceAll("\"", "");

                            //  Log.d(TAG, "Images 1: " + User_id);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("listing_id", listing_id);
                            hashMap.put("user_id", user_id);
                            hashMap.put("rating_id", rating_id);
                            hashMap.put("comment_count", comment_count);
                            hashMap.put("helpful_count", helpful_count);
                            hashMap.put("helpful_total", helpful_total);
                            hashMap.put("status", status);
                            hashMap.put("date", date);
                            hashMap.put("name", name);
                            hashMap.put("title", title);
                            hashMap.put("review", review);
                            hashMap.put("sync", sync);
                            hashMap.put("comments", comments);
                            hashMap.put("rating", rating);
                            hashMap.put("rating_static", rating_static);


                            list_namesfavoriteAll.add(hashMap);
                            Log.d(TAG, "Listmane: " + list_namesfavoriteAll);

                        }
                        Log.d(TAG, "Listmane outer: " + list_namesfavoriteAll);

                        adapterReview = new AdapterReview(My_Submitted_Reviews.this, list_namesfavoriteAll);
                        rv_submitted_review.setAdapter(adapterReview);
                    }
                    else


                    {


                        Toasty.success(My_Submitted_Reviews.this, result, Toast.LENGTH_SHORT, true).show();
                    }
                    // favorite();

                } catch (Exception e) {

                    Toasty.warning(My_Submitted_Reviews.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("view", "getMyReviews");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}
