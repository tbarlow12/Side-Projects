import com.sun.org.apache.xpath.internal.operations.Or;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tanner on 9/14/2016.
 */
public class Organizer {

    public Organizer(){
        System.out.println("Creating organizer");
    }

    public int organize(String path){
        File rootFolder = new File(path);
        return organizeRecursive(rootFolder);
    }

    public int organizeRecursive(File directory){
        File[] files = directory.listFiles();
        int count = 0;
        for(int i = 0; i < files.length; i++){
            File f = files[i];
            if(f.isDirectory()){
                count += organizeRecursive(f);
            }else if(f.isFile()){
                ProcessFile(f);
            }
        }
        return count;
    }

    private void ProcessFile(File f) {
        BasicFileAttributes attr = getAttributes(f);
        FileTime date = attr.creationTime();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.toMillis());
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        String m = getMonth(month);
        int day = c.get(Calendar.DAY_OF_MONTH);

        System.out.println(f.getName() + " " + year + " " + m + " " + day);
    }

    private String getMonth(int month){
        switch(month){
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "";
        }

    }

    private static BasicFileAttributes getAttributes(File f) {
        BasicFileAttributes attr = null;
        Path path = f.toPath();
        try{
            attr = Files.readAttributes(path,BasicFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return attr;
    }
}
