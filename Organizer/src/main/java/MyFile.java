import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Tanner on 9/20/2016.
 */
public class MyFile extends File {
    private String oldPath;
    private String newPath;
    private MyDate date;
    private ArrayList<MyFile> myFiles;

    public MyFile(String pathname) {
        super(pathname);
        oldPath = pathname;
        BasicFileAttributes attr = getAttributes();
        date = getDate(attr);
        File[] files = this.listFiles();
        myFiles = new ArrayList<MyFile>();
        for(int i = 0; i < files.length; i++){
            myFiles.add(new MyFile(files[i]));
        }
    }

    private BasicFileAttributes getAttributes() {
        BasicFileAttributes attr = null;
        Path path = this.toPath();
        try{
            attr = Files.readAttributes(path,BasicFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return attr;
    }

    private MyDate getDate(BasicFileAttributes attr) {
        FileTime fileTime = attr.creationTime();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(fileTime.toMillis());
        return new MyDate(c);
    }

    public MyFile(File f){
        this(f.getAbsolutePath());
    }

    public void setDate(MyDate date) {
        this.date = date;
    }
}
