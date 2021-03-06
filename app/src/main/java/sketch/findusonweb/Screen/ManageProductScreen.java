package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import sketch.findusonweb.Adapter.AdapterManageProduct;
import sketch.findusonweb.Adapter.AdapterProduct;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by Developer on 9/3/18.
 */

public class ManageProductScreen extends AppCompatActivity {

    Shared_Preference prefrence;
    GlobalClass globalClass;
    ProgressDialog pd;
    RecyclerView rv_manage_product;
    String TAG = "product";
    String id;
    ImageView back_img;
    TextView tv_add_products;
    RelativeLayout rl_add_product;
    ArrayList<HashMap<String,String>> list_products;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_product_screen);

        initialisation();
        function();
    }

    private void initialisation() {

        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(ManageProductScreen.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(ManageProductScreen.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        id=getIntent().getStringExtra("id");
        list_products = new ArrayList<>();



        back_img =findViewById(R.id.back_img);
        rv_manage_product =findViewById(R.id.rv_manage_product);
        tv_add_products =findViewById(R.id.tv_add_products);

    }

    private void function() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_manage_product.setLayoutManager(mLayoutManager);


        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        tv_add_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageProductScreen.this,AddProductScreen.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        ViewList();

    }

    public void ViewList() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Invitation response: " + response.toString());

                pd.dismiss();

                Gson gson = new Gson();

                try {

                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String success = jobj.get("success").toString().replaceAll("\"", "");
                    // String message = jobj.get("message").toString().replaceAll("\"", "");

                    if (success.equals("1")) {
                        JsonArray products = jobj.getAsJsonArray("data");

                        for (int i = 0; i < products.size(); i++) {



                            JsonObject images1 = products.get(i).getAsJsonObject();
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String listing_id = images1.get("listing_id").toString().replaceAll("\"", "");
                            String title = images1.get("title").toString().replaceAll("\"", "");
                            String friendly_url = images1.get("friendly_url").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");
                            String description = images1.get("description").toString().replaceAll("\"", "");
                            String price = images1.get("price").toString().replaceAll("\"", "");
                            String expire_date = images1.get("expire_date").toString().replaceAll("\"", "");
                            String type = images1.get("type").toString().replaceAll("\"", "");
                            String listing_name = images1.get("listing_name").toString().replaceAll("\"", "");
                            String images = images1.get("images").toString().replaceAll("\"", "");
                            String keywords = images1.get("keywords").toString().replaceAll("\"", "");
                            String image_url = images1.get("image_url").toString().replaceAll("\"", "");
                            String listingfriendly_url = images1.get("listingfriendly_url").toString().replaceAll("\"", "");
                            String listing_location_id = images1.get("listing_location_id").toString().replaceAll("\"", "");
                            String primary_category_id = images1.get("primary_category_id").toString().replaceAll("\"", "");


                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("listing_id", listing_id);
                            hashMap.put("friendly_url", friendly_url);
                            hashMap.put("title", title);
                            hashMap.put("date", date);
                            hashMap.put("description", description);
                            hashMap.put("price",price);
                            hashMap.put("expire_date",expire_date);
                            hashMap.put("type",type);
                            hashMap.put("listing_name",listing_name);
                            hashMap.put("images",images);
                            hashMap.put("keywords",keywords);
                            hashMap.put("image_url",image_url);

                            hashMap.put("listingfriendly_url",listingfriendly_url);
                            // hashMap.put(pricerating",rating);

                            list_products.add(hashMap);
                            Log.d(TAG, "id: " + id);
                            // Toasty.success(SearchListing.this, login, Toast.LENGTH_SHORT, true).show();

                        }

                        AdapterManageProduct adapterManageProduct = new AdapterManageProduct(ManageProductScreen.this, list_products);
                        rv_manage_product.setAdapter(adapterManageProduct);
                    }else {
                        // Toasty.success(ManageProductScreen.this, message, Toast.LENGTH_SHORT, true).show();
                    }


                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    //  Log.d(TAG, "Token \n" + logo_allow);


                }
                catch (Exception e) {
                    Log.d(TAG, "Exception: ");

                    Toasty.warning(ManageProductScreen.this, "Search Not found", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Login Error: " + error.getMessage());
                // Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();



                params.put("user_id", globalClass.getId());
                params.put("view","myProducts");
                params.put("listing_id",id);

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
}
