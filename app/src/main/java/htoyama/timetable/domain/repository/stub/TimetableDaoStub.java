package htoyama.timetable.domain.repository.stub;

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

    @Override
    public TimetableDao setLimit(int limit) {
        return this;
    }

    @Override
    public Timetable findBy(int baseId) {
        final Timetable timetable = new Timetable();
        if (baseId != 1) {
            return timetable;
        }

        for(int i = 0; i < 30; i++) {
            Time time = new Time(baseId, DayType.WEEKDAY, TrainType.EXPRESS, i+":00", "新宿行き");
            timetable.add(time);
        }

        return timetable;
    }

    @Override
    public Timetable findBy(int baseId, String afterDepatureTIme) {
        return findBy(baseId);
    }

    @Override
    public Timetable findBy(int baseId, DayType dayType) {
        return findBy(baseId);
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
