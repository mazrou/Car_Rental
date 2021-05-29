package com.example.a1171832jumanafinalproj.ui;

import static android.app.Activity.RESULT_OK;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1171832jumanafinalproj.DataBaseHelper;
import com.example.a1171832jumanafinalproj.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Profile extends Fragment {

    private ProfileViewModel ProfileViewModel;
    String email;
    private DataBaseHelper dataBaseHelper; //here
    private ImageView avatar ;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ProfileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.profile_fragment, container, false);

        ProfileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        return root;
    }



    private void pickFromGallery(int requestCode) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, requestCode);
    }


    private void dispatchTakePictureIntent(int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
     //
        startActivityForResult(takePictureIntent, requestCode);

    }

    final int GALLERY_REQUEST_CODE = 100 ;
    final int REQUEST_IMAGE_CAPTURE = 101 ;
    final int PICK_IMAGE_REQUEST_CODE = 102 ;
    Bitmap bitmap ;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            pickFromGallery(GALLERY_REQUEST_CODE);
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent(PICK_IMAGE_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
            super.onActivityResult(reqCode, resultCode, data);

            if (resultCode == RESULT_OK && (data.getData() != null||data.getExtras()!=null)) {
                if (reqCode == GALLERY_REQUEST_CODE) {
                    try {
                       final Uri imageUri = data.getData();
                        final InputStream imageStream = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(imageUri);
                        bitmap = BitmapFactory.decodeStream(imageStream);
                        avatar.setImageBitmap(bitmap);

                    } catch (FileNotFoundException e) {
                         e.printStackTrace();
                    }
                } else if (reqCode == PICK_IMAGE_REQUEST_CODE && data.getExtras() != null) {
                     Bundle extras = data.getExtras();
                     bitmap = (Bitmap) extras.get("data");
                    avatar.setImageBitmap(bitmap);
                }
            }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final TextView nameonprofile = getActivity().findViewById(R.id.editprofile_name_phone);
        final TextView detailpass = getActivity().findViewById(R.id.editprofile_detailpass);
        avatar = requireActivity().findViewById(R.id.image_view) ;
        final ImageView editbtn = getActivity().findViewById(R.id.editprofile_editbtn);
        final Button savebtn = getActivity().findViewById(R.id.editprofile_savebtn);

        final EditText firstnamefield = (EditText) getActivity().findViewById(R.id.editProfileFirstnameBox);
        final EditText lastnamefield = (EditText) getActivity().findViewById(R.id.editProfileLastnameBox);
        final EditText phonefield = (EditText) getActivity().findViewById(R.id.editProfilePhoneBox);
        final EditText passfield = (EditText) getActivity().findViewById(R.id.editProfilePassBox);
        final EditText confirmpassfield = (EditText) getActivity().findViewById(R.id.editProfilePassBox2);
        if ( getArguments() != null && !getArguments().isEmpty()) {
            ProfileArgs args = ProfileArgs.fromBundle(getArguments());

            email = args.getEmail();

            final String[] editeddata = new String[4];







            dataBaseHelper = new DataBaseHelper(getActivity(), "database", null, 1);
            String name = "AKITA NERU", phone = "0569916752";
            Cursor data = dataBaseHelper.getUser(email);
            try {
                while (data.moveToNext()) {
                    name = data.getString(data.getColumnIndex("NAME"));
                    phone = data.getString(data.getColumnIndex("PHONE"));
                }
            } finally {
                if (data != null && data.isClosed())
                    data.close();
                dataBaseHelper.close();
            }
            nameonprofile.setText(name);
            String[] splited = name.split("\\s+");
            firstnamefield.setEnabled(false);

            firstnamefield.setText(splited[0]);

            lastnamefield.setEnabled(false);
            lastnamefield.setText(splited[1]);

            phonefield.setEnabled(false);
            phonefield.setText(phone);

            detailpass.setVisibility(View.INVISIBLE);
            passfield.setVisibility(View.INVISIBLE);
            confirmpassfield.setVisibility(View.INVISIBLE);
            savebtn.setVisibility(View.INVISIBLE);

            editbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    firstnamefield.setEnabled(true);
                    lastnamefield.setEnabled(true);
                    phonefield.setEnabled(true);
                    detailpass.setVisibility(View.VISIBLE);
                    passfield.setVisibility(View.VISIBLE);
                    confirmpassfield.setVisibility(View.VISIBLE);
                    savebtn.setVisibility(View.VISIBLE);

                }
            });

            savebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    boolean flag = true;
                    //Check first and last name
                    if (firstnamefield.getText().toString().length() < 3) {
                        firstnamefield.setText("");
                        firstnamefield.setHint("First Name");
                        firstnamefield.setHintTextColor(Color.parseColor("#c44f4f"));
                        Toast toast = Toast.makeText(getActivity(), "First name must be longer than three characters", Toast.LENGTH_SHORT);
                        toast.show();
                        flag = false;
                    } else {
                        editeddata[0] = firstnamefield.getText().toString();
                    }
                    if (lastnamefield.getText().toString().length() < 3) {
                        lastnamefield.setText("");
                        lastnamefield.setHint("Last Name");
                        lastnamefield.setHintTextColor(Color.parseColor("#c44f4f"));
                        Toast toast = Toast.makeText(getActivity(), "Last name must be longer than three characters", Toast.LENGTH_SHORT);
                        toast.show();
                        flag = false;
                    } else {
                        editeddata[1] = lastnamefield.getText().toString();
                    }
                    //check phone number
                    if (!(phonefield.getText().toString().startsWith("05")) || phonefield.getText().toString().length() != 10) {
                        phonefield.setText("");
                        phonefield.setHint("Phone : 05");
                        phonefield.setHintTextColor(Color.parseColor("#c44f4f"));

                        Toast toast = Toast.makeText(getActivity(), "Invalid Phone Number", Toast.LENGTH_SHORT);
                        toast.show();
                        flag = false;
                    } else {
                        editeddata[2] = phonefield.getText().toString();
                    }

                    List<String> errorList = new ArrayList<String>();

                    if (isValid(passfield.getText().toString(), confirmpassfield.getText().toString(), errorList)) {
                        editeddata[3] = passfield.getText().toString();
                    } else {
                        String errorline = "";
                        for (String error : errorList) {
                            error = error + "\n";
                            errorline = errorline + error;
                        }
                        Toast toast = Toast.makeText(getActivity(), errorline, Toast.LENGTH_SHORT);
                        toast.show();
                        flag = false;
                    }

                    if (flag) {
                        String name = editeddata[0] + " " + editeddata[1];
                        dataBaseHelper.updateUser(email, name, editeddata[2], editeddata[3]);

                        nameonprofile.setText(name);

                        firstnamefield.setEnabled(false);
                        firstnamefield.setText(editeddata[0]);

                        lastnamefield.setEnabled(false);
                        lastnamefield.setText(editeddata[1]);

                        phonefield.setEnabled(false);
                        phonefield.setText(editeddata[2]);

                        detailpass.setVisibility(View.INVISIBLE);
                        passfield.setVisibility(View.INVISIBLE);
                        confirmpassfield.setVisibility(View.INVISIBLE);
                        savebtn.setVisibility(View.INVISIBLE);

                        Toast toast = Toast.makeText(getActivity(), "Data Changed Successfully!! ", Toast.LENGTH_SHORT);
                        toast.show();

                    }

                }
            });

        }
        editbtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                addImage();
            }
        });
    }
    private void addImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        String[] choices = {getText(R.string.import_image).toString(), getText(R.string.take_photo).toString()};
        builder.setSingleChoiceItems(choices, -1, ((dialog, which) -> {
            if (which == 0) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                else
                    pickFromGallery(GALLERY_REQUEST_CODE);
            } else {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
                    ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
                else
                    dispatchTakePictureIntent(PICK_IMAGE_REQUEST_CODE);
            }
            dialog.dismiss();
        }));
        builder.setTitle(getText(R.string.select));
        builder.setNegativeButton(getText(R.string.cancel), (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }


    public static boolean isValid(String passwordhere, String confirmhere, List<String> errorList) {

        Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern CharCasePatten = Pattern.compile("[A-Za-z ]");
       // Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        //Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");
        errorList.clear();

        boolean flag = true;

        if (!passwordhere.equals(confirmhere)) {
            errorList.add("password and confirm password does not match");
            flag = false;
        }
        if (passwordhere.length() < 5) {
            errorList.add("Password length must have at least 5 characters !!");
            flag = false;
        }
        if (!specailCharPatten.matcher(passwordhere).find()) {
            errorList.add("Password must have at least one special character !!");
            flag = false;
        }

        if (!CharCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have at least uppercase character !!");
            flag = false;
        }
       /* if (!UpperCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have at least one uppercase character !!");
            flag = false;
        }
        if (!lowerCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have at least one lowercase character !!");
            flag = false;
        }*/
        if (!digitCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have at least one digit character !!");
            flag = false;
        }

        return flag;

    }
}