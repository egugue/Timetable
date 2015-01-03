package htoyama.timetable.domain.repository.loaders;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.domain.models.PartType;
import htoyama.timetable.domain.models.Timetable;
import htoyama.timetable.domain.models.TopItem;
import htoyama.timetable.domain.repository.BaseInfoDao;
import htoyama.timetable.domain.repository.TimetableDao;
import htoyama.timetable.domain.repository.sqlite.BaseInfoSqliteDao;
import htoyama.timetable.domain.repository.sqlite.TimetableSqliteDao;
import htoyama.timetable.events.BusHolder;
import htoyama.timetable.events.LoadTopItemListCompleteEvent;
import htoyama.timetable.tools.MainThreadExecutor;
import htoyama.timetable.tools.WorkerThreadExecutor;

/**
 * Created by toyamaosamuyu on 2014/12/30.
 */
public class TopItemLoader {
    private final SimpleDateFormat mSdf = new SimpleDateFormat("kk:mm");
    private Context mContext;

    public TopItemLoader(final Context context) {
        mContext = context;
    }

    public void loadTopItemList() {

        Runnable command = new Runnable() {
            @Override
            public void run() {
                final List<TopItem> list = getTopItemList();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                MainThreadExecutor.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                        BusHolder.getBus().post(
                                new LoadTopItemListCompleteEvent(list)
                        );
                    }
                });
            }
        };

        WorkerThreadExecutor.getInstance().execute(command);
    }

    private List<TopItem> getTopItemList() {
        BaseInfoDao baseInfoDao = new BaseInfoSqliteDao(mContext);
        TimetableDao timetableDao = new TimetableSqliteDao(mContext);

        final String currentHhMm = mSdf.format(new Date());
        List<TopItem> topItemList = new ArrayList<>();

        PartType partType = PartType.valueOf(new Date(), mContext);
        List<BaseInfo> baseInfoList = baseInfoDao.findBy(partType);

        for (BaseInfo baseInfo : baseInfoList) {
            Timetable timetable = timetableDao.findBy(baseInfo.id, currentHhMm);
            topItemList.add(new TopItem(baseInfo, timetable));
        }

        return topItemList;
    }

}
