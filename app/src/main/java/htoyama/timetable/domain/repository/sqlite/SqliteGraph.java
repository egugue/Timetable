package htoyama.timetable.domain.repository.sqlite;

import javax.inject.Singleton;

import dagger.Component;
import htoyama.timetable.DebugTimetableModule;
import htoyama.timetable.Graph;
import htoyama.timetable.TimetableApp;
import htoyama.timetable.domain.repository.RepositoryModule;

/**
 * Created by toyamaosamuyu on 2015/01/14.
 */
@Singleton
@Component(
        modules = {SqliteModule.class},
        dependencies = {Graph.class}
)
public interface SqliteGraph {
    void inject(BaseInfoSqliteDao baseInfoSqliteDao);

    public static class Initializer{
        public static SqliteGraph init(TimetableApp app) {
            return Dagger_SqliteGraph.builder()
                    .graph(app.getGraph())
                    .build();

        }
    }
}
