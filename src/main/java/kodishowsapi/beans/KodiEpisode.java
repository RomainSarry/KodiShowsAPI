package kodishowsapi.beans;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KodiEpisode {
	private String id;

	private String idFile;
	
	private String title;
	
	private Boolean watched;

    public KodiEpisode(ResultSet rs) {
        try {
            id = rs.getString("idEpisode");
            idFile = rs.getString("idFile");
            title = rs.getString("c00");
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

    public String getIdFile() {
        return idFile;
    }

    public void setIdFile(String idFile) {
        this.idFile = idFile;
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
