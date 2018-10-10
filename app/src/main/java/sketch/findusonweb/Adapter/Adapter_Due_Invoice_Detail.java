package sketch.findusonweb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ivbaranov.mli.MaterialLetterIcon;
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

import static android.view.View.VISIBLE;


public class Adapter_Due_Invoice_Detail extends RecyclerView.Adapter<Adapter_Due_Invoice_Detail.ViewHolder> {

    private LayoutInflater mInflater;
    Context context;

    ArrayList<HashMap<String, String>> list_products;
    ImageLoader loader;
    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;

    public Adapter_Due_Invoice_Detail(Context c, ArrayList<HashMap<String, String>> list_products) {
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.single_dueinvoice_detail, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //   int po = position+1;

       // holder.print.setVisibility(View.GONE);
        holder.tv_total_val.setText(list_products.get(position).get("total"));
       // holder.tv_edit.setText(list_products.get(position).get("from_user"));
       // holder.tv_to_val.setText(list_products.get(position).get("to_user"));
       // holder.tv_attendees_val.setText(Html.fromHtml(list_products.get(position).get("attendies")));
        holder.status.setText(list_products.get(position).get("status"));
        holder.tv_invoice_id_val.setText(list_products.get(position).get("invoice_id"));
       holder.order_id.setText(list_products.get(position).get("order_id"));
        holder.tv_balance_val.setText(list_products.get(position).get("balance"));
      //  holder.tv_start_time_val.setText(list_products.get(position).get("start_time"));
        holder.tv_date_val.setText(list_products.get(position).get("date"));
        if(list_products.get(position).get("status").equals("paid"))
        {
            holder.pay_invoice.setVisibility(View.GONE);


           holder.print.setVisibility(VISIBLE);
        }
        else{
            holder.pay_invoice.setVisibility(VISIBLE);
            holder.print.setVisibility(View.GONE);
        }
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


        TextView tv_date_val, tv_to_val,tv_balance_val,
                tv_title, order_id,tv_des,tv_edit,tv_total_val,
                status, tv_start_time_val, tv_end_time_val,tv_invoice_id_val,print,pay_invoice;


        ViewHolder(View itemView) {
            super(itemView);

            tv_date_val = itemView.findViewById(R.id.tv_date_val);
            tv_to_val = itemView.findViewById(R.id.tv_to_val);
            tv_edit = itemView.findViewById(R.id.tv_edit);
            pay_invoice=itemView.findViewById(R.id.pay_invoice);
            order_id = itemView.findViewById(R.id.tv_order_id_val);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_total_val = itemView.findViewById(R.id.tv_total_val);
            status = itemView.findViewById(R.id.status);
            tv_start_time_val = itemView.findViewById(R.id.tv_start_time_val);
            tv_end_time_val = itemView.findViewById(R.id.tv_end_time_val);
            tv_des = itemView.findViewById(R.id.tv_des);
            tv_invoice_id_val = itemView.findViewById(R.id.tv_invoice_id_val);
            print = itemView.findViewById(R.id.print);
            tv_balance_val = itemView.findViewById(R.id.tv_balance_val);


        }

    }
}