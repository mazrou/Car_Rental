package com.example.a1171832jumanafinalproj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataBaseHelper extends android.database.sqlite.SQLiteOpenHelper {


    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE USERS(EMAIL TEXT PRIMARY KEY  NOT NULL,NAME TEXT, PHONE TEXT,GENDER TEXT,PASSWORD TEXT,COUNTRY TEXT,CITY TEXT , IS_ADMIN INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE FAVOURITES(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,MANUFACTURER TEXT, MODEL TEXT,YEAR TEXT,DISTANCE TEXT,ACCIDENTS TEXT,OFFERS TEXT,FAVSTATUS TEXT,IMAGE TEXT,PRICE TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE CARS(id INTEGER PRIMARY KEY NOT NULL,MANUFACTURER TEXT, MODEL TEXT,YEAR TEXT,DISTANCE TEXT,ACCIDENTS TEXT,OFFERS TEXT,FAVSTATUS TEXT,IMAGE TEXT,PRICE TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE RESERVATIONS(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,CARID TEXT, RESERVATIONDATE TEXT,RETURNDATE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    public void insertEmptyFavTable() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 1; i < 26; i++) {
            contentValues.put("FAVSTATUS", "0");
            sqLiteDatabase.insert("FAVOURITES", null, contentValues);
        }
    }

    public void insertFavEntry(String id, String manufacturer, int image_id, String model, String year, String distance, String accidents, String offers, String fav_status, String price) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", id);
        contentValues.put("MANUFACTURER", manufacturer);
        contentValues.put("MODEL", model);
        contentValues.put("YEAR", year);
        contentValues.put("DISTANCE", distance);
        contentValues.put("ACCIDENTS", accidents);
        contentValues.put("OFFERS", offers);
        contentValues.put("FAVSTATUS", fav_status);
        contentValues.put("IMAGE", image_id);
        contentValues.put("PRICE", price);

        sqLiteDatabase.insert("FAVOURITES", null, contentValues);
        Log.d("FavDB Status", manufacturer + ", favstatus -" + fav_status + " - " + contentValues); // might cause something check
    }

    public Cursor getAllFAV(String id) {
        String[] selectionArgs = {id};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM FAVOURITES where id=?", selectionArgs);
    }

    public void removeFavFromDatabase(String id) {
        String[] selectionArgs = {id};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        sqLiteDatabase.delete("FAVOURITES", "id=?", selectionArgs);
        Log.d("remove", id.toString());
        // UPDATE FAVOURITES SET FAVSTATUS="0" where id=? , argument array or delete
    }

    public Cursor getAllMarkedAsFAV() {
        String[] selectionArgs = {"1"};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM FAVOURITES where FAVSTATUS=?", selectionArgs);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------
    public void insertUser(User user) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", user.getEmail());
        contentValues.put("NAME", user.getName());
        contentValues.put("PHONE", user.getPhone());
        contentValues.put("GENDER", user.getGender());
        contentValues.put("COUNTRY", user.getCountry());
        contentValues.put("CITY", user.getCity());
        contentValues.put("PASSWORD", user.getPassword());
        contentValues.put("IS_ADMIN", user.isAdmin()? 1 : 0 );
        sqLiteDatabase.insert("USERS", null, contentValues);
    }


    public void updateUser(String email, String name ,String phone, String password) {
        String[] selectionArgs = {email};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("NAME", name);
        contentValues.put("PHONE", phone);
        contentValues.put("PASSWORD", password);

        sqLiteDatabase.update("USERS", contentValues, "EMAIL=?", selectionArgs);
        Log.d("updateduser------->", email.toString());
        // UPDATE FAVOURITES SET FAVSTATUS="0" where id=? , argument array
    }

    public void removeUser(User user ) {
        String[] selectionArgs = {user.getEmail()};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("USERS", "EMAIL=?", selectionArgs);
    }

    public Cursor getAllUsers() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM USERS", null);
    }

    public Cursor getUser(String email) {
        String[] selectionArgs = {email};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM USERS where EMAIL=?", selectionArgs);
    }

    public boolean checkUserExist(String email, String password) {
        String[] selectionArgs = {email, password};

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM USERS WHERE EMAIL=? and PASSWORD = ?", selectionArgs);
        int count = cursor.getCount();
        cursor.close();
        close();

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkUserIsAdmin (String email , String password ){
        String[] selectionArgs = {email, password};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM USERS WHERE EMAIL=? and PASSWORD = ?", selectionArgs);
        if (cursor.getCount() < 1){
            return  false ;
        }
        cursor.moveToNext() ;
        int isAdmin =   cursor.getInt(cursor.getColumnIndexOrThrow("IS_ADMIN")) ;
        cursor.close();
        close();
        return isAdmin > 0;
    }

    public boolean EmailExist(String email) {
        String[] selectionArgs = {email};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM USERS WHERE EMAIL=?", selectionArgs);
        int count = cursor.getCount();
        cursor.close();
        close();
        return count > 0;
    }

    //------------------------------------------------------------------------------------------------------------------------------
    public void insertEmptyCarTable() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 1; i < 26; i++) {
            contentValues.put("FAVSTATUS", "0");
            sqLiteDatabase.insert("CARS", null, contentValues);
        }
    }

    public void insertCarEntry(String manufacturer, int image_id, String model, String year, String distance, String accidents, String offers, String fav_status, String price) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("MANUFACTURER", manufacturer);
        contentValues.put("MODEL", model);
        contentValues.put("YEAR", year);
        contentValues.put("DISTANCE", distance);
        contentValues.put("ACCIDENTS", accidents);
        contentValues.put("OFFERS", offers);
        contentValues.put("FAVSTATUS", fav_status);
        contentValues.put("IMAGE", image_id);
        contentValues.put("PRICE", price);

        sqLiteDatabase.insert("CARS", null, contentValues);
        Log.d("CarDB Status", manufacturer + ", favstatus -" + fav_status + " - " + contentValues); // might cause something check
    }

    public Cursor getAllCars() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM CARS", null);
    }

    public Cursor getCarGivenId(String id) {
        String[] selectionArgs = {id};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM CARS WHERE id=?", selectionArgs);
    }


    public void updateCarStatusInDatabase(String id, String status) {
        String[] selectionArgs = {id};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FAVSTATUS", status);

        sqLiteDatabase.update("CARS", contentValues, "id=" + id, null);
        Log.d("updatedcar", id.toString());
        // UPDATE FAVOURITES SET FAVSTATUS="0" where id=? , argument array
    }


    public boolean checkCarIsFav(String id) {
        String[] selectionArgs = {id, "1"};

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM CARS WHERE id=? and FAVSTATUS=?", selectionArgs);
        int count = cursor.getCount();
        cursor.close();
        close();

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }


//--------------------------------------------------------------------------------------------------------

    public void insertReservationEntry(String carid, String reservationdate, String returndate) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put("CARID", carid);
        contentValues.put("RESERVATIONDATE", reservationdate);
        contentValues.put("RETURNDATE", returndate);


        sqLiteDatabase.insert("RESERVATIONS", null, contentValues);
        Log.d("ReservationDB ", carid + " - " + contentValues); // might cause something check
    }

    public Cursor getAllReservations() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM RESERVATIONS", null);
    }

    public void removeReservationFromDatabase(String id) {
        String[] selectionArgs = {id};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        sqLiteDatabase.delete("RESERVATIONS", "id=?", selectionArgs);
        Log.d("remove reservation", id.toString());
        // UPDATE FAVOURITES SET FAVSTATUS="0" where id=? , argument array or delete
    }

    public boolean checkCarIsReserved(String carid) {
        String[] selectionArgs = {carid};

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM RESERVATIONS WHERE CARID=?", selectionArgs);
        int count = cursor.getCount();
        cursor.close();
        close();

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }







}
