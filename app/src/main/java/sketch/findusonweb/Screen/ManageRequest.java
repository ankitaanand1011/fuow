package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Adapter.AdapterManageRequest;

import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by developer on 21/6/18.
 */

public class ManageRequest extends AppCompatActivity{

    Shared_Preference prefrence;
    GlobalClass globalClass;
    ProgressDialog pd;
    RecyclerView rv_list_product;
    String TAG = "product";
    ImageView back_img;
    Spinner spinner_type;
    ArrayList<String> type;
    ///RelativeLayout rl_add_product;
    ArrayList<HashMap<String,String>> list_products;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_request);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(ManageRequest.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(ManageRequest.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));

        list_products = new ArrayList<>();

        spinner_type=findViewById(R.id.spinner_type);
        back_img =findViewById(R.id.back_img);
        rv_list_product =findViewById(R.id.list_product);
       // rl_add_product =findViewById(R.id.rl_add_product);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_list_product.setLayoutManager(mLayoutManager);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

     /*   post_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PostRequriementScreen.class);
                startActivity(intent);
            }
        });
*/
        type = new ArrayList<>();
        type.add("All");
        type.add("Active");
        type.add("Paused");
        type.add("Pending");
        type.add("Paid");
        type.add("In Progress");
        type.add("Acceptance");
        type.add("Unapproved");
        type.add("Complete");


        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item1 = parent.getItemAtPosition(position).toString();

                //Log.d(TAG, "onItemSelected: "+item);
                String item;
                switch (item1) {
                    case "All":
                        item = "all";
                        ViewList(item);
                        break;

                    case "Active":
                        item = "active";
                        ViewList(item);
                        break;

                    case "Pending":
                        item = "pending";
                        ViewList(item);
                        break;

                    case "Complete":
                        item = "complete";
                        ViewList(item);
                        break;

                    case "Unapproved":
                        item = "unapproved";
                        ViewList(item);
                        break;

                    case "Acceptance":
                        item = "acceptance";
                        ViewList(item);
                        break;


                    case "Paused":
                        item = "paused";
                        ViewList(item);
                        break;

                    case "Paid":
                        item = "paid";
                        ViewList(item);
                        break;

                    case "In Progress":
                        item = "in_progress";
                        ViewList(item);
                        break;




                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, type);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(dataAdapter);







    }

    public void ViewList(final String item) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Invitation response: " + response.toString());


                list_products.clear();
                Gson gson = new Gson();

                try {

                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                         String status_new = jobj.get("success").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");
                    String count = jobj.get("count").toString().replaceAll("\"", "");
                    //    Log.d(TAG, "message: "+message);
                    // final_search.setText(message);

                     if (status_new.equals("1")) {
                         JsonArray products = jobj.getAsJsonArray("data");

                         for (int i = 0; i < products.size(); i++) {


                             JsonObject images1 = products.get(i).getAsJsonObject();
                             String id = images1.get("id").toString().replaceAll("\"", "");
                             String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                             String description = images1.get("description").toString().replaceAll("\"", "");
                             String title = images1.get("title").toString().replaceAll("\"", "");
                             String primary_category = images1.get("primary_category").toString().replaceAll("\"", "");
                             String duration = images1.get("duration").toString().replaceAll("\"", "");
                             String status = images1.get("status").toString().replaceAll("\"", "");
                             String date_requested = images1.get("date_requested").toString().replaceAll("\"", "");
                             String budget = images1.get("budget").toString().replaceAll("\"", "");
                             String isEdit = images1.get("isEdit").toString().replaceAll("\"", "");
                             String isPaused = images1.get("isPaused").toString().replaceAll("\"", "");
                             String isDelete = images1.get("isDelete").toString().replaceAll("\"", "");
                             String isActivate = images1.get("isActivate").toString().replaceAll("\"", "");
                             String completeOrder = images1.get("completeOrder").toString().replaceAll("\"", "");
                             String requestRevision = images1.get("requestRevision").toString().replaceAll("\"", "");


                             HashMap<String, String> hashMap = new HashMap<>();
                             hashMap.put("id", id);
                             hashMap.put("primary_category", primary_category);
                             hashMap.put("duration", duration);
                             hashMap.put("budget", budget);
                             hashMap.put("description", description);
                             hashMap.put("status", status);
                             hashMap.put("title", title);
                             hashMap.put("date_requested", date_requested);
                             hashMap.put("isEdit", isEdit);
                             hashMap.put("isPaused", isPaused);
                             hashMap.put("isDelete", isDelete);
                             hashMap.put("isActivate", isActivate);
                             hashMap.put("completeOrder", completeOrder);
                             hashMap.put("requestRevision", requestRevision);
                             hashMap.put("user_id", user_id);


                             list_products.add(hashMap);


                         }


                         AdapterManageRequest adapterSearch = new AdapterManageRequest(ManageRequest.this, list_products,pd);
                         rv_list_product.setAdapter(adapterSearch);

                         pd.dismiss();
                     }
                     else{
                         Toasty.warning(ManageRequest.this, message, Toast.LENGTH_SHORT, true).show();

                     }


                }
                catch (Exception e) {
                    Log.d(TAG, "Exception: ");

                    Toasty.warning(ManageRequest.this, "Search Not found", Toast.LENGTH_SHORT, true).show();
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
                params.put("view","manageRequests");
                params.put("status",item);
                Log.d(TAG, "getID: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
}
