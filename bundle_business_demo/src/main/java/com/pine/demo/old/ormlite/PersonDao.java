package com.pine.demo.old.ormlite;

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

public class PersonDao {
    private MyDatabaseHelper mMyDatabaseHelper;
    private Dao<PersonEntity, Integer> mPersonDaoOpe;

    public PersonDao(Context context) {
        try {
            mMyDatabaseHelper = MyDatabaseHelper.getMyDatabaseHelper(context);
            mPersonDaoOpe = mMyDatabaseHelper.getPersonDaoOpe();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String addPerson(String name, String age) {
        String consoleOut = "Add Person:\n";
        PersonEntity personEntity = new PersonEntity();
        if (name.isEmpty()) {
            consoleOut += " - Fail: name should not be null!\n";
            return consoleOut;
        }
        personEntity.setName(name);
        if (!age.isEmpty() && TextUtils.isDigitsOnly(age)) {
            personEntity.setAge(Integer.parseInt(age));
        }
        try {
            int count = create(personEntity);
            if (count < 1) {
                consoleOut += " - Success: but no row was added!\n";
            } else {
                consoleOut += " - Success: " + count + " row was added, person:[" + personEntity.toString() + "]\n";
            }
        } catch (SQLException e) {
            consoleOut += " - Exception: " + e.toString() + "\n";
            return consoleOut;
        }
        return consoleOut;
    }

    public String queryPerson(String id, String name, String age) {
        String consoleOut = "Query Person:\n";
        if (!id.isEmpty() && TextUtils.isDigitsOnly(id)) {
            consoleOut += queryPersonById(Integer.parseInt(id));
        } else {
            consoleOut += queryPersonBuilder(name, age);
        }
        return consoleOut;
    }

    public String queryPersonById(int id) {
        String consoleOut = "";
        PersonEntity personEntity = null;
        try {
            personEntity = queryForId(id);
        } catch (SQLException e) {
            consoleOut += " - Exception: " + e.toString() + "\n";
            return consoleOut;
        }
        if (personEntity == null) {
            consoleOut += " - Success: query Person by id: " + id + ", there is no such person!\n";
            return consoleOut;
        }
        consoleOut += " - Success: query Person by id: " + id + " \n";
        consoleOut += " " + personEntity.toString() + "\n";
        return consoleOut;
    }

    public String queryPersonBuilder(String name, String age) {
        String consoleOut = "";
        List<PersonEntity> personEntityList = null;
        Map<String, String> queryWhere = new HashMap<String, String>();
        if (!name.isEmpty()) {
            queryWhere.put("name", name);
        }
        if (!age.isEmpty() && TextUtils.isDigitsOnly(age)) {
            queryWhere.put("age", age);
        }
        try {
            personEntityList = queryBuilder(queryWhere);
        } catch (SQLException e) {
            consoleOut += " - Exception: " + e.toString() + "\n";
            return consoleOut;
        }
        if (personEntityList == null || personEntityList.size() < 1) {
            consoleOut += " - Success: no person matched!\n";
            return consoleOut;
        }
        consoleOut += " - Success: there " + personEntityList.size() + " row matched\n";
        for (PersonEntity personEntity : personEntityList) {
            consoleOut += " " + personEntity.toString() + "\n";
        }
        return consoleOut;
    }

    public String updatePerson(String id, String name, String age) {
        String consoleOut = "Update Person:\n";
        PersonEntity personEntity = null;
        if (!id.isEmpty() && TextUtils.isDigitsOnly(id)) {
            try {
                personEntity = queryForId(Integer.parseInt(id));
            } catch (SQLException e) {
                consoleOut += " - Exception: " + e.toString() + "\n";
                return consoleOut;
            }
            if (personEntity == null) {
                consoleOut += " - Fail: no person changed because no person matched!\n";
                return consoleOut;
            }
            int changeCount = 0;
            if (!name.isEmpty()) {
                personEntity.setName(name);
                changeCount++;
            }
            if (!age.isEmpty() && TextUtils.isDigitsOnly(age)) {
                personEntity.setAge(Integer.parseInt(age));
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
            count = update(personEntity);
        } catch (SQLException e) {
            consoleOut += " - Exception: " + e.toString() + "\n";
            return consoleOut;
        }
        if (count > 0) {
            consoleOut += " - Success: there " + count + " row update\n";
            consoleOut += " " + personEntity.toString() + "\n";
        }
        return consoleOut;
    }

    public String updatePersonBuilder(String id, String name, String age) {
        String consoleOut = "Update Person:\n";
        Map<String, String> queryWhere = new HashMap<String, String>();
        ContentValues updateValues = new ContentValues();
        boolean idExist = false;
        if (!id.isEmpty() && TextUtils.isDigitsOnly(id)) {
            idExist = true;
            queryWhere.put("id", id);
        }
        if (!name.isEmpty()) {
            updateValues.put("name", name);
        }
        if (!age.isEmpty() && TextUtils.isDigitsOnly(age)) {
            updateValues.put("age", age);
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
                    PersonEntity entityForConsole = queryForId(Integer.parseInt(id));
                    consoleOut += " " + entityForConsole.toString() + "\n";
                } catch (SQLException e) {
                    consoleOut += " - Exception: " + e.toString() + "\n";
                    return consoleOut;
                }
                return consoleOut;
            } else {
                consoleOut += " - Success: all rows(" + count + " row) update\n";
                consoleOut += queryAllPerson();
                return consoleOut;
            }
        } else {
            consoleOut += " - Fail: no person changed because no person matched!\n";
            return consoleOut;
        }
    }

    public String deletePerson(String id, String name, String age) {
        String consoleOut = "Delete Person:\n";
        PersonEntity personEntity = null;
        if (!id.isEmpty() && TextUtils.isDigitsOnly(id)) {
            consoleOut += deletePersonById(Integer.parseInt(id));
        } else {
            consoleOut += deletePersonBuilder(name, age);
        }
        return consoleOut;
    }

    public String deletePersonById(int id) {
        String consoleOut = "";
        PersonEntity entityForConsole = null;
        try {
            entityForConsole = queryForId(id);
            if (entityForConsole != null) {
                int count = deleteById(id);
                if (count > 0) {
                    consoleOut += " - Success: the person:[" + entityForConsole.toString() + "] was deleted!\n";
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

    public String deletePersonBuilder(String name, String age) {
        String consoleOut = "";
        Map<String, String> queryWhere = new HashMap<String, String>();
        if (!name.isEmpty()) {
            queryWhere.put("name", name);
        }
        if (!age.isEmpty() && TextUtils.isDigitsOnly(age)) {
            queryWhere.put("age", age);
        }
        try {
            List<PersonEntity> listForConsole = queryBuilder(queryWhere);
            int count = deleteBuilder(queryWhere);
            if (count > 0) {
                consoleOut += " - Success: " + count + " rows were deleted. persons[";
                for (PersonEntity entity : listForConsole) {
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

    public String queryAllPerson() {
        String consoleOut = "Query All Person:\n";
        List<PersonEntity> persons = null;
        try {
            persons = queryForAll();
        } catch (SQLException e) {
            consoleOut += " - Exception: " + e.toString() + "\n";
            return consoleOut;
        }
        if (persons == null || persons.size() < 1) {
            consoleOut += " - Success: no person matched!\n";
            return consoleOut;
        }
        consoleOut += " - Success: there " + persons.size() + " row\n";
        for (PersonEntity personEntity : persons) {
            consoleOut += " " + personEntity.toString() + "\n";
        }
        return consoleOut;
    }

    public String deleteAllPerson() {
        String consoleOut = "Delete All Person:\n";
        try {
            int count = deleteAll();
            consoleOut += " - Success: " + count + " rows were deleted.";
        } catch (SQLException e) {
            consoleOut += " - Exception: " + e.toString() + "\n";
            return consoleOut;
        }
        return consoleOut;
    }


    public int create(PersonEntity personEntity) throws SQLException {
        return mPersonDaoOpe.create(personEntity);
    }

    public PersonEntity queryForId(int id) throws SQLException {
        return mPersonDaoOpe.queryForId(id);
    }

    public List<PersonEntity> queryBuilder(Map<String, String> whereMap) throws SQLException {
        QueryBuilder queryBuilder = mPersonDaoOpe.queryBuilder();
        if (whereMap.size() > 0) {
            Where where = queryBuilder.where();
            Iterator<Map.Entry<String, String>> iterator = whereMap.entrySet().iterator();
            Map.Entry<String, String> entry = iterator.next();
            where.eq(entry.getKey(), entry.getValue());
            while (iterator.hasNext()) {
                entry = iterator.next();
                where.and().eq(entry.getKey(), entry.getValue());
            }
        }
        return queryBuilder.query();
    }

    public int update(PersonEntity personEntity) throws SQLException {
        return mPersonDaoOpe.update(personEntity);
    }

    public int updateBuilder(Map<String, String> whereMap, ContentValues updateValues) throws SQLException {
        UpdateBuilder updateBuilder = mPersonDaoOpe.updateBuilder();
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

    public int delete(PersonEntity personEntity) throws SQLException {
        return mPersonDaoOpe.delete(personEntity);
    }

    public int deleteById(int id) throws SQLException {
        return mPersonDaoOpe.deleteById(id);
    }

    public int deleteIds(List<Integer> ids) throws SQLException {
        return mPersonDaoOpe.deleteIds(ids);
    }

    public int deleteBuilder(Map<String, String> whereMap) throws SQLException {
        DeleteBuilder deleteBuilder = mPersonDaoOpe.deleteBuilder();
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
        return mPersonDaoOpe.delete(mPersonDaoOpe.queryForAll());
    }

    public List<PersonEntity> queryForAll() throws SQLException {
        return mPersonDaoOpe.queryForAll();
    }
}
