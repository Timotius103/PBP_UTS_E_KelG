package platformpbp.uajy.quickresto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyReservationMenu extends AppCompatActivity {
    private FloatingActionButton backmr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservation_menu2);

        backmr = (FloatingActionButton) findViewById(R.id.floating_back_mr);
        backmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyReservationMenu.this,Home.class);
                startActivity(intent);
            }
        });
    }
}
