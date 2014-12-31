package htoyama.timetable.domain.repository;

import java.util.List;

import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.domain.models.PartType;

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

    public List<BaseInfo> findBy(PartType partType);

    /**
     * 最新のIDを取得する。
     * @return 最新のID。もし1つも登録されていなかったら、0を返す。
     */
    public int getLatestId();

    /**
     * 登録されている基本情報をすべて取得する
     * @return
     */
    public List<BaseInfo> findAll();

    /**
     * 基本情報のリストを登録する
     * @param baseInfoList
     */
    public void addAll(List<BaseInfo> baseInfoList);

    /**
     * 基本情報を登録する
     * @param baseInfo
     */
    public void add(BaseInfo baseInfo);

    public void clear();

}
