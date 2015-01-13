package htoyama.timetable.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.utils.StringUtils;

/**
 * Created by toyamaosamuyu on 2015/01/13.
 */
public class SameBaseInfo {

    public static BaseInfoMatcher sameBaseInfo(final BaseInfo baseInfo) {
        return new BaseInfoMatcher(baseInfo);
    }

    private static class BaseInfoMatcher extends BaseMatcher<BaseInfo> {
        private final BaseInfo expected;
        private BaseInfo actual;

        public BaseInfoMatcher(BaseInfo baseInfo) {
            expected = baseInfo;
        }

        @Override
        public void describeTo(Description description) {
            if (actual == null) {
                description.appendValue(expected);
            }

            description.appendText(expected.toString())
                    .appendText(", " + StringUtils.getNewLineChar() + "but ")
                    .appendText(actual.toString());
        }

        @Override
        public boolean matches(Object o) {
            if (!(o instanceof BaseInfo)) {
                return false;
            }

            actual = (BaseInfo) o;

            if (!actual.station.equals(expected.station)) {
                return false;
            }
            if (!actual.train.equals(expected.train)) {
                return false;
            }
            if (!actual.boundForName.equals(expected.boundForName)) {
                return false;
            }
            /*
            if (!actual.modified.equals(expected.modified)) {
                return false;
            }
            */
            if (!actual.partType.equals(expected.partType)) {
                return false;
            }

            return true;
        }
    }
}
