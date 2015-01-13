package htoyama.timetable.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import htoyama.timetable.domain.models.Time;

/**
 * Created by toyamaosamuyu on 2015/01/01.
 */
public class TimeUtils {
    private static final String TAG = TimeUtils.class.getSimpleName();

    private TimeUtils(){}

    public static String convertMidnightTimeIfNeeded(String depatureTime, boolean isToZero) {
        String[] values = depatureTime.split(":");
        String hour = values[0];
        String minute = values[1];

        if (isToZero && hour.equals("24")) {
            hour = "00";
        } else if (!isToZero && hour.equals("00")) {
            hour = "24";
        }

        return hour+":"+minute;
    }

    /**
     * 0時の場合は24時で返す
     * @param date
     * @return
     */
    public static String stringizeDepatureTime(Date date) {
        return  new SimpleDateFormat("kk:mm").format(date);
    }

    /**
     * TODO : {@link Time}の移譲するか検討
     * @param time
     * @param depatureTime
     * @return
     */
    public static int compareToForDepatureTime(final Time time, String depatureTime) {
        String timeDepatureTime  = TimeUtils.convertMidnightTimeIfNeeded(time.depatureTime, false);
        return timeDepatureTime.compareTo(depatureTime);
    }

}
