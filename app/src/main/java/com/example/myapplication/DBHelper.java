package com.example.myapplication;

        import android.content.Context;
 import android.database.sqlite.SQLiteDatabase;
 import android.database.sqlite.SQLiteOpenHelper;

         public class DBHelper extends SQLiteOpenHelper {
     private static final int DATABASE_VERSION = 1;
     private static final String DATABASE_NAME = "contacts.db";

            DBHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

            @Override
    public void onCreate(SQLiteDatabase db) {
                String sql = "CREATE TABLE IF NOT EXISTS " + Contact.TABLE_NAME + " (" +
                                Contact.COLUMN_NAME_NAME + " VARCHAR(255) NOT NULL PRIMARY KEY, " +
                                Contact.COLUMN_NAME_EMAIL + " VARCHAR(255) NOT NULL, " +
                                Contact.COLUMN_NAME_PHONE + " VARCHAR(255) NOT NULL)";
                db.execSQL(sql);
            }

            @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                String sql = "DROP TABLE IF EXISTS " + Contact.TABLE_NAME;
                db.execSQL(sql);
            }
}
