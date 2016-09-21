import static org.junit.Assert.*;

/**
 * Created by Tanner on 9/21/2016.
 */
public class OrganizerTest {
    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void connectToDatabase() throws Exception {
        LocalDB db = new LocalDB();
        db.connectToDatabase();
       // db.closeConnection();

    }

    @org.junit.Test
    public void captureFileState() throws Exception {

    }

    @org.junit.Test
    public void organize() throws Exception {

    }

}