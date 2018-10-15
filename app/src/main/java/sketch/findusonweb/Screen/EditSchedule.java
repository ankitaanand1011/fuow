package sketch.findusonweb.Screen;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Adapter.AdapterClaim;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by developer on 28/9/18.
 */

public class EditSchedule extends AppCompatActivity {
    String id,from_user,to_user,subject,location,start_time,end_time,attendies,overview,meeting;
    EditText et_from_user,et_to_user,et_location,et_start_time,et_end_time,et_attendees,et_overview,et_subject,input_meeting_cost;
    ImageView img_btn,img_btn1,img_close;
    SimpleDateFormat simpleDateFormat;
    TextView tv_submit;
    int mYear, mMonth, mDay;
    GlobalClass globalClass;
String TAG="EDIT";
    Shared_Preference prefrence;
    ProgressDialog pd;
    String date_to_send,date_to_end;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener datepicker;
    DatePickerDialog.OnDateSetListener datepicker1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_schedule);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(EditSchedule.this);

        pd = new ProgressDialog(EditSchedule.this);
        et_from_user=findViewById(R.id.input_from);
        et_to_user=findViewById(R.id.input_to);
        et_location=findViewById(R.id.input_location);
        et_subject=findViewById(R.id.input_subject);
        et_start_time=findViewById(R.id.input_start_date);
        et_end_time=findViewById(R.id.input_end_date);
        et_attendees=findViewById(R.id.input_attendees);
        et_overview=findViewById(R.id.edt_overview);
        img_btn=findViewById(R.id.img_btn);
        img_btn1=findViewById(R.id.img_btn1);
        img_close=findViewById(R.id.img_close);
        tv_submit=findViewById(R.id.tv_submit);
        input_meeting_cost=findViewById(R.id.input_meeting_cost);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        id = getIntent().getExtras().getString("id");
        from_user = getIntent().getExtras().getString("from_user");
        to_user = getIntent().getExtras().getString("to_user");
        subject = getIntent().getExtras().getString("subject");
        location = getIntent().getExtras().getString("location");
        start_time = getIntent().getExtras().getString("start_time");
        end_time = getIntent().getExtras().getString("end_time");
        attendies = getIntent().getExtras().getString("attendies");
        overview = getIntent().getExtras().getString("overview");
        et_from_user.setText(from_user);
        et_to_user.setText(to_user);
        et_location.setText(location);
        et_subject.setText(subject);
        et_start_time.setText(start_time);
        et_end_time.setText(end_time);
        et_attendees.setText(attendies);
        et_overview.setText(overview);
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = new DatePickerDialog(EditSchedule.this, datepicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
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
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from_user = et_from_user.getText().toString();
                to_user = et_to_user.getText().toString();
                subject = et_subject.getText().toString();
                location = et_location.getText().toString();
               /* Float f= Float.parseFloat(price);
                new_tax_price=((20/100)*f);*/
                attendies=et_attendees.getText().toString();
                overview=et_overview.getText().toString();
                meeting=input_meeting_cost.getText().toString();

                editSchedule(from_user,to_user,subject,location,overview,attendies);
                //  user_profile_pic_update_url();
            }
        });

        img_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = new DatePickerDialog(EditSchedule.this, datepicker1, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
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
                et_start_time.setText(date_to_send+date_to_show);
            }
        };
        datepicker1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MMM dd, yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                date_to_end = sdf1.format(myCalendar.getTime());
                String date_to_show = sdf.format(myCalendar.getTime());
                Log.d("TP", date_to_send + " " + date_to_show);
                et_end_time.setText(date_to_end+date_to_show);
            }
        };


    }
    private void editSchedule(final String start_time_new,final String end_time_new,final String subject,final String location_new,final String overview_new,final String attendies_new) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                pd.dismiss();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String success = jobj.get("success").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");
if(success.equals("1")){
    Toasty.success(EditSchedule.this, message, Toast.LENGTH_SHORT, true).show();
Intent intent=new Intent(EditSchedule.this,My_Schedule.class);
startActivity(intent);
}
else {
    Toasty.success(EditSchedule.this, message, Toast.LENGTH_SHORT, true).show();

}
                }

                   /* adapterClaim = new AdapterClaim(ClaimBusiness.this, list_claim);
                    claim_business_lv.setAdapter(adapterClaim);*/


                    /*} else


                    {


                        Toasty.success(BrowseProduct.this, message, Toast.LENGTH_SHORT, true).show();
                    }
*/
                //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                // Log.d(TAG, "Token \n" + message);


                 catch (Exception e) {

                    Toasty.warning(EditSchedule.this, "Username Already Exists", Toast.LENGTH_SHORT, true).show();
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

                params.put("view","manageSchedule");
                params.put("manage","update");
                params.put("start_time",start_time_new);
                params.put("end_time",end_time_new);
                params.put("id",id);
                params.put("subject",subject);
                params.put("location",location_new);
                params.put("overview",overview_new);
                params.put("attendies",attendies);

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}