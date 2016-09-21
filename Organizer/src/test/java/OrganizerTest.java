import java.io.File;

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
        String path = "C:\\Users\\Tanner\\Google Drive\\School";
        Organizer o = new Organizer(path);
        String s = o.getNewPath(new MyFile("C:\\Users\\Tanner\\Google Drive\\School\\Former Classes\\Algorithms\\CS 4150 Asymptotic Complexity.pptx"),4);
        //Organizer o = new Organizer();
        //o.organize(path);
        System.out.println(s);
    }

}