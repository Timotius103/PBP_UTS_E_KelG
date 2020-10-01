package platformpbp.uajy.quickresto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    private Button signup,back;
    private TextInputEditText email;
    private TextInputEditText password;
    private FirebaseAuth mAuth;
    private TextInputLayout mail2;
    private TextInputLayout pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signup=(Button)findViewById(R.id.Next);
        back=(Button)findViewById(R.id.back2);
        email=(TextInputEditText)findViewById(R.id.mail);
        password=(TextInputEditText)findViewById(R.id.pass);
        mail2=(TextInputLayout)findViewById(R.id.mail_layout);
        pass=(TextInputLayout)findViewById(R.id.pass_layout);
        mAuth=FirebaseAuth.getInstance();
    }
    public void signUp(View view){
        String mail=email.getText().toString();
        String pw=password.getText().toString();

        if(mail.isEmpty()&&pw.isEmpty()) {
            mail2.setError("Please fill Email");
            pass.setError(("Please fill Password"));
            Toast.makeText(this,"Please fill email and password",Toast.LENGTH_SHORT).show();
        }else if(mail.isEmpty()){
//            Toast.makeText(this,"Email invalid",Toast.LENGTH_SHORT).show();
            mail2.setError("Please fill correctly");
            Toast.makeText(this,"Please fill Email",Toast.LENGTH_SHORT).show();
        }else if(pw.isEmpty()){
            pass.setError(("Please fill Password"));
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
        }else  if(!mail.contains("@")){
            mail2.setError("Please fill Email");
            Toast.makeText(this,"Email invalid",Toast.LENGTH_SHORT).show();
        }else if(pw.length()<7) {
            pass.setError(("Please fill Password"));
            Toast.makeText(this, "Password too short", Toast.LENGTH_SHORT).show();
        }else{
            ProgressDialog dialog= new ProgressDialog(SignUp.this);
            dialog.setMessage("Your message");
            dialog.show();
            mAuth.createUserWithEmailAndPassword(mail, pw)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Create", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(SignUp.this,SignIn.class));

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Create", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUp.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    public void back2(View view){
        startActivity(new Intent(SignUp.this,LoginSignIn.class));
    }
}