package htoyama.timetable.domain.models;


import android.database.Cursor;


/**
 * Created by toyamaosamuyu on 2014/12/26.
 */
public class Time {
    public int baseInfoId = -1;
    public DayType dayType;
    public TrainType trainType;
    public String depatureTime;
    public String destination;

    public Time(int baseInfoId, DayType dayType, TrainType trainType, String depatureTime, String destination) {
        this.baseInfoId = baseInfoId;
        this.dayType = dayType;
        this.trainType = trainType;
        this.depatureTime = depatureTime;
        this.destination = destination;
    }


    @Override
    public String toString() {
        String dayStr = "null";
        if (dayType != null) dayStr = dayType.toString();

        return "Time["
                + "baseInfoId = " + baseInfoId
                + ",  dayType = " + dayStr
                + ",  trainType = " + trainType.toString()
                + ",  depatureTime = " + depatureTime
                + ",  destination = " + destination
                + "]";
    }

    public static Time createWith(Cursor cursor) {
        int baseInfoId = cursor.getInt(0);
        int dayTypeId = cursor.getInt(1);
        int trainTypeId = cursor.getInt(2);
        String depatureTime = cursor.getString(3);
        String destination = cursor.getString(4);

        return new Time(baseInfoId, DayType.valueOf(dayTypeId),
                TrainType.valueOf(trainTypeId), depatureTime, destination);
    }

    @Override
    public Time clone() {
        return new Time(baseInfoId, dayType, trainType, depatureTime, destination);

    }

}
