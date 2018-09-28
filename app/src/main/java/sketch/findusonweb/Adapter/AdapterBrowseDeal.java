package sketch.findusonweb.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import sketch.findusonweb.Screen.Job_details;
import sketch.findusonweb.Screen.LoginScreen;
import sketch.findusonweb.Screen.SendProposal;


public class AdapterBrowseDeal extends BaseAdapter {

    Context mContext;

    GlobalClass globalClass;

    LayoutInflater inflater;

    TextView tv_des,tv_name,category,tv_off,tv_price_starting,tv_n_price_starting;
    ImageView img;
   ArrayList<HashMap<String,String>> list_names;
   // ArrayList<String> list_names;
    ImageLoader loader;
    DisplayImageOptions defaultOptions;
    MaterialLetterIcon icon;
    RelativeLayout rl_icon;



    public AdapterBrowseDeal(Context c, ArrayList<HashMap<String,String>> list_names) {
        mContext = c;
        this.list_names = list_names;
        globalClass = ((GlobalClass) mContext.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
/*                 .showImageOnLoading(R.mipmap.loading_black128px)
                 .showImageForEmptyUri(R.mipmap.no_image)
                 .showImageOnFail(R.mipmap.no_image)
                 .showImageOnFail(R.mipmap.img_failed)*/
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext.getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();
        ImageLoader.getInstance().init(config);
        loader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return list_names.size();
    }

    @Override
    public Object getItem(int position) {
        return list_names.size();

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {





        Log.d("TAG", "getItem: "+position);
        View view1 = inflater.inflate(R.layout.browse_deal_single_row, parent, false);

        tv_name=view1.findViewById(R.id.tv_product_name);
        tv_off=view1.findViewById(R.id.tv_off);
        tv_price_starting=view1.findViewById(R.id.tv_price_starting);
        category=view1.findViewById(R.id.category);
        img=view1.findViewById(R.id.img);
        tv_n_price_starting=view1.findViewById(R.id.tv_n_price_starting);

        tv_des = view1.findViewById(R.id.tv_des);
        icon =  view1.findViewById(R.id.icon);
        rl_icon =  view1.findViewById(R.id.rl_icon);

        int[] androidColors = mContext.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        String n_price = list_names.get(position).get("new_price");
        String price = list_names.get(position).get("price");
        tv_price_starting.setPaintFlags(tv_price_starting.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        tv_name.setText(list_names.get(position).get("title"));
        tv_off.setText(list_names.get(position).get("discount_value")+" off");
        tv_price_starting.setText(price);
       // tv_des.setText(list_names.get(position).get("description"));
        category.setText(list_names.get(position).get("listing_name"));
        tv_n_price_starting.setText(n_price);


        img.setVisibility(View.GONE);
        icon.setVisibility(View.VISIBLE);
        icon.setLetter(list_names.get(position).get("title"));
        icon.setLetterColor(mContext.getResources().getColor(R.color.black));
        icon.setShapeColor(mContext.getResources().getColor(R.color.white));
        icon.setShapeType(MaterialLetterIcon.Shape.CIRCLE);
        icon.setLetterSize(26);
        icon.setLetterTypeface(Typeface.SANS_SERIF);
        icon.setInitials(true);
        icon.setInitialsNumber(2);

        rl_icon.setBackgroundColor(randomAndroidColor);

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

        return view1;
    }
}
