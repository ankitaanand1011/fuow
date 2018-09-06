package sketch.findusonweb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.HashMap;

import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Screen.EditReview;



public class AdapterTrasanction extends RecyclerView.Adapter<AdapterTrasanction.MyViewHolder>{

    Context context;

    GlobalClass globalClass;

    LayoutInflater inflater;


    ArrayList<HashMap<String,String>> list_names;

    DisplayImageOptions defaultOptions;




    public AdapterTrasanction(Context c, ArrayList<HashMap<String,String>> list_names) {
        context = c;
        this.list_names = list_names;
        globalClass = ((GlobalClass) context.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public AdapterTrasanction.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_financial_transaction, parent, false);

        AdapterTrasanction.MyViewHolder vh = new AdapterTrasanction.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(AdapterTrasanction.MyViewHolder holder, final int position) {




        holder. date.setText(list_names.get(position).get("date"));
        holder. gateway.setText(list_names.get(position).get("gateway_id"));
        holder.amount.setText(list_names.get(position).get("amount"));
        holder.type.setText(list_names.get(position).get("type"));
        holder. transaction_id.setText(list_names.get(position).get("transaction_id"));
        holder. tv_invoice_val.setText(list_names.get(position).get("invoice_id"));
        holder. tv_status.setText(list_names.get(position).get("description"));




/*

        holder.tv_claim.setOnClickListener(new View.OnClickListener() {
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
        return list_names.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView transaction_id,date,gateway,name,amount,type,status,tv_invoice_val,tv_status;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            date=itemView.findViewById(R.id.tv_date_transaction_value_order);
            gateway=itemView.findViewById(R.id.tv_gateway_value);
            amount=itemView.findViewById(R.id.tv_amount_value_order);
            type=itemView.findViewById(R.id.tv_type_transaction_value_order);
            transaction_id=itemView.findViewById(R.id.tv_transaction_id_value_order);
            tv_invoice_val=itemView.findViewById(R.id.tv_invoice_val);
            tv_status=itemView.findViewById(R.id.tv_status);
        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }



}