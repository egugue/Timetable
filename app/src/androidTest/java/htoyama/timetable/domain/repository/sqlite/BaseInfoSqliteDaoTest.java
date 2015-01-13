package htoyama.timetable.domain.repository.sqlite;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.RenamingDelegatingContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.List;

import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.domain.models.PartType;

import static htoyama.timetable.matchers.SameBaseInfo.sameBaseInfo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class BaseInfoSqliteDaoTest {

    public static class BaseInfoが0件入っていた場合 {
        private BaseInfoSqliteDao sut;

        @Before
        public void setUp() throws Exception {
            final Context context = new RenamingDelegatingContext(
                    InstrumentationRegistry.getTargetContext(), "test_");

            sut = new BaseInfoSqliteDao(context);
        }

        @After
        public void tearDown() throws Exception {
            sut.clear();
        }

        @Test
        public void addしたら1件DBに挿入される() throws Exception {
            BaseInfo baseInfo = new BaseInfo("station", "train", "bound", PartType.LEAVING_WORK);
            BaseInfo expected = baseInfo;
            sut.add(baseInfo);

            List<BaseInfo> list = sut.findAll();
            assertThat(list.size(), is(1));

            BaseInfo addedInfo = list.get(0);
            assertThat(addedInfo, is(sameBaseInfo(expected)));
        }

        @Test
        public void findAllは空リストを返す() throws Exception {
            List<BaseInfo> result = sut.findAll();

            assertNotNull(result);
            assertThat(result.size(), is(0));
        }

        @Test
        public void getLatestIdは0を返す() {
            assertThat(sut.getLatestId(), is(0));
        }

        @Test
        public void findByIdはnullを返す() throws Exception {
            assertNull(sut.findById(100));
        }

    }

    public static class BaseInfoに2件入っていた場合 {
        private BaseInfoSqliteDao sut;
        private BaseInfo mBaseInfo1 = new BaseInfo("station1", "train1", "bound1", PartType.LEAVING_WORK);
        private BaseInfo mBaseInfo2 = new BaseInfo("station2", "train2", "bound2", PartType.GOING_TO_WORK);

        @Before
        public void setUp() throws Exception {
            final Context context = new RenamingDelegatingContext(
                    InstrumentationRegistry.getTargetContext(), "test_");

            sut = new BaseInfoSqliteDao(context);
            sut.add(mBaseInfo1);
            sut.add(mBaseInfo2);
        }

        @After
        public void tearDown() throws Exception {
            sut.clear();
        }

        @Test
        public void addしたら1件DBに挿入される() throws Exception {
            BaseInfo baseInfo = new BaseInfo("station3", "train3", "bound3", PartType.LEAVING_WORK);
            sut.add(baseInfo);

            List<BaseInfo> list = sut.findAll();
            assertThat(list.size(), is(3));
        }

        @Test
        public void findAllは大きさが2のリストを返す() throws Exception {
            List<BaseInfo> result = sut.findAll();

            assertNotNull(result);
            assertThat(result.size(), is(2));
        }

        @Test
        public void getLatestIdは2を返す() {
            assertThat(sut.getLatestId(), is(2));
        }

        @Test
        public void findByIdはDBに追加されたIDならそのIDに基づいたBaseInfoを返す() throws Exception {
            BaseInfo actual = sut.findById(1);
            assertThat(actual, is(sameBaseInfo(mBaseInfo1)));
        }

        @Test
        public void findByIdはDBに追加されていないIDならnulを返す() throws Exception {
            BaseInfo actual = sut.findById(3);
            assertNull(actual);
        }

    }

}