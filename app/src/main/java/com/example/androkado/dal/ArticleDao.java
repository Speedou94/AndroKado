package com.example.androkado.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.androkado.bo.Article;
import com.example.androkado.contract.ArticleContract;

import java.util.ArrayList;
import java.util.List;

public class ArticleDao {
    private SQLiteDatabase db;
    private BddHelper helper;

    public ArticleDao(Context context)
    {
        helper = new BddHelper(context);
        db = helper.getWritableDatabase();
    }

    public long insert(Article article)
    {
        ContentValues values = new ContentValues();
        values.put(ArticleContract.COL_NOM,article.getNom());
        values.put(ArticleContract.COL_PRIX,article.getPrix());
        values.put(ArticleContract.COL_DESCRIPTION,article.getDescription());
        values.put(ArticleContract.COL_NOTE,article.getNote());
        values.put(ArticleContract.COL_URL,article.getUrl());
        values.put(ArticleContract.COL_ETAT,article.getEtat());

        return db.insert(ArticleContract.TABLE_NAME,null,values);
    }

    public Article get(long id)
    {
        Article article = null;

        Cursor cursor = db.query(
                ArticleContract.TABLE_NAME,
                null,
                ArticleContract.COL_ID + " =?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        if(cursor.moveToNext())
        {
            article = new Article();
            article.setId(cursor.getInt(cursor.getColumnIndex(ArticleContract.COL_ID.trim())));
            article.setNom(cursor.getString(cursor.getColumnIndex(ArticleContract.COL_NOM.trim())));
            article.setPrix(cursor.getDouble(cursor.getColumnIndex(ArticleContract.COL_PRIX.trim())));
            article.setDescription(cursor.getString(cursor.getColumnIndex(ArticleContract.COL_DESCRIPTION.trim())));
            article.setNote(cursor.getFloat(cursor.getColumnIndex(ArticleContract.COL_NOTE.trim())));
            article.setUrl(cursor.getString(cursor.getColumnIndex(ArticleContract.COL_URL.trim())));
            article.setEtat(cursor.getInt(cursor.getColumnIndex(ArticleContract.COL_ETAT.trim())) > 0);
        }
        return article;
    }

    public List<Article> get(boolean isTrie)
    {
        List<Article> resultat = new ArrayList<>();

        Cursor cursor = db.query(
                ArticleContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                isTrie ? ArticleContract.COL_PRIX : null);

        while(cursor.moveToNext())
        {
            Article article = new Article();
            article.setId(cursor.getInt(cursor.getColumnIndex(ArticleContract.COL_ID.trim())));
            article.setNom(cursor.getString(cursor.getColumnIndex(ArticleContract.COL_NOM.trim())));
            article.setPrix(cursor.getDouble(cursor.getColumnIndex(ArticleContract.COL_PRIX.trim())));
            article.setDescription(cursor.getString(cursor.getColumnIndex(ArticleContract.COL_DESCRIPTION.trim())));
            article.setNote(cursor.getFloat(cursor.getColumnIndex(ArticleContract.COL_NOTE.trim())));
            article.setUrl(cursor.getString(cursor.getColumnIndex(ArticleContract.COL_URL.trim())));
            article.setEtat(cursor.getInt(cursor.getColumnIndex(ArticleContract.COL_ETAT.trim())) > 0);
            resultat.add(article);
        }
        return resultat;
    }

    public boolean update(Article article)
    {
        ContentValues cv = new ContentValues();
        cv.put(ArticleContract.COL_NOM,article.getNom());
        cv.put(ArticleContract.COL_PRIX,article.getPrix());
        cv.put(ArticleContract.COL_DESCRIPTION,article.getDescription());
        cv.put(ArticleContract.COL_NOTE,article.getNote());
        cv.put(ArticleContract.COL_URL,article.getUrl());
        cv.put(ArticleContract.COL_ETAT,article.getEtat());

        return db.update(
                ArticleContract.TABLE_NAME,
                cv,
                ArticleContract.COL_ID + "=?",
                new String[]{String.valueOf(article.getId())})
                >0;
    }

    public boolean delete(Article article)
    {
        return db.delete(
                ArticleContract.TABLE_NAME,
                ArticleContract.COL_ID + " =?",
                new String[]{String.valueOf(article.getId())})
                >0;
    }

}
