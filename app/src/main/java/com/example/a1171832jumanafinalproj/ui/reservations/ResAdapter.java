package com.example.a1171832jumanafinalproj.ui.reservations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1171832jumanafinalproj.DataBaseHelper;
import com.example.a1171832jumanafinalproj.R;


import java.util.ArrayList;

public class ResAdapter extends RecyclerView.Adapter<ResAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<ModelRes> mlist;
    DataBaseHelper dataBaseHelper;


    ResAdapter(Context context, ArrayList<ModelRes> list) {
        mContext = context;
        mlist = list;
    }


    @NonNull
    @Override
    public ResAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        dataBaseHelper = new DataBaseHelper(mContext, "database", null, 1);

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.reserve_item, null, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResAdapter.ViewHolder holder, final int position) {


        final ModelRes resItem = mlist.get(position);


        final TextView carname, accident, offer, distance, resdate, price, retdate, pay, cancel;
        final LinearLayout bottomsheet;


        carname = holder.item_carname;
        distance = holder.item_distance;
        accident = holder.item_accident;
        offer = holder.item_offer;
        price = holder.item_price;
        resdate = holder.item_resdate;
        retdate = holder.item_retdate;
        pay = holder.item_pay;
        cancel = holder.item_cancel;


        carname.setText("Car: " + resItem.getCarname());
        distance.setText("Distance Traveled: " + resItem.getDistance());
        price.setText("₪" + resItem.getPrice());
        accident.setText("Has Been In Accidents: " + resItem.getAccident());
        offer.setText("Has Been In Offers: " + resItem.getOffer());
        retdate.setText(resItem.getRetdate());
        resdate.setText(resItem.getResdate());

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Enter Credit card Details and stuff goes here", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseHelper.removeReservationFromDatabase(resItem.getId());
                 mlist.remove(position);
                 notifyItemRemoved(position);
                 notifyItemRangeChanged(position, mlist.size());
                Toast.makeText(mContext, "Removed Reservation!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView item_carname, item_price, item_resdate, item_accident, item_offer, item_distance, item_retdate, item_pay, item_cancel;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            item_carname = itemView.findViewById(R.id.res_item_car);
            item_price = itemView.findViewById(R.id.res_item_price);
            item_accident = itemView.findViewById(R.id.res_item_accident);
            item_offer = itemView.findViewById(R.id.res_item_offer);
            item_distance = itemView.findViewById(R.id.res_item_distance);
            item_resdate = itemView.findViewById(R.id.res_item_resdate);
            item_retdate = itemView.findViewById(R.id.res_item_retdate);
            item_pay = itemView.findViewById(R.id.res_item_pay);
            item_cancel = itemView.findViewById(R.id.res_item_cancel);

        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}



