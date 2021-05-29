package com.example.a1171832jumanafinalproj.ui.car;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1171832jumanafinalproj.CustomFilter;
import com.example.a1171832jumanafinalproj.DataBaseHelper;
import com.example.a1171832jumanafinalproj.ItemClickListener;
import com.example.a1171832jumanafinalproj.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> implements Filterable {

    private Context mContext;
    public ArrayList<ModelCar> mlist;
    DataBaseHelper dataBaseHelper;
    private ArrayList<ModelCar> filterList;
    CustomFilter filter;


    public CarAdapter(Context context, ArrayList<ModelCar> list) {
        mContext = context;
        mlist = list;
        this.filterList = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        dataBaseHelper = new DataBaseHelper(mContext, "database", null, 1);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.rv_items, null, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final ModelCar carItem = mlist.get(position);
        final String[] retdate = new String[1];
        ImageView image = holder.itemImage;
        final ImageView favimg = holder.itemimagefav;

        final TextView name, model, price, manufacturer, accident, offer, distance;

        final LinearLayout bottomsheetreserve;

        final Button book;

        name = holder.item_name;
        model = holder.item_model;
        price = holder.item_price;
        manufacturer = holder.item_manufacturer;
        distance = holder.item_distance;
        accident = holder.item_accident;
        offer = holder.item_offer;

        bottomsheetreserve = holder.bottomsheetreserve;

        book = holder.item_book;

        image.setImageResource(carItem.getImage());
        name.setText("In Gallary");//id
        model.setText(carItem.getModel() + " , " + carItem.getYear());
        price.setText("₪" + carItem.getPrice());
        manufacturer.setText(carItem.getManufacturer());
        distance.setText(carItem.getDistance());
        accident.setText("Accidents: "+carItem.getAccident());
        offer.setText("Offers: "+carItem.getOffer());

        if (carItem.getStatus().equals("1")) {
            favimg.setImageResource(R.drawable.ic_baseline_favorite_24);
        }

        if (dataBaseHelper.checkCarIsReserved(mlist.get(position).getName())) {
            name.setText("Reserved");//id
            book.setVisibility(View.INVISIBLE);
        }

        favimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String manufacturer, model, year, price, distance, accident, offer, status, id;
                int image = mlist.get(position).getImage();
                manufacturer = mlist.get(position).getManufacturer();
                model = mlist.get(position).getModel();
                year = mlist.get(position).getYear();
                price = mlist.get(position).getPrice();
                distance = mlist.get(position).getDistance();
                accident = mlist.get(position).getAccident();
                offer = mlist.get(position).getOffer();
                id = mlist.get(position).getName();
                status = "1";

                if (dataBaseHelper.checkCarIsFav(id)) {

                    favimg.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    Toast.makeText(mContext, "Removed From Favourites", Toast.LENGTH_SHORT).show();

                    dataBaseHelper.updateCarStatusInDatabase(id, "0");
                    dataBaseHelper.removeFavFromDatabase(id);
                } else {

                    favimg.setImageResource(R.drawable.ic_baseline_favorite_24);

                    Toast.makeText(mContext, "Added To favourites", Toast.LENGTH_SHORT).show();

                    dataBaseHelper.insertFavEntry(id, manufacturer, image, model, year, distance, accident, offer, status, price);
                    dataBaseHelper.updateCarStatusInDatabase(id, "1");
                }

            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are You Sure You Want To Reserve : " + "\n" + name.getText().toString() + " " + model.getText().toString() + " for : " + price.getText().toString())
                        .setPositiveButton("Reserve", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final String id = mlist.get(position).getName();

                                final BottomSheetDialog bottomSheetDialog2 = new BottomSheetDialog(mContext, R.style.BottomSheetDialogTheme);
                                View bottomSheetView2 = LayoutInflater.from(mContext)
                                        .inflate(R.layout.layot_bottom_sheet_reserve, bottomsheetreserve);

                                final CalendarView calender = bottomSheetView2.findViewById(R.id.calendarView);
                                final TextView retday = bottomSheetView2.findViewById(R.id.resBottomSheet_retday);
                                final TextView retdaymonth = bottomSheetView2.findViewById(R.id.resBottomSheet_retdaymonth);
                                final String[] days = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
                                final TextView resday = bottomSheetView2.findViewById(R.id.resBottomSheet_resday);
                                final TextView resdaymonth = bottomSheetView2.findViewById(R.id.resBottomSheet_resdaymonth);


                                Calendar cal = Calendar.getInstance();
                                resday.setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));

                                Date date = new Date();
                                final String ReservationDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

                                String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
                                resdaymonth.setText(days[cal.get(Calendar.DAY_OF_WEEK) - 1] + " | " + month);

                                calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                                    @Override
                                    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(i, i1, i2);
                                        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                                        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

                                        retday.setText(Integer.toString(dayOfMonth));
                                        retdaymonth.setText(days[calendar.get(Calendar.DAY_OF_WEEK) - 1] + " | " + month);

                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        dateFormat.setCalendar(calendar);

                                        retdate[0] = dateFormat.format(calendar.getTime());

                                    }
                                });


                                bottomSheetView2.findViewById(R.id.buttonConfirmReserve).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(mContext, "Reservation placed Successfully!", Toast.LENGTH_SHORT).show();
                                        dataBaseHelper.insertReservationEntry(id, ReservationDate, retdate[0]);
                                        name.setText("Reserved");//id
                                        book.setVisibility(View.INVISIBLE);
                                        bottomSheetDialog2.dismiss();
                                    }
                                });


                                bottomSheetDialog2.setContentView(bottomSheetView2);
                                bottomSheetDialog2.show();


                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                if (mlist.get(pos).getModel().equals("RAM")) {
                    Toast.makeText(mContext, "Home...", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView itemImage, itemimagefav;
        TextView item_name, item_model, item_price, item_manufacturer, item_accident, item_offer, item_distance;
        LinearLayout bottomsheetreserve;
        Button item_book;
        ItemClickListener itemClickListener;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            itemImage = itemView.findViewById(R.id.rv_item_img);
            itemimagefav = itemView.findViewById(R.id.rv_item_fav);

            item_name = itemView.findViewById(R.id.rv_item_name);
            item_model = itemView.findViewById(R.id.rv_item_model);
            item_price = itemView.findViewById(R.id.rv_item_price);
            item_manufacturer = itemView.findViewById(R.id.rv_item_manufacturer);
            item_accident = itemView.findViewById(R.id.rv_item_accident);
            item_offer = itemView.findViewById(R.id.rv_item_offer);
            item_distance = itemView.findViewById(R.id.rv_item_distance);
            item_book = itemView.findViewById(R.id.rv_item_book);

            bottomsheetreserve = itemView.findViewById(R.id.bottomSheetReserveContainer);
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v, getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }
    }

    //RETURN FILTER OBJ
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter(filterList, this);
        }
        return filter;
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

