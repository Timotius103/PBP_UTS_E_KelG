package platformpbp.uajy.quickresto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import platformpbp.uajy.quickresto.dabase.DatabaseClient;
import platformpbp.uajy.quickresto.model.User;

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
//        fullname=userClass.getFullName();
        email=userClass.getMail();
//        tlpon=userClass.getPhone();

        findEmail(email);

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

    private void findEmail(final String cari){
        class GetUsers extends AsyncTask<Void, Void, List<User>>{

            @Override
            protected List<User> doInBackground(Void... voids) {
                List<User> userList = DatabaseClient
                        .getInstance2(getApplicationContext())
                        .getDatabase()
                        .userDAO()
                        .getCari(cari);
                return userList;
            }

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);
                txtFullName.setText(users.get(0).getFullName());
                txtEmail.setText(users.get(0).getMail());
                txtPhone.setText(users.get(0).getPhone());
                if (users.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Empty List", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetUsers get = new GetUsers();
        get.execute();
    }
}