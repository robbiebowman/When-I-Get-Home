package com.example.robbiebowman.whenigethome;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "wigh.db";
    private static final String REMINDER_TABLE = "reminder";
    private static final String REMINDER_TABLE_ID = "id";
    private static final String REMINDER_TABLE_TEXT = "text";
    private static final String REMINDER_TABLE_TIMESTAMP = "timestamp";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE " + REMINDER_TABLE + " (" +
                REMINDER_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                REMINDER_TABLE_TEXT + " TYPE_TEXT, " +
                REMINDER_TABLE_TIMESTAMP + " INTEGER" +
                " )";
        db.execSQL(createQuery);
    }

    public void saveReminder(Reminder reminder) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(String.format("INSERT INTO %s (%s, %s) VALUES ('%s', %s)", REMINDER_TABLE, REMINDER_TABLE_TEXT, REMINDER_TABLE_TIMESTAMP, reminder.getText(), reminder.getDateTime().toDate().getTime()));
    }

    public List<Reminder> getReminders() {
        List<Reminder> reminders = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor resultCursor = db.query(REMINDER_TABLE, new String[] {REMINDER_TABLE_ID, REMINDER_TABLE_TEXT, REMINDER_TABLE_TIMESTAMP}, null, null, null, null, REMINDER_TABLE_TIMESTAMP + " DESC");
        while (resultCursor.moveToNext()) {
            Reminder reminder = new Reminder();
            reminder.setId(resultCursor.getInt(resultCursor.getColumnIndex(REMINDER_TABLE_ID)));
            reminder.setText(resultCursor.getString(resultCursor.getColumnIndex(REMINDER_TABLE_TEXT)));
            reminder.setDateTime(new DateTime(resultCursor.getInt(resultCursor.getColumnIndex(REMINDER_TABLE_TIMESTAMP))));
            reminders.add(reminder);
        }
        return reminders;
    }

    public void removeReminder(Reminder reminder) {
        int idToRemove = reminder.getId();
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(String.format("DELETE FROM %s WHERE %s = %s", REMINDER_TABLE, REMINDER_TABLE_ID, idToRemove));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
