package com.example.uipl.sqlitedemoapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfile extends AppCompatActivity {
    private ImageView  topbar_back_icon;
    private TextView topbar_title_text ;
    private EditText ed_Username,ed_email,ed_pNumber,ed_address;
    private Button btn_save,btn_cancel;
    private ProgressDialog progressDialog;
    String username,email,pNumber,address;
    SharedPreferences mPrefs;
    String email_id;
    public static final String mPrefrences = "myPrefrence";
    private HelperClass db;
    private UserModel userModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        db = new HelperClass(this);
        mPrefs = getSharedPreferences(mPrefrences, MODE_PRIVATE);
        username = (mPrefs.getString("username", ""));
        email    = (mPrefs.getString("email",""));
        pNumber  = (mPrefs.getString("pNumber",""));
        address  = (mPrefs.getString("address",""));
        Log.d("Shared username",username);

        Intent intent = getIntent();
         email_id = intent.getStringExtra("emailid");

        Log.d("bskjfbskjf",email_id);

        ed_Username =(EditText)findViewById(R.id.edt_userNameRes);
        ed_email =(EditText)findViewById(R.id.edt_email_id_res);
        ed_pNumber =(EditText)findViewById(R.id.edt_phone_number_res);
        ed_address =(EditText)findViewById(R.id.edt_address_res);


        /*ed_Username.setText(username);
        ed_email.setText(email);
        ed_pNumber.setText(pNumber);
        ed_address.setText(address);*/
        topbar_title_text =(TextView)findViewById(R.id.topbar_title_text);
        topbar_title_text.setText("UserProfile");
        topbar_back_icon =(ImageView)findViewById(R.id.topbar_back_icon);
        topbar_back_icon.setVisibility(View.VISIBLE);
        btn_save = (Button)findViewById(R.id.btn_save);
        btn_cancel =(Button)findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        //updateData(ed_Username,ed_email,ed_pNumber,ed_address);
        topbar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(UserProfile.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
        myAsyncTasks.execute();
    }
    public class MyAsyncTasks extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(UserProfile.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            getUserDetaByMailId(email_id);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (userModel != null) {
                String name = userModel.getUsername();
                String emailid = userModel.getEmailId();
                String phoneNumber = userModel.getPhoneNumber();
                String userAddress = userModel.getAddress();
                Log.d("username", name);
                Log.d("emailId", emailid);
                Log.d("pNumber", phoneNumber);
                Log.d("uAddress", userAddress);
                ed_Username.setText(name);
                ed_email.setText(emailid);
                ed_pNumber.setText(phoneNumber);
                ed_address.setText(userAddress);
                updateData(ed_Username,ed_email,ed_pNumber,ed_address);
            }
        }
    }
        private void getUserDetaByMailId(String email_id) {
                 UserModel detail = db.getUserDetailByMailId(email_id);
                 this.userModel = detail;

    }
    private void updateData(final EditText ed_username, final EditText ed_email, final EditText ed_pNumber, final EditText ed_address) {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String uName = ed_username.getText().toString().trim();
                 String eMail = ed_email.getText().toString().trim();
                 String pNumber = ed_pNumber.getText().toString().trim();
                 String uAddress = ed_address.getText().toString().trim();
                Log.d("string username",uName);
                Log.d("string eMail",eMail);
                Log.d("string pNumber",pNumber);
                Log.d("string uAddress",uAddress);
                boolean isUpdate = db.updateData(uName,eMail,pNumber,uAddress );
                if (isUpdate == true)
                {
                    Toast.makeText(getApplicationContext(),"Data Updated",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Data not Updated",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
