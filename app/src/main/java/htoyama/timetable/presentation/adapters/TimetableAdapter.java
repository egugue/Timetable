package htoyama.timetable.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import htoyama.timetable.R;
import htoyama.timetable.domain.models.Time;
import htoyama.timetable.utils.TimeUtils;

/**
 * Created by toyamaosamuyu on 2014/12/26.
 */
public class TimetableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = TimetableAdapter.class.getSimpleName();
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private List<Time> mList;
    private View mHeaderView;

    public TimetableAdapter() {
        mList = new ArrayList<>();
    }

    public TimetableAdapter(List<Time> list) {
        mList = list;
    }

    public TimetableAdapter(List<Time> list, View headerView) {
        mList = list;
        mHeaderView = headerView;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_HEADER) {
            return new HeaderViewHolder(mHeaderView);
        }

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_timetable, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).bind( mList.get(position-1), position-1 );
        }
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null) {
            return mList.size();
        } else {
            return mList.size() + 1;
        }
    }

    /**
     * 引数に渡した出発時刻に一番近いアイテムのポジションを取得する。
     * @param depatureTime
     * @return
     */
    public int getClosePosition(String depatureTime) {
        final int size = getItemCount();

        for (int i = 0; i < size; i++) {
            //TODO: より良いやり方を考える (パフォーマンス的に)
            if (TimeUtils.compareToForDepatureTime(mList.get(i), depatureTime) > 0) {
                return i;
            }
        }

        return -1;
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.list_item_timetable_depature_time)
        TextView mDepartureTimeTextView;
        @InjectView(R.id.list_item_timetable_train_type)
        TextView mTrainTypeTextView;
        @InjectView(R.id.list_item_timetable_destination)
        TextView mDestinationTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void bind(Time item, int position) {
            mDepartureTimeTextView.setText(item.depatureTime);
            mTrainTypeTextView.setText(item.trainType.name+position);
            mDestinationTextView.setText(item.destination);
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

}
