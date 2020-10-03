package platformpbp.uajy.quickresto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Home extends AppCompatActivity {
    private ImageButton reservation;
    private ImageButton myReservationMenu;
    private ImageButton rateMeMenu;
    private Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        reservation = (ImageButton) findViewById(R.id.reservation);
        myReservationMenu = (ImageButton) findViewById(R.id.myReservationMenu);
        rateMeMenu = (ImageButton) findViewById(R.id.rateMeMenu);
        logOut = (Button) findViewById(R.id.logOut);

        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,ReservationMenu.class);
                startActivity(intent);
            }
        });

        myReservationMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,MyReservationMenu.class);
                startActivity(intent);
            }
        });

        rateMeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,RateMeMenu.class);
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