package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
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
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;


public class DashboardScreenLogin extends AppCompatActivity {

    GlobalClass globalClass;
    ProgressDialog pd;
    Shared_Preference prefrence;
    String TAG = "login_dashboard";

    RelativeLayout rl_products,rl_invoice,rl_my_todo,my_sales_order,
            rl_dashboard,rl_submitted_review,rl_invite,rl_order,rl_favorites, rl_financial,
            rl_manage_request,rl_manage_proposal,rl_my_rewards,rl_sales_enquiry,rl_my_earnings,
            rl_my_schedule,rl_my_msg;
    ImageView back;
    TextView tv_name,tv_balance_val,tv_business_val;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_screen);

        initialisation();
        function();
        myAccountBalance();

    }

    public void initialisation(){

        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(DashboardScreenLogin.this);

        pd = new ProgressDialog(DashboardScreenLogin.this);


        rl_submitted_review=findViewById(R.id.rl_submitted_review);

        rl_products =findViewById(R.id.rl_products);
        rl_dashboard=findViewById(R.id.dashboard);
        rl_invoice=findViewById(R.id.rl_invoice);
        rl_invite=findViewById(R.id.rl_invite_friends);
        rl_order=findViewById(R.id.rl_my_orders);
        rl_favorites=findViewById(R.id.rl_favorites);
        rl_financial=findViewById(R.id.rl_financial);
        rl_manage_request=findViewById(R.id.rl_manage_request);
        rl_manage_proposal=findViewById(R.id.rl_manage_proposal);
        rl_my_rewards=findViewById(R.id.rl_my_rewards);
        rl_my_todo=findViewById(R.id.rl_my_todo);
        rl_sales_enquiry=findViewById(R.id.rl_sales_enquiry);
        rl_my_earnings=findViewById(R.id.rl_my_earnings);
        rl_my_schedule=findViewById(R.id.rl_my_schedule);
        my_sales_order=findViewById(R.id.my_sales_order);
        rl_my_msg=findViewById(R.id.rl_my_msg);

        back=findViewById(R.id.back_img);


        tv_name=findViewById(R.id.tv_name);
        tv_balance_val=findViewById(R.id.tv_balance_val);


    }

    public void function(){
        prefrence.loadPrefrence();
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));


        tv_name.setText(globalClass.getFname()+" "+globalClass.getLname());


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        rl_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, ProductScreen.class);
                startActivity(intent);
            }
        });

        rl_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, DashboardNew.class);
                startActivity(intent);
            }
        });

        rl_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, Invoice.class);
                startActivity(intent);
            }
        });

        rl_manage_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, ManageRequest.class);
                startActivity(intent);
            }
        });

        rl_submitted_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, My_Submitted_Reviews.class);
                startActivity(intent);
            }
        });

        rl_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, Invite_friend_from_dashboard.class);
                startActivity(intent);
            }
        });

        rl_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, MyOrderLIst.class);
                startActivity(intent);
            }
        });

        rl_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, Favorites.class);
                startActivity(intent);
            }
        });

        rl_financial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, Financial_Transaction.class);
                startActivity(intent);
            }
        });

        rl_manage_proposal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, Manage_Proposal.class);
                startActivity(intent);
            }
        });

        rl_my_rewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, My_Rewards.class);
                startActivity(intent);
            }
        });

        rl_my_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, ToDoScreen.class);
                startActivity(intent);
            }
        });


        rl_sales_enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, SalesEnquiry.class);
                startActivity(intent);
            }
        });

        rl_my_earnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, EarningsScreen.class);
                startActivity(intent);
            }
        });

        rl_my_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, My_Schedule.class);
                startActivity(intent);
            }
        });

        my_sales_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, My_Sales_Order.class);
                startActivity(intent);
            }
        });

        rl_my_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, ChatScreen.class);
                startActivity(intent);
            }
        });



    }

    private void myAccountBalance() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Invitation response: " + response);

                // pd.dismiss();

                Gson gson = new Gson();

                try
                {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String success = jobj.get("success").getAsString().replaceAll("\"", "");

                    String data = jobj.get("data").getAsString().replaceAll("\"", "");
                    tv_balance_val.setText(data+"CR");
                    pd.dismiss();


                }catch (Exception e) {

                    e.printStackTrace();

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

                params.put("user_id", globalClass.getId());
                params.put("view","myAccountBalance");

                Log.d(TAG, "getParams: "+params);

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
}
