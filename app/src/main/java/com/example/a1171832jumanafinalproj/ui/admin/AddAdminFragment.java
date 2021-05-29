package com.example.a1171832jumanafinalproj.ui.admin;

import static com.example.a1171832jumanafinalproj.SignupActivity.isValid;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a1171832jumanafinalproj.DataBaseHelper;
import com.example.a1171832jumanafinalproj.R;
import com.example.a1171832jumanafinalproj.SignupActivity;
import com.example.a1171832jumanafinalproj.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class AddAdminFragment extends Fragment {

    User user;
    DataBaseHelper dataBaseHelper;
    FloatingActionButton signup  ; //= (FloatingActionButton) requireActivity().findViewById(R.id.add_admin_button);
     EditText firstnamefield ; // (EditText) requireActivity().findViewById(R.id.signUpFirstnameAdmin);
     EditText lastnamefield ; // (EditText) requireActivity().findViewById(R.id.signUpLastnameAdmin);
     EditText email ; // (EditText) requireActivity().findViewById(R.id.signUpEmailAdmin);
     EditText phonefield ; // (EditText) requireActivity().findViewById(R.id.signUpPhoneAdmin);
     EditText phoneCountryField ; // (EditText) requireActivity().findViewById(R.id.signUpCountryAdmin);
     EditText passfield ; // (EditText) requireActivity().findViewById(R.id.signUpPassAdmin);
     EditText confirmpassfield ; // (EditText) requireActivity().findViewById(R.id.signUpPassAdmin2);

     CheckBox showPass; //(CheckBox)requireActivity().findViewById(R.id.show);


    public AddAdminFragment() {
        // Required empty public constructor
    }
    public static AddAdminFragment newInstance(String param1, String param2) {
        return new AddAdminFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
   signup = (FloatingActionButton) requireActivity().findViewById(R.id.add_admin_button);
      firstnamefield = (EditText) requireActivity().findViewById(R.id.signUpFirstnameAdmin);
         lastnamefield = (EditText) requireActivity().findViewById(R.id.signUpLastnameAdmin);
         email = (EditText) requireActivity().findViewById(R.id.signUpEmailAdmin);
         phonefield = (EditText) requireActivity().findViewById(R.id.signUpPhoneAdmin);
       phoneCountryField = (EditText) requireActivity().findViewById(R.id.signUpCountryAdmin);
         passfield = (EditText) requireActivity().findViewById(R.id.signUpPassAdmin);
        confirmpassfield = (EditText) requireActivity().findViewById(R.id.signUpPassAdmin2);

         showPass=(CheckBox)requireActivity().findViewById(R.id.show);
        user = new User() ;
        dataBaseHelper= new DataBaseHelper(requireContext(), "database", null, 1);

    }

    @Override
    public void onResume() {
        super.onResume();


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
                    Toast toast =Toast.makeText(requireContext(), "First name must be longer than three characters",Toast.LENGTH_SHORT);
                    toast.show();
                    flag=false;

                }else{
                    if (lastnamefield.getText().toString().length() < 3){
                        lastnamefield.setText("");
                        lastnamefield.setHint("Last Name");
                        lastnamefield.setHintTextColor(Color.parseColor("#c44f4f"));
                        Toast toast =Toast.makeText(requireContext(), "Last name must be longer than three characters",Toast.LENGTH_SHORT);
                        toast.show();
                    }else{
                        user.setName(firstnamefield.getText() +" "+ lastnamefield.getText());
                    }
                }
                if(phoneCountryField.getText().toString().length() == 0 ){
                    phoneCountryField.setText("");
                    phoneCountryField.setHint("country");
                    phoneCountryField.setHintTextColor(Color.parseColor("#c44f4f"));

                    Toast toast =Toast.makeText(requireContext(), "Invalid Country Number",Toast.LENGTH_SHORT);
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

                    Toast toast =Toast.makeText(requireContext(), "Invalid Phone Number",Toast.LENGTH_SHORT);
                    toast.show();
                    flag=false;
                }else{
                    user.setPhone("+"+phoneCountryField.getText().toString() + " " + phonefield.getText().toString().substring(1));
                }

                if(!email.getText().toString().contains("@") || !email.getText().toString().endsWith(".com")){
                    email.setText("");
                    email.setHint("Email");
                    email.setHintTextColor(Color.parseColor("#c44f4f"));
                    Toast toast =Toast.makeText(requireContext(), "Invalid Email Address",Toast.LENGTH_SHORT);
                    toast.show();
                    flag=false;
                }else {
                    if(dataBaseHelper.EmailExist(email.getText().toString())){
                        email.setText("");
                        email.setHint("Email");
                        email.setHintTextColor(Color.parseColor("#c44f4f"));
                        Toast toast =Toast.makeText(requireContext(), "An account with this email already exists",Toast.LENGTH_SHORT);
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
                    Toast toast =Toast.makeText(requireContext(), errorline,Toast.LENGTH_SHORT);
                    toast.show();
                    flag=false;
                }

                if(flag){
                    Toast toast =Toast.makeText(requireContext(), "Account Created Successfully!! ",Toast.LENGTH_SHORT);
                    toast.show();
                    user.setAdmin(true);
                    dataBaseHelper.insertUser(user);
                    Navigation.findNavController(view).popBackStack();
                }

            }
        });
    }
}