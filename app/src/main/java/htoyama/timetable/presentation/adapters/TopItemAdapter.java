package htoyama.timetable.presentation.adapters;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import htoyama.timetable.R;
import htoyama.timetable.domain.models.DayType;
import htoyama.timetable.domain.models.PartType;
import htoyama.timetable.domain.models.TopItem;
import htoyama.timetable.presentation.views.TimetableLayout;


/**
 * Created by toyamaosamuyu on 2014/12/30.
 */
public class TopItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String TAG = TopItemAdapter.class.getSimpleName();
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private List<TopItem> mList;

    public TopItemAdapter() {
        this(new ArrayList<TopItem>());
    }

    public TopItemAdapter(List<TopItem> list) {
        mList = list;
    }

    public void addAll(List<TopItem> list) {
        mList = list;
        notifyDataSetChanged();
    }

    //FIXME : ヘッダーを追加したことによってindexの扱いが変わった
    public void add(TopItem item, int index) {
        mList.add(index, item);
        notifyItemInserted(index);
    }

    /**
     * 表示されているTopItemを取得する
     * @param index 何番目のTopItemか　ただし0はヘッダーなので入れないこと
     * @return
     */
    public TopItem getItem(int index) {
        if (index == 0 || getItemCount() < index) {
            return  null;
        }

        index--; //decrement for header
        return mList.get(index).clone();
    }

    public void remove(int index) {
        int a = index--;
        mList.remove(a);
        //notifyItemChanged(index);
        notifyItemRemoved(index);
    }

    public void removeAll() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            return HeaderViewHolder.create(parent);
        }

        return ItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_HEADER:
                ((HeaderViewHolder) holder).bind();
                break;

            default:
                position--; //minus for header
                ((ItemViewHolder) holder).bind( mList.get(position) );
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mList.size() + 1; //plus for header
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return VIEW_TYPE_HEADER;
        return VIEW_TYPE_ITEM;
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView mHeaderTextView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mHeaderTextView = (TextView) itemView.findViewById(R.id.list_item_header_text);
        }

        public static HeaderViewHolder create(ViewGroup parent) {
            View view = LayoutInflater
                    .from(parent.getContext()).inflate(R.layout.list_item_top_header, parent, false);

            return new HeaderViewHolder(view);
        }

        public void bind() {
            Date now = new Date();
            String dayTypeName = DayType.valueOf(now).name;
            String partTypeName = PartType.valueOf(now, mHeaderTextView.getContext()).name;

            mHeaderTextView.setText(
                    dayTypeName+"の"+partTypeName
            );
        }

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        //すでに画面に表示したアイテムの位置
        private static int sShowedPosition = -1;

        private CardView mCardView;
        private ImageView mThumbnailImageView;
        private TextView mStationTextView;
        private TextView mBoundForNameTextView;
        private TimetableLayout mTimetableLayout;

        public ItemViewHolder(final View itemView) {
            super(itemView);

            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mThumbnailImageView = (ImageView) itemView.findViewById(R.id.list_item_card_big_thumbnail);
            mStationTextView = (TextView) itemView.findViewById(R.id.list_item_card_station);
            mBoundForNameTextView = (TextView) itemView.findViewById(R.id.list_item_card_bound_for_name);
            mTimetableLayout = (TimetableLayout) itemView.findViewById(R.id.list_item_card_timetable_layout);
        }

        public static ItemViewHolder create(ViewGroup parent) {
            View view = LayoutInflater. from(parent.getContext())
                    .inflate(R.layout.list_item_card_big, parent, false);

            return new ItemViewHolder(view);
        }

        public void bind(final TopItem item) {
            mStationTextView.setText(item.baseInfo.station);
            mBoundForNameTextView.setText(item.baseInfo.boundForName);
            mTimetableLayout.setTimetable(item.timetable);

            Picasso.with(mThumbnailImageView.getContext())
                    .load(R.drawable.shibuya_02)
                    .into(mThumbnailImageView);

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
