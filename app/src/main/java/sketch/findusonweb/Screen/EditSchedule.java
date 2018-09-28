package sketch.findusonweb.Screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import sketch.findusonweb.R;

/**
 * Created by developer on 28/9/18.
 */

public class EditSchedule extends AppCompatActivity {
    String id,from_user,to_user,subject,location,start_time,end_time,attendies,overview;
    EditText et_from_user,et_to_user,et_location,et_start_time,et_end_time,et_attendees,et_overview,et_subject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_schedule);
        et_from_user=findViewById(R.id.input_from);
        et_to_user=findViewById(R.id.input_to);
        et_location=findViewById(R.id.input_location);
        et_subject=findViewById(R.id.input_subject);
        et_start_time=findViewById(R.id.input_start_date);
        et_end_time=findViewById(R.id.input_end_date);
        et_attendees=findViewById(R.id.input_attendees);
        et_overview=findViewById(R.id.edt_overview);

        id = getIntent().getExtras().getString("id");
        from_user = getIntent().getExtras().getString("from_user");
        to_user = getIntent().getExtras().getString("to_user");
        subject = getIntent().getExtras().getString("subject");
        location = getIntent().getExtras().getString("location");
        start_time = getIntent().getExtras().getString("start_time");
        end_time = getIntent().getExtras().getString("end_time");
        attendies = getIntent().getExtras().getString("attendies");
        overview = getIntent().getExtras().getString("overview");
        et_from_user.setText(from_user);
        et_to_user.setText(to_user);
        et_location.setText(location);
        et_subject.setText(subject);
        et_start_time.setText(start_time);
        et_end_time.setText(end_time);
        et_attendees.setText(attendies);
        et_overview.setText(overview);
    }
}