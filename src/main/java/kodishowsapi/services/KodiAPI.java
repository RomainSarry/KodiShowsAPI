package kodishowsapi.services;

import kodishowsapi.beans.KodiEpisode;
import kodishowsapi.beans.KodiSeason;
import kodishowsapi.beans.KodiShow;
import kodishowsapi.exceptions.KodiDatabaseConnectionException;
import kodishowsapi.exceptions.KodiDatabaseDisconnectionException;
import kodishowsapi.exceptions.KodiEpisodesFetchingException;
import kodishowsapi.exceptions.KodiShowFetchingException;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Romain on 05/11/2017.
 */
public class KodiAPI {

    private String databasePath;

    private Connection connection;

    private Statement statement;

    public KodiAPI(String databasePath) throws KodiDatabaseConnectionException {
        this.databasePath = databasePath;
        openConnection();
    }

    public void openConnection() throws KodiDatabaseConnectionException {
        connection = null;

        try {
            Class.forName("org.sqlite.JDBC").newInstance();
            connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);

            statement = connection.createStatement();
            statement.setQueryTimeout(30);
        } catch (Exception e) {
            throw new KodiDatabaseConnectionException(databasePath);
        }
    }

    public void closeConnection() throws KodiDatabaseDisconnectionException {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new KodiDatabaseDisconnectionException(databasePath);
            }
        }
    }
    
    public KodiShow getShow(String title) throws KodiShowFetchingException, KodiEpisodesFetchingException {
        KodiShow show;

        try {
            show = new KodiShow(statement.executeQuery("select * from tvshow where c00  = '" + title.replace("'", "''") + "'"));
            show.setSeasons(getSeasons(show));
        } catch (SQLException e) {
            throw new KodiShowFetchingException(title);
        }
    	
    	return show;
    }

    public Map<Integer, KodiSeason> getSeasons(KodiShow show) throws KodiEpisodesFetchingException {
        try {
            ResultSet resultSet = statement.executeQuery("select * from seasons where idShow = " + show.getId());
            Map<Integer, KodiSeason> seasons = new HashMap<>();

            while (resultSet.next()) {
                KodiSeason season = new KodiSeason(resultSet);

                Map<Integer, KodiEpisode> episodes = getEpisodes(season);
                if (episodes != null && !episodes.isEmpty()) {
                    season.setEpisodes(episodes);
                    seasons.put(resultSet.getInt("season"), season);
                }
            }
            
            return seasons;
        } catch (KodiEpisodesFetchingException | SQLException e) {
            throw new KodiEpisodesFetchingException(show);
        }
    }
    
    public Map<Integer, KodiEpisode> getEpisodes(KodiSeason season) throws KodiEpisodesFetchingException {
    	try {
    		ResultSet resultSet = statement.executeQuery("select * from episode where idSeason = " + season.getId());
    		Map<Integer, KodiEpisode> episodes = new HashMap<>();

            while(resultSet.next()) {
                KodiEpisode episode = new KodiEpisode(resultSet);
                Integer playCount = statement.executeQuery("select * from files where idFile = " + episode.getIdFile()).getInt("playCount");
                episode.setWatched(playCount != 0);

                episodes.put(episode.getNumber(), episode);
            }
    			
    		return episodes;
    	} catch (SQLException e) {
            throw new KodiEpisodesFetchingException(season);
    	}
    }
}
