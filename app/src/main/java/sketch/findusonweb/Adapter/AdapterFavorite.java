package sketch.findusonweb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Screen.BrowseProductSingleSelection;


public class AdapterFavorite  extends RecyclerView.Adapter<AdapterFavorite.MyViewHolder> {


    Context context;
    ArrayList<HashMap<String,String>> list_namesfavoriteAll;

    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;





    LayoutInflater inflater;

    public AdapterFavorite(Context c,ArrayList<HashMap<String,String>> list_namesfavoriteAll) {
        this.context = c;
        this.list_namesfavoriteAll = list_namesfavoriteAll;

        globalClass = ((GlobalClass) c.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        // ImageLoader.getInstance().init(config);
        // loader = ImageLoader.getInstance();


    }
    @Override
    public AdapterFavorite.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_favorite, parent, false);

        AdapterFavorite.MyViewHolder vh = new AdapterFavorite.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(AdapterFavorite.MyViewHolder holder, final int position) {




        holder.title_favorite.setText(list_namesfavoriteAll.get(position).get("title"));






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
        TextView title_favorite;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            title_favorite =  itemView.findViewById(R.id.title_favorite);

        }
    }
}