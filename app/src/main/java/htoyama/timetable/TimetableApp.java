package htoyama.timetable;

import android.app.Application;
import android.content.Context;

import htoyama.timetable.domain.repository.sqlite.BaseInfoSqliteDao;

/**
 * Created by toyamaosamuyu on 2015/01/14.
 */
public class TimetableApp extends Application{
    private static final String TAG = TimetableApp.class.getSimpleName();

    private static TimetableApp sInstance;
    private Graph mGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mGraph = Graph.Initializer.init(this, false);
    }

    public static TimetableApp getInstance() {
        return sInstance;
    }

    public static TimetableApp get(final Context context) {
        return (TimetableApp) context.getApplicationContext();
    }

    public Graph getGraph() {
        return mGraph;
    }

    public TimetableApp setMockMode(boolean useMock) {
        mGraph = Graph.Initializer.init(this, useMock);
        return this;
    }

}
