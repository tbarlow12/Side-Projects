/**
 * Created by Tanner on 9/20/2016.
 */
import org.apache.commons.io.*;

import java.io.File;
import java.io.IOException;

public class Organizer {
    public Organizer(){
        File source = new File("");
        File dest = new File("");
        try{
            FileUtils.copyDirectory(source,dest);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
