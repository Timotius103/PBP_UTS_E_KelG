package platformpbp.uajy.quickresto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyReservationMenu extends AppCompatActivity {
    private Button backmr;
    TextView nameResto,numb,address,date,time,user;
    String name,no,tgl,wktu,alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservation);
        nameResto=findViewById(R.id.subtitle_reserve);
        address=findViewById(R.id.address);
        numb =findViewById(R.id.numberPepel);
        date=findViewById(R.id.datereserveproses);
        time=findViewById(R.id.timeproses);
        user=findViewById(R.id.UserName);
        user.setText(Common.currentUser.getFullName());

        name=getIntent().getStringExtra("resto3");
        alamat=getIntent().getStringExtra("alamat3");
        no=getIntent().getStringExtra("Number");
        tgl=getIntent().getStringExtra("Date");
        wktu=getIntent().getStringExtra("Time");

        nameResto.setText(name);
        address.setText(alamat);
        numb.setText(no);
        date.setText(tgl);
        time.setText(wktu);


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
                startActivity(intent);
            }
        });
    }
}
