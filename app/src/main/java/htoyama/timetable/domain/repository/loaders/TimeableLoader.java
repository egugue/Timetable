package htoyama.timetable.domain.repository.loaders;

import android.content.Context;

import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.domain.models.DayType;
import htoyama.timetable.domain.models.Timetable;
import htoyama.timetable.domain.repository.TimetableDao;
import htoyama.timetable.domain.repository.sqlite.TimetableSqliteDao;
import htoyama.timetable.events.BusHolder;
import htoyama.timetable.events.LoadTimetableCompleteEvent;
import htoyama.timetable.tools.MainThreadExecutor;
import htoyama.timetable.tools.WorkerThreadExecutor;


/**
 * Created by toyamaosamuyu on 2014/12/28.
 */
public class TimeableLoader {
    private static final String TAG = TimeableLoader.class.getSimpleName();
    private Context mContext;

    public TimeableLoader(final Context context) {
        mContext = context;
    }

    public void loadTimeline(final BaseInfo baseInfo, final DayType dayType) {

        Runnable command = new Runnable() {
            @Override
            public void run() {
                final Timetable timetable = getTimetableList(baseInfo, dayType);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                MainThreadExecutor.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                        BusHolder.getBus().post(new LoadTimetableCompleteEvent(timetable));
                    }
                });
            }
        };

        WorkerThreadExecutor.getInstance()
                .execute(command);
    }

    private Timetable getTimetableList(BaseInfo baseInfo, DayType dayType) {
        TimetableDao timetableDao = new TimetableSqliteDao(mContext);
        return timetableDao.findBy(baseInfo.id, dayType);
    }
}
