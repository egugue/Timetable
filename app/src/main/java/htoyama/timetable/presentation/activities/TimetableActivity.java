package htoyama.timetable.presentation.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import htoyama.timetable.R;
import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.domain.models.Time;
import htoyama.timetable.presentation.adapters.TimetablePagerAdapter;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class TimetableActivity extends BaseActivity
        implements MaterialTabListener, TimetablePagerAdapter.OnStateChangeListener {

    private static final String EXSTRA_KEY_BASE_INFO = "BASE_INFO";

    private MaterialTabHost mTabHost;
    private ViewPager mViewPager;
    private boolean isHideBar = false;

    @InjectView(R.id.timetable_wraping_layout)
    FrameLayout mWrapingFrameLayout;

    @InjectView(R.id.timetable_header)
    View mHeaderView;

    public static Intent createIntent(Context context, BaseInfo baseInfo) {
        Intent intent = new Intent(context, TimetableActivity.class);
        intent.putExtra(EXSTRA_KEY_BASE_INFO, baseInfo);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        final BaseInfo baseInfo = getBaseInfoFromExtras();
        final TimetablePagerAdapter pagerAdapter = new TimetablePagerAdapter(baseInfo);
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

    private BaseInfo getBaseInfoFromExtras() {
        Bundle extaras = getIntent().getExtras();
        if (extaras == null) {
            return null;
        }
        return (BaseInfo) extaras.get(EXSTRA_KEY_BASE_INFO);
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
