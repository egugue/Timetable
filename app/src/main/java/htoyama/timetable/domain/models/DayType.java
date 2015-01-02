package htoyama.timetable.domain.models;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by toyamaosamuyu on 2014/12/31.
 */
public enum DayType {
    WEEKDAY(1, "平日"),
    SATURDAY(2, "土曜"),
    HOLIDAY(3, "休日");

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
        return null;
    }

    public static DayType valueOf(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);

        switch (now.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SATURDAY:
                return SATURDAY;
            case Calendar.MONDAY:
                return HOLIDAY;
            default:
                return WEEKDAY;
        }

    }

}
