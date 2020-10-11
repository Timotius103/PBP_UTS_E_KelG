package platformpbp.uajy.quickresto;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // langsung pindah ke MainActivity atau activity lain
        // begitu memasuki splash screen ini

            Intent intent = new Intent(this, LoginSignIn.class);
            startActivity(intent);
            finish();


    }
}