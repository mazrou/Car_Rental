package com.example.a1171832jumanafinalproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class Connect extends AppCompatActivity {

    private DataBaseHelper dataBaseHelper;
    Button button;
    LinearLayout linearLayout;
    SharedPreferences prefs = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        dataBaseHelper = new DataBaseHelper(this, "database", null, 1);
        setProgress(false);

  ///      checkFirstRun();

        if(!dataBaseHelper.checkUserIsAdmin("admin@gmail.com" , "admin")){
            User user = new User() ;
            user.setAdmin(true );
            user.setEmail("admin@gmail.com");
            user.setPassword("admin");
            user.setName("admin");
            dataBaseHelper.insertUser(user);
        }
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(Connect.this);
                connectionAsyncTask.execute("https://www.mocky.io/v2/5bfea5963100006300bb4d9a");
            }
        });

        TextView status = (TextView) findViewById(R.id.statustext);
        status.setVisibility(View.INVISIBLE);
    }


    public void setButtonText(String text) {
        button.setText(text);
    }

    public void fillcars(List<Car> cars) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout);
        linearLayout.removeAllViews();
        int imageid = R.drawable.mikutest;
        for (int i = 0; i < cars.size(); i++) {
            //Was Working HERE
            String logoname = cars.get(i).getManufacturer().toUpperCase().trim();
            switch (logoname) {
                case "CHEVROLET":
                    imageid = R.drawable.chevrolet;
                    break;
                case "SUBARU":
                    imageid = R.drawable.subaru;
                    break;
                case "DUCATI":
                    imageid = R.drawable.ducati;
                    break;
                case "VOLVO":
                    imageid = R.drawable.volvo;
                    break;
                case "MAZDA":
                    imageid = R.drawable.mazda;
                    break;
                case "GMC":
                    imageid = R.drawable.gmc;
                    break;
                case "AUDI":
                    imageid = R.drawable.audi;
                    break;
                case "KIA":
                    imageid = R.drawable.kia;
                    break;
                case "KAWASAKI":
                    imageid = R.drawable.kawasaki;
                    break;
                case "HONDA":
                    imageid = R.drawable.honda;
                    break;
                case "FORD":
                    imageid = R.drawable.ford;
                    break;
                case "BMW":
                    imageid = R.drawable.bmw;
                    break;
                case "FERRARI":
                    imageid = R.drawable.ferrari;
                    break;
                case "RAM":
                    imageid = R.drawable.ram;
                    break;
                case "SATURN":
                    imageid = R.drawable.saturn;
                    break;

                default:
                    imageid = R.drawable.mikutest;
            }

            dataBaseHelper.insertCarEntry(cars.get(i).getManufacturer(), imageid, cars.get(i).getModel(),
                    Integer.toString(cars.get(i).getYear()), cars.get(i).getDistance(), Boolean.toString(cars.get(i).isBeenInAccident()), Boolean.toString(cars.get(i).isBeenInOffer()), "0", Double.toString(cars.get(i).getPrice()));
            //HERE
            TextView textView = new TextView(this);
            textView.setText(cars.get(i).toString());
            linearLayout.addView(textView);
        }
    }

    public void setProgress(boolean progress) {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (progress) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void setstatus(boolean state) {
        TextView status = (TextView) findViewById(R.id.statustext);
        if (state) {
            status.setVisibility(View.VISIBLE);
            status.setText("Connected!");
            OpenSigninPage();
            finish();

        } else {
            status.setVisibility(View.VISIBLE);
            status.setText("Connection Failed Try Again!");
        }

    }
    // CONNECT -- done
    // PHONE NUMER -- done
    // HOME PAGE -- Done
    // SPECIAL OFFERS -- Done
    // PROFILE = i CAN'T change the avatar

    public void OpenSigninPage() {
        startActivity(new Intent(Connect.this, LoginActivity.class));
    }

    private void checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;


        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

           /* OpenSigninPage();
            finish();*/
            // This is just a normal run


        } else if (savedVersionCode == DOESNT_EXIST) {
            // Update the shared preferences with the current version code
            prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();


        } else if (currentVersionCode > savedVersionCode) {

            // TODO This is an upgrade
        }


    }

}