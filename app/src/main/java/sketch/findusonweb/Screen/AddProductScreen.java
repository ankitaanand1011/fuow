package sketch.findusonweb.Screen;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.provider.MediaStore;

import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;

import sketch.findusonweb.Adapter.AdapterRequirement;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.Controller.Sliding;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;



public class AddProductScreen extends AppCompatActivity {
    ImageView back_img;
    Spinner spinner_type,spinner_status;
    EditText edt_title,edt_description,edt_price,edt_expire,edt_tax_price;
    ArrayList<String> type;
    ArrayList<String> status;
    ArrayList<String> requirement;
    RelativeLayout primary_details,requirement_new,gallery;
    String TAG = "add_product";
    ProgressDialog pd;
    String listing_id;
    GlobalClass globalClass;
    ImageView arrow_img_1,arrow_img_2,arrow_img_3,arrow_img_4,arrow_img_5,arrow_img_6,arrow_img_7;
    LinearLayout rl_account_detail,rl_listing_detail,rl_favorite_detail;

    Shared_Preference prefrence;
    String status_text,type_text;
    TextView tv_submit;
    ImageButton attach_data,attach_data1,attach_data2;
    ImageView img_attach_1,img_attach_2,img_attach_3,img_btn;
    String title , description, expire, price,tax;
    File file1,file2,file3;
    Float new_tax_price;
    private static final int PICK_IMAGE_CAMERA1 = 11;
    private static final int PICK_IMAGE_CAMERA2 = 12;
    private static final int PICK_IMAGE_CAMERA3 = 13;

    private static final int PICK_IMAGE_GALLERY1 = 21;
    private static final int PICK_IMAGE_GALLERY2 = 22;
    private static final int PICK_IMAGE_GALLERY3 = 23;
    int mYear, mMonth, mDay;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener datepicker;
    static final int DATE_PICKER_ID = 1111;
    SimpleDateFormat simpleDateFormat;
    String date_to_send;
    ArrayList<String> ans_type;
    Spinner spinner_ans_type;
    Sliding popup;
    int key=0;
    ScrollView scrl_mian;
    TextView tv_view_all,tv_add_listing,tv_view_all_fav,tv_top_up,tv_new_search,
            tv_view_all_invoice,tv_summary,tv,tv_view_publication_list,tv_production_service,
            tv_plus_product_service,tv_my_business_brief,tv_purchase_context,tv_one_page_business,
            tv_promotiion_discount,tv_subs_details,tv_edit_list,tv_statistics,tv_my_busiiness_review,
            tv_gallery,tv_galley_plus,tv_document,tv_document_plus,tv_event,tv_event_plus,tv_banner,tv_banner_plus,tv_location,tv_location_plus;
    TextView tv_primary_detail,tv_requirement,tv_image_gallery,tv_add_requiremnt;
     String id,title_product;
    ImageView btn;
    LinearLayout ll_primary_detail,ll_requirement,ll_image_gallery,ll_radio_option;
    LinearLayout rl_add_requirement;
    RelativeLayout rl_requirement_list;

