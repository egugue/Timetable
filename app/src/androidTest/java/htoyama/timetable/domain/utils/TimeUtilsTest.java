package htoyama.timetable.domain.utils;

import org.junit.Test;

import htoyama.timetable.utils.TimeUtils;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TimeUtilsTest {

    @Test
    public void convertMidnightTimeIfNeeded_0000のとき2400に変換する() {
        String time = "00:00";
        String actual = TimeUtils.convertMidnightTimeIfNeeded(time, false);

        assertThat(actual, is("24:00"));
    }

    @Test
    public void convertMidnightTimeIfNeeded_2400のとき0000に変換する() {
        String time = "24:00";
        String actual = TimeUtils.convertMidnightTimeIfNeeded(time, true);

        assertThat(actual, is("00:00"));
    }

    @Test
    public void convertMidnightTimeIfNeeded_時間が00でも24でもないときは変換しない() {
        String time = "01:00";
        String actual = TimeUtils.convertMidnightTimeIfNeeded(time, true);
        assertThat(actual, is(time));

        time = "23:00";
        actual = TimeUtils.convertMidnightTimeIfNeeded(time, false);
        assertThat(actual, is(time));
    }

}