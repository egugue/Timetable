package htoyama.timetable.domain.models;


import android.database.Cursor;

import java.util.Date;


/**
 * Created by toyamaosamuyu on 2014/12/26.
 */
public class Time {
    public int baseInfoId = -1;
    public TrainType trainType;
    public String depatureTime;
    public String destination;

    public Time(int baseInfoId, TrainType trainType, String depatureTime, String destination) {
        this.baseInfoId = baseInfoId;
        this.trainType = trainType;
        this.depatureTime = depatureTime;
        this.destination = destination;
    }


    @Override
    public String toString() {
        return "Time["
                + "baseInfoId = " + baseInfoId
                + ",  trainType = " + trainType.toString()
                + ",  depatureTime = " + depatureTime
                + ",  destination = " + destination
                + "]";
    }

    public static Time createWith(Cursor cursor) {
        int baseInfoId = cursor.getInt(0);
        int trainTypeId = cursor.getInt(1);
        String depatureTime = cursor.getString(2);
        String destination = cursor.getString(3);

        return new Time(baseInfoId, TrainType.valueOf(trainTypeId),
                depatureTime, destination);
    }

}
