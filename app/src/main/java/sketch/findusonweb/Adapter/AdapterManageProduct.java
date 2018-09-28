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


public class AdapterManageProduct extends RecyclerView.Adapter<AdapterManageProduct.ViewHolder> {

    private LayoutInflater mInflater;
    Context context;
    ArrayList<HashMap<String,String>> list_products;
    ImageLoader loader;
    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;

    public AdapterManageProduct(Context c, ArrayList<HashMap<String,String>> list_products) {
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
        View view = mInflater.inflate(R.layout.manage_product_single_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

      /*  int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

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



    /*    holder.tv_name.setText(list_products.get(position).get("title"));
        holder.tv_des.setText(list_products.get(position).get("listing_name"));
        holder.category.setText(list_products.get(position).get("type"));
        holder.tv_price_starting.setText("Starting at "+globalClass.pound+list_products.get(position).get("price"));


        holder.rl_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.rl_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ManageProductScreen.class);
                context.startActivity(intent);
            }
        });
*/

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


        ImageView img;
        TextView tv_id_value, tv_name,tv_des, tv_price_value,category;
        RelativeLayout rl_edit,rl_delete;
        MaterialLetterIcon icon;
        RelativeLayout   rl_icon ;

        ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            tv_id_value = itemView.findViewById(R.id.tv_id_value);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_price_value = itemView.findViewById(R.id.tv_price_value);
            tv_des = itemView.findViewById(R.id.tv_des);
            category = itemView.findViewById(R.id.tv_cat);
            rl_edit = itemView.findViewById(R.id.rl_edit);
            rl_delete = itemView.findViewById(R.id.rl_delete);
            icon =  itemView.findViewById(R.id.icon);
            rl_icon =  itemView.findViewById(R.id.rl_icon);
          //  tv_product_price = itemView.findViewById(R.id.tv_product_price);



        }


    }
}