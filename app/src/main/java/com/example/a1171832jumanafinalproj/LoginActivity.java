package com.example.a1171832jumanafinalproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a1171832jumanafinalproj.ui.admin.AdminActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LoginActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    DataBaseHelper dataBaseHelper;
public static String EXTRA_EMAIL = "package com.example.a1171832jumanafinalproj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataBaseHelper= new DataBaseHelper(LoginActivity.this, "database", null, 1);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.login_activity);
    }

    public void OpenSignupPage(View view) {
        startActivity(new Intent(LoginActivity.this,SignupActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();

        FloatingActionButton signin = (FloatingActionButton) findViewById(R.id.signInButton);
        final EditText email = (EditText) findViewById(R.id.signInEmailBox);
        final EditText password = (EditText) findViewById(R.id.signInPasswordBox);
        final CheckBox rememberme = (CheckBox) findViewById(R.id.login_rememberme);

        sharedPrefManager=SharedPrefManager.getInstance(this);
        if(!sharedPrefManager.readString("email","noValue").equals("noValue")){
            email.setText(sharedPrefManager.readString("email","noValue"));
            password.setText(sharedPrefManager.readString("password","noValue"));
            rememberme.setChecked(true);
        }

      rememberme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              if(rememberme.isChecked()){
              sharedPrefManager=SharedPrefManager.getInstance(LoginActivity.this);
              sharedPrefManager.writeString("email",email.getText().toString());
              sharedPrefManager.writeString("password",password.getText().toString());
          }else if(!rememberme.isChecked()){
                  sharedPrefManager=SharedPrefManager.getInstance(LoginActivity.this);
                  sharedPrefManager.writeString("email","noValue");
                  sharedPrefManager.writeString("password","noValue");
              }
          }
      });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

if(dataBaseHelper.checkUserExist(email.getText().toString(),password.getText().toString())){

     if (dataBaseHelper.checkUserIsAdmin(email.getText().toString(),password.getText().toString())){
         OpenAdminPage(email.getText().toString());
    }else {
         OpenMainPage(email.getText().toString());
     }

}else{
    email.setHint("Email");
    email.setHintTextColor(Color.RED);
    password.setHint("password");
    password.setHintTextColor(Color.RED);

    Toast toast =Toast.makeText(LoginActivity.this, "Wrong Email or password",Toast.LENGTH_SHORT);
    toast.show();
}
            }
        });
    }

    public void OpenMainPage(String email) {
         startActivity(new Intent(LoginActivity.this,MainActivity.class).putExtra(EXTRA_EMAIL,email));
        finish();
    }

    public void OpenAdminPage(String email) {
        startActivity(new Intent(LoginActivity.this, AdminActivity.class).putExtra(EXTRA_EMAIL,email));
        finish();
    }



}