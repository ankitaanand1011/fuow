package sketch.findusonweb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import sketch.findusonweb.Screen.EditSchedule;

/**
 * Created by developer on 25/10/18.
 */

public class AdapterBusinessBriefing extends RecyclerView.Adapter<AdapterBusinessBriefing.ViewHolder> {

    private LayoutInflater mInflater;
    Context context;
String TAG="adapterBrief";
    ArrayList<HashMap<String, String>> list_brief;
    ImageLoader loader;
    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;
    ArrayList<String> listString;

    public AdapterBusinessBriefing(Context c, ArrayList<HashMap<String, String>> list_brief) {
        this.mInflater = LayoutInflater.from(c);
        context = c;
        this.list_brief = list_brief;

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

        listString = new ArrayList<>();
        initValues();

    }

    private void initValues(){
        for (int i = 0; i < list_brief.size(); i++){
            listString.add("");
        }

    }

    @Override
    public AdapterBusinessBriefing.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.single_business_brief, parent, false);
        return new AdapterBusinessBriefing.ViewHolder(view);


    }



    @Override
    public void onBindViewHolder(AdapterBusinessBriefing.ViewHolder holder, final int position) {
        //   int po = position+1;


        // holder.tv_from_val.setText(list_products.get(position).get("id"));
        // holder.tv_edit.setText(list_products.get(position).get("from_user"));
        holder.lName.setHint(list_brief.get(position).get("label_name"));
        Log.d(TAG, "onBindViewHolder: "+list_brief.get(position).get("label_name"));



        holder.lName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                //setting data to array, when changed
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() == 0){
                    listString.set(position, "");
                }else {
                    listString.set(position, s.toString());
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                //blank
            }

        });


    }

    @Override
    public int getItemCount() {
        return list_brief.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

       EditText lName;

        ViewHolder(View itemView) {
            super(itemView);

            lName = itemView.findViewById(R.id.lName);


        }

    }

    public ArrayList<String> getListString() {
        return listString;
    }
}
