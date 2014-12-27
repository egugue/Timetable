package htoyama.timetable.domain.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by toyamaosamuyu on 2014/12/26.
 */
public class BaseInfo implements Parcelable{

    public int id;
    public String station;
    public String train;
    public String boundForName;

    public BaseInfo(int id, String station, String train, String boundForName) {
        this.id = id;
        this.station = station;
        this.train = train;
        this.boundForName = boundForName;
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
