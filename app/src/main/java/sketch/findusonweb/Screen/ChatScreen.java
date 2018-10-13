package sketch.findusonweb.Screen;


import android.app.ProgressDialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;


import android.widget.ImageView;
import android.widget.ListView;

import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

import sketch.findusonweb.Adapter.ChatAdapter;
import sketch.findusonweb.Constants.AppConfig;
import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.Pojo.RowItem;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

public class ChatScreen extends AppCompatActivity {


	ListView mylistview;
	ImageView img_back;
	ChatAdapter adapter;
	String TAG = "chat";
	GlobalClass globalClass;
	Shared_Preference prefrence;

	ProgressDialog pd;
	ArrayList<HashMap<String,String>> list_namesfavoriteAll;
	String message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_screen);


		initialisation();
		function();
		ReviewList();


	}

	private void initialisation() {

		globalClass = (GlobalClass) getApplicationContext();
		prefrence = new Shared_Preference(ChatScreen.this);
		pd = new ProgressDialog(ChatScreen.this);


		img_back = findViewById(R.id.img_back);
		mylistview =  findViewById(R.id.list);
	}

	private void function() {
		prefrence.loadPrefrence();
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage(getResources().getString(R.string.loading));

		list_namesfavoriteAll = new ArrayList<>();

		img_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	private void ReviewList() {
		// Tag used to cancel the request
		String tag_string_req = "req_login";

		pd.show();

		StringRequest strReq = new StringRequest(Request.Method.POST,
				AppConfig.URL_DEV, new Response.Listener<String>() {

			@Override
			public void onResponse(String response)
			{
				Log.d(TAG, "JOB RESPONSE: " + response.toString());

				pd.dismiss();

				Gson gson = new Gson();

				try {


					JsonObject jobj = gson.fromJson(response, JsonObject.class);
					Log.d(TAG, "onResponse: " + jobj);

					String result = jobj.get("success").toString().replaceAll("\"", "");


					if (result.equals("1")) {
						JsonArray data = jobj.getAsJsonArray("data");
						Log.d(TAG, "Data: " + data);

						for (int i = 0; i < data.size(); i++) {

							JsonObject images1 = data.get(i).getAsJsonObject();
							String conv_id = images1.get("conv_id").toString().replaceAll("\"", "");
							String remote_profile = images1.get("remote_profile").toString().replaceAll("\"", "");
							String remote_user_id = images1.get("remote_user_id").toString().replaceAll("\"", "");
							String remote_user_name = images1.get("remote_user_name").toString().replaceAll("\"", "");

							JsonArray messages = images1.getAsJsonArray("messages");
							Log.d(TAG, "messages: " +messages);

							for (int j = 0; j < messages.size(); j++) {
								JsonObject jsonObject = messages.get(j).getAsJsonObject();

								String sender = jsonObject.get("sender").toString().replaceAll("\"", "");
								String receiver = jsonObject.get("receiver").toString().replaceAll("\"", "");
								message = jsonObject.get("message").toString().replaceAll("\"","");
								String date = jsonObject.get("date").toString().replaceAll("\"", "");
								String sender_name = jsonObject.get("sender_name").toString().replaceAll("\"", "");
								String receiver_name = jsonObject.get("receiver_name").toString().replaceAll("\"", "");



							}

							HashMap<String, String> hashMap = new HashMap<>();
							hashMap.put("conv_id", conv_id);
							hashMap.put("remote_user_id", remote_user_id);
							hashMap.put("remote_user_name", remote_user_name);
							hashMap.put("message", message);
							hashMap.put("remote_profile", remote_profile);


							list_namesfavoriteAll.add(hashMap);
							Log.d(TAG, "Listmane: " + list_namesfavoriteAll);

						}
						//total.setText(total_credits);
						//Used.setText(used_credits);
						Log.d(TAG, "Listmane outer: " + list_namesfavoriteAll);

						adapter = new ChatAdapter(ChatScreen.this,list_namesfavoriteAll);
						mylistview.setAdapter(adapter);

                      /*  adapterCreditHistory = new AdapterCreditHistory(CreditHistory.this, list_namesfavoriteAll);
                        listing.setAdapter(adapterCreditHistory);*/
					}
					else


					{


						Toasty.success(ChatScreen.this, result, Toast.LENGTH_SHORT, true).show();
					}
					// favorite();

				} catch (Exception e) {

					Toasty.warning(ChatScreen.this, "NO DATA FOUND", Toast.LENGTH_SHORT, true).show();
					e.printStackTrace();

				}


			}
		}, new Response.ErrorListener() {

			@Override

			public void onErrorResponse(VolleyError error) {
				Log.e(TAG, "Login Error: " + error.getMessage());
				Toast.makeText(getApplicationContext(),"Registration Error", Toast.LENGTH_LONG).show();
				pd.dismiss();
			}
		}) {

			@Override
			protected Map<String, String> getParams() {
				// Posting parameters to login url
				Map<String, String> params = new HashMap<>();
				params.put("user_id", globalClass.getId());
				params.put("view", "myInbox");

				Log.d(TAG, "getParams: "+params);
				return params;
			}

		};

		// Adding request to request queue
		GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
		strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

	}


/*
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		String member_name = rowItems.get(position).getMember_name();
		Toast.makeText(getApplicationContext(), "" + member_name,
				Toast.LENGTH_SHORT).show();
	}*/

}
