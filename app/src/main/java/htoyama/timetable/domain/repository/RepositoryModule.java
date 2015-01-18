package htoyama.timetable.domain.repository;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import htoyama.timetable.domain.repository.loaders.TimeableLoader;
import htoyama.timetable.domain.repository.loaders.TopItemLoader;
import htoyama.timetable.domain.repository.sqlite.BaseInfoSqliteDao;
import htoyama.timetable.domain.repository.sqlite.TimetableSqliteDao;

/**
 * Created by toyamaosamuyu on 2015/01/14.
 */
@Module(
        injects = {
                TopItemLoader.class,
                TimeableLoader.class
        },
        complete = false,
        library = true
)
public class RepositoryModule {

    @Provides
    BaseInfoDao provideBaseInfoDao(Application application) {
        return new BaseInfoSqliteDao(application);
    }

    @Provides
    TimetableDao provideTimetableDao(Application application) {
        return new TimetableSqliteDao(application);
    }

}
