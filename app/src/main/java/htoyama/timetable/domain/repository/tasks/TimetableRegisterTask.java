package htoyama.timetable.domain.repository.tasks;

import android.content.Context;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.List;

import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.domain.models.DayType;
import htoyama.timetable.domain.models.Time;
import htoyama.timetable.domain.models.Timetable;
import htoyama.timetable.domain.models.TrainType;
import htoyama.timetable.domain.repository.BaseInfoDao;
import htoyama.timetable.domain.repository.TimetableDao;
import htoyama.timetable.domain.repository.TimetableFileParser;
import htoyama.timetable.domain.repository.sqlite.BaseInfoSqliteDao;
import htoyama.timetable.domain.repository.sqlite.TimetableSqliteDao;
import htoyama.timetable.tools.MainThreadExecutor;
import htoyama.timetable.tools.WorkerThreadExecutor;

import static htoyama.timetable.domain.repository.TimetableFileParser.Item;

/**
 * Tha task that is registers repository with each inputed value
 */
public class TimetableRegisterTask {
    private static final String TAG = TimetableRegisterTask.class.getSimpleName();
    private Context mContext;

    public TimetableRegisterTask(final Context context) {
        mContext = context;
    }

    /**
     * Registers repository with each inputed value
     *
     * @param baseInfo the {@link htoyama.timetable.domain.models.BaseInfo} to register repository
     * @param dayTypeId the Id of {@link htoyama.timetable.domain.models.DayType} to register repository
     * @param filePath the file path to read as timetable file
     */
    public void register(final BaseInfo baseInfo,
                         final int dayTypeId, final String filePath) {

        WorkerThreadExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                final int latestId = registerBaseInfo(baseInfo);
                try {
                    registerTimetable(latestId, dayTypeId, filePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                MainThreadExecutor.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "OK", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private int registerBaseInfo(BaseInfo baseInfo) {
        BaseInfoDao dao = new BaseInfoSqliteDao(mContext);
        //dao.add(baseInfo);
        return dao.getLatestId();
    }

    private void registerTimetable(int baseInfoId, int dayTypeId, String filePath) throws FileNotFoundException {
        List<Item> itemList = new TimetableFileParser().parse(filePath);

        Timetable timetable = createTimetable(baseInfoId, dayTypeId, itemList);
        TimetableDao dao = new TimetableSqliteDao(mContext);
        //dao.addAll(timetable);
    }

    private Timetable createTimetable(int baseInfoId, int dayTypeId, List<Item> itemList) {
        Timetable timetable = new Timetable();

        Time time;
        for (Item item : itemList) {
            time = new Time(baseInfoId, DayType.valueOf(dayTypeId),
                    TrainType.valueOf(item.trainId), item.depatureTime, item.destination);
            timetable.add(time);
        }

        return timetable;
    }

}
