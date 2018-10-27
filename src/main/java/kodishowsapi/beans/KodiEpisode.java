package kodishowsapi.beans;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KodiEpisode {
	private String id;

	private Integer number;

	private String idFile;
	
	private String title;
	
	private Boolean watched;

    public KodiEpisode(ResultSet rs) throws SQLException {
		id = rs.getString("idEpisode");
		number = Integer.valueOf(rs.getString("c13"));
		idFile = rs.getString("idFile");
		title = rs.getString("c00");
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
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
