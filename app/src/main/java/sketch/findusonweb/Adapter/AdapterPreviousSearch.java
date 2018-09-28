package sketch.findusonweb.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;


public class AdapterPreviousSearch extends RecyclerView.Adapter<AdapterPreviousSearch.MyViewHolder> {


    Context context;
    ArrayList<HashMap<String,String>> search;

    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;





    LayoutInflater inflater;

    public AdapterPreviousSearch(Context c, ArrayList<HashMap<String,String>> search) {
        this.context = c;
        this.search = search;

        globalClass = ((GlobalClass) c.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        // ImageLoader.getInstance().init(config);
        // loader = ImageLoader.getInstance();


    }
    @Override
    public AdapterPreviousSearch.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_previous_search, parent, false);

        AdapterPreviousSearch.MyViewHolder vh = new AdapterPreviousSearch.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(AdapterPreviousSearch.MyViewHolder holder, final int position) {




        holder.name.setText(search.get(position).get("keywords"));

        String date = search.get(position).get("date");
        holder.date.setText(date);    // format output

        Log.d("mega", "onBindViewHolder: "+search.get(position).get("keywords")+"\n date"+date);




    }

    @Override
    public int getItemCount() {
        return search.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name,date;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name =  itemView.findViewById(R.id.title);
            date =  itemView.findViewById(R.id.date);

        }
    }
}