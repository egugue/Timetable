package htoyama.timetable.domain.repository.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import htoyama.timetable.domain.models.DayType;
import htoyama.timetable.domain.models.Time;
import htoyama.timetable.domain.models.Timetable;
import htoyama.timetable.domain.repository.TimetableDao;
import htoyama.timetable.utils.TimeUtils;

import static htoyama.timetable.domain.repository.sqlite.TimetableDbConstants.*;

/**
 * Created by toyamaosamuyu on 2014/12/31.
 */
public class TimetableSqliteDao implements TimetableDao{
    private static final String TAG = TimetableSqliteDao.class.getSimpleName();
    private TimetableDbHelper mHelper;
    private int mLimit = -1;

    public TimetableSqliteDao(final Context context) {
        mHelper = new TimetableDbHelper(context);
    }

    @Override
    public TimetableDao setLimit(int limit) {
        mLimit = limit;
        return this;
    }

    @Override
    public Timetable findBy(int baseId) {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String query = getDefaultSelectQuery(baseId);

        if (mLimit != -1) {
            query += " LIMIT " + mLimit;
        }

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        Timetable timetable = createTimetable(cursor);
        cursor.close();
        db.close();

        return timetable;
    }

    @Override
    public Timetable findBy(int baseId, String afterDepatureTime) {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_TIMETABLE
                + " WHERE"
                + " " + COL_TIMETABLE_BASE_INFO_ID + " = " + baseId;

        if (afterDepatureTime != null) {
            afterDepatureTime = TimeUtils.convertMidnightTimeIfNeeded(afterDepatureTime, false);
            query += " AND"
                    + " " + COL_TIMETABLE_DEPATURE_TIME + " >= '"+afterDepatureTime+"'";
        }

        query = attacheLimitPhraseIfNeeded(query);
        //Log.d(TAG,, query);

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        Timetable timetable = createTimetable(cursor);
        cursor.close();
        db.close();

        return timetable;
    }

    @Override
    public Timetable findBy(int baseId, DayType dayType) {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String query = getDefaultSelectQuery(baseId);
        query += " AND"
                + " " + COL_TIMETABLE_DAY_TYPE + " = '"+dayType.id+"'";

        query = attacheLimitPhraseIfNeeded(query);
        //Log.d(TAG, query);

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        Timetable timetable = createTimetable(cursor);
        cursor.close();
        db.close();

        return timetable;
    }

    @Override
    public Timetable findAll() {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String query = "SELECT * FROM "+TABLE_TIMETABLE;
        query = attacheLimitPhraseIfNeeded(query);

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        Timetable timetable = createTimetable(cursor);
        cursor.close();
        db.close();

        return timetable;
    }


    @Override
    public void addAll(Timetable timetable) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.beginTransaction();

        SQLiteStatement stmt = db.compileStatement(
                "INSERT INTO "+TABLE_TIMETABLE+" VALUES(?, ?, ?, ?, ?);");

        for (Time time : timetable) {
            time.depatureTime = TimeUtils.convertMidnightTimeIfNeeded(time.depatureTime, false);

            stmt.bindLong(1, time.baseInfoId);
            stmt.bindLong(2, time.dayType.id);
            stmt.bindLong(3, time.trainType.id);
            stmt.bindString(4, time.depatureTime);
            stmt.bindString(5, time.destination);
            stmt.execute();
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    @Override
    public void clear() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TABLE_TIMETABLE, null, null);
        db.close();
    }

    private Timetable createTimetable(final Cursor cursor) {
        Timetable timetable = new Timetable();
        final int size = cursor.getCount();
        for (int i = 0; i < size; i++) {
            timetable.add(createTime(cursor));
            cursor.moveToNext();
        }

        return timetable;
    }

    private Time createTime(Cursor cursor) {
        Time time = Time.createWith(cursor);
        time.depatureTime = TimeUtils.convertMidnightTimeIfNeeded(time.depatureTime, true);
        return time;
    }

    private String getDefaultSelectQuery(int baseId) {
        return "SELECT * FROM " + TABLE_TIMETABLE
                + " WHERE"
                + " " + COL_TIMETABLE_BASE_INFO_ID + " = " + baseId;

    }

    private String attacheLimitPhraseIfNeeded(String query) {
        if (mLimit != -1) {
            query += " LIMIT " + mLimit;
        }
        return query;
    }

}
