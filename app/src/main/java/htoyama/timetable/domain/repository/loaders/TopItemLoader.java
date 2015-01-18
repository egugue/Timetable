package htoyama.timetable.domain.repository.loaders;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import htoyama.timetable.RxUtils;
import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.domain.models.PartType;
import htoyama.timetable.domain.models.Timetable;
import htoyama.timetable.domain.models.TopItem;
import htoyama.timetable.domain.repository.BaseInfoDao;
import htoyama.timetable.domain.repository.TimetableDao;
import htoyama.timetable.domain.repository.sqlite.BaseInfoSqliteDao;
import htoyama.timetable.domain.repository.sqlite.TimetableSqliteDao;
import htoyama.timetable.domain.repository.stub.BaseInfoDaoStub;
import htoyama.timetable.domain.repository.stub.TimetableDaoStub;
import htoyama.timetable.events.BusHolder;
import htoyama.timetable.events.LoadTopItemListCompleteEvent;
import htoyama.timetable.tools.MainThreadExecutor;
import htoyama.timetable.tools.WorkerThreadExecutor;
import htoyama.timetable.utils.TimeUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by toyamaosamuyu on 2014/12/30.
 */
public class TopItemLoader {
    private Context mContext;

    public TopItemLoader(final Context context) {
        mContext = context;
    }

    private <T> Observable<T> load(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<TopItem>> loadAll() {


        List<BaseInfo> list = new BaseInfoSqliteDao(mContext).findAll();

        Observable.from(list)
                .map(new Func1<BaseInfo, Integer>() {
                    @Override
                    public Integer call(BaseInfo baseInfo) {
                        return baseInfo.id;
                    }
                })
                .
                .map(new FindTimetableByBaseId())

        RxUtils.toObservable(new BaseFunc())
                .forEach(new Action1<List<BaseInfo>>() {
                    @Override
                    public void call(List<BaseInfo> baseInfoList) {

                    }
                })
                .flatMap(new Func1<List<BaseInfo>, Observable<BaseInfo>>() {
                    @Override
                    public Observable<BaseInfo> call(List<BaseInfo> baseInfoList) {
                        for (BaseInfo baseInfo : baseInfoList) {
                            return Observable.from(baseInfoList);
                        }
                    }
                })


        return load(
                getAllBaseStream(mContext).flatMap(new Func1<List<BaseInfo>, Observable<List<TopItem>>() {
                    @Override
                    public Observable<List<TopItem>> call(List<BaseInfo> baseInfoList) {
                        return null;
                    }
                })
        );
    }




    private class FindTimetableByBaseId implements Func1<Integer, Timetable> {
        @Override
        public Timetable call(Integer baseId) {
            TimetableDao dao = new TimetableSqliteDao(mContext);
            return dao.findBy(baseId);
        }
    }









    public Observable<List<TopItem>> getTopItemListStream(final ) {
        return Observable.create(new Observable.OnSubscribe<List<TopItem>>() {
            @Override
            public void call(Subscriber<? super List<TopItem>> subscriber) {

            }
        });
    }

    public Observable<List<BaseInfo>> getAllBaseStream(final Context context) {
        return Observable.create(new Observable.OnSubscribe<List<BaseInfo>>() {
            @Override
            public void call(Subscriber<? super List<BaseInfo>> subscriber) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                List<BaseInfo> list = new BaseInfoSqliteDao(context).findAll();
                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        });

    }


    public Observable<List<TopItem>> getTopItemStream(final Context context) {
        getAllBaseStream(context)
                .flatMap(new Func1<List<BaseInfo>, Observable<Timetable>>() {
                    @Override
                    public Observable<Timetable> call(List<BaseInfo> baseInfoList) {
                    }
                });

    }

    private Observable<List<TopItem>> getTopItemStream(final Context context, final List<BaseInfo> baseInfoList) {
        return Observable.create(new Observable.OnSubscribe<List<TopItem>>() {
             @Override
             public void call(Subscriber<? super List<TopItem>> subscriber) {
                 List<TopItem> list = new ArrayList<>();
                 for(BaseInfo baseInfo : baseInfoList) {
                     Timetable timetable = new TimetableSqliteDao(context).findBy(baseInfo.id);
                     list.add(new TopItem(baseInfo, timetable));
                 }

                 subscriber.onNext(list);
                 subscriber.onCompleted();

             }
        };
    }







    private class BaseFunc implements Func0<List<BaseInfo>> {
        @Override
        public List<BaseInfo> call() {
            return new BaseInfoDaoStub().findAll();
        }
    }






    public Observable<Timetable> getTimetble(final BaseInfo baseInfo) {

        return Observable.create(new Observable.OnSubscribe<Timetable>() {
            @Override
            public void call(Subscriber<? super Timetable> subscriber) {
                final String currentHhMm24 = TimeUtils.stringizeDepatureTime(new Date());
                final String currentHhMm00 = TimeUtils.convertMidnightTimeIfNeeded(currentHhMm24, true);

                TimetableDao timetableDao = new TimetableSqliteDao(mContext);
                Timetable timetable = timetableDao.findBy(baseInfo.id, currentHhMm24);
                if (timetable.isEmpty()) {
                    //0時表記で再度検索し、早朝のタイムテーブルを取得する
                    timetable = timetableDao.findBy(baseInfo.id, currentHhMm00);
                }
            }
        });

    }

    private List<TopItem> getTopItemList() {
        BaseInfoDao baseInfoDao = new BaseInfoSqliteDao(mContext);
        TimetableDao timetableDao = new TimetableSqliteDao(mContext);

        final String currentHhMm24 = TimeUtils.stringizeDepatureTime(new Date());
        final String currentHhMm00 = TimeUtils.convertMidnightTimeIfNeeded(currentHhMm24, true);

        PartType partType = PartType.valueOf(new Date(), mContext);
        List<BaseInfo> baseInfoList = baseInfoDao.findBy(partType);

        List<TopItem> topItemList = new ArrayList<>();

        for (BaseInfo baseInfo : baseInfoList) {

            Timetable timetable = timetableDao.findBy(baseInfo.id, currentHhMm24);
            if (timetable.isEmpty()) {
                //0時表記で再度検索し、早朝のタイムテーブルを取得する
                timetable = timetableDao.findBy(baseInfo.id, currentHhMm00);
            }

            topItemList.add(new TopItem(baseInfo, timetable));
        }

        return topItemList;
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

}
