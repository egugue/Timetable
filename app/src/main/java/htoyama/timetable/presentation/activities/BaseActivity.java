package htoyama.timetable.presentation.activities;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import htoyama.timetable.R;

/**
 * Created by toyamaosamuyu on 2014/12/25.
 */
public class BaseActivity extends ActionBarActivity{
    private Toolbar mToolbar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setupToolbar();
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

}
