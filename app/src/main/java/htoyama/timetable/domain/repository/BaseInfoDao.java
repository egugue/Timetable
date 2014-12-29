package htoyama.timetable.domain.repository;

import java.util.List;

import htoyama.timetable.domain.models.BaseInfo;

/**
 * Created by toyamaosamuyu on 2014/12/27.
 */
public interface BaseInfoDao {

    /**
     * idに紐づく基本情報を取得する
     * @param id
     * @return
     */
    public BaseInfo findById(int id);

    public List<BaseInfo> findBy(BaseInfo.Type type);

    /**
     * 登録されている基本情報をすべて取得する
     * @return
     */
    public List<BaseInfo> findAll();
}
