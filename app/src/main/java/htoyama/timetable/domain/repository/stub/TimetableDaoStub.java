package htoyama.timetable.domain.repository.stub;

import java.util.Date;

import htoyama.timetable.domain.models.DayType;
import htoyama.timetable.domain.models.Time;
import htoyama.timetable.domain.models.Timetable;
import htoyama.timetable.domain.models.TrainType;
import htoyama.timetable.domain.repository.TimetableDao;


/**
 * Created by toyamaosamuyu on 2014/12/27.
 */
public class TimetableDaoStub implements TimetableDao {
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
    public TimetableDao setLimit(int limit) {
        return null;
    }

    @Override
    public Timetable findBy(int baseId) {
        final Timetable timetable = new Timetable();
        if (baseId != 1) {
            return timetable;
        }

        for(int i = 0; i < 30; i++) {
            Time time = new Time(baseId, TrainType.EXPRESS, "10:00", "新宿行き");
            timetable.add(time);
        }

        return timetable;
    }

    @Override
    public Timetable findBy(int baseId, String afterDepatureTIme) {
        return null;
    }

    @Override
    public Timetable findAll() {
        return null;
    }

    @Override
    public void addAll(Timetable timetable) {
    }

    @Override
    public void clear() {
    }

}
