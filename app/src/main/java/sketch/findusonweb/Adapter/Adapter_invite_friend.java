package sketch.findusonweb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.HashMap;

import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Screen.SendProposal;



public class Adapter_invite_friend extends BaseAdapter {

    Context mContext;

    GlobalClass globalClass;

    LayoutInflater inflater;

    TextView tv_name,email,phone_number,status,tv_organisation;
    RatingBar rating;
    ImageView img,img_tick;
    ArrayList<HashMap<String,String>> list_names;
    // ArrayList<String> list_names;
    //ImageLoader loader;
    CheckBox cb;
    DisplayImageOptions defaultOptions;




    public Adapter_invite_friend(Context c, ArrayList<HashMap<String,String>> list_names) {
        mContext = c;
        this.list_names = list_names;
        globalClass = ((GlobalClass) mContext.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


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
        View view1 = inflater.inflate(R.layout.single_invite_fiend, parent, false);
        //business_name=view1.findViewById(R.id.tv_busniess_value);
        tv_name=view1.findViewById(R.id.tv_name);

        email=view1.findViewById(R.id.tv_email_value);
        phone_number=view1.findViewById(R.id.tv_phone_value);
        status=view1.findViewById(R.id.tv_status_value);
        cb=view1.findViewById(R.id.cb);
        img_tick=view1.findViewById(R.id.img_tick);
        tv_organisation=view1.findViewById(R.id.tv_organisation);

        String is_checked = list_names.get(position).get("ischecked") ;

        //pay_invoice.setVisibility(View.GONE);
        tv_organisation.setText(list_names.get(position).get("user_organization"));
        tv_name.setText(list_names.get(position).get("user_first_name")+" "+list_names.get(position).get("user_last_name"));
        email.setText(list_names.get(position).get("user_email"));
        phone_number.setText(list_names.get(position).get("user_phone"));
        status.setText(list_names.get(position).get("status"));


        switch (is_checked) {
            case "checkbox":
                    cb.setVisibility(View.VISIBLE);
                    img_tick.setVisibility(View.GONE);
                    status.setText("Pending");
                    status.setBackgroundColor(mContext.getResources().getColor(R.color.dark_grey));

                break;

            case "invited":
                    cb.setVisibility(View.GONE);
                    img_tick.setVisibility(View.VISIBLE);
                    status.setText("Invited");
                    status.setBackgroundColor(mContext.getResources().getColor(R.color.orange));

                break;

            case "join":
                    cb.setVisibility(View.GONE);
                    img_tick.setVisibility(View.VISIBLE);
                    img_tick.setImageResource(R.mipmap.checked_green);
                    status.setText("Joined");
                    status.setBackgroundColor(mContext.getResources().getColor(R.color.light_green));

                break;
        }




      /*  if(list_names.get(position).get("status").equals("1")){



        }else if(list_names.get(position).get("status").equals("0")){


            Log.d("buzz", "getView: "+list_names.get(position).get("status"));

        }else if(list_names.get(position).get("status").equals("2")){


        }
*/

/*
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Job_details.class);
                intent.putExtra("id",list_names.get(position).get("id"));
                Log.d("tag", "onClick: "+list_names.get(position).get("id"));
                mContext.startActivity(intent);
            }
        });
*/
/*
        pay_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SendProposal.class);
                intent.putExtra("id", list_names.get(position).get("id"));
                Log.d("tag", "onClick: " + list_names.get(position).get("id"));
                mContext.startActivity(intent);

            }
        });
*/


        return view1;
    }
}