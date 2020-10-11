package platformpbp.uajy.quickresto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.TimeZone;

public class regisReservation extends AppCompatActivity {
    private Button myReserve,chooseDate;
    TextInputEditText dateReserve,number,time;
    TextView title,user;
    ImageView gambar;
    FloatingActionButton balik;
    private String namaresto,alamatRest,url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis_reservation);
        dateReserve=findViewById(R.id.input_date);
        chooseDate=findViewById(R.id.Date);
        number=findViewById(R.id.input_number);
        time=findViewById(R.id.input_time);
        alamatRest=getIntent().getStringExtra("alamat2");
        namaresto=getIntent().getStringExtra("resto2");
        title=findViewById(R.id.title_regis_menu);
        title.setText(namaresto);
        gambar=findViewById(R.id.image_resto_detail);
        user=findViewById(R.id.UserName);
        user.setText(Common.currentUser.getFullName());
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
            }
        });

        myReserve=findViewById(R.id.submit_regis);
        myReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
    }
}