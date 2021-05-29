package com.example.a1171832jumanafinalproj.ui.admin;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.NavType;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.a1171832jumanafinalproj.LoginActivity;
import com.example.a1171832jumanafinalproj.MainActivity;
import com.example.a1171832jumanafinalproj.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity   {
    private AppBarConfiguration mAppBarConfiguration;


    interface CheckoutButtonListener {
        void onClick(/*provide whatever arguments you need to back to parent*/);
    }

    private CheckoutButtonListener mCheckoutListener;

    LinearLayout linearLayout;
    LinearLayout bottomsheetcallus;
    LinearLayout bottomsheet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//////////////

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_admin);
        Intent intent = getIntent();
        final String email = intent.getStringExtra(LoginActivity.EXTRA_EMAIL);

        linearLayout = (LinearLayout) findViewById(R.id.layout);
        bottomsheet = findViewById(R.id.bottomSheetContainer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.admin_profile, R.id.admin_reservations, R.id.admin_costumers , R.id.admin_add_admin , R.id.admin_delete)
                .setDrawerLayout(drawer)
                .build();

        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_admin);


        NavArgument emailargument = new NavArgument.Builder().setType(NavType.StringType).setDefaultValue(email).build();
        NavGraph graph = navController.getGraph();
        graph.addArgument( "Email", emailargument) ;
      /*  NavDestination demoDestination = graph.findNode(R.id.admin_profile);
        assert demoDestination != null;
        demoDestination.addArgument("Email", emailargument); */
        navController.setGraph(graph);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//listeners
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.nav_call) {
                    // Toast.makeText(getApplicationContext(), "call US", Toast.LENGTH_SHORT).show();
                    bottomsheetcallus = findViewById(R.id.bottomSheetCallusContainer);
                    final BottomSheetDialog bottomSheetDialog3 = new BottomSheetDialog(AdminActivity.this, R.style.BottomSheetDialogTheme
                    );
                    View bottomSheetView3 = LayoutInflater.from(AdminActivity.this)
                            .inflate(
                                    R.layout.layout_bottom_sheet_callus, bottomsheetcallus);

                    bottomSheetView3.findViewById(R.id.calluscall).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent dialIntent = new Intent();
                            dialIntent.setAction(Intent.ACTION_DIAL);
                            dialIntent.setData(Uri.parse("tel:+970569916752"));
                            startActivity(dialIntent);

                            bottomSheetDialog3.dismiss();
                        }
                    });

                    bottomSheetView3.findViewById(R.id.callusemail).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent gmailIntent = new Intent();
                            gmailIntent.setAction(Intent.ACTION_SENDTO);
                            gmailIntent.setType("message/rfc822");
                            gmailIntent.setData(Uri.parse("mailto:"));
                            gmailIntent.putExtra(Intent.EXTRA_EMAIL, "HatsuneMiku@Miku.com");
                            gmailIntent.putExtra(Intent.EXTRA_SUBJECT, "This is my Project");
                            gmailIntent.putExtra(Intent.EXTRA_TEXT, "Snow Drive is a really awesome song.");
                            startActivity(gmailIntent);

                            bottomSheetDialog3.dismiss();
                        }
                    });

                    bottomSheetView3.findViewById(R.id.callusmap).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent mapsIntent = new Intent();
                            mapsIntent.setAction(Intent.ACTION_VIEW);
                            mapsIntent.setData(Uri.parse("geo:31.9591925,35.1820071"));
                            startActivity(mapsIntent);
                            bottomSheetDialog3.dismiss();
                        }
                    });

                    bottomSheetDialog3.setContentView(bottomSheetView3);
                    bottomSheetDialog3.show();
                }


                if (id == R.id.admin_logout) {
                    OpenSigninPage();
                    finish();
                }

                //This is for maintaining the behavior of the Navigation view
                NavigationUI.onNavDestinationSelected(menuItem, navController);
                //This is for closing the drawer after acting on it
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_admin);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void OpenSigninPage() {
        startActivity(new Intent(AdminActivity.this, LoginActivity.class));
    }



}