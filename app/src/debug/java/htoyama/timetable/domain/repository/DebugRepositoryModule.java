package htoyama.timetable.domain.repository;

import dagger.Module;
import dagger.Provides;
import htoyama.timetable.domain.repository.loaders.TimeableLoader;
import htoyama.timetable.domain.repository.loaders.TopItemLoader;
import htoyama.timetable.domain.repository.stub.BaseInfoDaoStub;
import htoyama.timetable.domain.repository.stub.TimetableDaoStub;

/**
 * Created by toyamaosamuyu on 2015/01/18.
 */
@Module(
        injects = {
                TopItemLoader.class,
                TimeableLoader.class
        },
        complete = false,
        library = true
)
public class DebugRepositoryModule {

    @Provides
    BaseInfoDao provideBaseInfoDao() {
        return new BaseInfoDaoStub();
    }

    @Provides
    TimetableDao provideTimetableDao() {
        return new TimetableDaoStub();
    }

}
