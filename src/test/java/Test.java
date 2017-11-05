import kodishowsapi.beans.KodiShow;
import kodishowsapi.services.KodiAPI;

/**
 * Created by Romain on 05/11/2017.
 */
public class Test {
    @org.junit.Test
    public void test() {
        KodiAPI api = new KodiAPI();
        KodiShow show = api.getShowByTitle("Parks and Rec");
    }
}
