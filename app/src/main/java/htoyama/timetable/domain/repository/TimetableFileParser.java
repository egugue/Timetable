package htoyama.timetable.domain.repository;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import htoyama.timetable.exceptions.FileFormatException;
import htoyama.timetable.utils.FileUtils;
import htoyama.timetable.utils.StringUtils;

/**
 * Parses timetable file inputed from {@link htoyama.timetable.presentation.activities.InputActivity}.
 */
public class TimetableFileParser {
    private static final String TAG = TimetableFileParser.class.getSimpleName();
    private static final String WORD_SEPARETOR = ",";

    /**
     * Parses file to List of {@link Item}
     *
     * @param filePath file path to read
     * @return List of Item that was each line in file
     * @throws FileNotFoundException when file cannot found
     */
    public List<Item> parse(String filePath) throws FileNotFoundException {
        List<String> lines = FileUtils.readFileToLines(filePath);
        List<Item> itemList = new ArrayList<>();

        for (String line : lines) {
            itemList.add(parseLineToItem(line));
        }

        return itemList;
    }

    private Item parseLineToItem(String line) {
        String[] values = line.split(WORD_SEPARETOR);
        if (values.length != 3) {
            throw new FileFormatException(
                    "each line must separete 3 words with "+WORD_SEPARETOR+StringUtils.getNewLineChar()
                            +"but separeted words count was "+values.length);
        }

        int trainId = Integer.parseInt(values[0]);
        String depatureTime = convertToDepatureTime(values[1]);
        String destination = values[2];

        return new Item(trainId, depatureTime, destination);
    }

    private String convertToDepatureTime(String stg) {
        return new StringBuffer(stg)
                .insert(2, ":")
                .toString();
    }

    /**
     * Specifies values as that each line is separeted.
     */
    public static class Item {
        public final int trainId;
        public final String depatureTime;
        public final String destination;

        public Item(int trainId, String depatureTime, String destination) {
            this.trainId = trainId;
            this.depatureTime = depatureTime;
            this.destination = destination;
        }
    }

}
