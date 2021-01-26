package com.mrf.mrfmaharashtra.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mrf.mrfmaharashtra.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class SignupActivity extends AppCompatActivity {

    public static final String sign_up = "http://mcfrakshak.in/mrfWebservices/registration.php";

    Bitmap bitmap;
    String encodeImageString;
    TextView tvSignupbtn;
    private static final int SELECT_FILE1 = 1234;
    String selectedPath1;
    private EditText editTextName;
    private EditText editEmail;
    private EditText editContact;
    private EditText editAddress;
    private EditText editTextPassword;
    private Button etPhoto;

     Button upload;
     ImageView browse;




    AwesomeValidation awesomeValidation;
    Dialog dialog;
    Preferences preferences;


    TextView tvRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tvSignupbtn=findViewById(R.id.tvSigninbtn);
        tvRegisterButton=findViewById(R.id.tvRegisterButton);

        preferences = new Preferences(this);


        editTextName = (EditText) findViewById(R.id.etName);
        editEmail = (EditText) findViewById(R.id.etEmail);
        editContact = (EditText) findViewById(R.id.etContact);
        editAddress = (EditText) findViewById(R.id.etAddress);
        editTextPassword = (EditText) findViewById(R.id.etPassword);
        etPhoto=findViewById(R.id.uploadPhoto);

        upload=findViewById(R.id.uploads);
        browse=findViewById(R.id.images);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(SignupActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Browse Image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });




        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.uploads, RegexTemplate.NOT_EMPTY, R.string.upload);
        awesomeValidation.addValidation(this, R.id.etName, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.etContact, "[5-9]{1}[0-9]{9}$", R.string.invalid_PhoneNumber);
        awesomeValidation.addValidation(this, R.id.etEmail, Patterns.EMAIL_ADDRESS, R.string.invalid_Email);
        awesomeValidation.addValidation(this, R.id.etAddress, RegexTemplate.NOT_EMPTY, R.string.invalid_address);
        awesomeValidation.addValidation(this, R.id.etPassword, RegexTemplate.NOT_EMPTY, R.string.invalid_Password);
        awesomeValidation.addValidation(this, R.id.uploadPhoto, RegexTemplate.NOT_EMPTY, R.string.invalid_photo);


        etPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openGallery(SELECT_FILE1);

            }

        });


        tvSignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        tvRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (awesomeValidation.validate()) {
                    Log.d("success", "onResponse: ");
                    if (Utils.isNetworkConnectedMainThred(SignupActivity.this)) {
                        Validation();
                        ProgressForSignup();
                        dialog.show();
                    } else {
                        Toasty.error(SignupActivity.this,"No Internet Connection!", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Log.d("error", "onResponse: ");
                }
                //Intent intent=new Intent(SignupActivity.this,MainActivity.class);
                //startActivity(intent);
            }
        });

    }

    private void Validation() {
        StringRequest request = new StringRequest(Request.Method.POST, sign_up, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.cancel();
                try {
                    JSONObject jsonObject=new JSONObject(response);

                    if(jsonObject.getString("message").equalsIgnoreCase("successfully registered.."))
                    {
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.cancel();
                Toasty.error(SignupActivity.this, "Some error occurred -> " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("Name", editTextName.getText().toString());
                parameters.put("Email", editEmail.getText().toString());
                parameters.put("Contact1", editContact.getText().toString());
                parameters.put("Address", editAddress.getText().toString());
                parameters.put("Password", editTextPassword.getText().toString());
               // parameters.put("User_photo",etPhoto.getText().toString());
                 parameters.put("upload",encodeImageString);

                return parameters;
            }
        };
        RequestQueue rQueue = Volley.newRequestQueue(SignupActivity.this);
        rQueue.add(request);

    }

    private void ProgressForSignup() {
        dialog = new Dialog(SignupActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_for_load);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
    }

    /*private void openGallery(int selectFile1) {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i, selectFile1);
    }*/
    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();

            if (requestCode == SELECT_FILE1) {
                selectedPath1 = getPath(selectedImageUri);
            }
            etPhoto.setText(selectedPath1);
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            Uri filepath=data.getData();
            try
            {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                browse.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }
}
