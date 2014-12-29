package htoyama.timetable.domain.models;

/**
 * Created by toyamaosamuyu on 2014/12/30.
 */
public class TopItem {
    public BaseInfo baseInfo;
    public Timetable timetable;

    public TopItem(BaseInfo baseInfo, Timetable timetable) {
        this.baseInfo = baseInfo;
        this.timetable = timetable;
    }
}
