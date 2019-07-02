package com.example.uipl.sqlitedemoapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class HelperClass extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "registration_database";
    public static final String TABLE_NAME ="registration";
    public static final String COL_1 = "USERNAME";
    public static final String COL_2 = "EMAIL_ID";
    public static final String COL_3 = "PHONE_NUMBER";
    public static final String COL_4 ="ADDRESS";
    public static final String COL_5 = "PASSWORD";
    public static final String COL_6 = "CONFIRM_PASSWORD";
    public static final String COL_7 = "ID";

    public HelperClass(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME + "(USERNAME TEXT,EMAIL_ID TEXT PRIMARY KEY,PHONE_NUMBER INTEGER,ADDRESS TEXT,PASSWORD TEXT,CONFIRM_PASSWORD TEXT)" );
    }
   /* @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // If you need to add a column
        if (newVersion > oldVersion) {
            db.execSQL("ALTER TABLE registration ADD COLUMN  ID INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 0");
        }
    }*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String uName,String eMail,
                              String pNumber,String uAddress,
                              String pass,String con_pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,uName);
        contentValues.put(COL_2,eMail);
        contentValues.put(COL_3,pNumber);
        contentValues.put(COL_4,uAddress);
        contentValues.put(COL_5,pass);
        contentValues.put(COL_6,con_pass);
        Long result = db.insert(TABLE_NAME,null,contentValues);
        if(result ==-1) {
            return false;
        }else
            return true;
    }
    // checking email if exist
    public  boolean checkEmail(String eMail) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from registration where EMAIL_ID=?",new String[] {eMail});
        if (cursor.getCount()>0) {
            return false;
        }else {
            return true;
        }
    }
// checking the email and password
    public boolean chechEmailPAssword(String username,String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from registration where EMAIL_ID=? and PASSWORD=?",new String[] {username,password});
        if (cursor.getCount()>0) {
            return true;
        }else {
            return false;
        }
    }
    // update the user profile
    public boolean updateData(String uName, String eMail,
                              String pNumber, String uAddress) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,uName);
        contentValues.put(COL_2,eMail);
        contentValues.put(COL_3,pNumber);
        contentValues.put(COL_4,uAddress);
        db.update(TABLE_NAME,contentValues,"EMAIL_ID=?",new String[]{eMail});
        return true;
    }
   /* public boolean getUserDetailByMailId(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from registration where EMAIL_ID=?",new String[]{email});
        if (cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }*/

   // to get the User Detail
    public UserModel getUserDetailByMailId(String email_id ) {

        UserModel detail = new UserModel();
        SQLiteDatabase db = this.getReadableDatabase();
        //specify the columns to be fetched
        String[] columns = {COL_1, COL_2, COL_3, COL_4};

        //Select condition
        String selection = COL_2 + " = ?";           // col 2 means emailId
        //Arguments for selection
        String[] selectionArgs = {String.valueOf(email_id)};

        Cursor cursor = db.query(TABLE_NAME, columns, selection,
                selectionArgs, null, null, null);
        if (null != cursor) {
            cursor.moveToFirst();
            detail.setUsername(cursor.getString(0));
            detail.setEmailId(cursor.getString(1));
            detail.setPhoneNumber(cursor.getString(2));
            detail.setAddress(cursor.getString(3));
        }
        db.close();
        return detail;
    }
}
