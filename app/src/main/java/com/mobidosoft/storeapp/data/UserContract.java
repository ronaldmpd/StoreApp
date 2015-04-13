package com.mobidosoft.storeapp.data;

import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by RP on 3/17/2015.
 */
public class UserContract {

    public static long normalizeDate(long startDate) {
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);

        return time.setJulianDay(julianDay);
    }


    public static final class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "users";

        public static final String COLUMN_SUCCESS = "success";
        public static final String COLUMN_MESSAGE = "message";
        public static final String COLUMN_ERROR_MESSAGE = "error_message";
        public static final String COLUMN_USER_ACCESS_KEY = "user_access_key";
        public static final String COLUMN_IS_TOKEN_DEVICE = "is_token_device";

        public static final String COLUMN_ADMINS_ID = "admins_id";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ADMINROLES_ID = "adminroles_id";
        public static final String COLUMN_ADMINROLES_NAME = "adminroles_name";
        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_MIDDLE_NAME = "middle_name";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_STATUS = "status";



    }
}