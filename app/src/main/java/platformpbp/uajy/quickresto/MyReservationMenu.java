package platformpbp.uajy.quickresto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import platformpbp.uajy.quickresto.model.Reservation;


public class MyReservationMenu extends AppCompatActivity {
    private Button backmr;
    TextView nameResto,numb,address,date,time,user;
    String name,no,tgl,wktu,alamat;
//    Reservation reservt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservation);
        String username;
        Reservation reserv=new Reservation();
        SharePreferenceClass sp=new SharePreferenceClass(this); //mySql dinyalain
        nameResto=findViewById(R.id.subtitle_reserve);
        address=findViewById(R.id.address);
        numb =findViewById(R.id.numberPepel);
        date=findViewById(R.id.datereserveproses);
        time=findViewById(R.id.timeproses);

        username = sp.getUsernameS();
        user=findViewById(R.id.UserName);
        user.setText(username);

        //sleect dari mysql

        reserv = sp.getReservation();
        name=reserv.getNameRest();
        alamat=reserv.getAddress();
        tgl=reserv.getDateResrv();
        no=String.valueOf(reserv.getJmlhOrg());
        wktu=reserv.getTimeResrv();


        nameResto.setText(name);
        address.setText(alamat);
        numb.setText(no);
        date.setText(tgl);
        time.setText(wktu);

//        nameResto.setText(reservt.nameRest);
//        address.setText(reservt.address);
//        numb.setText(reservt.jmlhOrg);
//        date.setText(reservt.dateResrv);
//        time.setText(reservt.timeResrv);


        backmr = (Button) findViewById(R.id.back_menu);
        backmr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyReservationMenu.this,Home.class);
                intent.putExtra("resto4",name);
                intent.putExtra("alamat4",alamat);
                intent.putExtra("number",no);
                intent.putExtra("Date1",tgl);
                intent.putExtra("Time1",wktu);
                String masuk="yes";
                intent.putExtra("masuk",masuk);
                startActivity(intent);
            }
        });
    }
}
