package sharingiscaring.sharingiscaring.ElasticSearch;

import android.os.AsyncTask;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;

/**
 * @author hoye
 * @since  3/18/16.
 * @version 0.1
 *
 * This object is used to validate user input when they are trying to log into the application.
 *
 * Based on Lab07 ElasticSearchController.
 * Original created by esports on 2/17/16.
 * https://github.com/earthiverse/lonelyTwitter
 *
 */
public abstract class ElasticSearchController {
    protected static JestDroidClient client;
    private final static String url = "http://cmput301.softwareprocess.es:8080";
    protected final static String searchIndex = "team17";
    protected static String searchType;


    //eliminate the unnecessary semicolons
    public abstract static class GetLoginsTask{}
    public abstract static class AddLoginTask{}


    protected static void verifyClient(){
        // 1. Verify that client exists.
        // 2. If it does not, then make it.
        if (client == null){
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(url);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);

            client = (JestDroidClient) factory.getObject();
        }
    }
}
