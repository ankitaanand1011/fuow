package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
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
import sketch.findusonweb.Adapter.AdapterCreditHistory;
import sketch.findusonweb.Adapter.AdapterFavorite;
import sketch.findusonweb.Adapter.AdapterToDoScreen;
import sketch.findusonweb.Adapter.AdpaterFavoritesAll;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;


public class DashboardNew extends AppCompatActivity {

    GlobalClass globalClass;
    Shared_Preference prefrence;
    ProgressDialog pd;
    String TAG = "DashboardNew";
    RelativeLayout rl_account,rl_account_detail,
            rl_listing,rl_listing_detail,
            rl_favorite,rl_favorite_detail,
            rl_credit,rl_credit_detail,
            rl_to_do,rl_to_do_detail,
            rl_search,rl_search_detail,
            rl_invoice,rl_invoice_detail;

    RecyclerView rv_listing,rv_favorite,rv_credit,rv_to_do,rv_search,rv_invoice;
    RecyclerView.LayoutManager mLayoutManager,mLayoutManager1,mLayoutManager2,
            mLayoutManager3,mLayoutManager4,mLayoutManager5;


    TextView tv_total,tv_used,tv_balance;

    AdapterFavorite adapterFavorite;
    AdapterCreditHistory adapterCreditHistory;
    AdapterToDoScreen adapterToDo;

