package sketch.findusonweb.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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


public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {

    private LayoutInflater mInflater;
    Context context;
    ArrayList<HashMap<String,String>> list_products;
    ImageLoader loader;
    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;

    public AdapterProduct(Context c, ArrayList<HashMap<String,String>> list_products) {
        this.mInflater = LayoutInflater.from(c);
        context = c;
        this.list_products = list_products;

        globalClass = (GlobalClass)context.getApplicationContext();
         defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                //  .showImageOnLoading(R.mipmap.loading_black128px)
                //  .showImageForEmptyUri(R.mipmap.no_image)
                //  .showImageOnFail(R.mipmap.no_image)
                //  .showImageOnFail(R.mipmap.img_failed)
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.product_single_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        holder.tv_product_name.setText(list_products.get(position).get("title"));
        holder.tv_des.setText(list_products.get(position).get("listing_name"));
        holder.category.setText(list_products.get(position).get("type"));
        holder.tv_price_starting.setText("Starting at "+globalClass.pound+list_products.get(position).get("price"));


        holder.rl_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ViewProductsDetails.class);
                intent.putExtra("title",list_products.get(position).get("title"));
                intent.putExtra("listing_name",list_products.get(position).get("listing_name"));
                intent.putExtra("description",list_products.get(position).get("description"));
                intent.putExtra("price",list_products.get(position).get("price"));
                intent.putExtra("custom_15",list_products.get(position).get("custom_15"));
                intent.putExtra("custom_58",list_products.get(position).get("custom_58"));
                context.startActivity(intent);
            }
        });

        holder.rl_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ManageProductScreen.class);
                context.startActivity(intent);
            }
        });


        holder.img_product.setVisibility(View.GONE);
        holder.icon.setVisibility(View.VISIBLE);
        holder.icon.setLetter(list_products.get(position).get("title"));
        holder.icon.setLetterColor(context.getResources().getColor(R.color.black));
        holder.icon.setShapeColor(context.getResources().getColor(R.color.white));
        holder.icon.setShapeType(MaterialLetterIcon.Shape.CIRCLE);
        holder.icon.setLetterSize(26);
        holder.icon.setLetterTypeface(Typeface.SANS_SERIF);
        holder.icon.setInitials(true);
        holder.icon.setInitialsNumber(2);

        holder.rl_icon.setBackgroundColor(randomAndroidColor);


         /*   if(list_names.get(position).get("profile_pic").equals(""))
        {
            img.setVisibility(View.GONE);
            icon.setVisibility(View.VISIBLE);
            icon.setLetter(list_names.get(position).get("title"));
            icon.setLetterColor(mContext.getResources().getColor(R.color.black));
            icon.setShapeColor(R.color.white);
            icon.setShapeType(MaterialLetterIcon.Shape.CIRCLE);
            icon.setLetterSize(26);
            icon.setLetterTypeface(Typeface.SANS_SERIF);
            icon.setInitials(true);
            icon.setInitialsNumber(2);

            rl_icon.setBackgroundColor(randomAndroidColor);
        }
        else {
            img.setVisibility(View.VISIBLE);
            rl_icon.setVisibility(View.GONE);
            loader.displayImage(list_names.get(position).get("profile_pic"), img, defaultOptions);
        }*/


       /* if(list_products.get(position).get("product_image").equals("null") ||
                list_products.get(position).get("product_image").equals("")){

            holder.img_product.setImageResource(R.mipmap.no_image);
        }else{
            loader.displayImage(list_products.get(position).get("product_image"), holder.img_product , defaultOptions);
        }*/


    }

    @Override
    public int getItemCount() {
        return list_products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        ImageView img_product;
        TextView tv_product_name, tv_des, tv_price_starting,category;
        RelativeLayout rl_manage,rl_view;
        MaterialLetterIcon icon;
        RelativeLayout rl_icon;
        ViewHolder(View itemView) {
            super(itemView);

            img_product = itemView.findViewById(R.id.img);
            tv_price_starting = itemView.findViewById(R.id.tv_price_starting);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_des = itemView.findViewById(R.id.tv_des);
            category = itemView.findViewById(R.id.category);
            rl_manage = itemView.findViewById(R.id.rl_manage);
            rl_view = itemView.findViewById(R.id.rl_view);
            icon =  itemView.findViewById(R.id.icon);
            rl_icon =  itemView.findViewById(R.id.rl_icon);
          //  tv_product_price = itemView.findViewById(R.id.tv_product_price);



        }


    }
}