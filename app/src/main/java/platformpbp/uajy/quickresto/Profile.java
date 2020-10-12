package platformpbp.uajy.quickresto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Profile extends AppCompatActivity {

    private FloatingActionButton backmr;
    Button editprofile;
    Button deleteprofile;
    TextView txtFullName;
    TextView txtEmail;
    TextView txtPhone;
    String fullname,email,tlpon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);;

        UserClass userClass=new UserClass();
        SharePreferenceClass sp=new SharePreferenceClass(this);

        backmr = (FloatingActionButton) findViewById(R.id.floating_back_mr);
        editprofile = (Button) findViewById(R.id.editprofile);
        txtFullName=findViewById(R.id.UserName);
        txtEmail=findViewById(R.id.UserEmail);
        txtPhone=findViewById(R.id.UserPhone);

        userClass=sp.getuser();
        fullname=userClass.getFullName();
        email=userClass.getMail();
        tlpon=userClass.getPhone();

        txtFullName.setText(fullname);
        txtEmail.setText(email);
        txtPhone.setText(tlpon);

        backmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this,Home.class);
                startActivity(intent);
            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this,ProfileEdit.class);
                startActivity(intent);
            }
        });
    }
}