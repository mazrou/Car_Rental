package com.example.a1171832jumanafinalproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    User user;
     DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final CheckBox showPass=(CheckBox)findViewById(R.id.show);

        user = new User();
        dataBaseHelper= new DataBaseHelper(SignupActivity.this, "database", null, 1);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_signup);


        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.spinnerItems, R.layout.spinner_item);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.Countries, R.layout.spinner_item);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.City, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        adapter1.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
    }

    @Override
    protected void onResume() {
        super.onResume();

        FloatingActionButton signup = (FloatingActionButton) findViewById(R.id.SignupFloatingButton);
        final EditText firstnamefield = (EditText) findViewById(R.id.signUpFirstnameBox);
        final EditText lastnamefield = (EditText) findViewById(R.id.signUpLastnameBox);
        final EditText email = (EditText) findViewById(R.id.signUpEmailBox);
        final EditText phonefield = (EditText) findViewById(R.id.signUpPhoneBox);
        final EditText phoneCountryField = (EditText) findViewById(R.id.signUpCountryBox);
        final EditText passfield = (EditText) findViewById(R.id.signUpPassBox);
        final EditText confirmpassfield = (EditText) findViewById(R.id.signUpPassBox2);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        final CheckBox showPass=(CheckBox)findViewById(R.id.show);

        showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // show password
                    confirmpassfield.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passfield.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                } else {
                    // hide password
                    confirmpassfield.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passfield.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag=true;
                //Check Gender
                if (spinner.getSelectedItem().equals("MALE") || spinner.getSelectedItem().equals("FEMALE") ){
                    user.setGender(spinner.getSelectedItem().toString());
                }else{
                    Toast toast =Toast.makeText(SignupActivity.this, "Please Select a Gender!",Toast.LENGTH_SHORT);
                    toast.show();
                    flag=false;
                }


               //Check Country
                  if (spinner1.getSelectedItem().equals("Turky") || spinner1.getSelectedItem().equals("Palestine")|| spinner1.getSelectedItem().equals("Egypt") || spinner1.getSelectedItem().equals("Jordan")
                          || spinner1.getSelectedItem().equals("Lebanon") || spinner1.getSelectedItem().equals("United States of America") || spinner1.getSelectedItem().equals("United Kingdom") || spinner1.getSelectedItem().equals("China")){
                    user.setGender(spinner1.getSelectedItem().toString());
                }else{
                    Toast toast =Toast.makeText(SignupActivity.this, "Please Select a Country!",Toast.LENGTH_SHORT);
                    toast.show();
                    flag=false;
                }

                  //Check city
                if (spinner2.getSelectedItem().equals("Ramallah") || spinner2.getSelectedItem().equals("Hebron")|| spinner2.getSelectedItem().equals("Jerico") || spinner2.getSelectedItem().equals("Cairo")
                        || spinner2.getSelectedItem().equals("Alexandria") || spinner2.getSelectedItem().equals("Hurghada") || spinner2.getSelectedItem().equals("Oman") || spinner2.getSelectedItem().equals("Irbid") || spinner2.getSelectedItem().equals("Salt")){
                    user.setGender(spinner2.getSelectedItem().toString());
                }else{
                    Toast toast =Toast.makeText(SignupActivity.this, "Please Select a City!",Toast.LENGTH_SHORT);
                    toast.show();
                    flag=false;
                }

                //Check first and last name
                if(firstnamefield.getText().toString().length() < 3){
                    firstnamefield.setText("");
                    firstnamefield.setHint("First Name");
                    firstnamefield.setHintTextColor(Color.parseColor("#c44f4f"));
                    if (lastnamefield.getText().toString().length() < 3){
                        lastnamefield.setText("");
                        lastnamefield.setHint("Last Name");
                        lastnamefield.setHintTextColor(Color.parseColor("#c44f4f"));
                    }
                    Toast toast =Toast.makeText(SignupActivity.this, "First name must be longer than three characters",Toast.LENGTH_SHORT);
                    toast.show();
                    flag=false;

                }else{
                    if (lastnamefield.getText().toString().length() < 3){
                        lastnamefield.setText("");
                        lastnamefield.setHint("Last Name");
                        lastnamefield.setHintTextColor(Color.parseColor("#c44f4f"));
                        Toast toast =Toast.makeText(SignupActivity.this, "Last name must be longer than three characters",Toast.LENGTH_SHORT);
                        toast.show();
                    }else{
                        user.setName(firstnamefield.getText().toString() +" "+ lastnamefield.getText().toString());
                    }
                }
                if(phoneCountryField.getText().toString().length() == 0 ){
                    phoneCountryField.setText("");
                    phoneCountryField.setHint("country");
                    phoneCountryField.setHintTextColor(Color.parseColor("#c44f4f"));

                    Toast toast =Toast.makeText(SignupActivity.this, "Invalid Country Number",Toast.LENGTH_SHORT);
                    toast.show();
                    flag=false;
                }else{
                    user.setPhone(phonefield.getText().toString());
                }
                //check phone number
                if(!(phonefield.getText().toString().startsWith("05")) || phonefield.getText().toString().length() != 10 ){
                    phonefield.setText("");
                    phonefield.setHint("Phone : 05");
                    phonefield.setHintTextColor(Color.parseColor("#c44f4f"));

                    Toast toast =Toast.makeText(SignupActivity.this, "Invalid Phone Number",Toast.LENGTH_SHORT);
                    toast.show();
                    flag=false;
                }else{
                    user.setPhone("+"+phoneCountryField.getText().toString() + " " + phonefield.getText().toString().substring(1));
                }

                if(!email.getText().toString().contains("@") || !email.getText().toString().endsWith(".com")){
                    email.setText("");
                    email.setHint("Email");
                    email.setHintTextColor(Color.parseColor("#c44f4f"));
                    Toast toast =Toast.makeText(SignupActivity.this, "Invalid Email Address",Toast.LENGTH_SHORT);
                    toast.show();
                    flag=false;
                }else {
                    if(dataBaseHelper.EmailExist(email.getText().toString())){
                        email.setText("");
                        email.setHint("Email");
                        email.setHintTextColor(Color.parseColor("#c44f4f"));
                        Toast toast =Toast.makeText(SignupActivity.this, "An account with this email already exists",Toast.LENGTH_SHORT);
                        toast.show();
                        flag=false;
                    }else{
                    user.setEmail(email.getText().toString());
                    }
                }
                //check password

                List<String> errorList = new ArrayList<String>();

                if(isValid(passfield.getText().toString(),confirmpassfield.getText().toString(),errorList)){
                    user.setPassword(passfield.getText().toString());
                }else{
                    String errorline="";
                    for (String error : errorList) {
                        error=error+"\n";
                        errorline=errorline+error;
                    }
                    Toast toast =Toast.makeText(SignupActivity.this, errorline,Toast.LENGTH_SHORT);
                    toast.show();
                    flag=false;
                }

                if(flag==true){

                    Toast toast =Toast.makeText(SignupActivity.this, "Account Created Successfully!! ",Toast.LENGTH_SHORT);
                    toast.show();
                    OpenSigninPage();
                    user.setAdmin(false);
                    dataBaseHelper.insertUser(user);
                }

            }
        });

    }

    public static boolean isValid(String passwordhere, String confirmhere, List<String> errorList) {

        Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern CharCasePatten = Pattern.compile("[A-Za-z ]");
       // Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
       // Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");
        errorList.clear();

        boolean flag=true;

        if (!passwordhere.equals(confirmhere)) {
            errorList.add("password and confirm password does not match");
            flag=false;
        }
        if (passwordhere.length() < 5) {
            errorList.add("Password length must have at least 5 characters !!");
            flag=false;
        }
        if (!specailCharPatten.matcher(passwordhere).find()) {
            errorList.add("Password must have at least one special character !!");
            flag=false;
        }

        if (!CharCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have at least one character !!");
            flag=false;
        }

      /*  if (!UpperCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have at least one uppercase character !!");
            flag=false;
        }
        if (!lowerCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have at least one lowercase character !!");
            flag=false;
        }*/
        if (!digitCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have at least one digit character !!");
            flag=false;
        }

        return flag;

    }




    public void OpenSigninPage() {
        startActivity(new Intent(SignupActivity.this,LoginActivity.class));
    }

}



