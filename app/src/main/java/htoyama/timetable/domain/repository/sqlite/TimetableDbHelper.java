package htoyama.timetable.domain.repository.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static htoyama.timetable.domain.repository.sqlite.TimetableDbConstants.*;

/**
 * Created by toyamaosamuyu on 2014/12/30.
 */
public class TimetableDbHelper extends SQLiteOpenHelper{
    private static final String TAG = TimetableDbHelper.class.getSimpleName();
    private static final String DB_NAME = "timetable.db";
    private static final int DB_VERSION = 1;

    public TimetableDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createBaseInfosTable(db);
        createTimetableTable(db);
    }

    private void createBaseInfosTable(final SQLiteDatabase db) {
        Log.d("HOGE", "createTimetableTable();");

        String createSql =
                "CREATE TABLE IF NOT EXISTS "+TABLE_BASE_INFOS
                + "("
                + " "+COL_BASE_INFOS_ID+" "+TYPE_INTEGER+" "+"PRIMARY KEY AUTOINCREMENT"
                + ", "+COL_BASE_INFOS_STATION+" "+TYPE_TEXT
                + ", "+COL_BASE_INFOS_TRAIN+" "+TYPE_TEXT
                + ", "+COL_BASE_INFOS_BOUND_FOR_NAME+" "+TYPE_TEXT
                + ", "+COL_BASE_INFOS_DAY_TYPE+" "+TYPE_INTEGER
                + ", "+COL_BASE_INFOS_PART_TYPE+" "+TYPE_INTEGER
                + ", "+COL_BASE_INFOS_MODIFIED+" "+TYPE_TEXT
                + ");";

        Log.d("HOGE", createSql);

        db.execSQL(createSql);
    }

    private void createTimetableTable(final SQLiteDatabase db) {
        String createSql =
                "CREATE TABLE IF NOT EXISTS "+TABLE_TIMETABLE
                + "("
                + " "+COL_TIMETABLE_BASE_INFO_ID+" "+TYPE_INTEGER
                + ", "+COL_TIMETABLE_TRAIN_TYPE+" "+TYPE_INTEGER
                + ", "+COL_TIMETABLE_DEPATURE_TIME+" "+TYPE_TEXT
                + ", "+COL_TIMETABLE_DESTINATION+" "+TYPE_TEXT
                + ");";

        db.execSQL(createSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
