package sketch.findusonweb.Screen;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Utils.Shared_Preference;

/**
 * Created by Developer on 9/6/18.
 */

public class ViewProductsDetails extends AppCompatActivity {

    TextView tv_des,tv_cat_val,tv_name,tv_price,tv_status,tv_commission_value_order;
    GlobalClass globalClass;
    Shared_Preference prefrence;
    ProgressDialog pd;
    String TAG = "ViewProductsDetails";
    ImageView back;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_product_details);

        initialisation();
        function();

    }

    private void initialisation() {

        globalClass = (GlobalClass) getApplicationContext();
        prefrence = new Shared_Preference(ViewProductsDetails.this);
        pd = new ProgressDialog(ViewProductsDetails.this);


        back=findViewById(R.id.back_img);

        tv_des = findViewById(R.id.tv_des);
        tv_cat_val = findViewById(R.id.tv_cat_val);
        tv_name = findViewById(R.id.tv_name);
        tv_price = findViewById(R.id.tv_price);
        tv_status = findViewById(R.id.tv_status);
        tv_commission_value_order = findViewById(R.id.tv_commission_value_order);
    }

    private void function() {

        tv_des.setText(getIntent().getStringExtra("description"));
        tv_cat_val.setText(getIntent().getStringExtra("listing_name"));
        tv_name.setText(getIntent().getStringExtra("title"));
        tv_price.setText(globalClass.pound+getIntent().getStringExtra("price"));
        tv_commission_value_order.setText(getIntent().getStringExtra("custom_58"));
        tv_status.setText(getIntent().getStringExtra("custom_15"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
