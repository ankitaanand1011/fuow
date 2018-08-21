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
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.HashMap;

import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Screen.Job_details;
import sketch.findusonweb.Screen.SendProposal;


public class AdapterCreditHistory extends RecyclerView.Adapter<AdapterCreditHistory.MyViewHolder>  {

    Context mContext;

    GlobalClass globalClass;

    LayoutInflater inflater;

    ImageView img;
    ArrayList<HashMap<String,String>> list_names;
    // ArrayList<String> list_names;
    //ImageLoader loader;
    DisplayImageOptions defaultOptions;

    @Override
    public AdapterCreditHistory.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_credit_history_list, parent, false);

        AdapterCreditHistory.MyViewHolder vh = new AdapterCreditHistory.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(AdapterCreditHistory.MyViewHolder holder, final int position) {






        holder.tv_credited_point.setText(list_names.get(position).get("points"));
     //   holder. name.setText(list_names.get(position).get("name"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Job_details.class);
                intent.putExtra("id",list_names.get(position).get("id"));
                Log.d("tag", "onClick: "+list_names.get(position).get("id"));
                mContext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list_names.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's

        TextView tv_credited_point,name;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            tv_credited_point=itemView.findViewById(R.id.credit_point);
          //  name=itemView.findViewById(R.id.name);

        }
    }


    public AdapterCreditHistory(Context c, ArrayList<HashMap<String,String>> list_names) {
        mContext = c;
        this.list_names = list_names;
        globalClass = ((GlobalClass) mContext.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }


    @Override
    public long getItemId(int position) {
        return position;
    }



}