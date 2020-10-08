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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);;

        backmr = (FloatingActionButton) findViewById(R.id.floating_back_mr);
        editprofile = (Button) findViewById(R.id.editprofile);
        txtFullName=findViewById(R.id.UserName);
        txtEmail=findViewById(R.id.UserEmail);
        txtPhone=findViewById(R.id.UserPhone);

        txtFullName.setText(Common.currentUser.getFullName());
        txtEmail.setText(Common.currentUser.getMail());
        txtPhone.setText(Common.currentUser.getPhone());

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