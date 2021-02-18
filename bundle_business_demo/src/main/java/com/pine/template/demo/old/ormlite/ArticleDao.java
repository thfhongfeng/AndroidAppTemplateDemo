package com.pine.template.demo.old.ormlite;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by tanghongfeng on 2017/8/10.
 */

public class ArticleDao {
    private MyDatabaseHelper mMyDataHelper;
    private Dao<ArticleEntity, Integer> mArticleDaoOpe;
    private PersonDao mPersonDao;

    public ArticleDao(Context context) {
        try {
            mMyDataHelper = MyDatabaseHelper.getMyDatabaseHelper(context);
            mArticleDaoOpe = mMyDataHelper.getArticleDaoOpe();
            mPersonDao = new PersonDao(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String addArticle(String title, String authorId) {
        String consoleOut = "Add Article:\n";
        ArticleEntity articleEntity = new ArticleEntity();
        if (title.isEmpty()) {
            consoleOut += " - Fail: title should not be null!\n";
            return consoleOut;
        }
        articleEntity.setTitle(title);
        if (!authorId.isEmpty() && TextUtils.isDigitsOnly(authorId)) {
            PersonEntity Author = null;
            try {
                Author = mPersonDao.queryForId(Integer.parseInt(authorId));
            } catch (SQLException e) {
                consoleOut += " - Exception: " + e.toString() + "\n";
                return consoleOut;
            }
            if (Author == null) {
                consoleOut += " set author null(the author is not exist)\n";
            }
            articleEntity.setAuthor(Author);
        }
        try {
            int count = create(articleEntity);
            if (count < 1) {
                consoleOut += " - Success: but no row was added!\n";
            } else {
                consoleOut += " - Success: " + count + " row was added, article:[" + articleEntity.toString() + "]\n";
            }
        } catch (SQLException e) {
            consoleOut += " - Exception: " + e.toString() + "\n";
            return consoleOut;
        }
        return consoleOut;
    }

    public String queryArticle(String id, String title, String authorId) {
        String consoleOut = "Query Article:\n";
        if (!id.isEmpty() && TextUtils.isDigitsOnly(id)) {
            consoleOut += queryArticleById(Integer.parseInt(id));
        } else {
            consoleOut += queryArticleBuilder(title, authorId);
        }
        return consoleOut;
    }

    public String queryArticleById(int id) {
        String consoleOut = "";
        ArticleEntity articleEntity = null;
        try {
            articleEntity = queryForId(id, true);
        } catch (SQLException e) {
            consoleOut += " - Exception: " + e.toString() + "\n";
            return consoleOut;
        }
        if (articleEntity == null) {
            consoleOut += " - Success: query Article by id: " + id + ", there is no such article!\n";
            return consoleOut;
        }
        consoleOut += " - Success: query Article by id: " + id + " \n";
        consoleOut += " " + articleEntity.toString() + "\n";
        return consoleOut;
    }

    public String queryArticleBuilder(String title, String authorId) {
        String consoleOut = "";
        List<ArticleEntity> articleEntityList = null;
        Map<String, String> queryWhere = new HashMap<String, String>();
        if (!title.isEmpty()) {
            queryWhere.put("title", title);
        }
        if (!authorId.isEmpty() && TextUtils.isDigitsOnly(authorId)) {
            queryWhere.put("author_id", authorId);
        }
        try {
            articleEntityList = queryBuilder(queryWhere, true);
        } catch (SQLException e) {
            consoleOut += " - Exception: " + e.toString() + "\n";
            return consoleOut;
        }
        if (articleEntityList == null || articleEntityList.size() < 1) {
            consoleOut += " - Success: no article matched!\n";
            return consoleOut;
        }
        consoleOut += " - Success: there " + articleEntityList.size() + " row matched\n";
        for (ArticleEntity articleEntity : articleEntityList) {
            consoleOut += " " + articleEntity.toString() + "\n";
        }
        return consoleOut;
    }

    public String updateArticle(String id, String title, String authorId) {
        String consoleOut = "Update Article:\n";
        ArticleEntity articleEntity = null;
        if (!id.isEmpty() && TextUtils.isDigitsOnly(id)) {
            try {
                articleEntity = queryForId(Integer.parseInt(id), true);
            } catch (SQLException e) {
                consoleOut += " - Exception: " + e.toString() + "\n";
                return consoleOut;
            }
            if (articleEntity == null) {
                consoleOut += " - Fail: no article changed because no article matched!\n";
                return consoleOut;
            }
            int changeCount = 0;
            if (!title.isEmpty()) {
                articleEntity.setTitle(title);
                changeCount++;
            }
            if (!authorId.isEmpty() && TextUtils.isDigitsOnly(authorId)) {
                PersonEntity Author = null;
                try {
                    Author = mPersonDao.queryForId(Integer.parseInt(authorId));
                } catch (SQLException e) {
                    consoleOut += " - Exception: " + e.toString() + "\n";
                    return consoleOut;
                }
                if (Author == null) {
                    consoleOut += " set author null(the author is not exist)\n";
                }
                articleEntity.setAuthor(Author);
                changeCount++;
            }
            if (changeCount < 1) {
                consoleOut += " - Fail: UPDATE statements must have at least one SET column\n";
                return consoleOut;
            }
        } else {
            consoleOut += " - Fail: id must be correct\n";
            return consoleOut;
        }
        int count;
        try {
            count = update(articleEntity);
        } catch (SQLException e) {
            consoleOut += " - Exception: " + e.toString() + "\n";
            return consoleOut;
        }
        if (count > 0) {
            consoleOut += " - Success: there " + count + " row update\n";
            consoleOut += " " + articleEntity.toString() + "\n";
        }
        return consoleOut;
    }

    public String updateArticleBuilder(String id, String title, String authorId) {
        String consoleOut = "Update Article:\n";
        Map<String, String> queryWhere = new HashMap<String, String>();
        ContentValues updateValues = new ContentValues();
        boolean idExist = false;
        if (!id.isEmpty() && TextUtils.isDigitsOnly(id)) {
            idExist = true;
            queryWhere.put("id", id);
        }
        if (!title.isEmpty()) {
            updateValues.put("title", title);
        }
        if (!authorId.isEmpty() && TextUtils.isDigitsOnly(authorId)) {
            updateValues.put("author_id", authorId);
        }
        if (updateValues.size() < 1) {
            consoleOut += " - Fail: UPDATE statements must have at least one SET column\n";
            return consoleOut;
        }
        int count;
        try {
            count = updateBuilder(queryWhere, updateValues);
        } catch (SQLException e) {
            consoleOut += " - Exception: " + e.toString() + "\n";
            return consoleOut;
        }
        if (count > 0) {
            if (idExist) {
                consoleOut += " - Success: updateBuilder for id: " + id + "\n";
                try {
                    ArticleEntity entityForConsole = queryForId(Integer.parseInt(id), true);
                    consoleOut += " " + entityForConsole.toString() + "\n";
                } catch (SQLException e) {
                    consoleOut += " - Exception: " + e.toString() + "\n";
                    return consoleOut;
                }
                return consoleOut;
            } else {
                consoleOut += " - Success: all rows(" + count + " row) update\n";
                consoleOut += queryAllArticle();
                return consoleOut;
            }
        } else {
            consoleOut += " - Fail: no article changed because no article matched!\n";
            return consoleOut;
        }
    }

    public String deleteArticle(String id, String title, String authorId) {
        String consoleOut = "Delete Article:\n";
        ArticleEntity articleEntity = null;
        if (!id.isEmpty() && TextUtils.isDigitsOnly(id)) {
            consoleOut += deleteArticleById(Integer.parseInt(id));
        } else {
            consoleOut += deleteArticleBuilder(title, authorId);
        }
        return consoleOut;
    }

    public String deleteArticleById(int id) {
        String consoleOut = "";
        ArticleEntity entityForConsole = null;
        try {
            entityForConsole = queryForId(id, true);
            if (entityForConsole != null) {
                int count = deleteById(id);
                if (count > 0) {
                    consoleOut += " - Success: the article:[" + entityForConsole.toString() + "] was deleted!\n";
                } else {
                    consoleOut += " - Fail: no row was deleted!\n";
                }
                return consoleOut;
            } else {
                consoleOut += " - Fail: the id is not exist!\n";
                return consoleOut;
            }
        } catch (SQLException e) {
            consoleOut += " - Exception: " + e.toString() + "\n";
            return consoleOut;
        }
    }

    public String deleteArticleBuilder(String title, String authorId) {
        String consoleOut = "";
        Map<String, String> queryWhere = new HashMap<String, String>();
        if (!title.isEmpty()) {
            queryWhere.put("title", title);
        }
        if (!authorId.isEmpty() && TextUtils.isDigitsOnly(authorId)) {
            queryWhere.put("author_id", authorId);
        }
        try {
            List<ArticleEntity> listForConsole = queryBuilder(queryWhere, true);
            int count = deleteBuilder(queryWhere);
            if (count > 0) {
                consoleOut += " - Success: " + count + " rows were deleted. articles[";
                for (ArticleEntity entity : listForConsole) {
                    consoleOut += "{" + entity.toString() + "},";
                }
                consoleOut = consoleOut.substring(0, consoleOut.length() - 1);
                consoleOut += "] was deleted!\n";
                return consoleOut;
            }
            consoleOut += " - Fail: the entity is not exist!\n";
            return consoleOut;
        } catch (SQLException e) {
            consoleOut += " - Exception: " + e.toString() + "\n";
            return consoleOut;
        }
    }

    public String queryAllArticle() {
        String consoleOut = "Query All Article:\n";
        List<ArticleEntity> articles = null;
        try {
            articles = queryForAll(true);
        } catch (SQLException e) {
            consoleOut += " - Exception: " + e.toString() + "\n";
            return consoleOut;
        }
        if (articles == null || articles.size() < 1) {
            consoleOut += " - Success: no article matched!\n";
            return consoleOut;
        }
        consoleOut += " - Success: there " + articles.size() + " row\n";
        for (ArticleEntity articleEntity : articles) {
            consoleOut += " " + articleEntity.toString() + "\n";
        }
        return consoleOut;
    }

    public String deleteAllArticle() {
        String consoleOut = "Delete All Article:\n";
        try {
            int count = deleteAll();
            consoleOut += " - Success: " + count + " rows were deleted.";
        } catch (SQLException e) {
            consoleOut += " - Exception: " + e.toString() + "\n";
            return consoleOut;
        }
        return consoleOut;
    }


    public int create(ArticleEntity articleEntity) throws SQLException {
        return mArticleDaoOpe.create(articleEntity);
    }

    public ArticleEntity queryForId(int id, boolean withAuthor) throws SQLException {
        ArticleEntity articleEntity = mArticleDaoOpe.queryForId(id);
        if (withAuthor && articleEntity != null) {
            mMyDataHelper.getDao(PersonEntity.class).refresh(articleEntity.getAuthor());
        }
        return articleEntity;
    }

    public List<ArticleEntity> queryBuilder(Map<String, String> whereMap, boolean withAuthor) throws SQLException {
        QueryBuilder queryBuilder = mArticleDaoOpe.queryBuilder();
        if (whereMap.size() > 0) {
            Where where = queryBuilder.where();
            Iterator<Map.Entry<String, String>> iterator = whereMap.entrySet().iterator();
            Map.Entry<String, String> entry = iterator.next();
            where = where.eq(entry.getKey(), entry.getValue());
            while (iterator.hasNext()) {
                entry = iterator.next();
                where = where.and().eq(entry.getKey(), entry.getValue());
            }
        }
        List<ArticleEntity> articleEntityList = queryBuilder.query();
        if (withAuthor && articleEntityList != null) {
            for (ArticleEntity entity : articleEntityList) {
                mMyDataHelper.getDao(PersonEntity.class).refresh(entity.getAuthor());
            }
        }
        return articleEntityList;
    }

    public int update(ArticleEntity articleEntity) throws SQLException {
        return mArticleDaoOpe.update(articleEntity);
    }

    public int updateBuilder(Map<String, String> whereMap, ContentValues updateValues) throws SQLException {
        UpdateBuilder updateBuilder = mArticleDaoOpe.updateBuilder();
        if (whereMap.size() > 0) {
            Where where = updateBuilder.where();
            Iterator<Map.Entry<String, String>> iterator = whereMap.entrySet().iterator();
            Map.Entry<String, String> entry = iterator.next();
            where.eq(entry.getKey(), entry.getValue());
            while (iterator.hasNext()) {
                entry = iterator.next();
                where.and().eq(entry.getKey(), entry.getValue());
            }
        }
        for (String key : updateValues.keySet()) {
            updateBuilder.updateColumnValue(key, updateValues.get(key));
        }
        return updateBuilder.update();
    }

    public int delete(ArticleEntity articleEntity) throws SQLException {
        return mArticleDaoOpe.delete(articleEntity);
    }

    public int deleteById(int id) throws SQLException {
        return mArticleDaoOpe.deleteById(id);
    }

    public int deleteIds(List<Integer> ids) throws SQLException {
        return mArticleDaoOpe.deleteIds(ids);
    }

    public int deleteBuilder(Map<String, String> whereMap) throws SQLException {
        DeleteBuilder deleteBuilder = mArticleDaoOpe.deleteBuilder();
        if (whereMap.size() > 0) {
            Where where = deleteBuilder.where();
            Iterator<Map.Entry<String, String>> iterator = whereMap.entrySet().iterator();
            Map.Entry<String, String> entry = iterator.next();
            where.eq(entry.getKey(), entry.getValue());
            while (iterator.hasNext()) {
                entry = iterator.next();
                where.and().eq(entry.getKey(), entry.getValue());
            }
        }
        return deleteBuilder.delete();
    }

    public int deleteAll() throws SQLException {
        return mArticleDaoOpe.delete(mArticleDaoOpe.queryForAll());
    }

    public List<ArticleEntity> queryForAll(boolean withAuthor) throws SQLException {
        List<ArticleEntity> articleEntityList = mArticleDaoOpe.queryForAll();
        if (withAuthor && articleEntityList != null) {
            for (ArticleEntity entity : articleEntityList) {
                mMyDataHelper.getDao(PersonEntity.class).refresh(entity.getAuthor());
            }
        }
        return articleEntityList;
    }
}