    RecyclerView rv_requirement;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_services);
        popup =  findViewById(R.id.sliding1);
        scrl_mian=findViewById(R.id.scrl_main);
        popup.setVisibility(View.GONE);
        btn=findViewById(R.id.show1);
        initialisation();
        function();


    }

    private void initialisation() {
        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(AddProductScreen.this);
        prefrence.loadPrefrence();
        tv_subs_details=findViewById(R.id.tv_subs_details);
        rl_account_detail=findViewById(R.id.ll_primary_detail);
        rl_listing_detail=findViewById(R.id.ll_add_requirement);
        rl_favorite_detail=findViewById(R.id.ll_image_gallery);
        pd = new ProgressDialog(AddProductScreen.this);
        primary_details=findViewById(R.id.rl_primary);
        requirement_new=findViewById(R.id.rl_requirement);
        gallery=findViewById(R.id.rl_gallery);
        tv_summary=findViewById(R.id.tv_summary);
        tv_view_publication_list=findViewById(R.id.tv_view_public_listing);
        tv_production_service=findViewById(R.id.tv_product_service);
        tv_plus_product_service=findViewById(R.id.tv_plus_product_service);
        tv_my_business_brief=findViewById(R.id.my_business_earning);
        tv_purchase_context=findViewById(R.id.purchase_context);
        tv_one_page_business=findViewById(R.id.tv_one_page_business);
        tv_promotiion_discount=findViewById(R.id.tv_promotion_discount);
        tv_subs_details=findViewById(R.id.tv_subs_details);
        tv_edit_list=findViewById(R.id.tv_edit_list);
        tv_statistics=findViewById(R.id.tv_statistics);
        tv_my_busiiness_review=findViewById(R.id.tv_my_business_review);
        tv_gallery=findViewById(R.id.tv_gallery);
        tv_galley_plus=findViewById(R.id.tv_gallery_plus);
        tv_document=findViewById(R.id.tv_document);
        tv_document_plus=findViewById(R.id.tv_document_plus);
        tv_event=findViewById(R.id.tv_event);
        tv_event_plus=findViewById(R.id.tv_event_plus);
        tv_banner=findViewById(R.id.tv_banner);
        tv_banner_plus=findViewById(R.id.tv_banner_plus);
        tv_location=findViewById(R.id.tv_location);
        tv_location_plus=findViewById(R.id.tv_locations_plus);
        arrow_img_2 = findViewById(R.id.arrow_img_2);
        arrow_img_3 = findViewById(R.id.arrow_img_3);
        edt_tax_price=findViewById(R.id.edt_tax_price);
        back_img=findViewById(R.id.back_img);
        spinner_type=findViewById(R.id.spinner_type);
        spinner_status=findViewById(R.id.spinner_status);
        edt_title=findViewById(R.id.edt_title);
        edt_description=findViewById(R.id.edt_description);
        edt_price=findViewById(R.id.edt_price);
        edt_expire=findViewById(R.id.edt_expire);
        attach_data=findViewById(R.id.attach_data);
        attach_data1=findViewById(R.id.attach_data1);
        attach_data2=findViewById(R.id.attach_data2);
        img_attach_1=findViewById(R.id.img_attach_1);
        img_attach_2=findViewById(R.id.img_attach_2);
        img_attach_3=findViewById(R.id.img_attach_3);
        tv_submit=findViewById(R.id.tv_submit);
        img_btn=findViewById(R.id.img_btn);
        tv_add_requiremnt=findViewById(R.id.tv_add_requiremnt);
        tv_primary_detail=findViewById(R.id.tv_primary_detail);
        tv_requirement=findViewById(R.id.tv_requirement);
        tv_image_gallery=findViewById(R.id.tv_image_gallery);
        ll_primary_detail=findViewById(R.id.ll_primary_detail);
        ll_requirement=findViewById(R.id.ll_requirement);
        ll_image_gallery=findViewById(R.id.ll_image_gallery);
        rl_add_requirement=findViewById(R.id.ll_add_requirement);
        rl_requirement_list=findViewById(R.id.rl_requirement_list);
        rv_requirement=findViewById(R.id.rv_requirement);
        spinner_ans_type = findViewById(R.id.spinner_ans_type);
        ll_radio_option = findViewById(R.id.ll_radio_option);
        arrow_img_1 = findViewById(R.id.arrow_img_1);

    }

    private void function() {

        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(AddProductScreen.this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(AddProductScreen.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onCreate: ");
            }
            else{
                if(checkForPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA}, 124)){
                    Log.d(TAG, "onCreate: ");
                }

            }
        }

       id=getIntent().getStringExtra("id");
        title_product=getIntent().getStringExtra("title");
        Log.d(TAG, "ID of Activty: "+id);
       // function_primary_detail();
       // function_requirement();
       // function_image_gallery();
        rl_account_detail.setVisibility(View.GONE);
        rl_listing_detail.setVisibility(View.GONE);
        rl_favorite_detail.setVisibility(View.GONE);
        primary_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_account_detail.setVisibility(View.VISIBLE);
                rl_listing_detail.setVisibility(View.GONE);
                rl_favorite_detail.setVisibility(View.GONE);


                arrow_img_1.setVisibility(View.GONE);
                arrow_img_2.setVisibility(View.VISIBLE);
                arrow_img_3.setVisibility(View.VISIBLE);



            }
        });
        tv_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent summary=new Intent(AddProductScreen.this,Summary_new.class);
                summary.putExtra("id",id);
                 startActivity(summary);
            }
        });
        tv_one_page_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent summary=new Intent(AddProductScreen.this,OnePageBusinessPlan.class);
                summary.putExtra("id",id);
                summary.putExtra("title",title_product);
                 startActivity(summary);
            }
        });
        tv_my_business_brief.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent summary=new Intent(AddProductScreen.this,BusinessBriefing.class);
                summary.putExtra("id",id);

                startActivity(summary);
            }
        });
        tv_subs_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent summary=new Intent(AddProductScreen.this,SunscriptionsDetails.class);
                summary.putExtra("id",id);

                startActivity(summary);
            }
        });
        tv_plus_product_service .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent summary=new Intent(AddProductScreen.this,AddProductScreen.class);
                summary.putExtra("id",id);

                startActivity(summary);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(key==0){
                    key=1;
                    popup.setVisibility(View.VISIBLE);
                    btn.setBackgroundResource(R.mipmap.arrow);
                    scrl_mian.setVisibility(View.GONE);
                }
                else if(key==1){
                    key=0;
                    popup.setVisibility(View.GONE);
                    btn.setBackgroundResource(R.mipmap.arrow);
                    scrl_mian.setVisibility(View.VISIBLE);
                }
            }
        });
        tv_production_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent manageProduct=new Intent(AddProductScreen.this,ManageProductScreen.class);
                manageProduct.putExtra("id",id);
                startActivity(manageProduct);
            }
        });

        requirement_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_listing_detail.setVisibility(View.VISIBLE);
                rl_account_detail.setVisibility(View.GONE);
                rl_favorite_detail.setVisibility(View.GONE);


                arrow_img_1.setVisibility(View.VISIBLE);
                arrow_img_2.setVisibility(View.GONE);
                arrow_img_3.setVisibility(View.VISIBLE);



            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_listing_detail.setVisibility(View.GONE);
                rl_account_detail.setVisibility(View.GONE);
                rl_favorite_detail.setVisibility(View.VISIBLE);


                arrow_img_1.setVisibility(View.VISIBLE);
                arrow_img_2.setVisibility(View.VISIBLE);
                arrow_img_3.setVisibility(View.GONE);



            }
        });
      /*  ll_primary_detail.setVisibility(View.VISIBLE);
        ll_requirement.setVisibility(View.GONE);
        ll_image_gallery.setVisibility(View.GONE);

        tv_add_requiremnt.setVisibility(View.GONE);*/

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





    }

