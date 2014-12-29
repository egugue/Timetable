package htoyama.timetable.events;

import java.util.List;

import htoyama.timetable.domain.models.TopItem;

/**
 * Created by toyamaosamuyu on 2014/12/30.
 */
public class LoadTopItemListCompleteEvent {
    private List<TopItem> mTopItemList;

    public LoadTopItemListCompleteEvent(List<TopItem> list) {
        mTopItemList = list;
    }

    public List<TopItem> getTopItemList() {
        return mTopItemList;
    }

}
