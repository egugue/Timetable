package htoyama.timetable.presentation.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import htoyama.timetable.R;
import htoyama.timetable.domain.models.Timetable;
import htoyama.timetable.presentation.adapters.TimetablePagerAdapter;
import htoyama.timetable.presentation.adapters.TimetableAdapter;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class TimetableActivity extends BaseActivity implements MaterialTabListener {
    private TimetableAdapter mTimetableAdapter;

    /*
    @InjectView(R.id.timetable_header)
    View mHeaderView;

    @InjectView(R.id.timetable_list)
    RecyclerView mTimetableRecyclerView;
    */

    private MaterialTabHost mTabHost;
    private ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO : Toolbarに影をつける https://speakerdeck.com/cockscomb/android-5-dot-0-lollipop-toribiada-quan
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        ButterKnife.inject(this);
        setupToolbar();
        setupTab();
    }

    private void setupToolbar() {
        getToolbar().setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
    }

    private void setupTab() {
        final TimetablePagerAdapter pagerAdapter = new TimetablePagerAdapter();

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

    /*
    private void setupTab() {

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new TimetablePagerAdapter(this));
        for (int i = 0; i < mViewPager.getAdapter().getCount(); i++ ){
        }

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
    }
    */

    /*
    private void setupTimetable() {
        mTimetableAdapter = new TimetableAdapter(this, getListStub());

        //mTimetableRecyclerView.setHasFixedSize(true);
        mTimetableRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTimetableRecyclerView.setAdapter(mTimetableAdapter);
        mTimetableRecyclerView.setAnimation(null);
    }
    */

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
}
