package htoyama.timetable.presentation.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

import htoyama.timetable.R;
import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.domain.models.DayType;
import htoyama.timetable.domain.models.Timetable;
import htoyama.timetable.domain.repository.TimetableDao;
import htoyama.timetable.domain.repository.sqlite.TimetableSqliteDao;
import htoyama.timetable.presentation.decorations.DividerItemDecoration;


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
    private String mCurrentHhMm= new SimpleDateFormat("hh:mm").format(new Date());

    public static interface OnStateChangeListener {
        public void onScrolledTimetable(RecyclerView recyclerView, int dx, int dy);
    }

    public TimetablePagerAdapter(BaseInfo baseInfo) {
        mBaseInfo = baseInfo;
    }

    @Override
    public int getCount() {
        return DayType.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int id = position + 1;
        return DayType.valueOf(id).name;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final Context context = container.getContext();

        View view = LayoutInflater.from(context)
                .inflate(R.layout.pager_item_timetable, container, false);
        container.addView(view);

        mTimetableRecyclerView = (RecyclerView) view.findViewById(R.id.pager_item_timetable_list);

        DayType dayType = DayType.values()[position];

        //TimetableDao timetableDao = new TimetableDaoStub();
        TimetableDao timetableDao = new TimetableSqliteDao(view.getContext());

        Timetable timetable = timetableDao.findBy(mBaseInfo.id);
        setupTimetable(context, timetable);

        int closePosition = mTimetableAdapter.getClosePosition(mCurrentHhMm);
        mTimetableRecyclerView.scrollToPosition(closePosition);

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

        mTimetableAdapter = new TimetableAdapter(timetable);

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
