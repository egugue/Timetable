package htoyama.timetable.presentation.activities;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.squareup.otto.Subscribe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import htoyama.timetable.R;
import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.domain.models.TopItem;
import htoyama.timetable.domain.repository.BaseInfoDao;
import htoyama.timetable.domain.repository.TimetableParser;
import htoyama.timetable.domain.repository.TopItemLoader;
import htoyama.timetable.domain.repository.sqlite.BaseInfoSqliteDao;
import htoyama.timetable.events.BusHolder;
import htoyama.timetable.events.ClickTopItemEvent;
import htoyama.timetable.events.LoadTopItemListCompleteEvent;
import htoyama.timetable.presentation.views.StateFrameLayout;
import htoyama.timetable.presentation.views.TimetableCardListView;


public class TopActivity extends BaseActivity {

    @InjectView(R.id.top_timetable_list)
    TimetableCardListView mTimetableCardListView;

    @InjectView(R.id.top_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.state_frame_layout)
    StateFrameLayout mStateFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        ButterKnife.inject(this);
        BusHolder.getBus().register(this);

        setupSwipeRefreshLayout();
        loadListItem();

        getToolbar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InputActivity.class);
                startActivity(intent);
            }
        });

        /*
        BaseInfoDao dao = new BaseInfoSqliteDao(this);
        Log.d("HOGE", dao.getLatestId()+"");
        */

    }

    @Override
    protected void onDestroy() {
        BusHolder.getBus().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onListItemClick(ClickTopItemEvent event) {
        openTimetableActivity(event.getTopItem().baseInfo);
    }

    @Subscribe
    public void onLoadTopItemListComplete(LoadTopItemListCompleteEvent event) {
        List<TopItem> list = event.getTopItemList();

        if (list.isEmpty()) {
            mStateFrameLayout.showError();
            return;
        }

        mStateFrameLayout.showContent();
        mTimetableCardListView.setupList(list);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void setupSwipeRefreshLayout() {
        final Resources res = getResources();
        mSwipeRefreshLayout.setColorSchemeColors(
                res.getColor(android.R.color.holo_blue_bright),
                res.getColor(android.R.color.holo_green_light),
                res.getColor(android.R.color.holo_orange_light),
                res.getColor(android.R.color.holo_red_light));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                /*
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 5000);
                */
            }
        });

    }

    private void refresh() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.card_slide_out);
        animator.setTarget(mTimetableCardListView);
        animator.start();
        loadListItem();
    }

    private void loadListItem() {
        mStateFrameLayout.showProgress();
        new TopItemLoader(this).loadTopItemList();
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

    private String loadFile() {
        String fileName = "roppongi.txt";

        StringBuilder file = new StringBuilder();

        final AssetManager assetManager = getAssets();
        try {
            InputStream is = assetManager.open(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            String sep = System.getProperty("line.separator");
            while ((line = br.readLine()) != null) {
                file.append(line).append(sep);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.toString();
    }

    private String separete(String str) {
        final String sep = System.getProperty("line.separator");
        String[] lines = str.split(sep);
        for (String line : lines) {
            Log.d("HOGE", line);
        }

        return null;
    }

}
