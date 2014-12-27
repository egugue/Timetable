package htoyama.timetable.events;

import htoyama.timetable.domain.models.Timetable;

/**
 * Created by toyamaosamuyu on 2014/12/28.
 */
public class LoadTimetableCompleteEvent {
    private Timetable mTimetable;

    public LoadTimetableCompleteEvent(Timetable timetable) {
        mTimetable = timetable;
    }

    public Timetable getTimetable() {
        return mTimetable;
    }

}
