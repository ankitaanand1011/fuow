package sketch.findusonweb.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

/**
 * Created by developer on 27/10/18.
 */

public class AdapterbusinessBriefingTitle2 extends RecyclerView.Adapter<AdapterbusinessBriefingTitle2.ViewHolder> {

    private LayoutInflater mInflater;
    Context context;
    String TAG="adapterBrief";
    ArrayList<HashMap<String, String>> list_service;
    ImageLoader loader;
    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;
    ArrayList<String> listMyServiceString;

    public AdapterbusinessBriefingTitle2(Context c, ArrayList<HashMap<String, String>> list_service) {
        this.mInflater = LayoutInflater.from(c);
        context = c;
        this.list_service = list_service;

        globalClass = (GlobalClass) context.getApplicationContext();
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

        listMyServiceString = new ArrayList<>();
        initServiceValues();

    }
    public void initServiceValues(){
        for (int i = 0; i < list_service.size(); i++){
            listMyServiceString.add("");
        }

    }

    @Override
    public AdapterbusinessBriefingTitle2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.single_business_brief_checkbox, parent, false);
        return new AdapterbusinessBriefingTitle2.ViewHolder(view);


    }



    @Override
    public void onBindViewHolder(final AdapterbusinessBriefingTitle2.ViewHolder holder, final int position) {
        //   int po = position+1;


        // holder.tv_from_val.setText(list_products.get(position).get("id"));
        // holder.tv_edit.setText(list_products.get(position).get("from_user"));
        Log.d(TAG, "onBindViewHolder: "+list_service.get(position).get("label_name"));
        if(list_service.get(position).get("type").equals("text")){
            holder.ed_lName.setText(list_service.get(position).get("label_name"));

            holder.checkBox1.setVisibility(View.GONE);
            holder.checkBox1.setVisibility(View.GONE);
            holder.ed_lName.setVisibility(View.VISIBLE);
            holder.lName.setVisibility(View.GONE);
        }
        else {
            holder.lName.setText(list_service.get(position).get("label_name"));

            holder.checkBox1.setVisibility(View.VISIBLE);
        }
        //  holder.tv_end_time_val.setText(list_products.get(position).get("sync"));

        holder.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){

                    listMyServiceString.set(position, list_service.get(position).get("label_name"));
                }else {
                    listMyServiceString.set(position, "");


                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list_service.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView lName;
        CheckBox checkBox1;
EditText ed_lName;

        ViewHolder(View itemView) {
            super(itemView);

            lName = itemView.findViewById(R.id.lName);
            checkBox1 = itemView.findViewById(R.id.checkBox1);
            ed_lName = itemView.findViewById(R.id.edt_lName);





        }

    }
    public ArrayList<String> getListString() {
        return listMyServiceString;
    }
}