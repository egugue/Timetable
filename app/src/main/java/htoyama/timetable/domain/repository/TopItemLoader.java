package htoyama.timetable.domain.repository;

import java.util.ArrayList;
import java.util.List;

import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.domain.models.Time;
import htoyama.timetable.domain.models.Timetable;
import htoyama.timetable.domain.models.TopItem;
import htoyama.timetable.events.BusHolder;
import htoyama.timetable.events.LoadTopItemListCompleteEvent;
import htoyama.timetable.tools.MainThreadExecutor;
import htoyama.timetable.tools.WorkerThreadExecutor;

/**
 * Created by toyamaosamuyu on 2014/12/30.
 */
public class TopItemLoader {

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

        BaseInfo.Type type = BaseInfo.Type.GO_TO_WORK;
        BaseInfoDao baseInfoDao  = new BaseInfoDaoStub();
        List<BaseInfo> baseInfoList = baseInfoDao.findBy(type);

        TimetableDao timetableDao = new TimetableDaoStub();

        List<TopItem> topItemList = new ArrayList<>();
        for (BaseInfo baseInfo : baseInfoList) {
            Timetable timetable = timetableDao.findBy(1, Time.DayType.HOLIDAY);
            topItemList.add(new TopItem(baseInfo, timetable));
        }

        return topItemList;
    }

}
