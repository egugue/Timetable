package htoyama.timetable.events;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by toyamaosamuyu on 2014/12/28.
 */
public class BusHolder {
    private static Bus sBus = new Bus(ThreadEnforcer.ANY);

    public static Bus getBus() {
        return sBus;
    }

    private BusHolder() {
        //nothing to do
    }

}
