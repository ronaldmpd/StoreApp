package com.mobidosoft.storeapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.HandlerThread;
import android.test.AndroidTestCase;
import android.os.Handler;

import com.mobidosoft.storeapp.utils.PollingCheck;

import java.util.Map;
import java.util.Set;

/**
 * Created by RP on 3/18/2015.
 */

public class TestUtilitiesDB extends AndroidTestCase {
    static final int TEST_TEAM_ID = 38;
    static final long TEST_DATE = 1419033600L;  // December 20th, 2014

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());

    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();

        for (Map.Entry<String, Object> entry : valueSet) {
            String columName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columName);
            assertFalse("Column '" + columName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString()
                            + "' did not match the expected value '" + expectedValue + "'. " + error,
                    expectedValue, valueCursor.getString(idx));
        }
    }

    static ContentValues createResultValues() {
        ContentValues resultValues = new ContentValues();
        resultValues.put(UserContract.UserEntry.COLUMN_SUCCESS, "1");
        resultValues.put(UserContract.UserEntry.COLUMN_MESSAGE, "ok");
        resultValues.put(UserContract.UserEntry.COLUMN_ERROR_MESSAGE, "");
        resultValues.put(UserContract.UserEntry.COLUMN_USER_ACCESS_KEY, "asdf-1");
        resultValues.put(UserContract.UserEntry.COLUMN_IS_TOKEN_DEVICE, "0");
        resultValues.put(UserContract.UserEntry.COLUMN_ADMINS_ID, 1);
        resultValues.put(UserContract.UserEntry.COLUMN_USERNAME, "admin");
        resultValues.put(UserContract.UserEntry.COLUMN_PASSWORD, "asdf");
        resultValues.put(UserContract.UserEntry.COLUMN_NAME, "Ronald Pinto");
        resultValues.put(UserContract.UserEntry.COLUMN_ADMINROLES_ID, 1);
        resultValues.put(UserContract.UserEntry.COLUMN_ADMINROLES_NAME, "administrador");
        resultValues.put(UserContract.UserEntry.COLUMN_FIRST_NAME, "Ronald");
        resultValues.put(UserContract.UserEntry.COLUMN_MIDDLE_NAME, "M");
        resultValues.put(UserContract.UserEntry.COLUMN_LAST_NAME, "Pinto");
        resultValues.put(UserContract.UserEntry.COLUMN_EMAIL, "ronald.pinto@mobidosoft.com");
        resultValues.put(UserContract.UserEntry.COLUMN_STATUS, "Active");

        return resultValues;
    }

    static long insertNewTeam(Context context) {
        UserDbHelper dbHelper = new UserDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues testValues = TestUtilitiesDB.createResultValues();

        long resultRowId = db.insert(UserContract.UserEntry.TABLE_NAME, null, testValues);

        assertTrue("Error: Failure to insert test Result Values", resultRowId != -1);

        return resultRowId;
    }

    static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

        public void waitForNotificationOrFail() {
            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
            // It's useful to look at the Android CTS source for ideas on how to test your Android
            // applications.  The reason that PollingCheck works is that, by default, the JUnit
            // testing framework is not running on the main Android application thread.
            new PollingCheck(5000) {
                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }
}
