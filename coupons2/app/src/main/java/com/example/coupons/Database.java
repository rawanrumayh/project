package com.example.coupons;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import com.example.coupons.globals.BaseClass;
import com.example.coupons.model.challenge_model;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    static final String DBname = "Database";
    static final int DBversion = 4;
    static String currentUser;

    public static final String ChallengesTable = "Challenges";
    public static final String colChallengeID = "ChallengeID";
    public static final String colChallengeQuestion = "Question";
    public static final String colChallengeAnswer = "Answer";
    public static final String colChallengeCoupon = "Coupon";
    public static final String colChallengeCouponPercentage = "Percentage";
    public static final String colOwnerID = "Owner";
    public static final String colOwnerLng = "Latitude";
    public static final String colOwnerLat = "Longitude"; //reference//reference
//reference


    public Database(Context context) {
        super(context, DBname, null, DBversion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");
        db.execSQL("DROP TABLE IF EXISTS Challenges");
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table users(username TEXT primary key, password Text, name TEXT, type TEXT)");
        db.execSQL("CREATE TABLE " + ChallengesTable + " (" + colChallengeID + "  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , " + colChallengeQuestion + " TEXT, " + colChallengeAnswer + " TEXT, " + colChallengeCoupon + " TEXT, " + colOwnerID + " TEXT, " + colChallengeCouponPercentage + " INTEGER, " + colOwnerLng + " REAL, " + colOwnerLat + " REAL);");
    }

    public String getChallengeQuestion(String id){
        String quest="";
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.query(Database.ChallengesTable, new String[]{Database.colChallengeQuestion},
                "ChallengeID=?", new String[]{id}, null, null, null); //selection

        // intent id
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String question = cursor.getString(cursor.getColumnIndex(Database.colChallengeQuestion));
                quest= question;
            }
        }
        cursor.close();
        return quest;
    }

    public String getChallengeAnswer(String id){
        String value="";
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.query(Database.ChallengesTable, new String[]{Database.colChallengeAnswer},
                "ChallengeID=?", new String[]{id}, null, null, null); //selection

        // intent id
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String answer = cursor.getString(cursor.getColumnIndex(Database.colChallengeAnswer));
                value= answer;
            }
        }
        cursor.close();
        return value;
    }

    public String getChallengeCoupon(String id){
        String value="";
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.query(Database.ChallengesTable, new String[]{Database.colChallengeCoupon},
                "ChallengeID=?", new String[]{id}, null, null, null); //selection

        // intent id
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String coupon = cursor.getString(cursor.getColumnIndex(Database.colChallengeCoupon));
                value= coupon;
            }
        }
        cursor.close();
        return value;
    }

    public double getChallengeLat(String id){
        double value=0;
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.query(Database.ChallengesTable, new String[]{Database.colOwnerLat},
                "ChallengeID=?", new String[]{id}, null, null, null); //selection

        // intent id
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") double lat = cursor.getDouble(cursor.getColumnIndex(Database.colOwnerLat));
                value= lat;
            }
        }
        cursor.close();
        return value;
    }


    public String getChallengeAddress(String id){
        String value="";
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.query(Database.ChallengesTable, new String[]{Database.colOwnerLat},
                "ChallengeID=?", new String[]{id}, null, null, null); //selection

        // intent id
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String lat = cursor.getString(cursor.getColumnIndex(Database.colOwnerLat));
                value= lat;
            }
        }
        cursor.close();
        return value;
    }

    public double getChallengeLng(String id){
        double value=0;
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.query(Database.ChallengesTable, new String[]{Database.colOwnerLng},
                "ChallengeID=?", new String[]{id}, null, null, null); //selection

        // intent id
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") double lat = cursor.getDouble(cursor.getColumnIndex(Database.colOwnerLng));
                value= lat;
            }
        }
        cursor.close();
        return value;
    }

    public String getChallengeCouponP(String id){
        String value="";
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.query(Database.ChallengesTable, new String[]{Database.colChallengeCouponPercentage},
                "ChallengeID=?", new String[]{id}, null, null, null); //selection

        // intent id
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String percent = cursor.getString(cursor.getColumnIndex(Database.colChallengeCouponPercentage));
                value= percent;
            }
        }
        cursor.close();
        return value;
    }

    public String getChallengeOwner(String id){
        String value="";
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.query(Database.ChallengesTable, new String[]{Database.colOwnerID},
                "ChallengeID=?", new String[]{id}, null, null, null); //selection

        // intent id
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String owner = cursor.getString(cursor.getColumnIndex(Database.colOwnerID));
                value= owner;
            }
        }
        cursor.close();
        return value;
    }

    public Boolean register(String username, String password, String name , String type){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username" , username);
        contentValues.put("password" , password);
        contentValues.put("name" , name);
        contentValues.put("type" , type);
        long result = MyDB.insert("users",null,contentValues);

        if (result==-1){
            return false;
        }
        else{
            currentUser = username;
            return true;
        }

    }
    public Boolean checkUsername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();

        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[] {username});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }
    public Boolean checkUsernamePassword(String username , String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();

        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0){
            currentUser = username;
            return true;
        }
        else{
            return false;
        }
    }
    public String getUserType(String username) {

        SQLiteDatabase MyDB = this.getReadableDatabase();

        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[] {username});
        cursor.moveToFirst();
        String type = cursor.getString(3);

        cursor.close();
        return type;
    }

    public String getCurrentUser(){
        return currentUser;
    }

    public ArrayList<challenge_model> getNearestChallenge(Location userLocation) {

        String lat= ""+BaseClass.my_lat;
        String lng= ""+BaseClass.my_lng;

        ArrayList<challenge_model> challenges = new ArrayList<>();

        Cursor cursor = readAllData();
        if (cursor.getCount() == 0) {
            return challenges;

        } else {

            while (cursor.moveToNext()) {

                if (cursor.getString(3) != null) {
                    if (lat.equals(""+cursor.getFloat(7)) && lng.equals(""+cursor.getFloat(6))) {
                        challenge_model challenge = new challenge_model();
                        challenge.setId(Integer.parseInt(cursor.getString(0)));
                        challenge.setAnswer(cursor.getString(2));
                        challenge.setQuestion(cursor.getString(1));

                        challenges.add(challenge);
                    }
                }
            }
        }

        return challenges;
    }

    public Cursor readAllData() {


        // String query = "SELECT * FROM poll where Usernamee=?\",new String[]{UserName}";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {

            cursor = db.rawQuery("Select * from Challenges ", null);

        }
        return cursor;
    }


}
