package htoyama.timetable.domain.models;

import android.content.Context;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import htoyama.timetable.R;
import htoyama.timetable.helpers.SharedPreferencesHelper;

/**
 * 各タイムテーブルが、
 * 通勤時に見たいのか、退勤時に見たいものなのか
 * を決定する
 */
public enum PartType {
    GO_TO_WORK(1, "出勤時"),
    LEAVING_WORK(2, "退勤時"),
    NONE(3, "設定しない");

    public final int id;
    public final String name;

    private PartType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static PartType valueOf(int id) {
        for (PartType partType : values()) {
            if (partType.id == id) {
                return partType;
            }
        }
        return null;
    }

    public static PartType valueOf(Date date, final Context context) {
        final int defaultTime = context.getResources().getInteger(R.integer.default_to_leaving_work_time);
        final int toLeavingWorkTime = new SharedPreferencesHelper(context)
                .getInt(SharedPreferencesHelper.Key.TO_LEAVEING_WORK_TIME, defaultTime);

        Calendar now = Calendar.getInstance();
        now.setTime(date);

        if (toLeavingWorkTime <= Calendar.HOUR_OF_DAY) {
            return LEAVING_WORK;
        }

        return GO_TO_WORK;
    }

}
