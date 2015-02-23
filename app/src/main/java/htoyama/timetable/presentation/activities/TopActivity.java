package htoyama.timetable.presentation.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import htoyama.timetable.R;
import htoyama.timetable.TimetableApp;
import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.domain.models.TopItem;
import htoyama.timetable.domain.repository.loaders.TopItemLoader;
import htoyama.timetable.events.BusHolder;
import htoyama.timetable.events.ChangeTimetableDataCompleteEvent;
import htoyama.timetable.events.ClickTopItemEvent;
import htoyama.timetable.events.LoadTopItemListCompleteEvent;
import htoyama.timetable.presentation.listeners.SwipeableRecyclerViewTouchListener;
import htoyama.timetable.presentation.views.StateFrameLayout;
import htoyama.timetable.presentation.views.TimetableCardListView;


public class TopActivity extends BaseActivity {

    @InjectView(R.id.wraping_frame_layout)
    FrameLayout mWrapingFrameLayout;

    @InjectView(R.id.top_timetable_list)
    TimetableCardListView mTimetableCardListView;

    @InjectView(R.id.top_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.state_frame_layout)
    StateFrameLayout mStateFrameLayout;

    @Inject
    TopItemLoader mTopItemLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        TimetableApp.get(this).getComponent().inject(this);
        ButterKnife.inject(this);
        BusHolder.getBus().register(this);

        setupSwipeRefreshLayout();
        mTimetableCardListView.addOnItemTouchListener(
                new SwipeableRecyclerViewTouchListener(mTimetableCardListView, mOnSwipeListener)
        );
        loadListItem();

        ViewTreeObserver vto = mWrapingFrameLayout.getViewTreeObserver();
        if (vto.isAlive()) {
            vto.addOnGlobalLayoutListener(mGlobalLayoutListener);
        }
    }

    @Override
    protected void onDestroy() {
        BusHolder.getBus().unregister(this);
        super.onDestroy();
        if (mWrapingFrameLayout == null) {
            return;
        }

        ViewTreeObserver vto = mWrapingFrameLayout.getViewTreeObserver();
        if (vto.isAlive()) {
            vto.removeOnGlobalLayoutListener(mGlobalLayoutListener);
        }
    }

    @OnClick(R.id.fab_add_timetable)
    public void onAddTimetableButton() {
        startActivity(InputActivity.createIntent(this));
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

    @Subscribe
    public void onChangeTimetableDataComplete(ChangeTimetableDataCompleteEvent event) {
        loadListItem();
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
            }
        });

    }

    private void refresh() {
        loadListItem();
    }

    private void loadListItem() {
        mStateFrameLayout.showProgress();
        mTopItemLoader.loadTopItemList();
    }

    private void openTimetableActivity(BaseInfo baseInfo) {
        Intent intent = TimetableActivity.createIntent(getApplicationContext(), baseInfo);
        startActivity(intent);
    }

    private void recomputeMetrics() {
        ViewGroup.MarginLayoutParams mlp = (StateFrameLayout.MarginLayoutParams)
                mStateFrameLayout.getLayoutParams();

        mlp.topMargin = getToolbar().getHeight();
        mStateFrameLayout.setLayoutParams(mlp);
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

    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener
            = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            recomputeMetrics();
            mWrapingFrameLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    };

    private SwipeableRecyclerViewTouchListener.OnSwipeListener mOnSwipeListener
            = new SwipeableRecyclerViewTouchListener.OnSwipeListener() {
        @Override
        public boolean canSwipe(int position) {
            return position == 0 ? false : true;
        }

        @Override
        public void onDismiss(int[] dismissPositions) {
            for (int position : dismissPositions) {
                mTimetableCardListView.remove(position);
            }
        }
    };

}
