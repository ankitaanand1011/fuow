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

import sketch.findusonweb.Adapter.Adapter_My_Schedule;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;


public class My_Schedule extends AppCompatActivity{

    Shared_Preference prefrence;
    GlobalClass globalClass;
    ProgressDialog pd;
    RecyclerView my_schedule;
    String TAG = "schedule";
    ImageView back_img;
    Spinner spinner_type;
    ArrayList<String> type;
    ///RelativeLayout rl_add_product;
    ArrayList<HashMap<String,String>> list_products;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_schedule);

        initialisation();
        functions();

    }

    private void initialisation() {
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(My_Schedule.this);

        pd = new ProgressDialog(My_Schedule.this);

        back_img =findViewById(R.id.img_back);
        my_schedule =findViewById(R.id.my_schedule);
        spinner_type =findViewById(R.id.spinner_type);

    }

    private void functions() {
        prefrence.loadPrefrence();
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));

        list_products=new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        my_schedule.setLayoutManager(mLayoutManager);


        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        type = new ArrayList<>();
        type.add("All");
        type.add("Active");
        type.add("Pending");
        type.add("Awaiting Acceptance");
        type.add("Paused");
        type.add("Unapproved");
        type.add("Complete");

        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item1 = parent.getItemAtPosition(position).toString();
                String item;
                Log.d(TAG, "onItemSelected: "+item1);
                switch (item1) {
                    case "All":
                        item = "all";
                        ViewList(item);
                        break;

                    case "Today":
                        item = "today";
                        ViewList(item);
                        break;

                    case "Tomorrow":
                        item = "tomorrow";
                        ViewList(item);
                        break;

                    case "This Week":
                        item = "this_week";
                        ViewList(item);
                        break;

                    case "Next Week":
                        item = "next_week";
                        ViewList(item);
                        break;

                    case "History":
                        item = "history";
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



                Gson gson = new Gson();

                try {

                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    //      String status = jobj.get("status").toString().replaceAll("\"", "");
                    String success = jobj.get("success").toString().replaceAll("\"", "");
                    //    Log.d(TAG, "message: "+message);
                    // final_search.setText(message);

                    if (success.equals("1")) {
                        JsonArray products = jobj.getAsJsonArray("data");

                        for (int i = 0; i < products.size(); i++) {


                            JsonObject images1 = products.get(i).getAsJsonObject();
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String from_user_id = images1.get("from_user_id").toString().replaceAll("\"", "");
                            String to_user_id = images1.get("to_user_id").toString().replaceAll("\"", "");
                            String listing_id = images1.get("listing_id").toString().replaceAll("\"", "");
                            String from_user = images1.get("from_user").toString().replaceAll("\"", "");
                            String to_user = images1.get("to_user").toString().replaceAll("\"", "");
                            String subject = images1.get("subject").toString().replaceAll("\"", "");
                            String location = images1.get("location").toString().replaceAll("\"", "");
                            String start_time = images1.get("start_time").toString().replaceAll("\"", "");
                            String end_time = images1.get("end_time").toString().replaceAll("\"", "");
                            String overview = images1.get("overview").toString().replaceAll("\"", "");
                            String attendies = images1.get("attendies").toString().replaceAll("\"", "");
                            String status = images1.get("status").toString().replaceAll("\"", "");
                            String sync = images1.get("sync").toString().replaceAll("\"", "");


                            HashMap<String, String> hashMap = new HashMap<>();

                            hashMap.put("id", id);
                            hashMap.put("from_user_id", from_user_id);
                            hashMap.put("to_user_id", to_user_id);
                            hashMap.put("listing_id", listing_id);
                            hashMap.put("from_user", from_user);
                            hashMap.put("to_user", to_user);
                            hashMap.put("subject", subject);
                            hashMap.put("location", location);
                            hashMap.put("start_time", start_time);
                            hashMap.put("end_time", end_time);
                            hashMap.put("overview", overview);
                            hashMap.put("attendies", attendies);
                            hashMap.put("status", status);
                            hashMap.put("sync", sync);


                            list_products.add(hashMap);


                        }
                    }
                    Adapter_My_Schedule adapter_my_schedule = new Adapter_My_Schedule(My_Schedule.this, list_products);
                    my_schedule.setAdapter(adapter_my_schedule);

                    pd.dismiss();



                }
                catch (Exception e) {
                    Log.d(TAG, "Exception: ");

                    Toasty.warning(My_Schedule.this, "Search Not found", Toast.LENGTH_SHORT, true).show();
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
                params.put("view","getScheduleByUser");
                params.put("action","all");
                Log.d(TAG, "getID: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
}
