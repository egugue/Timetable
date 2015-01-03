package htoyama.timetable.domain.models;

import android.content.Context;
import android.content.res.Resources;

import java.util.Calendar;
import java.util.Date;

import htoyama.timetable.R;
import htoyama.timetable.helpers.SharedPreferencesHelper;

import static htoyama.timetable.helpers.SharedPreferencesHelper.Key;

/**
 * 各タイムテーブルが、
 * 通勤時に見たいのか、退勤時に見たいものなのか
 * を決定する
 */
public enum PartType {
    GOING_TO_WORK(0, "出勤時"),
    LEAVING_WORK(1, "退勤時"),
    NONE(2, "設定しない");

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
        throw new IllegalArgumentException("no such enum object for the id: " + id);
    }

    public static PartType valueOf(final Date date, final Context context) {
        final Resources res = context.getResources();
        final SharedPreferencesHelper helper = new SharedPreferencesHelper(context);

        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int hourOfDay = now.get(Calendar.HOUR_OF_DAY);

        int leavingWorkTimeDef = res.getInteger(R.integer.leaving_work_time_threshold_default);
        int leavingWorkTime = helper.getInt(Key.LEAVEING_WORK_TIME_THRESHOLD, leavingWorkTimeDef);

        int goingWorkTimeDef = res.getInteger(R.integer.going_work_time_threshold_default);
        int goningWorkTime = helper.getInt(Key.GOING_TO_WORK_TIME_THRESHOLD, goingWorkTimeDef);

        return valueOf(hourOfDay, goningWorkTime, leavingWorkTime);
    }

    /**
     *
     * @param hourOfDay 現在の時刻 24時間表記で
     * @param goingWorkTime 出勤時間に切り替わる閾値
     * @param leavingWorkTime 退勤時間に切り替わる閾値
     * @return
     */
    static PartType valueOf(int hourOfDay, int goingWorkTime, int leavingWorkTime) {

        //出勤時間 < 退勤時間の場合
        if (goingWorkTime < leavingWorkTime) {

            if (goingWorkTime <= hourOfDay
                    && hourOfDay < leavingWorkTime) {
                return GOING_TO_WORK;
            }
            return LEAVING_WORK;
        }

        //以下は 退勤時間 < 出勤時間の場合 (例えば夜勤のケース)

        if ( leavingWorkTime <= hourOfDay &&
                hourOfDay < goingWorkTime )  {
           return LEAVING_WORK;
        }

        return GOING_TO_WORK;
    }

}
