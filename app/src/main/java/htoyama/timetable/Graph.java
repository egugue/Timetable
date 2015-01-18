package htoyama.timetable;

import android.app.Application;

import htoyama.timetable.presentation.activities.TopActivity;

/**
 * Created by toyamaosamuyu on 2015/01/14.
 */
public interface Graph {
    void inject(TimetableApp timetableApp);
    void inject(TopActivity topActivity);

    Application application();
}
