package sketch.findusonweb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ivbaranov.mli.MaterialLetterIcon;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Screen.BrowseProductSingleSelection;
import sketch.findusonweb.Screen.EditReview;

/**
 * Created by developer on 1/8/18.
 */

public class AdapterReview  extends RecyclerView.Adapter<AdapterReview.MyViewHolder> {


    Context context;
    ArrayList<HashMap<String,String>> Arraylist_review;

    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;
    LayerDrawable stars;
    LayoutInflater inflater;

    public AdapterReview(Context c,ArrayList<HashMap<String,String>> Arraylist_review
    ) {
        this.context = c;
        this.Arraylist_review = Arraylist_review;

        globalClass = ((GlobalClass) c.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        // ImageLoader.getInstance().init(config);
        // loader = ImageLoader.getInstance();


    }
    @Override
    public AdapterReview.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_single_row, parent, false);

        AdapterReview.MyViewHolder vh = new AdapterReview.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(AdapterReview.MyViewHolder holder, final int position) {





        final String id =  Arraylist_review.get(position).get("id");
        String name =  Arraylist_review.get(position).get("title");
        String review =  Arraylist_review.get(position).get("review");
        String status =  Arraylist_review.get(position).get("status");
        String ratingBar =  Arraylist_review.get(position).get("rating");

        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];




        stars = (LayerDrawable) holder.rating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(context.getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);


        holder.name.setText(name);
        holder.rating.setRating(Float.parseFloat(ratingBar));
        holder.tv_des.setText(review);


        holder.img.setVisibility(View.GONE);
        holder.icon.setVisibility(View.VISIBLE);
        holder.icon.setLetter(Arraylist_review.get(position).get("title"));
        holder.icon.setLetterColor(context.getResources().getColor(R.color.black));
        holder.icon.setShapeColor(context.getResources().getColor(R.color.white));
        holder.icon.setShapeType(MaterialLetterIcon.Shape.CIRCLE);
        holder.icon.setLetterSize(26);
        holder.icon.setLetterTypeface(Typeface.SANS_SERIF);
        holder.icon.setInitials(true);
        holder.icon.setInitialsNumber(2);
        holder.rl_icon.setBackgroundColor(randomAndroidColor);


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditReview.class);
                intent.putExtra("id",Arraylist_review.get(position).get("id"));
                Log.d("tag", "onClick: " + id);
                context.startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return Arraylist_review.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's


        TextView name,tv_des,edit;
        RatingBar rating;
        ImageView img;
        MaterialLetterIcon icon;
        RelativeLayout rl_icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = itemView.findViewById(R.id.title_review_new);
            tv_des = itemView.findViewById(R.id.tv_des);
            rating = itemView.findViewById(R.id.ratingBar);
            edit = itemView.findViewById(R.id.edit_review);
            icon =  itemView.findViewById(R.id.icon);
            img =  itemView.findViewById(R.id.img);
            rl_icon =  itemView.findViewById(R.id.rl_icon);

        }
    }
}