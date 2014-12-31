package htoyama.timetable.tools;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by toyamaosamuyu on 2014/12/31.
 */
public class AssetDao {
    private static final String TAG = AssetDao.class.getSimpleName();
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private final AssetManager mAssetManager;

    public AssetDao(final Context context) {
        mAssetManager = context.getAssets();
    }

    public String loadAssetFile(String fileName) {

        StringBuilder file = new StringBuilder();

        try {
            InputStream is = mAssetManager.open(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = br.readLine()) != null) {
                file.append(line)
                    .append(LINE_SEPARATOR);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.toString();
    }

}
