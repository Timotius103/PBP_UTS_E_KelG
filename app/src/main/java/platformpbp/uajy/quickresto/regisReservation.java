package platformpbp.uajy.quickresto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.TimeZone;

import platformpbp.uajy.quickresto.model.Reservation;


public class regisReservation extends AppCompatActivity {
    private Button myReserve,chooseDate;
    TextInputEditText dateReserve,number,time;
    TextInputLayout banyakLayout,tanggalLayout,waktupesanLayout;
    TextView title,user;
    ImageView gambar;
    FloatingActionButton balik;
    Double lon,la;
    private String namaresto,alamatRest,url,email,date,waktu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis_reservation);
        String username;
        Reservation reserv=new Reservation();
        SharePreferenceClass sp=new SharePreferenceClass(regisReservation.this);
        dateReserve=findViewById(R.id.input_date);
        chooseDate=findViewById(R.id.Date);
        number=findViewById(R.id.input_number);
        time=findViewById(R.id.input_time);

        banyakLayout=findViewById(R.id.input_number_layout);
        tanggalLayout=findViewById(R.id.input_date_layout);
        waktupesanLayout=findViewById(R.id.input_time_layout);

        alamatRest=getIntent().getStringExtra("alamat2");
        namaresto=getIntent().getStringExtra("resto2");
        title=findViewById(R.id.title_regis_menu);
        title.setText(namaresto);
        gambar=findViewById(R.id.image_resto_detail);

//        username = sp.getUsernameS();
//        user=findViewById(R.id.UserName);
//        user.setText(username);


        url=getIntent().getStringExtra("gambar2");
        Picasso.get().load(url).into(gambar);


        balik=findViewById(R.id.floating_back);
        balik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(regisReservation.this,MapRestaurant.class);

                startActivity(intent);
            }
        });


        MaterialDatePicker.Builder builder= MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a Date");

        MaterialDatePicker materialDatePicker=builder.build();
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");

            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                dateReserve.setText(materialDatePicker.getHeaderText());
                date=dateReserve.getText().toString();
            }
        });


        myReserve=findViewById(R.id.submit_regis);
        myReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String banyak = number.getText().toString();
                String tanggal= dateReserve.getText().toString();
                String wktu=time.getText().toString();

                if(banyak.isEmpty()&&tanggal.isEmpty()&&wktu.isEmpty()) {
                    banyakLayout.setError("Please fill fullname");
                    tanggalLayout.setError("Please fill Phone Number");
                    waktupesanLayout.setError("Please fill Email");
                    Toast.makeText(view.getContext(), "Please fill full name, phone, email and password", Toast.LENGTH_SHORT).show();
                }else if(banyak.isEmpty()) {
                    banyakLayout.setError("Please fill Number of People");
                    Toast.makeText(view.getContext(), "Please fill Number of People", Toast.LENGTH_SHORT).show();
                }else if(tanggal.isEmpty()) {
                    tanggalLayout.setError("Please fill Date Reservation");
                    Toast.makeText(view.getContext(), "Please fill Date Reservation", Toast.LENGTH_SHORT).show();
                }else if(wktu.isEmpty()){
                    waktupesanLayout.setError("Please fill Time Reservation");
                    Toast.makeText(view.getContext(), "Please fill Time Reservation", Toast.LENGTH_SHORT).show();
                }else{
                    reserv.setNameRest(namaresto);
                    reserv.setAddress(alamatRest);
                    reserv.setJmlhOrg(Integer.parseInt(number.getText().toString()));
                    reserv.setDateResrv(String.valueOf(date));
                    waktu=time.getText().toString();
                    reserv.setTimeResrv(String.valueOf(waktu));
                    sp.createReseravation(reserv);
                    Intent intent = new Intent(regisReservation.this,MyReservationMenu.class);
                    intent.putExtra("resto3",String.valueOf(namaresto));
                    intent.putExtra("alamat3",String.valueOf(alamatRest));
                    int angka=Integer.valueOf(number.getText().toString());
                    intent.putExtra("Number",String.valueOf(angka));
                    String tanggalan=dateReserve.getText().toString();
                    intent.putExtra("Date",String.valueOf(tanggalan));
                    String wketu=time.getText().toString();
                    intent.putExtra("Time",wketu);
                    startActivity(intent);
                }



            }
        });
    }




}