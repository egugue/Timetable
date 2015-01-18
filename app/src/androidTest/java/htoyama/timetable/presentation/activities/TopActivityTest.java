package htoyama.timetable.presentation.activities;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import htoyama.timetable.ActivityRule;
import htoyama.timetable.R;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.is;
import static android.support.test.espresso.Espresso.onView;
import static android.app.Instrumentation.ActivityMonitor;


public class TopActivityTest {

    @Rule
    public final ActivityRule<TopActivity> main = new ActivityRule<>(TopActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void タイムテーブル追加ボタンを押すとInputActivityに遷移する() throws Exception {
        ActivityMonitor monitor = main.instrumentation().addMonitor(InputActivity.class.getName(), null, true);

        onView(withId(R.id.fab_add_timetable)).perform(click());
        assertThat(monitor.getHits(), is(1));

        main.instrumentation().removeMonitor(monitor);
    }

}
