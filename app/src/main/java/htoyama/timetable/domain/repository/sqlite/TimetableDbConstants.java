package htoyama.timetable.domain.repository.sqlite;

/**
 * Created by toyamaosamuyu on 2014/12/30.
 */
public class TimetableDbConstants {

    public static final String TYPE_TEXT = "TEXT";
    public static final String TYPE_INTEGER = "INTEGER";
    public static final String TYPE_BLOB = "BLOB";
    public static final String TYPE_REAL = "REAL";

    public static final String TABLE_BASE_INFOS ="base_infos";
    public static final String COL_BASE_INFOS_ID = "id";
    public static final String COL_BASE_INFOS_STATION = "station";
    public static final String COL_BASE_INFOS_TRAIN = "train";
    public static final String COL_BASE_INFOS_BOUND_FOR_NAME= "bound_for_name";
    public static final String COL_BASE_INFOS_PART_TYPE = "part_type";
    public static final String COL_BASE_INFOS_MODIFIED = "modified";

    public static final String TABLE_TIMETABLE  = "timetable";
    public static final String COL_TIMETABLE_BASE_INFO_ID = "base_info_id";
    public static final String COL_TIMETABLE_DAY_TYPE = "day_type";
    public static final String COL_TIMETABLE_DEPATURE_TIME = "depature_time";
    public static final String COL_TIMETABLE_TRAIN_TYPE = "train_type";
    public static final String COL_TIMETABLE_DESTINATION = "destination";

    private TimetableDbConstants() {}
}
