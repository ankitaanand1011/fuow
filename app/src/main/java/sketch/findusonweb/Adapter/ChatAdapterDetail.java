package sketch.findusonweb.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;

/**
 * Created by ANDROID on 6/21/2016.
 */
public class ChatAdapterDetail extends BaseAdapter {
    Context mContext;
    private static LayoutInflater inflater = null;
    ArrayList<HashMap<String , String>> chatMessageList;
    public ImageLoader loader;
    DisplayImageOptions defaultOptions;
    GlobalClass global;

    public ChatAdapterDetail(FragmentActivity activity, ArrayList<HashMap<String, String>> arraylist_chatlist) {
        this.mContext = activity;
        chatMessageList = arraylist_chatlist;
        global = (GlobalClass) mContext.getApplicationContext();
        defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();
        final ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(mContext)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
        loader = ImageLoader.getInstance();
    }

    public int getCount() {
        return chatMessageList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = null;
        if(chatMessageList.get(position).get("sender").equals(global.getId())) {
            rowView = inflater.inflate(R.layout.chat_row_out, null, true);
            TextView chat_message2 = rowView.findViewById(R.id.chat_message2);
            TextView user_name = rowView.findViewById(R.id.user_name);
            TextView chat2_time = rowView.findViewById(R.id.chat2_time);

            chat_message2.setText(chatMessageList.get(position).get("message").replaceAll("\"", ""));
    //        user_name.setText(chatMessageList.get(position).get("sender_name"));
            chat2_time.setText(changeTimeFormat(chatMessageList.get(position).get("date")));
         //   Log.d("ujm", "thm: "+chatMessageList.get(position).get("iseen"));


        }else{
            rowView = inflater.inflate(R.layout.chat_row_in, null, true);
            TextView chat_message1 = rowView.findViewById(R.id.chat_message1);
            TextView user_name_incoming = rowView.findViewById(R.id.user_name_incoming);
            TextView chat1_time = rowView.findViewById(R.id.chat1_time);

            chat_message1.setText(chatMessageList.get(position).get("message").replaceAll("\"", ""));
            //user_name_incoming.setText(chatMessageList.get(position).get("receiver_name"));
            chat1_time.setText(changeTimeFormat(chatMessageList.get(position).get("date")));


            }


        //
        return rowView;
    }


    public String changeTimeFormat(String time){
        SimpleDateFormat df_source = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        SimpleDateFormat df_destination = new SimpleDateFormat("EEE, d MMM yy, hh:mm a", Locale.ENGLISH);
        Date d = null;
        String s = null;
        try {
            d = df_source.parse(time);
            s = df_destination.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return s;
    }
}
