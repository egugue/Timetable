package htoyama.timetable.domain.models;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TopItemTest {

    private TopItem sut;

    @Before
    public void setUp() {
        BaseInfo baseInfo = new BaseInfo(1, "station", "train", "boundForName", PartType.GOING_TO_WORK, new Date());
        Timetable timetable = new Timetable();
        timetable.add(new Time(1, DayType.WEEKDAY, TrainType.LOCAL, "depatureTime", "destination"));

        sut = new TopItem(baseInfo, timetable);
    }

    @Test
    public void cloneは引数に渡されたTopItemに影響を与えない() {
        TopItem cloned = sut.clone();

        cloned.baseInfo.station = "break";
        assertThat(sut.baseInfo.station, is("station"));

        cloned.timetable.get(0).depatureTime = "break";
        assertThat(sut.timetable.get(0).depatureTime, is("depatureTime"));

        cloned.timetable.get(0).destination = "break";
        assertThat(sut.timetable.get(0).destination, is("destination"));
    }


}