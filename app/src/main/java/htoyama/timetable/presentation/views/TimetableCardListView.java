package htoyama.timetable.presentation.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import java.util.List;

import htoyama.timetable.R;
import htoyama.timetable.domain.models.TopItem;
import htoyama.timetable.presentation.adapters.TopItemAdapter;

/**
 * Created by toyamaosamuyu on 2014/12/30.
 */
public class TimetableCardListView extends RecyclerView{
    private static final String TAG = TimetableCardListView.class.getSimpleName();
    private TopItemAdapter mAdapter;

    public TimetableCardListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public void setupList(List<TopItem> list) {
        if (mAdapter.getItemCount() != 0) {
            mAdapter.removeAll();
        }
        mAdapter.addAll(list);
    }

    private void initialize() {
        mAdapter = new TopItemAdapter(getContext());

        setHasFixedSize(true);
        setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter(mAdapter);
        setAnimation(null);
        addItemDecoration(
                new PaddingItemDecoration(
                        getResources().getDimensionPixelSize(R.dimen.spacing_medium))
        );
    }

}
