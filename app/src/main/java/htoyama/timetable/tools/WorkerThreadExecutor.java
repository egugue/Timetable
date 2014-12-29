package htoyama.timetable.tools;

import android.os.Handler;
import android.os.HandlerThread;

import java.util.concurrent.Executor;

/**
 * Created by toyamaosamuyu on 2014/12/28.
 */
public class WorkerThreadExecutor implements Executor{
    private static WorkerThreadExecutor sInstance;

    private final Handler mHandler;
    private final HandlerThread mHandlerThread;

    private WorkerThreadExecutor() {
        mHandlerThread = new HandlerThread("WORKER");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    public static WorkerThreadExecutor getInstance() {
        if (sInstance != null) {
            return sInstance;
        }

        synchronized (WorkerThreadExecutor.class) {
            if (sInstance == null) {
                sInstance = new WorkerThreadExecutor();
            }
        }
        return sInstance;
    }

    @Override
    public void execute(Runnable command) {
        mHandler.post(command);
    }

}
