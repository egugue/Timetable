package htoyama.timetable.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by toyamaosamuyu on 2015/01/02.
 */
public class SharedPreferencesHelper {
    private static final String TAG = SharedPreferencesHelper.class.getSimpleName();
    private static final String PREFERENCES_NAME = "timetable_preferences";
    private SharedPreferences mSharedPreferences;

    public static enum Key {
        LEAVEING_WORK_TIME_THRESHOLD("leaving_work_time_threshold"),
        GOING_TO_WORK_TIME_THRESHOLD("going_to_work_time_threshold");

        private String name;
        private Key(String name) {
            this.name = name;
        }
    }

    public SharedPreferencesHelper(final Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public int getInt(Key key, int defalut) {
        return mSharedPreferences.getInt(key.name, defalut);
    }

    public float getFloat(Key key, float defalut) {
        return mSharedPreferences.getFloat(key.name, defalut);
    }

    public boolean getBoolean(Key key, boolean defalut) {
        return mSharedPreferences.getBoolean(key.name, defalut);
    }

    public long getLong(Key key, long defalut) {
        return mSharedPreferences.getLong(key.name, defalut);
    }

    public String getString(Key key, String defalut) {
        return mSharedPreferences.getString(key.name, defalut);
    }

    public Set<String> getStringSet(Key key, Set<String> defalut) {
        return mSharedPreferences.getStringSet(key.name, defalut);
    }

    public SharedPreferencesHelper putInt(Key key, int value) {
        mSharedPreferences.edit().putInt(key.name, value);
        return this;
    }

    public SharedPreferencesHelper putBoolean(Key key, boolean value) {
        mSharedPreferences.edit().putBoolean(key.name, value);
        return this;
    }

    public SharedPreferencesHelper putFloat(Key key, float value) {
        mSharedPreferences.edit().putFloat(key.name, value);
        return this;
    }

    public SharedPreferencesHelper putLong(Key key, long value) {
        mSharedPreferences.edit().putLong(key.name, value);
        return this;
    }

    public SharedPreferencesHelper putString(Key key, String value) {
        mSharedPreferences.edit().putString(key.name, value);
        return this;
    }

    public SharedPreferencesHelper putStringSet(Key key, Set<String> value) {
        mSharedPreferences.edit().putStringSet(key.name, value);
        return this;
    }

    public boolean commit() {
        return mSharedPreferences.edit().commit();
    }

}
