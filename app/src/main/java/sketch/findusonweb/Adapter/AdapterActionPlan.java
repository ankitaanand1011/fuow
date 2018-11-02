package sketch.findusonweb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;

import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Screen.EditSchedule;

/**
 * Created by developer on 25/10/18.
 */

public class AdapterActionPlan extends RecyclerView.Adapter<AdapterActionPlan.ViewHolder> {

    private LayoutInflater mInflater;
    Context context;

    ArrayList<HashMap<String, String>> list_products;
    ImageLoader loader;
    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;

    public AdapterActionPlan(Context c, ArrayList<HashMap<String, String>> list_products) {
        this.mInflater = LayoutInflater.from(c);
        context = c;
        this.list_products = list_products;

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
    public AdapterActionPlan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.single_add_action_pan, parent, false);
        return new AdapterActionPlan.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(AdapterActionPlan.ViewHolder holder, final int position) {
        //   int po = position+1;


       // holder.tv_from_val.setText(list_products.get(position).get("id"));
        // holder.tv_edit.setText(list_products.get(position).get("from_user"));
        holder.tv_invoice_value.setText(list_products.get(position).get("plan_id"));
        holder.tv_strategy_value.setText(Html.fromHtml(list_products.get(position).get("strategy")));
        holder.tv_action_plan_value.setText(list_products.get(position).get("action_plan"));
       // holder.tv_title.setText(list_products.get(position).get("person"));
        holder.tv_time_completion_value.setText(list_products.get(position).get("time_completion"));

      //  holder.tv_end_time_val.setText(list_products.get(position).get("sync"));

/*
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,EditSchedule.class);
                intent.putExtra("id",list_products.get(position).get("id"));
                intent.putExtra("from_user_id",list_products.get(position).get("from_user_id"));
                intent.putExtra("to_user_id",list_products.get(position).get("to_user_id"));
                intent.putExtra("listing_id",list_products.get(position).get("listing_id"));
                intent.putExtra("from_user",list_products.get(position).get("from_user"));
                intent.putExtra("to_user",list_products.get(position).get("to_user"));
                intent.putExtra("subject",list_products.get(position).get("subject"));
                intent.putExtra("location",list_products.get(position).get("location"));
                intent.putExtra("start_time",list_products.get(position).get("start_time"));
                intent.putExtra("end_time",list_products.get(position).get("end_time"));
                intent.putExtra("overview",list_products.get(position).get("overview"));
                intent.putExtra("attendies",list_products.get(position).get("attendies"));
                intent.putExtra("propose_user_id",list_products.get(position).get("propose_user_id"));
                intent.putExtra("status",list_products.get(position).get("status"));
                intent.putExtra("propose_user_id",list_products.get(position).get("propose_user_id"));
                //intent.putExtra("propose_user_id",list_products.get(position).get("propose_user_id"));
                context.startActivity(intent);
            }
        });
*/

    }

    @Override
    public int getItemCount() {
        return list_products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_invoice_value, tv_time_completion_value,tv_strategy_value,
                tv_action_plan_value,tv_des,tv_edit;


        ViewHolder(View itemView) {
            super(itemView);

            tv_invoice_value = itemView.findViewById(R.id.tv_invoice_value);
            tv_time_completion_value = itemView.findViewById(R.id.tv_time_completion_val);
            //tv_edit = itemView.findViewById(R.id.tv_edit);

            tv_strategy_value = itemView.findViewById(R.id.tv_strategy_value);
            tv_action_plan_value = itemView.findViewById(R.id.tv_action_plan_value);




        }

    }
}
