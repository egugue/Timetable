package htoyama.timetable.tools;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * Created by toyamaosamuyu on 2014/12/30.
 */
public class MainThreadExecutor implements Executor{
    private static final String TAG = MainThreadExecutor.class.getSimpleName();
    private static MainThreadExecutor sInstance;
    private Handler mHandler;

    public static MainThreadExecutor getInstance() {
        if (sInstance != null) {
            return sInstance;
        }

        synchronized (MainThreadExecutor.class) {
            if (sInstance == null) {
                sInstance = new MainThreadExecutor();
            }
        }
        return sInstance;
    }

    @Override
    public void execute(Runnable command) {
        mHandler.post(command);
    }

    private MainThreadExecutor() {
        mHandler = new Handler(Looper.getMainLooper());
    }

}
