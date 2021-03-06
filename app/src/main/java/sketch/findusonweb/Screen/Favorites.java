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
import sketch.findusonweb.Adapter.AdapterFavorite;
import sketch.findusonweb.Adapter.AdapterFavoriteDescriptive;
import sketch.findusonweb.Adapter.AdpaterFavoritesAll;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

public class Favorites extends AppCompatActivity {

    TextView all,listings,product;
    ImageView back;
    ListView list_favorite;
    GlobalClass globalClass;
    Shared_Preference prefrence;
    ProgressDialog pd;
    String TAG = "Favorites";
    AdapterFavoriteDescriptive favoriteDescriptive;
    ArrayList<HashMap<String,String>> list_namesfavoriteAll;
    RecyclerView rv_listfavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(Favorites.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(Favorites.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));

        all=findViewById(R.id.all_list);
        listings=findViewById(R.id.listing_list);
        product=findViewById(R.id.product_list);
        back=findViewById(R.id.back_img);
        rv_listfavorite=findViewById(R.id.rv_listfavorite);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_listfavorite.setLayoutManager(mLayoutManager);

        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {
                favoriteALL();
            }
        } else {
            Toasty.info(Favorites.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all.setBackground(getResources().getDrawable(R.drawable.button_yellow_dashboard));
                listings.setBackground(getResources().getDrawable(R.drawable.button_grey_dashboard));
                product.setBackground(getResources().getDrawable(R.drawable.button_grey_dashboard));
                favoriteALL();
            }
        });
        listings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                all.setBackground(getResources().getDrawable(R.drawable.button_grey_dashboard));
                listings.setBackground(getResources().getDrawable(R.drawable.button_yellow_dashboard));
                product.setBackground(getResources().getDrawable(R.drawable.button_grey_dashboard));
                favoriteLisitngs();
            }
        });
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                all.setBackground(getResources().getDrawable(R.drawable.button_grey_dashboard));
                listings.setBackground(getResources().getDrawable(R.drawable.button_grey_dashboard));
                product.setBackground(getResources().getDrawable(R.drawable.button_yellow_dashboard));
                favoriteProduct();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        list_namesfavoriteAll=new ArrayList<>();

    }

    private void favoriteALL() {
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
                list_namesfavoriteAll.clear();
                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    Log.d(TAG, "onResponse: " + jobj);
                    JsonArray data = jobj.getAsJsonArray("data");
                    Log.d(TAG, "Data: " + data);

                    for (int i = 0; i < data.size(); i++) {

                        JsonObject images1 = data.get(i).getAsJsonObject();
                        String title = images1.get("title").toString().replaceAll("\"", "");
                        String id = images1.get("id").toString().replaceAll("\"", "");
                        String listing_id = images1.get("listing_id").toString().replaceAll("\"", "");
                        String description = images1.get("description").toString().replaceAll("\"", "");
                        String rating = images1.get("rating").toString().replaceAll("\"", "");
                        String type = images1.get("type").toString().replaceAll("\"", "");
                        String product_id = images1.get("product_id").toString().replaceAll("\"", "");
                        // String sync = images1.get("sync").toString().replaceAll("\"", "");
                        String img_url = images1.get("img_url").toString().replaceAll("\"", "");

                        //  Log.d(TAG, "Images 1: " + User_id);
                        HashMap<String, String> hashMap = new HashMap<>();
                        //  hashMap.put("user_id", User_id);
                        hashMap.put("description", description);
                        hashMap.put("id", id);
                        hashMap.put("listing_id", listing_id);
                        hashMap.put("title", title);
                        hashMap.put("img_url", img_url);
                        hashMap.put("product_id", product_id);
                        hashMap.put("type", type);
                        //hashMap.put("credits_points", credits_points);
                        hashMap.put("rating", rating);

                        list_namesfavoriteAll.add(hashMap);
                        Log.d(TAG, "Listmane: " + list_namesfavoriteAll);

                    }

                    favoriteDescriptive = new AdapterFavoriteDescriptive(Favorites.this, list_namesfavoriteAll);
                    rv_listfavorite.setAdapter(favoriteDescriptive);
                  //  ReviewList();
                    pd.dismiss();

                } catch (Exception e) {

                    Toasty.warning(Favorites.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("type", "All");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    private void favoriteLisitngs() {
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


                list_namesfavoriteAll.clear();
                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    Log.d(TAG, "onResponse: " + jobj);
                    JsonArray data = jobj.getAsJsonArray("data");
                    Log.d(TAG, "Data: " + data);

                    for (int i = 0; i < data.size(); i++) {

                        JsonObject images1 = data.get(i).getAsJsonObject();
                        String title = images1.get("title").toString().replaceAll("\"", "");
                        String id = images1.get("id").toString().replaceAll("\"", "");
                        String listing_id = images1.get("listing_id").toString().replaceAll("\"", "");
                        String description = images1.get("description").toString().replaceAll("\"", "");
                        String rating = images1.get("rating").toString().replaceAll("\"", "");
                        String type = images1.get("type").toString().replaceAll("\"", "");
                        String product_id = images1.get("product_id").toString().replaceAll("\"", "");
                        // String sync = images1.get("sync").toString().replaceAll("\"", "");
                        String img_url = images1.get("img_url").toString().replaceAll("\"", "");

                        //  Log.d(TAG, "Images 1: " + User_id);
                        HashMap<String, String> hashMap = new HashMap<>();
                        //  hashMap.put("user_id", User_id);
                        hashMap.put("description", description);
                        hashMap.put("id", id);
                        hashMap.put("listing_id", listing_id);
                        hashMap.put("title", title);
                        hashMap.put("img_url", img_url);
                        hashMap.put("product_id", product_id);
                        hashMap.put("type", type);
                        //hashMap.put("credits_points", credits_points);
                        hashMap.put("rating", rating);

                        list_namesfavoriteAll.add(hashMap);
                        Log.d(TAG, "Listmane: " + list_namesfavoriteAll);

                    }

                    favoriteDescriptive = new AdapterFavoriteDescriptive(Favorites.this, list_namesfavoriteAll);
                    rv_listfavorite.setAdapter(favoriteDescriptive);
                    //  ReviewList();
                    pd.dismiss();

                } catch (Exception e) {

                    Toasty.warning(Favorites.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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

    private void favoriteProduct() {
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


                list_namesfavoriteAll.clear();
                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    Log.d(TAG, "onResponse: " + jobj);
                    JsonArray data = jobj.getAsJsonArray("data");
                    Log.d(TAG, "Data: " + data);

                    for (int i = 0; i < data.size(); i++) {

                        JsonObject images1 = data.get(i).getAsJsonObject();
                        String title = images1.get("title").toString().replaceAll("\"", "");
                        String id = images1.get("id").toString().replaceAll("\"", "");
                        String listing_id = images1.get("listing_id").toString().replaceAll("\"", "");
                        String description = images1.get("description").toString().replaceAll("\"", "");
                        String rating = images1.get("rating").toString().replaceAll("\"", "");
                        String type = images1.get("type").toString().replaceAll("\"", "");
                        String product_id = images1.get("product_id").toString().replaceAll("\"", "");
                        // String sync = images1.get("sync").toString().replaceAll("\"", "");
                        String img_url = images1.get("img_url").toString().replaceAll("\"", "");

                        //  Log.d(TAG, "Images 1: " + User_id);
                        HashMap<String, String> hashMap = new HashMap<>();
                        //  hashMap.put("user_id", User_id);
                        hashMap.put("description", description);
                        hashMap.put("id", id);
                        hashMap.put("listing_id", listing_id);
                        hashMap.put("title", title);
                        hashMap.put("img_url", img_url);
                        hashMap.put("product_id", product_id);
                        hashMap.put("type", type);
                        //hashMap.put("credits_points", credits_points);
                        hashMap.put("rating", rating);

                        list_namesfavoriteAll.add(hashMap);
                        Log.d(TAG, "Listmane: " + list_namesfavoriteAll);

                    }

                    favoriteDescriptive = new AdapterFavoriteDescriptive(Favorites.this, list_namesfavoriteAll);
                    rv_listfavorite.setAdapter(favoriteDescriptive);
                    //  ReviewList();

                    pd.dismiss();
                } catch (Exception e) {

                    Toasty.warning(Favorites.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("type", "Product");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


}
