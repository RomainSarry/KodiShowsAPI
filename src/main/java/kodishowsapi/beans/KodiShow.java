package kodishowsapi.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * Created by Romain on 05/11/2017.
 */
public class KodiShow {
    String id;

    String title;

    Map<Integer, KodiSeason> seasons;

    public KodiShow(ResultSet rs) {
        try {
            title = rs.getString("c00");
            id = rs.getString("idShow");
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
}
