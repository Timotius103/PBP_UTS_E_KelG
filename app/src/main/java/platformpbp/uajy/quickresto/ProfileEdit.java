package platformpbp.uajy.quickresto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import platformpbp.uajy.quickresto.dabase.DatabaseClient;
import platformpbp.uajy.quickresto.model.User;

public class ProfileEdit extends AppCompatActivity {

    private FloatingActionButton backmr;
    String AES = "AES";
    Button done;
    private ImageView imageView;
    private Uri filePath;
    TextInputEditText name,phone;
    TextInputLayout namaLayout,phoneLayout;
    String encrip, decrip,email;
    String enEmail;
    FirebaseStorage storage;
    private User currentUser;
    StorageReference storageReference;

    private final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        UserClass userClass = new UserClass();
        SharePreferenceClass sp = new SharePreferenceClass(this);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        name=findViewById(R.id.fullnameupdte);
        phone=findViewById(R.id.phoneEdit);
        namaLayout=findViewById(R.id.fullname_update);
        phoneLayout=findViewById(R.id.newPhone);

        userClass = sp.getuser();
        email = userClass.getMail();
        findEmail(email);

        backmr = (FloatingActionButton) findViewById(R.id.floating_back_mr);
        done = (Button) findViewById(R.id.update);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama=name.getText().toString();
                String tlpon=phone.getText().toString();

                if(nama.isEmpty()){
                    namaLayout.setError("Fill Full Name!");
                    Toast.makeText(getApplicationContext(), "Please Fill Full Name!", Toast.LENGTH_SHORT).show();
                }else if(tlpon.isEmpty()){
                    phoneLayout.setError("Fill Phone!");
                    Toast.makeText(getApplicationContext(), "Please Fill phone!", Toast.LENGTH_SHORT).show();
                }else{
                    currentUser.setFullName(nama);
                    currentUser.setPhone(tlpon);
                    update(currentUser);
                    uploadImage();
                }

            }
        });

//        btnChoose = (Button)findViewById(R.id.btnChoose);

        backmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileEdit.this, Profile.class);
                startActivity(intent);
            }
        });
    }
    private void findEmail(final String cari){
        class GetUsers extends AsyncTask<Void, Void, List<User>> {

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
                name.setText(users.get(0).getFullName());
                phone.setText(users.get(0).getPhone());
                currentUser = users.get(0);
                if (users.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Empty List", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetUsers get = new GetUsers();
        get.execute();
    }

    private void update(final User user){
        class UpdateUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance2(ProfileEdit.this).getDatabase()
                        .userDAO()
                        .update(user);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(ProfileEdit.this, "User updated", Toast.LENGTH_SHORT).show();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.hide(UpdateFragment.this).commit();
            }
        }

        UpdateUser update = new UpdateUser();
        update.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        TextInputEditText edtName = (TextInputEditText) findViewById(R.id.fullnameupdte);
        TextInputEditText edtPhone = (TextInputEditText) findViewById(R.id.phoneEdit);


        enEmail=currentUser.getMail().replace(".",",");

        Map<String, Object> DataUpdate = new HashMap<>();
        DataUpdate.put("fullName", edtName.getText().toString());
        DataUpdate.put("phone", edtPhone.getText().toString());
//
        //update
        DatabaseReference user = FirebaseDatabase.getInstance().getReference("User");
        user.child(enEmail).child("fullName").setValue(edtName.getText().toString());
        user.child(enEmail).child("phone").setValue(edtPhone.getText().toString());
        System.out.println("name: " + edtName.getText().toString());
        System.out.println("enmail: " + enEmail);
        System.out.println("phonenya: " + edtPhone.getText().toString());
        //update

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child(Common.currentUser.getMail());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(ProfileEdit.this, "Uploaded", Toast.LENGTH_SHORT).show();

                            //Intent intent = new Intent(UpdateProfile.this, Home.class);
                            //startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ProfileEdit.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });

        }



    }

    private String decrypt(String decrip, String password) throws Exception {
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = Base64.decode(decrip, Base64.DEFAULT);
        byte[] decValue = c.doFinal(decodedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;

    }

    private SecretKeySpec generateKey(String password) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }
//    private String encodeUserEmail(String userEmail) {
//        return userEmail.replace(".", ",");
//    }

}