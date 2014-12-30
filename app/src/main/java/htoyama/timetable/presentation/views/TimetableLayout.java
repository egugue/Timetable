package htoyama.timetable.presentation.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import htoyama.timetable.R;
import htoyama.timetable.domain.models.Time;
import htoyama.timetable.domain.models.Timetable;

/**
 * タイムテーブルを画面に表示するためのレイアウト
 */
public class TimetableLayout extends LinearLayout {

    private int mMaxItems;

    public TimetableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);

        final int defaultMaxItems = getResources().getInteger(R.integer.timetable_layout_default_max_items);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimetableLayout);
        mMaxItems = typedArray.getInt(R.styleable.TimetableLayout_maxItems, defaultMaxItems);
    }

    /**
     * タイムテーブル情報を画面にセットする
     * @param timetable タイムテーブル情報。格納されているタイム文だけ表示する。
     */
    public void setTimetable(Timetable timetable) {
        removeAllViews();

        View row;
        TextView depatureTimeTextView;
        TextView trainTypeTextView;
        TextView destinationTextView;
        final SimpleDateFormat sdf = new SimpleDateFormat("hh':'mm");

        for (Time time : timetable) {

            if (isOverMaxItems()) break;

            row = LayoutInflater.from(getContext()).inflate(R.layout.list_item_timetable_small, null);

            depatureTimeTextView = (TextView) row.findViewById(R.id.list_item_timetable_depature_time);
            trainTypeTextView = (TextView) row.findViewById(R.id.list_item_timetable_train_type);
            destinationTextView = (TextView) row.findViewById(R.id.list_item_timetable_destination);

            depatureTimeTextView.setText( sdf.format(time.depatureTime) );
            trainTypeTextView.setText(time.trainType.name);
            destinationTextView.setText(time.destination);

            addView(row);
        }

    }

    /**
     * 表示する最大アイテム数を設定する。
     * @param maxItems 表示したい最大アイテム数
     */
    public void setMaxItems(int maxItems) {
        mMaxItems = maxItems;
    }

    /**
     * 現在設定されている、表示する最大アイテム数を取得する
     * @return 現在設定されている、表示する最大アイテム数
     */
    public int getMaxItems() {
        return mMaxItems;
    }

    private boolean isOverMaxItems() {
        return getChildCount() >= mMaxItems;
    }

}
