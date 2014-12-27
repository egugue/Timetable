package htoyama.timetable.presentation.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import htoyama.timetable.R;

/**
 * Created by toyamaosamuyu on 2014/12/27.
 */
public class StateFrameLayout extends FrameLayout{
    private static final int DEFAULT_PROGRESS_LAYOUT = R.layout.view_state_frame_layout_progress;
    private static final int DEFAULT_ERROR_LAYOUT = R.layout.view_state_frame_layout_error;

    private AttributeSet mAttrs;
    private State mState = State.CONTENT;
    private View mContentView;
    private View mProgressView;
    private View mErrorView;

    public static enum State {
        CONTENT, PROGRESS, ERROR
    }

    public StateFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAttrs = attrs;
    }

    @Override
    public void onFinishInflate() {
        setupView();
    }

    public State getState() {
        return mState;
    }

    public boolean isStateContent() {
        return (mState == State.CONTENT);
    }

    public boolean isStateProgress() {
        return (mState == State.PROGRESS);
    }

    public boolean isStateError() {
        return (mState == State.ERROR);
    }

    public void showContent() {
        mContentView.setVisibility(View.VISIBLE);
        mProgressView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);

        mState = State.CONTENT;
    }

    public void showProgress() {
        mContentView.setVisibility(View.GONE);
        mProgressView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);

        mState = State.PROGRESS;
    }

    public void showError() {
        mContentView.setVisibility(View.GONE);
        mProgressView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);

        mState = State.ERROR;
    }

    private void setupView() {
        //プログレスとエラーのレイアウトIDを取得
        final TypedArray typedArray = getContext()
                .obtainStyledAttributes(mAttrs, R.styleable.StateFrameLayout);
        final int progressLayoutId = typedArray.getResourceId(
                R.styleable.StateFrameLayout_progressView, DEFAULT_PROGRESS_LAYOUT);
        final int errorLayoutId = typedArray.getResourceId(
                R.styleable.StateFrameLayout_errorView, DEFAULT_ERROR_LAYOUT);

        typedArray.recycle();

        List<View> children = getAllChildren();
        removeAllViews();

        mContentView = inflateContentView(children);
        mProgressView = View.inflate(getContext(), progressLayoutId, null);
        mErrorView = View.inflate(getContext(), errorLayoutId, null);

        mContentView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
        mProgressView.setVisibility(View.GONE);

        addView(mContentView);
        addView(mProgressView);
        addView(mErrorView);
    }

    private List<View> getAllChildren() {
        List<View> children = new ArrayList<>();

        final int size = getChildCount();
        for (int i = 0; i < size; i++) {
            children.add(getChildAt(i));
        }

        return children;
    }

    private View inflateContentView(List<View> children) {
        FrameLayout frameLayout = new FrameLayout(getContext());
        for (View child : children) {
            frameLayout.addView(child);
        }
        return frameLayout;
    }

}
