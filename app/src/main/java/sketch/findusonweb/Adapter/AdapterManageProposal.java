package sketch.findusonweb.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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


public class AdapterManageProposal extends RecyclerView.Adapter<AdapterManageProposal.MyViewHolder> {


    Context context;
    ArrayList<HashMap<String,String>> list_namesfavoriteAll;

    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;

    LayoutInflater inflater;
    ImageLoader loader;

    public AdapterManageProposal(Context c, ArrayList<HashMap<String,String>> list_namesfavoriteAll) {
        this.context = c;
        this.list_namesfavoriteAll = list_namesfavoriteAll;

        globalClass = ((GlobalClass) c.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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


    }
    @Override
    public AdapterManageProposal.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_manage_proposal, parent, false);

        AdapterManageProposal.MyViewHolder vh = new AdapterManageProposal.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(AdapterManageProposal.MyViewHolder holder, final int position) {




        holder.tv_manage_proposal.setText(list_namesfavoriteAll.get(position).get("title"));
        holder.tv_manage_proposal_brief.setText(list_namesfavoriteAll.get(position).get("description"));
        holder.tv_in_pound.setText(globalClass.pound+list_namesfavoriteAll.get(position).get("budget"));
        holder.tv_complete_order.setText(list_namesfavoriteAll.get(position).get("status"));
        holder.tv_date_manage_proposal.setText(list_namesfavoriteAll.get(position).get("date_requested"));
        holder.tv_date_manage_proposal_days.setText(list_namesfavoriteAll.get(position).get("duration")+" Days");

       /* if(list_namesfavoriteAll.get(position).get("logo_url").equals(""))
        {
            img.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.no_image));
        }
        else {
            loader.displayImage(list_names.get(position).get("logo_url"),img, defaultOptions);
        }*/

        holder.imageView1.setImageDrawable(context.getResources().getDrawable(R.mipmap.no_image));

/*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BrowseProductSingleSelection.class);
                intent.putExtra("id",list_namesfavoriteAll.get(position).get("id"));

                context.startActivity(intent);

            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return list_namesfavoriteAll.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView tv_manage_proposal,tv_manage_proposal_brief,tv_in_pound,tv_complete_order,
        tv_date_manage_proposal,tv_date_manage_proposal_days;
        ImageView imageView1;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            tv_manage_proposal =  itemView.findViewById(R.id.tv_manage_proposal);
            tv_manage_proposal_brief =  itemView.findViewById(R.id.tv_manage_proposal_brief);
            tv_in_pound =  itemView.findViewById(R.id.tv_in_pound);
            tv_complete_order =  itemView.findViewById(R.id.tv_complete_order);
            tv_date_manage_proposal =  itemView.findViewById(R.id.tv_date_manage_proposal);
            tv_date_manage_proposal_days =  itemView.findViewById(R.id.tv_date_manage_proposal_days);
            imageView1 =  itemView.findViewById(R.id.imageView1);

        }
    }
}