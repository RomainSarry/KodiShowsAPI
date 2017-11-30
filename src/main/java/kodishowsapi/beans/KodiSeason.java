package kodishowsapi.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Romain on 05/11/2017.
 */
public class KodiSeason {
    private String id;
    
    private Map<Integer, KodiEpisode> episodes;

    public KodiSeason(ResultSet rs) {
        try {
            id = rs.getString("idSeason");
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<Integer, KodiEpisode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(Map<Integer, KodiEpisode> episodes) {
		this.episodes = episodes;
	}
	
	public KodiEpisode getEpisode(Integer number) {
		return episodes.get(number);
	}
	
	public void putEpisode(Integer number, KodiEpisode episode) {
		this.episodes.put(number, episode);
	}
}
