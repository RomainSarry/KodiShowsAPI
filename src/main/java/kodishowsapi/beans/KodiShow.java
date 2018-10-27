package kodishowsapi.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Romain on 05/11/2017.
 */
public class KodiShow {
    private String id;

    private String title;

    private Map<Integer, KodiSeason> seasons;

    public KodiShow(ResultSet rs) throws SQLException {
        title = rs.getString("c00");
        id = rs.getString("idShow");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<Integer, KodiSeason> getSeasons() {
        return seasons;
    }

    public void setSeasons(Map<Integer, KodiSeason> seasons) {
        this.seasons = seasons;
    }
    
    public KodiSeason getSeason(Integer number) {
    	if (seasons == null) {
    		return null;
    	}
    	return seasons.get(number);
    }
    
    public void putSeason(Integer number, KodiSeason season) {
    	if (this.seasons == null) {
    		seasons = new HashMap<Integer, KodiSeason>();
    	}
    	this.seasons.put(number, season);
    }
    
    public Integer getNumberOfUnwatchedEpisodes() {
    	Integer numberOfEpisodes = 0;

    	if (seasons != null && !seasons.isEmpty()) {
            for (Map.Entry<Integer, KodiSeason> seasonEntry : seasons.entrySet()) {
                Map<Integer, KodiEpisode> episodeMap = seasonEntry.getValue().getEpisodes();
                if (episodeMap != null && !episodeMap.isEmpty()) {
                    for (Map.Entry<Integer, KodiEpisode> episodeEntry : seasonEntry.getValue().getEpisodes().entrySet()) {
                        if (!episodeEntry.getValue().isWatched()) {
                            numberOfEpisodes++;
                        }
                    }
                }
            }
        }
    	
    	return numberOfEpisodes;
    }
    
    public Boolean hasEpisode(Integer season, Integer episode) {
    	return getSeason(season) != null && getSeason(season).getEpisode(episode) != null;
    }
}
