package com.example.uipl.sqlitedemoapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    private EditText username,email,phone_number,address,password,confirm_password;
    private Button submit,cancel;
    private ImageView img_check_mark;
    private HelperClass db;
    private ImageView  topbar_back_icon;
    SharedPreferences mPrefs;
    public static final String mPrefrences = "myPrefrence";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        db = new HelperClass(this);

        topbar_back_icon =(ImageView)findViewById(R.id.topbar_back_icon);
        topbar_back_icon.setVisibility(View.VISIBLE);
        username =(EditText)findViewById(R.id.userName);
        email =(EditText) findViewById(R.id.edt_email_id);
        phone_number =(EditText)findViewById(R.id.edt_phone_number);
        address =(EditText)findViewById(R.id.edt_address);
        password =(EditText)findViewById(R.id.edt_password);
        img_check_mark = (ImageView)findViewById(R.id.img_check_mark);
        confirm_password =(EditText)findViewById(R.id.edt_confirm_password);
        submit = (Button)findViewById(R.id.btn_submit);
        cancel =(Button)findViewById(R.id.btn_cancel);

        topbar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uName = username.getText().toString().trim();
                String eMail = email.getText().toString().trim();
                String pNumber =phone_number.getText().toString().trim();
                String uAddress = address.getText().toString().trim();

                String  pass = password.getText().toString().trim();
                String con_pass = confirm_password.getText().toString().trim();
                if (uName.equals("")||eMail.equals("")||pNumber.equals("")||
                    uAddress.equals("")||pass.equals("")||con_pass.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (eMail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                        if (pass .equals(con_pass)) {
                            img_check_mark.setVisibility(View.VISIBLE);
                            Boolean checkMail = db.checkEmail(eMail);
                            if (checkMail)
                            {
                                Boolean insert = db.insertData(uName,eMail,pNumber,uAddress,pass,con_pass);
                                if (insert)
                                {
                                    Toast.makeText(getApplicationContext(),"Registration successfully",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                                    mPrefs = getApplicationContext().getSharedPreferences(mPrefrences,MODE_PRIVATE);
                                    SharedPreferences.Editor editor = mPrefs.edit();
                                    editor.putString("username",uName);
                                    editor.putString("email",eMail);
                                    editor.putString("pNumber",pNumber);
                                    editor.putString("address",uAddress);
                                    //editor.commit();
                                    editor.apply();
                                    startActivity(intent);
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Email Already exists",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(),"Password do not match ",Toast.LENGTH_SHORT).show();
                    }else {
                       // Toast.makeText(getApplicationContext(),"Invalid Email Address",Toast.LENGTH_SHORT).show();
                        email.setError("Invalid Email Address");
                    }

                }
            }
        });
        }
}
