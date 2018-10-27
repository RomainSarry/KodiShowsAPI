package kodishowsapi.exceptions;

/**
 * Created by Romain on 27/10/2018.
 */
public class KodiDatabaseDisconnectionException extends KodiShowsAPIException {
    public KodiDatabaseDisconnectionException(String databasePath) {
        super("Error disconnecting from database : " + databasePath);
    }
}
