package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
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
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Adapter.AdapterProduct;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by developer on 29/10/18.
 */

public class EditSummary extends AppCompatActivity {
    Shared_Preference prefrence;
    GlobalClass globalClass;
    ProgressDialog pd;
    String TAG="Edit Summary";
    DisplayImageOptions defaultOptions;
    ImageLoader loader;
    ArrayAdapter<String> dataAdapter1, dataAdapter7, dataAdapter;
    String city_id,cat_id;

    TextView tv_userId_value;
    ImageView imageView2;
    Spinner spinner_country, spinner_security_questions;
    ArrayList<HashMap<String, String>> selectedCategory = new ArrayList<>();
    ArrayList<HashMap<String, String>> selectedLocation = new ArrayList<>();
    ArrayList<String> array;
    EditText ed_username,ed_email,ed_userfname,ed_userlname,ed_userbusinessname,ed_user_refrd,ed_session_cost,
    ed_address_1,ed_address2,ed_city,ed_state,ed_zip,ed_fax,ed_vat_id,ed_phone_no,ed_security_ans;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_summary);
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(EditSummary.this);
        prefrence.loadPrefrence();
        pd = new ProgressDialog(EditSummary.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        if (globalClass.isNetworkAvailable()) {


            getCountry();
            getSecurityQuestion();

        } else {

            Toasty.warning(getApplicationContext(), getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();

        }
        ed_username=findViewById(R.id.input_username);
        imageView2=findViewById(R.id.imageView2);
        spinner_country=findViewById(R.id.spinner_country);
        spinner_security_questions=findViewById(R.id.spinner_security_questions);

        ed_email=findViewById(R.id.tv_input_email);
        ed_userfname=findViewById(R.id.tv_user_fname_value);
        ed_userlname=findViewById(R.id.tv_user_lname_value);
        ed_userbusinessname=findViewById(R.id.tv_business_name_value);
        ed_user_refrd=findViewById(R.id.tv_referred_by_value);
        ed_session_cost=findViewById(R.id.tv_session_cost_value);
        ed_address_1=findViewById(R.id.tv_address_one_value);
        ed_address_1=findViewById(R.id.tv_address_one_value);
        ed_address2=findViewById(R.id.tv_address_two_value);
        ed_city=findViewById(R.id.tv_city_value);
        ed_state=findViewById(R.id.tv_state_value);
        ed_zip=findViewById(R.id.tv_zip_code_value);
        ed_fax=findViewById(R.id.tv_fax_value);
        ed_vat_id=findViewById(R.id.tv_vat_id_value);
        ed_phone_no=findViewById(R.id.tv_phone_verifyvalue);
        ed_security_ans=findViewById(R.id.tv_security_answer_value);
        tv_userId_value=findViewById(R.id.tv_userId_value);
        ViewList();
        defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
/*                 .showImageOnLoading(R.mipmap.loading_black128px)
                 .showImageForEmptyUri(R.mipmap.no_image)
                 .showImageOnFail(R.mipmap.no_image)
                 .showImageOnFail(R.mipmap.img_failed)*/
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();
        ImageLoader.getInstance().init(config);
        loader = ImageLoader.getInstance();
        spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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

                        Log.d(TAG, "onItemSelected: "+cat_id);


                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

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
                    String message = jobj.get("message").toString().replaceAll("\"", "");
                    // String message = jobj.get("message").toString().replaceAll("\"", "");

                    if (success.equals("1")) {
                        JsonObject products = jobj.getAsJsonObject("data");

                            String id = products.get("id").toString().replaceAll("\"", "");
                            String login = products.get("login").toString().replaceAll("\"", "");
                            String user_email = products.get("user_email").toString().replaceAll("\"", "");
                            String user_first_name = products.get("user_first_name").toString().replaceAll("\"", "");
                            String user_last_name = products.get("user_last_name").toString().replaceAll("\"", "");
                            String user_organization = products.get("user_organization").toString().replaceAll("\"", "");
                            String user_phone = products.get("user_phone").toString().replaceAll("\"", "");
                            String timezone = products.get("timezone").toString().replaceAll("\"", "");
                            String user_address1 = products.get("user_address1").toString().replaceAll("\"", "");
                            String user_city = products.get("user_city").toString().replaceAll("\"", "");
                            String user_state = products.get("user_state").toString().replaceAll("\"", "");
                            String user_country = products.get("user_country").toString().replaceAll("\"", "");
                            String user_zip = products.get("user_zip").toString().replaceAll("\"", "");
                            String user_fax = products.get("user_fax").toString().replaceAll("\"", "");
                           // String vat_registered = products.get("vat_registered").toString().replaceAll("\"", "");
                            String invoices = products.get("invoices").toString().replaceAll("\"", "");
                            String favorites_notify = products.get("favorites_notify").toString().replaceAll("\"", "");
                            String com_email = products.get("com_email").toString().replaceAll("\"", "");
                            String com_phone = products.get("com_phone").toString().replaceAll("\"", "");
                            String com_post = products.get("com_post").toString().replaceAll("\"", "");
                            String security_question = products.get("set_security_question").toString().replaceAll("\"", "");
                            String security_answer = products.get("security_question_answer").toString().replaceAll("\"", "");
                            String session_cost = products.get("your_session_cost").toString().replaceAll("\"", "");
                            String image_url = products.get("image_url").toString().replaceAll("\"", "");
                            String credit = products.get("credit").toString().replaceAll("\"", "");
                            String tax_exempt = products.get("tax_exempt").toString().replaceAll("\"", "");
                            String disable_overdue_notices = products.get("disable_overdue_notices").toString().replaceAll("\"", "");
                            String user_comment = products.get("user_comment").toString().replaceAll("\"", "");
                            String signature = products.get("signature").toString().replaceAll("\"", "");
                            String out_disable = products.get("out_disable").toString().replaceAll("\"", "");
                            String check_terms_accepted = products.get("terms_accepted").toString().replaceAll("\"", "");
                            String import_id = products.get("import_id").toString().replaceAll("\"", "");
                            String bank_account_holder_name = products.get("bank_account_holder_name").toString().replaceAll("\"", "");
                            String bank_account_sort_code = products.get("bank_account_sort_code").toString().replaceAll("\"", "");
                            String bank_account_number = products.get("bank_account_number").toString().replaceAll("\"", "");
                            String bank_account_ifsc = products.get("bank_account_ifsc").toString().replaceAll("\"", "");
                            String bank_name = products.get("bank_name").toString().replaceAll("\"", "");
                            String bank_address = products.get("bank_address").toString().replaceAll("\"", "");
                            String vat_id = products.get("vat_id").toString().replaceAll("\"", "");
                            String check_for_invoice = products.get("invoices").toString().replaceAll("\"", "");
                            String refered_by = products.get("referred_by").toString().replaceAll("\"", "");
                            String check_for_vat = products.get("credits_shopping").toString().replaceAll("\"", "");
                            String check_for_supplier_account = products.get("supplier_account").toString().replaceAll("\"", "");
                            String phone_verification_status = products.get("phone_verification_status").toString().replaceAll("\"", "");
                            String otp = products.get("otp").toString().replaceAll("\"", "");
                            String otp_stamp_time = products.get("otp_stamp_time").toString().replaceAll("\"", "");
                            String seller_grade = products.get("seller_grade").toString().replaceAll("\"", "");
                            String under_franchise_user = products.get("under_franchise_user").toString().replaceAll("\"", "");
                            String under_support_user = products.get("under_support_user").toString().replaceAll("\"", "");
                            tv_userId_value.setText(id);
                            ed_username.setText(login);
                            ed_email.setText(user_email);
                            ed_userfname.setText(user_first_name);
                            ed_userlname.setText(user_last_name);
                            ed_userbusinessname.setText(user_organization);
                            ed_phone_no.setText(user_phone);
                            ed_session_cost.setText(session_cost);
                            ed_address_1.setText(user_address1);
                            ed_city.setText(user_city);
                            ed_state.setText(user_state);
                            ed_zip.setText(user_zip);
                            ed_fax.setText(user_fax);
                            ed_vat_id.setText(vat_id);
                            ed_security_ans.setText(security_answer);
                        spinner_security_questions.setPrompt(security_question);
                            Log.d(TAG, "id: " + id);
                        loader.displayImage(image_url, imageView2 , defaultOptions);
                            // Toasty.success(SearchListing.this, login, Toast.LENGTH_SHORT, true).show();




                    }else {
                         Toasty.success(EditSummary.this, message, Toast.LENGTH_SHORT, true).show();
                    }


                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    //  Log.d(TAG, "Token \n" + logo_allow);


                }
                catch (Exception e) {
                    Log.d(TAG, "Exception: ");

                    Toasty.warning(EditSummary.this, "Search Not found", Toast.LENGTH_SHORT, true).show();
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
                params.put("view","userprofile");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    private void getCountry()
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
                    array = new ArrayList<>();
                    Log.d("jobj", "" + jobj);

                    JsonObject offerObject = jobj.getAsJsonObject();
                    array.add("Select Category");
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


                         //ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_layout, array_spinner);
                        dataAdapter1 = new ArrayAdapter(EditSummary.this, android.R.layout.simple_spinner_item, array);
                        spinner_country.setAdapter(dataAdapter1);


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



                params.put("view","getLocation");

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    private void getSecurityQuestion()
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
                    array = new ArrayList<>();
                    Log.d("jobj", "" + jobj);

                    JsonObject offerObject = jobj.getAsJsonObject();
                    array.add("Select Category");
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


                       // map_ser.put("id", id);
                        map_ser.put("title", title);

                        selectedLocation.add(map_ser);
                        array.add(title);

                        Log.d("title", "" + array);


                        // ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_layout, array_spinner);
                        dataAdapter7 = new ArrayAdapter(EditSummary.this, android.R.layout.simple_spinner_item, array);
                        spinner_security_questions.setAdapter(dataAdapter7);


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



                params.put("view","getSecurityQuestion");

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}
