package htoyama.timetable.domain.repository;

import dagger.Component;
import htoyama.timetable.Graph;
import htoyama.timetable.TimetableApp;
import htoyama.timetable.domain.repository.loaders.TimeableLoader;
import htoyama.timetable.domain.repository.loaders.TopItemLoader;

/**
 * Created by toyamaosamuyu on 2015/01/15.
 */
@Component(
        modules = {RepositoryModule.class},
        dependencies = {Graph.class}
)
public interface RepositoryGraph {
    void inject(TopItemLoader topItemLoader);
    void inject(TimeableLoader timeableLoader);

    public static class Initializer {
        public static RepositoryGraph init(TimetableApp app) {
            return Dagger_RepositoryGraph.builder()
                    .graph(app.getGraph())
                    .build();
        }
    }

}
