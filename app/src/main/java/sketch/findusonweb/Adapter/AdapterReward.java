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
import java.util.StringTokenizer;

import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;


public class AdapterReward extends RecyclerView.Adapter<AdapterReward.MyViewHolder> {


    Context context;
    ArrayList<HashMap<String,String>> list_namesfavoriteAll;

    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;





    LayoutInflater inflater;

    public AdapterReward(Context c, ArrayList<HashMap<String,String>> list_namesfavoriteAll) {
        this.context = c;
        this.list_namesfavoriteAll = list_namesfavoriteAll;

        globalClass = ((GlobalClass) c.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        // ImageLoader.getInstance().init(config);
        // loader = ImageLoader.getInstance();


    }
    @Override
    public AdapterReward.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_reward, parent, false);

        AdapterReward.MyViewHolder vh = new AdapterReward.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(AdapterReward.MyViewHolder holder, final int position) {


        String date = list_namesfavoriteAll.get(position).get("date");

        StringTokenizer tk = new StringTokenizer(date);

        String date1 = tk.nextToken();  // <---  yyyy-mm-dd
        String time = tk.nextToken();

        holder.tv_date_manage_proposal.setText(date1);
        holder.tv_date_manage_proposal_days.setText(time);
        holder.tv_manage_proposal.setText(list_namesfavoriteAll.get(position).get("name"));
        holder.tv_manage_proposal_brief.setText(list_namesfavoriteAll.get(position).get("product_title"));
        holder.tv_in_pound.setText(list_namesfavoriteAll.get(position).get("points"));
        holder.tv_total_value.setText(list_namesfavoriteAll.get(position).get("sub_total"));





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
        TextView tv_date_manage_proposal,tv_date_manage_proposal_days,tv_manage_proposal,tv_manage_proposal_brief,
                tv_in_pound,tv_total_value;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            tv_date_manage_proposal =  itemView.findViewById(R.id.tv_date_manage_proposal);
            tv_date_manage_proposal_days =  itemView.findViewById(R.id.tv_date_manage_proposal_days);
            tv_manage_proposal =  itemView.findViewById(R.id.tv_manage_proposal);
            tv_manage_proposal_brief =  itemView.findViewById(R.id.tv_manage_proposal_brief);
            tv_in_pound =  itemView.findViewById(R.id.tv_in_pound);
            tv_total_value =  itemView.findViewById(R.id.tv_total_value);

        }
    }
}