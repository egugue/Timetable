package htoyama.timetable.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import htoyama.timetable.R;
import htoyama.timetable.domain.models.BaseInfo;


/**
 * Created by toyamaosamuyu on 2014/12/26.
 */
public class BaseInfoAdapter extends RecyclerView.Adapter<BaseInfoAdapter.ViewHolder>{
    private static final String TAG = BaseInfoAdapter.class.getSimpleName();
    private Context mContext;
    private List<BaseInfo> mList;
    private OnItemClickListener mItemClickListener;

    public static interface OnItemClickListener {
        public void onCardClick(BaseInfo baseInfo);
    }

    public BaseInfoAdapter(Context context, OnItemClickListener listener) {
        this(context, new ArrayList<BaseInfo>(), listener);
    }

    public BaseInfoAdapter(Context context, List<BaseInfo> list, OnItemClickListener listener) {
        mContext = context;
        mList = list;
        mItemClickListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater. from(parent.getContext())
               .inflate(R.layout.list_item_card_big, parent, false);

        return new ViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BaseInfo item = mList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addAll(List<BaseInfo> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private OnItemClickListener mListener;
        private CardView mCardView;
        private ImageView mThumbnailImageView;
        private TextView mDestTextView;

        public ViewHolder(final View itemView, OnItemClickListener listener) {
            super(itemView);
            mListener = listener;
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mThumbnailImageView = (ImageView) itemView.findViewById(R.id.list_item_card_big_thumbnail);
            mDestTextView = (TextView) itemView.findViewById(R.id.list_item_card_big_dest_text);
        }

        public void bind(final BaseInfo item) {
            mDestTextView.setText(item.id+"");

            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onCardClick(item);
                }
            });

        }
    }
}
