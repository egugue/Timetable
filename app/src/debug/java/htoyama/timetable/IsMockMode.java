package htoyama.timetable;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by toyamaosamuyu on 2015/01/16.
 */
@Qualifier
@Retention(RUNTIME)
public @interface IsMockMode {
}
