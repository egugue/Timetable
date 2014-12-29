package htoyama.timetable.domain.repository;

import android.os.Handler;
import android.os.Looper;

import java.util.List;

import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.tools.MainThreadExecutor;
import htoyama.timetable.tools.WorkerThreadExecutor;

/**
 * Created by toyamaosamuyu on 2014/12/29.
 */
public class BaseInfoLoader {
    private static final String TAG = BaseInfoLoader.class.getSimpleName();

    public static interface OnLoadCompleteListener {
        public void onLoadeAllBaseInfo(List<BaseInfo> list);
    }

    public void loadAllBaseInfo(final OnLoadCompleteListener listener) {
        Runnable command = new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                BaseInfoDaoStub dao = new BaseInfoDaoStub();
                final List<BaseInfo> list = dao.findAll();

                MainThreadExecutor.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                        listener.onLoadeAllBaseInfo(list);
                    }
                });

            }
        };

        WorkerThreadExecutor.getInstance().execute(command);
    }
}
