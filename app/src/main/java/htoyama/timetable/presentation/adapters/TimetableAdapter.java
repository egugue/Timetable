package htoyama.timetable.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
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


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private static final SimpleDateFormat SDF = new SimpleDateFormat("hh':'mm");

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
            mDepartureTimeTextView.setText(SDF.format(item.depatureTime));
            mDestinationTextView.setText("池袋・新宿方面");
        }
    }

}
