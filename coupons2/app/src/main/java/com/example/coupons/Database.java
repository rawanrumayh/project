package com.example.coupons;

import android.content.Context;
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

}
