package htoyama.timetable.presentation.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import htoyama.timetable.R;
import htoyama.timetable.domain.models.Timetable;

/**
 * Created by toyamaosamuyu on 2014/12/26.
 */
public class TimetablePagerAdapter extends PagerAdapter{
    private static final String TAG = TimetablePagerAdapter.class.getSimpleName();

    private RecyclerView mTimetableRecyclerView;
    private TimetableAdapter mTimetableAdapter;

    @Override
    public int getCount() {
        return Timetable.DayType.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Timetable.DayType.values()[position].name;
    }

    /**
     * Instantiate the {@link View} which should be displayed at {@code position}. Here we
     * inflate a layout from the apps resources and then change the text view to signify the position.
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final Context context = container.getContext();

        View view = LayoutInflater.from(context)
                .inflate(R.layout.pager_item_timetable, container, false);

        container.addView(view);

        mTimetableRecyclerView = (RecyclerView) view.findViewById(R.id.pager_item_timetable_list);
        setupTimetable(context);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        Log.i("HOGE", "destroyItem() [position: " + position + "]");
    }

    private void setupTimetable(final Context context) {
        mTimetableAdapter = new TimetableAdapter(context, getListStub());

        mTimetableRecyclerView.setHasFixedSize(true);
        mTimetableRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mTimetableRecyclerView.setAdapter(mTimetableAdapter);
        mTimetableRecyclerView.setAnimation(null);
    }

    private List<Timetable> getListStub() {
        List<Timetable> timetableList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Timetable item = new Timetable(i, 1, null, null, null, null);
            timetableList.add(item);
        }
        return timetableList;
    }

}
