package kodishowsapi.services;

import kodishowsapi.beans.KodiEpisode;
import kodishowsapi.beans.KodiSeason;
import kodishowsapi.beans.KodiShow;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Romain on 05/11/2017.
 */
public class KodiAPI {
    public static final String FILE_LOCATION = "jdbc:sqlite:C:\\Users\\Romain\\AppData\\Roaming\\Kodi\\userdata\\Database\\MyVideos107.db";

    private Connection connection;

    private Statement statement;

    public KodiAPI() {
        openConnection();
    }

    public void openConnection() {
        connection = null;

        try {
            Class.forName("org.sqlite.JDBC").newInstance();
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource) envCtx.lookup("jdbc/sqlite");

            connection = ds.getConnection();
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void closeConnection() {
        try {
            if(connection != null) {
                connection.close();
            }
        } catch(SQLException e) {
            System.err.println(e);
        }
    }
    
    public KodiShow getShowAndDetailsByTitle(String title) {
    	KodiShow show = getShowByTitle(title);

    	Map<Integer, KodiSeason> seasonMap = getSeasons(show.getId());
    	if (seasonMap != null && !seasonMap.isEmpty()) {
            for (Map.Entry<Integer, KodiSeason> seasonEntry : seasonMap.entrySet()) {
                KodiSeason season = seasonEntry.getValue();
                season.setEpisodes(getEpisodes(season.getId()));
                if (season.getEpisodes()!= null && !season.getEpisodes().isEmpty()) {
                    show.putSeason(seasonEntry.getKey(), season);
                }
            }
        }
    	
    	return show;
    }

    public KodiShow getShowByTitle(String title) {
        try {
            ResultSet resultSet = statement.executeQuery("select * from tvshow where c00  LIKE '%" + title.replace("'", "''") + "%'");
            return new KodiShow(resultSet);
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    public Map<Integer, KodiSeason> getSeasons(String showId) {
        try {
            ResultSet resultSet = statement.executeQuery("select * from seasons where idShow = " + showId);
            Map<Integer, KodiSeason> seasons = new HashMap<Integer, KodiSeason>();

            while (resultSet.next()) {
                KodiSeason season = new KodiSeason(resultSet);
                seasons.put(resultSet.getInt("season"), season);
            }
            
            return seasons;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }
    
    public Map<Integer, KodiEpisode> getEpisodes(String seasonId) {
    	try {
    	    String query = "select * from episode where idSeason = " + seasonId + "";
    		ResultSet resultSet = statement.executeQuery(query);
    		Map<Integer, KodiEpisode> episodes = new HashMap<Integer, KodiEpisode>();

            while(resultSet.next()) {
                KodiEpisode episode = new KodiEpisode(resultSet);
                Integer episodeNumber = Integer.valueOf(resultSet.getString("c13"));

                episodes.put(episodeNumber, episode);
            }

            for (Map.Entry<Integer, KodiEpisode> episodeEntry : episodes.entrySet()) {
                KodiEpisode episode = episodeEntry.getValue();

                Integer playCount = statement.executeQuery("select * from files where idFile = " + episode.getIdFile()).getInt("playCount");
                episode.setWatched(playCount != 0);
            }
    			
    		return episodes;
    	} catch (SQLException e) {
            System.err.println(e);
            return null;
    	}
    }
}
