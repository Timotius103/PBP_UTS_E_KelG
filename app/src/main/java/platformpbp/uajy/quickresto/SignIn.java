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

import platformpbp.uajy.quickresto.model.Reservation;

public class SignIn extends AppCompatActivity {
    private Button signin;
    private Button back;
    private TextInputEditText inputEmail;
    private TextInputEditText password;
    private FirebaseAuth mAuth;
    String decrip, uid, enkrip;
    String AES = "AES";
    private TextInputLayout mail2;
    private TextInputLayout pass;

    private String CHANNEL_ID = "Channel 2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signin=(Button)findViewById(R.id.login);
        back=(Button)findViewById(R.id.back);
        inputEmail=(TextInputEditText)findViewById(R.id.email);
        password=(TextInputEditText)findViewById(R.id.password);
        mail2=(TextInputLayout)findViewById(R.id.mail_login);
        pass=(TextInputLayout)findViewById(R.id.pass_login);
        mAuth=FirebaseAuth.getInstance();



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                login();
            }
        });

    }
//    public void login(View view){
//        String mail=email.getText().toString();
//        String pw=password.getText().toString();
//
//        if(mail.isEmpty()&&pw.isEmpty()) {
//
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
//            final ProgressDialog dialog= new ProgressDialog(SignIn.this);
//            dialog.setMessage("Please Whait");
//            dialog.show();
//            mAuth.signInWithEmailAndPassword(mail, pw)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                // Sign in success, update UI with the signed-in user's information
//                                Log.d("signIn", "signInWithEmail:success");
//                                FirebaseUser user = mAuth.getCurrentUser();
//                                startActivity(new Intent(SignIn.this,Home.class));
////                                createNotificationChannel();
////                                addNotification();
//                            } else {
//                                // If sign in fails, display a message to the user.
//                                Log.w("signIn", "signInWithEmail:failure", task.getException());
//                                Toast.makeText(SignIn.this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//                                dialog.cancel();
//                            }
//                        }
//                    });
//        }
//
//    }

    private void login() {
        UserClass user=new UserClass();
        SharePreferenceClass sp=new SharePreferenceClass(this);
        String mail=inputEmail.getText().toString();
        String pw=password.getText().toString();
        String email = inputEmail.getText().toString();
        try {
            enkrip = encrypt(password.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        }else {
            final ProgressDialog dialog = new ProgressDialog(SignIn.this);
            dialog.setMessage("Please Waiting");
            dialog.show();
            mAuth.signInWithEmailAndPassword(email, enkrip).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        CheckEmail();
                        //startActivity(new Intent(Login.this,Dashboard.class));
                    } else {
                        Toast.makeText(SignIn.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                }
            });

        }

    }

    private void CheckEmail() {
        UserClass user=new UserClass();
        SharePreferenceClass sp=new SharePreferenceClass(this);
        String mail=inputEmail.getText().toString();
        String pw=password.getText().toString();
        String email = inputEmail.getText().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        uid = mAuth.getUid();
        final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
        mDialog.setMessage("Please Waiting...");
        mDialog.show();

        table_user.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String enEmail = inputEmail.getText().toString();
                enEmail = encodeUserEmail(enEmail);

                //check if user not exist in database
                if (dataSnapshot.child(enEmail).exists()) {
                    //get user information
                    mDialog.dismiss();

                    UserClass user = dataSnapshot.child(enEmail).getValue(UserClass.class);
                    user.setMail(inputEmail.getText().toString());
                    try {
//                                if(user2.isEmailVerified()) {
                        decrip = decrypt(user.getPass(), password.getText().toString());
                        if (decrip.equals(password.getText().toString())) {
                            FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser();
                            Boolean emailflag = user2.isEmailVerified();
                            dataSnapshot.child(uid).getValue(String.class);
                            if (emailflag) {
                                user.setMail(mail);
                                user.setPass(pw);
                                sp.createSession(user);
                                Intent homeIntent = new Intent(SignIn.this, Home.class);
                                Common.currentUser = user;
                                startActivity(homeIntent);
                                finish();
                                createNotificationChannel();
                                addNotification();
                                table_user.removeEventListener(this);
                                Toast.makeText(SignIn.this, "Anda Berhasil Login", Toast.LENGTH_LONG).show();
                                mAuth.signOut();
                            } else {
                                Toast.makeText(SignIn.this, "Please Verify Your Email!", Toast.LENGTH_LONG).show();
                                mAuth.signOut();
                                mDialog.cancel();

                            }

                        } else {

                            Toast.makeText(SignIn.this, "Please Verify Your Account!", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            mDialog.cancel();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {

                    Toast.makeText(SignIn.this, "User not exist in Database !", Toast.LENGTH_SHORT).show();
                    mDialog.cancel();

                }
                mDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

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

    private String decrypt(String decrip, String password) throws Exception{
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = Base64.decode(decrip, Base64.DEFAULT);
        byte[] decValue = c.doFinal(decodedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;

    }
    private SecretKeySpec generateKey(String password) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="Channel 2";
            String description = "This is Channel 2";
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
                .setContentTitle("Welcome!!")
                .setContentText("Selamat Datang di aplikasi Quickresto.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notificationIntent= new Intent(this,Home.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    public void back(View view){
        startActivity(new Intent(SignIn.this,LoginSignIn.class));
    }
}