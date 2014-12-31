package htoyama.timetable.domain.repository.stub;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.domain.models.DayType;
import htoyama.timetable.domain.models.PartType;
import htoyama.timetable.domain.repository.BaseInfoDao;

/**
 * Created by toyamaosamuyu on 2014/12/27.
 */
public class BaseInfoDaoStub implements BaseInfoDao {
    private static final String TAG = BaseInfoDaoStub.class.getSimpleName();

    @Override
    public BaseInfo findById(int id) {
        return new BaseInfo(id, "池袋", "山手線", "新宿・渋谷方面",
                DayType.WEEKDAY, PartType.GO_TO_WORK, new Date());
    }

    @Override
    public List<BaseInfo> findBy(PartType partType) {
        List<BaseInfo> list = new ArrayList<>();

        BaseInfo baseInfo = new BaseInfo(1, "池袋", "山手線", "新宿・渋谷方面",
                DayType.WEEKDAY, PartType.GO_TO_WORK, new Date());
        for (int i = 0; i < 10; i++) {
            list.add(baseInfo);
        }

        return  list;
    }

    @Override
    public int getLatestId() {
        return 0;
    }

    @Override
    public List<BaseInfo> findAll() {
        List<BaseInfo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(findById(i));
        }
        return list;
    }

    @Override
    public void addAll(List<BaseInfo> baseInfoList) {

    }

    @Override
    public void add(BaseInfo baseInfo) {

    }

    @Override
    public void clear() {

    }
}
