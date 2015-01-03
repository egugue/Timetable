package htoyama.timetable.domain.models;

import java.util.ArrayList;

/**
 * Created by toyamaosamuyu on 2014/12/27.
 */
public class Timetable extends ArrayList<Time> {


    @Override
    public Timetable clone() {
        Timetable result = new Timetable();
        for (Time time : this) {
            result.add(time.clone());
        }

        return result;
    }
}
