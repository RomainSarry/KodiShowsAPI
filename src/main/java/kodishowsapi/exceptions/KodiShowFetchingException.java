package kodishowsapi.exceptions;

/**
 * Created by Romain on 27/10/2018.
 */
public class KodiShowFetchingException extends KodiShowsAPIException {
    public KodiShowFetchingException(String showName) {
        super("Show not found : " + showName);
    }
    public KodiShowFetchingException() {
        super("Shows not found");
    }
}
