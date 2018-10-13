package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import es.dmoral.toasty.Toasty;
import sketch.findusonweb.Adapter.ChatAdapterDetail;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.ChatMessage;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by developer on 4/10/18.
 */

public class ChatDetail extends AppCompatActivity {
    String TAG = "chat details";
    private EditText msg_edittext;
    ListView msgListView;
    ImageButton send;
    public static ArrayList<ChatMessage> chatlist;
    private String user1 = "user1", user2 = "user2";
    GlobalClass global_class;
    Shared_Preference shared_prefrence;
    ProgressDialog progressBar;
    String str_date;
    Toolbar toolbar;
    ImageLoader loader;
    String seen;
    ImageView toolbar_image;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int offseT , data_recieveD , limiT = 40;
    ArrayList<HashMap<String , String>> arraylist_chatlist, arraylist_chatlist1 , arraylist_chatlist2 ;
    String refresh_status, called;
    TextView user_name;
    ImageView img_back,imageView2;
    Bitmap resized;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_detail_screen);

       
        
        initialisation();
        function();
        
    }

    private void initialisation() {
        global_class = (GlobalClass) getApplicationContext();
        shared_prefrence = new Shared_Preference(ChatDetail.this);
        progressBar=new ProgressDialog(ChatDetail.this);


        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                //  .showImageOnLoading(R.mipmap.loading_black128px)
                //  .showImageForEmptyUri(R.mipmap.no_image)
                //  .showImageOnFail(R.mipmap.no_image)
                //  .showImageOnFail(R.mipmap.img_failed)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ChatDetail.this.getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();
        ImageLoader.getInstance().init(config);
        loader = ImageLoader.getInstance();
        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        msgListView =  findViewById(R.id.lvmsg);
        msg_edittext =  findViewById(R.id.msg_edittext);
        imageView2=findViewById(R.id.imageView2);
        send =  findViewById(R.id.send);
        user_name =  findViewById(R.id.user_name);
        img_back =  findViewById(R.id.img_back);
    }

    private void function() {
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Loading...");

        arraylist_chatlist = new ArrayList<>();
        arraylist_chatlist1 = new ArrayList<>();
        arraylist_chatlist2 = new ArrayList<>();

        chatlist = new ArrayList<>();
        String remote_user_id = getIntent().getStringExtra("remote_user_id");
        String remote_user_name = getIntent().getStringExtra("remote_user_name");
        String remote_profile = getIntent().getStringExtra("remote_profile");
        user_name.setText(remote_user_name);
        Log.d(TAG, "Chat: "+remote_profile);
  if(remote_profile.isEmpty()){
            imageView2.setImageResource(R.mipmap.no_image);
        }else{
      Picasso.get().load(remote_profile).into(imageView2);

  }

        fetch_chat(remote_user_id);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(global_class.isNetworkAvailable()){
                    if(refresh_status.equals("released")) {
                       // fetch_chat_refresh();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);}
            }
        });



       

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((global_class.isNetworkAvailable())){
                    if (!msg_edittext.getText().toString().isEmpty()){
                        String conv_id = getIntent().getStringExtra("conv_id");
                        String remote_user_id = getIntent().getStringExtra("remote_user_id");
                        chat_send(conv_id,remote_user_id);

                    }
                }else {
                    Toasty.info(ChatDetail.this,"Check your internet connection.", Toast.LENGTH_LONG,true).show();

                }
            }
        });


    }


    public  void fetch_chat( String remote_user_id){
        called="1";
        refresh_status = "released";


        String url = AppConfig.URL_DEV;
        AsyncHttpClient cl = new AsyncHttpClient();
        RequestParams jsonParams = new RequestParams();

        jsonParams.put("user_id",global_class.getId());
        jsonParams.put("remote_user_id",remote_user_id);
        jsonParams.put("view","myInbox");


        Log.d("poiu" , "URL "+url);
        Log.d("poiu" , "params "+jsonParams.toString());


        int DEFAULT_TIMEOUT = 30 * 1000;
        cl.setMaxRetriesAndTimeout(5 , DEFAULT_TIMEOUT);
        cl.post(url,jsonParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response != null) {
                        progressBar.dismiss();
                        Log.d("poiu", "reset_pass- " + response.toString());
                        String status = response.optString("success");
                        if (status.equals("1")) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("data");
                                data_recieveD = jsonArray.length();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    JSONArray messages = jsonObject.getJSONArray("messages");

                                    for (int j = 0; j < messages.length(); j++) {
                                        JSONObject jobj = messages.getJSONObject(j);

                                        HashMap<String, String> map = new HashMap<>();

                                        map.put("sender", jobj.getString("sender"));
                                        map.put("receiver", jobj.getString("receiver"));
                                        map.put("message", jobj.getString("message"));
                                        map.put("date", jobj.getString("date"));
                                        map.put("media", jobj.getString("media"));
                                        map.put("sender_name", jobj.getString("sender_name"));
                                        map.put("receiver_name", jobj.getString("receiver_name"));

                                        //Log.d("ujm", "iseen: "+jobj.getString("iseen"));
                                        arraylist_chatlist1.add(map);
                                    }



                                }
                                arraylist_chatlist = arraylist_chatlist1;
                                ChatAdapterDetail chatAdapterDetail = new ChatAdapterDetail(ChatDetail.this, arraylist_chatlist);

                                msgListView.setAdapter(chatAdapterDetail);
                                chatAdapterDetail.notifyDataSetChanged();
                                msgListView.setStackFromBottom(true);


                                String chat_fetch =response.toString();
                                Log.d("price", "json_Data>>> "+chat_fetch);
                                global_class.setChatfetch(chat_fetch);
                                shared_prefrence.savePrefrence();

                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        } else if (status.equals("2")) {
                            Toasty.info(ChatDetail.this, "Message Not Sent.", Toast.LENGTH_SHORT, true).show();
                            progressBar.dismiss();

                        } else if (status.equals("0")) {
                            Toasty.error(ChatDetail.this, "No Data Found.", Toast.LENGTH_SHORT, true).show();

                            progressBar.dismiss();

                        }

                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                    Log.d("TAG", "reset_pass- " + res);

                    android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(ChatDetail.this).create();
                    alert.setMessage("Server Error");
                    alert.show();
                }
            });




    }




    public  void fetch_chat_refresh(){
        called="2";
        refresh_status = "pulled";
        String url = AppConfig.URL_DEV;
        AsyncHttpClient client = new AsyncHttpClient();
        int j =0;
        try {
            if(arraylist_chatlist.size()<1){
                j=0;
            }else{
                j = arraylist_chatlist.size();
            }
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("limit",limiT);
            jsonParams.put("offset",j);
            jsonParams.put("uid",global_class.getId());
            StringEntity entity = new StringEntity(jsonParams.toString());
            Log.d("poiu", "url- " + url);
            Log.d("poiu", "url_entity- " + entity.toString());
            Log.d("poiu", "url_entity- " + jsonParams.toString());
            client.post(ChatDetail.this, url, entity, "application/json", new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    if (response != null) {
                        Log.d("poiu", "reset_pass- " + response.toString());
                        String status = response.optString("status");
                        if (status.equals("1")) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("Messages");
                                data_recieveD = jsonArray.length();
                                arraylist_chatlist2.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    HashMap<String, String> map = new HashMap<>();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    map.put("id", jsonObject.getString("id"));
                                    map.put("name", jsonObject.getString("name"));
                                    map.put("message", jsonObject.getString("message"));
                                    map.put("time", jsonObject.getString("time"));
                                    map.put("message_id", jsonObject.getString("message_id"));
                                    map.put("seen", jsonObject.getString("seen"));
                                    map.put("iseen", jsonObject.getString("iseen"));
                                    arraylist_chatlist2.add(map);
                                }
                                arraylist_chatlist2.addAll(arraylist_chatlist);
                                ChatAdapterDetail ChatAdapterDetail = new ChatAdapterDetail(ChatDetail.this, arraylist_chatlist2);
                                msgListView.setAdapter(ChatAdapterDetail);
                                ChatAdapterDetail.notifyDataSetChanged();
                                msgListView.setSelection(jsonArray.length()+1);
                                arraylist_chatlist.clear();
                                arraylist_chatlist.addAll(arraylist_chatlist2);
                                refresh_status = "released";
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        } else if (status.equals("2")) {
                            Toasty.info(ChatDetail.this, "No more chats", Toast.LENGTH_SHORT, true).show();
                            progressBar.dismiss();

                        } else if (status.equals("0")) {
                            Toasty.error(ChatDetail.this, "No Data Found.", Toast.LENGTH_SHORT, true).show();

                            progressBar.dismiss();

                        }

                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                    Log.d("TAG", "reset_pass- " + res);

                    android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(ChatDetail.this).create();
                    alert.setMessage("Server Error");
                    alert.show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public  void chat_send(String conv_id, String remote_user_id){

        sendTextMessage(msg_edittext.getText().toString().trim());

        String d =msg_edittext.getText().toString();
        msg_edittext.setText("");
        String url = AppConfig.URL_DEV;
        AsyncHttpClient cl = new AsyncHttpClient();
        RequestParams jsonParams = new RequestParams();

        jsonParams.put("user_id",global_class.getId());
        jsonParams.put("remote_user_id",remote_user_id);
        jsonParams.put("view","sendMessage");
        jsonParams.put("conversation_id",conv_id);
        jsonParams.put("message",d);
        jsonParams.put("attachedfile","");


        Log.d("poiu" , "URL "+url);
        Log.d("poiu" , "params "+jsonParams.toString());


        int DEFAULT_TIMEOUT = 30 * 1000;
        cl.setMaxRetriesAndTimeout(5 , DEFAULT_TIMEOUT);
        cl.post(url,jsonParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    if (response != null) {
                        Log.d("TAG", "reset_pass- " + response.toString());


                        String status = response.optString("success");

                        if (status.equals("1")) {


                        } else if (status.equals("2")) {
                            Toasty.info(ChatDetail.this, "Message Not Sent.", Toast.LENGTH_SHORT, true).show();


                        } else if (status.equals("0")) {
                            Toasty.error(ChatDetail.this, "No Data Found.", Toast.LENGTH_SHORT, true).show();

                            // progressBar.dismiss();

                        }

                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                    Log.d("TAG", "reset_pass- " + res);

                    android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(ChatDetail.this).create();
                    alert.setMessage("Server Error");
                    alert.show();
                }
            });



    }


    public void sendTextMessage(String msg) {
        String sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // String now =changeTimeFormat(sdf);
        Log.d("timmy", "sendTextMessage: "+sdf);
        Log.d("timmy", "sendTextMessage: "+changeTimeFormat(sdf));
        ArrayList<HashMap<String, String>> arr = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("sender", global_class.getId());
        map.put("sender_name", global_class.getName());
        map.put("message", msg);
        map.put("date", sdf);
        map.put("message_id", "null");
        map.put("seen", "0");
        map.put("iseen","1");
        arr.add(map);
        Log.d("YPO", arr.size() + " size0");
        Log.d("YPO", arraylist_chatlist.size() + " size00");
        if(called.equals("1")) {
            Log.d("YPO", arraylist_chatlist.size() + " size1");
            arraylist_chatlist.addAll(arr);
            Log.d("YPO", arraylist_chatlist.size() + " size2");
            ChatAdapterDetail chatAdapter = new ChatAdapterDetail(ChatDetail.this, arraylist_chatlist);
            msgListView.setAdapter(chatAdapter);
        }else{
            Log.d("YPO", arraylist_chatlist2.size() + " size1");
            arraylist_chatlist2.addAll(arr);
            Log.d("YPO", arraylist_chatlist2.size() + " size2");
            ChatAdapterDetail chatAdapter = new ChatAdapterDetail(ChatDetail.this, arraylist_chatlist2);
            msgListView.setAdapter(chatAdapter);
        }
        // chatAdapter.notifyDataSetChanged();
        // msgListView.setSelection(arraylist_chatlist2.size());

    }
/*
    public void sendImage(Uri uri) {

        try {

            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            if (inputStream != null) inputStream.close();
            resized = getResizedBitmap(bmp,480);
        }catch (Exception e){

        }


        long time = 0;
        time =  System.currentTimeMillis();
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/HBHG/Images/sent";
        File dir = new File(file_path);
        if(!dir.exists())
            dir.mkdirs();
        final File image_file = new File(dir, "hbhg"+time+ ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(image_file);
            resized.compress(Bitmap.CompressFormat.JPEG, 90, os);
            os.flush();
            os.close();
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(image_file)));
        } catch (Exception e) {

        }

*/
/*
        Date today = Calendar.getInstance().getTime();
        final ChatMessage chatMessage = new ChatMessage("img","me", "test",true);
        chatMessage.note = "sentnow";
        chatMessage.Date = dateFormat.format(today);
        chatMessage.img = Uri.fromFile(image_file).toString();
        arrData.add(chatMessage);
        adapter.notifyDataSetChanged();

        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        // donutProgress=(DonutProgress)listView.getChildAt(listView.getChildCount()-1).findViewById(R.id.donut_progress_iv22);

        postChat("2",image_file.getAbsolutePath());*//*

    }
*/
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        if (width < maxSize && height < maxSize){

            return image;

        }else {

            float bitmapRatio = (float) width / (float) height;
            if (bitmapRatio > 1) {
                width = maxSize;
                height = (int) (width / bitmapRatio);
            } else {
                height = maxSize;
                width = (int) (height * bitmapRatio);
            }

            return Bitmap.createScaledBitmap(image, width, height, true);
        }


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
