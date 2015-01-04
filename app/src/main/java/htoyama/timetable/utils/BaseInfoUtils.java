package htoyama.timetable.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by toyamaosamuyu on 2015/01/04.
 */
public class BaseInfoUtils {
    private static final String TAG = BaseInfoUtils.class.getSimpleName();

    public static String stringizeModified(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd kk-mm-ss").format(date);
    }

    private BaseInfoUtils(){}
}
