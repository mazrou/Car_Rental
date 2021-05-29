package com.example.a1171832jumanafinalproj.ui.car;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatDialogFragment;

public class ReserveDialog extends AppCompatDialogFragment {

    private String Message;

    public String getMessage() {
        return Message;
    }

    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Do You Want to Reserve car: ");
        builder.setMessage(this.getMessage());
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Reserve", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              flag=true;
            }
        });

        return builder.create();

    }






}
