package htoyama.timetable.exceptions;

/**
 * Thrown when file is not expected format.
 */
public class FileFormatException extends RuntimeException {

    public FileFormatException(String detailMessage) {
        super(detailMessage);
    }
}
