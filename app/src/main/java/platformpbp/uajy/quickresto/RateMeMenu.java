package platformpbp.uajy.quickresto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RateMeMenu extends AppCompatActivity {
    private FloatingActionButton back;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_me_menu);

        back = (FloatingActionButton) findViewById(R.id.floating_back);
        submit = (Button) findViewById(R.id.submit);
        final RatingBar ratingRatingBar = (RatingBar) findViewById(R.id.rating_rating_bar);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RateMeMenu.this,Home.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RateMeMenu.this,ThankYouMenu.class);
                startActivity(intent);
            }
        });
    }
}
