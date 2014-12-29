package htoyama.timetable.presentation.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import htoyama.timetable.R;
import htoyama.timetable.domain.models.Time;
import htoyama.timetable.domain.models.Timetable;

/**
 * タイムテーブルを画面に表示するためのレイアウト
 */
public class TimetableLayout extends LinearLayout {

    public TimetableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
    }

    /**
     * タイムテーブル情報を画面にセットする
     * @param timetable タイムテーブル情報。格納されているタイム文だけ表示する。
     */
    public void setTimetable(Timetable timetable) {
        View row;
        TextView depatureTimeTextView;
        TextView trainTypeTextView;
        TextView destinationTextView;
        final SimpleDateFormat sdf = new SimpleDateFormat("mm':'yy");

        for (Time time : timetable) {
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

}
