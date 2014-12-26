package htoyama.timetable.domain.models;

/**
 * Created by toyamaosamuyu on 2014/12/26.
 */
public class BaseInfo {

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

}