/*
    private void function_primary_detail() {
        type = new ArrayList<>();
        status = new ArrayList<>();

        type.add("Product");
        type.add("Service");
        type.add("Promotional");

        status.add("Active");
        status.add("Inactive");

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = new DatePickerDialog(AddProductScreen.this, datepicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                String today_date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                Date d = null;

                try {
                    d = simpleDateFormat.parse(today_date);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                dpd.getDatePicker();
                dpd.show();
            }


        });
        datepicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MMM dd, yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                date_to_send = sdf1.format(myCalendar.getTime());
                String date_to_show = sdf.format(myCalendar.getTime());
                Log.d("TP", date_to_send + " " + date_to_show);
                edt_expire.setText(date_to_show);
            }
        };


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.spinner_color,R.id.txt,type)
        {

            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View view = super.getDropDownView(position, convertView, parent);

                if (position == 0) {



                } else {
                }
                return view;


            }
        };


        dataAdapter.setDropDownViewResource(R.layout.spinner_color);
        spinner_type.setAdapter(dataAdapter);


        ArrayAdapter<String> dataAdapter1  = new ArrayAdapter<String>(this,R.layout.spinner_color,R.id.txt,status)
        {

            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View view = super.getDropDownView(position, convertView, parent);

                if (position == 0) {



                } else {
                }
                return view;


            }
        };


        dataAdapter1.setDropDownViewResource(R.layout.spinner_color);
        spinner_status.setAdapter(dataAdapter1);


        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                // Locate the textviews in activity_main.xml
                type_text= parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemSelected: "+type_text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                // Locate the textviews in activity_main.xml
                status_text= parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemSelected: "+status_text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = edt_title.getText().toString();
                description = edt_description.getText().toString();
                expire = edt_expire.getText().toString();
                price = edt_price.getText().toString();
               */
