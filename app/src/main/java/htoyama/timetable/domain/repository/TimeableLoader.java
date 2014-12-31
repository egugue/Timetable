package htoyama.timetable.domain.repository;

import android.content.Context;
import android.util.Log;

import htoyama.timetable.domain.models.DayType;
import htoyama.timetable.domain.models.Timetable;
import htoyama.timetable.domain.repository.sqlite.TimetableSqliteDao;
import htoyama.timetable.tools.MainThreadExecutor;
import htoyama.timetable.tools.WorkerThreadExecutor;


/**
 * Created by toyamaosamuyu on 2014/12/28.
 */
public class TimeableLoader {
    private static final String TAG = TimeableLoader.class.getSimpleName();
    private OnLoadTimetableCompleteListener mListener;
    private Context mContext;

    public static interface OnLoadTimetableCompleteListener {
        public void onLoadTimetableComplete(Timetable timetable);
    }

    public TimeableLoader(final Context context) {
        mContext = context;
    }

    public void loadTimeline(final int baseId, final DayType dayType,
                             final OnLoadTimetableCompleteListener listener) {

        Runnable command = new Runnable() {
            @Override
            public void run() {
                //TimetableDao timetableDao = new TimetableDaoStub();
                TimetableDao timetableDao = new TimetableSqliteDao(mContext);
                final Timetable timetable = timetableDao.findBy(baseId);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                MainThreadExecutor.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                        listener.onLoadTimetableComplete(timetable);
                    }
                });
            }
        };

        WorkerThreadExecutor.getInstance()
                .execute(command);
    }
}
