package sketch.findusonweb.Adapter;

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

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.HashMap;

import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Screen.EditReview;



public class AdapterEarning extends RecyclerView.Adapter<AdapterEarning.MyViewHolder> {


    Context context;
    ArrayList<HashMap<String,String>> Arraylist_review;

    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;
    LayoutInflater inflater;

    public AdapterEarning(Context c, ArrayList<HashMap<String,String>> Arraylist_review
    ) {
        this.context = c;
        this.Arraylist_review = Arraylist_review;

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
        holder.tv_in_pound.setText(globalClass.pound+amount);
        holder.tv_commission_val.setText(globalClass.pound+com_val);
        holder.tv_sales_earning_val.setText(globalClass.pound+sales_val);
        holder.tv_order_status_val.setText(order_val);
        holder.tv_invoice_status_val.setText(invoice_val);

       /* holder.tv_claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditReview.class);
                intent.putExtra("id", id);
                Log.d("tag", "onClick: " + id);
                context.startActivity(intent);

            }
        });
*/



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
}