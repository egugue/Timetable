package htoyama.timetable.presentation.activities;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import htoyama.timetable.ActivityRule;
import htoyama.timetable.R;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.is;
import static android.support.test.espresso.Espresso.onView;
import static android.app.Instrumentation.ActivityMonitor;


public class TopActivityTest {

    @Rule
    public final ActivityRule<TopActivity> main = new ActivityRule<>(TopActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    /**
     * Make sure that move InputActivyt if add_time_table button clicked
     */
    @Test
    public void addButtonClick() throws Exception {
        ActivityMonitor monitor = main.instrumentation()
                .addMonitor(InputActivity.class.getName(), null, true);

        onView(withId(R.id.fab_add_timetable)).perform(click());
        assertThat(monitor.getHits(), is(1));

        main.instrumentation().removeMonitor(monitor);
    }

    /**
     * No actions header item
     */
    @Test
    public void headerItem_noAction() throws Exception {
        //wait until load items completed
        onView(isRoot()).perform(waitAtLeast(1000)); //TODO: use registerIdlingResource

        ActivityMonitor monitor = main.instrumentation()
                .addMonitor(TimetableActivity.class.getName(), null, true);

        final int headerPoistion = 0;
        actionListItemAtPosition(headerPoistion, click());

        //assert that don't move TimetableActivity
        assertThat(monitor.getHits(), is(0));

        main.instrumentation().removeMonitor(monitor);
    }

    /**
     * Make sure that move {@link TimetableActivity} if list item clicked
     */
    @Test
    public void listItemClick_startTimetableActivity() throws Exception {
        //wait until load items completed
        onView(isRoot()).perform(waitAtLeast(1000)); //TODO: use registerIdlingResource

        ActivityMonitor monitor = main.instrumentation()
                .addMonitor(TimetableActivity.class.getName(), null, true);

        actionListItemAtPosition(1, click());

        assertThat(monitor.getHits(), is(1));

        main.instrumentation().removeMonitor(monitor);
    }

    /**
     * Make sure that refresh contents if swiped down refresh layout
     */
    @Test
    public void swipeToRefresh() throws Exception {
        //wait until load items completed
        onView(isRoot()).perform(waitAtLeast(1000)); //TODO: use registerIdlingResource

        onView(withId(R.id.top_swipe_refresh_layout))
                .perform(swipeDown());

        onView(withId(R.id.top_timetable_list)).check(
                matches(withEffectiveVisibility(
                        ViewMatchers.Visibility.GONE))
        );
        //assertTrue(main.get().mStateFrameLayout.isStateProgress());

        //wait until refresh completed
        onView(isRoot()).perform(waitAtLeast(1000)); //TODO: use registerIdlingResource

        onView(withId(R.id.top_timetable_list)).check(
                matches(withEffectiveVisibility(
                        ViewMatchers.Visibility.VISIBLE))
        );
        //assertTrue(main.get().mStateFrameLayout.isStateContent());
    }

    private void actionListItemAtPosition(int position, ViewAction action) {
        onView(withId(R.id.top_timetable_list))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(position, action));
    }

    public static ViewAction waitAtLeast(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return anything();
            }

            @Override
            public String getDescription() {
                return "wait for at least " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }
}
