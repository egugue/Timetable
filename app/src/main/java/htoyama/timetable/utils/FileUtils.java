package htoyama.timetable.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by toyamaosamuyu on 2015/01/10.
 */
public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();

    public static List<String> readFileToLines(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        List<String> lines = new ArrayList<>();
        String line;
        try {
            while((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return lines;
    }


    private FileUtils() {}
}
