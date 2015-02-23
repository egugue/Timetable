package htoyama.timetable.presentation.listeners;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by toyamaosamuyu on 2015/02/18.
 */
public class SwipeableRecyclerViewTouchListener implements RecyclerView.OnItemTouchListener{
    private static final String TAG = SwipeableRecyclerViewTouchListener.class.getSimpleName();

    private final int mSlop;
    private final float mMaxFilingVelocity;
    private final float mMinFilingVelocity;

    private int mDownX;
    private int mDownY;
    private int mDownViewWidth;
    private int mDownPosition;
    private boolean mIsSwiping;
    private View mDownView;
    private VelocityTracker mVelocityTracker;
    private OnSwipeListener mListener;
    private List<PendingDismissData> mPendingDismissDatas = new ArrayList<>();
    private int mPendingDismissCount = 0;

    public interface OnSwipeListener {
        public boolean canSwipe(int position);
        public void onDismiss(int[] dismissPositions);
    }

    public SwipeableRecyclerViewTouchListener(RecyclerView rv, OnSwipeListener listener) {
        ViewConfiguration conf = ViewConfiguration.get(rv.getContext());
        mSlop = conf.getScaledTouchSlop();
        mMaxFilingVelocity = conf.getScaledMaximumFlingVelocity();
        mMinFilingVelocity = conf.getScaledMinimumFlingVelocity() * 16;

        mListener = listener;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        //TODO: なぜかCancelイベントが送られてくるのを調査
        //TODO:   → 巷のOSSで使っているクラスを使ってもダメだけど、このクラスを他のレイアウトファイルでやってもOK
        //TODO:   つまり多分レイアウトが原因

        switch (MotionEventCompat.getActionMasked(e)) {
            case MotionEvent.ACTION_UP: {
                touchLog("onIntercept ACTION_UP");
                upAction(rv, e);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                touchLog("onIntercept ACTION_MOVE");
                moveAction(rv, e);
                return moveAction(rv, e);
            }

            case MotionEvent.ACTION_DOWN: {
                touchLog("onIntercept ACTION_DOWN");
                downAction(rv, e);
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                touchLog("onIntercept ACTION_CANCEL");
                Log.d("onInterceptTouchEvent", " !!!!! cancel Event !!!!!!!");
                cancelAction();
                break;
            }

            default: {
                touchLog("onIntercept default "+ MotionEventCompat.getActionMasked(e));
            }
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        switch (MotionEventCompat.getActionMasked(e)) {
            case MotionEvent.ACTION_UP: {
                touchLog("onTouchEvent ACTION_UP");
                upAction(rv, e);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                touchLog("onTouchEvent ACTION_MOVE");
                moveAction(rv, e);
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                touchLog("onTouchEvent ACTION_CANCEL");
                Log.d("onTouchEvent", " !!!!! cancel Event !!!!!!!");
                cancelAction();
                break;
            }

            default: {
                touchLog("onTouchEvent default " + MotionEventCompat.getActionMasked(e));

            }
        }
    }

    private void downAction(RecyclerView rv, MotionEvent e) {
        mDownView = rv.findChildViewUnder(e.getX(), e.getY());
        if (mDownView == null) { //when list item is empty, then return null
            return;
        }

        mDownX = (int) e.getRawX();
        mDownY = (int) e.getRawY();
        mDownViewWidth = mDownView.getWidth();
        mDownPosition = rv.getChildPosition(mDownView);

        if (mListener.canSwipe(mDownPosition))  {
            mVelocityTracker = VelocityTracker.obtain();
            mVelocityTracker.addMovement(e);
        }
    }

    private boolean moveAction(RecyclerView rv, MotionEvent e) {
        if (mDownView == null || mVelocityTracker == null) {
            return false;
        }
        mVelocityTracker.addMovement(e);

        final float diffX = e.getRawX() - mDownX;
        final float diffY = e.getRawY() - mDownY;
        final float absDiffX = Math.abs(diffX);

        if (absDiffX > mSlop
                && Math.abs(diffY) < absDiffX) {
            mIsSwiping = true;
            //when swiping, disallow RecyclerView to scroll
            rv.requestDisallowInterceptTouchEvent(true);
        }

        if (mIsSwiping) {
            int width  = mDownViewWidth;
            float wariai = computeAlpha(diffX, width);
            mDownView.setAlpha(wariai);
            mDownView.setTranslationX(diffX);
            return true;
        }

        return false;
    }

    private void cancelAction() {
        if (mDownView != null && mIsSwiping) {
            repositView(mDownView);
        }

        refreshState();
    }

    private void upAction(final RecyclerView rv, MotionEvent e) {
        rv.getParent().requestDisallowInterceptTouchEvent(false);

        //mDownView is null when this is called without happening ACTION_MOVE event.
        if (mDownView == null || mVelocityTracker == null) {
            refreshState();
            return;
        }

        mVelocityTracker.addMovement(e);
        mVelocityTracker.computeCurrentVelocity(1000);

        final float velocityX = mVelocityTracker.getXVelocity();
        final float velocityY = mVelocityTracker.getYVelocity();
        final float diffX = e.getRawX() - mDownX;
        boolean isDismiss = false;
        boolean isDismissRight = false;

        if (diffX > mDownViewWidth / 2) {
            isDismiss = true;
            isDismissRight = diffX > 0;

        } else if (isDismissByVelocity(velocityX, velocityY)) {
            isDismiss = (velocityX > 0) == (diffX > 0);
            isDismissRight = velocityX > 0;
        }

        //TODO: mDownPositionが不正の場合
        if (isDismiss) {
            //because of executing refreshState(), mDownView will be null before animation ends.
            final View downView = mDownView;
            final int downPosition = mDownPosition;

            mDownView.animate()
                    .translationX(isDismissRight ? mDownViewWidth : -mDownViewWidth)
                    //.translationX(2000)
                    .alpha(0)
                    .setDuration(200) //googleは200
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            performDismiss(downView, downPosition);
                        }
                    });
        } else {
            repositView(mDownView);
        }

        refreshState();
    }

    private boolean isDismissByVelocity(float velocityX, float velocityY) {
        final float absVelocityX = Math.abs(velocityX);
        final float absVelocityY = Math.abs(velocityY);

        return mMinFilingVelocity < absVelocityX
                && absVelocityX < mMaxFilingVelocity
                && absVelocityX > absVelocityY;
    }


    private void performDismiss(final View view, final int position) {
        final int height = view.getHeight();
        final ViewGroup.LayoutParams lp = view.getLayoutParams();
        final ValueAnimator animator = ValueAnimator.ofInt(view.getHeight(), 0).setDuration(300);

        mPendingDismissCount++;
        mPendingDismissDatas.add(new PendingDismissData(view, position, height));

        animator.addListener(animationEndListener);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lp.height = (Integer) animation.getAnimatedValue();
                view.setLayoutParams(lp);
            }
        });

        animator.start();
    }

    private final AnimatorListenerAdapter animationEndListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            mPendingDismissCount--;
            if (mPendingDismissCount != 0) {
                return;
            }

            int dismissPositions[] = new int[mPendingDismissDatas.size()];
            Collections.sort(mPendingDismissDatas);
            for (int i = 0; i < mPendingDismissDatas.size(); i++) {
                dismissPositions[i] = mPendingDismissDatas.get(i).position;
                Log.d(TAG, mPendingDismissDatas.get(i).position+"");
            }

            mListener.onDismiss(dismissPositions);
            mDownPosition = RecyclerView.NO_POSITION;

            ViewGroup.LayoutParams lp;
            for (PendingDismissData data : mPendingDismissDatas) {
                //represent view for recycling view
                data.view.setAlpha(1f);
                data.view.setTranslationX(0);
                lp = data.view.getLayoutParams();
                lp.height = data.height;
                data.view.setLayoutParams(lp);
            }

            mPendingDismissDatas.clear();
        }
    };

    private void repositView(final View view) {
        view.animate()
                .translationX(0)
                .alpha(1)
                .setDuration(200)
                .setListener(null);
    }

    private void refreshState() {
        mDownX = 0;
        mDownY = 0;
        mDownView = null;
        mDownViewWidth = 0;
        mDownPosition = RecyclerView.NO_POSITION;
        mIsSwiping = false;

        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private float computeAlpha(float diffX, int width) {
        float min = Math.min(1f, 1f - 2f * Math.abs(diffX) / width);
        return Math.max(0f, min);

        /*
        float wariai = 4f * Math.abs(diffX) / width;
        Log.d("HOGE", "one = "+wariai);
        if (wariai < 1) {
            return 1;
        }

        wariai = Math.max(0f, Math.abs(wariai - 4f));
        Log.d("HOGE", "two = "+wariai);
        Log.d("--------", "-------------");

        return wariai;
        */
    }

    private void touchLog(String mes) {
        if (false) {
            Log.d("HOGE", mes);
        }
    }

    private static class PendingDismissData implements Comparable<PendingDismissData>{
        public final View view;
        public final int position;
        public final int height;

        public PendingDismissData(View view, int position, int height) {
            this.view = view;
            this.position = position;
            this.height = height;
        }

        @Override
        public int compareTo(PendingDismissData another) {
            //sort by desc
            return another.position - position;
        }
    }

}
