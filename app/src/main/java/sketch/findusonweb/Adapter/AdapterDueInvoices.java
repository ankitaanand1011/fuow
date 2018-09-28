package sketch.findusonweb.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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


public class AdapterDueInvoices extends RecyclerView.Adapter<AdapterDueInvoices.MyViewHolder> {


    Context context;
    ArrayList<HashMap<String,String>> invoice;

    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;





    LayoutInflater inflater;

    public AdapterDueInvoices(Context c, ArrayList<HashMap<String,String>> invoice) {
        this.context = c;
        this.invoice = invoice;

        globalClass = ((GlobalClass) c.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        // ImageLoader.getInstance().init(config);
        // loader = ImageLoader.getInstance();


    }
    @Override
    public AdapterDueInvoices.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_due_invoices, parent, false);

        AdapterDueInvoices.MyViewHolder vh = new AdapterDueInvoices.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(AdapterDueInvoices.MyViewHolder holder, final int position) {




        holder.name.setText(invoice.get(position).get("title"));






    }

    @Override
    public int getItemCount() {
        return invoice.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name,date;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name =  itemView.findViewById(R.id.title1);
            date =  itemView.findViewById(R.id.date);

        }
    }
}