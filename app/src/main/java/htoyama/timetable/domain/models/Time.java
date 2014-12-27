package htoyama.timetable.domain.models;


import java.util.Date;


/**
 * Created by toyamaosamuyu on 2014/12/26.
 */
public class Time {
    private static final String TAG = Time.class.getSimpleName();

    public int id;
    public int baseInfoId;
    public DayType dayType;
    public Date depatureTime;
    public TrainType trainType;
    public String destination;

    public Time(int id, int baseInfoId, DayType dayType,
                TrainType trainType, Date depatureTime, String destination) {
        this.id = id;
        this.baseInfoId = baseInfoId;
        this.dayType = dayType;
        this.trainType = trainType;
        this.depatureTime = depatureTime;
        this.destination = destination;
    }

    public static enum DayType {
        WEEKDAY("平日"),
        SATURDAY("土曜"),
        HOLIDAY("休日");

        public final String name;

        private DayType(String name) {
            this.name = name;
        }

    }

    public static enum TrainType {
        RAPID("快速"), //快速
        SEMI_RAPID("準急"), //準急
        EXPRESS("急行"), //急行
        LOCAL("各駅"); //各駅

        public final String name;

        private TrainType(String name) {
            this.name = name;
        }
    }

}
