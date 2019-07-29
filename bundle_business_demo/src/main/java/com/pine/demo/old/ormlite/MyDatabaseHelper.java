package com.pine.demo.old.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class MyDatabaseHelper extends OrmLiteSqliteOpenHelper {
    private final static String TAG = "MyDatabaseHelper";

    private final static String DATABASE_NAME = "my_database.db";

    private static MyDatabaseHelper mInstance;

    private MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static synchronized MyDatabaseHelper getMyDatabaseHelper(Context context) {
        if (mInstance == null) {
            mInstance = new MyDatabaseHelper(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, PersonEntity.class);
            TableUtils.createTable(connectionSource, ArticleEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, PersonEntity.class, true);
            TableUtils.dropTable(connectionSource, ArticleEntity.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Dao<PersonEntity, Integer> mPersonDao;
    private Dao<ArticleEntity, Integer> mArticleDao;

    public Dao<PersonEntity, Integer> getPersonDaoOpe() throws SQLException {
        if (mPersonDao == null) {
            mPersonDao = getDao(PersonEntity.class);
        }
        return mPersonDao;
    }

    public Dao<ArticleEntity, Integer> getArticleDaoOpe() throws SQLException {
        if (mArticleDao == null) {
            mArticleDao = getDao(ArticleEntity.class);
        }
        return mArticleDao;
    }

    @Override
    public void close() {
        super.close();
        mPersonDao = null;
        mArticleDao = null;
    }
}
