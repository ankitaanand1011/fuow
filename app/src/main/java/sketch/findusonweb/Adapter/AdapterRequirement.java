package sketch.findusonweb.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ivbaranov.mli.MaterialLetterIcon;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Screen.ManageProductScreen;
import sketch.findusonweb.Screen.ViewProductsDetails;


public class AdapterRequirement extends RecyclerView.Adapter<AdapterRequirement.ViewHolder> {

    private LayoutInflater mInflater;
    Context context;
    ArrayList<HashMap<String,String>> list_products;
    ArrayList<String> requirement;
    ImageLoader loader;
    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;

    public AdapterRequirement(Context c, ArrayList<String> requirement) {
        this.mInflater = LayoutInflater.from(c);
        context = c;
        this.requirement =  requirement;

        globalClass = (GlobalClass)context.getApplicationContext();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.requirement_single_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


       holder.tv_title.setText(requirement.get(position));



    }

    @Override
    public int getItemCount() {
        return requirement.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {



        TextView tv_title;

        ViewHolder(View itemView) {
            super(itemView);


            tv_title = itemView.findViewById(R.id.tv_title);




        }


    }
}