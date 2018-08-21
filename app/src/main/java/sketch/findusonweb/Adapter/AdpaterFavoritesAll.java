package sketch.findusonweb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.HashMap;

import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Screen.Job_details;
import sketch.findusonweb.Screen.SendProposal;



public class AdpaterFavoritesAll extends RecyclerView.Adapter<AdpaterFavoritesAll.ViewHolder> {

    Context mContext;

    GlobalClass globalClass;

    LayoutInflater inflater;


    ImageView img;
    ArrayList<HashMap<String,String>> list_names;

    DisplayImageOptions defaultOptions;


    public AdpaterFavoritesAll(Context c, ArrayList<HashMap<String,String>> list_names) {
        mContext = c;
        this.list_names = list_names;
        globalClass = ((GlobalClass) mContext.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.all_single_row, parent, false);
        return new AdpaterFavoritesAll.ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_list,type_list,link_list,delete_list;

        ViewHolder(View itemView) {
            super(itemView);



            title_list=itemView.findViewById(R.id.title_favorites_list);
            type_list=itemView.findViewById(R.id.type_list);
            link_list=itemView.findViewById(R.id.list_link);
            delete_list=itemView.findViewById(R.id.delete_list);






        }



    }

    @Override
    public void onBindViewHolder(@NonNull AdpaterFavoritesAll.ViewHolder holder, final int position) {
        holder.title_list.setText(list_names.get(position).get("listing_title"));
        holder.type_list.setText(list_names.get(position).get("type"));
        holder.link_list.setText(list_names.get(position).get("product_url"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Job_details.class);
                intent.putExtra("id",list_names.get(position).get("id"));
                Log.d("tag", "onClick: "+list_names.get(position).get("id"));
                mContext.startActivity(intent);
            }
        });
        holder.delete_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SendProposal.class);
                intent.putExtra("id", list_names.get(position).get("id"));
                Log.d("tag", "onClick: " + list_names.get(position).get("id"));
                mContext.startActivity(intent);

            }
        });
    }




    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list_names.size();
    }


}