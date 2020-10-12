package platformpbp.uajy.quickresto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class LoginSignIn extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_in);


    }

    public void signin(View view){
        startActivity(new Intent(LoginSignIn.this,SignIn.class));
    }

    public void signup(View view){
        startActivity(new Intent(LoginSignIn.this,SignUp.class));
    }

}