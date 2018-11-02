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

public class AdapterBusinessBriefingTitle3 extends RecyclerView.Adapter<AdapterBusinessBriefingTitle3.ViewHolder> {

    private LayoutInflater mInflater;
    Context context;
    String TAG="adapterBrief";
    ArrayList<HashMap<String, String>> list_needs;
    ImageLoader loader;
    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;
    ArrayList<String> list3String;

    public AdapterBusinessBriefingTitle3(Context c, ArrayList<HashMap<String, String>> list_needs) {
        this.mInflater = LayoutInflater.from(c);
        context = c;
        this.list_needs = list_needs;

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


        list3String = new ArrayList<>();
        initValues();

    }

    private void initValues(){
        for (int i = 0; i < list_needs.size(); i++){
            list3String.add("");
        }

    }



    @Override
    public AdapterBusinessBriefingTitle3.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.single_business_brief_checkbox, parent, false);
        return new AdapterBusinessBriefingTitle3.ViewHolder(view);


    }



    @Override
    public void onBindViewHolder(AdapterBusinessBriefingTitle3.ViewHolder holder, final int position) {
        //   int po = position+1;


        // holder.tv_from_val.setText(list_products.get(position).get("id"));
        // holder.tv_edit.setText(list_products.get(position).get("from_user"));
        Log.d(TAG, "onBindViewHolder: "+list_needs.get(position).get("label_name"));
        if(list_needs.get(position).get("type").equals("text")){
            holder.ed_Lname.setHint(list_needs.get(position).get("label_name"));

            holder.checkBox1.setVisibility(View.GONE);
            holder.ed_Lname.setVisibility(View.VISIBLE);
            holder.lName.setVisibility(View.GONE);
            holder.ed_Lname.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    //setting data to array, when changed
                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (s.toString().length() == 0){
                        list3String.set(position, "");
                    }else {
                        list3String.set(position, s.toString());
                    }

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                    //blank
                }

            });

        }
        else {
            holder.lName.setHint(list_needs.get(position).get("label_name"));
            if(list_needs.get(position).get("value").equals("1")){
                holder.checkBox1.setChecked(true);
            }
            else
            {
                holder.checkBox1.setChecked(false);
            }

            holder.lName.setVisibility(View.VISIBLE);
            holder.checkBox1.setVisibility(View.VISIBLE);
            holder.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked){

                        list3String.set(position, list_needs.get(position).get("label_name"));
                    }else {
                        list3String.set(position, "");


                    }

                }
            });

        }






    }

    @Override
    public int getItemCount() {
        return list_needs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lName;
            CheckBox checkBox1;
            EditText ed_Lname;

        ViewHolder(View itemView) {
            super(itemView);

            lName = itemView.findViewById(R.id.lName);
            checkBox1 = itemView.findViewById(R.id.checkBox1);
            ed_Lname = itemView.findViewById(R.id.edt_lName);


        }

    }


    public ArrayList<String> getListString() {
        return list3String;
    }
}