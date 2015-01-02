package htoyama.timetable.domain.models;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class PartTypeTest {

    @Test
    public void 退勤時間のしきい値より出勤時間のしきい値のほうが早い場合() {
        int goingWorkTime = 3;
        int leavingWorkTime = 15;
        int hourOfDay;
        PartType actual;

        hourOfDay = 3;
        actual = PartType.valueOf(hourOfDay, goingWorkTime, leavingWorkTime);
        assertThat(actual, is(PartType.GOING_TO_WORK));

        hourOfDay = 15;
        actual = PartType.valueOf(hourOfDay, goingWorkTime, leavingWorkTime);
        assertThat(actual, is(PartType.LEAVING_WORK));
    }

    @Test
    public void 退勤時間のしきい値のほうが出勤時間のしきい値よりもが早い場合() {
        int goingWorkTime = 15;
        int leavingWorkTime = 3;
        int hourOfDay;
        PartType actual;

        hourOfDay = 3;
        actual = PartType.valueOf(hourOfDay, goingWorkTime, leavingWorkTime);
        assertThat(actual, is(PartType.LEAVING_WORK));

        hourOfDay = 15;
        actual = PartType.valueOf(hourOfDay, goingWorkTime, leavingWorkTime);
        assertThat(actual, is(PartType.GOING_TO_WORK));
    }

}