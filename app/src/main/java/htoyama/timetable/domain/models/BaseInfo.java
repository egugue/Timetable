package htoyama.timetable.domain.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by toyamaosamuyu on 2014/12/26.
 */
public class BaseInfo implements Parcelable{
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd kk-mm-ss");

    public int id = -1;
    public String station;
    public String train;
    public String boundForName;
    public DayType dayType;
    public PartType partType;
    public Date modified;

    public BaseInfo(String station, String train, String boundForName,
                    DayType dayType, PartType partType) {

        this.station = station;
        this.train = train;
        this.boundForName = boundForName;
        this.dayType = dayType;
        this.partType = partType;
    }

    public BaseInfo(int id, String station, String train, String boundForName,
                    DayType dayType, PartType partType, Date modified) {

        this.id = id;
        this.station = station;
        this.train = train;
        this.boundForName = boundForName;
        this.partType = partType;
        this.dayType = dayType;
        this.modified = modified;
    }

    public static BaseInfo createWith(Cursor cursor) {
        int dayTypeId = cursor.getInt(4);
        int partTypeId = cursor.getInt(5);

        Date date = null;
        try {
            date = SDF.parse(cursor.getString(6));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new BaseInfo(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            DayType.valueOf(dayTypeId),
            PartType.valueOf(partTypeId),
            date
        );
    }

    @Override
    public String toString() {
        String partStr = "null";
        if (partType != null)  partStr = partType.toString();

        String dayStr = "null";
        if (dayType != null) dayStr = dayType.toString();

        return "BaseInfo[ "
                + "id= " + id
                + ",  station= " + station
                + ",  train = " + train
                + ",  boundForName = " + boundForName
                + ",  partType = " + partStr
                + ",  dayType = " + dayStr
                + ",  modified = " +  modified
                + " ]";
    }

    private BaseInfo(Parcel source) {
        id = source.readInt();
        station = source.readString();
        train = source.readString();
        boundForName = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(station);
        dest.writeString(train);
        dest.writeString(boundForName);
    }

    public static final Creator<BaseInfo> CREATOR
            = new Creator<BaseInfo>() {
        @Override
        public BaseInfo createFromParcel(Parcel source) {
            return new BaseInfo(source);
        }

        @Override
        public BaseInfo[] newArray(int size) {
            return new BaseInfo[0];
        }
    };

}
