package com.mobidosoft.storeapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobidosoft.storeapp.Model.User;
import com.mobidosoft.storeapp.Utils.ConvertUtil;

import static com.mobidosoft.storeapp.data.UserContract.UserEntry;

/**
 * Created by RP on 3/17/2015.
 */
public class UserDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "store.db";
    private static final String LOG_TAG = UserDbHelper.class.getSimpleName();

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                UserEntry._ID + " INTEGER PRIMARY KEY," +
                UserEntry.COLUMN_SUCCESS + " TEXT NOT NULL," +
                UserEntry.COLUMN_MESSAGE + " TEXT NOT NULL," +
                UserEntry.COLUMN_ERROR_MESSAGE + " TEXT," +
                UserEntry.COLUMN_USER_ACCESS_KEY + " TEXT NOT NULL," +
                UserEntry.COLUMN_IS_TOKEN_DEVICE + " TEXT NOT NULL," +
                UserEntry.COLUMN_ADMINS_ID + " INTEGER NOT NULL," +
                UserEntry.COLUMN_USERNAME + " TEXT NOT NULL," +
                UserEntry.COLUMN_PASSWORD + " TEXT NOT_NULL," +
                UserEntry.COLUMN_NAME + " TEXT," +
                UserEntry.COLUMN_ADMINROLES_ID + " INTEGER NOT_NULL," +
                UserEntry.COLUMN_ADMINROLES_NAME + " TEXT NOT_NULL," +
                UserEntry.COLUMN_FIRST_NAME + " TEXT NOT NULL," +
                UserEntry.COLUMN_MIDDLE_NAME + " TEXT," +
                UserEntry.COLUMN_LAST_NAME + " TEXT NOT NULL," +
                UserEntry.COLUMN_EMAIL + " TEXT NOT NULL," +
                UserEntry.COLUMN_STATUS + " TEXT NOT NULL" +
                " );";
        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME);

        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues userValues = new ContentValues();
        userValues.put(UserEntry.COLUMN_SUCCESS,  user.getSuccess());
        userValues.put(UserEntry.COLUMN_MESSAGE,  user.getMessage());
        userValues.put(UserEntry.COLUMN_ERROR_MESSAGE,  user.getErrorMessage());
        userValues.put(UserEntry.COLUMN_USER_ACCESS_KEY,  user.getUserAccessKey());
        userValues.put(UserEntry.COLUMN_IS_TOKEN_DEVICE,  user.getIsTokenDevice());
        userValues.put(UserEntry.COLUMN_ADMINS_ID,  user.getAdminsId());
        userValues.put(UserEntry.COLUMN_USERNAME, user.getUsername());
        userValues.put(UserEntry.COLUMN_PASSWORD, user.getPassword());
        userValues.put(UserEntry.COLUMN_NAME, user.getName());
        userValues.put(UserEntry.COLUMN_ADMINROLES_ID, user.getAdminRolesId());
        userValues.put(UserEntry.COLUMN_ADMINROLES_NAME, user.getAdminRolesName());
        userValues.put(UserEntry.COLUMN_FIRST_NAME, user.getFirstName());
        userValues.put(UserEntry.COLUMN_MIDDLE_NAME,user.getMiddleName());
        userValues.put(UserEntry.COLUMN_LAST_NAME, user.getLastName());
        userValues.put(UserEntry.COLUMN_EMAIL, user.getEmail());
        userValues.put(UserEntry.COLUMN_STATUS, user.getStatus());

        // Inserting Row
        db.insert(UserEntry.TABLE_NAME, null, userValues);
        db.close(); // Closing database connection
    }


    public User getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(UserEntry.TABLE_NAME, null, UserEntry._ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        User user = null;

        if (cursor != null) {
            cursor.moveToFirst();

            user = new User();
            user.setId(cursor.getColumnIndex(UserEntry._ID));
            user.setSuccess(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_SUCCESS)));
            user.setMessage(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_MESSAGE)));
            user.setErrorMessage(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_ERROR_MESSAGE)));
            user.setUserAccessKey(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_USER_ACCESS_KEY)));
            user.setIsTokenDevice(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_IS_TOKEN_DEVICE)));
            user.setAdminsId(ConvertUtil.stringToInteger(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_ADMINS_ID))));
            user.setUsername(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_PASSWORD)));
            user.setName(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_NAME)));
            user.setAdminRolesId(ConvertUtil.stringToInteger(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_ADMINROLES_ID))));
            user.setAdminRolesName(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_ADMINROLES_NAME)));
            user.setFirstName(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_FIRST_NAME)));
            user.setMiddleName(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_MIDDLE_NAME)));
            user.setLastName(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_LAST_NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_EMAIL)));
            user.setStatus(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_STATUS)));

        }

        return user;
    }


    public User getUserByUsername(String username) {
        User user = null;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(UserEntry.TABLE_NAME, null, UserEntry.COLUMN_USERNAME + " = ?",
                    new String[]{String.valueOf(username)}, null, null, null, null);


            Log.d(LOG_TAG, "cursor.getCount() " + cursor.getCount());

            if(cursor.getCount() == 0) {
                Log.d(LOG_TAG, "Return null");
                return user;
            }

            if (cursor != null && cursor.moveToFirst()) {
                //cursor.moveToFirst();


                user = new User();
                user.setId(ConvertUtil.stringToInteger(cursor.getString(cursor.getColumnIndex(UserEntry._ID))));
                user.setSuccess(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_SUCCESS)));
                user.setMessage(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_MESSAGE)));
                user.setErrorMessage(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_ERROR_MESSAGE)));
                user.setUserAccessKey(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_USER_ACCESS_KEY)));
                user.setIsTokenDevice(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_IS_TOKEN_DEVICE)));
                user.setAdminsId(ConvertUtil.stringToInteger(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_ADMINS_ID))));
                user.setUsername(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_PASSWORD)));
                user.setName(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_NAME)));
                user.setAdminRolesId(ConvertUtil.stringToInteger(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_ADMINROLES_ID))));
                user.setAdminRolesName(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_ADMINROLES_NAME)));
                user.setFirstName(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_FIRST_NAME)));
                user.setMiddleName(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_MIDDLE_NAME)));
                user.setLastName(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_LAST_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_EMAIL)));
                user.setStatus(cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_STATUS)));

            }
        }
        catch (Exception ex)
        {
            Log.e(LOG_TAG,ex.getMessage());
            ex.printStackTrace();
        }
        return user;
    }



    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues userValues = new ContentValues();
        userValues.put(UserEntry.COLUMN_SUCCESS,  user.getSuccess());
        userValues.put(UserEntry.COLUMN_MESSAGE,  user.getMessage());
        userValues.put(UserEntry.COLUMN_ERROR_MESSAGE,  user.getErrorMessage());
        userValues.put(UserEntry.COLUMN_USER_ACCESS_KEY,  user.getUserAccessKey());
        userValues.put(UserEntry.COLUMN_IS_TOKEN_DEVICE,  user.getIsTokenDevice());
        userValues.put(UserEntry.COLUMN_ADMINS_ID,  user.getAdminsId());
        userValues.put(UserEntry.COLUMN_USERNAME, user.getUsername());
        userValues.put(UserEntry.COLUMN_PASSWORD, user.getPassword());
        userValues.put(UserEntry.COLUMN_NAME, user.getName());
        userValues.put(UserEntry.COLUMN_ADMINROLES_ID, user.getAdminRolesId());
        userValues.put(UserEntry.COLUMN_ADMINROLES_NAME, user.getAdminRolesName());
        userValues.put(UserEntry.COLUMN_FIRST_NAME, user.getFirstName());
        userValues.put(UserEntry.COLUMN_MIDDLE_NAME,user.getMiddleName());
        userValues.put(UserEntry.COLUMN_LAST_NAME, user.getLastName());
        userValues.put(UserEntry.COLUMN_EMAIL, user.getEmail());
        userValues.put(UserEntry.COLUMN_STATUS, user.getStatus());

        // updating row
        return db.update(UserEntry.TABLE_NAME, userValues, UserEntry._ID + " = ?",
                new String[] { String.valueOf(user.getId().toString()) });
    }


    public void deleteContact(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UserEntry.TABLE_NAME, UserEntry._ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
        db.close();
    }


}
