package platformpbp.uajy.quickresto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class ProfileEdit extends AppCompatActivity {

    private FloatingActionButton backmr;
    String AES = "AES";
    Button done;
    private ImageView imageView;
    private Uri filePath;

    String encrip, decrip;
    String enEmail;
    FirebaseStorage storage;
    StorageReference storageReference;

    private final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        backmr = (FloatingActionButton) findViewById(R.id.floating_back_mr);
        done = (Button) findViewById(R.id.update);

//        btnChoose = (Button)findViewById(R.id.btnChoose);

        backmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileEdit.this,Profile.class);
                startActivity(intent);
            }
        });


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        imageView = (ImageView) findViewById(R.id.profil);
        try {

            storageReference = FirebaseStorage.getInstance().getReference().child("photo_profile").child(Common.currentUser.getMail());

            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                @Override


                public void onSuccess(Uri uri) {
                    Glide.with(ProfileEdit.this)
                            .load(uri)
                            .into(imageView);

                }

            }).addOnFailureListener(new OnFailureListener() {


                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }catch (Exception e){e.printStackTrace();}

        storageReference = FirebaseStorage.getInstance().getReference().child("photo_profile");


//        btnChoose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chooseImage();
//            }
//        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
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
        TextInputEditText edtPassword = (TextInputEditText) findViewById(R.id.awalpass);
        TextInputEditText edtNewPassword = (TextInputEditText) findViewById(R.id.newpassword);
        TextInputEditText edtRepeatPassword = (TextInputEditText) findViewById(R.id.passwordconfrm);

        enEmail=Common.currentUser.getMail();
        enEmail=encodeUserEmail(enEmail);


        try {
            decrip = decrypt(Common.currentUser.getPass(), edtPassword.getText().toString());
            if (decrip.equals(edtPassword.getText().toString())) {
                if (edtNewPassword.getText().toString().equals(edtRepeatPassword.getText().toString())) {
                    try {
                        encrip = encrypt(edtNewPassword.getText().toString());
                        Map<String, Object> DataUpdate = new HashMap<>();
                        DataUpdate.put("pass", encrip);
                        DataUpdate.put("fullName", edtName.getText().toString());
                        DataUpdate.put("mail", enEmail);

                        //update
                        DatabaseReference user = FirebaseDatabase.getInstance().getReference("User");
                        user.child(enEmail)
                                .updateChildren(DataUpdate);

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

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(ProfileEdit.this, "New Password doesn't match", Toast.LENGTH_LONG).show();

                }
            } else {
                Toast.makeText(ProfileEdit.this, "Wrong old Password", Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private String encrypt(String password) throws Exception {

        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(password.getBytes());
        String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;
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
    private String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

}