/* Float f= Float.parseFloat(price);
                new_tax_price=((20/100)*f);*//*

                 tax=edt_tax_price.getText().toString();

                postjob(title,description,price,expire,tax);
                //  user_profile_pic_update_url();
            }
        });

        attach_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder(PICK_IMAGE_CAMERA1,PICK_IMAGE_GALLERY1);
            }
        });

        attach_data1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder(PICK_IMAGE_CAMERA2,PICK_IMAGE_GALLERY2);
            }
        });
        attach_data2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder(PICK_IMAGE_CAMERA3,PICK_IMAGE_GALLERY3);
            }
        });


*/
/*
        tv_primary_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_primary_detail.setTextColor(getResources().getColor(R.color.white));
                tv_primary_detail.setBackground(getResources().getDrawable(R.drawable.bg_add_product_tab_left));
                tv_requirement.setTextColor(getResources().getColor(R.color.black));
                tv_requirement.setBackground(getResources().getDrawable(R.drawable.bg_add_product_tab_white));
                tv_image_gallery.setTextColor(getResources().getColor(R.color.black));
                tv_image_gallery.setBackground(getResources().getDrawable(R.drawable.bg_tab_right_white));

                ll_primary_detail.setVisibility(View.VISIBLE);
                ll_requirement.setVisibility(View.GONE);
                ll_image_gallery.setVisibility(View.GONE);

                tv_add_requiremnt.setVisibility(View.GONE);
            }
        });
*//*

        primary_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_account_detail.setVisibility(View.VISIBLE);
                rl_listing_detail.setVisibility(View.GONE);
                rl_favorite_detail.setVisibility(View.GONE);


                arrow_img_1.setVisibility(View.GONE);
                arrow_img_2.setVisibility(View.VISIBLE);
                arrow_img_3.setVisibility(View.VISIBLE);



            }
        });
        requirement_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_listing_detail.setVisibility(View.VISIBLE);
                rl_account_detail.setVisibility(View.GONE);
                rl_favorite_detail.setVisibility(View.GONE);


                arrow_img_1.setVisibility(View.VISIBLE);
                arrow_img_2.setVisibility(View.GONE);
                arrow_img_3.setVisibility(View.VISIBLE);



            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_listing_detail.setVisibility(View.GONE);
                rl_account_detail.setVisibility(View.GONE);
                rl_favorite_detail.setVisibility(View.VISIBLE);


                arrow_img_1.setVisibility(View.VISIBLE);
                arrow_img_2.setVisibility(View.VISIBLE);
                arrow_img_3.setVisibility(View.GONE);



            }
        });

    }
*/

    private void function_requirement() {

        ans_type = new ArrayList<>();
        requirement = new ArrayList<>();

        ans_type.add("Free Text");
        ans_type.add("Radio Options");
        ans_type.add("Multiple Answers");
        ans_type.add("Attached Files");

        requirement.add("sdsfdgfh");
        requirement.add("zdffggds");


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
       rv_requirement.setLayoutManager(mLayoutManager);
        AdapterRequirement adapterRequirement = new AdapterRequirement(AddProductScreen.this, requirement);
        rv_requirement.setAdapter(adapterRequirement);


        ArrayAdapter<String> adp = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, ans_type);
        spinner_ans_type.setAdapter(adp);

        spinner_ans_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //@Override
            public void onItemSelected(AdapterView<?> parent, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                String item = spinner_ans_type.getItemAtPosition(0).toString();

                String text = spinner_ans_type.getSelectedItem().toString();

                if(text.equals("Free Text") || text.equals("Attached Files")){
                    ll_radio_option.setVisibility(View.GONE);


                }else {

                    ll_radio_option.setVisibility(View.VISIBLE);
                }



                }


            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });




        tv_requirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_primary_detail.setTextColor(getResources().getColor(R.color.black));
                tv_primary_detail.setBackground(getResources().getDrawable(R.drawable.bg_add_product_tab_white));
                tv_requirement.setTextColor(getResources().getColor(R.color.white));
                tv_requirement.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_image_gallery.setTextColor(getResources().getColor(R.color.black));
                tv_image_gallery.setBackground(getResources().getDrawable(R.drawable.bg_tab_right_white));

                ll_primary_detail.setVisibility(View.GONE);
                ll_requirement.setVisibility(View.VISIBLE);
                ll_image_gallery.setVisibility(View.GONE);

                tv_add_requiremnt.setVisibility(View.VISIBLE);
                rl_add_requirement.setVisibility(View.GONE);
                rl_requirement_list.setVisibility(View.VISIBLE);
            }
        });



        tv_add_requiremnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_add_requirement.setVisibility(View.VISIBLE);
                rl_requirement_list.setVisibility(View.GONE);



            }
        });
    }

    private void function_image_gallery() {

        tv_image_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_primary_detail.setTextColor(getResources().getColor(R.color.black));
                tv_primary_detail.setBackground(getResources().getDrawable(R.drawable.bg_tab_left_white));
                tv_requirement.setTextColor(getResources().getColor(R.color.black));
                tv_requirement.setBackground(getResources().getDrawable(R.drawable.bg_add_product_tab_white));
                tv_image_gallery.setTextColor(getResources().getColor(R.color.white));
                tv_image_gallery.setBackground(getResources().getDrawable(R.drawable.bg_add_product_tab_right));

                ll_primary_detail.setVisibility(View.GONE);
                ll_requirement.setVisibility(View.GONE);
                ll_image_gallery.setVisibility(View.VISIBLE);

                tv_add_requiremnt.setVisibility(View.GONE);
            }
        });



    }





    public void openFolder(final int code_camera,final int code_gallery) {


        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {
                        getResources().getString(R.string.take_photo),
                        getResources().getString(R.string.choose_from_gallery),
                        getResources().getString(R.string.cancel),
                };
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(AddProductScreen.this);
                builder.setTitle(getResources().getString(R.string.select_option));
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals(getResources().getString(R.string.take_photo))) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, code_camera);
                            Log.d(TAG, "code_camera: "+code_camera);
                        } else if (options[item].equals(getResources().getString(R.string.choose_from_gallery))) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, code_gallery);
                            Log.d(TAG, "code_gallery: "+code_gallery);
                        } else if (options[item].equals(getResources().getString(R.string.cancel))) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toasty.error(AddProductScreen.this, getResources().getString(R.string.camera_permission_error), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(AddProductScreen.this,  getResources().getString(R.string.camera_permission_error), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: "+requestCode);

        if(resultCode == RESULT_OK && data != null && data.getData() != null){
            switch (requestCode){
                case PICK_IMAGE_CAMERA1:

                    Bitmap bitmap;
                    bitmap = (Bitmap) data.getExtras().get("data");
                    Log.d(TAG, "onActivityResult: "+bitmap);
                    file1=camera_pick(bitmap);
                //    attach_data_name.setText(file1.getName());
                    img_attach_1.setImageBitmap(bitmap);
                    break;
                case PICK_IMAGE_CAMERA2:
                    Bitmap bitmap1;
                    bitmap1 = (Bitmap) data.getExtras().get("data");
                    file2=camera_pick(bitmap1);
                 //   attach_data_name1.setText(file2.getName());
                    img_attach_2.setImageBitmap(bitmap1);
                    break;
                case PICK_IMAGE_CAMERA3:
                    Bitmap bitmap2;
                    bitmap2 = (Bitmap) data.getExtras().get("data");
                    file3=camera_pick(bitmap2);
                 //   attach_data_name2.setText(file3.getName());
                    img_attach_3.setImageBitmap(bitmap2);
                    break;
             /*   case PICK_IMAGE_CAMERA4:
                    Bitmap bitmap3;
                    bitmap3 = (Bitmap) data.getExtras().get("data");
                    file4=camera_pick(bitmap3);
                    attach_data_name3.setText(file4.getName());
                    break;
                case PICK_IMAGE_CAMERA5:
                    Bitmap bitmap4;
                    bitmap4 = (Bitmap) data.getExtras().get("data");
                    file5=camera_pick(bitmap4);
                    attach_data_name4.setText(file5.getName());
                    break;
*/
                case PICK_IMAGE_GALLERY1:
                    Uri uri = data.getData();
                    file1 = gallery_pick(uri);
                   // attach_data_name.setText(file1.getName());
                    img_attach_1.setImageURI(uri);
                    break;
                case PICK_IMAGE_GALLERY2:
                    Uri uri2 = data.getData();
                    file2 = gallery_pick(uri2);
                  //  attach_data_name1.setText(file2.getName());
                    img_attach_2.setImageURI(uri2);
                    break;
                case PICK_IMAGE_GALLERY3:
                    Uri uri3 = data.getData();
                    file3 = gallery_pick(uri3);
                  //  attach_data_name2.setText(file3.getName());
                    img_attach_3.setImageURI(uri3);
                    break;
        /*        case PICK_IMAGE_GALLERY4:
                    Uri uri4 = data.getData();
                    file4 = gallery_pick(uri4);
                    attach_data_name3.setText(file4.getName());
                    break;
                case PICK_IMAGE_GALLERY5:
                    Uri uri5 = data.getData();
                    file5 = gallery_pick(uri5);
                    attach_data_name4.setText(file5.getName());
                    break;*/


            }
        }




    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public File camera_pick(Bitmap bitmap){

        File file = null;


        File f = new File(Environment.getExternalStorageDirectory().toString());
        for (File temp : f.listFiles()) {
            if (temp.getName().equals("temp.jpg")) {
                f = temp;
                break;
            }
        }


        try {

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            assert bitmap != null;
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);


            Log.d(TAG, "bitmap: " + bitmap);

            //imageView2.setImageBitmap(bitmap);

            String path = Environment.getExternalStorageDirectory() + File.separator;

            f.delete();
            OutputStream outFile = null;
            file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
            try {
                file1 = file;
                Log.d(TAG, "camera_pick: "+file.getName());

                outFile = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outFile);
                outFile.flush();
                outFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }

    public File gallery_pick(Uri uri){
        File file = null;
        file = new File(getRealPathFromURI(uri));
        /*String strFileName = attachmentFile.getName();
        attach_data_link.setText(strFileName);

        Log.d(TAG, "image = " + attachmentFile);*/

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            // Log.d(TAG, String.valueOf(bitmap));
            //imageView2.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }


    private void postjob(final String title, final String description, final String price,
                         final String expire_date,final String tax){
        pd.show();
        String url = AppConfig.URL_DEV;
        AsyncHttpClient cl = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("view","addProductApp");
        params.put("title",title);
        params.put("user_id",globalClass.getId());
        params.put("type",status_text);
        params.put("description",description);
        params.put("expire_date",expire_date);
        params.put("custom_16",status_text);
        params.put("price",price);
        params.put("inc_tax_price",tax);
        params.put("listing_id", id);
        Log.d(TAG, "listing ID for Add Product: "+listing_id);
        params.put("friendly_url","https://www.mydevsystems.com/dev/findusonweb/rest/RestController.php");

        try{

            params.put("classified_image1", file1);
            params.put("classified_image2", file2);
            params.put("classified_image3", file3);
            params.put("classified_image4", file1);
            params.put("classified_image5", file1);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }


        Log.d(TAG , "URL "+url);
        Log.d(TAG , "params "+params.toString());


        int DEFAULT_TIMEOUT = 30 * 1000;
        cl.setMaxRetriesAndTimeout(5 , DEFAULT_TIMEOUT);
        cl.post(url,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                if (response != null) {
                    Log.d(TAG, "postjob- " + response.toString());
                    try {

                        //JSONObject result = response.getJSONObject("result");

                        String status = response.optString("status");
                        String message = response.getString("message");
                        Toasty.success(AddProductScreen.this, message, Toast.LENGTH_SHORT, true).show();

                        pd.dismiss();
                        Intent newIntent=new Intent(AddProductScreen.this,ProductScreen.class);
                        startActivity(newIntent);

                        // pd.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                // pd.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("Failed: ", ""+statusCode);
                Log.d("Error : ", "" + throwable);
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkForPermission(final String[] permissions, final int permRequestCode) {

        final List<String> permissionsNeeded = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            final String perm = permissions[i];
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(AddProductScreen.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {

                    Log.d("permisssion","not granted");


                    if (shouldShowRequestPermissionRationale(permissions[i])) {

                        Log.d("if","if");
                        permissionsNeeded.add(perm);

                    } else {
                        // add the request.
                        Log.d("else","else");
                        permissionsNeeded.add(perm);
                    }

                }
            }
        }

        if (permissionsNeeded.size() > 0) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // go ahead and request permissions
                requestPermissions(permissionsNeeded.toArray(new String[permissionsNeeded.size()]), permRequestCode);
            }
            return false;
        } else {
            // no permission need to be asked so all good...we have them all.
            return true;
        }

    }


}