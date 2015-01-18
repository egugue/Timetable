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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import htoyama.timetable.R;
import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.domain.models.TopItem;
import htoyama.timetable.domain.repository.loaders.TopItemLoader;
import htoyama.timetable.events.BusHolder;
import htoyama.timetable.events.ChangeTimetableDataCompleteEvent;
import htoyama.timetable.events.ClickTopItemEvent;
import htoyama.timetable.events.LoadTopItemListCompleteEvent;
import htoyama.timetable.presentation.views.StateFrameLayout;
import htoyama.timetable.presentation.views.TimetableCardListView;
import rx.Observable;
import rx.Subscription;
import rx.android.observables.AndroidObservable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;


public class TopActivity extends BaseActivity {

    private Subscription mTimelineSub = Subscriptions.empty();

    @InjectView(R.id.wraping_frame_layout)
    FrameLayout mWrapingFrameLayout;

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

        ViewTreeObserver vto = mWrapingFrameLayout.getViewTreeObserver();
        if (vto.isAlive()) {
            vto.addOnGlobalLayoutListener(mGlobalLayoutListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        BusHolder.getBus().unregister(this);
        mTimelineSub.unsubscribe();

        if (mWrapingFrameLayout != null) return;

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

        mTimelineSub =  AndroidObservable.bindActivity(this,new TopItemLoader(this).getTopItemStream(this))
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<List<TopItem>>() {
                    @Override
                    public void call(List<TopItem> topItems) {
                        if (topItems.isEmpty()) {
                            mStateFrameLayout.showError();
                            return;
                        }

                        mStateFrameLayout.showContent();
                        mTimetableCardListView.setupList(topItems);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
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

    /*
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
    */

    /*
    private void loadListItem() {
        mStateFrameLayout.showProgress();
        new TopItemLoader(this).loadTopItemList();
    }
    */

}
