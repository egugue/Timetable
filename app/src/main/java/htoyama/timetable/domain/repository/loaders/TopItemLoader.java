package htoyama.timetable.domain.repository.loaders;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.domain.models.PartType;
import htoyama.timetable.domain.models.Timetable;
import htoyama.timetable.domain.models.TopItem;
import htoyama.timetable.domain.repository.BaseInfoDao;
import htoyama.timetable.domain.repository.TimetableDao;
import htoyama.timetable.events.BusHolder;
import htoyama.timetable.events.LoadTopItemListCompleteEvent;
import htoyama.timetable.tools.MainThreadExecutor;
import htoyama.timetable.tools.WorkerThreadExecutor;
import htoyama.timetable.utils.TimeUtils;

/**
 * Created by toyamaosamuyu on 2014/12/30.
 */
public class TopItemLoader {
    private BaseInfoDao mBaseInfoDao;
    private TimetableDao mTimetableDao;
    private Context mContext;

    @Inject
    public TopItemLoader(Application app, BaseInfoDao baseInfoDao, TimetableDao timetableDao) {
        mContext = app.getApplicationContext();
        mBaseInfoDao = baseInfoDao;
        mTimetableDao = timetableDao;
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
        final String currentHhMm24 = TimeUtils.stringizeDepatureTime(new Date());
        final String currentHhMm00 = TimeUtils.convertMidnightTimeIfNeeded(currentHhMm24, true);

        PartType partType = PartType.valueOf(new Date(), mContext);
        List<BaseInfo> baseInfoList = mBaseInfoDao.findBy(partType);

        List<TopItem> topItemList = new ArrayList<>();

        for (BaseInfo baseInfo : baseInfoList) {

            Timetable timetable = mTimetableDao.findBy(baseInfo.id, currentHhMm24);
            if (timetable.isEmpty()) {
                //0時表記で再度検索し、早朝のタイムテーブルを取得する
                timetable = mTimetableDao.findBy(baseInfo.id, currentHhMm00);
            }

            topItemList.add(new TopItem(baseInfo, timetable));
        }

        return topItemList;
    }

}
