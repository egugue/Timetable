package htoyama.timetable.domain.models;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by toyamaosamuyu on 2014/12/31.
 */
public enum DayType {
    WEEKDAY(0, "平日"),
    SATURDAY(1, "土曜"),
    HOLIDAY(2, "休日");

    public final int id;
    public final String name;

    private DayType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DayType valueOf(int id) {
        for (DayType dayType : values()) {
            if (dayType.id == id) {
                return dayType;
            }
        }
        throw new IllegalArgumentException("no such enum object for the id: " + id);
    }

    public static DayType valueOf(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);

        switch (now.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SATURDAY:
                return SATURDAY;
            case Calendar.SUNDAY:
                return HOLIDAY;
            default:
                return WEEKDAY;
        }

    }

}
