package kodishowsapi.exceptions;

import kodishowsapi.beans.KodiSeason;
import kodishowsapi.beans.KodiShow;

/**
 * Created by Romain on 27/10/2018.
 */
public class KodiEpisodesFetchingException extends KodiShowsAPIException {
    public KodiEpisodesFetchingException(KodiSeason season) {
        super("Episodes not found for season : " + season.getId());
    }

    public KodiEpisodesFetchingException(KodiShow show) {
        super("Episodes not found for season : " + show.getTitle());
    }
}
