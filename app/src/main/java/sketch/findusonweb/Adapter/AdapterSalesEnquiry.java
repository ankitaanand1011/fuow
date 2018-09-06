package sketch.findusonweb.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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


public class AdapterSalesEnquiry extends RecyclerView.Adapter<AdapterSalesEnquiry.ViewHolder> {

    private LayoutInflater mInflater;
    Context context;
    ArrayList<HashMap<String,String>> list_products;
    ImageLoader loader;
    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;

    public AdapterSalesEnquiry(Context c, ArrayList<HashMap<String,String>> list_products) {
        this.mInflater = LayoutInflater.from(c);
        context = c;
        this.list_products = list_products;

        globalClass = (GlobalClass)context.getApplicationContext();
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.single_row_sales_enquiry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


       // holder.tv_name.setText(list_products.get(position).get("title"));
        holder.tv_des.setText(list_products.get(position).get("message"));
        holder.tv_date_manage_proposal.setText(list_products.get(position).get("date_requested"));
        holder.tv_status.setText(list_products.get(position).get("status"));



       /* if(list_products.get(position).get("product_image").equals("null") ||
                list_products.get(position).get("product_image").equals("")){

            holder.img_product.setImageResource(R.mipmap.no_image);
        }else{
            loader.displayImage(list_products.get(position).get("product_image"), holder.img_product , defaultOptions);
        }*/


    }

    @Override
    public int getItemCount() {
        return list_products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_name, tv_des, tv_date_manage_proposal,tv_status;


        ViewHolder(View itemView) {
            super(itemView);


            tv_date_manage_proposal = itemView.findViewById(R.id.tv_date_manage_proposal);
          //  tv_name = itemView.findViewById(R.id.tv_name);
            tv_des = itemView.findViewById(R.id.tv_des);
            tv_status = itemView.findViewById(R.id.tv_status);

          //  tv_product_price = itemView.findViewById(R.id.tv_product_price);



        }


    }
}