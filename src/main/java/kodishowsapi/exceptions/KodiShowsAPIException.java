package kodishowsapi.exceptions;

/**
 * Created by Romain on 27/10/2018.
 */
public abstract class KodiShowsAPIException extends Exception {
    public KodiShowsAPIException(String message) {
        super(message);
    }
}
