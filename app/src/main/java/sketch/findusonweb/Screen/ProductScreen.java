package sketch.findusonweb.Screen;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Adapter.AdapterProduct;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

import static org.bouncycastle.asn1.x509.X509ObjectIdentifiers.id;


public class ProductScreen extends AppCompatActivity{

    Shared_Preference prefrence;
    GlobalClass globalClass;
    ProgressDialog pd;
    RecyclerView rv_list_product;
    String TAG = "product";
    ImageView back_img;
    TextView tv_add_products;
    RelativeLayout rl_add_product;
    ArrayList<HashMap<String,String>> list_products;
    Spinner spinner_business;
    List<String> list;
    String id;
    ArrayList<String> array;
    ArrayList<String> arrayid;
    ArrayList<String> addProductList = new ArrayList<>();
    ArrayList<HashMap<String, String>> selectedLocation = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();
    ArrayAdapter<String> dataAdapter1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_product);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(ProductScreen.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(ProductScreen.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));

        list_products = new ArrayList<>();





        back_img =findViewById(R.id.back_img);
        rv_list_product =findViewById(R.id.rv_list_product);
        tv_add_products =findViewById(R.id.tv_add_products);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_list_product.setLayoutManager(mLayoutManager);


        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        tv_add_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_product_dialog();

            }
        });

        ViewList();
    }

    public  void ViewList() {
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
                             id = images1.get("id").toString().replaceAll("\"", "");
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
                            String www = images1.get("www").toString().replaceAll("\"", "");
                            String listingfriendly_url = images1.get("listingfriendly_url").toString().replaceAll("\"", "");
                            String listing_location_id = images1.get("listing_location_id").toString().replaceAll("\"", "");
                            String primary_category_id = images1.get("primary_category_id").toString().replaceAll("\"", "");
                            String custom_15 = images1.get("custom_15").toString().replaceAll("\"", "");
                            String custom_58 = images1.get("custom_58").toString().replaceAll("\"", "");


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
                            hashMap.put("custom_15",custom_15);
                            hashMap.put("custom_58",custom_58);
                           /* hashMap.put("www",www);
                            hashMap.put("id",id);
                            hashMap.put("listingfriendly_url",listingfriendly_url);*/
                            // hashMap.put(pricerating",rating);

                            list_products.add(hashMap);
                            Log.d(TAG, "id: " + id);
                            // Toasty.success(SearchListing.this, login, Toast.LENGTH_SHORT, true).show();

                        }

                        AdapterProduct adapterSearch = new AdapterProduct(ProductScreen.this, list_products);
                        rv_list_product.setAdapter(adapterSearch);
                  }else {
                       // Toasty.success(ProductScreen.this, message, Toast.LENGTH_SHORT, true).show();
                   }


                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    //  Log.d(TAG, "Token \n" + logo_allow);


                }
                catch (Exception e) {
                    Log.d(TAG, "Exception: ");

                    Toasty.warning(ProductScreen.this, "Search Not found", Toast.LENGTH_SHORT, true).show();
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

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    private void AddProductList()
    {
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Category " + response.toString());



                Gson gson = new Gson();

                try
                {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);

                    Log.d("jobj", "" + jobj);
                    JsonObject offerObject = jobj.getAsJsonObject();

                    JsonArray jarray=offerObject.getAsJsonArray("name");
                  //  JsonObject offerObject = jobj.getAsJsonObject();
                  //  addProductList.add("Select Business");
                   // JsonArray jarray=jobj.getAsJsonArray();
                    Log.d("jarray", "" + jarray.toString());


                    for (int i = 0; i < jarray.size(); i++) {
                        JsonObject jobj1 = jarray.get(i).getAsJsonObject();

                        String id = jobj1.get("id").toString().replaceAll("\"", "");
                        String title = jobj1.get("title").toString().replaceAll("\"", "");
                        Log.d(TAG, "title: "+title);
                        HashMap<String, String> map_ser = new HashMap<>();


                        map_ser.put("id", id);
                        map_ser.put("title", title);

                        selectedLocation.add(map_ser);
                        array.add(title);
                        arrayid.add(id);
                    }

                        Log.d("title", "" + addProductList);
                    ArrayAdapter<String> adp = new ArrayAdapter<String>
                            (ProductScreen.this, android.R.layout.simple_spinner_item, array);
                    spinner_business.setAdapter(adp);


                        pd.dismiss();




                }catch (Exception e) {

                    Toast.makeText(getApplicationContext(),"data Not found", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    pd.dismiss();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Connection Error", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();



                params.put("view","listing_lists");
                params.put("user_id", globalClass.getId());
                params.put("id",id);
                params.put("action","add");

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    private void add_product_dialog() {

        final Dialog dialog = new Dialog(ProductScreen.this);
        dialog.setContentView(R.layout.add_product_dialog);

        array = new ArrayList<>();
        arrayid = new ArrayList<>();
        array.add("Select Business");
        //list = new ArrayList<>();
        AddProductList();
       /* list.add("Select Business");
        list.add("Ready to Hire");
        list.add("Planning and Budgeting");
        list.add("Need a quote for budgeting purpose");*/



        spinner_business = dialog.findViewById(R.id.spinner_business);



        spinner_business.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //@Override
            public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                // TODO Auto-generated method stub
              //  String item = spinner_business.getItemAtPosition(0).toString();
                String selected = parent.getItemAtPosition(pos).toString();
                String text = spinner_business.getSelectedItem().toString();

               if(!spinner_business.getItemAtPosition(pos).toString().equals("Select Business")){

                   dialog.dismiss();
                   Intent intent = new Intent(ProductScreen.this,AddProductScreen.class);
                   intent.putExtra("id",arrayid.get(pos-1));
                   startActivity(intent);


               }

            }


            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        dialog.show();

    }


}
