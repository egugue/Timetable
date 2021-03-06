package htoyama.timetable.domain.models;

/**
 * Created by toyamaosamuyu on 2014/12/31.
 */
public enum TrainType {
    LOCAL(0,"普通"),
    EXPRESS(1, "急行"),
    RAPID(2, "快速"),
    SEMI_RAPID(3, "準急");

    public final int id;
    public final String name;

    private TrainType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TrainType valueOf(int id) {
        for (TrainType trainType : values()) {
            if (id == trainType.id) {
                return trainType;
            }
        }
        throw new IllegalArgumentException("no such enum object for the id: " + id);
    }
}
