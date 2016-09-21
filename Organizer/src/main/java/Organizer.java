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
        File rootFolder = new File(path);
        organizeRecursive(rootFolder);
    }

    private void organizeRecursive(File directory) {
        File[] files = directory.listFiles();
        for(int i = 0; i < files.length; i++){
            File f = files[i];
            if(f.isDirectory()){
                organizeRecursive(f);
            }else if(f.isFile()){
                processFile(f);
            }
        }
    }

    private void processFile(File f) {
        BasicFileAttributes attr = getAttributes(f);
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
