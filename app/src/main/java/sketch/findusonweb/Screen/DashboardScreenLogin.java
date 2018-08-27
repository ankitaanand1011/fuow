package sketch.findusonweb.Screen;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sketch.findusonweb.R;



public class DashboardScreenLogin extends AppCompatActivity {

    RelativeLayout rl_products,rl_invoice,rl_claim_business,
            rl_dashboard,rl_submitted_review,rl_invite,rl_order,rl_favorites,
            rl_financial,rl_manage_request,rl_manage_proposal;
    TextView back;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_screen);

        initialisation();
        function();

    }

    public void initialisation(){
        rl_submitted_review=findViewById(R.id.rl_submitted_review);

        rl_products =findViewById(R.id.rl_products);
        rl_dashboard=findViewById(R.id.dashboard);
        rl_invoice=findViewById(R.id.rl_invoice);
        rl_invite=findViewById(R.id.rl_invite_friends);
        rl_order=findViewById(R.id.rl_my_orders);
        rl_favorites=findViewById(R.id.rl_favorites);
        rl_financial=findViewById(R.id.rl_financial);
        rl_manage_request=findViewById(R.id.rl_manage_request);
        rl_manage_proposal=findViewById(R.id.rl_manage_proposal);

        back=findViewById(R.id.back_img);
         /* rl_claim_business=findViewById(R.id.rl_claim_business);*/
    }

    public void function(){


        rl_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, ProductScreen.class);
                startActivity(intent);
            }
        });
        rl_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, DashboardNew.class);
                startActivity(intent);
            }
        });
        rl_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, Invoice.class);
                startActivity(intent);
            }
        });

        rl_manage_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, ManageRequest.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        rl_submitted_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, Listings.class);
                startActivity(intent);
            }
        });
        rl_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, Invite_friend_from_dashboard.class);
                startActivity(intent);
            }
        });
        rl_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, MyOrderLIst.class);
                startActivity(intent);
            }
        });
        rl_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, Favorites.class);
                startActivity(intent);
            }
        });
        rl_financial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, Financial_Transaction.class);
                startActivity(intent);
            }
        });


        rl_manage_proposal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, Manage_Proposal.class);
                startActivity(intent);
            }
        });

         /* rl_claim_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreenLogin.this, ClaimBusiness.class);
                startActivity(intent);
            }
        });*/

    }
}
