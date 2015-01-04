package htoyama.timetable.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

/**
 * Created by toyamaosamuyu on 2014/12/26.
 */
public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.ViewHolder>{
    private static final String TAG = TimetableAdapter.class.getSimpleName();

    private List<Time> mList;

    public TimetableAdapter() {
        mList = new ArrayList<>();
    }

    public TimetableAdapter(List<Time> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_timetable, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind( mList.get(position) );
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 引数に渡した出発時刻に一番近いアイテムのポジションを取得する。
     * @param depatureTime
     * @return
     */
    public int getClosePosition(String depatureTime) {
        final int size = getItemCount();
        Time time;
        for (int i = 0; i < size; i++) {
            time = mList.get(i);
            if (time.depatureTime.compareTo(depatureTime) > 0) {
                return i;
            }
        }

        return -1;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.list_item_timetable_depature_time)
        TextView mDepartureTimeTextView;
        @InjectView(R.id.list_item_timetable_train_type)
        TextView mTrainTypeTextView;
        @InjectView(R.id.list_item_timetable_destination)
        TextView mDestinationTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void bind(Time item) {
            mDepartureTimeTextView.setText(item.depatureTime);
            mTrainTypeTextView.setText(item.trainType.name);
            mDestinationTextView.setText(item.destination);
        }
    }

}
