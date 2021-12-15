package com.example.coupons;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.coupons.globals.BaseClass;

public class Database extends SQLiteOpenHelper {
    static final String DBname = "Database";
    static final int DBversion = 3;

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
        db.execSQL("DROP TABLE IF EXISTS Challenges");
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ChallengesTable + " (" + colChallengeID + "  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , " + colChallengeQuestion + " TEXT, " + colChallengeAnswer + " TEXT, " + colChallengeCoupon + " TEXT, " + colOwnerID + " INTEGER, " + colChallengeCouponPercentage + " INTEGER, " + colOwnerLng + " REAL, " + colOwnerLat + " REAL);");
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

}
