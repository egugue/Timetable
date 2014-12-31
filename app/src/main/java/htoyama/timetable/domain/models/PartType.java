package htoyama.timetable.domain.models;

/**
 * 各タイムテーブルが、
 * 通勤時に見たいのか、退勤時に見たいものなのか
 * を決定する
 */
public enum PartType {
    GO_TO_WORK(1, "出勤時"),
    LEAVING_WORK(2, "退勤時"),
    NONE(3, "設定しない");

    public final int id;
    public final String name;

    private PartType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static PartType valueOf(int id) {
        for (PartType partType : values()) {
            if (partType.id == id) {
                return partType;
            }
        }
        return null;
    }

}
