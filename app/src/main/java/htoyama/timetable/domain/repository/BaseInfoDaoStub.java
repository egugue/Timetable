package htoyama.timetable.domain.repository;

import java.util.ArrayList;
import java.util.List;

import htoyama.timetable.domain.models.BaseInfo;

/**
 * Created by toyamaosamuyu on 2014/12/27.
 */
public class BaseInfoDaoStub implements BaseInfoDao{
    private static final String TAG = BaseInfoDaoStub.class.getSimpleName();

    @Override
    public BaseInfo findById(int id) {
        return new BaseInfo(id, "池袋", "山手線", "新宿・渋谷方面");
    }

    @Override
    public List<BaseInfo> findAll() {
        List<BaseInfo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(findById(i));
        }
        return list;
    }
}
