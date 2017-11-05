package kodishowsapi.beans;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Romain on 05/11/2017.
 */
public class KodiSeason {
    String id;

    public KodiSeason(ResultSet rs) {
        try {
            id = rs.getString("idSeason");
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
}
