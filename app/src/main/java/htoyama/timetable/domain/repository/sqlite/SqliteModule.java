package htoyama.timetable.domain.repository.sqlite;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by toyamaosamuyu on 2015/01/14.
 */
@Module(
        includes = {
                TimetableSqliteDao.class,
                BaseInfoSqliteDao.class
        },
        complete = false,
        library = true
)
public class SqliteModule {

    @Provides @Singleton
    TimetableDbHelper provideTimtableDbHelper(Application application) {
        return new TimetableDbHelper(application);
    }

}
