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

public class SignIn extends AppCompatActivity {
    private Button signin;
    private Button back;
    private TextInputEditText email;
    private TextInputEditText password;
    private FirebaseAuth mAuth;
    private TextInputLayout mail2;
    private TextInputLayout pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signin=(Button)findViewById(R.id.login);
        back=(Button)findViewById(R.id.back);
        email=(TextInputEditText)findViewById(R.id.email);
        password=(TextInputEditText)findViewById(R.id.password);
        mail2=(TextInputLayout)findViewById(R.id.mail_login);
        pass=(TextInputLayout)findViewById(R.id.pass_login);
        mAuth=FirebaseAuth.getInstance();


    }
    public void login(View view){
        String mail=email.getText().toString();
        String pw=password.getText().toString();
        ProgressDialog dialog= new ProgressDialog(SignIn.this);
        dialog.setMessage("Please Whait");
        dialog.show();
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

            mAuth.signInWithEmailAndPassword(mail, pw)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("signIn", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(SignIn.this,Home.class));
//                                createNotificationChannel();
//                                addNotification();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("signIn", "signInWithEmail:failure", task.getException());
                                Toast.makeText(SignIn.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
//    private void CheckEmail() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference table_user = database.getReference("User");
//        uid = mAuth.getUid();
//        final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
//        mDialog.setMessage("Please Waiting...");
//        mDialog.show();
//
//        table_user.addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                String enEmail = inputEmail.getText().toString();
//                enEmail = encodeUserEmail(enEmail);
//
//                //check if user not exist in database
//                if (dataSnapshot.child(enEmail).exists()) {
//                    //get user information
//                    mDialog.dismiss();
//
//                    User user = dataSnapshot.child(enEmail).getValue(User.class);
//                    user.setEmail(inputEmail.getText().toString());
//                    try {
////                                if(user2.isEmailVerified()) {
//                        decrip = decrypt(user.getPassword(), edtPassword.getText().toString());
//                        if (decrip.equals(edtPassword.getText().toString())) {
//                            FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser();
//                            Boolean emailflag = user2.isEmailVerified();
//                            dataSnapshot.child(uid).getValue(String.class);
//                            if (emailflag) {
//                                Intent homeIntent = new Intent(SignIn.this, Home.class);
//                                Common.currentUser = user;
//                                startActivity(homeIntent);
//                                finish();
//                                table_user.removeEventListener(this);
//                                //Toast.makeText(SignIn.this, "Berhasil", Toast.LENGTH_LONG).show();
//                                mAuth.signOut();
//                            } else {
//                                Toast.makeText(SignIn.this, "Please Verify Your Email!", Toast.LENGTH_LONG).show();
//                                mAuth.signOut();
//                            }
//
//                        } else {
//                            Toast.makeText(SignIn.this, "Please Verify Your Account!", Toast.LENGTH_SHORT).show();
//                            mAuth.signOut();
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//
//                } else {
//                    Toast.makeText(SignIn.this, "User not exist in Database !", Toast.LENGTH_SHORT).show();
//                }
//                mDialog.dismiss();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void back(View view){
        startActivity(new Intent(SignIn.this,LoginSignIn.class));
    }
}