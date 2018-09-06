package sketch.findusonweb.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sketch.findusonweb.Pojo.RowItem;
import sketch.findusonweb.R;

public class ChatAdapter extends BaseAdapter {

	Context context;
	List<RowItem> rowItems;

	public ChatAdapter(Context context, List<RowItem> rowItems) {
		this.context = context;
		this.rowItems = rowItems;
	}

	@Override
	public int getCount() {
		return rowItems.size();
	}

	@Override
	public Object getItem(int position) {
		return rowItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return rowItems.indexOf(getItem(position));
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView profile_pic,img_active;
		TextView member_name;
		TextView message;
		TextView cart_badge;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

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

			RowItem row_pos = rowItems.get(position);

			holder.profile_pic.setImageResource(row_pos.getProfile_pic_id());
			holder.member_name.setText(row_pos.getMember_name());


			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}

}
