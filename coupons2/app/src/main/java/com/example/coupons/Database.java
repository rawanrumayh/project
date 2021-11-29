package com.example.coupons;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database  extends SQLiteOpenHelper {
  static final String DBname ="Database";
  static final int DBversion=1;

    static final String ChallengesTable="Challenges";
    static final String colChallengeID="ChallengeID";
    static final String colChallengeQuestion="Question";
    static final String colChallengeAnswer="Answer";
    static final String colChallengeCoupon="Coupon";
    static final String colChallengeCouponPercentage="Percentage";
    static final String colOwnerID="Owner"; //reference


    public  Database(Context context) {
        super(context, DBname, null, DBversion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ ChallengesTable + " (" + colChallengeID + "  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , " + colChallengeQuestion + " TEXT, " + colChallengeAnswer + " TEXT, " + colChallengeCoupon + " TEXT, " + colOwnerID + " INTEGER, " + colChallengeCouponPercentage + " INTEGER);");
    }

}
