package sketch.findusonweb.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sketch.findusonweb.R;


public class DashboardScreenWithoutLogin extends AppCompatActivity{

    RelativeLayout rl_claim_business,rl_recommend_business,rl_login,rl_forgot_password,rl_register;
    TextView back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_without_login);

        initialisation();
        function();

    }

    public void initialisation(){

        rl_claim_business=findViewById(R.id.rl_claim_business);
        rl_recommend_business=findViewById(R.id.rl_recommedbusiness);
        rl_login=findViewById(R.id.rl_login);
        rl_forgot_password=findViewById(R.id.rl_forgot_password);
        rl_register=findViewById(R.id.rl_register);
        back=findViewById(R.id.back_img);

    }

    public void function(){

        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenWithoutLogin.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });

        rl_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenWithoutLogin.this, Forget_Password.class);
                startActivity(intent);
                finish();
            }
        });

        rl_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenWithoutLogin.this, Register.class);
                startActivity(intent);
                finish();
            }
        });


        rl_claim_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenWithoutLogin.this, ClaimBusiness.class);
                startActivity(intent);
            }
        });

        rl_recommend_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenWithoutLogin.this, RecommedBuisness.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
