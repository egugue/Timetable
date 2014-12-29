package htoyama.timetable.domain.repository;

import java.util.Date;

import htoyama.timetable.domain.models.Time;
import htoyama.timetable.domain.models.Timetable;

import static htoyama.timetable.domain.models.Time.DayType;
import static htoyama.timetable.domain.models.Time.TrainType;

/**
 * Created by toyamaosamuyu on 2014/12/27.
 */
public class TimetableDaoStub implements TimetableDao{
    private static final String TAG = TimetableDaoStub.class.getSimpleName();

    /*
    @Override
    public Timetable findById(int id) {
        return null;
    }

    @Override
    public Timetable findByBaseInfoId(int id) {
        final Timetable timetable = new Timetable();
        if (id != 1) {
            return timetable;
        }

        for(int i = 0; i < 30; i++) {
            Time time = new Time(i, id, null, null, null, null);
            timetable.add(time);
        }

        return timetable;
    }
    */

    @Override
    public Timetable findBy(int baseId, Time.DayType dayType) {
        final Timetable timetable = new Timetable();
        if (baseId != 1) {
            return timetable;
        }

        for(int i = 0; i < 30; i++) {
            Time time = new Time(i, baseId, dayType, TrainType.EXPRESS, new Date(), "新宿行き");
            timetable.add(time);
        }

        return timetable;
    }
}
