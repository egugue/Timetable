package htoyama.timetable;

import android.app.Application;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by toyamaosamuyu on 2015/01/14.
 */
@Module(
        injects = {
                TimetableApp.class
        }
)
public final class TimetableModule {
    private Application mApplication;

    public TimetableModule(final Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

}
