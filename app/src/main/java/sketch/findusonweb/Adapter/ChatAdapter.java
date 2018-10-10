package sketch.findusonweb.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sketch.findusonweb.Pojo.RowItem;
import sketch.findusonweb.R;
import sketch.findusonweb.Screen.ChatDetail;

public class ChatAdapter extends BaseAdapter {

	Context context;
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
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.chat_list_item, null);
			holder = new ViewHolder();

			holder.member_name = convertView.findViewById(R.id.member_name);
			holder.profile_pic = convertView.findViewById(R.id.imageView2);
			holder.img_active = convertView.findViewById(R.id.img_active);
			holder.message =  convertView.findViewById(R.id.message);
			holder.cart_badge =  convertView.findViewById(R.id.cart_badge);




			holder.profile_pic.setImageResource(R.mipmap.default_user_img);
			holder.member_name.setText(list_namesfavoriteAll.get(position).get("remote_user_name"));
			holder.message.setText(list_namesfavoriteAll.get(position).get("message").replaceAll("\"", ""));

			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(context,ChatDetail.class);
					intent.putExtra("remote_user_id",list_namesfavoriteAll.get(position).get("remote_user_id"));
					intent.putExtra("remote_user_name",list_namesfavoriteAll.get(position).get("remote_user_name"));
					intent.putExtra("conv_id",list_namesfavoriteAll.get(position).get("conv_id"));
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
