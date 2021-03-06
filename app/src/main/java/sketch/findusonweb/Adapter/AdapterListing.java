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



public class AdapterListing extends RecyclerView.Adapter<AdapterListing.MyViewHolder> {


    Context context;
    ArrayList<HashMap<String,String>> list_names;

    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;

    LayoutInflater inflater;

    public AdapterListing(Context c, ArrayList<HashMap<String,String>> list_names
    ) {
        this.context = c;
        this.list_names = list_names;

        globalClass = ((GlobalClass) c.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


       // ImageLoader.getInstance().init(config);
       // loader = ImageLoader.getInstance();


    }
    @Override
    public AdapterListing.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_single_listing, parent, false);

        AdapterListing.MyViewHolder vh = new AdapterListing.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(AdapterListing.MyViewHolder holder, final int position) {


        String name =  list_names.get(position).get("title");
        String id =  list_names.get(position).get("title");
        String status =  list_names.get(position).get("title");


        holder.tv_title.setText(name);
/*
        holder.tv_id.setText(id);
        holder.tv_status.setText(status);
*/





        //  final HashMap<String, String> hashMap_child = (HashMap<String, String>) getChild(groupPosition, childPosition);


/*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BrowseProductSingleSelection.class);
                intent.putExtra("id",list_names.get(position).get("id"));

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
        TextView tv_title,tv_id,tv_status;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            tv_title =  itemView.findViewById(R.id.tv_title);
            tv_id =  itemView.findViewById(R.id.tv_id);
            tv_status =  itemView.findViewById(R.id.tv_status);

        }
    }
}