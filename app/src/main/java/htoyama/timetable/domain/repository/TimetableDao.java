package htoyama.timetable.domain.repository;

import java.util.List;

import htoyama.timetable.domain.models.DayType;
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

    public TimetableDao setLimit(int limit);

    /**
     *
     * @param baseId
     * @return
     */
    public Timetable findBy(int baseId);

    public Timetable findBy(int baseId, String afterDepatureTIme);


    public Timetable findAll();

    /**
     * タイムテーブルのデータを入稿する
     * @param timetable
     */
    public void addAll(Timetable timetable);

    public void clear();

    /*
    /**
     * タイムテーブルの１行だけを入稿する
     * @param time
    public void add(Time time);
    */
}
