package sketch.findusonweb.Screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import sketch.findusonweb.R;



public class Forget_Password extends AppCompatActivity{

    EditText email;
    TextView submit_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        initialisation();
        function();

    }


    private void initialisation() {

        email = findViewById(R.id.email);
        submit_tv = findViewById(R.id.submit_tv);

    }
    private void function() {


    }




}
