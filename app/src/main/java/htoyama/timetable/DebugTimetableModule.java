package htoyama.timetable;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import htoyama.timetable.domain.repository.BaseInfoDao;
import htoyama.timetable.domain.repository.RepositoryModule;
import htoyama.timetable.domain.repository.TimetableDao;
import htoyama.timetable.domain.repository.sqlite.BaseInfoSqliteDao;
import htoyama.timetable.domain.repository.sqlite.TimetableSqliteDao;
import htoyama.timetable.domain.repository.stub.BaseInfoDaoStub;
import htoyama.timetable.domain.repository.stub.TimetableDaoStub;

/**
 * Created by toyamaosamuyu on 2015/01/14.
 */
@Module(
        includes = {
                RepositoryModule.class
        },
        injects = {
                TimetableApp.class
        }
)
public final class DebugTimetableModule {
    private final boolean mIsMockMode;
    private Application mApplication;

    public DebugTimetableModule(final Application application, boolean isProvideMocks) {
        mApplication = application;
        mIsMockMode = isProvideMocks;
    }

    @Provides @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides @IsMockMode
    boolean provideIsMockMode() {
        return mIsMockMode;
    }
}
