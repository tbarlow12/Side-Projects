/**
 * Created by Tanner on 9/20/2016.
 */
import org.apache.commons.io.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Organizer {

    public Organizer(){
        /*
        File source = new File("");
        File dest = new File("");
        try{
            FileUtils.copyDirectory(source,dest);
        }catch(IOException e){
            e.printStackTrace();
        }
        */
    }



    public void captureFileState(String path){
        MyFile root = new MyFile(path);
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


    }






}
