package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by developer on 10/10/18.
 */

public class AddContactRequest extends AppCompatActivity {

    String TAG = "Add_contact_request";

    ImageView back_button;
    TextView attach_data,attach_data_link,tv_submit;
    String city_id,cat_id;
    Shared_Preference prefrence;
    Spinner spinner_select_category,spinner_city;
    Uri URI = null;
    String ratedValue;
    private static final int PICK_FROM_GALLERY = 101;
    GlobalClass globalClass;
    ArrayAdapter<String> dataAdapter1,dataAdapter7;
    ProgressDialog pd;
    RatingBar ratingBar;
    RadioGroup rg;
    RadioButton rb;
    ArrayList<String> array;
    ImageView down_arrow,down_arrow1;
    int columnIndex;
    String id;
    EditText edt_description,et_first_name,et_last_name,edit_contact,edit_username,edit_usernumber,edit_email;
    String fname,lname , description, business_contact, username,user_phone,postcode,usermail;

    ArrayList<HashMap<String, String>> selectedLocation = new ArrayList<>();
    ArrayList<HashMap<String, String>> selectedCategory = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();
    ArrayList<String> location = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_request);
        spinner_select_category = findViewById(R.id.spinner_category);

        pd = new ProgressDialog(AddContactRequest.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        //rg=findViewById(R.id.radiogroup);
        array = new ArrayList<>();
         et_first_name=findViewById(R.id.et_first_name);
         et_last_name=findViewById(R.id.et_last_name);
        // ratingBar =  findViewById(R.id.rating);
        edt_description=findViewById(R.id.edt_description);
        // edit_email=findViewById(R.id.edt_email);
        // edit_username=findViewById(R.id.edt_username);
        spinner_city = findViewById(R.id.spinner_location);
        // attach_data=findViewById(R.id.attach_data);
        // spinner_days=findViewById(R.id.spinner_days);
        // attach_data_link=findViewById(R.id.attach_data_name);
        back_button = findViewById(R.id.back_img);
        // down_arrow=findViewById(R.id.down_arraowpost);
        //down_arrow1=findViewById(R.id.down_arrowlocation);
        tv_submit = findViewById(R.id.tv_submit);
        // edit_btitle=findViewById(R.id.edt_btitle);
        edt_description = findViewById(R.id.edt_description);

        globalClass = (GlobalClass) getApplicationContext();
        et_first_name.setText(globalClass.getFname());
           et_last_name.setText(globalClass.getLname());
        // edit_email.setText(globalClass.getEmail());
        prefrence = new Shared_Preference(AddContactRequest.this);
        Log.d(TAG, "Mail id: " + globalClass.getEmail());
        prefrence.loadPrefrence();


        if (globalClass.isNetworkAvailable()) {

            getCategory(globalClass.Category);
            getLocation(globalClass.Location);


            //  getCategory_list();
            // getLocation(globalClass.Location);

        } else {

            Toasty.warning(getApplicationContext(), getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();

        }
        spinner_select_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                // Locate the textviews in activity_main.xml
                String item = parent.getItemAtPosition(position).toString();
                Spinner spinner_select_category = (Spinner) parent;
                // Set the text followed by the position
                if (spinner_select_category.getId() == R.id.spinner_category) {


                    if (position != 0) {
                        cat_id = selectedCategory.get(position - 1).get("id");
                        String str_service = selectedCategory.get(position - 1).get("name");

                        Log.d(TAG, "onItemSelected: " + cat_id);


                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                // Locate the textviews in activity_main.xml
                String item = parent.getItemAtPosition(position).toString();
                Spinner spinner_days = (Spinner) parent;
                // Set the text followed by the position
                if (spinner_days.getId() == R.id.spinner_location) {


                    if (position != 0) {
                        city_id = selectedCategory.get(position - 1).get("id");
                        //String str_service = selectedCategory.get(position - 1).get("name");

                        Log.d(TAG, "onItemSelected: " + cat_id);


                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname = et_first_name.getText().toString();
                lname = et_last_name.getText().toString();
                description = edt_description.getText().toString();


                Log.d(TAG, "cat_id "+cat_id);

                postjob(fname,lname,description,cat_id,city_id);

            }
        });
    }





    private void getCategory(final String Category)
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
                    category.add("Select Category");
                    JsonArray jarray=offerObject.getAsJsonArray("name");
                    Log.d("jarray", "" + jarray.toString());
                    ArrayList<String> array = new ArrayList<>();
                    for (int i = 0; i < jarray.size(); i++) {
                        JsonObject jobj1 = jarray.get(i).getAsJsonObject();
                        //get the object

                        //JsonObject jobj1 = jarray.get(i).getAsJsonObject();
                        String id =jobj1.get("id").toString().replaceAll("\"", "");
                        String title= jobj1.get("title").toString().replaceAll("\"", "");
                        HashMap<String, String> map_ser = new HashMap<>();


                        map_ser.put("id", id);
                        map_ser.put("title", title);

                        selectedCategory.add(map_ser);
                        array.add(title);

                        Log.d("title", "" + array);

                        dataAdapter1 = new ArrayAdapter<String>(AddContactRequest.this,R.layout.spinner_color,R.id.txt,array)
                        {

                            @Override
                            public boolean isEnabled(int position) {
                                return position != 0;
                            }

                            @Override
                            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                                View view = super.getDropDownView(position, convertView, parent);

                                if (position == 0) {



                                } else {
                                }
                                return view;


                            }
                        };
                        // ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_layout, array_spinner);
                        dataAdapter1.setDropDownViewResource(R.layout.spinner_color);
                        spinner_select_category.setAdapter(dataAdapter1);


                        pd.dismiss();

                    }


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



                params.put("view",Category);

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    private void getLocation(final String Location_new)
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
                    location.add("Select Country");
                    JsonArray jarray=offerObject.getAsJsonArray("name");
                    Log.d("jarray", "" + jarray.toString());
                    ArrayList<String> array = new ArrayList<>();

                    for (int i = 0; i < jarray.size(); i++) {
                        JsonObject jobj1 = jarray.get(i).getAsJsonObject();
                        //get the object
                        //JsonObject jobj1 = jarray.get(i).getAsJsonObject();
                        String id =jobj1.get("id").toString().replaceAll("\"", "");
                        String title= jobj1.get("title").toString().replaceAll("\"", "");

                        HashMap<String, String> map_ser = new HashMap<>();


                        map_ser.put("id", id);
                        map_ser.put("title", title);

                        selectedLocation.add(map_ser);
                        array.add(title);

                        Log.d("title", "" + location);
                        dataAdapter7 = new ArrayAdapter<String>(AddContactRequest.this,R.layout.spinner_color,R.id.txt,array)
                        {

                            @Override
                            public boolean isEnabled(int position) {
                                return position != 0;
                            }

                            @Override
                            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                                View view = super.getDropDownView(position, convertView, parent);


                                if (position == 0) {

                                    // tv.setTextColor(getResources().getColor(R.color.white));

                                } else {
                                    // tv.setTextColor(Color.WHITE);
                                }
                                return view;


                            }
                        };
                        // ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_layout, array_spinner);
                        dataAdapter7.setDropDownViewResource(R.layout.spinner_color);
                        spinner_city.setAdapter(dataAdapter7);


                        pd.dismiss();

                    }


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



                params.put("view",Location_new);

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


    private void postjob(final String fname,final String lname,final String description,final String cat_id,final String city_id
    ){
        pd.show();
        String url = AppConfig.URL_DEV;
        AsyncHttpClient cl = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("view","addSalesEnquiry");
        params.put("user_id",globalClass.getId());
        params.put("message",description);
        params.put("primary_category_id",cat_id);
        params.put("location_id",city_id);





        Log.d(TAG , "URL "+url);
        Log.d(TAG , "params "+params.toString());


        int DEFAULT_TIMEOUT = 30 * 1000;
        cl.setMaxRetriesAndTimeout(5 , DEFAULT_TIMEOUT);
        cl.post(url,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                if (response != null) {
                    Log.d(TAG, "postjob- " + response.toString());
                    try {

                        //JSONObject result = response.getJSONObject("result");

                        String success = response.optString("success");
                        String message = response.getString("message");
                        //   pd.dismiss();
                        if(success.equals("1")) {


                            Intent i = new Intent(AddContactRequest.this, SalesEnquiry.class);


                            startActivity(i);

                            Toasty.success(AddContactRequest.this, message, Toast.LENGTH_SHORT, true).show();
                        }
                        else {
                            Toasty.success(AddContactRequest.this, message, Toast.LENGTH_SHORT, true).show();

                        }
                        pd.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                // pd.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("Failed: ", ""+statusCode);
                Log.d("Error : ", "" + throwable);
            }
        });


    }

}