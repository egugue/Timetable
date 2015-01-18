package htoyama.timetable;

import android.app.Application;
import android.content.Context;


/**
 * Created by toyamaosamuyu on 2015/01/14.
 */
public class TimetableApp extends Application{
    private static final String TAG = TimetableApp.class.getSimpleName();

    private TimetableComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        buildComponentAndInject();
    }

    public void buildComponentAndInject() {
        mComponent = TimetableComponent.Initializer.init(this);
        mComponent.inject(this);
    }

    public static TimetableApp get(final Context context) {
        return (TimetableApp) context.getApplicationContext();
    }

    public TimetableComponent getComponent() {
        return mComponent;
    }

}
