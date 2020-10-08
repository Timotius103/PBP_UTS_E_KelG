package platformpbp.uajy.quickresto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class Home extends AppCompatActivity {
    private Button reserve;
    TextView txtFullName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        reserve=findViewById(R.id.rsrvenow);
        txtFullName=findViewById(R.id.UserName);
        txtFullName.setText(Common.currentUser.getFullName());
    }
    public void reservenow(View view){
        startActivity(new Intent(Home.this,ReservationMenu.class));
    }
    public void rate(View view){
        startActivity(new Intent(Home.this,RateMeMenu.class));
    }
    public void logout(View view){
        startActivity(new Intent(Home.this,LoginSignIn.class));
    }
}