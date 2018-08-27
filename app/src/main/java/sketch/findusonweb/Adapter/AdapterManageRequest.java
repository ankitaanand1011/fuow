package sketch.findusonweb.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
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

    public AdapterManageRequest(Context c, ArrayList<HashMap<String, String>> list_products) {
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
    public AdapterManageRequest.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.single_manage_request, parent, false);
        return new AdapterManageRequest.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(AdapterManageRequest.ViewHolder holder, int position) {
        //   int po = position+1;


        holder.tv_date_manage_proposal.setText(list_products.get(position).get("date_requested"));
        holder.tv_manage_proposal.setText(list_products.get(position).get("title"));
        holder.tv_manage_proposal_brief.setText(Html.fromHtml(list_products.get(position).get("description")));
        holder.tv_status.setText(list_products.get(position).get("status"));
        holder.tv_budget.setText(globalClass.pound+list_products.get(position).get("budget"));
        holder.tv_date_manage_proposal_days.setText(globalClass.pound+list_products.get(position).get("duration")+" Days");


    }

    @Override
    public int getItemCount() {
        return list_products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_manage_proposal_brief, tv_date_manage_proposal_days,
                tv_manage_proposal, tv_budget, tv_date_manage_proposal,
                tv_status, tv_complete_order, tv_complete_order1;


        ViewHolder(View itemView) {
            super(itemView);

            tv_manage_proposal_brief = itemView.findViewById(R.id.tv_manage_proposal_brief);
            tv_date_manage_proposal_days = itemView.findViewById(R.id.tv_date_manage_proposal_days);
            tv_manage_proposal = itemView.findViewById(R.id.tv_manage_proposal);
            tv_budget = itemView.findViewById(R.id.tv_budget);
            tv_date_manage_proposal = itemView.findViewById(R.id.tv_date_manage_proposal);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_complete_order = itemView.findViewById(R.id.tv_complete_order);
            tv_complete_order1 = itemView.findViewById(R.id.tv_complete_order1);


        }

    }
}