package htoyama.timetable.utils;

/**
 * Created by toyamaosamuyu on 2015/01/05.
 */
public class StringUtils {
    private static final String TAG = StringUtils.class.getSimpleName();
    private static final String FILE_SCHEME_PREFIX = "file://";

    public static String getNewLineChar() {
        return System.getProperty("line.separator");
    }

    /**
     * 以下の項目をすべてトリミングする。
     * 1:半角スペース, 2:全角スペース
     *
     * @param str
     * @return
     */
    public static String trimSpacing(String str) {
        return str.replaceAll("[ 　]", "");
    }

    /**
     * {@code filePath}の先頭に、{@code file://}がついていた場合トリミングする。
     *
     * @param filePath ファイルパス
     * @return {@code file://}がついていた場合トリミングした値。
     *         ついてない場合は値を変えずにそのまま返す。
     */
    public static String trimFileSchemeIfNeeded(String filePath) {
        if (!filePath.startsWith(FILE_SCHEME_PREFIX)) {
            return filePath;
        }

        final int prefixLength = FILE_SCHEME_PREFIX.length();
        return filePath.substring(prefixLength);
    }

    private StringUtils(){}
}
