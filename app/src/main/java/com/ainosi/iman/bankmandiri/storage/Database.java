package com.ainosi.iman.bankmandiri.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ainosi.iman.bankmandiri.model.FinancialPlannerModel;
import com.ainosi.iman.bankmandiri.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private final String TAG = "Database";
    private static String DATABASE_NAME = "mandiri.db";

    //TABLE NAME
    private static final String TABLE_USER = "user";
    private static final String TABLE_FINANCIAL_PLANNER = "financial_planner";

    //USER TABLE
    private static final String CIFNUMBER = "cifnumber";
    private static final String ACCOUNTNUMBER = "accountnumber";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String BALANCE = "balance";
    private static final String SET_A = "set_a";
    private static final String SET_B = "set_b";
    private static final String SET_C = "set_c";

    //FINANCIAL PLANNER
    private static final String ID = "id";
    private static final String ACCOUNTNUMBER_ = "accountnumber";
    private static final String DATE = "date_";
    private static final String SAVING_A = "saving_a";
    private static final String SAVING_B = "saving_b";
    private static final String SAVING_C = "saving_c";
    private static final String TOTAL = "total";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USER = "CREATE TABLE "+TABLE_USER+" ("+CIFNUMBER+" TEXT, "+ACCOUNTNUMBER+" TEXT, "+USERNAME
                +" TEXT, "+PASSWORD+" TEXT, "+NAME+" TEXT, "+BALANCE+" TEXT, "+SET_A+" INT, "+SET_B+" INT, "+SET_C+" INT)";

        String CREATE_TABLE_FINANCIAL_PLANNER = "CREATE TABLE "+TABLE_FINANCIAL_PLANNER+" ("+ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +ACCOUNTNUMBER_+" TEXT, "+DATE+" TEXT, "+SAVING_A
                +" TEXT, "+SAVING_B+" TEXT, "+SAVING_C+" TEXT, "+TOTAL+" TEXT)";

        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_FINANCIAL_PLANNER);
    }

    public int updateUser(UserModel user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BALANCE, user.getBalance());

        // updating row
        return db.update(TABLE_USER, values, ACCOUNTNUMBER + " = ?", new String[]{user.getAccountnumber()});
    }

    public UserModel selectUser(String accountnumber){
        UserModel user = new UserModel();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        String sqlQuery = "SELECT * FROM " + TABLE_USER +" WHERE "+ ACCOUNTNUMBER +"='" + accountnumber+"'";
        cursor = db.rawQuery(sqlQuery, null);
        if (cursor.moveToFirst()) {
            user.setCifnumber(cursor.getString(0));
            user.setAccountnumber(cursor.getString(1));
            user.setUsername(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            user.setName(cursor.getString(4));
            user.setBalance(cursor.getString(5));
            user.setSet_a(cursor.getInt(6));
            user.setSet_b(cursor.getInt(7));
            user.setSet_c(cursor.getInt(8));
        }
        Log.e(TAG, "selectuser: " + user.getAccountnumber() + " ||| " + user.getName());
        cursor.close();
        return user;
    }

    public UserModel selectUser(String username, String password){
        UserModel user = new UserModel();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        String sqlQuery = "SELECT * FROM " + TABLE_USER +" WHERE "+ USERNAME +"='" + username + "' AND "+PASSWORD+"='"+password+"'";
        cursor = db.rawQuery(sqlQuery, null);
        if (cursor.moveToFirst()) {
            user.setCifnumber(cursor.getString(0));
            user.setAccountnumber(cursor.getString(1));
            user.setUsername(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            user.setName(cursor.getString(4));
            user.setBalance(cursor.getString(5));
            user.setSet_a(cursor.getInt(6));
            user.setSet_b(cursor.getInt(7));
            user.setSet_c(cursor.getInt(8));
        }
        Log.e(TAG, "selectuser: " + user.getAccountnumber() + " ||| " + user.getName());
        cursor.close();
        return user;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FINANCIAL_PLANNER);

        onCreate(db);
    }



    public void insertUser(UserModel user) {
        ContentValues values = new ContentValues();
        values.put(CIFNUMBER, user.getCifnumber()); // id
        values.put(ACCOUNTNUMBER, user.getAccountnumber()); // mid
        values.put(USERNAME, user.getUsername()); // tid
        values.put(PASSWORD, user.getPassword()); // amount
        values.put(NAME, user.getName()); // date
        values.put(BALANCE, user.getBalance());
        values.put(SET_A, user.getSet_a());
        values.put(SET_B, user.getSet_b());
        values.put(SET_C, user.getSet_c());

        // Inserting Row
        SQLiteDatabase database = this.getWritableDatabase();
        database.insertWithOnConflict(TABLE_USER, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        database.close(); // Closing database connection
    }

    public List<FinancialPlannerModel> selectFinancialPlanner(){
        List<FinancialPlannerModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlQuery = "SELECT * FROM " + TABLE_FINANCIAL_PLANNER;
        Cursor cursor = db.rawQuery(sqlQuery, null);
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                FinancialPlannerModel fpm = new FinancialPlannerModel();
                Log.e(TAG, "selectFinancialPlanner: " + ++i);
                fpm.setAccountnumber(cursor.getString(1));
                fpm.setDate(cursor.getString(2));
                fpm.setSaving_a(cursor.getString(3));
                fpm.setSaving_b(cursor.getString(4));
                fpm.setSaving_c(cursor.getString(5));
                fpm.setTotal(cursor.getString(6));
                list.add(fpm);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void insertFinancialPlanner(FinancialPlannerModel fpm){
        ContentValues values = new ContentValues();
        values.put(ACCOUNTNUMBER_, fpm.getAccountnumber()); // mid
        values.put(DATE, fpm.getDate());
        values.put(SAVING_A, fpm.getSaving_a()); // tid
        values.put(SAVING_B, fpm.getSaving_b()); // amount
        values.put(SAVING_C, fpm.getSaving_c()); // date
        values.put(TOTAL, fpm.getTotal()); // date

        // Inserting Row
        SQLiteDatabase database = this.getWritableDatabase();
        database.insertWithOnConflict(TABLE_FINANCIAL_PLANNER, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        database.close(); // Closing database connection
    }

}
