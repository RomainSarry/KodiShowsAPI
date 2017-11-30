package kodishowsapi.beans;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KodiEpisode {
	private String id;
	
	private String title;
	
	private Boolean watched;

    public KodiEpisode(ResultSet rs) {
        try {
            id = rs.getString("idEpisode");
            title = rs.getString("c00");
            watched = rs.getBoolean("c08");
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

	public Boolean isWatched() {
		return watched;
	}

	public void setWatched(Boolean watched) {
		this.watched = watched;
	}
}
