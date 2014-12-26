package htoyama.timetable.presentation.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import htoyama.timetable.R;
import htoyama.timetable.domain.models.Timetable;
import htoyama.timetable.presentation.adapters.TimetablePagerAdapter;
import htoyama.timetable.presentation.adapters.TimetableAdapter;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class TimetableActivity extends BaseActivity
        implements MaterialTabListener, TimetablePagerAdapter.OnStateChangeListener {

    private MaterialTabHost mTabHost;
    private ViewPager mViewPager;
    private boolean isHideBar = false;

    @InjectView(R.id.timetable_wraping_layout)
    FrameLayout mWrapingFrameLayout;

    @InjectView(R.id.timetable_header)
    View mHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO : Toolbarに影をつける https://speakerdeck.com/cockscomb/android-5-dot-0-lollipop-toribiada-quan
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        ButterKnife.inject(this);

        ViewTreeObserver vto = mWrapingFrameLayout.getViewTreeObserver();
        if (vto.isAlive()) {
            vto.addOnGlobalLayoutListener(mGlobalLayoutListener);
        }

        setupToolbar();
        setupTab();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWrapingFrameLayout == null) {
            return;
        }

        ViewTreeObserver vto = mWrapingFrameLayout.getViewTreeObserver();
        if (vto.isAlive()) {
            vto.removeOnGlobalLayoutListener(mGlobalLayoutListener);
        }
    }


    @Override
    public void onScrolledTimetable(RecyclerView recyclerView, int dx, int dy) {
        if (dy < 0) {
            return;
        }

        if (!isHideBar) {
            isHideBar = true;
            int height = getToolbar().getHeight();
            mHeaderView.setTranslationY(-height);

            ViewPager.MarginLayoutParams mlp = (ViewPager.MarginLayoutParams)
                    mViewPager.getLayoutParams();
            mlp.topMargin -= height;
            mViewPager.setLayoutParams(mlp);
        }
    }

    private void recomputeMetrics() {
        ViewPager.MarginLayoutParams mlp = (ViewPager.MarginLayoutParams)
                mViewPager.getLayoutParams();

        mlp.topMargin = mHeaderView.getHeight();
        mViewPager.setLayoutParams(mlp);
    }

    private void setupToolbar() {
        getToolbar().setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
    }

    private void setupTab() {

        final TimetablePagerAdapter pagerAdapter = new TimetablePagerAdapter();
        pagerAdapter.setOnStateChangeLister(this);

        mTabHost = (MaterialTabHost) this.findViewById(R.id.tabHost);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                mTabHost.setSelectedNavigationItem(position);
            }
        });

        final int size = pagerAdapter.getCount();
        for (int i = 0; i < size; i++ ){
            mTabHost.addTab(
                    mTabHost.newTab()
                            .setText( pagerAdapter.getPageTitle(i) )
                            .setTabListener(this)
            );
        }

    }

    private List<Timetable> getListStub() {
        List<Timetable> timetableList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Timetable item = new Timetable(i, 1, null, null, null, null);
            timetableList.add(item);
        }
        return timetableList;
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        mViewPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timetable, menu);
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

}
