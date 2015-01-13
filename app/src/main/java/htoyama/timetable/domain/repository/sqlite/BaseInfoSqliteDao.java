package htoyama.timetable.domain.repository.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.domain.models.PartType;
import htoyama.timetable.domain.repository.BaseInfoDao;
import htoyama.timetable.utils.BaseInfoUtils;

import static htoyama.timetable.domain.repository.sqlite.TimetableDbConstants.COL_BASE_INFOS_MODIFIED;
import static htoyama.timetable.domain.repository.sqlite.TimetableDbConstants.TABLE_BASE_INFOS;
import static htoyama.timetable.domain.repository.sqlite.TimetableDbConstants.COL_BASE_INFOS_ID;
import static htoyama.timetable.domain.repository.sqlite.TimetableDbConstants.COL_BASE_INFOS_TRAIN;
import static htoyama.timetable.domain.repository.sqlite.TimetableDbConstants.COL_BASE_INFOS_STATION;
import static htoyama.timetable.domain.repository.sqlite.TimetableDbConstants.COL_BASE_INFOS_BOUND_FOR_NAME;
import static htoyama.timetable.domain.repository.sqlite.TimetableDbConstants.COL_BASE_INFOS_PART_TYPE;

/**
 * Created by toyamaosamuyu on 2014/12/30.
 */
public class BaseInfoSqliteDao implements BaseInfoDao{
    private static final String TAG = BaseInfoSqliteDao.class.getSimpleName();
    private TimetableDbHelper mHelper;

    public BaseInfoSqliteDao(final Context context) {
        mHelper = new TimetableDbHelper(context);
    }

    @Override
    public BaseInfo findById(int id) {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String query =
                "SELECT * FROM " + TABLE_BASE_INFOS
                + " WHERE"
                + " " + COL_BASE_INFOS_ID + " = " + id;

        Cursor cursor = db.rawQuery(query, null);
        if (!cursor.moveToFirst()) {
            return null;
        }
        BaseInfo baseInfo = BaseInfo.createWith(cursor);
        cursor.close();
        db.close();

        return baseInfo;
    }

    @Override
    public List<BaseInfo> findBy(PartType partType) {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String query =
                "SELECT * FROM " + TABLE_BASE_INFOS
                        + " WHERE"
                        + " " + COL_BASE_INFOS_PART_TYPE + " = " + partType.id;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        List<BaseInfo> baseInfoList = createBaeInfoList(cursor);
        cursor.close();
        db.close();

        return baseInfoList;
    }

    @Override
    public int getLatestId() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String query =
                "SELECT MAX("+COL_BASE_INFOS_ID+")"
                + " FROM " + TABLE_BASE_INFOS;

        Cursor cursor = db.rawQuery(query, null);

        int lastId = 0; //デフォルト
        if (cursor.moveToNext()) {
            lastId = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return lastId;
    }

    @Override
    public List<BaseInfo> findAll() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_BASE_INFOS, null);

        cursor.moveToFirst();
        List<BaseInfo> baseInfoList = createBaeInfoList(cursor);
        cursor.close();
        db.close();

        return baseInfoList;
    }

    private List<BaseInfo> createBaeInfoList(Cursor cursor) {
        List<BaseInfo> baseInfoList = new ArrayList<>();

        final int size = cursor.getCount();
        for (int i = 0; i < size; i++) {
            baseInfoList.add(BaseInfo.createWith(cursor));
            cursor.moveToNext();
        }

        return baseInfoList;
    }

    @Override
    public void addAll(List<BaseInfo> baseInfoList) {

    }

    @Override
    public void add(BaseInfo baseInfo) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        if (baseInfo.modified == null) {
            baseInfo.modified = new Date();
        }
        String modified = BaseInfoUtils.stringizeModified(baseInfo.modified);

        ContentValues values = new ContentValues();
        values.put(COL_BASE_INFOS_STATION, baseInfo.station);
        values.put(COL_BASE_INFOS_TRAIN, baseInfo.train);
        values.put(COL_BASE_INFOS_BOUND_FOR_NAME, baseInfo.boundForName);
        values.put(COL_BASE_INFOS_PART_TYPE, baseInfo.partType.id);
        values.put(COL_BASE_INFOS_MODIFIED, modified);

        db.insert(TABLE_BASE_INFOS, null, values);
        db.close();
    }

    @Override
    public void clear() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TABLE_BASE_INFOS, null, null);
        db.close();
    }

}
