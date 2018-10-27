package kodishowsapi.exceptions;

/**
 * Created by Romain on 27/10/2018.
 */
public class KodiDatabaseConnectionException extends KodiShowsAPIException {
    public KodiDatabaseConnectionException(String databasePath) {
        super("Cannot connect to database : " + databasePath);
    }
}
