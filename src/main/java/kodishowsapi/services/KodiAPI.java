package kodishowsapi.services;

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

    public KodiShow getShowByTitle(String title) {
        try {
            ResultSet resultSet = statement.executeQuery("select * from tvshow where c00  LIKE '%" + title + "%'");
            KodiShow show = new KodiShow(resultSet);
            show.setSeasons(getSeasons(show.getId()));
            return show;
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
}
