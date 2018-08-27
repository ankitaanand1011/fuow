package sketch.findusonweb.Adapter;

import android.app.Activity;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import sketch.findusonweb.Screen.LoginScreen;
import sketch.findusonweb.Screen.Messages;
import sketch.findusonweb.Screen.ReviewScreen;
import sketch.findusonweb.Screen.ServiceDetailScreen;


public class AdapterFavoriteDescriptive extends RecyclerView.Adapter<AdapterFavoriteDescriptive.MyViewHolder> {


    Context context;
    ArrayList<HashMap<String,String>> list_namesfavoriteAll;


    ImageLoader loader;
    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;

    LayoutInflater inflater;

    public AdapterFavoriteDescriptive(Context c, ArrayList<HashMap<String,String>> list_namesfavoriteAll) {
        this.context = c;
        this.list_namesfavoriteAll = list_namesfavoriteAll;

        globalClass = ((GlobalClass) c.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        globalClass = ((GlobalClass) context.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


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
    public AdapterFavoriteDescriptive.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_single_row, parent, false);

        AdapterFavoriteDescriptive.MyViewHolder vh = new AdapterFavoriteDescriptive.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(AdapterFavoriteDescriptive.MyViewHolder holder, final int position) {



        holder.tv_name.setText(list_namesfavoriteAll.get(position).get("listing_title"));
     /*   holder.tv_des.setText(list_namesfavoriteAll.get(position).get("description"));
        holder.category.setText(list_namesfavoriteAll.get(position).get("primary_category_name"));
        holder.location_name.setText(list_namesfavoriteAll.get(position).get("location_name"));

        if(list_namesfavoriteAll.get(position).get("logo_url").equals(""))
        {
            holder.img.setImageDrawable(context.getResources().getDrawable(R.mipmap.no_image));
        }
        else {
            loader.displayImage(list_namesfavoriteAll.get(position).get("logo_url"),holder.img, defaultOptions);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ServiceDetailScreen.class);
                intent.putExtra("id",list_namesfavoriteAll.get(position).get("id"));
                Log.d("tag", "onClick: "+list_namesfavoriteAll.get(position).get("id"));
                context.startActivity(intent);
            }
        });

        holder.rl_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalClass.login_status.equals(false)){

                    Intent intent = new Intent(context, LoginScreen.class);
                    context.startActivity(intent);
                    ((Activity)context).finish();

                }
                else {
                    Intent intent = new Intent(context, Messages.class);
                    intent.putExtra("id", list_namesfavoriteAll.get(position).get("id"));
                    context.startActivity(intent);
                }
            }
        });
        holder.rl_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalClass.login_status.equals(false)){

                    Intent intent = new Intent(context, LoginScreen.class);
                    context.startActivity(intent);
                    ((Activity)context).finish();

                }
                else {
                    Intent intent = new Intent(context, ReviewScreen.class);
                    intent.putExtra("id", list_namesfavoriteAll.get(position).get("id"));
                    Log.d("review", "onClick: " + list_namesfavoriteAll.get(position).get("id"));
                    context.startActivity(intent);

                }
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
        TextView tv_name,tv_des,category,location_name,send_message,add_review;
        ImageView img;
        RelativeLayout rl_message,rl_review;
        RatingBar rating;
        LayerDrawable stars;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            send_message=itemView.findViewById(R.id.send_message);
            rl_message=itemView.findViewById(R.id.rl_message);
            rl_review=itemView.findViewById(R.id.Rl_add_review);
            add_review=itemView.findViewById(R.id.add_review);
            location_name=itemView.findViewById(R.id.location_check);
            category=itemView.findViewById(R.id.category);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_des = itemView.findViewById(R.id.tv_des);
            img = itemView.findViewById(R.id.img);
            rating=itemView.findViewById(R.id.rating_adpater);

            stars = (LayerDrawable) rating.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(context.getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);

        }
    }
}