package sketch.findusonweb.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.google.gson.JsonObject;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Screen.EditRequest;
import sketch.findusonweb.Screen.PostRequriementScreen;
import sketch.findusonweb.Screen.ReviewScreen;
import sketch.findusonweb.Screen.ServiceDetailScreen;

/**
 * Created by developer on 21/6/18.
 */

public class AdapterManageRequest extends RecyclerView.Adapter<AdapterManageRequest.ViewHolder> {

    private LayoutInflater mInflater;
    Context context;

    ArrayList<HashMap<String, String>> list_products;
    ImageLoader loader;
    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;
    String TAG ="delete";

    LayoutInflater inflater;
    ProgressDialog pd;

    public AdapterManageRequest(Context c, ArrayList<HashMap<String, String>> list_products,ProgressDialog pd) {
        this.mInflater = LayoutInflater.from(c);
        context = c;
        this.list_products = list_products;
        this.pd = pd;

        globalClass = (GlobalClass) context.getApplicationContext();
        defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)

                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context.getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();
        ImageLoader.getInstance().init(config);
        loader = ImageLoader.getInstance();

    }

    @Override
    public AdapterManageRequest.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.single_manage_request, parent, false);
        return new AdapterManageRequest.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(AdapterManageRequest.ViewHolder holder, final int position) {
        //   int po = position+1;

if(list_products.get(position).get("isEdit").equals("true")){
    holder.rl_edit.setVisibility(View.VISIBLE);
}
else{
    holder.rl_edit.setVisibility(View.GONE);
}
        if(list_products.get(position).get("isPaused").equals("true")){
            holder.rl_paused.setVisibility(View.VISIBLE);
        }
        else{
            holder.rl_paused.setVisibility(View.GONE);
        }
        if(list_products.get(position).get("isDelete").equals("true")){
            holder.rl_delete.setVisibility(View.VISIBLE);
        }
        else{
            holder.rl_delete.setVisibility(View.GONE);
        }
        if(list_products.get(position).get("isActivate").equals("true")){
            holder.rl_activate.setVisibility(View.VISIBLE);
        }
        else{
            holder.rl_activate.setVisibility(View.GONE);
        }
        if(list_products.get(position).get("completeOrder").equals("true")){
            holder.rl_complete.setVisibility(View.VISIBLE);
        }
        else{
            holder.rl_complete.setVisibility(View.GONE);
        }
        if(list_products.get(position).get("requestRevision").equals("true")){
            holder.rl_request.setVisibility(View.VISIBLE);
        }
        else{
            holder.rl_request.setVisibility(View.GONE);
        }
        holder.tv_date_manage_proposal.setText(list_products.get(position).get("date_requested"));
        holder.tv_manage_proposal.setText(list_products.get(position).get("title"));
        holder.tv_manage_proposal_brief.setText(Html.fromHtml(list_products.get(position).get("description")));
       // holder.tv_status.setText(list_products.get(position).get("status"));
        holder.tv_budget.setText(globalClass.pound+list_products.get(position).get("budget"));
        holder.tv_date_manage_proposal_days.setText(globalClass.pound+list_products.get(position).get("duration")+" Days");

        holder.tv_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EditRequest.class);
                intent.putExtra("id",list_products.get(position).get("id"));
                intent.putExtra("fw_id",list_products.get(position).get("fw_id"));
                intent.putExtra("title", list_products.get(position).get("title"));
                intent.putExtra("description", list_products.get(position).get("description"));
                intent.putExtra("budget", list_products.get(position).get("budget"));
                context.startActivity(intent);


            }
        });
        holder.rl_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id =  list_products.get(position).get("id");
               delete(id);


            }
        });

    }
    private void delete(final String id) {
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
                    String message = jobj.get("data").getAsString().replaceAll("\"", "");

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

                params.put("view", "delete_product");
                params.put("product_id", id);

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    @Override
    public int getItemCount() {
        return list_products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_manage_proposal_brief, tv_date_manage_proposal_days,
                tv_manage_proposal, tv_budget, tv_date_manage_proposal,
                tv_status, tv_complete_order, tv_complete_order1,tv_paused,tv_activate,tv_delete,tv_complete,tv_request;
          RelativeLayout rl_paused,rl_activate,rl_delete,rl_complete,rl_request,rl_edit;

        ViewHolder(View itemView) {
            super(itemView);

            tv_manage_proposal_brief = itemView.findViewById(R.id.tv_manage_proposal_brief);
            tv_date_manage_proposal_days = itemView.findViewById(R.id.tv_date_manage_proposal_days);
            tv_manage_proposal = itemView.findViewById(R.id.tv_manage_proposal);
            tv_budget = itemView.findViewById(R.id.tv_budget);
            tv_date_manage_proposal = itemView.findViewById(R.id.tv_date_manage_proposal);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_complete_order = itemView.findViewById(R.id.tv_complete_order);
            tv_paused = itemView.findViewById(R.id.tv_pause);
            tv_activate = itemView.findViewById(R.id.tv_activate);
            tv_request = itemView.findViewById(R.id.tv_request);
            tv_delete = itemView.findViewById(R.id.tv_delete);
            tv_complete = itemView.findViewById(R.id.tv_complete);
            rl_paused = itemView.findViewById(R.id.rl_pause);
            rl_activate = itemView.findViewById(R.id.rl_activate);
            rl_delete = itemView.findViewById(R.id.rl_delete);
            rl_complete = itemView.findViewById(R.id.rl_complete);
            rl_request = itemView.findViewById(R.id.rl_request);
            rl_edit = itemView.findViewById(R.id.rl_cost_in_pound);
          //  tv_complete_order1 = itemView.findViewById(R.id.tv_complete_order1);


        }

    }
}