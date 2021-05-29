package com.example.a1171832jumanafinalproj.ui.favourites;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1171832jumanafinalproj.DataBaseHelper;
import com.example.a1171832jumanafinalproj.R;

import java.util.ArrayList;

public class FavouritesFragment extends Fragment {

    private FavouritesViewModel favouritesViewModel;
    private DataBaseHelper dataBaseHelper; //here


    ArrayList<Modelfav> favsList;
    RecyclerView recyclerView;
    FavAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        favouritesViewModel = ViewModelProviders.of(this).get(FavouritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favourites, container, false);
        // final TextView textView = root.findViewById(R.id.text_slideshow);
        recyclerView = (RecyclerView) root.findViewById(R.id.fav_rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager rvLayoutManager = layoutManager;
        recyclerView.setLayoutManager(rvLayoutManager);

        favouritesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //   textView.setText(s);
            }
        });

        dataBaseHelper = new DataBaseHelper(getActivity(), "database", null, 1);
        favsList = new ArrayList<>();

        String manufacturer, model, year, price, distance, accident, offer, status, id;
        int image;
        Cursor data = dataBaseHelper.getAllMarkedAsFAV();
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

            favsList.add(new Modelfav(image, id, model, price, manufacturer, distance, accident, offer, year, id, status));

        }

        FavAdapter adapter = new FavAdapter(this.getActivity(), favsList);
        recyclerView.setAdapter(adapter);

        return root;


    }
}