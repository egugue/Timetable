package htoyama.timetable;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by toyamaosamuyu on 2015/01/14.
 */
@Singleton
@Component(
        modules = {DebugTimetableModule.class}
)
public interface Graph {
    void inject(TimetableApp timetableApp);

    public static class Initializer{
        public static Graph init(Application application, boolean isMockMode) {
            return Dagger_Graph.builder()
                    .debugTimetableModule(new DebugTimetableModule(application, isMockMode))
                    .build();
        }
    }

    Application application();
    @IsMockMode boolean isMockMode();
}
