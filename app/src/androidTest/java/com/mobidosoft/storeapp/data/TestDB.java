package com.mobidosoft.storeapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.mobidosoft.storeapp.Model.User;
import com.mobidosoft.storeapp.Utils.ConvertUtil;

import java.util.HashSet;

/**
 * Created by RP on 3/18/2015.
 */


public class TestDB extends AndroidTestCase {
    public static final String LOG_TAG = TestDB.class.getSimpleName();

    void deleteTheDatabase() {
        mContext.deleteDatabase(UserDbHelper.DATABASE_NAME);
    }

    public void setUp() {
        deleteTheDatabase();
    }

    public void testCreateDb() throws Throwable {
        final HashSet<String> tableNameHashSet = new HashSet<>();

        tableNameHashSet.add(UserContract.UserEntry.TABLE_NAME);

        mContext.deleteDatabase(UserDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new UserDbHelper(this.mContext).getWritableDatabase();

        assertEquals(true, db.isOpen());

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly", c.moveToFirst());

        do {
            tableNameHashSet.remove(c.getString(0));
        } while (c.moveToNext());

        assertTrue("Error: Your database was created without the results entry", tableNameHashSet.isEmpty());

        c = db.rawQuery("PRAGMA table_info(" + UserContract.UserEntry.TABLE_NAME + ")", null);

        assertTrue("Error: This means that we were unable to querty the dataase for table information.", c.moveToFirst());

        final HashSet<String> resultColumnHashSet = new HashSet<>();

        resultColumnHashSet.add(UserContract.UserEntry._ID);
        resultColumnHashSet.add(UserContract.UserEntry.COLUMN_SUCCESS);
        resultColumnHashSet.add(UserContract.UserEntry.COLUMN_MESSAGE);
        resultColumnHashSet.add(UserContract.UserEntry.COLUMN_ERROR_MESSAGE);
        resultColumnHashSet.add(UserContract.UserEntry.COLUMN_USER_ACCESS_KEY);
        resultColumnHashSet.add(UserContract.UserEntry.COLUMN_IS_TOKEN_DEVICE);
        resultColumnHashSet.add(UserContract.UserEntry.COLUMN_ADMINS_ID);
        resultColumnHashSet.add(UserContract.UserEntry.COLUMN_USERNAME);
        resultColumnHashSet.add(UserContract.UserEntry.COLUMN_PASSWORD);
        resultColumnHashSet.add(UserContract.UserEntry.COLUMN_NAME);
        resultColumnHashSet.add(UserContract.UserEntry.COLUMN_ADMINROLES_ID);
        resultColumnHashSet.add(UserContract.UserEntry.COLUMN_ADMINROLES_NAME);
        resultColumnHashSet.add(UserContract.UserEntry.COLUMN_FIRST_NAME);
        resultColumnHashSet.add(UserContract.UserEntry.COLUMN_MIDDLE_NAME);
        resultColumnHashSet.add(UserContract.UserEntry.COLUMN_LAST_NAME);
        resultColumnHashSet.add(UserContract.UserEntry.COLUMN_EMAIL);
        resultColumnHashSet.add(UserContract.UserEntry.COLUMN_STATUS);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);

            resultColumnHashSet.remove(columnName);
        } while (c.moveToNext());

        assertTrue("Error: The database doesn't contain all of the required result entry columns", resultColumnHashSet.isEmpty());
        db.close();
    }


    public void testResultTable() {
        UserDbHelper dbHelper = new UserDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues resultValues = TestUtilitiesDB.createResultValues();

        long resultRowId = db.insert(UserContract.UserEntry.TABLE_NAME, null, resultValues);

        assertTrue(resultRowId != -1);

        Cursor resultCursor = db.query(
                UserContract.UserEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        assertTrue("Error: No records found", resultCursor.moveToFirst());

        TestUtilitiesDB.validateCurrentRecord("testInsertReadDb resultEntry failed to validate", resultCursor, resultValues);

        assertFalse("Error: More than one record returned from the results query", resultCursor.moveToNext());

        resultCursor.close();
        dbHelper.close();
    }


    public void testAddUser()
    {
        User user = new User();
        user = new User();

        user.setSuccess("1");
        user.setMessage("ok");
        user.setErrorMessage("");
        user.setUserAccessKey("asdf-1");
        user.setIsTokenDevice("0");

        user.setAdminsId(1);
        user.setUsername("admin2");
        user.setPassword("asdf");
        user.setName("Ronald Pinto");
        user.setAdminRolesId(1);
        user.setAdminRolesName("administrador");
        user.setFirstName("Ronald");
        user.setMiddleName("M");
        user.setLastName("Pinto");
        user.setEmail("ronald.pinto@mobidosoft.com");
        user.setStatus("Active");

        UserDbHelper dbHelper = new UserDbHelper(mContext);

        dbHelper.addUser(user);
        Log.v(LOG_TAG,"TEST save user: " + user.toString());

        User userAdd = dbHelper.getUserByUsername("admin2");
        Log.v(LOG_TAG,"TEST user recuperado: " + userAdd.toString());


        assertFalse("Error: In Add user:",  !user.getUsername().equals(userAdd.getUsername()));
        dbHelper.close();
    }



    public void testGetUserNull()
    {

        UserDbHelper dbHelper = new UserDbHelper(mContext);
        User user = dbHelper.getUserByUsername("asdfasdfasasdf");

        assertFalse("Error: In Get user null", user != null);

    }


    public void testUpdateUser()
    {
        User user = new User();
        //user = new User();

        user.setSuccess("1");
        user.setMessage("ok");
        user.setErrorMessage("");
        user.setUserAccessKey("asdf-3");
        user.setIsTokenDevice("0");

        user.setAdminsId(1);
        user.setUsername("admin3");
        user.setPassword("asdf");
        user.setName("Ronald Pinto");
        user.setAdminRolesId(1);
        user.setAdminRolesName("administrador");
        user.setFirstName("Ronald");
        user.setMiddleName("M");
        user.setLastName("Pinto");
        user.setEmail("ronald.pinto@mobidosoft.com");
        user.setStatus("Active");

        UserDbHelper dbHelper = new UserDbHelper(mContext);

        dbHelper.addUser(user);
        Log.v(LOG_TAG,"TEST save user: " + user.toString());

        User userAdd = dbHelper.getUserByUsername("admin3");
        Log.v(LOG_TAG,"TEST user recuperado: " + userAdd.toString());

        userAdd.setPassword("asdfasdf");
        dbHelper.updateUser(userAdd);
        Log.v(LOG_TAG,"TEST user update: " + userAdd.toString());
        User userUpdate = dbHelper.getUserByUsername("admin3");


        assertFalse("Error: In Update user:", !userUpdate.getPassword().equals("asdfasdf"));
        dbHelper.close();
    }



}