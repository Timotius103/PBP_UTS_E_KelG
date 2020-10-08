package platformpbp.uajy.quickresto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileEdit extends AppCompatActivity {

    private FloatingActionButton backmr;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        backmr = (FloatingActionButton) findViewById(R.id.floating_back_mr);
        done = (Button) findViewById(R.id.done);

        backmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileEdit.this,Profile.class);
                startActivity(intent);
            }
        });
    }
}