    ArrayList<HashMap<String,String>> list_namesfavoriteAll;
    ArrayList<HashMap<String,String>> list_credit;
    ArrayList<HashMap<String,String>> list_todo;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_new);

        initialisation();
        function();
        getFavoritiesListByUser();

    }

    public void initialisation(){
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(DashboardNew.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(DashboardNew.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));



        rl_account = findViewById(R.id.rl_account);
        rl_account_detail = findViewById(R.id.rl_account_detail);
        rl_listing = findViewById(R.id.rl_listing);
        rl_listing_detail = findViewById(R.id.rl_listing_detail);
        rl_favorite = findViewById(R.id.rl_favorite);
        rl_favorite_detail = findViewById(R.id.rl_favorite_detail);
        rl_credit = findViewById(R.id.rl_credit);
        rl_credit_detail = findViewById(R.id.rl_credit_detail);
        rl_to_do = findViewById(R.id.rl_to_do);
        rl_to_do_detail = findViewById(R.id.rl_to_do_detail);
        rl_search = findViewById(R.id.rl_search);
        rl_search_detail = findViewById(R.id.rl_search_detail);
        rl_invoice = findViewById(R.id.rl_invoice);
        rl_invoice_detail = findViewById(R.id.rl_invoice_detail);

        rv_listing = findViewById(R.id.rv_listing);
        rv_favorite = findViewById(R.id.rv_favorite);
        rv_credit = findViewById(R.id.rv_credit);
        rv_to_do = findViewById(R.id.rv_to_do);
        rv_search = findViewById(R.id.rv_search);
        rv_invoice = findViewById(R.id.rv_invoice);


        tv_total = findViewById(R.id.tv_total);
        tv_used = findViewById(R.id.tv_used);
        tv_balance = findViewById(R.id.tv_balance);

    }

    public void function(){

        list_namesfavoriteAll=new ArrayList<>();
        list_credit=new ArrayList<>();
        list_todo=new ArrayList<>();


        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        mLayoutManager3 = new LinearLayoutManager(getApplicationContext());
        mLayoutManager4 = new LinearLayoutManager(getApplicationContext());
        mLayoutManager5 = new LinearLayoutManager(getApplicationContext());

        rl_account_detail.setVisibility(View.GONE);
        rl_listing_detail.setVisibility(View.GONE);
        rl_favorite_detail.setVisibility(View.GONE);
        rl_credit_detail.setVisibility(View.GONE);
        rl_to_do_detail.setVisibility(View.GONE);
        rl_search_detail.setVisibility(View.GONE);
        rl_invoice_detail.setVisibility(View.GONE);

        rv_listing.setLayoutManager(mLayoutManager);
        rv_favorite.setLayoutManager(mLayoutManager1);
        rv_credit.setLayoutManager(mLayoutManager2);
        rv_to_do.setLayoutManager(mLayoutManager3);
        rv_search.setLayoutManager(mLayoutManager4);
        rv_invoice.setLayoutManager(mLayoutManager5);


        rl_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_account_detail.setVisibility(View.VISIBLE);
                rl_listing_detail.setVisibility(View.GONE);
                rl_favorite_detail.setVisibility(View.GONE);
                rl_credit_detail.setVisibility(View.GONE);
                rl_to_do_detail.setVisibility(View.GONE);
                rl_search_detail.setVisibility(View.GONE);
                rl_invoice_detail.setVisibility(View.GONE);
            }
        });

        rl_listing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_account_detail.setVisibility(View.GONE);
                rl_listing_detail.setVisibility(View.VISIBLE);
                rl_favorite_detail.setVisibility(View.GONE);
                rl_credit_detail.setVisibility(View.GONE);
                rl_to_do_detail.setVisibility(View.GONE);
                rl_search_detail.setVisibility(View.GONE);
                rl_invoice_detail.setVisibility(View.GONE);
            }
        });

        rl_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_account_detail.setVisibility(View.GONE);
                rl_listing_detail.setVisibility(View.GONE);
                rl_favorite_detail.setVisibility(View.VISIBLE);
                rl_credit_detail.setVisibility(View.GONE);
                rl_to_do_detail.setVisibility(View.GONE);
                rl_search_detail.setVisibility(View.GONE);
                rl_invoice_detail.setVisibility(View.GONE);
            }
        });

        rl_credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_account_detail.setVisibility(View.GONE);
                rl_listing_detail.setVisibility(View.GONE);
                rl_favorite_detail.setVisibility(View.GONE);
                rl_credit_detail.setVisibility(View.VISIBLE);
                rl_to_do_detail.setVisibility(View.GONE);
                rl_search_detail.setVisibility(View.GONE);
                rl_invoice_detail.setVisibility(View.GONE);
            }
        });

        rl_to_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_account_detail.setVisibility(View.GONE);
                rl_listing_detail.setVisibility(View.GONE);
                rl_favorite_detail.setVisibility(View.GONE);
                rl_credit_detail.setVisibility(View.GONE);
                rl_to_do_detail.setVisibility(View.VISIBLE);
                rl_search_detail.setVisibility(View.GONE);
                rl_invoice_detail.setVisibility(View.GONE);
            }
        });

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_account_detail.setVisibility(View.GONE);
                rl_listing_detail.setVisibility(View.GONE);
                rl_favorite_detail.setVisibility(View.GONE);
                rl_credit_detail.setVisibility(View.GONE);
                rl_to_do_detail.setVisibility(View.GONE);
                rl_search_detail.setVisibility(View.VISIBLE);
                rl_invoice_detail.setVisibility(View.GONE);
            }
        });


        rl_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_account_detail.setVisibility(View.GONE);
                rl_listing_detail.setVisibility(View.GONE);
                rl_favorite_detail.setVisibility(View.GONE);
                rl_credit_detail.setVisibility(View.GONE);
                rl_to_do_detail.setVisibility(View.GONE);
                rl_search_detail.setVisibility(View.GONE);
                rl_invoice_detail.setVisibility(View.VISIBLE);
            }
        });

    }

    private void getFavoritiesListByUser() {
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
                        String listing_id = images1.get("listing_id").toString().replaceAll("\"", "");
                        String listing_title = images1.get("listing_title").toString().replaceAll("\"", "");
                        String listing_url = images1.get("listing_url").toString().replaceAll("\"", "");
                        String type = images1.get("type").toString().replaceAll("\"", "");
                        String product_title = images1.get("product_title").toString().replaceAll("\"", "");
                        // String sync = images1.get("sync").toString().replaceAll("\"", "");
                        String product_url = images1.get("product_url").toString().replaceAll("\"", "");

                        //  Log.d(TAG, "Images 1: " + User_id);
                        HashMap<String, String> hashMap = new HashMap<>();
                        //  hashMap.put("user_id", User_id);
                        //  hashMap.put("listing_id", listing_id);
                        hashMap.put("id",id);
                        hashMap.put("listing_id", listing_id);
                        hashMap.put("listing_title", listing_title);
                        hashMap.put("listing_url", listing_url);
                        hashMap.put("product_title", product_title);
                        hashMap.put("type", type);
                        //hashMap.put("credits_points", credits_points);
                        hashMap.put("product_url", product_url);


                        list_namesfavoriteAll.add(hashMap);
                        Log.d(TAG, "Listmane: " + list_namesfavoriteAll);

                    }

                    adapterFavorite = new AdapterFavorite(DashboardNew.this, list_namesfavoriteAll);
                    rv_favorite.setAdapter(adapterFavorite);
                    //  ReviewList();
                    getCreditHistory();

                } catch (Exception e) {

                    Toasty.warning(DashboardNew.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("view", "getFavoritiesListByUser");
                params.put("type", "Listing");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    private void getCreditHistory() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

     //   pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                //pd.dismiss();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    Log.d(TAG, "onResponse: " + jobj);

                    String result = jobj.get("success").toString().replaceAll("\"", "");
                    String total_credits = jobj.get("total_credits").toString().replaceAll("\"", "");
                    String used_credits = jobj.get("used_credits").toString().replaceAll("\"", "");
                    if (result.equals("1")) {
                        JsonArray data = jobj.getAsJsonArray("records");
                        Log.d(TAG, "Data: " + data);

                        for (int i = 0; i < data.size(); i++) {

                            JsonObject images1 = data.get(i).getAsJsonObject();
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String type = images1.get("type").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");
                            String name = images1.get("name").toString().replaceAll("\"", "");
                            String Creditpoints = images1.get("points").toString().replaceAll("\"", "");

                            String Comments = images1.get("Comments").toString().replaceAll("\"", "");
                            String HTMLConvert = Html.fromHtml(name).toString();
                            //  Log.d(TAG, "Images 1: " + User_id);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("type", type);
                            hashMap.put("points", Creditpoints);
                            hashMap.put("date", date);
                            hashMap.put("name", HTMLConvert);
                            hashMap.put("Comments", Comments);


                            list_credit.add(hashMap);
                            Log.d(TAG, "Listmane: " + list_credit);

                        }
                        tv_total.setText(total_credits);
                        tv_used.setText(used_credits);
                        Log.d(TAG, "Listmane outer: " + list_credit);

                        adapterCreditHistory = new AdapterCreditHistory(DashboardNew.this, list_credit);
                        rv_credit.setAdapter(adapterCreditHistory);
                    }
                    else


                    {


                        Toasty.success(DashboardNew.this, result, Toast.LENGTH_SHORT, true).show();
                    }
                    getToDoListByUser();

                } catch (Exception e) {

                    Toasty.warning(DashboardNew.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("view", "getCreditHistory");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    private void getToDoListByUser() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                //pd.dismiss();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    Log.d(TAG, "onResponse: " + jobj);
                    JsonArray data = jobj.getAsJsonArray("data");
                    Log.d(TAG, "Data: " + data);

                    for (int i = 0; i < data.size(); i++) {

                        JsonObject images1 = data.get(i).getAsJsonObject();
                        //  String User_id = images1.get("user_id").toString().replaceAll("\"", "");
                        // String listing_id = images1.get("listing_id").toString().replaceAll("\"", "");
                        String credit_id = images1.get("credit_id").toString().replaceAll("\"", "");
                        String points = images1.get("points").toString().replaceAll("\"", "");
                        //  String date = images1.get("date").toString().replaceAll("\"", "");
                        String status = images1.get("status").toString().replaceAll("\"", "");
                        // String sync = images1.get("sync").toString().replaceAll("\"", "");
                        String todo_task = images1.get("todo_task").toString().replaceAll("\"", "");
//                        String credits_points = images1.get("credits_points").toString().replaceAll("\"", "");
                        String fw_id = images1.get("fw_id").toString().replaceAll("\"", "");

                        //  Log.d(TAG, "Images 1: " + User_id);
                        HashMap<String, String> hashMap = new HashMap<>();
                        //  hashMap.put("user_id", User_id);
                        //  hashMap.put("listing_id", listing_id);
                        hashMap.put("credit_id", credit_id);
                        hashMap.put("points", points);
                        // hashMap.put("date", date);
                        hashMap.put("status", status);
                        // hashMap.put("sync", sync);
                        hashMap.put("todo_task", todo_task);
                        //hashMap.put("credits_points", credits_points);
                        hashMap.put("fw_id", fw_id);


                        list_todo.add(hashMap);
                        //Log.d(TAG, "Listmane: " + list_todo);

                    }
                    Log.d(TAG, "Listmane outer: " + list_todo);

                    adapterToDo = new AdapterToDoScreen(DashboardNew.this, list_todo);
                    rv_to_do.setAdapter(adapterToDo);

                   // favorite();

                    pd.dismiss();

                } catch (Exception e) {

                    Toasty.warning(DashboardNew.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),"Registration Error", Toast.LENGTH_LONG).show();
                //  pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("user_id", globalClass.getId());
                params.put("view", "getToDoListByUser");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}
