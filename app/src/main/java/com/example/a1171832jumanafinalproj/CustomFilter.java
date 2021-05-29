package com.example.a1171832jumanafinalproj;

import android.widget.Filter;

import com.example.a1171832jumanafinalproj.ui.car.CarAdapter;
import com.example.a1171832jumanafinalproj.ui.car.ModelCar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomFilter extends Filter {

    CarAdapter adapter;
    ArrayList<ModelCar> filterList;

    public CustomFilter(ArrayList<ModelCar> filterList, CarAdapter adapter) {
        this.filterList = filterList;
        this.adapter = adapter;
    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        //CHECK CONSTRAINT VALIDITY

        if (constraint != null && constraint.length() > 0 && !constraint.toString().startsWith("filter")) {
            //CHANGE TO UPPER
            constraint = constraint.toString().toUpperCase().trim();
            //STORE OUR FILTERED item
            ArrayList<ModelCar> filteredItems = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {
                //CHECK
                if (filterList.get(i).getManufacturer().toUpperCase().trim().contains(constraint)
                        || filterList.get(i).getModel().toUpperCase().trim().contains(constraint)
                        || filterList.get(i).getDistance().toUpperCase().trim().contains(constraint)
                        || filterList.get(i).getAccident().toUpperCase().trim().contains(constraint)) {
                    //ADD item TO FILTERED list
                    filteredItems.add(filterList.get(i));
                }
            }
            results.count = filteredItems.size();
            results.values = filteredItems;

        } else if (constraint != null && constraint.length() > 0 && constraint.toString().toLowerCase().startsWith("filter")) {

            String model, names;
            constraint = constraint.toString().toLowerCase().trim();
            ArrayList<ModelCar> filteredItems = new ArrayList<>();

            List<String> inputString = Arrays.asList(constraint.toString().split(","));
            float min = Float.parseFloat(inputString.get(1).trim());
            float max = Float.parseFloat(inputString.get(2).trim());
            System.out.println("----------------------------------" + min);
            System.out.println("----------------------------------" + max);
            model = inputString.get(3).trim();
            System.out.println("----------------------------------" + model);
            //accidents = inputString.get(4).toLowerCase().trim();
           /* if (accidents.equals("yes")) {
                accidents = "true";
            } else if (accidents.equals("no")) {
                accidents = "false";
            }else{
                accidents="nan";
            }*/
            names = inputString.get(4).toLowerCase().trim();
            System.out.println("----------------------------------" + names);

            for (int i = 0; i < filterList.size(); i++) {

                float price = Float.parseFloat(filterList.get(i).getPrice());

                if (min < price && price < max
                        && filterList.get(i).getManufacturer().toLowerCase().trim().contains(names)
                        && filterList.get(i).getModel().toLowerCase().trim().contains(model)) {
                    filteredItems.add(filterList.get(i));

                } else if (min < price && price < max
                        && names.toLowerCase().trim().equals("nan")
                        && model.trim().equals("nan")) {
                    filteredItems.add(filterList.get(i));
                } else if (min < price && price < max
                        && !names.toLowerCase().trim().equals("nan")
                        && filterList.get(i).getManufacturer().toLowerCase().trim().contains(names)
                        && model.trim().equals("nan")) {
                    filteredItems.add(filterList.get(i));
                } else if (min < price && price < max
                        && names.toLowerCase().trim().equals("nan")
                        && filterList.get(i).getModel().toLowerCase().trim().contains(model)
                        && !model.trim().equals("nan")) {
                    filteredItems.add(filterList.get(i));
                }

            }

            results.count = filteredItems.size();
            results.values = filteredItems;
        } else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.mlist = (ArrayList<ModelCar>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}




