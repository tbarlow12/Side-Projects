/**
 * Created by Tanner on 9/20/2016.
 */
import org.apache.commons.io.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class Organizer {

    private Hashtable<String,ArrayList<MyFile>> filesByExtension;
    private MyFile rootFolder;

    public Organizer(String path){
        rootFolder = new MyFile(path);
    }



    public void captureFileState(String path){
        MyFile root = new MyFile(path);
    }


    public void organize(int level){


    }
    
    private void organizeRecursive(MyFile directory, int level) {
        ArrayList<MyFile> myFiles = directory.getMyFiles();
        for(int i = 0; i < myFiles.size(); i++){
            MyFile f = myFiles.get(i);
            if(f.isDirectory()){
                organizeRecursive(f, level);
            }else if(f.isFile()){
                processFile(f, level);
            }
        }
    }

    private void processFile(MyFile f, int level) {
        addToFilesByExt(f);
        String newPath = getNewPath(f,level);
        /*
        try {
            FileUtils.moveFile(f,f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    String getNewPath(MyFile f, int level) {
        StringBuilder sb = new StringBuilder();
        sb.append(rootFolder.getAbsolutePath());
        MyDate date = f.getDate();
        if(level > 0){
            sb.append("\\" + date.getYear());
            if(level > 1){
                sb.append("\\" + date.getMonth());
                if(level > 2){
                    sb.append("\\" + date.getDay());
                    if(level > 3){
                        sb.append("\\" + date.getHour());
                        if(level > 4){
                            sb.append("\\" + date.getMinute());
                        }
                    }
                }
            }
        }
        return sb.toString();
    }

    private void addToFilesByExt(MyFile f) {
        if(filesByExtension.containsKey(f.getExtension())){
            filesByExtension.get(f.getExtension()).add(f);
        }else{
            ArrayList<MyFile> files = new ArrayList<MyFile>();
            files.add(f);
            filesByExtension.put(f.getExtension(),files);
        }
    }
}
