/**
 * Created by Tanner on 9/20/2016.
 */
import org.apache.commons.io.*;

import java.io.File;
import java.io.IOException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    
    public void organize(String path){
        MyFile rootFolder = new MyFile(path);
        organizeRecursive(rootFolder);
    }

    private void organizeRecursive(MyFile directory) {
        File[] files = directory.listFiles();
        for(int i = 0; i < files.length; i++){
            File f = files[i];
            MyFile m = new MyFile(f);
            if(m.isDirectory()){
                organizeRecursive(m);
            }else if(m.isFile()){
                processFile(m);
            }
        }
    }

    private void processFile(MyFile f) {
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
}
