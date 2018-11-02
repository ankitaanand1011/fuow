package sketch.findusonweb.Screen;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by developer on 25/10/18.
 */

public class AddActionPlan extends AppCompatActivity {
    GlobalClass globalClass;
    ProgressDialog pd;
    ArrayAdapter dataAdapter1;
    Shared_Preference prefrence;
    String TAG="Add Action";
    ArrayList<String> array;
    String item,id,plan_id;
    ImageView img_btn,back_img;
    Calendar myCalendar = Calendar.getInstance();
    String date_to_send;
    DatePickerDialog.OnDateSetListener datepicker;
    SimpleDateFormat simpleDateFormat;
    EditText edt_actionPlan,edt_time_comp,edt_person_responsible,input_end_date;
    TextView save_action;
    Spinner spinner_select_strategy;
    int mYear, mMonth, mDay;
    ArrayList<HashMap<String, String>> selectedCategory = new ArrayList<>();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_action_plan);

        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(AddActionPlan.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(AddActionPlan.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        id=getIntent().getStringExtra("id");
        plan_id=getIntent().getStringExtra("plan_id");
        back_img=findViewById(R.id.back_img);
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        pd.setMessage(getResources().getString(R.string.loading));
        array = new ArrayList<>();
        if (globalClass.isNetworkAvailable()) {

            getStrategy();

            //  getCategory_list();
            // getLocation(globalClass.Location);

        } else {

            Toasty.warning(getApplicationContext(), getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();

        }
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        img_btn=findViewById(R.id.img_btn1);
        spinner_select_strategy = findViewById(R.id.spinner_strategy);
        edt_actionPlan=findViewById(R.id.edt_action_plan);
        save_action=findViewById(R.id.tv_save_action);
        edt_time_comp=findViewById(R.id.input_end_date);
        edt_person_responsible=findViewById(R.id.edt_person_responsible);
        input_end_date=findViewById(R.id.input_end_date);
        spinner_select_strategy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                // Locate the textviews in activity_main.xml
                item = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemSelected: "+item);
                // Set the text followed by the position
/*
                if (spinner_select_category.getId() == R.id.spinner_category) {



                    if (position != 0) {
                       // cat_id = selectedCategory.get(position - 1).get("id");
                       // String str_service = selectedCategory.get(position - 1).get("name");

                        //Log.d(TAG, "onItemSelected: "+cat_id);


                    }

                }
*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        save_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_action_plan = edt_actionPlan.getText().toString().trim();
                String str_date = input_end_date.getText().toString().trim();
                String str_responsible = edt_person_responsible.getText().toString().trim();
                if (!edt_actionPlan.getText().toString().isEmpty()) {
                    if (!input_end_date.getText().toString().isEmpty()) {

                        if (!edt_person_responsible.getText().toString().isEmpty()) {

                            AddActionResponse(str_action_plan, str_date, str_responsible,item);
                        } else {
                            Toasty.warning(AddActionPlan.this, "Please Enter Big Picture Plan", Toast.LENGTH_SHORT, true).show();

                        }
                    } else {
                        Toasty.warning(AddActionPlan.this, "Please Enter Big Picture Plan", Toast.LENGTH_SHORT, true).show();

                    }


                } else {
                    Toasty.warning(AddActionPlan.this, "Please Enter Big Picture Plan", Toast.LENGTH_SHORT, true).show();

                }

            }


        });


        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = new DatePickerDialog(AddActionPlan.this, datepicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                String today_date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                Date d = null;

                try {
                    d = simpleDateFormat.parse(today_date);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                dpd.getDatePicker();
                dpd.show();
            }


        });
        datepicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MMM dd, yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                date_to_send = sdf1.format(myCalendar.getTime());
                String date_to_show = sdf.format(myCalendar.getTime());
                Log.d("TP", date_to_send + " " + date_to_show);
                input_end_date.setText(date_to_send+date_to_show);
            }
        };


    }
    private void AddActionResponse(final String action_plan,final String time_completion,final String person_responsible,final String person)
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
                    Log.d(TAG, "onResponse: " + jobj);
                    String result = jobj.get("success").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");

                    //  JsonArray data = jobj.getAsJsonArray("data");
                    //  Log.d(TAG, "Data: " + data);

                    if (result.equals("1")) {

                       edt_actionPlan.setText("");
                       edt_person_responsible.setText("");
                       edt_time_comp.setText("");
                        Toasty.success(AddActionPlan.this,message, Toast.LENGTH_SHORT, true).show();

                    }
                    else{
                        Toasty.warning(AddActionPlan.this,message, Toast.LENGTH_SHORT, true).show();

                    }


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



                params.put("view","addOnePageItem");
                params.put("user_id", globalClass.getId());
                params.put("listing_id",id);
                params.put("plan_id",plan_id);
                params.put("strategy",person);
                params.put("action_plan",action_plan);
                params.put("time",time_completion);
                params.put("person",person_responsible);


                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
private void getStrategy(){
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
                array.add("Select Strategy");
                JsonArray jarray=offerObject.getAsJsonArray("name");
                Log.d("jarray", "" + jarray.toString());
                ArrayList<String> array = new ArrayList<>();
                for (int i = 0; i < jarray.size(); i++) {
                    JsonObject jobj1 = jarray.get(i).getAsJsonObject();
                    //get the object

                    //JsonObject jobj1 = jarray.get(i).getAsJsonObject();
                   // String id =jobj1.get("id").toString().replaceAll("\"", "");
                    String title= jobj1.get("title").toString().replaceAll("\"", "");
                    HashMap<String, String> map_ser = new HashMap<>();


                    //map_ser.put("id", id);
                    map_ser.put("title", title);

                    selectedCategory.add(map_ser);
                    array.add(title);

                    Log.d("title", "" + array);




                    dataAdapter1 = new ArrayAdapter(AddActionPlan.this, android.R.layout.simple_spinner_item, array);
                    // dataAdapter1.setDropDownViewResource(R.layout.spinner_color);
                    spinner_select_strategy.setAdapter(dataAdapter1);





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



            params.put("view","strategyDropDown");

            return params;
        }

    };

    // Adding request to request queue
    GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
    strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

}

}