package sketch.findusonweb.Screen;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import sketch.findusonweb.Adapter.Adapter_invite_friend;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

public class Invite_friend_from_dashboard extends AppCompatActivity {
    ListView listing;
    String TAG = "Favorites";
    GlobalClass globalClass;
    Shared_Preference prefrence;
    Adapter_invite_friend adapter_invoice;
    ImageView back_img;
    ProgressDialog pd;
    ArrayList<HashMap<String,String>> list_namesfavoriteAll;
    TextView tv_in_ref,tv_joined_ref,tv_pending_join,tv_pending_ref,tv_in_earning,
            tv_join_earning,tv_potential_earning,tv_estimated_earning,tv_invite_friend;
    Dialog dialog;
    EditText firstname_dialog,lastname_dialog,email_dialog,business_name,phone_dialog,campaign_id;
    TextView save,submit,tv_browse;
    LinearLayout post_job_layout;
    ImageView dialog_cut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend_from_dashboard);


        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(Invite_friend_from_dashboard.this);
        prefrence.loadPrefrence();

        pd = new ProgressDialog(Invite_friend_from_dashboard.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));

        listing=findViewById(R.id.invite_listing);
        back_img=findViewById(R.id.back_img);

        if (globalClass.isNetworkAvailable()) {
            if (globalClass.getLogin_status()) {


                ReviewList();
            }
        } else {
            Toasty.info(Invite_friend_from_dashboard.this, getResources().getString(R.string.check_internet), Toast.LENGTH_LONG, true).show();
        }

        tv_in_ref=findViewById(R.id.tv_in_ref);
        tv_joined_ref=findViewById(R.id.tv_joined_ref);
        tv_pending_join=findViewById(R.id.tv_pending_join);
        tv_pending_ref=findViewById(R.id.tv_pending_ref);
        tv_in_earning=findViewById(R.id.tv_in_earning);
        tv_join_earning=findViewById(R.id.tv_join_earning);
        tv_potential_earning=findViewById(R.id.tv_potential_earning);
        tv_estimated_earning=findViewById(R.id.tv_estimated_earning);
        tv_invite_friend=findViewById(R.id.tv_invite_friend);


        tv_invite_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog1();
            }
        });

        list_namesfavoriteAll=new ArrayList<>();
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    public void openDialog1() {
        dialog = new Dialog(Invite_friend_from_dashboard.this);
        dialog.setContentView(R.layout.invite_dialog);

        dialog_cut=dialog.findViewById(R.id.cut_dialog);
        firstname_dialog=dialog.findViewById(R.id.firstname);
        lastname_dialog=dialog.findViewById(R.id.lastname);
        email_dialog=dialog.findViewById(R.id.email);
        phone_dialog=dialog.findViewById(R.id.phoone_no);
        campaign_id=dialog.findViewById(R.id.campaign_id);
        business_name=dialog.findViewById(R.id.business_name);
        save=dialog.findViewById(R.id.save);
        submit=dialog.findViewById(R.id.tv_submit);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = firstname_dialog.getText().toString().trim();
                String lastname = lastname_dialog.getText().toString().trim();
                String email = email_dialog.getText().toString().trim();
                String Phone = phone_dialog.getText().toString().trim();
                String organization = business_name.getText().toString().trim();
                String campaign_id1 = campaign_id.getText().toString().trim();

                if (globalClass.isNetworkAvailable()) {
                    if (!firstname_dialog.getText().toString().isEmpty()) {
                        if (!lastname_dialog.getText().toString().isEmpty()) {
                            if (isValidEmail(email_dialog.getText().toString())) {
                                if (!phone_dialog.getText().toString().isEmpty()) {
                                    if (!business_name.getText().toString().isEmpty()) {
                                        if (!campaign_id.getText().toString().isEmpty()) {

                                            inviteFriend(firstname,lastname,email,organization,Phone,campaign_id1,globalClass.view_friend);

                                        }
                                        else {
                                            Toasty.warning(Invite_friend_from_dashboard.this, getResources().getString(R.string.enter_campaign_id), Toast.LENGTH_SHORT, true).show();

                                        }
                                    } else {
                                        Toasty.warning(Invite_friend_from_dashboard.this, getResources().getString(R.string.enter_mobile), Toast.LENGTH_SHORT, true).show();
                                    }
                                }
                            } else {
                                Toasty.warning(Invite_friend_from_dashboard.this, getResources().getString(R.string.valid_email), Toast.LENGTH_SHORT, true).show();
                            }
                        } else {
                            Toasty.warning(Invite_friend_from_dashboard.this, getResources().getString(R.string.enter_last_name), Toast.LENGTH_SHORT, true).show();
                        }
                    } else {
                        Toasty.warning(Invite_friend_from_dashboard.this, getResources().getString(R.string.enter_first_name), Toast.LENGTH_SHORT, true).show();
                    }
                } else {
                    Toasty.warning(Invite_friend_from_dashboard.this, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT, true).show();
                }
            }



        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = firstname_dialog.getText().toString().trim();
                String lastname = lastname_dialog.getText().toString().trim();
                String email = email_dialog.getText().toString().trim();
                String Phone = phone_dialog.getText().toString().trim();
                String organization = business_name.getText().toString().trim();
                String campaign_id1 = campaign_id.getText().toString().trim();

                if (globalClass.isNetworkAvailable()) {
                    if (!firstname_dialog.getText().toString().isEmpty()) {
                        if (!lastname_dialog.getText().toString().isEmpty()) {
                            if (isValidEmail(email_dialog.getText().toString())) {
                                if (!phone_dialog.getText().toString().isEmpty()) {
                                    if (!business_name.getText().toString().isEmpty()) {
                                        if (!campaign_id.getText().toString().isEmpty()) {

                                            submitFriend(firstname,lastname,email,organization,Phone,campaign_id1,globalClass.view_friend);

                                        }
                                        else {
                                            Toasty.warning(Invite_friend_from_dashboard.this, getResources().getString(R.string.enter_campaign_id), Toast.LENGTH_SHORT, true).show();

                                        }
                                    } else {
                                        Toasty.warning(Invite_friend_from_dashboard.this, getResources().getString(R.string.enter_mobile), Toast.LENGTH_SHORT, true).show();
                                    }
                                }
                            } else {
                                Toasty.warning(Invite_friend_from_dashboard.this, getResources().getString(R.string.valid_email), Toast.LENGTH_SHORT, true).show();
                            }
                        } else {
                            Toasty.warning(Invite_friend_from_dashboard.this, getResources().getString(R.string.enter_last_name), Toast.LENGTH_SHORT, true).show();
                        }
                    } else {
                        Toasty.warning(Invite_friend_from_dashboard.this, getResources().getString(R.string.enter_first_name), Toast.LENGTH_SHORT, true).show();
                    }
                } else {
                    Toasty.warning(Invite_friend_from_dashboard.this, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT, true).show();
                }
            }



        });

        dialog_cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void ReviewList() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

         pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());



                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    Log.d(TAG, "onResponse: " + jobj);
                    String invite_referrals = jobj.get("invite_referrals").toString().replaceAll("\"", "");
                    String joined_referrals = jobj.get("joined_referrals").toString().replaceAll("\"", "");
                    String pending_referrals = jobj.get("pending_referrals").toString().replaceAll("\"", "");
                    String pending_joining = jobj.get("pending_joining").toString().replaceAll("\"", "");
                    String invited_amount = jobj.get("invited_amount").toString().replaceAll("\"", "");
                    String joined_amount = jobj.get("joined_amount").toString().replaceAll("\"", "");
                    String total_amount = jobj.get("total_amount").toString().replaceAll("\"", "");
                    String pending_amount = jobj.get("pending_amount").toString().replaceAll("\"", "");

                    tv_in_ref.setText(invite_referrals);
                    tv_joined_ref.setText(joined_referrals);
                    tv_pending_join.setText(pending_joining);
                    tv_pending_ref.setText(pending_referrals);
                    tv_in_earning.setText(globalClass.pound+invited_amount);
                    tv_join_earning.setText(globalClass.pound+joined_amount);
                    tv_potential_earning.setText(globalClass.pound+total_amount);
                    tv_estimated_earning.setText(globalClass.pound+pending_amount);

                    String result = jobj.get("success").toString().replaceAll("\"", "");
                    if (result.equals("1")) {
                        JsonArray data = jobj.getAsJsonArray("data");
                        Log.d(TAG, "Data: " + data);

                        for (int i = 0; i < data.size(); i++) {

                            JsonObject images1 = data.get(i).getAsJsonObject();
                            String mail_id = images1.get("mail_id").toString().replaceAll("\"", "");
                            String user_organization = images1.get("user_organization").toString().replaceAll("\"", "");
                            String user_first_name = images1.get("user_first_name").toString().replaceAll("\"", "");
                            String user_last_name = images1.get("user_last_name").toString().replaceAll("\"", "");
                            String user_email = images1.get("user_email").toString().replaceAll("\"", "");
                            String user_phone = images1.get("user_phone").toString().replaceAll("\"", "");
                            String refer_userid = images1.get("refer_userid").toString().replaceAll("\"", "");
                            String campaign_source_field = images1.get("campaign_source_field").toString().replaceAll("\"", "");
                            String sync = images1.get("sync").toString().replaceAll("\"", "");
                            String cor_mail_id = images1.get("cor_mail_id").toString().replaceAll("\"", "");
                            String status = images1.get("status").toString().replaceAll("\"", "");
                            String ischecked = images1.get("ischecked").toString().replaceAll("\"", "");

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("mail_id", mail_id);
                            hashMap.put("user_organization", user_organization);
                            hashMap.put("user_first_name", user_first_name);
                            hashMap.put("user_last_name", user_last_name);
                            hashMap.put("user_email", user_email);
                            hashMap.put("user_phone", user_phone);
                            hashMap.put("refer_userid", refer_userid);
                            hashMap.put("campaign_source_field", campaign_source_field);
                            hashMap.put("sync", sync);
                            hashMap.put("cor_mail_id", cor_mail_id);
                            hashMap.put("status", status);
                            hashMap.put("ischecked", ischecked);




                            list_namesfavoriteAll.add(hashMap);
                            Log.d(TAG, "Listmane: " + list_namesfavoriteAll);

                        }
                        Log.d(TAG, "Listmane outer: " + list_namesfavoriteAll);

                        adapter_invoice = new Adapter_invite_friend(Invite_friend_from_dashboard.this, list_namesfavoriteAll);
                        listing.setAdapter(adapter_invoice);
                    }
                    else


                    {


                        Toasty.success(Invite_friend_from_dashboard.this, result, Toast.LENGTH_SHORT, true).show();
                    }
                    pd.dismiss();

                } catch (Exception e) {

                    Toasty.warning(Invite_friend_from_dashboard.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
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
                params.put("view", "getInviteFriendsByUser");

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();}

    private void inviteFriend(final String user_first_name, final String user_last_name, final String user_email,  final String user_organizaton, final String phone_number, final String campaign_source_field, final String view) {
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
                    String result = jobj.get("result").getAsString().replaceAll("\"", "");
                    String message = jobj.get("msg").getAsString().replaceAll("\"", "");
                    // JsonObject offerObject = jobj.getAsJsonObject("result");

                    if (result.equals("success")) {


                        Toasty.success(Invite_friend_from_dashboard.this, message, Toast.LENGTH_SHORT, true).show();
                        // Intent intent = new Intent(Invite_friend_from_dashboard.this, Invite_friend_from_dashboard.class);
                        // startActivity(intent);
                        // finish();
                        dialog.dismiss();

                    } else


                    {


                        Toasty.success(Invite_friend_from_dashboard.this, message, Toast.LENGTH_SHORT, true).show();
                    }

                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    Log.d(TAG, "Token \n" + message);


                } catch (Exception e) {

                    Toasty.warning(Invite_friend_from_dashboard.this, "Something went wrong", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), (CharSequence) error, Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();



                params.put("user_first_name", user_first_name);
                params.put("user_last_name", user_last_name);
                params.put("user_email", user_email);
                params.put("user_organization", user_organizaton);
                params.put("user_phone", phone_number);
                params.put("status", globalClass.Status_new);
                params.put("campaign_source_field",campaign_source_field);
                params.put("refer_user_id",globalClass.getId());
                params.put("view", view);
                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    private void submitFriend(final String user_first_name, final String user_last_name, final String user_email,  final String user_organizaton, final String phone_number, final String campaign_source_field, final String view) {
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
                    String result = jobj.get("result").getAsString().replaceAll("\"", "");
                    String message = jobj.get("msg").getAsString().replaceAll("\"", "");
                    // JsonObject offerObject = jobj.getAsJsonObject("result");

                    if (result.equals("success")) {


                        Toasty.success(Invite_friend_from_dashboard.this, message, Toast.LENGTH_SHORT, true).show();
                        // Intent intent = new Intent(Invite_friend_from_dashboard.this, Invite_friend_from_dashboard.class);
                        // startActivity(intent);
                        // finish();
                        dialog.dismiss();

                    } else


                    {


                        Toasty.success(Invite_friend_from_dashboard.this, message, Toast.LENGTH_SHORT, true).show();
                    }

                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    Log.d(TAG, "Token \n" + message);


                } catch (Exception e) {

                    Toasty.warning(Invite_friend_from_dashboard.this, "Something went wrong", Toast.LENGTH_SHORT, true).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), (CharSequence) error, Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();



                params.put("user_first_name", user_first_name);
                params.put("user_last_name", user_last_name);
                params.put("user_email", user_email);
                params.put("user_organization", user_organizaton);
                params.put("user_phone", phone_number);
                params.put("campaign_source_field",campaign_source_field);
                params.put("refer_user_id",globalClass.getId());
                params.put("view", view);
                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


}