package htoyama.timetable.presentation.adapters;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class BaseInfoAdapterTest {


    /*
    public static class 初期状態が空だった場合 {
        private BaseInfoAdapter sut;

        @Before
        public void setUp() {
            Context context = InstrumentationRegistry.getContext();
            sut = new BaseInfoAdapter(context);
        }

        @Test
        public void getItemCountは0を返す() {
            assertThat(sut.getItemCount(), is(0));
        }

    }

    public static class 初期状態が2つ登録されていた場合 {
        private BaseInfoAdapter sut;

        @Before
        public void setUp() {
            List<Integer> list = new ArrayList<>();
            list.add(1);
            list.add(1);

            Context context = InstrumentationRegistry.getContext();
            sut = new BaseInfoAdapter(context, list);
        }

        @Test
        public void getItemCountは2を返す() {
            assertThat(sut.getItemCount(), is(2));
        }

    }
    */

}