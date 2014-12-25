package htoyama.timetable.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import htoyama.timetable.R;


/**
 * Created by toyamaosamuyu on 2014/12/26.
 */
public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.ViewHolder>{
    private static final String TAG = TimetableAdapter.class.getSimpleName();
    private Context mContext;
    private List<Integer> mList;

    public TimetableAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    public TimetableAdapter(Context context, List<Integer> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater. from(parent.getContext())
               .inflate(R.layout.list_item_card_big, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Integer item = mList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addAll(List<Integer> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mThumbnailImageView;
        private TextView mDestTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mThumbnailImageView = (ImageView) itemView.findViewById(R.id.list_item_card_big_thumbnail);
            mDestTextView = (TextView) itemView.findViewById(R.id.list_item_card_big_dest_text);
        }

        public void bind(Integer item) {
           mDestTextView.setText(item.toString());
        }
    }
}
