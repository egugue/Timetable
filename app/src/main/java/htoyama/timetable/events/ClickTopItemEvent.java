package htoyama.timetable.events;

import htoyama.timetable.domain.models.TopItem;

/**
 * Created by toyamaosamuyu on 2014/12/30.
 */
public class ClickTopItemEvent {
    private TopItem mTopItem;

    public ClickTopItemEvent(TopItem topItem) {
        mTopItem = topItem;
    }

    public TopItem getTopItem() {
        return mTopItem;
    }
}
