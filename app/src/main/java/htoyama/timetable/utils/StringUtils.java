package htoyama.timetable.utils;

/**
 * Created by toyamaosamuyu on 2015/01/05.
 */
public class StringUtils {
    private static final String TAG = StringUtils.class.getSimpleName();

    public static String getNewLineChar() {
        return System.getProperty("line.separator");
    }

    private StringUtils(){}
}
