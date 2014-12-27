package htoyama.timetable.domain.repository;

import android.util.Log;

import htoyama.timetable.domain.models.Timetable;
import htoyama.timetable.presentation.views.StateFrameLayout;
import htoyama.timetable.tools.WorkerThreadExecutor;

import static htoyama.timetable.domain.models.Time.DayType;

/**
 * Created by toyamaosamuyu on 2014/12/28.
 */
public class TimeableLoader {
    private static final String TAG = TimeableLoader.class.getSimpleName();
    private OnLoadTimetableCompleteListener mListener;

    public static interface OnLoadTimetableCompleteListener {
        public void onLoadTimetableComplete(Timetable timetable);
    }

    public void loadTimeline(final int baseId, final DayType dayType,
                             final OnLoadTimetableCompleteListener listener) {

        Runnable command = new Runnable() {
            @Override
            public void run() {
                TimetableDao timetableDao = new TimetableDaoStub();
                Timetable timetable = timetableDao.findBy(baseId, dayType);
                listener.onLoadTimetableComplete(timetable);
            }
        };

        WorkerThreadExecutor.getInstance()
                .execute(command);
    }
}
