package htoyama.timetable.presentation.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Bus;

import htoyama.timetable.R;
import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.domain.models.Time;
import htoyama.timetable.domain.models.Timetable;
import htoyama.timetable.domain.repository.TimetableDao;
import htoyama.timetable.domain.repository.TimetableDaoStub;
import htoyama.timetable.presentation.views.DividerItemDecoration;

/**
 * Created by toyamaosamuyu on 2014/12/26.
 */
public class TimetablePagerAdapter extends PagerAdapter{
    private static final String TAG = TimetablePagerAdapter.class.getSimpleName();

    private RecyclerView mTimetableRecyclerView;
    private TimetableAdapter mTimetableAdapter;
    private OnStateChangeListener mStateChangeListener;
    private DividerItemDecoration mDividerItemDecoration;
    private BaseInfo mBaseInfo;

    public static interface OnStateChangeListener {
        public void onScrolledTimetable(RecyclerView recyclerView, int dx, int dy);
    }

    public TimetablePagerAdapter(BaseInfo baseInfo) {
        mBaseInfo = baseInfo;
    }

    @Override
    public int getCount() {
        return Time.DayType.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Time.DayType.values()[position].name;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final Context context = container.getContext();

        View view = LayoutInflater.from(context)
                .inflate(R.layout.pager_item_timetable, container, false);
        container.addView(view);

        mTimetableRecyclerView = (RecyclerView) view.findViewById(R.id.pager_item_timetable_list);

        Time.DayType dayType = Time.DayType.values()[position];
        TimetableDao timetableDao = new TimetableDaoStub();
        Timetable timetable = timetableDao.findBy(mBaseInfo.id, dayType);
        setupTimetable(context, timetable);
        Log.i("HOGE", "instaniateItem() [position: " + position + "]");

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        Log.i("HOGE", "destroyItem() [position: " + position + "]");
    }

    public void setOnStateChangeLister(OnStateChangeListener lister) {
        mStateChangeListener = lister;
    }

    private void setupTimetable(final Context context, Timetable timetable) {
        if (mDividerItemDecoration == null) {
            mDividerItemDecoration = new DividerItemDecoration(context);
        }

        mTimetableAdapter = new TimetableAdapter(context, timetable);

        mTimetableRecyclerView.setHasFixedSize(true);
        mTimetableRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mTimetableRecyclerView.setAdapter(mTimetableAdapter);
        mTimetableRecyclerView.setAnimation(null);
        mTimetableRecyclerView.addItemDecoration(mDividerItemDecoration);

        mTimetableRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mStateChangeListener != null) {
                    mStateChangeListener.onScrolledTimetable(recyclerView, dx, dy);
                }
            }
        });
    }

}
