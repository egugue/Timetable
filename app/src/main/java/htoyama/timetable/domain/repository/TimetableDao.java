package htoyama.timetable.domain.repository;

import java.util.List;

import htoyama.timetable.domain.models.Time;
import htoyama.timetable.domain.models.Timetable;

/**
 * Created by toyamaosamuyu on 2014/12/27.
 */
public interface TimetableDao {

    /**
     * idを指定して、タイムテーブルを取得する。
     * @param id
     * @return
    public Timetable findById(int id);
     */

    /**
     *
     * @param baseId
     * @param dayType
     * @return
     */
    public Timetable findBy(int baseId, Time.DayType dayType);
}
