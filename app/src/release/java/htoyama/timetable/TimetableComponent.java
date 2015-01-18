package htoyama.timetable;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import htoyama.timetable.domain.repository.RepositoryModule;

/**
 * Created by toyamaosamuyu on 2015/01/18.
 */
@Singleton
@Component(modules = {TimetableModule.class, RepositoryModule.class})
public interface TimetableComponent extends Graph {

    public static class Initializer{
        public static TimetableComponent init(Application app) {
            return Dagger_TimetableComponent.builder()
                    .timetableModule(new TimetableModule(app))
                    .build();
        }
    }

}
