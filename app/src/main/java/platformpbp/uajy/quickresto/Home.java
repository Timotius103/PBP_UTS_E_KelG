package platformpbp.uajy.quickresto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Home extends AppCompatActivity {
    private ImageButton reservation;
    private ImageButton myReservation;
    private ImageButton rateMe;
    private Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        reservation = (ImageButton) findViewById(R.id.reservation);
        myReservation = (ImageButton) findViewById(R.id.myReservation);
        rateMe = (ImageButton) findViewById(R.id.rateMe);
        logOut = (Button) findViewById(R.id.logOut);

        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,ReservationMenu.class);
                startActivity(intent);
            }
        });

        myReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,MyReservation.class);
                startActivity(intent);
            }
        });

        rateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,RateMe.class);
                startActivity(intent);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,LoginSignIn.class);
                startActivity(intent);
            }
        });
    }
}