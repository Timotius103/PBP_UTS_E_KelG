package platformpbp.uajy.quickresto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class Home extends AppCompatActivity {
    ImageButton reserve;
    ImageButton myreserve;
    ImageButton rate;
    ImageButton profil;
    Button logout;
    TextView txtFullName;
    private String CHANNEL_ID = "Channel 3";

    String name,alamt,no,tglan,thun,cek=" ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String username;
        SharePreferenceClass sp=new SharePreferenceClass(this);

        reserve = (ImageButton) findViewById(R.id.rsrvenow);
        myreserve = (ImageButton) findViewById(R.id.myreserve);
        rate = (ImageButton) findViewById(R.id.rate);
        profil = (ImageButton) findViewById(R.id.profil);
        logout = (Button) findViewById(R.id.out);

        username = sp.getUsernameS();
        txtFullName=findViewById(R.id.UserName);
        txtFullName.setText(username);

        name=getIntent().getStringExtra("resto4");
        alamt=getIntent().getStringExtra("alamat4");
        no=getIntent().getStringExtra("number");
        tglan=getIntent().getStringExtra("Date1");
        thun=getIntent().getStringExtra("Time1");

        cek=getIntent().getStringExtra("masuk");
        System.out.println("TESSSSS ASUUU"+cek);
        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,ReservationMenu.class);
                startActivity(intent);
            }
        });

        myreserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,MyReservationMenu.class);
                intent.putExtra("resto3",name);
                intent.putExtra("alamat3",alamt);
                intent.putExtra("Number",no);
                intent.putExtra("Date",tglan);
                intent.putExtra("Time",thun);
                startActivity(intent);
//                if (cek.equals("")){
//
//                }else{
//                    Toast.makeText(Home.this, "You touch me?" , Toast.LENGTH_LONG).show();
//
//                }
            }
        });

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
                        createNotificationChannel();
                        addNotification();
                        sp.closeSession();
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
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="Channel 3";
            String description = "This is Channel 3";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    private void addNotification(){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Selamat Datang Kembali!")
                .setContentText("Semoga Harimu Menyenangkan :)")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notificationIntent= new Intent(this,LoginSignIn.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

}