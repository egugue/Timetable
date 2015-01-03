package htoyama.timetable.presentation.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import htoyama.timetable.R;
import htoyama.timetable.domain.models.BaseInfo;
import htoyama.timetable.domain.models.TopItem;
import htoyama.timetable.events.BusHolder;
import htoyama.timetable.events.ClickTopItemEvent;
import htoyama.timetable.presentation.adapters.TopItemAdapter;
import htoyama.timetable.presentation.decorations.PaddingItemDecoration;
import htoyama.timetable.presentation.listeners.RecyclerItemClickListener;
import htoyama.timetable.presentation.listeners.SwipeDismissRecyclerViewTouchListener;

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
        mAdapter = new TopItemAdapter();

        setHasFixedSize(true);
        setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter(mAdapter);
        //setAnimation(null);
        addItemDecoration(
                new PaddingItemDecoration(
                        getResources().getDimensionPixelSize(R.dimen.spacing_medium))
        );

        addOnItemTouchListener(new RecyclerItemClickListener( getContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        TopItem topItem = mAdapter.getItem(position);
                        if (topItem == null) {
                            return;
                        }
                        BusHolder.getBus().post(
                                new ClickTopItemEvent(mAdapter.getItem(position))
                        );
                    }
                }
        ));

        /*
        SwipeDismissRecyclerViewTouchListener listener = new SwipeDismissRecyclerViewTouchListener(this,
                new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(int position) {
                        //for header
                        if (position == 0) return false;

                        return true;
                    }

                    @Override
                    public void onDismiss(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            mAdapter.remove(position);
                        }
                        //mAdapter.notifyDataSetChanged();
                    }
                });

        setOnTouchListener(listener);
        setOnScrollListener(listener.makeScrollListener());
                */
    }

}
