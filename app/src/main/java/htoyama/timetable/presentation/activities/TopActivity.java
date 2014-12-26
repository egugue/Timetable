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
        mBaseInfoAdapter = new BaseInfoAdapter(this, getListStub());

        mTimetableRecyclerView.setHasFixedSize(true);
        mTimetableRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTimetableRecyclerView.setAdapter(mBaseInfoAdapter);
        mTimetableRecyclerView.setAnimation(null);
        getToolbar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TimetableActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<Integer> getListStub() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(i);
        }
        return list;
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
}
