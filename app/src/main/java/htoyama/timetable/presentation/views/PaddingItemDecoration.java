package htoyama.timetable.presentation.views;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * {@link RecyclerView}で表示された各アイテム間にPaddingをもたせるデコレーション
 */
public class PaddingItemDecoration extends RecyclerView.ItemDecoration {
    private final int mPaddingTopPixel;

    /**
     * コンストラクタ
     * @param paddingTopPixel もたせたいPaddingのピクセル値
     */
    public PaddingItemDecoration(int paddingTopPixel) {
        mPaddingTopPixel = paddingTopPixel;
    }

    /**
     * コンストラクタ
     * @param context
     * @param paddingTopDp もたせたいPaddingのDP値
     */
    public PaddingItemDecoration(final Context context, int paddingTopDp) {
        float density = context.getResources().getDisplayMetrics().density;

        //ピクセルに変換する
        mPaddingTopPixel = (int) (paddingTopDp * density);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildPosition(view) == 0) {
            //最初のアイテムだったら、paddingはつけない
            return;
        }

        outRect.top = mPaddingTopPixel;
    }

}
