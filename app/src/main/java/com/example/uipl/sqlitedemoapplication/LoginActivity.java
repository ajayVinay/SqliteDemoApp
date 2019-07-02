package com.example.uipl.sqlitedemoapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    HelperClass db;
    Button login,signup,viewDetail;
    EditText edt_email,edt_password;
    SharedPreferences mPrefs;
    public static final String mPrefrences = "myPrefrence";
    String username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPrefs = getSharedPreferences(mPrefrences, MODE_PRIVATE);
        username = (mPrefs.getString("username", ""));
        Log.d("uName",username);

        db = new HelperClass(this);
        edt_email = (EditText)findViewById(R.id.edt_userName) ;
        edt_password =(EditText)findViewById(R.id.edt_password);
        viewDetail = (Button)findViewById(R.id.btn_view);
        login =(Button)findViewById(R.id.btn_login);
        signup =(Button)findViewById(R.id.btn_signup);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();*/
                String username = edt_email.getText().toString().trim();
                String password = edt_password.getText().toString().trim();

                Log.d("emailid",username);
                Log.d("password",password);
                if (!username.isEmpty() && !password.isEmpty()) {
                    Boolean checkEmailPass =db.chechEmailPAssword(username,password);
                    if (checkEmailPass)
                    {
                        Toast.makeText(getApplicationContext(),"SuccessFully Login",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,UserProfile.class);
                        intent.putExtra("emailid",username);
                        startActivity(intent);
                    }else
                    {
                        Toast.makeText(getApplicationContext(),"Wrong email or password",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Enter Your Email And Password", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
