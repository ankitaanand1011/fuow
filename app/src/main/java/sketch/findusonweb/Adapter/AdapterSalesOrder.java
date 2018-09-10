package sketch.findusonweb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.HashMap;

import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Screen.EditReview;


public class AdapterSalesOrder extends RecyclerView.Adapter<AdapterSalesOrder.MyViewHolder> {


    Context context;
    ArrayList<HashMap<String,String>> Arraylist_review;

    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;
    LayoutInflater inflater;

    public AdapterSalesOrder(Context c, ArrayList<HashMap<String,String>> Arraylist_review
    ) {
        this.context = c;
        this.Arraylist_review = Arraylist_review;

        globalClass = ((GlobalClass) c.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        // ImageLoader.getInstance().init(config);
        // loader = ImageLoader.getInstance();


    }
    @Override
    public AdapterSalesOrder.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_order_single_row, parent, false);

        AdapterSalesOrder.MyViewHolder vh = new AdapterSalesOrder.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(AdapterSalesOrder.MyViewHolder holder, final int position) {





        final String id =  Arraylist_review.get(position).get("id");
     //   String name =  Arraylist_review.get(position).get("name");
        String order_id =  Arraylist_review.get(position).get("order_id");
        String date =  Arraylist_review.get(position).get("date");
       // String type =  Arraylist_review.get(position).get("type");
        String title =  Arraylist_review.get(position).get("title");
        String listing_name =  Arraylist_review.get(position).get("listing_name");
      //  String payment =  Arraylist_review.get(position).get("gateway");
        String amount =  Arraylist_review.get(position).get("amount");
        String order_status =  Arraylist_review.get(position).get("order_status");
        String invoice_status =  Arraylist_review.get(position).get("invoice_status");
        String buyer =  Arraylist_review.get(position).get("buyer");
        String status =  Arraylist_review.get(position).get("status");







        holder.tv_id_val.setText(id);
        holder.tv_order_id.setText(order_id);
        holder.tv_date.setText(date);
        holder.tv_title.setText(title);
        holder.tv_type.setText("( "+listing_name+" )");
        holder.tv_listing_name_val.setText(globalClass.getFname());
        holder.tv_payment_method_val.setText(buyer);
        holder.tv_in_pound.setText(amount);
        holder.tv_order_status_val.setText(order_status);
        holder.tv_invoice_status_val.setText(invoice_status);




    }

    @Override
    public int getItemCount() {
        return Arraylist_review.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's


        TextView tv_id_val, tv_title,tv_type,tv_date,tv_listing_name_val,
                tv_payment_method_val,tv_in_pound,tv_order_id,
                tv_order_status_val,tv_invoice_status_val;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            tv_id_val = itemView.findViewById(R.id.tv_id_val);
            tv_order_id = itemView.findViewById(R.id.tv_order_id);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_listing_name_val = itemView.findViewById(R.id.tv_listing_name_val);
            tv_payment_method_val = itemView.findViewById(R.id.tv_payment_method_val);
            tv_in_pound = itemView.findViewById(R.id.tv_in_pound);
            tv_order_status_val = itemView.findViewById(R.id.tv_order_status_val);
            tv_invoice_status_val = itemView.findViewById(R.id.tv_invoice_status_val);

        }
    }
}