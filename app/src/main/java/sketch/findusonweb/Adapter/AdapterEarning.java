package sketch.findusonweb.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Screen.EditReview;
import sketch.findusonweb.Screen.HomeScreen;
import sketch.findusonweb.Screen.LoginScreen;


public class AdapterEarning extends RecyclerView.Adapter<AdapterEarning.MyViewHolder> {


    Context context;
    ArrayList<HashMap<String,String>> Arraylist_review;
    String TAG ="AdapterEarning";
    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;
    LayoutInflater inflater;
    ProgressDialog pd;

    public AdapterEarning(Context c, ArrayList<HashMap<String,String>> Arraylist_review,ProgressDialog pd
    ) {
        this.context = c;
        this.Arraylist_review = Arraylist_review;
        this.pd = pd;

        globalClass = ((GlobalClass) c.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        // ImageLoader.getInstance().init(config);
        // loader = ImageLoader.getInstance();


    }
    @Override
    public AdapterEarning.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.earning_single_row, parent, false);

        AdapterEarning.MyViewHolder vh = new AdapterEarning.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(AdapterEarning.MyViewHolder holder, final int position) {






        String title =  Arraylist_review.get(position).get("title");
        String order_id =  Arraylist_review.get(position).get("order_id");
        String date =  Arraylist_review.get(position).get("date");
        String type =  Arraylist_review.get(position).get("type");
        String listing_name =  Arraylist_review.get(position).get("listing_name");
        String payment =  Arraylist_review.get(position).get("gateway");
        String amount =  Arraylist_review.get(position).get("amount");
        String com_val =  Arraylist_review.get(position).get("commission_amount");
        String sales_val =  Arraylist_review.get(position).get("sales_earning");
        String order_val =  Arraylist_review.get(position).get("order_status");
        String invoice_val =  Arraylist_review.get(position).get("invoice_status");







        holder.tv_order_id.setText(order_id);
        holder.tv_date.setText(date);
        holder.tv_title.setText(title);
        holder.tv_type.setText("( "+type+" )");
        holder.tv_listing_name_val.setText(listing_name);
        holder.tv_payment_method_val.setText(payment);
        holder.tv_in_pound.setText(amount);
        holder.tv_commission_val.setText(com_val);
        holder.tv_sales_earning_val.setText(globalClass.pound+sales_val);
        holder.tv_order_status_val.setText(order_val);
        holder.tv_invoice_status_val.setText(invoice_val);

        holder.tv_claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String order_id =  Arraylist_review.get(position).get("order_id");
                String type =  Arraylist_review.get(position).get("type");
                myEarningsClaims(type,order_id);
            }
        });



    }

    @Override
    public int getItemCount() {
        return Arraylist_review.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's


        TextView tv_title,tv_type,tv_date,tv_listing_name_val,tv_payment_method_val,tv_in_pound,tv_order_id,
                tv_claim,tv_commission_val,tv_sales_earning_val,tv_order_status_val,tv_invoice_status_val;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            tv_order_id = itemView.findViewById(R.id.tv_order_id);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_listing_name_val = itemView.findViewById(R.id.tv_listing_name_val);
            tv_payment_method_val = itemView.findViewById(R.id.tv_payment_method_val);
            tv_in_pound = itemView.findViewById(R.id.tv_in_pound);
            tv_claim = itemView.findViewById(R.id.tv_claim);
            tv_commission_val = itemView.findViewById(R.id.tv_commission_val);
            tv_sales_earning_val = itemView.findViewById(R.id.tv_sales_earning_val);
            tv_order_status_val = itemView.findViewById(R.id.tv_order_status_val);
            tv_invoice_status_val = itemView.findViewById(R.id.tv_invoice_status_val);

        }
    }


    private void myEarningsClaims(final String type, final String order_id) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                pd.dismiss();

                Gson gson = new Gson();

                try
                {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String success = jobj.get("success").getAsString().replaceAll("\"", "");
                    String message = jobj.get("message").getAsString().replaceAll("\"", "");

                    if(success.equals("1")) {
                        Toasty.success(context, message, Toast.LENGTH_SHORT, true).show();

                    }else{
                        Toasty.error(context, message, Toast.LENGTH_SHORT, true).show();
                    }
                    pd.dismiss();

                   // Log.d(TAG,"Token \n" +message);



                }catch (Exception e) {

                   // Toast.makeText(context,"Incorrect Client ID/Password", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(context,
                        "Connection Error", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("type", type);
                params.put("order_id", order_id);
                params.put("view","myEarningsClaims");

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
}