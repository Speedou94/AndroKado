package com.example.androkado.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.androkado.contract.ArticleContract;

public class BddHelper extends SQLiteOpenHelper {
    private final static int VERSION = 1;
    private final static String BDD_NAME = "androkado.db";

    public BddHelper(Context context)
    {
        super(context, BDD_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(ArticleContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL(ArticleContract.DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

}
