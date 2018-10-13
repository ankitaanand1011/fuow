package sketch.findusonweb.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ivbaranov.mli.MaterialLetterIcon;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import sketch.findusonweb.Pojo.RowItem;
import sketch.findusonweb.R;
import sketch.findusonweb.Screen.ChatDetail;

public class ChatAdapter extends BaseAdapter {
	DisplayImageOptions defaultOptions;
	Context context;
	ImageLoader loader;
	ArrayList<HashMap<String,String>> list_namesfavoriteAll;

	public ChatAdapter(Context context, ArrayList<HashMap<String,String>> list_namesfavoriteAll) {
		this.context = context;
		this.list_namesfavoriteAll = list_namesfavoriteAll;
	}

	@Override
	public int getCount() {
		return list_namesfavoriteAll.size();
	}

	@Override
	public Object getItem(int position) {
		return list_namesfavoriteAll.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView profile_pic,img_active;
		TextView member_name;
		TextView message;
		TextView cart_badge;

		MaterialLetterIcon icon;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
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

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.chat_list_item, null);
			holder = new ViewHolder();
			String name =  list_namesfavoriteAll.get(position).get("remote_user_name");
			holder.member_name = convertView.findViewById(R.id.member_name);
			holder.profile_pic = convertView.findViewById(R.id.image);
			holder.img_active = convertView.findViewById(R.id.img_active);
			holder.message =  convertView.findViewById(R.id.message);
			holder.cart_badge =  convertView.findViewById(R.id.cart_badge);
			holder.icon =  convertView.findViewById(R.id.icon);


			int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
			int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

			holder.profile_pic.setImageResource(R.mipmap.default_user_img);
			holder.member_name.setText(list_namesfavoriteAll.get(position).get("remote_user_name"));
			holder.message.setText(list_namesfavoriteAll.get(position).get("message").replaceAll("\"", ""));
			if (list_namesfavoriteAll.get(position).get("remote_profile").equals("")){

				holder.profile_pic.setVisibility(View.GONE);
				holder.icon.setVisibility(View.VISIBLE);
				holder.icon.setLetter(name);
				holder.icon.setLetterColor(context.getResources().getColor(R.color.white));
				holder.icon.setShapeColor(randomAndroidColor);
				holder.icon.setShapeType(MaterialLetterIcon.Shape.ROUND_RECT);
				holder.icon.setLetterSize(26);
				holder.icon.setLetterTypeface(Typeface.SANS_SERIF);
				holder.icon.setInitials(true);
				holder.icon.setInitialsNumber(2);

			}else {
				holder.profile_pic.setVisibility(View.VISIBLE);
				holder.icon.setVisibility(View.GONE);
				loader.displayImage(list_namesfavoriteAll.get(position).get("remote_profile"), holder.profile_pic, defaultOptions);
			}
			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(context,ChatDetail.class);
					intent.putExtra("remote_user_id",list_namesfavoriteAll.get(position).get("remote_user_id"));
					intent.putExtra("remote_user_name",list_namesfavoriteAll.get(position).get("remote_user_name"));
					intent.putExtra("conv_id",list_namesfavoriteAll.get(position).get("conv_id"));
					intent.putExtra("remote_profile",list_namesfavoriteAll.get(position).get("remote_profile"));
					context.startActivity(intent);
				}
			});

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}

}
