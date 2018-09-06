package sketch.findusonweb.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.HashMap;

import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;


public class AdapterToDoDetails extends RecyclerView.Adapter<AdapterToDoDetails.MyViewHolder> {


    Context context;
    ArrayList<HashMap<String,String>> list_namesfavoriteAll;

    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;





    LayoutInflater inflater;

    public AdapterToDoDetails(Context c, ArrayList<HashMap<String,String>> list_namesfavoriteAll) {
        this.context = c;
        this.list_namesfavoriteAll = list_namesfavoriteAll;

        globalClass = ((GlobalClass) c.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        // ImageLoader.getInstance().init(config);
        // loader = ImageLoader.getInstance();


    }
    @Override
    public AdapterToDoDetails.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_todo_detail, parent, false);

        AdapterToDoDetails.MyViewHolder vh = new AdapterToDoDetails.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(AdapterToDoDetails.MyViewHolder holder, final int position) {


        String points = list_namesfavoriteAll.get(position).get("points");

        holder.name.setText(list_namesfavoriteAll.get(position).get("todo_task"));
        holder.tv_status.setText(list_namesfavoriteAll.get(position).get("status"));
        holder.tv_credit_point.setText(list_namesfavoriteAll.get(position).get("points"));
        holder.tv_invoice_value.setText(list_namesfavoriteAll.get(position).get("credit_id"));





        //  final HashMap<String, String> hashMap_child = (HashMap<String, String>) getChild(groupPosition, childPosition);


/*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BrowseProductSingleSelection.class);
                intent.putExtra("id",list_namesfavoriteAll.get(position).get("id"));

                context.startActivity(intent);

            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return list_namesfavoriteAll.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name,tv_status,tv_credit_point,tv_invoice_value;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name =  itemView.findViewById(R.id.tv_manage_proposal_brief);
            tv_status =  itemView.findViewById(R.id.tv_status);
            tv_credit_point =  itemView.findViewById(R.id.tv_credit_point);
            tv_invoice_value =  itemView.findViewById(R.id.tv_invoice_value);

        }
    }
}