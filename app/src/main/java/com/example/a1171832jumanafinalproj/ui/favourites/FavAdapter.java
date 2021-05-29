package com.example.a1171832jumanafinalproj.ui.favourites;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a1171832jumanafinalproj.DataBaseHelper;
import com.example.a1171832jumanafinalproj.R;
import com.example.a1171832jumanafinalproj.ui.car.ModelCar;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<Modelfav> mlist;
    private DataBaseHelper dataBaseHelper;

    FavAdapter(Context context, ArrayList<Modelfav> list) {
        mContext = context;
        mlist = list;
    }

    @NonNull
    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        dataBaseHelper = new DataBaseHelper(mContext, "database", null, 1);

        SharedPreferences prefs = mContext.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firststart = prefs.getBoolean("firststart", true);
        if (firststart) {
            createTableOnFirstStart();
        }

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.fav_item, parent, false);

        FavAdapter.ViewHolder viewHolder = new FavAdapter.ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull FavAdapter.ViewHolder holder, final int position) {


        ModelCar carItem = new ModelCar(mlist.get(position).getImage(), mlist.get(position).getName(), mlist.get(position).getModel(), mlist.get(position).getPrice(),
                mlist.get(position).getManufacturer(), mlist.get(position).getDistance(), mlist.get(position).getAccident(), mlist.get(position).getOffer(), mlist.get(position).getYear(), mlist.get(position).getFavstatus());

        ImageView image = holder.itemImage;
        final ImageView favimg = holder.itemimagefav;
        final String[] retdate = new String[1];
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
        name.setText("In Gallary");
        model.setText(carItem.getModel() + ", " + carItem.getYear());
        price.setText("₪" +carItem.getPrice());
        manufacturer.setText(carItem.getManufacturer());
        distance.setText(carItem.getDistance());
        accident.setText("Accidents: "+carItem.getAccident());
        offer.setText("Offers: "+carItem.getOffer());

        if (dataBaseHelper.checkCarIsReserved(mlist.get(position).getName())) {
            name.setText("Reserved");//id
            book.setVisibility(View.INVISIBLE);
        }


        favimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id;
                id = mlist.get(position).getName();

                dataBaseHelper.updateCarStatusInDatabase(id, "0");
                dataBaseHelper.removeFavFromDatabase(id);
                mlist.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mlist.size());
                Toast.makeText(mContext, "Removed From Favourites", Toast.LENGTH_SHORT).show();
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

                                final BottomSheetDialog bottomSheetDialog2 = new BottomSheetDialog(mContext, R.style.BottomSheetDialogTheme
                                );
                                View bottomSheetView2 = LayoutInflater.from(mContext)
                                        .inflate(
                                                R.layout.layot_bottom_sheet_reserve, bottomsheetreserve);

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


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage, itemimagefav;
        TextView item_name, item_model, item_price, item_manufacturer, item_accident, item_offer, item_distance;
        Button item_book;
        LinearLayout bottomsheetreserve;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.fav_item_img);
            itemimagefav = itemView.findViewById(R.id.fav_item_fav);

            item_name = itemView.findViewById(R.id.fav_item_name);
            item_model = itemView.findViewById(R.id.fav_item_model);
            item_price = itemView.findViewById(R.id.fav_item_price);
            item_manufacturer = itemView.findViewById(R.id.fav_item_manufacturer);
            item_accident = itemView.findViewById(R.id.fav_item_accident);
            item_offer = itemView.findViewById(R.id.fav_item_offer);
            item_distance = itemView.findViewById(R.id.fav_item_distance);

            item_book = itemView.findViewById(R.id.fav_item_book);
            bottomsheetreserve = itemView.findViewById(R.id.bottomSheetReserveContainer);


        }
    }

    private void createTableOnFirstStart() {

        dataBaseHelper.insertEmptyFavTable();

        SharedPreferences prefs = mContext.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firststart", false);

    }


}
