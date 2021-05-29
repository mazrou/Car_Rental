package com.example.a1171832jumanafinalproj.ui.reservations;

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

public class ReservationsFragment extends Fragment {

    private ReservationsViewModel reservationsViewModel;
    private DataBaseHelper dataBaseHelper; //here


    ArrayList<ModelRes> resList;
    RecyclerView recyclerView;
    ResAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        reservationsViewModel = ViewModelProviders.of(this).get(ReservationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reservations, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.res_rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager rvLayoutManager = layoutManager;
        recyclerView.setLayoutManager(rvLayoutManager);

        reservationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });


        dataBaseHelper = new DataBaseHelper(getActivity(), "database", null, 1);
        resList = new ArrayList<>();

        String carname, distance, price, accident, offer, retdate, resdate, id,carid;




        Cursor data = dataBaseHelper.getAllReservations();

        try{
        while (data.moveToNext()) {

            id = data.getString(0);
            carid = data.getString(1);
            resdate = data.getString(2);
            retdate = data.getString(3);
            carname="";
            distance=" ";
            accident=" ";
            offer=" ";
            price=" ";

            Cursor car = dataBaseHelper.getCarGivenId(carid);
            try{
                while (car.moveToNext()) {

                    carname= car.getString(1)+","+car.getString(2)+","+car.getString(3);;
                    distance = car.getString(4);
                    accident = car.getString(5);
                    offer = car.getString(6);
                    price = car.getString(8);
                }
            } finally {
                if (car != null && car.isClosed())
                    car.close();
                dataBaseHelper.close();
            }

            resList.add(new ModelRes(carname, distance, accident, offer, resdate, retdate, price, id, carid));

        }
        } finally {
            if (data != null && data.isClosed())
                data.close();
            dataBaseHelper.close();
        }

        ResAdapter adapter = new ResAdapter(this.getActivity(), resList);
        recyclerView.setAdapter(adapter);

        return root;
    }
}