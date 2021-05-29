package com.example.a1171832jumanafinalproj.ui.car;


import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1171832jumanafinalproj.DataBaseHelper;
import com.example.a1171832jumanafinalproj.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jaygoo.widget.RangeSeekBar;


import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private DataBaseHelper dataBaseHelper; //here
    ArrayList<ModelCar> carsList;
    RecyclerView recyclerView;
    CarAdapter adapter;
    LinearLayout bottomsheet;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        bottomsheet = getActivity().findViewById(R.id.bottomSheetContainer);

        recyclerView = (RecyclerView) root.findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()); //moved it to here
        RecyclerView.LayoutManager rvLayoutManager = layoutManager;
        recyclerView.setLayoutManager(rvLayoutManager);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        dataBaseHelper = new DataBaseHelper(getActivity(), "database", null, 1);
        carsList = new ArrayList<>();


        if (carsList != null) {
            carsList.clear();
        }

        String manufacturer, model, year, price, distance, accident, offer, status, id;
        int image;
        Cursor data = dataBaseHelper.getAllCars();
        try {
            while (data.moveToNext()) {

                id = data.getString(0);
                manufacturer = data.getString(1);
                model = data.getString(2);
                year = data.getString(3);
                distance = data.getString(4);
                accident = data.getString(5);
                offer = data.getString(6);
                status = data.getString(7);
                image = data.getInt(8);
                price = data.getString(9);
                carsList.add(new ModelCar(image, id, model, price, manufacturer, distance, accident, offer, year, status));
            }
        } finally {
            if (data != null && data.isClosed())
                data.close();
            dataBaseHelper.close();
        }

        //was here
        System.out.println("Home "+ carsList.size());

        CarAdapter adapter = new CarAdapter(this.getActivity(), carsList);
        recyclerView.setAdapter(adapter);

        return root;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.actionbar_filterbtn:
                // Do Activity menu item stuff here
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_bottom_sheet, bottomsheet);

                final RangeSeekBar rangeSeekBar = bottomSheetView.findViewById(R.id.sb_range);

                final Spinner spinnermodel = bottomSheetView.findViewById(R.id.bottomsheet_spinner_model);
                ArrayAdapter adapterspin1 = ArrayAdapter.createFromResource(getActivity(), R.array.ModelItems, R.layout.spinner_item);
                adapterspin1.setDropDownViewResource(R.layout.spinner_dropdown_item);
                spinnermodel.setAdapter(adapterspin1);


                final Spinner spinnernames = bottomSheetView.findViewById(R.id.bottomsheet_spinner_make);
                ArrayAdapter adapterspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.NamesItems, R.layout.spinner_item);
                adapterspin1.setDropDownViewResource(R.layout.spinner_dropdown_item);
                spinnernames.setAdapter(adapterspin2);


                rangeSeekBar.setRange(1, 4000000, 5000);
                rangeSeekBar.setProgress(1, 4000000);

                bottomSheetView.findViewById(R.id.buttonfilter).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String min_price = Float.toString(rangeSeekBar.getLeftSeekBar().getProgress());
                        String max_price = Float.toString(rangeSeekBar.getRightSeekBar().getProgress());
                        String modelfilter = spinnermodel.getSelectedItem().toString();
                        String namesfilter = spinnernames.getSelectedItem().toString();

                        adapter.getFilter().filter("filter" + "," + min_price + "," + max_price + "," + modelfilter + "," + namesfilter);

                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        adapter = new CarAdapter(getActivity(), carsList);
        recyclerView.setAdapter(adapter);
        inflater.inflate(R.menu.main, menu);
        final MenuItem item = menu.findItem(R.id.actionbar_searchbtn);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                adapter.getFilter().filter(query);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


}