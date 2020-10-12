package platformpbp.uajy.quickresto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SignUp extends AppCompatActivity {
    private Button signup,back;
    private TextInputEditText fullname,phone,email,password;
    private FirebaseAuth mAuth;
    String enkrip;
    String AES="AES";
    String enEmail;
    private String CHANNEL_ID = "Channel 1";
    private TextInputLayout mail2;
    private TextInputLayout pass;
    private TextInputLayout username;
    private TextInputLayout tlpon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        UserClass userClass=new UserClass();
        SharePreferenceClass sp=new SharePreferenceClass(this);

        fullname=(TextInputEditText) findViewById(R.id.name);
        phone=(TextInputEditText) findViewById(R.id.phone);
        signup=(Button)findViewById(R.id.Next);
        back=(Button)findViewById(R.id.back2);
        email=(TextInputEditText)findViewById(R.id.mail);
        password=(TextInputEditText)findViewById(R.id.pass);

        mail2=(TextInputLayout)findViewById(R.id.mail_layout);
        pass=(TextInputLayout)findViewById(R.id.pass_layout);
        username=(TextInputLayout)findViewById(R.id.FullName_layout) ;
        tlpon=(TextInputLayout)findViewById(R.id.phone_layout);

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");

        mAuth=FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=email.getText().toString();
                String pw=password.getText().toString();
                String name=fullname.getText().toString();
                String tlp=phone.getText().toString();
                if(mail.isEmpty()&&pw.isEmpty()&&name.isEmpty()&&tlp.isEmpty()) {
                    username.setError("Please fill fullname");
                    tlpon.setError("Please fill Phone Number");
                    mail2.setError("Please fill Email");
                    pass.setError(("Please fill Password"));
                    Toast.makeText(v.getContext(), "Please fill full name, phone, email and password", Toast.LENGTH_SHORT).show();
                }else if(name.isEmpty()) {
                    username.setError("Please fill fullname");
                    Toast.makeText(v.getContext(), "Please fill full name", Toast.LENGTH_SHORT).show();
                }else if(tlp.isEmpty()){
                    tlpon.setError("Please fill Phone Number");
                    Toast.makeText(v.getContext(), "Please fill full phone", Toast.LENGTH_SHORT).show();
                }else if(mail.isEmpty()){
//            Toast.makeText(this,"Email invalid",Toast.LENGTH_SHORT).show();
                    mail2.setError("Please fill correctly");
                    Toast.makeText(v.getContext(),"Please fill Email",Toast.LENGTH_SHORT).show();
                }else if(pw.isEmpty()){
                    pass.setError(("Please fill Password"));
                    Toast.makeText(v.getContext(),"Please Enter Password",Toast.LENGTH_SHORT).show();
                }else  if(!mail.contains("@")){
                    mail2.setError("Please fill Email");
                    Toast.makeText(v.getContext(),"Email invalid",Toast.LENGTH_SHORT).show();
                }else if(pw.length()<7) {
                    pass.setError(("Please fill Password"));
                    Toast.makeText(v.getContext(), "Password too short", Toast.LENGTH_SHORT).show();
                }else {
                    final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                    mDialog.setMessage("Please waiting....");
                    mDialog.show();
                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.child(fullname.getText().toString()).exists()) {
                                mDialog.dismiss();
                                Toast.makeText(SignUp.this, "Name already exist.", Toast.LENGTH_SHORT).show();
                            } else {
                                mDialog.dismiss();
                                try {
                                    enEmail = email.getText().toString();
                                    enEmail = encodeUserEmail(enEmail);
                                    if (dataSnapshot.child(enEmail).exists()) {
                                        mDialog.dismiss();
                                        Toast.makeText(SignUp.this, "Email already exist.", Toast.LENGTH_SHORT).show();
                                        mDialog.cancel();

                                    } else {
                                        createNotificationChannel();
                                        addNotification();
                                        Toast.makeText(SignUp.this, "Sign up successfully.", Toast.LENGTH_SHORT).show();
                                        enkrip = encrypt(password.getText().toString());
                                        mAuth.createUserWithEmailAndPassword(email.getText().toString(), enkrip)
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        UserClass user = new UserClass(fullname.getText().toString(), phone.getText().toString(), email.getText().toString(), enkrip);
                                                        table_user.child(enEmail).setValue(user);
                                                        sendEmailVerification();
                                                        userClass.setFullName(name);
                                                        userClass.setPhone(tlp);
                                                        userClass.setMail(mail);
                                                        userClass.setPass(pw);
                                                        sp.createSession(userClass);
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //      Toast.makeText(SignUp.this, "Failed Register", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        finish();
                                    }


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
//
    private String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    private String encrypt(String password) throws Exception{

        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(password.getBytes());
        String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;
    }

    private SecretKeySpec generateKey(String password) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }
    private void sendEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignUp.this,"Check your Email for verification",Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                    }
                }
            });
        }
    }

////////////////////// INI SIGN UP FIREBASE BIASA ////////////////////////////////////////////////////////////////////////
//    public void signUp(View view){
//        String mail=email.getText().toString();
//        String pw=password.getText().toString();
//
//        ProgressDialog dialog= new ProgressDialog(SignUp.this);
//        dialog.setMessage("Please Whait");
//        dialog.show();
//        if(mail.isEmpty()&&pw.isEmpty()) {
//            mail2.setError("Please fill Email");
//            pass.setError(("Please fill Password"));
//            Toast.makeText(this,"Please fill email and password",Toast.LENGTH_SHORT).show();
//        }else if(mail.isEmpty()){
////            Toast.makeText(this,"Email invalid",Toast.LENGTH_SHORT).show();
//            mail2.setError("Please fill correctly");
//            Toast.makeText(this,"Please fill Email",Toast.LENGTH_SHORT).show();
//        }else if(pw.isEmpty()){
//            pass.setError(("Please fill Password"));
//            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
//        }else  if(!mail.contains("@")){
//            mail2.setError("Please fill Email");
//            Toast.makeText(this,"Email invalid",Toast.LENGTH_SHORT).show();
//        }else if(pw.length()<7) {
//            pass.setError(("Please fill Password"));
//            Toast.makeText(this, "Password too short", Toast.LENGTH_SHORT).show();
//        }else{
//
//            mAuth.createUserWithEmailAndPassword(mail, pw)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//
//                            if (task.isSuccessful()) {
//                                // Sign in success, update UI with the signed-in user's information
//                                Log.d("Create", "createUserWithEmail:success");
//                                FirebaseUser user = mAuth.getCurrentUser();
//                                startActivity(new Intent(SignUp.this,SignIn.class));
//
//                            } else {
//                                // If sign in fails, display a message to the user.
//                                Log.w("Create", "createUserWithEmail:failure", task.getException());
//                                Toast.makeText(SignUp.this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//        }
//
//    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="Channel 1";
            String description = "This is Channel 1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    private void addNotification(){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Hello")
                .setContentText("Terima kasih telah mendaftar. Silahkan verifikasi email anda :)")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notificationIntent= new Intent(this,LoginSignIn.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    public void back2(View view){
        startActivity(new Intent(SignUp.this,LoginSignIn.class));
    }
}