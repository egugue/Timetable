package htoyama.timetable.presentation.adapters;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import htoyama.timetable.R;
import htoyama.timetable.domain.models.TopItem;
import htoyama.timetable.events.BusHolder;
import htoyama.timetable.events.ClickTopItemEvent;
import htoyama.timetable.presentation.views.TimetableLayout;


/**
 * Created by toyamaosamuyu on 2014/12/30.
 */
public class TopItemAdapter extends RecyclerView.Adapter<TopItemAdapter.ViewHolder>{

    private static final String TAG = BaseInfoAdapter.class.getSimpleName();

    private Context mContext;
    private List<TopItem> mList;

    public TopItemAdapter(Context context) {
        this(context, new ArrayList<TopItem>());
    }

    public TopItemAdapter(Context context, List<TopItem> list) {
        mContext = context;
        mList = list;
    }

    public void addAll(List<TopItem> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void add(TopItem item, int index) {
        mList.add(index, item);
        notifyItemInserted(index);
    }

    public void remove(int index) {
        mList.remove(index);
        notifyItemRemoved(index);
    }

    public void removeAll() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater. from(parent.getContext())
                .inflate(R.layout.list_item_card_big, parent, false);

        return new ViewHolder(view, view.getContext());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TopItem item = mList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //すでに画面に表示したアイテムの位置
        private static int sShowedPosition = -1;

        private CardView mCardView;
        private ImageView mThumbnailImageView;
        private TextView mStationTextView;
        private TextView mBoundForNameTextView;
        private TimetableLayout mTimetableLayout;
        private Context mContext;

        public ViewHolder(final View itemView, Context context) {
            super(itemView);
            mContext = context;

            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mThumbnailImageView = (ImageView) itemView.findViewById(R.id.list_item_card_big_thumbnail);
            mStationTextView = (TextView) itemView.findViewById(R.id.list_item_card_station);
            mBoundForNameTextView = (TextView) itemView.findViewById(R.id.list_item_card_bound_for_name);
            mTimetableLayout = (TimetableLayout) itemView.findViewById(R.id.list_item_card_timetable_layout);
        }

        public void bind(final TopItem item) {

            mStationTextView.setText(item.baseInfo.station);
            mBoundForNameTextView.setText(item.baseInfo.boundForName);
            mTimetableLayout.setTimetable(item.timetable);

            Picasso.with(mContext)
                    .load(R.drawable.shibuya_02)
                    .into(mThumbnailImageView);

            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BusHolder.getBus().post(new ClickTopItemEvent(item));
                }
            });

            //まだ見たことがないアイテムなら
            if (sShowedPosition < getPosition() ) {
                animateView();
            }
        }

        private void animateView() {
            Animator animator = AnimatorInflater.loadAnimator(mCardView.getContext(), R.animator.card_slide_in);
            animator.setTarget(mCardView);
            animator.start();
            sShowedPosition = getPosition();
        }

    }
}
