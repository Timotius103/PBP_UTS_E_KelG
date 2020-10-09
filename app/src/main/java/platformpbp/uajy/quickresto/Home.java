package platformpbp.uajy.quickresto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class Home extends AppCompatActivity {
    ImageButton reserve;
    ImageButton myreserve;
    ImageButton rate;
    ImageButton profil;
    Button logout;
    TextView txtFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        reserve = (ImageButton) findViewById(R.id.rsrvenow);
        myreserve = (ImageButton) findViewById(R.id.myreserve);
        rate = (ImageButton) findViewById(R.id.rate);
        profil = (ImageButton) findViewById(R.id.profil);
        logout = (Button) findViewById(R.id.out);

        txtFullName=findViewById(R.id.UserName);
        txtFullName.setText(Common.currentUser.getFullName());

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,ReservationMenu.class);
                startActivity(intent);
            }
        });

//        myreserve.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Home.this,MyReservationMenu.class);
//                startActivity(intent);
//            }
//        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,RateMeMenu.class);
                startActivity(intent);
            }
        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,Profile.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Home.this);
                builder.setMessage("Are You Sure?");
                builder.setTitle("Alert!");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Home.this,LoginSignIn.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

            }
        });
    }
}