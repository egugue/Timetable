package htoyama.timetable.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import htoyama.timetable.R;
import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.domain.repository.BaseInfoDao;
import htoyama.timetable.domain.repository.BaseInfoDaoStub;
import htoyama.timetable.presentation.adapters.BaseInfoAdapter;


public class TopActivity extends BaseActivity {

    private BaseInfoAdapter mBaseInfoAdapter;

    @InjectView(R.id.top_timetable_list)
    RecyclerView mTimetableRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        ButterKnife.inject(this);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        BaseInfoDao baseInfoDao = new BaseInfoDaoStub();
        mBaseInfoAdapter = new BaseInfoAdapter(this, baseInfoDao.findAll(), mOnItemClickListener);

        mTimetableRecyclerView.setHasFixedSize(true);
        mTimetableRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTimetableRecyclerView.setAdapter(mBaseInfoAdapter);
        mTimetableRecyclerView.setAnimation(null);
    }

    private void openTimetableActivity(BaseInfo baseInfo) {
        Intent intent = TimetableActivity.createIntent(getApplicationContext(), baseInfo);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private BaseInfoAdapter.OnItemClickListener mOnItemClickListener
            = new BaseInfoAdapter.OnItemClickListener() {
        @Override
        public void onCardClick(BaseInfo baseInfo) {
            openTimetableActivity(baseInfo);
        }
    };
}
