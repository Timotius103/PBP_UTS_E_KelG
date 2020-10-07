package platformpbp.uajy.quickresto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ThankYouMenu extends AppCompatActivity {
    private Button backtm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you_menu);

        backtm = (Button) findViewById(R.id.backtm);

        backtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThankYouMenu.this,Home.class);
                startActivity(intent);
            }
        });
    }
}
