package htoyama.timetable.presentation.activities;

import android.app.Instrumentation;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.test.ActivityInstrumentationTestCase2;

import org.hamcrest.Matchers;
import org.junit.Test;

import htoyama.timetable.R;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;
import static android.support.test.espresso.Espresso.onView;

import static android.app.Instrumentation.ActivityMonitor;

public class TopActivityTest extends ActivityInstrumentationTestCase2<TopActivity>{

    public TopActivityTest() {
        super(TopActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    @Test
    public void testタイムテーブル追加ボタンを押すとInputActivityに遷移する() throws Exception {
        ActivityMonitor monitor = getInstrumentation().addMonitor(InputActivity.class.getName(), null, true);

        onView(withId(R.id.fab_add_timetable)).perform(click());
        assertThat(monitor.getHits(), is(1));

        getInstrumentation().removeMonitor(monitor);
    }
}