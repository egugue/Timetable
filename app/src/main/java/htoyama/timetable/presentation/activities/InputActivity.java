package htoyama.timetable.presentation.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import htoyama.timetable.R;
import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.domain.models.DayType;
import htoyama.timetable.domain.models.PartType;
import htoyama.timetable.domain.models.Time;
import htoyama.timetable.domain.models.Timetable;
import htoyama.timetable.domain.models.TrainType;
import htoyama.timetable.domain.repository.BaseInfoDao;
import htoyama.timetable.domain.repository.TimetableDao;
import htoyama.timetable.domain.repository.sqlite.BaseInfoSqliteDao;
import htoyama.timetable.domain.repository.sqlite.TimetableSqliteDao;
import htoyama.timetable.events.BusHolder;
import htoyama.timetable.tools.AssetDao;

public class InputActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        ButterKnife.inject(this);
    }

    @Override
    protected void onDestroy() {
        BusHolder.getBus().unregister(this);
        super.onDestroy();
    }

    @OnClick(R.id.add_roppongi)
    public void onClickAddRoppongi() {
        BaseInfo baseInfo = new BaseInfo("六本木", "大江戸線", "新宿・光が丘方面", DayType.WEEKDAY, PartType.LEAVING_WORK);
        String fileName = "roppongi.txt";
        registerData(baseInfo, fileName);

    }

    @OnClick(R.id.add_koma)
    public void onClickKoma() {
        BaseInfo baseInfo = new BaseInfo("駒込", "山手線", "池袋・新宿方面", DayType.WEEKDAY, PartType.GO_TO_WORK);
        String fileName = "komagome.txt";
        registerData(baseInfo, fileName);
    }

    @OnClick(R.id.all_delete_button)
    public void onClickAllDelete() {
        BaseInfoDao dao = new BaseInfoSqliteDao(this);
        dao.clear();


        TimetableDao tDao = new TimetableSqliteDao(this);
        tDao.clear();

    }

    private void registerData(BaseInfo baseInfo, String textName) {

        registerBaseInfo(baseInfo);

        BaseInfoDao dao = new BaseInfoSqliteDao(this);
        int baseId = dao.getLatestId();

        String files = new AssetDao(this).loadAssetFile(textName);
        String[] lines = separeteFile(files);
        Timetable timetable = createTimetable(baseId, lines);
        registerTimetable(timetable);

        List<BaseInfo> list = dao.findAll();
        for(BaseInfo info : list) {
            Log.d("HOGE", info.id+" "+info.station);
        }

        TimetableDao tDao = new TimetableSqliteDao(this);
        Timetable timetable1 = tDao.findAll();
        for (int i = 0; i < 3; i++) {
            Time time = timetable1.get(i);
            Log.d("HOGE", time.toString());
        }

    }

    private void registerTimetable(Timetable timetable) {
        TimetableDao dao = new TimetableSqliteDao(this);
        dao.addAll(timetable);
    }

    private void registerBaseInfo(final BaseInfo baseInfo) {
        BaseInfoDao dao = new BaseInfoSqliteDao(this);
        dao.add(baseInfo);
    }

    private Timetable createTimetable(int baseInfoId, String[] lines) {
        Timetable timetable = new Timetable();

        for (String line : lines) {
            timetable.add(createTime(baseInfoId, line));
        }

        return timetable;
    }

    private String[] separeteFile(String str) {
        final String sep = System.getProperty("line.separator");
        return str.split(sep);
    }

    private Time createTime(int baseInfoId, String line) {
        String[] values = line.split(",");

        int trainId = Integer.parseInt(values[0]);
        String depatureTime = convertDepatureTime(values[1]);
        String destination = values[2];

        TrainType trainType = TrainType.valueOf(trainId);
        return new Time(baseInfoId, trainType, depatureTime, destination);
    }

    private String convertDepatureTime(String stg) {
        return new StringBuffer(stg)
                .insert(2, ":")
                .toString();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
