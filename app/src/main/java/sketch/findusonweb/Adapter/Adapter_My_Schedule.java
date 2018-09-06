package sketch.findusonweb.Adapter;

import android.content.Context;
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



public class Adapter_My_Schedule extends RecyclerView.Adapter<Adapter_My_Schedule.ViewHolder> {

    private LayoutInflater mInflater;
    Context context;

    ArrayList<HashMap<String, String>> list_products;
    ImageLoader loader;
    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;

    public Adapter_My_Schedule(Context c, ArrayList<HashMap<String, String>> list_products) {
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
    public Adapter_My_Schedule.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.single_my_schedule, parent, false);
        return new Adapter_My_Schedule.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(Adapter_My_Schedule.ViewHolder holder, int position) {
        //   int po = position+1;


        holder.tv_from_val.setText(list_products.get(position).get("from_user"));
        holder.tv_to_val.setText(list_products.get(position).get("to_user"));
        holder.tv_attendees_val.setText(Html.fromHtml(list_products.get(position).get("attendies")));
        holder.tv_status.setText(list_products.get(position).get("status"));
        holder.tv_title.setText(list_products.get(position).get("overview"));
        holder.tv_des.setText(list_products.get(position).get("subject"));
        holder.tv_location.setText("( "+list_products.get(position).get("location")+" )");
        holder.tv_start_time_val.setText(list_products.get(position).get("start_time"));
        holder.tv_end_time_val.setText(list_products.get(position).get("end_time"));


    }

    @Override
    public int getItemCount() {
        return list_products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_from_val, tv_to_val,tv_attendees_val,
                tv_title, tv_location,tv_des,
                tv_status, tv_start_time_val, tv_end_time_val;


        ViewHolder(View itemView) {
            super(itemView);

            tv_from_val = itemView.findViewById(R.id.tv_from_val);
            tv_to_val = itemView.findViewById(R.id.tv_to_val);
            tv_attendees_val = itemView.findViewById(R.id.tv_attendees_val);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_start_time_val = itemView.findViewById(R.id.tv_start_time_val);
            tv_end_time_val = itemView.findViewById(R.id.tv_end_time_val);
            tv_des = itemView.findViewById(R.id.tv_des);


        }

    }
}