package com.example.connectdbattempt1;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

    public class ResultsDatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "audio_response.db";
        private static final int DATABASE_VERSION = 2;

        private static final String TABLE_RESPONSE = "response";
        private static final String COLUMN_ID = "_id";
        private static final String COLUMN_AUDIO_INDEX = "audio_index";
        private static final String COLUMN_USER_RESPONSE = "user_response";

        public ResultsDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTableQuery = "CREATE TABLE " + TABLE_RESPONSE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_AUDIO_INDEX + " INTEGER, " +
                    COLUMN_USER_RESPONSE + " TEXT)";
            db.execSQL(createTableQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESPONSE);
            onCreate(db);
        }

        public void insertResponse(int audioIndex, String userResponse) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_AUDIO_INDEX, audioIndex);
            values.put(COLUMN_USER_RESPONSE, userResponse);
            db.insert(TABLE_RESPONSE, null, values);

        }

}
